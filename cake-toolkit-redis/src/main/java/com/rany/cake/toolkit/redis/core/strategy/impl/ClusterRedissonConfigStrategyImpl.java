package com.rany.cake.toolkit.redis.core.strategy.impl;

import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.redis.GlobalConstant;
import com.rany.cake.toolkit.redis.RedissonProperties;
import com.rany.cake.toolkit.redis.core.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 集群方式Redisson配置
 * cluster方式至少6个节点(3主3从)
 * 配置方式:127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384
 *
 * @author zhongshengwang
 */
public class ClusterRedissonConfigStrategyImpl implements RedissonConfigStrategy {

    private final static Logger logger = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            // 设置集群(cluster)节点的服务IP和端口
            for (String addrToken : addrTokens) {
                config.useClusterServers().addNodeAddress(GlobalConstant.REDIS_CONNECTION_PREFIX + addrToken);
                if (Strings.isNotBlank(password)) {
                    config.useClusterServers().setPassword(password);
                }
            }
            logger.info("初始化集群方式Config,连接地址:" + address);
        } catch (Exception e) {
            logger.error("集群Redisson初始化错误", e);
        }
        return config;
    }
}
