package com.example.upload.entity;

public class UpLoadFileInfo {
    private String cxbh;
    private String startKM;
    private String stopKM;
    private String filePath;
    private String uploadpercent ="0.0%";

    public String getUploadpercent() {
        return uploadpercent;
    }

    public void setUploadpercent(String uploadpercent) {
        this.uploadpercent = uploadpercent;
    }

    private Boolean isfileup = false;
    private String fileRemotePath;
    private String photoPath;
    private String photoRemotePath;
    private Boolean isphotoup =false;

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

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
