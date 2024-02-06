package com.rany.cake.toolkit.lang.exception.argument;


import com.rany.cake.toolkit.lang.wrapper.HttpWrapper;

/**
 * HttpWrapper 的 exception
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */

public class HttpWrapperException extends WrapperException {

    public HttpWrapperException(HttpWrapper<?> wrapper) {
        super(wrapper);
    }

    public HttpWrapperException(HttpWrapper<?> wrapper, String message) {
        super(wrapper, message);
    }

    public HttpWrapperException(HttpWrapper<?> wrapper, String message, Throwable cause) {
        super(wrapper, message, cause);
    }

    public HttpWrapperException(HttpWrapper<?> wrapper, Throwable cause) {
        super(wrapper, cause);
    }

    @Override
    @SuppressWarnings("unchecked")
    public HttpWrapper<?> getWrapper() {
        return super.getWrapper();
    }

}
