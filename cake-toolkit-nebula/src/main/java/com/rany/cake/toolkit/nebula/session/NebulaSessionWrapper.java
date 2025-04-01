package com.rany.cake.toolkit.nebula.session;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.InvalidSessionException;
import com.vesoft.nebula.client.graph.net.Session;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 加强原有nebula-client能力
 *
 * @author lxy
 */
@Slf4j
public class NebulaSessionWrapper implements NebulaSession, AutoCloseable {

    private final Session session;

    private final NebulaPoolSessionManager sessionManager;

    private final LocalDateTime createTime;

    private final AtomicBoolean available = new AtomicBoolean(true);

    public NebulaSessionWrapper(Session session, NebulaPoolSessionManager sessionManager) {
        this.session = session;
        this.sessionManager = sessionManager;
        this.createTime = LocalDateTime.now();
    }

    @Override
    public ResultSet executeQuery(String stmt) throws IOErrorException {
        if (!ping()) {
            throw new InvalidSessionException();
        }
        if (!available()) {
            throw new InvalidSessionException();
        }
        return session.execute(stmt);
    }

    @Override
    public ResultSet executeWithParameter(String stmt, Map<String, Object> parameterMap) throws IOErrorException {
        if (!ping()) {
            throw new InvalidSessionException();
        }
        if (!available()) {
            throw new InvalidSessionException();
        }
        return session.executeWithParameter(stmt, parameterMap);
    }

    @Override
    public String executeJson(String stmt)
            throws IOErrorException {
        if (!ping()) {
            throw new InvalidSessionException();
        }
        if (!available()) {
            throw new InvalidSessionException();
        }
        return session.executeJson(stmt);
    }

    @Override
    public String executeJsonWithParams(String stmt, Map<String, Object> params)
            throws IOErrorException {
        if (!ping()) {
            throw new InvalidSessionException();
        }
        if (!available()) {
            throw new InvalidSessionException();
        }
        return session.executeJsonWithParameter(stmt, params);
    }

    /**
     * 每次执行语句会刷新update_time
     *
     * @return session是否可继续使用
     */
    public boolean ping() {
        return !createTime.isBefore(LocalDateTime.now().minusMinutes(30));
    }

    void setNoAvailable() {
        this.available.set(false);
    }

    void release() {
        setNoAvailable();
        session.release();
    }

    boolean available() {
        return available.get();
    }

    Session getSession() {
        return session;
    }

    @Override
    public void close() {
        this.sessionManager.returnSessionWrapper(this);
    }
}
