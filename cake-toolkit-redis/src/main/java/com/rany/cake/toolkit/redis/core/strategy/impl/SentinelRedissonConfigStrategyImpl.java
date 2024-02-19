package com.rany.cake.toolkit.redis.core.strategy.impl;

import com.rany.cake.toolkit.lang.constant.Letters;
import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.redis.GlobalConstant;
import com.rany.cake.toolkit.redis.RedissonProperties;
import com.rany.cake.toolkit.redis.core.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 哨兵方式Redis连接配置
 * 比如sentinel.conf里配置为sentinel monitor my-sentinel-name 127.0.0.1 6379 2,那么这里就配置my-sentinel-name
 * 配置方式:my-sentinel-name,127.0.0.1:26379,127.0.0.1:26389,127.0.0.1:26399
 *
 * @author zhongshengwang
 */
public class SentinelRedissonConfigStrategyImpl implements RedissonConfigStrategy {


    private final static Logger logger = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(String.valueOf(Letters.COMMA));
            String sentinelAliasName = addrTokens[0];
            // 设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (Strings.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            // 设置哨兵节点的服务IP和端口
            for (int i = 1; i < addrTokens.length; i++) {
                config.useSentinelServers().addSentinelAddress(GlobalConstant.REDIS_CONNECTION_PREFIX + addrTokens[i]);
            }
            logger.info("初始化哨兵方式Config,redisAddress:" + address);
        } catch (Exception e) {
            logger.error("哨兵Redisson初始化错误", e);
        }
        return config;
    }
}
