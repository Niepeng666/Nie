package com.main.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.common.com.util.ViewUtils;
import com.main.com.R;
import com.main.com.model.bean.Bb_Bean;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    private Context context;
        private List<Bb_Bean> list=new ArrayList<>();

    public MyAdapter(Context context, List<Bb_Bean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //(LayoutInflater.from(context).inflate(R.layout.shopping_cart_adapter,null,false)
        return new VH(LayoutInflater.from(context).inflate(R.layout.shopping_cart_adapter,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.VH holder, final int position) {
        holder.check_Box.setChecked(list.get(position).getCheck());
        holder.price.setText(list.get(position).getPrice()+"元");
        holder.name.setText(list.get(position).getName());
        holder.check_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.check_Box.isChecked();
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
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private TextView name,price;
        private CheckBox check_Box;
        public VH(@NonNull View itemView) {
            super(itemView);
            check_Box = itemView.findViewById(R.id.check_Box);
            name = itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
}
