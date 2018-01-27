package com.example.jungle.keepinmind1.Utils.DataBaseUtil;

import android.database.Cursor;

import com.example.jungle.keepinmind1.Bean.ManageMoneyDBBean;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Objects;

/**
 * Created by jungle on 2017/12/23.
 */

public class DataBaseUtils {
    public static void add(ManageMoneyDBBean dbBean) {

        if (dbBean.save()) {
            System.out.println("存储成功");
        } else {
            System.out.println("存储失败");
        }

    }

    /*
    * 通过起始时间和类型查询
    *
    * */
    public static List<ManageMoneyDBBean> queryUseType(String startTime, String endTime, String type) {
        List<ManageMoneyDBBean> list = DataSupport
                .where("time > ? and time < ? and type = ?", startTime, endTime, type)
                .order("time desc")
                .find(ManageMoneyDBBean.class);
        return list;
    }

    /*
    *通过起始时间和账户类型查询
    *
    * */
    public static List<ManageMoneyDBBean> queryUseAccount(String startTime, String endTime, String account) {
        List<ManageMoneyDBBean> list = DataSupport
                .where("time > ? and time < ? and account = ?", startTime, endTime, account)
                .order("time desc")
                .find(ManageMoneyDBBean.class);
        return list;
    }

    /*
    *通过起始时间和账户类型和类型查询
    *
    * */

    public static List<ManageMoneyDBBean> queryUseAccountType(String startTime, String endTime, String account,String type) {
        List<ManageMoneyDBBean> list = DataSupport
                .where("time > ? and time < ? and account = ? and type = ?", startTime, endTime, account,type)
                .order("time desc")
                .find(ManageMoneyDBBean.class);
        return list;
    }


    public static Cursor queryGroupByAccount(String startTime, String endTime,String type) {
        Cursor cursor = DataSupport.findBySQL("select sum(money),account from managemoneydbbean where type= ? and time > ? and time < ? group by account",type,startTime,endTime );
        return cursor;
    }

    public static double queryMax(String startTime, String endTime,String type) {
        Double max = DataSupport.where("type= ? and time > ? and time < ? ",type,startTime,endTime).max(ManageMoneyDBBean.class,"money",double.class);
        return max;
    }


    public static double sumMoney(List<ManageMoneyDBBean> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getMoney();
        }
        return sum;
    }

    public static double sumMoneyInAndOut(List<ManageMoneyDBBean> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getType().equals("in")) {
                sum += list.get(i).getMoney();
            } else if (list.get(i).getType().equals("out")) {
                sum -= list.get(i).getMoney();
            }
        }
        return sum;
    }


    public static void update(ManageMoneyDBBean dbBean) {
        dbBean.delete();
    }
}
