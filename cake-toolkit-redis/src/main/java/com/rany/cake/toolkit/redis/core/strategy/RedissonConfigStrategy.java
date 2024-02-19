package com.rany.cake.toolkit.redis.core.strategy;


import com.rany.cake.toolkit.redis.RedissonProperties;
import org.redisson.config.Config;

/**
 * Redisson配置构建接口
 *
 * @author zhongshengwang
 */
public interface RedissonConfigStrategy {


    /**
     * 根据不同的Redis配置策略创建对应的Config
     *
     * @param redissonProperties 配置信息
     * @return Config
     */
    Config createRedissonConfig(RedissonProperties redissonProperties);
}
