package com.example.jungle.keepinmind1.Bean;

import android.app.Activity;

/**
 * Created by jungle on 2018/1/20.
 */

public class EventPostBean {
    double num;
    Activity activity;

    public EventPostBean(double num, Activity activity) {
        this.num = num;
        this.activity = activity;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
