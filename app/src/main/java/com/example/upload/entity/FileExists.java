package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileExists implements Serializable {
    @SerializedName("exist")
    String fileExist;

    public String getFileExist() {
        return fileExist;
    }

    public void setFileExist(String fileExist) {
        this.fileExist = fileExist;
    }

    @Override
    public String toString() {
        return "FileExists{" +
                "fileExist='" + fileExist + '\'' +
                '}';
    }
}
