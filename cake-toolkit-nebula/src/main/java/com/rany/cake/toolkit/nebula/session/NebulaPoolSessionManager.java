package com.rany.cake.toolkit.nebula.session;

import com.rany.cake.toolkit.nebula.enums.ErrorEnum;
import com.rany.cake.toolkit.nebula.exception.NebulaException;
import com.vesoft.nebula.client.graph.SessionsManagerConfig;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;

import java.net.UnknownHostException;
import java.util.BitSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 单一space的nebula-session管理器，增强了复用session能力
 *
 * @author lxy
 */
@Slf4j
public class NebulaPoolSessionManager {
    private final SessionsManagerConfig config;
    private NebulaPool pool = null;
    private final CopyOnWriteArrayList<NebulaSessionWrapper> sessionList;
    private BitSet canUseBitSet;
    private Boolean isClose = false;

    public NebulaPoolSessionManager(SessionsManagerConfig config) {
        this.config = config;
        this.sessionList = new CopyOnWriteArrayList<>();
        checkConfig();
    }

    private void checkConfig() {
        if (config.getAddresses().isEmpty()) {
            throw new NebulaException(ErrorEnum.EMPTY_GRAPH_ADDRESS);
        }

        if (config.getSpaceName().isEmpty()) {
            throw new NebulaException(ErrorEnum.EMPTY_SPACE_NAME);
        }
    }

    /**
     * 获取session-wrapper，不支持多线程
     *
     * @return SessionWrapper 包装后的session
     * @throws RuntimeException the exception when get SessionWrapper
     */
    public synchronized NebulaSessionWrapper getSessionWrapper() throws ClientServerIncompatibleException {
        checkClose();
        if (pool == null) {
            init();
        }
        if (canUseBitSet.isEmpty()
                && sessionList.size() >= config.getPoolConfig().getMaxConnSize()) {
            throw new NebulaException(ErrorEnum.SESSION_COUNT_REACH_LIMIT);
        }
        if (!canUseBitSet.isEmpty()) {
            int index = canUseBitSet.nextSetBit(0);
            if (index >= 0) {
                if (canUseBitSet.get(index)) {
                    NebulaSessionWrapper wrapper = sessionList.get(index);
                    if (wrapper.ping()) {
                        canUseBitSet.set(index, false);
                        return wrapper;
                    }
                    wrapper.release();
                    sessionList.remove(index);
                    wrapper = createNewSessionWrapper(index);
                    canUseBitSet.set(index, false);
                    return wrapper;
                }
            }
        }
        return createNewSessionWrapper(sessionList.size());
    }

    private NebulaSessionWrapper createNewSessionWrapper(int index) throws ClientServerIncompatibleException {
        // create new session
        try {
            Session session = pool.getSession(
                    config.getUserName(), config.getPassword(), config.getReconnect());
            ResultSet resultSet = session.execute("USE " + config.getSpaceName());
            if (!resultSet.isSucceeded()) {
                log.error(resultSet.getErrorMessage());
                throw new NebulaException(ErrorEnum.FAIL_TO_SWITCH_SPACE);
            }
            NebulaSessionWrapper sessionWrapper = new NebulaSessionWrapper(session, this);
            sessionList.add(index, sessionWrapper);
            return sessionWrapper;
        } catch (AuthFailedException | NotValidConnectionException | IOErrorException e) {
            log.error("Get session failed: " + e.getMessage());
            throw new NebulaException(ErrorEnum.OPERATION_FAILED);
        }
    }

    /**
     * 将session-wrapper返还给管理器
     *
     * @param session The SessionWrapper
     */
    public synchronized void returnSessionWrapper(NebulaSessionWrapper session) {
        checkClose();
        if (session == null) {
            return;
        }
        int index = sessionList.indexOf(session);
        if (index >= 0) {
            Session ses = session.getSession();
            sessionList.set(index, new NebulaSessionWrapper(ses, this));
            session.setNoAvailable();
            canUseBitSet.set(index, true);
        }
    }

    /**
     * 释放全部session，关闭conn-pool
     */
    public synchronized void close() {
        for (NebulaSessionWrapper session : sessionList) {
            session.release();
        }
        pool.close();
        sessionList.clear();
        isClose = true;
    }

    private void init() {
        try {
            pool = new NebulaPool();
            if (!pool.init(config.getAddresses(), config.getPoolConfig())) {
                throw new NebulaException(ErrorEnum.CONN_POOL_INIT_FAIL);
            }
            canUseBitSet = new BitSet(config.getPoolConfig().getMaxConnSize());
            canUseBitSet.set(0, config.getPoolConfig().getMaxConnSize(), false);
        } catch (UnknownHostException e) {
            log.error("Init the pool failed: " + e.getMessage());
            throw new NebulaException(ErrorEnum.OPERATION_FAILED);
        }
    }

    private void checkClose() {
        if (isClose) {
            throw new NebulaException(ErrorEnum.SESSION_MANAGER_CLOSED);
        }
    }
}
