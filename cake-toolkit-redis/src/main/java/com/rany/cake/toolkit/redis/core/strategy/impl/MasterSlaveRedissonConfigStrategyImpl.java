package com.rany.cake.toolkit.redis.core.strategy.impl;


import com.rany.cake.toolkit.lang.constant.Letters;
import com.rany.cake.toolkit.lang.utils.Lists;
import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.redis.GlobalConstant;
import com.rany.cake.toolkit.redis.RedissonProperties;
import com.rany.cake.toolkit.redis.core.strategy.RedissonConfigStrategy;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 主从方式Redisson配置
 * <p>配置方式: 127.0.0.1:6379(主),127.0.0.1:6380(子),127.0.0.1:6381(子)</p>
 *
 * @author zhongshengwang
 */
public class MasterSlaveRedissonConfigStrategyImpl implements RedissonConfigStrategy {


    private final static Logger logger = LoggerFactory.getLogger(ClusterRedissonConfigStrategyImpl.class);

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(String.valueOf(Letters.COMMA));
            String masterNodeAddr = addrTokens[0];
            // 设置主节点ip
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (Strings.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            config.useMasterSlaveServers().setDatabase(database);
            // 设置从节点，移除第一个节点，默认第一个为主节点
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add(GlobalConstant.REDIS_CONNECTION_PREFIX + addrToken);
            }
            slaveList.remove(0);
            if (Lists.isNotEmpty(slaveList)) {
                String[] array = slaveList.toArray(new String[0]);
                config.useMasterSlaveServers().addSlaveAddress(array);
            }
            logger.info("初始化主从方式Config,redisAddress:" + address);
        } catch (Exception e) {
            logger.error("主从Redisson初始化错误", e);
        }
        return config;
    }
}
