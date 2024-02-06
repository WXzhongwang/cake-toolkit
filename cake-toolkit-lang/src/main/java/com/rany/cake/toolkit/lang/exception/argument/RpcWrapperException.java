package com.rany.cake.toolkit.lang.exception.argument;


import com.rany.cake.toolkit.lang.wrapper.RpcWrapper;

/**
 * RpcWrapper 的 exception
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class RpcWrapperException extends WrapperException {

    public RpcWrapperException(RpcWrapper<?> wrapper) {
        super(wrapper);
    }

    public RpcWrapperException(RpcWrapper<?> wrapper, String message) {
        super(wrapper, message);
    }

    public RpcWrapperException(RpcWrapper<?> wrapper, String message, Throwable cause) {
        super(wrapper, message, cause);
    }

    public RpcWrapperException(RpcWrapper<?> wrapper, Throwable cause) {
        super(wrapper, cause);
    }

    @Override
    @SuppressWarnings("unchecked")
    public RpcWrapper<?> getWrapper() {
        return super.getWrapper();
    }

}
