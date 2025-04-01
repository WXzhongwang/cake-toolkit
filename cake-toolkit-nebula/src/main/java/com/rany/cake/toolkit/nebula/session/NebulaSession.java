package com.rany.cake.toolkit.nebula.session;

import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.IOErrorException;

import java.util.Map;

/**
 * 图数据库通用操作接口
 *
 * @author lxy
 */
public interface NebulaSession {

    /**
     * 执行查询
     *
     * @param stmt 语句
     * @return 查询结果集
     * @throws IOErrorException IO异常
     */
    ResultSet executeQuery(String stmt) throws IOErrorException;

    /**
     * 执行带参数查询
     *
     * @param stmt 语句
     * @param parameterMap 参数
     * @return 查询结果集
     * @throws IOErrorException IO异常
     */
    ResultSet executeWithParameter(String stmt, Map<String, Object> parameterMap) throws IOErrorException;

    /**
     * 执行语句
     *
     * @param stmt 语句
     * @return json
     * @throws IOErrorException IO异常
     */
    String executeJson(String stmt) throws IOErrorException;

    /**
     * 带参数执行语句
     *
     * @param stmt 语句
     * @param params 参数
     * @return json
     * @throws IOErrorException IO异常
     */
    String executeJsonWithParams(String stmt, Map<String, Object> params) throws IOErrorException;
}
