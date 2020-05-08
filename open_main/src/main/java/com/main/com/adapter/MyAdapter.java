package com.main.com.adapter;

import android.widget.CheckBox;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.main.com.R;
import com.main.com.model.bean.Bb_Bean;

public class MyAdapter extends BaseQuickAdapter<Bb_Bean, BaseViewHolder> {


    private CheckBox check_Box;

    public MyAdapter() {
        super(R.layout.shopping_cart_adapter);
    }
    @Override
    protected void convert(BaseViewHolder helper, final Bb_Bean item) {
        //赋值
        helper.setChecked(R.id.check_Box,item.getCheck())
                .setText(R.id.name,item.getName())
                .setText(R.id.price,item.getPrice()+"")
                .addOnClickListener(R.id.check_Box)
                .addOnClickListener(R.id.linear_layout);


    }
}
