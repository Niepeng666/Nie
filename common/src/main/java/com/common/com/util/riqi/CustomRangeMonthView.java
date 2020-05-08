package com.common.com.util.riqi;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

/**
 * 范围选择月视图
 * Created by huanghaibin on 2018/9/13.
 */

public class CustomRangeMonthView extends RangeMonthView {

    private int mRadius;
    /**
     * 自定义魅族标记的文本画笔
     */
    private Paint mTextPaint = new Paint();


    /**
     * 24节气画笔
     */
    private Paint mSolarTermTextPaint = new Paint();

    /**
     * 背景圆点
     */
    private Paint mPointPaint = new Paint();
    /**
     * 其它不可选月份日期颜色
     */
    protected Paint mUnclickMonthTextPaint = new Paint();
    /**
     * 账单日还款日颜色
     */
    protected Paint mBIllPayDayMonthTextPaint = new Paint();
    /**
     * 今天的背景色
     */
    private Paint mCurrentDayPaint = new Paint();
    String paydate;
    void setdata(String paydate){
        this.paydate=paydate;
    }
    /**
     * 圆点半径
     */
    private float mPointRadius;

    private int mPadding;

    private float mCircleRadius;
    /**
     * 自定义魅族标记的圆形背景
     */
    private Paint mSchemeBasicPaint = new Paint();

    private float mSchemeBaseLine;

    public CustomRangeMonthView(Context context) {
        super(context);
        mTextPaint.setTextSize(dipToPx(context, 8));
        mTextPaint.setColor(0xffffffff);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setFakeBoldText(true);


        mSolarTermTextPaint.setColor(0xff489dff);
        mSolarTermTextPaint.setAntiAlias(true);
        mSolarTermTextPaint.setTextAlign(Paint.Align.CENTER);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);
        mSchemeBasicPaint.setColor(Color.WHITE);

        mUnclickMonthTextPaint.setAntiAlias(true);
        mUnclickMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        mUnclickMonthTextPaint.setColor(0xFFD3D3D3);
        mUnclickMonthTextPaint.setFakeBoldText(true);
        mUnclickMonthTextPaint.setTextSize(dipToPx(context, 16));

        mBIllPayDayMonthTextPaint.setAntiAlias(true);
        mBIllPayDayMonthTextPaint.setTextAlign(Paint.Align.CENTER);
        mBIllPayDayMonthTextPaint.setColor(0xFF489dff);
        mBIllPayDayMonthTextPaint.setFakeBoldText(true);
        mBIllPayDayMonthTextPaint.setTextSize(dipToPx(context, 10));

        mCurrentDayPaint.setAntiAlias(true);
        mCurrentDayPaint.setStyle(Paint.Style.FILL);
        mCurrentDayPaint.setColor(0xFFeaeaea);

        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setColor(Color.RED);

        mCircleRadius = dipToPx(getContext(), 7);

        mPadding = dipToPx(getContext(), 3);

        mPointRadius = dipToPx(context, 2);

        Paint.FontMetrics metrics = mSchemeBasicPaint.getFontMetrics();
        mSchemeBaseLine = mCircleRadius - metrics.descent + (metrics.bottom - metrics.top) / 2 + dipToPx(getContext(), 1);
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

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        if (isSelectedPre) {
            if (isSelectedNext) {
                canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            } else {//最后一个，the last
                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            }
        } else {
            if(isSelectedNext){
                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            }
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            //
        }

        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
      /*  float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable? mCurMonthTextPaint : mOtherMonthTextPaint);
        }*/
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int top = y - mItemHeight / 6;


        if (calendar.isCurrentDay() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, mCurrentDayPaint);
        }

        if (hasScheme) {
            canvas.drawCircle(x + mItemWidth - mPadding - mCircleRadius / 2, y + mPadding + mCircleRadius, mCircleRadius, mSchemeBasicPaint);
            mTextPaint.setColor(calendar.getSchemeColor());
            canvas.drawText(calendar.getScheme(), x + mItemWidth - mPadding - mCircleRadius, y + mPadding + mSchemeBaseLine, mTextPaint);
        }

        //当然可以换成其它对应的画笔就不麻烦，
        if (calendar.isWeekend() && calendar.isCurrentMonth()) {//是否是周末
            mCurMonthTextPaint.setColor(0xFF489dff);
            mCurMonthLunarTextPaint.setColor(0xFF489dff);
            mSchemeTextPaint.setColor(0xFF489dff);
            mSchemeLunarTextPaint.setColor(0xFF489dff);
            mOtherMonthLunarTextPaint.setColor(0xFF489dff);
            mOtherMonthTextPaint.setColor(0xFF489dff);
        } else {
            mCurMonthTextPaint.setColor(0xff333333);
            mCurMonthLunarTextPaint.setColor(0xffCFCFCF);
            mSchemeTextPaint.setColor(0xff333333);
            mSchemeLunarTextPaint.setColor(0xffCFCFCF);
            mOtherMonthTextPaint.setColor(0xFFe1e1e1);
            mOtherMonthLunarTextPaint.setColor(0xFFe1e1e1);
        }
        //新增 账单日 还款日
        SharedPreferences sps = getContext().getSharedPreferences("datas", Context.MODE_PRIVATE);
        int billDay  = sps.getInt("billDay",1);
        int payDay  = sps.getInt("payDay",1);


//    false可选 true不可
        if (setSelectedColor(calendar,billDay,payDay)){
            canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top, mUnclickMonthTextPaint);
        }else {
            if (isSelected) {
                //公历
                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        mSelectTextPaint);
                //底部农历
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10, mSelectedLunarTextPaint);
            } else if (hasScheme) {

                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
//                    !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint : mSchemeLunarTextPaint);
            } else {
                canvas.drawText(String.valueOf(calendar.getDay()), cx, mTextBaseLine + top,
                        calendar.isCurrentDay() ? mCurDayTextPaint :
                                calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
//            canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 10,
//                    calendar.isCurrentDay() ? mCurDayLunarTextPaint :
//                            calendar.isCurrentMonth() ? !TextUtils.isEmpty(calendar.getSolarTerm()) ? mSolarTermTextPaint  :
//                                    mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
            }

            if (calendar.getDay()== billDay){
                canvas.drawText("", cx, mTextBaseLine + y + mItemHeight / 10,mBIllPayDayMonthTextPaint);
            }
            if (calendar.getDay()== payDay){
                canvas.drawText("还款日", cx, mTextBaseLine + y + mItemHeight / 10,mBIllPayDayMonthTextPaint);
            }
        }
    }
    private boolean setSelectedColor(Calendar calendar, int billDay, int payDay) {
        java.util.Calendar calendarCurrent = java.util.Calendar.getInstance();
        calendarCurrent.add(java.util.Calendar.MONTH,1);
        int dataa = Integer.valueOf(calendar.getYear() + "" + calendar.getMonth());//选择到的日历年月之和
        int datab = Integer.valueOf(calendarCurrent.get(java.util.Calendar.YEAR) + "" + calendarCurrent.get(java.util.Calendar.MONTH));//当前时间的年月之和

        int calendarday=calendar.getDay();//遍历日历的日期
        if (dataa < datab) {//小于当前月不可选
            return true;

        } else{//当前月后或者当前月的时间
            if(billDay<payDay){//账单日小于还款日
                if ( billDay<=calendarday&&calendarday<=payDay) {
                    if (dataa == datab){//日历月份与当前月份相同
                        if (calendarday < calendarCurrent.get(java.util.Calendar.DAY_OF_MONTH)) {//日历的时间是否比当前月的今日时间小
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
                        if (calendarday < calendarCurrent.get(java.util.Calendar.DAY_OF_MONTH)) {//日历的时间是否比当前月的今日时间小
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
    }
}
