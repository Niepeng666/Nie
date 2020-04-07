package com.main.com.fragment;

import android.widget.TextView;

import com.common.com.core.WDFragment;
import com.main.com.R;


public class SendFragment extends WDFragment {

    private TextView text_dianji;

    @Override
    public String getPageName() {
        return "***Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_vip;
    }

    @Override
    protected void initView() {


        initData();

    }

    private void initData() {

    }
}
