package com.bianhua.com.presenter;

import com.bianhua.com.core.DataCall;
import com.bianhua.com.core.WDPresenter;
import com.bianhua.com.core.http.IAppRequest;

import io.reactivex.Observable;

/**
 * @author 聂俊鹏
 * @date 2018/12/28 11:23
 * qq:2241659414
 */
public class CirclePresenter extends WDPresenter<IAppRequest> {

    private int page=1;

    public int getPage() {
        return page;
    }

    public CirclePresenter(DataCall consumer) {
        super(consumer);
    }

    @Override
    protected Observable getModel(Object... args) {
        boolean refresh = (boolean)args[0];
        if (refresh){
            page = 1;
        }else{
            page++;
        }
        return iRequest.findCircleList((long)args[1],(String)args[2],page,20);
    }


}
