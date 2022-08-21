/*
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
package com.example.uploadmodule.upload.entity;


import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author wuxiaoxuan
* @date 2022-06-05
**/

public class TkyDetectionInformation implements Serializable {

    private String bydbh;

    private String account;

    private String testType;

    private String sjstartMile;

    private String sjstopMile;

    private String appFileTypeRadar;

    private String appFileTypePhoto;

    private String beizhu1;

    private String beizhu2;

    private String beizhu3;

    private String beizhu4;

    private String beizhu5;

    private Integer id;

    public String getBydbh() {
        return bydbh;
    }

    public void setBydbh(String bydbh) {
        this.bydbh = bydbh;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getSjstartMile() {
        return sjstartMile;
    }

    public void setSjstartMile(String sjstartMile) {
        this.sjstartMile = sjstartMile;
    }

    public String getSjstopMile() {
        return sjstopMile;
    }

    public void setSjstopMile(String sjstopMile) {
        this.sjstopMile = sjstopMile;
    }

    public String getAppFileTypeRadar() {
        return appFileTypeRadar;
    }

    public void setAppFileTypeRadar(String appFileTypeRadar) {
        this.appFileTypeRadar = appFileTypeRadar;
    }

    public String getAppFileTypePhoto() {
        return appFileTypePhoto;
    }

    public void setAppFileTypePhoto(String appFileTypePhoto) {
        this.appFileTypePhoto = appFileTypePhoto;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}