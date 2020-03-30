package com.bianhua.com.fragment;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bianhua.com.R;
import com.bianhua.com.R2;
import com.bianhua.com.activity.MainActivity;
import com.bianhua.com.core.WDFragment;


import butterknife.BindView;

/**
 * @author 聂俊鹏
 * @date 个人页面
 * qq:2241659414
 */
public class MeFragment extends WDFragment {
    @BindView(R2.id.text)
    TextView text;



    @Override
    public String getPageName() {
        return "我的Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_me;
    }

    @Override
    protected void initView() {

        initData();
    }

    private void initData() {

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.finish();


            }
        });
    }


}
