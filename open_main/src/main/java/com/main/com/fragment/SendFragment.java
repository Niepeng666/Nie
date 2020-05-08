package com.main.com.fragment;

import android.view.View;
import android.widget.CheckBox;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.com.base.WDFragment;
import com.common.com.util.tusi.ViewUtils;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.adapter.MyAdapter;
import com.main.com.model.bean.Bb_Bean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class SendFragment extends WDFragment {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.smart_layout)
    SmartRefreshLayout smart_layout;
    private int page=1;
    private MyAdapter myAdapter;
    private List<Bb_Bean> list;
    private CheckBox check_Box;
    private boolean zhia=false;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_vip;
    }

    @Override
    protected void initView() {
        //下拉刷新
        smart_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                smart_layout.finishRefresh(1000);
                page=1;
                initData(page);

            }
        });
        //上拉加载
        smart_layout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                smart_layout.finishLoadmore(1000);
                initData(page++);
            }
        });

        initData(page);


    }

    private void initData(int page) {
        Bb_Bean bb_bean = new Bb_Bean();
        bb_bean.setId(0);
        bb_bean.setPrice(9.90);
        bb_bean.setName("花里胡哨");
        bb_bean.setCheck(zhia);
        Bb_Bean bb_bean2 = new Bb_Bean();
        bb_bean2.setId(1);
        bb_bean2.setPrice(8.80);
        bb_bean2.setName("zezeze");
        bb_bean2.setCheck(zhia);
        Bb_Bean bb_bean3 = new Bb_Bean();
        bb_bean3.setId(2);
        bb_bean3.setPrice(15.50);
        bb_bean3.setName("啧啧啧");
        bb_bean3.setCheck(zhia);
        list = new ArrayList<>();
        for (int i = 0; i <= page; i++) {
            list.add(bb_bean);
            list.add(bb_bean2);
            list.add(bb_bean3);
        }
        myAdapter = new MyAdapter();
        myAdapter.setNewData(list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        myAdapter.bindToRecyclerView(recyclerView);
        myAdapter.setEmptyView(R.layout.layout_empty, smart_layout);
        intitem();

    }






    private void intitem() {
        myAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bb_Bean bb_bean1 = list.get(position);
                if (view.getId() == R.id.check_Box) {
                    check_Box =(CheckBox) view.findViewById(R.id.check_Box);
                    boolean checked =check_Box .isChecked();
                        if (checked){
                            zhia=true;
                        }else{
                            zhia=false;
                        }
                    ViewUtils.makeToast(context,checked+":状态值",500);
                    list.get(position).setCheck(checked);

                    int jj=0;
                    for (int i=0;i<list.size();i++){
                        if (list.get(i).getCheck()){
                            jj+=1;
                        }
                    }
                    if (jj==list.size()){
                        ViewUtils.makeToast(context,"全选了！",1500);

                    }

                }
            }
        });
        myAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                smart_layout.autoRefresh();
            }
        });


    }


}
