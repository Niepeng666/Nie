package com.common.com.util.riqi;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.com.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;


public class DateSelectPopupWindow extends PopupWindow implements
        CalendarView.OnCalendarInterceptListener,
        CalendarView.OnYearChangeListener,
        CalendarView.OnMonthChangeListener,
        View.OnClickListener,
        CalendarView.OnCalendarSelectListener {
    RadioButton rb1,rb2;
    TextView mTextMonthDay;

    TextView mTextYear;

    TextView mTextLunar;

    TextView mTextCurrentDay;

    CalendarView mCalendarView;

    RelativeLayout mRelativeTool;
    private int mYear;
    private int mCalendarHeight;

    //CustomMultiMonthView customMultiMonthView;
    List<String> SelectDate = new ArrayList<>();
    private View mMenuView;
    private final DialogDateSelecteListener listener;


    public DateSelectPopupWindow(Activity context, DialogDateSelecteListener listener) {
        super(context);
        this.listener = listener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.date_select_dialog, null);
        initView();

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
       // this.setAnimationStyle(R.style.DropDownUp);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(-00000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.calendarView).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
    @SuppressLint("SetTextI18n")
    protected void initView() {
//        setStatusBarDarkMode();
        rb1 = mMenuView.findViewById(R.id.rb1);
        rb2 = mMenuView.findViewById(R.id.rb2);
        mTextMonthDay = mMenuView.findViewById(R.id.tv_month_day);
        mTextYear =  mMenuView.findViewById(R.id.tv_year);
        mTextLunar =  mMenuView.findViewById(R.id.tv_lunar);
        mRelativeTool =  mMenuView.findViewById(R.id.rl_tool);
        mCalendarView =  mMenuView.findViewById(R.id.calendarView);
        mTextCurrentDay =  mMenuView.findViewById(R.id.tv_current_day);



        mMenuView.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMulti();
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRange();
            }
        });


       initMulti();
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
        mMenuView.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate.clear();
                List<Calendar> calendar;
                if (rb1.isChecked()){//间隔
                    calendar = mCalendarView.getMultiSelectCalendars();//自动排序
                }else {//非间隔
                    calendar = mCalendarView.getSelectCalendarRange();
                }

                if (calendar.size() > 0) {
                    for (int i = 0; i < calendar.size(); i++) {
                        Calendar calendar1 = calendar.get(i);
                        String data = calendar1.getYear() + "-" + calendar1.getMonth() + "-" + calendar1.getDay();

                        SelectDate.add(data);

                    }

                }
                if(SelectDate.size()>0){
                    listener.onSelectedDone(SelectDate);
                }
                mCalendarView.clearMultiSelect();
                mCalendarView.clearSelectRange();
            }
        });


    }

    private void initMulti() {
        mCalendarView=null;
        mCalendarView =  mMenuView.findViewById(R.id.calendarView);
        mCalendarView.clearMultiSelect();
        mCalendarView.clearSelectRange();
        mCalendarView.setMonthView(CustomMultiMonthView.class);
        mCalendarView.setMaxMultiSelectSize(25);
        mCalendarView.setSelectMultiMode();
        mCalendarView.setOnCalendarMultiSelectListener(new CalendarView.OnCalendarMultiSelectListener() {
            @Override
            public void onCalendarMultiSelectOutOfRange(Calendar calendar) {

            }

            @Override
            public void onMultiSelectOutOfSize(Calendar calendar, int maxSize) {

            }

            @Override
            public void onCalendarMultiSelect(Calendar calendar, int curSize, int maxSize) {
                mTextLunar.setVisibility(View.VISIBLE);
                mTextYear.setVisibility(View.VISIBLE);
                mTextMonthDay.setText(calendar.getMonth() + "月");
                mTextYear.setText(String.valueOf(calendar.getYear()));
                mTextLunar.setText(calendar.getLunar());
                mYear = calendar.getYear();

            }
        });
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        //设置日期拦截事件，当前有效
        mCalendarView.setOnCalendarInterceptListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mCalendarView.post(new Runnable() {
            @Override
            public void run() {
                mCalendarView.scrollToCurrent();
            }
        });
    }
    private void initRange() {
        mCalendarView=null;
        mCalendarView =  mMenuView.findViewById(R.id.calendarView);
        mCalendarView.clearMultiSelect();
        mCalendarView.clearSelectRange();
        mCalendarView.setMonthView(CustomRangeMonthView.class);
        mCalendarView.setMaxMultiSelectSize(25);
        mCalendarView.setSelectRangeMode();
        mCalendarView.setOnCalendarRangeSelectListener(new CalendarView.OnCalendarRangeSelectListener() {
            @Override
            public void onCalendarSelectOutOfRange(Calendar calendar) {

            }

            @Override
            public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {

            }

            @Override
            public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {
                mTextLunar.setVisibility(View.VISIBLE);
                mTextYear.setVisibility(View.VISIBLE);
                mTextMonthDay.setText(calendar.getMonth() + "月");
                mTextYear.setText(String.valueOf(calendar.getYear()));
                mTextLunar.setText(calendar.getLunar());
                mYear = calendar.getYear();
            }
        });
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnCalendarSelectListener(this);
        //设置日期拦截事件，当前有效
        mCalendarView.setOnCalendarInterceptListener(this);
        mCalendarView.setOnMonthChangeListener(this);

        /*mCalendarView.setRange(2000, 1, 1,

                mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay()

        );*/
        mCalendarView.post(new Runnable() {
            @Override
            public void run() {
                mCalendarView.scrollToCurrent();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }




    /*@Override
    public void onCalendarMultiSelectOutOfRange(Calendar calendar) {
    }*/

   /* @Override
    public void onMultiSelectOutOfSize(Calendar calendar, int maxSize) {
        Toast.makeText(mMenuView.getContext(), "最多选择 ：" + maxSize+"天", Toast.LENGTH_SHORT).show();
    }*/

   /* @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarMultiSelect(Calendar calendar, int curSize, int maxSize) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();

    }*/


    @Override
    public void onYearChange(int year) {
        mTextYear.setText(String.valueOf(year));
    }


    @Override
    public void onMonthChange(int year, int month) {
        mTextMonthDay.setText(month + "月");
    }

    /**
     * 屏蔽某些不可点击的日期，可根据自己的业务自行修改
     * 如 calendar > 2018年1月1日 && calendar <= 2020年12月31日
     *
     * @param calendar calendar
     * @return 是否屏蔽某些不可点击的日期，MonthView和WeekView有类似的API可调用
     * false可选 true不可
     */
    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        SharedPreferences sps = mMenuView.getContext().getSharedPreferences("datas", Context.MODE_PRIVATE);
        int billDay  = sps.getInt("billDay",1);
        int payDay  = sps.getInt("payDay",1);

        int dataa = Integer.valueOf(calendar.getYear() + "" + calendar.getMonth());//选择到的日历年月之和
        int datab = Integer.valueOf(mCalendarView.getCurYear() + "" + mCalendarView.getCurMonth());//当前时间的年月之和

        int calendarday=calendar.getDay();//遍历日历的日期
        if (dataa < datab) {//小于当前月不可选
            return true;

        } else{//当前月后或者当前月的时间
            if(billDay<payDay){//账单日小于还款日
                if ( billDay<=calendarday&&calendarday<=payDay) {
                    if (dataa == datab){//日历月份与当前月份相同
                        if (calendarday < Integer.valueOf(mCalendarView.getCurDay())) {//日历的时间是否比当前月的今日时间小
                            //与当前月相同，小于当天的不可选
                            return true;
                        }else {
                            return false;
                        }
                    }else {//当前月之后的月份
                        return false;
                    }

                }else {
                    return true;
                }
            }else {//账单日大于还款日
                if ( payDay<calendarday&&calendarday<billDay) {
                    //当月还款日到下一个账单日之间不可选
                    return true;
                }else {
                    if (dataa == datab){//日历月份与当前月份相同
                        if (calendarday < Integer.valueOf(mCalendarView.getCurDay())) {//日历的时间是否比当前月的今日时间小
                            //与当前月相同，小于当天的不可选
                            return true;
                        }else {
                            return false;
                        }
                    }else {//当前月之后的月份
                        return false;
                    }
                }
            }
        }
      /*  if (dataa < datab) {//小于当前月不可选
            return true;

        } else if (dataa == datab) {
            if (Integer.valueOf(calendar.getDay()) < Integer.valueOf(mCalendarView.getCurDay())) {
                //与当前月相同，小于当天的不可选
                return true;
            } else {
                //账单日大于还款日 还款日在下一月
                //账单日小于还款日
                if ()
                return false;
            }

        } else {//大于当前月

            return false;
        }*/

    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
      /*  Toast.makeText(this,
                calendar.toString() + (isClick ? "拦截不可点击" : "拦截设定为无效日期"),
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        Log.e("onDateSelected", "  -- " + calendar.getYear() +
                "  --  " + calendar.getMonth() +
                "  -- " + calendar.getDay() +
                "  --  " + isClick + "  --   " + calendar.getScheme());
    }
    public interface DialogDateSelecteListener{
        void onSelectedDone(List<String> selectDates);

    }


    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    /*@Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {
        // TODO: 2018/9/13 超出范围提示
    }*/

   /* @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        Toast.makeText(mMenuView.getContext(),
                calendar.toString() + (isOutOfMinRange ? "小于最小选择范围" : "超过最大选择范围"),
                Toast.LENGTH_SHORT).show();
    }*/

   /* @SuppressLint("SetTextI18n")
    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {

    }*/

}
