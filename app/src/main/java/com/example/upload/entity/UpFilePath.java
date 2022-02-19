package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

public class UpFilePath {
    @SerializedName("avatar")
    String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UpFilePath{" +
                "avatar='" + avatar + '\'' +
                '}';
    }
}
