package com.rany.cake.toolkit.nebula.common.util;

import com.rany.cake.toolkit.nebula.model.GqlResult;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.rany.cake.toolkit.nebula.model.SceneGraph;

public interface GraphParser {

    /**
     * 针对ResultSet返回形式做转换，以获取ResultSet中的各个列名及数据
     *
     * @param rs nebula的resultSet结果
     * @return GqlResult 获取到的ResultSet中的各个列名及数据
     * @throws Exception 异常
     */
    GqlResult to(final ResultSet rs) throws Exception;

    /**
     * 从执行结果字符串提取信息
     *
     * @param json json字符串
     * @return SceneGraph图对象
     */
    SceneGraph parsePathJson(String json);
}
