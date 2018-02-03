package com.example.jungle.keepinmind1.Bean;

import org.litepal.crud.DataSupport;

/**
 * Created by jungle on 2017/12/23.
 */

public class ManageMoneyDBBean extends DataSupport {
    private int id;
    private Double money;
    private String account;
    private String type;
    private String remarks;
    private long time;
    private String classification;

    public ManageMoneyDBBean() {
    }

    public ManageMoneyDBBean(Double money, String account, String type, String remarks, long time, String classification) {
        this.money = money;
        this.account = account;
        this.type = type;
        this.remarks = remarks;
        this.time = time;
        this.classification = classification;
    }

    public ManageMoneyDBBean(Double money, String account, String type, String remarks, long time) {
        this.money = money;
        this.account = account;
        this.type = type;
        this.remarks = remarks;
        this.time = time;

    }

    public ManageMoneyDBBean(Double money, String account, String type, long time) {
        this.money = money;
        this.account = account;
        this.type = type;
        this.time = time;

    }


    public ManageMoneyDBBean(Double money, String account, String type, long time, String classification) {
        this.money = money;
        this.account = account;
        this.type = type;
        this.time = time;
        this.classification = classification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
