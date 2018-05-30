package com.hbb.coder.smartgeoponics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/22.
 */

public class CalendarUtils {


    /**
     * 根据输入的日期,返回对应的周几?
     */
    public static String getWeek(String date) {

        Calendar instance = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (date.equals(simpleDateFormat.format(new Date()))) {
                return "今天";
            } else {
                Date parse = simpleDateFormat.parse(date);
                instance.setTime(parse);
                int dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);
                switch (dayOfWeek) {
                    case Calendar.MONDAY:
                        return "周一";
                    case Calendar.TUESDAY:
                        return "周二";
                    case Calendar.WEDNESDAY:
                        return "周三";
                    case Calendar.THURSDAY:
                        return "周四";
                    case Calendar.FRIDAY:
                        return "周五";
                    case Calendar.SATURDAY:
                        return "周六";
                    case Calendar.SUNDAY:
                        return "周日";


                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 根据日期,生成指定格式的日期
     */
    public static String getFormatDate(String date) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date parse = simpleDateFormat.parse(date);

            simpleDateFormat = new SimpleDateFormat("MM/dd");

            String format = simpleDateFormat.format(parse);

            return format;

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return "";

    }


    /**
     * 获得当前日期
     */
    public static String getCurrentDate(String format) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(new Date());


        } catch (Exception e) {

            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得当前日期
     */
    public static String getCurrentWeek() {

        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        int dayOfWeek = instance.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";

        }
        return "";
    }



}
