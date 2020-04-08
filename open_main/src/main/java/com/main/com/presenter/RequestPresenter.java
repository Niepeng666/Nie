package com.main.com.presenter;

import com.common.com.core.DataCall;
import com.common.com.core.WDPresenter;
import com.common.com.core.http.IAppRequest;

import io.reactivex.Observable;

/**
 * @author 聂俊鹏
 * @date 2018/12/28 11:23
 * qq:2241659414
 */
public class RequestPresenter extends WDPresenter<IAppRequest> {

    public RequestPresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.register((String)args[0],(String)args[1]);
    }


}