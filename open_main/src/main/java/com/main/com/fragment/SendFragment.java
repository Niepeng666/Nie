package com.main.com.fragment;



import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.common.com.core.WDFragment;
import com.common.com.util.ViewUtils;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.adapter.MyAdapter;
import com.main.com.model.bean.Bb_Bean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class SendFragment extends WDFragment {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.swipe)
    SwipeRefreshLayout swipe;
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
       Bb_Bean bb_bean=new Bb_Bean();
        bb_bean.setId(0);
        bb_bean.setPrice(9.90);
        bb_bean.setName("花里胡哨");
        bb_bean.setCheck(false);
        Bb_Bean bb_bean2=new Bb_Bean();
        bb_bean2.setId(1);
        bb_bean2.setPrice(8.80);
        bb_bean2.setName("zezeze");
        bb_bean2.setCheck(false);
        Bb_Bean bb_bean3=new Bb_Bean();
        bb_bean3.setId(2);
        bb_bean3.setPrice(15.50);
        bb_bean3.setName("啧啧啧");
        bb_bean3.setCheck(false);
        final List<Bb_Bean> list=new ArrayList<>();
            list.add(bb_bean);
            list.add(bb_bean2);
            list.add(bb_bean3);
        final MyAdapter myAdapter=new MyAdapter(context,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


        //设置下拉时圆圈的颜色（可以由多种颜色拼成）
        swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        //设置下拉时圆圈的背景颜色（这里设置成白色）
        swipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置下拉刷新时的操作
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                ViewUtils.makeToast(context,"刷新成功！",1500);
                list.clear();
                myAdapter.notifyDataSetChanged();
            }
        });




    }


}
