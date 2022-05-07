package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

public class FileMdRes {
    @SerializedName("avatar")
    private String filename;

    @SerializedName("exists")
    private String exits;

    @SerializedName("info")
    private String count;

    @Override
    public String toString() {
        return "FileMdRes{" +
                "filename='" + filename + '\'' +
                ", exits='" + exits + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExits() {
        return exits;
    }

    public void setExits(String exits) {
        this.exits = exits;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
