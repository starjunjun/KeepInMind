package com.example.jungle.keepinmind1.Bean;

public class User {
    private int id;
    private String username;
    private String account;
    private String password;

    public User() {
    }

    public User( String username, String account, String password) {

        this.username = username;
        this.account = account;
        this.password = password;
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
}
