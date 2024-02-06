package com.rany.cake.toolkit.lang.exception.argument;


import com.rany.cake.toolkit.lang.utils.Valid;
import com.rany.cake.toolkit.lang.wrapper.Wrapper;

/**
 * Wrapper 的 exception
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class WrapperException extends InvalidArgumentException {

    private final Wrapper<?> wrapper;

    public WrapperException(Wrapper<?> wrapper) {
        Valid.notNull(wrapper, "wrapper is null");
        this.wrapper = wrapper;
    }

    public WrapperException(Wrapper<?> wrapper, String message) {
        super(message);
        Valid.notNull(wrapper, "wrapper is null");
        this.wrapper = wrapper;
    }

    public WrapperException(Wrapper<?> wrapper, String message, Throwable cause) {
        super(message, cause);
        Valid.notNull(wrapper, "wrapper is null");
        this.wrapper = wrapper;
    }

    public WrapperException(Wrapper<?> wrapper, Throwable cause) {
        super(cause);
        Valid.notNull(wrapper, "wrapper is null");
        this.wrapper = wrapper;
    }

    @SuppressWarnings("unchecked")
    public <T extends Wrapper<?>> T getWrapper() {
        return (T) wrapper;
    }

}
