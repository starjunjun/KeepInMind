package com.example.jungle.keepinmind1.Bean;

/**
 * Created by jungle on 2017/12/13.
 */

public class RetrunJson<T> {
    private String resultcode;
    private String reason;
    private T result;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
