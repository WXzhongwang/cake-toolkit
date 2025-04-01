package com.rany.cake.toolkit.nebula.dao;

import com.rany.cake.toolkit.nebula.model.GqlResult;
import com.vesoft.nebula.client.graph.exception.ClientServerIncompatibleException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.InvalidValueException;
import com.rany.cake.toolkit.nebula.model.SceneGraph;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author lxy
 * @since 2024.12
 */
public interface GraphMapper {

    /**
     * 执行更新sql
     *
     * @param sql sql
     * @return 更新状态码
     * @throws ClientServerIncompatibleException 异常
     * @throws IOErrorException 异常
     */
    int executeUpdateSql(String sql) throws ClientServerIncompatibleException, IOErrorException;

    /**
     * 批量执行更新语句
     *
     * @param sqlList sql列表
     * @return 更新状态码
     * @throws ClientServerIncompatibleException 异常
     * @throws IOErrorException 异常
     */
    int executeBatchUpdateSql(List<String> sqlList) throws ClientServerIncompatibleException, IOErrorException;

    /**
     * 执行查询语句
     *
     * @param sql sql
     * @return 查询结果
     * @throws ClientServerIncompatibleException 异常
     * @throws IOErrorException 异常
     * @throws InvalidValueException 异常
     * @throws UnsupportedEncodingException 异常
     */
    GqlResult executeQuerySql(String sql) throws ClientServerIncompatibleException, IOErrorException, InvalidValueException, UnsupportedEncodingException;

    /**
     * 执行查询语句
     *
     * @param sql sql
     * @return 查询结果
     * @throws ClientServerIncompatibleException 异常
     * @throws IOErrorException 异常
     * @throws InvalidValueException 异常
     */
    String executeQuerySql2Json(String sql) throws ClientServerIncompatibleException, IOErrorException, InvalidValueException;

    /**
     * 执行查询语句
     *
     * @param sql sql
     * @return 查询结果
     * @throws ClientServerIncompatibleException 异常
     * @throws IOErrorException 异常
     * @throws InvalidValueException 异常
     */
    SceneGraph executeQuerySql2SceneGraph(String sql) throws ClientServerIncompatibleException, IOErrorException, InvalidValueException;
}
