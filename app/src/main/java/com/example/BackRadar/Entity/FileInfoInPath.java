package com.example.BackRadar.Entity;

public class FileInfoInPath {
    String Filename;
    String updateTime;

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FileInfoInPath{" +
                "Filename='" + Filename + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
