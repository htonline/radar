package com.example.uploadmodule.upload.entity;

public class UpLoadFileInfo {
    private String cxbh;
    private String startKM;
    private String stopKM;
    private String filePath;
    private String linelength;
    private String uploadpercent = "0.0%";
    private Boolean isfileup = false;
    private String fileRemotePath;
    private String photoPath;
    private String photoRemotePath;
    private Boolean isphotoup = false;

    public UpLoadFileInfo() {
        this.cxbh = "";
        this.startKM = "";
        this.stopKM = "";
        this.filePath = "";
        this.linelength = "";
        this.uploadpercent = "0.0%";
        this.isfileup = false;
        this.fileRemotePath = "";
        this.photoPath = "";
        this.photoRemotePath = "";
        this.isphotoup = false;
    }

    public String getUploadpercent() {
        return uploadpercent;
    }

    public void setUploadpercent(String uploadpercent) {
        this.uploadpercent = uploadpercent;
    }


    public Boolean getIsfileup() {
        return isfileup;
    }

    public String getPhotoRemotePath() {
        return photoRemotePath;
    }

    public void setPhotoRemotePath(String photoRemotePath) {
        this.photoRemotePath = photoRemotePath;
    }

    public String getFileRemotePath() {
        return fileRemotePath;
    }

    public void setFileRemotePath(String fileRemotePath) {
        this.fileRemotePath = fileRemotePath;
    }

    public void setIsfileup(Boolean isfileup) {
        this.isfileup = isfileup;
    }

    public Boolean getIsphotoup() {
        return isphotoup;
    }

    public void setIsphotoup(Boolean isphotoup) {
        this.isphotoup = isphotoup;
    }

    public String getCxbh() {
        return cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }

    public String getStartKM() {
        return startKM;
    }

    public void setStartKM(String startKM) {
        this.startKM = startKM;
    }

    public String getStopKM() {
        return stopKM;
    }

    public void setStopKM(String stopKM) {
        this.stopKM = stopKM;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getLinelength() {
        return linelength;
    }

    public void setLinelength(String linelength) {
        this.linelength = linelength;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "UpLoadFileInfo{" +
                "cxbh='" + cxbh + '\'' +
                ", startKM='" + startKM + '\'' +
                ", stopKM='" + stopKM + '\'' +
                ", filePath='" + filePath + '\'' +
                ", linelength='" + linelength + '\'' +
                ", uploadpercent='" + uploadpercent + '\'' +
                ", isfileup=" + isfileup +
                ", fileRemotePath='" + fileRemotePath + '\'' +
                ", photoPath='" + photoPath + '\'' +
                ", photoRemotePath='" + photoRemotePath + '\'' +
                ", isphotoup=" + isphotoup +
                '}';
    }
}
