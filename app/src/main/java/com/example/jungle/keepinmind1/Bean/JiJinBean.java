package com.example.jungle.keepinmind1.Bean;

/**
 * Created by jungle on 2017/12/19.
 */

public class JiJinBean {
    //    "code":"184698",		/*基金代码*/
//            "name":"基金天元",		/*基金简称*/
//            "netincome":"--",		/*单位净收益（元）*/
//            "assincome":"-0.1402",		/*单位可分配收益（元）*/
//            "netassrate":"--",		/*净资产收益率（%）*/
//            "netgrowrate":"3.7400",		/*净值增长率(%)*/
//            "tonetgrora":"358.5600",	/*累计净值增长率(%)*/
//            "time":"20120630"		/*时间*/

    private String code;		/*基金代码*/
    private String name;		/*基金简称*/
    private String netincome;		/*单位净收益（元）*/
    private String assincome;		/*单位可分配收益（元）*/
    private String netassrate;		/*净资产收益率（%）*/
    private String netgrowrate;		/*净值增长率(%)*/
    private String tonetgrora;	/*累计净值增长率(%)*/
    private String time;		/*时间*/

    public JiJinBean(String code, String name, String netincome, String assincome, String netassrate, String netgrowrate, String tonetgrora, String time) {
        this.code = code;
        this.name = name;
        this.netincome = netincome;
        this.assincome = assincome;
        this.netassrate = netassrate;
        this.netgrowrate = netgrowrate;
        this.tonetgrora = tonetgrora;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetincome() {
        return netincome;
    }

    public void setNetincome(String netincome) {
        this.netincome = netincome;
    }

    public String getAssincome() {
        return assincome;
    }

    public void setAssincome(String assincome) {
        this.assincome = assincome;
    }

    public String getNetassrate() {
        return netassrate;
    }

    public void setNetassrate(String netassrate) {
        this.netassrate = netassrate;
    }

    public String getNetgrowrate() {
        return netgrowrate;
    }

    public void setNetgrowrate(String netgrowrate) {
        this.netgrowrate = netgrowrate;
    }

    public String getTonetgrora() {
        return tonetgrora;
    }

    public void setTonetgrora(String tonetgrora) {
        this.tonetgrora = tonetgrora;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
