package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

public class Userinfo {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    public Userinfo(String name, String pwd){
        username = name;
        password = pwd;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
