package com.rany.cake.toolkit.nebula.common;


public interface ResponseService {

    /**
     * 获取响应码
     *
     * @return 返回码
     */
    String getResponseCode();

    /**
     * 获取响应信息
     *
     * @return 返回信息
     */
    String getResponseMessage();
}
