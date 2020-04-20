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
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
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
    final int i=2;
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
        relatLayout.setBackgroundColor(Color.parseColor("#13D1E2"));//设置选中日期的背景色
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
         final int month = calendar.get(Calendar.MONTH);
         mater_ial.setSelectedDate(calendar);
         mater_ial.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);//设置日历为多选日历

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_time.setText(today);
                Log.e("TAG",calendar.toString());
            }
        });
        button02.setOnClickListener(new View.OnClickListener() {//点击获取选中的时间 吐司...
            @Override
            public void onClick(View view) {
                CalendarDay selectedDate = mater_ial.getSelectedDate();
                if (selectedDate!=null){

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
                ViewUtils.makeToast(context,"日期:"+date.getYear() + "年"+(date.getMonth()+1)+ "月" + date.getDay() + "日",1500);
                text_time.setText(date.getYear() + "年"+(date.getMonth()+1) + "月" + date.getDay() + "日");
            }
        });
        mater_ial.addDecorator(new PrimeDayDisableDecorator());

    }
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


        ViewUtils.makeToast(getContext(),"走这个方法了",1500);
        String tag = getTag();
        String day = sdf.format(date.getDate());
        today=day;

        ViewUtils.makeToast(context,"日期:"+date.getYear() + "年"+date.getMonth() + 1 + "月" + date.getDay() + "日",1500);
        text_time.setText(date.getYear() + "年"+date.getMonth() + 1 + "月" + date.getDay() + "日");
    }
    private static class PrimeDayDisableDecorator implements DayViewDecorator {



        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.getCalendar().compareTo(Calendar.getInstance()) == -1;
            //这里return的是一个boolean类型的数据，
            // 如果直接返回为true，下面的decorate()会不好使，没有日期禁用效果
            // false，会让日历上的所有日期都无法选中，
            // 只能day.getCalendar().compareTo(Calendar.getInstance()) == -1可以让当前系统日期，之前的时间不能被选中，之后的可以被选中。


        }

        @Override
        public void decorate(DayViewFacade view) {
                view.setDaysDisabled(true);     //日期禁用
        }


    }


}
