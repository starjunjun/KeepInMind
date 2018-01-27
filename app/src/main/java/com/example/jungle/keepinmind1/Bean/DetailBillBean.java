package com.example.jungle.keepinmind1.Bean;

/**
 * Created by jungle on 2017/12/7.
 */

public class DetailBillBean {

    private String date;
    private String dateLine;
    private double add;
    private double decrease;

    public DetailBillBean(String date, String dateLine, double add, double decrease) {
        this.date = date;
        this.dateLine = dateLine;
        this.add = add;
        this.decrease = decrease;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateLine() {
        return dateLine;
    }

    public void setDateLine(String dateLine) {
        this.dateLine = dateLine;
    }

    public double getAdd() {
        return add;
    }

    public void setAdd(double add) {
        this.add = add;
    }

    public double getDecrease() {
        return decrease;
    }

    public void setDecrease(double decrease) {
        this.decrease = decrease;
    }
}
