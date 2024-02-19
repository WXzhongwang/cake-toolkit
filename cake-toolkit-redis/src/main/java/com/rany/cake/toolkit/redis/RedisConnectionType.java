package com.rany.cake.toolkit.redis;


/**
 * Redis连接方式
 *
 * @author zhongshengwang
 */
public enum RedisConnectionType {
    /**
     * 单机部署方式(默认)
     */
    STANDALONE("standalone", "单机部署方式"),
    /**
     * 哨兵部署方式
     */
    SENTINEL("sentinel", "哨兵部署方式"),
    /**
     * 集群部署方式
     */
    CLUSTER("cluster", "集群方式"),
    /**
     * 主从部署方式
     */
    MS("master-slave", "主从部署方式");

    /**
     * 编码
     */
    private final String code;
    /**
     * 名称
     */
    private final String name;

    RedisConnectionType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
