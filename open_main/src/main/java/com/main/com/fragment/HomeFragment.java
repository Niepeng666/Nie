package com.main.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.activity.WebViewActivity;
import com.common.com.core.WDFragment;
import com.main.com.model.SendMessageCommunitor;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author 聂俊鹏
 * @date 首页
 * qq:2241659414
 */
public class HomeFragment extends WDFragment {
    @BindView(R2.id.text)
    TextView text;
    @BindView(R2.id.recyclerView)
     RecyclerView recyclerView;
    @BindView(R2.id.text_url)
    TextView textUrl;
    @BindView(R2.id.flybanner)
   FlyBanner flyBanner;
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
        //沉浸式状态栏
        ImmersionBar.with(this).barAlpha(0.1f).init();

      ArrayList<Integer> listBanner=new ArrayList();
        listBanner.add(R.drawable.abc01);
        listBanner.add(R.drawable.abc02);

        flyBanner.setImages(listBanner);

        initData();//点击事件
    }

    private void initData() {

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage.sendMessage("mainselecet1");

            }
        });

        textUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WebViewActivity.class)
                        .putExtra("title","我的保单")
                        .putExtra("url","http://jucaibao.llyzf.cn:6442/lly-posp-proxy/jcb/helpCenter.html"));
            }
        });



        //banner的点击事件
        flyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG",hidden+"");
        if (!hidden){
            Log.e("TAG","走方法了!");
        }
    }
}
