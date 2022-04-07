package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileInfo implements Serializable {

    @SerializedName("Filename")
    private String Filename;

    @Override
    public String toString() {
        return "FileInfo{" +
                "Filename='" + Filename + '\'' +
                '}';
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }
}
