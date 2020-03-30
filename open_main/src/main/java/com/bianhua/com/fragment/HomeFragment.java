package com.bianhua.com.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bianhua.com.R;
import com.bianhua.com.R2;
import com.bianhua.com.core.WDFragment;
import com.bianhua.com.model.SendMessageCommunitor;

import butterknife.BindView;

/**
 * @author 聂俊鹏
 * @date
 * qq:2241659414
 */
public class HomeFragment extends WDFragment {
    @BindView(R2.id.text)
    TextView text;
    @BindView(R2.id.recyclerView)
     RecyclerView recyclerView;

    SendMessageCommunitor sendMessage;


    @Override
    public String getPageName() {
        return "首页Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    protected void initView() {
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage.sendMessage("mainselecet1");
                
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sendMessage = (SendMessageCommunitor) activity;
    }

}