package com.rany.cake.toolkit.lang.wrapper;


import com.rany.cake.toolkit.lang.reflect.BeanWrapper;

import java.util.Map;

/**
 * Map 转化接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2019/8/19 20:03
 */
public interface IMapObject<K, V> {

    /**
     * 转为 map
     *
     * @return map
     */
    Map<K, V> toMap();

    /**
     * 转为 map
     *
     * @return map
     */
    default Map<String, Object> asMap() {
        return BeanWrapper.toMap(this);
    }

}
