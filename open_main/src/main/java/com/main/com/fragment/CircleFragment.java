package com.main.com.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.common.com.base.WDFragment;
import com.common.com.util.riqi.DateSelectPopupWindow;
import com.main.com.R;
import com.main.com.R2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * @author 聂俊鹏
 * @date 2019/1/2 10:28
 * qq:2241659414
 */
public class CircleFragment extends WDFragment  {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf22 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @BindView(R2.id.relat_layout)
    RelativeLayout relatLayout;

    @BindView(R2.id.button01)
    Button button01;
    @BindView(R2.id.button02)
    Button button02;
    @BindView(R2.id.text_time)
    TextView text_time;
    private Calendar calendar;
    String today="";
    final int i=2;
    private DateSelectPopupWindow dateSelectPopupWindow;
    private List<String> mSelectDates;

    @Override
    protected int getLayoutId() {
        return R.layout.frag_circle;
    }

    @Override
    protected void initView() {
        relatLayout.setBackgroundColor(Color.parseColor("#13D1E2"));//设置选中日期的背景色
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        final int month = calendar.get(Calendar.MONTH);


        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_time.setText(today);
                Log.e("TAG", calendar.toString());
            }
        });
        button02.setOnClickListener(new View.OnClickListener() {//点击获取选中的时间 吐司...
            @Override
            public void onClick(View view) {
                showSelectDatePopupWindow();
            }
        });


    }

    private void showSelectDatePopupWindow() {
        dateSelectPopupWindow = new DateSelectPopupWindow((Activity) context, new DateSelectPopupWindow.DialogDateSelecteListener() {
            @Override
            public void onSelectedDone(List<String> selectDates) {
                dateSelectPopupWindow.dismiss();
                mSelectDates = selectDates;
                text_time.setText(selectDates.toString().replaceAll("\\[","")
                        .replaceAll("\\]",""));
            }
        });

        //显示窗口
        dateSelectPopupWindow.showAtLocation(text_time, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        backgroundAlpha(0.5f);
        dateSelectPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = bgAlpha; //0.0-1.0
//        getWindow().setAttributes(lp);

    }

}
