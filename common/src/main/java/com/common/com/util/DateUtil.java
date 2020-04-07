package com.common.com.util.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 */

public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static int intervalBetweenTwoDate(Date startDate, Date endDate) throws ParseException {

        startDate = sdf.parse(sdf.format(endDate));
        endDate = sdf.parse(sdf.format(endDate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(endDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String formateDateToHM(long time){
        return sdf1.format(time);
    }

    public static Date parseDateFromHM(String date) throws ParseException {
        return sdf1.parse(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return sdf.parse(date);
    }

    public static String getAllDateBetweenTwoDate(Date startDate, Date endDate) throws ParseException {
        String s = sdf.format(startDate);
        for (int i = 1; i <= intervalBetweenTwoDate(startDate, endDate); i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + i);
            s = s + "," + sdf.format(calendar.getTime());
        }
        return s;
    }

    public static long getDayBetweenTWodDate(String startDate, String endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null,end = null;
        try {
            start = sdf.parse(startDate);
            end = sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long spi = end.getTime() - start.getTime();
        long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
        return step+1;
    }
    public static boolean isBelongWithDrawTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("08:00");
            endTime = df.parse("22:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return belongCalendar(now, beginTime, endTime);
    }
    private static List<Date> dateSplit(Date startDate, Date endDate)
            throws Exception {
        if (!startDate.before(endDate))
            throw new Exception("开始时间应该在结束时间之后");
        Long spi = endDate.getTime() - startDate.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
        List<Date> dateList = new ArrayList<>();
        dateList.add(startDate);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    + (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        return dateList;
    }

    public static String getDateBetweenTwoDate(String startDate,String endDate) throws ParseException {
        String s = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            List<Date> lists = dateSplit(start, end);
            if (!lists.isEmpty()) {
                for (Date date : lists) {
                    s += sdf.format(date)+",";
                    System.out.println(sdf.format(date));
                }
            }
        } catch (Exception e) {
        }
        if (!TextUtils.isEmpty(s)){
           s = s.substring(0,s.length()-1);
        }
        return s;
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static  boolean isBelong(){

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("08:00");
            endTime = df.parse("24:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return belongCalendar(now, beginTime, endTime);
    }

}
