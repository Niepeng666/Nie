package com.main.com.activity;

import android.widget.TextView;
import android.widget.Toast;

import com.common.com.choujiang.LuckyView;
import com.common.com.core.WDActivity;
import com.main.com.R;
import com.main.com.R2;

import butterknife.BindView;

public class ChoujiangActivity extends WDActivity {
    @BindView(R2.id.luckView)
    LuckyView luckView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choujiang;
    }

    @Override
    protected void initView() {

        luckView.setLuckAnimationEndListener(new LuckyView.OnLuckAnimationEndListener() {
            @Override
            public void onLuckAnimationEnd(int pos, String msg) {
                //打印抽奖结果
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void destoryData() {

    }
}
