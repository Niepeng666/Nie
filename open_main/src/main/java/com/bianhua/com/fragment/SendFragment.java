package com.bianhua.com.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bianhua.com.R;
import com.bianhua.com.R2;
import com.bianhua.com.core.WDFragment;
import com.bianhua.com.model.text;
import com.bianhua.com.util.ViewUtils;

import butterknife.BindView;


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
