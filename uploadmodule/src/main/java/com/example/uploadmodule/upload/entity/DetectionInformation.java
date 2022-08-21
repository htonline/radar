package com.example.uploadmodule.upload.entity;/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;

public class DetectionInformation implements Serializable {

    @SerializedName("detectionId")
    private Long detectionId;

    @SerializedName("tunnelId")
    private String tunnelId;

    @SerializedName("detectionStartingDistance")
    private String detectionStartingDistance;

    @SerializedName("detectionEndingDistance")
    private String detectionEndingDistance;

    @SerializedName("detectionLength")
    private String detectionLength;

    @SerializedName("time")
    private Timestamp time;

    @SerializedName("detectionLineBiaohao")
    private String detectionLineBiaohao;

    @SerializedName("detectionData")
    private String detectionData;

    @SerializedName("detectionPhotos")
    private String detectionPhotos;

    @SerializedName("radarPhotos")
    private String radarPhotos;

    @SerializedName("detectionSummary")
    private String detectionSummary;

    @SerializedName("others")
    private String others;


    @SerializedName("beizhu1")
    private String beizhu1;

    @SerializedName("beizhu2")
    private String beizhu2;

    @SerializedName("beizhu3")
    private String beizhu3;

    @SerializedName("beizhu4")
    private String beizhu4;

    @SerializedName("beizhu5")
    private String beizhu5;

    @SerializedName("beizhu6")
    private String beizhu6;

    @SerializedName("beizhu7")
    private String beizhu7;

    @SerializedName("beizhu8")
    private String beizhu8;

    @SerializedName("beizhu9")
    private String beizhu9;

    @SerializedName("beizhu10")
    private String beizhu10;

    @SerializedName("beizhu11")
    private String beizhu11;

    @SerializedName("beizhu12")
    private String beizhu12;

    @SerializedName("beizhu13")
    private String beizhu13;

    @SerializedName("beizhu14")
    private String beizhu14;//beizhu14

    @SerializedName("beizhu15")
    private String beizhu15;//beizhu15

//----以下是testInformation里的----
    @SerializedName("tunnelName")
    private String tunnelName;//隧道名称

    @SerializedName("sectionName")
    private String sectionName;//标段名称

    @SerializedName("projectName")
    private String projectName;//项目名称

    @SerializedName("testId")
    private String testId;//报检号

    @SerializedName("worksiteName")
    private String worksiteName;//工点名称

    public Long getDetectionId() {
        return detectionId;
    }

    public void setDetectionId(Long detectionId) {
        this.detectionId = detectionId;
    }

    public String getTunnelId() {
        return tunnelId;
    }

    public void setTunnelId(String tunnelId) {
        this.tunnelId = tunnelId;
    }

    public String getDetectionStartingDistance() {
        return detectionStartingDistance;
    }

    public void setDetectionStartingDistance(String detectionStartingDistance) {
        this.detectionStartingDistance = detectionStartingDistance;
    }

    public String getDetectionEndingDistance() {
        return detectionEndingDistance;
    }

    public void setDetectionEndingDistance(String detectionEndingDistance) {
        this.detectionEndingDistance = detectionEndingDistance;
    }

    public String getDetectionLength() {
        return detectionLength;
    }

    public void setDetectionLength(String detectionLength) {
        this.detectionLength = detectionLength;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getDetectionLineBiaohao() {
        return detectionLineBiaohao;
    }

    public void setDetectionLineBiaohao(String detectionLineBiaohao) {
        this.detectionLineBiaohao = detectionLineBiaohao;
    }

    public String getDetectionData() {
        return detectionData;
    }

    public void setDetectionData(String detectionData) {
        this.detectionData = detectionData;
    }

    public String getDetectionPhotos() {
        return detectionPhotos;
    }

    public void setDetectionPhotos(String detectionPhotos) {
        this.detectionPhotos = detectionPhotos;
    }

    public String getRadarPhotos() {
        return radarPhotos;
    }

    public void setRadarPhotos(String radarPhotos) {
        this.radarPhotos = radarPhotos;
    }

    public String getDetectionSummary() {
        return detectionSummary;
    }

    public void setDetectionSummary(String detectionSummary) {
        this.detectionSummary = detectionSummary;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getBeizhu1() {
        return beizhu1;
    }

    public void setBeizhu1(String beizhu1) {
        this.beizhu1 = beizhu1;
    }

    public String getBeizhu2() {
        return beizhu2;
    }

    public void setBeizhu2(String beizhu2) {
        this.beizhu2 = beizhu2;
    }

    public String getBeizhu3() {
        return beizhu3;
    }

    public void setBeizhu3(String beizhu3) {
        this.beizhu3 = beizhu3;
    }

    public String getBeizhu4() {
        return beizhu4;
    }

    public void setBeizhu4(String beizhu4) {
        this.beizhu4 = beizhu4;
    }

    public String getBeizhu5() {
        return beizhu5;
    }

    public void setBeizhu5(String beizhu5) {
        this.beizhu5 = beizhu5;
    }

    public String getBeizhu6() {
        return beizhu6;
    }

    public void setBeizhu6(String beizhu6) {
        this.beizhu6 = beizhu6;
    }

    public String getBeizhu7() {
        return beizhu7;
    }

    public void setBeizhu7(String beizhu7) {
        this.beizhu7 = beizhu7;
    }

    public String getBeizhu8() {
        return beizhu8;
    }

    public void setBeizhu8(String beizhu8) {
        this.beizhu8 = beizhu8;
    }

    public String getBeizhu9() {
        return beizhu9;
    }

    public void setBeizhu9(String beizhu9) {
        this.beizhu9 = beizhu9;
    }

    public String getBeizhu10() {
        return beizhu10;
    }

    public void setBeizhu10(String beizhu10) {
        this.beizhu10 = beizhu10;
    }

    public String getBeizhu11() {
        return beizhu11;
    }

    public void setBeizhu11(String beizhu11) {
        this.beizhu11 = beizhu11;
    }

    public String getBeizhu12() {
        return beizhu12;
    }

    public void setBeizhu12(String beizhu12) {
        this.beizhu12 = beizhu12;
    }

    public String getBeizhu13() {
        return beizhu13;
    }

    public void setBeizhu13(String beizhu13) {
        this.beizhu13 = beizhu13;
    }

    public String getBeizhu14() {
        return beizhu14;
    }

    public void setBeizhu14(String beizhu14) {
        this.beizhu14 = beizhu14;
    }

    public String getBeizhu15() {
        return beizhu15;
    }

    public void setBeizhu15(String beizhu15) {
        this.beizhu15 = beizhu15;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getWorksiteName() {
        return worksiteName;
    }

    public void setWorksiteName(String worksiteName) {
        this.worksiteName = worksiteName;
    }
}