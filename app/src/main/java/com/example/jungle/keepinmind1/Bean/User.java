package com.example.jungle.keepinmind1.Bean;

public class User {
    private int id;
    private String username;
    private String account;
    private String password;
    private String headimg;
    private String introduce;

    public User() {
    }

    public User(String username, String account, String password, String headimg, String introduce) {

        this.username = username;
        this.account = account;
        this.password = password;
        this.headimg = headimg;
        this.introduce = introduce;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
