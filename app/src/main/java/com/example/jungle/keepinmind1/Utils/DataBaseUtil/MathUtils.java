package com.example.jungle.keepinmind1.Utils.DataBaseUtil;

/**
 * Created by jungle on 2018/1/8.
 */

public class MathUtils {
    public static double budget=0;
    public static  double format(double number){
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.00");
        df.format(number);
        return number;

    }

}
