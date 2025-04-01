package com.rany.cake.toolkit.nebula.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.cake.toolkit.lang.utils.Lists;
import com.rany.cake.toolkit.nebula.exception.NebulaExecException;
import com.rany.cake.toolkit.nebula.model.BaseGraphModel;
import com.rany.cake.toolkit.nebula.model.GqlResult;
import com.rany.cake.toolkit.nebula.model.SceneGraph;
import com.vesoft.nebula.client.graph.data.*;
import com.vesoft.nebula.client.graph.exception.InvalidValueException;
import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 基于Nebula Api返回结果的解析器
 * @author lxy
 */
public class NebGraphParser implements GraphParser {
    @Getter
    private static final NebGraphParser instance = new NebGraphParser();
    private NebGraphParser (){}

    private static Object getId(ValueWrapper valueWrapper) throws InvalidValueException, UnsupportedEncodingException {
        if (valueWrapper == null) {
            return null;
        }
        if (valueWrapper.isString()) {
            return valueWrapper.asString();
        } else if (valueWrapper.isLong()) {
            return valueWrapper.asLong();
        }
        return null;
    }

    private static Object getValue(ValueWrapper valueWrapper) throws InvalidValueException, UnsupportedEncodingException {
        if (valueWrapper == null) {
            return null;
        }
        if (valueWrapper.isVertex() || valueWrapper.isEdge() || valueWrapper.isPath()
                || valueWrapper.isList() || valueWrapper.isMap() || valueWrapper.isSet()) {
            return valueWrapper.toString();
        }
        return getBasicValue(valueWrapper);
    }

    private static Object getBasicValue(ValueWrapper valueWrapper) throws InvalidValueException, UnsupportedEncodingException {
        if (valueWrapper == null) {
            return null;
        }
        if (valueWrapper.isNull()) {
            return "NULL";
        } else if (valueWrapper.isString()) {
            return valueWrapper.asString();
        } else if (valueWrapper.isLong()) {
            return valueWrapper.asLong();
        } else if (valueWrapper.isBoolean()) {
            return valueWrapper.asBoolean();
        } else if (valueWrapper.isDouble()) {
            return valueWrapper.asDouble();
        } else if (valueWrapper.isTime()) {
            return valueWrapper.asTime();
        } else if (valueWrapper.isTime()) {
            return valueWrapper.asTime();
        } else if (valueWrapper.isDate()) {
            return valueWrapper.asDate();
        } else if (valueWrapper.isDateTime()) {
            return valueWrapper.asDateTime();
        } else if (valueWrapper.isGeography()) {
            return valueWrapper.toString();
        } else if (valueWrapper.isEmpty()) {
            return "_EMPTY_";
        }
        return "";
    }

    private static Map<String, Object> getVertexInfo(ValueWrapper valueWrapper, Map<String, Object> data) throws InvalidValueException, UnsupportedEncodingException {
        if (data == null) {
            data = new HashMap<>();
        }
        final Node node = valueWrapper.asNode();
        data.put("vid", getId(node.getId()));
        data.put("tags", node.tagNames());
        final Map<String, Map<String, Object>> properties = new HashMap<>();
        for (final String tag : node.tagNames()) {
            Map<String, Object> pp = new HashMap<>();
            for (final Map.Entry<String, ValueWrapper> entry : node.properties(tag).entrySet()) {
                pp.put(entry.getKey(), getValue(entry.getValue()));
            }
            properties.put(tag, pp);
        }
        data.put("properties", properties);
        return data;
    }

    private static Map<String, Object> getEdgeInfo(ValueWrapper valueWrapper, Map<String, Object> data) throws InvalidValueException, UnsupportedEncodingException {
        if (data == null) {
            data = new HashMap<>();
        }
        final Relationship rs = valueWrapper.asRelationship();
        data.put("srcID", getId(rs.srcId()));
        data.put("dstID", getId(rs.dstId()));
        data.put("edgeName", rs.edgeName());
        data.put("rank", rs.ranking());

        final Map<String, Object> properties = new HashMap<>();
        for (Map.Entry<String, ValueWrapper> entry : rs.properties().entrySet()) {
            properties.put(entry.getKey(), getValue(entry.getValue()));
        }
        data.put("properties", properties);
        return data;
    }

    private static Map<String, Object> getPathInfo(ValueWrapper valueWrapper, Map<String, Object> data) throws InvalidValueException, UnsupportedEncodingException {
        if (data == null) {
            data = new HashMap<>();
        }
        final PathWrapper pathWrapper = valueWrapper.asPath();
        final List<Map<String, Object>> ships = new ArrayList<>();
        for (Relationship ship : pathWrapper.getRelationships()) {
            ships.add(convertRelationship(ship));
        }
        data.put("relationships", ships);


        final List<Map<String, Object>> vertices = new ArrayList<>();
        for (Node node : pathWrapper.getNodes()) {
            vertices.add(convertPathNode(node));
        }
        data.put("nodes", vertices);

        // segments
        List<PathWrapper.Segment> segments = pathWrapper.getSegments();
        List<Map<String, Object>> segmentsParsedList = new ArrayList<>(segments.size());
        for (PathWrapper.Segment segment : segments) {
            Node startNode = segment.getStartNode();
            Node endNode = segment.getEndNode();
            Relationship relationship = segment.getRelationShip();
            Map<String, Object> segmentInfo = new HashMap<>();
            segmentInfo.put("startNode", convertPathNode(startNode));
            segmentInfo.put("endNode", convertPathNode(endNode));
            segmentInfo.put("relationShip", convertRelationship(relationship));
            segmentsParsedList.add(segmentInfo);
        }
        data.put("segments", segmentsParsedList);
        return data;
    }
    private static Map<String, Object> convertPathNode(Node startNode) throws UnsupportedEncodingException {
        Map<String, Object> startNodeInfo = new HashMap<>();
        startNodeInfo.put("vid", getId(startNode.getId()));
        startNodeInfo.put("tags", startNode.tagNames());
        final Map<String, Map<String, Object>> propertiesMap = new HashMap<>();
        for (String tag : startNode.tagNames()) {
            Map<String, Object> properties = new HashMap<>();
            for (Map.Entry<String, ValueWrapper> entry : startNode.properties(tag).entrySet()) {
                properties.put(entry.getKey(), getValue(entry.getValue()));
            }
            propertiesMap.put(tag, properties);
        }
        startNodeInfo.put("properties", propertiesMap);
        return startNodeInfo;
    }

    private static Map<String, Object> convertRelationship(Relationship relationship) throws UnsupportedEncodingException {
        Map<String, Object> relationshipInfo = new HashMap<>();
        relationshipInfo.put("srcID", getId(relationship.srcId()));
        relationshipInfo.put("dstID", getId(relationship.dstId()));
        relationshipInfo.put("edgeName", relationship.edgeName());
        relationshipInfo.put("rank", relationship.ranking());
        final Map<String, Object> properties = new HashMap<>();
        for (Map.Entry<String, ValueWrapper> entry : relationship.properties().entrySet()) {
            properties.put(entry.getKey(), getValue(entry.getValue()));
        }
        relationshipInfo.put("properties", properties);
        return relationshipInfo;
    }



    private static List<Object> getListInfo(ValueWrapper valueWrapper,
                                    List<Map<String, Object>> verticesParsedList,
                                    List<Map<String, Object>> edgesParsedList,
                                    List<Map<String, Object>> pathsParsedList) throws InvalidValueException, UnsupportedEncodingException {
        if (verticesParsedList == null) {
            verticesParsedList = new ArrayList<>();
        }
        if (edgesParsedList == null) {
            edgesParsedList = new ArrayList<>();
        }
        if (pathsParsedList == null) {
            pathsParsedList = new ArrayList<>();
        }
        final List<ValueWrapper> valueList = valueWrapper.asList();
        List<Object> result = new ArrayList<>(valueList.size());
        int index = 0;
        for (ValueWrapper vv : valueList) {
            Map<String, Object> props = new HashMap<>();

            if (vv.isVertex()) {
                props = getVertexInfo(vv, props);
                props.put("type", "vertex");
                verticesParsedList.add(props);
                Lists.setOrAppend(result, index, props);
            } else if (vv.isEdge()) {
                props = getEdgeInfo(vv, props);
                props.put("type", "edge");
                edgesParsedList.add(props);
                Lists.setOrAppend(result, index, props);
            } else if (vv.isPath()) {
                props = getPathInfo(vv, props);
                props.put("type", "path");
                pathsParsedList.add(props);
                Lists.setOrAppend(result, index, props);
            } else if (vv.isList()) {
                List<Object> res = getListInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                Lists.setOrAppend(result, index, res);
            } else if (vv.isMap()) {
                Map<String, Object> map = getMapInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                Lists.setOrAppend(result, index, map);
            } else if (vv.isSet()) {
                Set<Object> set = getSetInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                Lists.setOrAppend(result, index, set);
            } else {
                // 基础数据类型
                final Object obj = getValue(vv);
                Lists.setOrAppend(result, index, obj);
            }
            index ++;
        }
        return result;
    }




    private static Set<Object> getSetInfo(ValueWrapper valueWrapper,
                                            List<Map<String, Object>> verticesParsedList,
                                            List<Map<String, Object>> edgesParsedList,
                                            List<Map<String, Object>> pathsParsedList) throws InvalidValueException, UnsupportedEncodingException {
        if (verticesParsedList == null) {
            verticesParsedList = new ArrayList<>();
        }
        if (edgesParsedList == null) {
            edgesParsedList = new ArrayList<>();
        }
        if (pathsParsedList == null) {
            pathsParsedList = new ArrayList<>();
        }
        final List<ValueWrapper> valueList = valueWrapper.asList();
        Set<Object> result = new HashSet<>(valueList.size());
        for (ValueWrapper vv : valueList) {
            Map<String, Object> props = new HashMap<>();

            if (vv.isVertex()) {
                props = getVertexInfo(vv, props);
                props.put("type", "vertex");
                verticesParsedList.add(props);
                result.add(props);
            } else if (vv.isEdge()) {
                props = getEdgeInfo(vv, props);
                props.put("type", "edge");
                edgesParsedList.add(props);
                result.add(props);
            } else if (vv.isPath()) {
                props = getPathInfo(vv, props);
                props.put("type", "path");
                pathsParsedList.add(props);
                result.add(props);
            } else if (vv.isList()) {
                List<Object> res  =  getListInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                result.add(res);
            } else if (vv.isMap()) {
                Map<String, Object> map = getMapInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                result.add(map);
            } else if (vv.isSet()) {
                Set<Object> set  = getSetInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                result.add(set);
            } else {
                Object obj = getValue(vv);
                result.add(obj);
            }
        }
        return result;
    }


    private static Map<String, Object> getMapInfo(ValueWrapper valueWrapper, List<Map<String, Object>> verticesParsedList,
                                   List<Map<String, Object>> edgesParsedList,
                                   List<Map<String, Object>> pathsParsedList) throws InvalidValueException, UnsupportedEncodingException {
        final HashMap<String, ValueWrapper> valueMap = valueWrapper.asMap();

        Map<String, Object> result = new HashMap<String, Object>();

        for (Map.Entry<String, ValueWrapper> entry : valueMap.entrySet()) {
            final ValueWrapper vv = entry.getValue();
            final String key = entry.getKey();

            Map<String, Object> props = new HashMap<>();
            if (vv.isVertex()) {
                props = getVertexInfo(vv, props);
                props.put("type", "vertex");
                verticesParsedList.add(props);
                result.put(key, props);
            } else if (vv.isEdge()) {
                props = getEdgeInfo(vv, props);
                props.put("type", "edge");
                edgesParsedList.add(props);
                result.put(key, props);
            } else if (vv.isPath()) {
                props = getPathInfo(vv, props);
                props.put("type", "path");
                pathsParsedList.add(props);
                result.put(key, props);
            } else if (vv.isList()) {
                List<Object> res = getListInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                result.put(key, res);
            } else if (vv.isSet()) {
                Set<Object> res = getSetInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                result.put(key, res);
            } else if (vv.isMap()) {
                Map<String, Object> map = getMapInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                result.put(key, map);
            } else {
                // 基础数据类型
                result.put(key, getValue(vv));
            }
        }
        return result;
    }

    @Override
    public GqlResult to(final ResultSet rs) throws InvalidValueException, UnsupportedEncodingException {
        if (rs == null) {
            return null;
        }
        if (!rs.isSucceeded()) {
            throw new NebulaExecException(rs.getErrorMessage());
        }
        final GqlResult result = new GqlResult(rs.getColumnNames());
        result.setTimeCost((int) rs.getLatency());
        if (rs.isEmpty()) {
            return result;
        }

        for (int i = 0; i < rs.rowsSize(); i++) {
            final ResultSet.Record record = rs.rowValues(i);
            final Map<String, Object> map = new HashMap<>();
            final List<Map<String, Object>> verticesParsedList = new ArrayList<>();
            final List<Map<String, Object>> edgesParsedList = new ArrayList<>();
            final List<Map<String, Object>> pathsParsedList = new ArrayList<>();
            int index = 0;
            for (final ValueWrapper vv : record.values()) {
                if (vv.isVertex()) {
                    Map<String, Object> parseValue = getVertexInfo(vv, new HashMap<>());
                    parseValue.put("type", "vertex");
                    verticesParsedList.add(parseValue);
                    map.put(result.getHeaders().get(index), parseValue);
                } else if (vv.isEdge()) {
                    Map<String, Object> parseValue = getEdgeInfo(vv, new HashMap<>());
                    map.put("type", "edge");
                    edgesParsedList.add(parseValue);
                    map.put(result.getHeaders().get(index), parseValue);
                } else if (vv.isPath()) {
                    Map<String, Object> parseValue = getPathInfo(vv, new HashMap<>());
                    map.put("type", "path");
                    pathsParsedList.add(parseValue);
                    map.put(result.getHeaders().get(index), parseValue);
                } else if (vv.isList()) {
                    List<Object> listParseValue = getListInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                    map.put(result.getHeaders().get(index), listParseValue);
                } else if (vv.isSet()) {
                    Set<Object> setParseValue =  getSetInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                    map.put(result.getHeaders().get(index), setParseValue);
                } else if (vv.isMap()) {
                    Map<String, Object> mapParseValue = getMapInfo(vv, verticesParsedList, edgesParsedList, pathsParsedList);
                    map.put(result.getHeaders().get(index), mapParseValue);
                } else {
                    Object res = getValue(vv);
                    map.put(result.getHeaders().get(index), res);
                }
                index++;
            }
            if (!verticesParsedList.isEmpty()) {
                map.put("_verticesParsedList", verticesParsedList);
            }
            if (!edgesParsedList.isEmpty()) {
                map.put("_edgesParsedList", edgesParsedList);
            }
            if (!pathsParsedList.isEmpty()) {
                map.put("_pathsParsedList", pathsParsedList);
            }
            result.addRow(map);
        }
        return result;
    }

    @Override
    public SceneGraph parsePathJson(String json) {
        final JSONObject jsonObject = JSON.parseObject(json);
        final JSONArray results = jsonObject.getJSONArray("results");
        final JSONObject result = results.getJSONObject(0);
        final String spaceName = result.getString("spaceName");
        final JSONArray dataList = result.getJSONArray("data");
        final Collection<BaseGraphModel> vertices = new HashSet<>();
        final Collection<BaseGraphModel> edges = new HashSet<>();
        for (int i = 0; i < dataList.size(); i++) {
            final JSONObject data = dataList.getJSONObject(i);
            // 不能单单仅获取下标0的数据，需遍历全部元数据列
            for (int metaIndex = 0; metaIndex < data.getJSONArray("meta").size(); metaIndex++) {
                final JSONArray metaList = data.getJSONArray("meta").getJSONArray(metaIndex);
                final JSONArray rows = data.getJSONArray("row").getJSONArray(metaIndex);
                for (int j = 0; j < metaList.size(); j++) {
                    final JSONObject meta = metaList.getJSONObject(j);
                    final JSONObject row = rows.getJSONObject(j);
                    final String vid = meta.getString("id");
                    parseNode(vertices, edges, meta, row, vid);
                }
            }
        }
        return new SceneGraph(spaceName, vertices, edges);
    }

    private static void parseNode(Collection<BaseGraphModel> vertices, Collection<BaseGraphModel> edges,
                                  JSONObject meta, JSONObject row, String vid) {
        Map<String, Object> record = row.getInnerMap();
        record.put("id", vid);
        if ("edge".equals(meta.getString("type"))) {
            edges.add(BaseGraphModel.builder()
                    .type(meta.getJSONObject("id").getString("name"))
                    .map(record)
                    .build());
        } else {
            String labelName = row.keySet().stream().findFirst().orElse("").split("\\.")[0];
            Map<String, Object> replace = new HashMap<>();
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                replace.put(entry.getKey().replace(labelName + ".", ""), entry.getValue());
            }
            if (vertices != null) {
                vertices.add(BaseGraphModel.builder()
                        .type(labelName)
                        .map(replace)
                        .build());
            }
        }
    }
}
