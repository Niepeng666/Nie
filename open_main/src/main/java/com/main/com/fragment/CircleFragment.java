package com.main.com.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.common.com.core.WDFragment;
import com.common.com.util.ViewUtils;
import com.main.com.R;
import com.main.com.R2;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * @author 聂俊鹏
 * @date 2019/1/2 10:28
 * qq:2241659414
 */
public class CircleFragment extends WDFragment implements OnDateSelectedListener {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf22 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @BindView(R2.id.relat_layout)
    RelativeLayout relatLayout;
    @BindView(R2.id.mater_ial)
    MaterialCalendarView mater_ial;
    @BindView(R2.id.button01)
    Button button01;
    @BindView(R2.id.button02)
    Button button02;
    @BindView(R2.id.text_time)
    TextView text_time;
    private Calendar calendar;
    String today="";
    boolean flag=false;
    @Override
    public String getPageName() {
        return "**Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_circle;
    }

    @Override
    protected void initView() {

//set selected date



        relatLayout.setBackgroundColor(Color.parseColor("#13D1E2"));

        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
         final int month = calendar.get(Calendar.MONTH);
        mater_ial.setSelectedDate(calendar);
//        mater_ial.state().edit().setMinimumDate(calendar)
//                .commit();
//        mater_ial.state().edit().setMaximumDate(calendar)
//                .commit();

        if (flag){
            mater_ial.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        }else{

        }
        button01.setOnClickListener(new View.OnClickListener() {//设置日历变为多选日历
            @Override
            public void onClick(View view) {
                flag=true;
                text_time.setText(today);
                Log.e("TAG",calendar.toString());
//                 <!--multiple dates 多个日期-->

            }
        });

        button02.setOnClickListener(new View.OnClickListener() {//点击获取选中的时间 吐司...
            @Override
            public void onClick(View view) {
                // CalendarDay date = widget.getSelectedDate();
                CalendarDay selectedDate = mater_ial.getSelectedDate();
                if (selectedDate!=null){
                    flag=false;
                    Date time = calendar.getTime();
                    String format = sdf22.format(time);
                    ViewUtils.makeToast(context,calendar.get(Calendar.YEAR)+"年"+(month+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日"+"-------"+format,1500);
                    text_time.setText(calendar.get(Calendar.YEAR)+"年"+(month+1)+"月"+calendar.get(Calendar.DAY_OF_MONTH)+"日");
                }else{
                    ViewUtils.makeToast(context,"不好意思，没有获取到！",1500);
                    return;
                }

            }
        });

        mater_ial.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {




                String tag = getTag();
                String day = sdf.format(date.getDate());
                today=day;
                Log.e("TAG",date.toString());
                ViewUtils.makeToast(context,"日期:"+date.getYear() + "年"+(date.getMonth()+1)+ "月" + date.getDay() + "日",1500);
                text_time.setText(date.getYear() + "年"+(date.getMonth()+1) + "月" + date.getDay() + "日");
            }
        });


    }






    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


        ViewUtils.makeToast(getContext(),"走这个方法了",1500);
        String tag = getTag();
        String day = sdf.format(date.getDate());
        today=day;
//        tv_year.setText(date.getYear() + "年");
//        tv_monthAndDay.setText(date.getMonth() + 1 + "月" + date.getDay() + "日");
      //  tv_weekDay.setText("周" + numToChinese(date.getCalendar().get(Calendar.DAY_OF_WEEK)));
        ViewUtils.makeToast(context,"日期:"+date.getYear() + "年"+date.getMonth() + 1 + "月" + date.getDay() + "日",1500);
        text_time.setText(date.getYear() + "年"+date.getMonth() + 1 + "月" + date.getDay() + "日");
    }
}
