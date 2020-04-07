package com.main.com.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.core.WDActivity;
import com.common.com.util.Constant;
import com.main.com.R;

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
