package com.main.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.com.core.WDFragment;
import com.common.com.util.ChooseDialog;
import com.common.com.util.StorageCustomerInfo02Util;
import com.common.com.util.ViewUtils;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.adapter.MyAdapter;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShareFragment extends WDFragment {
    @BindView(R2.id.relat_layout)
    RelativeLayout relatLayout;
    @BindView(R2.id.text)
    TextView text;
    @BindView(R2.id.colorView)
    ColorPickerView colorView;
    @BindView(R2.id.text_fenxiang)
    TextView text_fenxiang;
    private PopupWindow popupWindow;
    private RecyclerView recycler;
    List list=new ArrayList();
    private TextView text_zidingyi;

    @Override
    public String getPageName() {
        return "***Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_share;
    }

    @Override
    protected void initView() {
        relatLayout.setBackgroundColor(Color.parseColor("#C69AEC52"));

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popwindon();
            }
        });
        //取色盘控件
        colorView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                //给控件填充背景色
                text.setBackgroundColor(colorEnvelope.getColor());
                //隐藏取色盘
                colorView.setVisibility(View.GONE);
            }
        });
        //分享
        text_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow01();
            }
        });


    }

    private void popupWindow01() {
        ViewUtils.makeToast(context,"弹框了！",500);
        new ChooseDialog(context,"微信分享","QQ分享").setOnChooseDialogListener(new ChooseDialog.OnChooseDialogListener() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onSelect0() {//WX
                ViewUtils.makeToast(context,"微信",500);
            }

            @Override
            public void onSelect1() {//QQ
                ViewUtils.makeToast(context,"QQ",500);
            }
        }).show();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            colorView.setVisibility(View.GONE);
        }
    }

    private void popwindon() {
        //及时清空list，要不然会数据重复
        list.clear();
        View  layout =this.getLayoutInflater().inflate(R.layout.pop_layout,null);
        layout.setBackgroundColor(Color.GRAY);
        TextView tv = new TextView(context);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tv.setTextColor(Color.WHITE);
        popupWindow = new PopupWindow(layout,200,LinearLayout.LayoutParams.WRAP_CONTENT);
          recycler =layout.findViewById(R.id.recyclerView);
        text_zidingyi = (TextView)layout.findViewById(R.id.text_zidingyi);
          list.add("红");
          list.add("橙");
          list.add("黄");
          list.add("绿");
          list.add("青");
          list.add("蓝");
          list.add("紫");
         //取色盘
          text_zidingyi.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  popupWindow.dismiss();
                  ViewUtils.makeToast(context,"打开自定义取色盘",1500);
                  colorView.setVisibility(View.VISIBLE);
              }
          });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        recycler.setLayoutManager(linearLayoutManager);
        MyAdapter2 myAdapter2=new MyAdapter2(context,list);
        recycler.setAdapter(myAdapter2);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        text.getLocationOnScreen(location);

        popupWindow.showAsDropDown(text);

    }

    public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.VH> {
        private TextView name;
        private Context context;
            private List list=new ArrayList();

        public MyAdapter2(Context context, List list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VH(View.inflate(context,R.layout.pop_item,null));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, final int position) {
            name.setText(list.get(position)+"");
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    text.setText(list.get(position)+"");
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public class VH extends RecyclerView.ViewHolder {
            public VH(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
            }
        }
    }

}
