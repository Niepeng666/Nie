package com.common.com.http;


import com.common.com.exception.ApiException;

/**
 * @author bianhua`
 * @date 2018/12/30 10:30
 * qq:2241659414
 */
public interface DataCall<T> {

    void success(T data,Object...args);
    void fail(ApiException data, Object...args);


}
