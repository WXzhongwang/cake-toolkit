package com.rany.cake.toolkit.redis;

/**
 * Redisson配置映射类
 *
 * @author zhongshengwang
 */
public class RedissonProperties {

    /**
     * redis主机地址，ip：port，多个用逗号(,)分隔
     */
    private String address;
    /**
     * 连接类型
     */
    private RedisConnectionType type;
    /**
     * 密码
     */
    private String password;
    /**
     * 数据库(默认0)
     */
    private int database;

    /**
     * 是否装配redisson配置
     */
    private Boolean enabled = true;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RedisConnectionType getType() {
        return type;
    }

    public void setType(RedisConnectionType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
