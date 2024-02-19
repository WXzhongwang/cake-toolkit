package com.rany.cake.toolkit.redis.core.strategy.impl;

import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.redis.GlobalConstant;
import com.rany.cake.toolkit.redis.RedissonProperties;
import com.rany.cake.toolkit.redis.core.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 单机方式Redisson配置
 *
 * @author zhongshengwang
 */
public class StandaloneRedissonConfigStrategyImpl implements RedissonConfigStrategy {
    private final static Logger logger = LoggerFactory.getLogger(StandaloneRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String redisAddr = GlobalConstant.REDIS_CONNECTION_PREFIX + address;
            config.useSingleServer().setAddress(redisAddr);
            config.useSingleServer().setDatabase(database);
            if (Strings.isNotBlank(password)) {
                config.useSingleServer().setPassword(password);
            }
            logger.info("初始化Redisson单机配置,连接地址:" + address);
        } catch (Exception e) {
            logger.error("单机Redisson初始化错误", e);
        }
        return config;
    }
}
