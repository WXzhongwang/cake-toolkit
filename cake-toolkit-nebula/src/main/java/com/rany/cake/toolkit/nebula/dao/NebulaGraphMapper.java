package com.rany.cake.toolkit.nebula.dao;

import com.rany.cake.toolkit.nebula.enums.ErrorEnum;
import com.rany.cake.toolkit.nebula.exception.NebulaException;
import com.rany.cake.toolkit.nebula.model.GqlResult;
import com.rany.cake.toolkit.nebula.model.SceneGraph;
import com.rany.cake.toolkit.nebula.session.NebulaPoolSessionManager;
import com.rany.cake.toolkit.nebula.session.NebulaSessionWrapper;
import com.vesoft.nebula.ErrorCode;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.InvalidValueException;
import com.rany.cake.toolkit.nebula.common.util.NebGraphParser;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author lxy
 */
@Slf4j
public class NebulaGraphMapper implements GraphMapper {

    private static final int BATCH_EXEC_SIZE = 500;

    private final NebulaPoolSessionManager nebulaPoolSessionManager;

    public NebulaGraphMapper(NebulaPoolSessionManager sessionManager) {
        this.nebulaPoolSessionManager = sessionManager;
    }

    @Override
    public int executeUpdateSql(String sql) throws ClientServerIncompatibleException, IOErrorException {
        try (NebulaSessionWrapper session = nebulaPoolSessionManager.getSessionWrapper()) {
            return session.executeQuery(sql).getErrorCode();
        }
    }

    @Override
    public int executeBatchUpdateSql(List<String> sqlList) throws ClientServerIncompatibleException, IOErrorException {
        for (int i = 0; i < sqlList.size(); i += BATCH_EXEC_SIZE) {
            List<String> sqls = sqlList.subList(i, Math.min(sqlList.size(), i + BATCH_EXEC_SIZE));
            String sql = String.join(";", sqls);
            try (NebulaSessionWrapper session = nebulaPoolSessionManager.getSessionWrapper()) {
                int code = session.executeQuery(sql).getErrorCode();
                if (code != ErrorCode.SUCCEEDED.getValue()) {
                    throw new NebulaException(ErrorEnum.NGQL_EXEC_FAILED);
                }
            }
        }
        return 0;
    }

    @Override
    public GqlResult executeQuerySql(String sql) throws ClientServerIncompatibleException, IOErrorException, InvalidValueException, UnsupportedEncodingException {
        try (NebulaSessionWrapper session = nebulaPoolSessionManager.getSessionWrapper()) {
            return NebGraphParser.getInstance().to(session.executeQuery(sql));
        }
    }

    @Override
    public String executeQuerySql2Json(String sql) throws ClientServerIncompatibleException, IOErrorException, InvalidValueException {
        try (NebulaSessionWrapper session = nebulaPoolSessionManager.getSessionWrapper()) {
            return session.executeJson(sql);
        }
    }

    @Override
    public SceneGraph executeQuerySql2SceneGraph(String sql) throws ClientServerIncompatibleException, IOErrorException, InvalidValueException {
        try (NebulaSessionWrapper session = nebulaPoolSessionManager.getSessionWrapper()) {
            return NebGraphParser.getInstance().parsePathJson(session.executeJson(sql));
        }
    }
}
