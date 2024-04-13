package com.rany.cake.toolkit.lang.wrapper;

import java.io.Serializable;

/**
 * wrapper code & message
 * <p>
 * 可以使用枚举对象定义
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public interface CodeInfo extends Serializable {

    /**
     * 获取code
     *
     * @return code
     */
    String code();

    /**
     * 获取message
     *
     * @return message
     */
    String message();

    /**
     * 转为 httpWrapper
     *
     * @param <T> T
     * @return HttpWrapper
     * @see HttpWrapper
     */
    default <T> HttpWrapper<T> toHttpWrapper() {
        return HttpWrapper.of(this);
    }

    /**
     * 转为 rpcWrapper
     *
     * @param <T> T
     * @return RpcWrapper
     * @see RpcWrapper
     */
    default <T> RpcWrapper<T> toRpcWrapper() {
        return RpcWrapper.of(this);
    }

}
