package com.example.jungle.keepinmind1.Utils;

import com.example.jungle.keepinmind1.Bean.JiJinBean;
import com.example.jungle.keepinmind1.Utils.DataBaseUtil.DateExchangeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jungle on 2017/12/29.
 */

public class TestMain {
    private static String firstDay;
    private static String lastDay;

    public static void main(String[] args) throws ParseException {


//        System.out.println(DateExchangeUtil.dateToStamp(DateExchangeUtil.getMonthStartDate()));
//        System.out.println(DateExchangeUtil.dateToStamp(DateExchangeUtil.getTodayDate(System.currentTimeMillis())));
//        System.out.println(System.currentTimeMillis());


        System.out.println(DateExchangeUtil.getMonthStartDate());
//        for (int i = 0; i < DateExchangeUtil.allWeekdays().size(); i++) {
//            System.out.println(DateExchangeUtil.allWeekdays().get(i));
//        }

    }


    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String getCurrYearFirst() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearFirst(currentYear);
    }

    /**
     * 获取当年的最后一天
     *
     * @return
     */
    public static String getCurrYearLast() {
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = f.format(currYearFirst);
        return sDate;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = f.format(currYearLast);
        return sDate;
    }

}
