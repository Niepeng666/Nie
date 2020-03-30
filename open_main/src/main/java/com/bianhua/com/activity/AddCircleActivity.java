package com.bianhua.com.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bianhua.com.R;
import com.bianhua.com.core.WDActivity;
import com.bianhua.com.util.Constant;
/**
 * @author 聂俊鹏
 * @date
 * qq:2241659414
 */
@Route(path = Constant.ACTIVITY_URL_ADD_CIRCLE)
public class AddCircleActivity extends WDActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_circle;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }



}
