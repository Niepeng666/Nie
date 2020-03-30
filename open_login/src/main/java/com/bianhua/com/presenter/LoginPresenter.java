package com.bianhua.com.presenter;

import com.bianhua.com.core.DataCall;
import com.bianhua.com.core.WDPresenter;
import com.bianhua.com.core.http.IAppRequest;

import io.reactivex.Observable;

/**
 * @author bianhua
 * @date 2018/12/28 11:23
 * qq:2241659414
 */
public class LoginPresenter extends WDPresenter<IAppRequest> {

    public LoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.login((String)args[0],(String)args[1]);
    }


}
