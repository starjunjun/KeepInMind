package com.example.jungle.keepinmind1.Bean;

/**
 * Created by jungle on 2018/1/13.
 */

public class GuPiaoBean {
    private String code;   //代码
    private String name;   //名称
    private String trade;  //最新价
    private String pricechange;   // 涨跌额
    private String changepercent;  //涨跌幅
    private String buy; //买入
    private String sell; //卖出
    private String high;  //最高
    private String low;  //最低
    private long volume; //成交量
    private long amount; //成交额
    private String open;  //今开
    private String settlement; //昨收


    public GuPiaoBean(String code, String name, String trade, String pricechange, String changepercent, String buy, String sell, String high, String low, long volume, long amount, String open, String settlement) {
        this.code = code;
        this.name = name;
        this.trade = trade;
        this.pricechange = pricechange;
        this.changepercent = changepercent;
        this.buy = buy;
        this.sell = sell;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.amount = amount;
        this.open = open;
        this.settlement = settlement;
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

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPricechange() {
        return pricechange;
    }

    public void setPricechange(String pricechange) {
        this.pricechange = pricechange;
    }

    public String getChangepercent() {
        return changepercent;
    }

    public void setChangepercent(String changepercent) {
        this.changepercent = changepercent;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }
}
