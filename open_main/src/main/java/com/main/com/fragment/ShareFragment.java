package com.main.com.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.common.com.base.WDFragment;
import com.common.com.util.ChooseDialog;
import com.common.com.util.tusi.ViewUtils;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.activity.MainActivity;
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
    @BindView(R2.id.text_fenxiang)
    TextView text_fenxiang;
    private PopupWindow popupWindow;
    private RecyclerView recycler;
    List list=new ArrayList();
    private TextView text_zidingyi;
    private ColorPickerView colorView;
    private  int haha;
    private AlertDialog dialog;
    @BindView(R2.id.text_new_tiao)
    TextView text_new_tiao;
    @BindView(R2.id.long_text)
    TextView long_text;
    @BindView(R2.id.change_text)
    TextView change_text;
    @BindView(R2.id.text_num)
    TextView text_num;
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
        //分享
        text_fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow01();
            }
        });

        //新式跳转
        text_new_tiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).skip();
            }
        });
        //收起和展示文章

        change_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Layout layout = long_text.getLayout();
                int lineCount = layout.getLineCount();
                if (lineCount<=3 && "更多".equals(change_text.getText().toString().trim())){
                    long_text.setMaxLines(666);
                    change_text.setText("收起");
                }else{
                    long_text.setMaxLines(3);
                    change_text.setText("更多");
                }
            }
        });

        //
        text_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            fenxiang();
            }
        });


    }

    private void fenxiang() {

    }

    private void dialog_color() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_qusepan, null);
         dialog = new AlertDialog.Builder(context)
                .setView(view)
                .show();
        view.findViewById(R.id.text_quxiao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setBackgroundColor(Color.parseColor("#ffffff"));
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.text_queding).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setBackgroundColor(haha);
             dialog.dismiss();
            }
        });
        colorView = (ColorPickerView)view.findViewById(R.id.colorView);
        colorView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                //给控件填充背景色
                haha=colorEnvelope.getColor();

            }
        });

    }

    private void popupWindow01() {
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
                  dialog_color();
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

                    if (0==position){
                        text.setTextColor(Color.parseColor("#ff0000"));
                    }else if (1==position){
                        text.setTextColor(Color.parseColor("#FF3300"));
                    }else if (2==position){
                        text.setTextColor(Color.parseColor("#FFFF33"));
                    }else if (3==position){
                        text.setTextColor(Color.parseColor("#66FF00"));
                    }else if (4==position){
                        text.setTextColor(Color.parseColor("#33FF99"));
                    }else if (5==position){
                        text.setTextColor(Color.parseColor("#00FFFF"));
                    }else if (6==position){
                        text.setTextColor(Color.parseColor("#6600CC"));
                    }else {
                        text.setTextColor(Color.parseColor("#000000"));
                    }
                    popupWindow.dismiss();
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
