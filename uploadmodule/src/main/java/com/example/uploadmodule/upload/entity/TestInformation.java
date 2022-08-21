package com.example.uploadmodule.upload.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;


public class TestInformation implements Serializable {

    @SerializedName("testInforId")
    private Long testInforId;//id

    @SerializedName("testId")
    private String testId;//报检号

    @SerializedName("testTime")
    private Timestamp testTime;//申请检测时间

    @SerializedName("testStartingDistance")
    private String testStartingDistance;//待检区段起始里程

    @SerializedName("testEndingDistance")
    private String testEndingDistance;//待检区段结束里程

    @SerializedName("testLength")
    private String testLength;//待检区段长度

    @SerializedName("wallRockType")
    private String wallRockType;//围岩类型

    @SerializedName("supportTickness")
    private String supportTickness;//初支厚度

    @SerializedName("separationDistance")
    private String separationDistance;//间距(m/榀)

    @SerializedName("meshDistance")
    private String meshDistance;//钢筋网间距

    @SerializedName("annularBarDistance")
    private String annularBarDistance;//环向钢筋间距

    @SerializedName("reinforPrtTickness")
    private String reinforPrtTickness;//钢筋保护厚度

    @SerializedName("secLineArchTickness")
    private String secLineArchTickness;//二次衬砌厚度-拱部

    @SerializedName("secLineWallTickness")
    private String secLineWallTickness;//二次衬砌厚度-边墙

    @SerializedName("secLineInverArchTickness")
    private String secLineInverArchTickness;//二次衬砌厚度-仰拱

    @SerializedName("secLineFilerTickness")
    private String secLineFilerTickness;//二次衬砌厚度-填充

    @SerializedName("projectName")
    private String projectName;//项目名称

    @SerializedName("sectionName")
    private String sectionName;//标段名称

    @SerializedName("tunnelName")
    private String tunnelName;//隧道名称

    @SerializedName("worksiteName")
    private String worksiteName;//工点名称

    @SerializedName("statute")
    private String statute;//状态

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

    private String beizhu16;
    private String beizhu17;
    private String beizhu18;
    private String beizhu19;
    private String beizhu20;
    private String beizhu21;
    private String beizhu22;
    private String beizhu23;
    private String beizhu24;
    private String beizhu25;

    @Override
    public String toString() {
        return "TestInformation{" +
                "testInforId=" + testInforId +
                ", testId='" + testId + '\'' +
                ", testTime=" + testTime +
                ", testStartingDistance='" + testStartingDistance + '\'' +
                ", testEndingDistance='" + testEndingDistance + '\'' +
                ", testLength='" + testLength + '\'' +
                ", wallRockType='" + wallRockType + '\'' +
                ", supportTickness='" + supportTickness + '\'' +
                ", separationDistance='" + separationDistance + '\'' +
                ", meshDistance='" + meshDistance + '\'' +
                ", annularBarDistance='" + annularBarDistance + '\'' +
                ", reinforPrtTickness='" + reinforPrtTickness + '\'' +
                ", secLineArchTickness='" + secLineArchTickness + '\'' +
                ", secLineWallTickness='" + secLineWallTickness + '\'' +
                ", secLineInverArchTickness='" + secLineInverArchTickness + '\'' +
                ", secLineFilerTickness='" + secLineFilerTickness + '\'' +
                ", projectName='" + projectName + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", tunnelName='" + tunnelName + '\'' +
                ", worksiteName='" + worksiteName + '\'' +
                ", statute='" + statute + '\'' +
                ", beizhu1='" + beizhu1 + '\'' +
                ", beizhu2='" + beizhu2 + '\'' +
                ", beizhu3='" + beizhu3 + '\'' +
                ", beizhu4='" + beizhu4 + '\'' +
                ", beizhu5='" + beizhu5 + '\'' +
                ", beizhu6='" + beizhu6 + '\'' +
                ", beizhu7='" + beizhu7 + '\'' +
                ", beizhu8='" + beizhu8 + '\'' +
                ", beizhu9='" + beizhu9 + '\'' +
                ", beizhu10='" + beizhu10 + '\'' +
                ", beizhu11='" + beizhu11 + '\'' +
                ", beizhu12='" + beizhu12 + '\'' +
                ", beizhu13='" + beizhu13 + '\'' +
                ", beizhu14='" + beizhu14 + '\'' +
                ", beizhu15='" + beizhu15 + '\'' +
                ", beizhu16='" + beizhu16 + '\'' +
                ", beizhu17='" + beizhu17 + '\'' +
                ", beizhu18='" + beizhu18 + '\'' +
                ", beizhu19='" + beizhu19 + '\'' +
                ", beizhu20='" + beizhu20 + '\'' +
                ", beizhu21='" + beizhu21 + '\'' +
                ", beizhu22='" + beizhu22 + '\'' +
                ", beizhu23='" + beizhu23 + '\'' +
                ", beizhu24='" + beizhu24 + '\'' +
                ", beizhu25='" + beizhu25 + '\'' +
                '}';
    }

    public String getBeizhu16() {
        return beizhu16;
    }

    public void setBeizhu16(String beizhu16) {
        this.beizhu16 = beizhu16;
    }

    public String getBeizhu17() {
        return beizhu17;
    }

    public void setBeizhu17(String beizhu17) {
        this.beizhu17 = beizhu17;
    }

    public String getBeizhu18() {
        return beizhu18;
    }

    public void setBeizhu18(String beizhu18) {
        this.beizhu18 = beizhu18;
    }

    public String getBeizhu19() {
        return beizhu19;
    }

    public void setBeizhu19(String beizhu19) {
        this.beizhu19 = beizhu19;
    }

    public String getBeizhu20() {
        return beizhu20;
    }

    public void setBeizhu20(String beizhu20) {
        this.beizhu20 = beizhu20;
    }

    public String getBeizhu21() {
        return beizhu21;
    }

    public void setBeizhu21(String beizhu21) {
        this.beizhu21 = beizhu21;
    }

    public String getBeizhu22() {
        return beizhu22;
    }

    public void setBeizhu22(String beizhu22) {
        this.beizhu22 = beizhu22;
    }

    public String getBeizhu23() {
        return beizhu23;
    }

    public void setBeizhu23(String beizhu23) {
        this.beizhu23 = beizhu23;
    }

    public String getBeizhu24() {
        return beizhu24;
    }

    public void setBeizhu24(String beizhu24) {
        this.beizhu24 = beizhu24;
    }

    public String getBeizhu25() {
        return beizhu25;
    }

    public void setBeizhu25(String beizhu25) {
        this.beizhu25 = beizhu25;
    }

    public Long getTestInforId() {
        return testInforId;
    }

    public void setTestInforId(Long testInforId) {
        this.testInforId = testInforId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public Timestamp getTestTime() {
        return testTime;
    }

    public void setTestTime(Timestamp testTime) {
        this.testTime = testTime;
    }

    public String getTestStartingDistance() {
        return testStartingDistance;
    }

    public void setTestStartingDistance(String testStartingDistance) {
        this.testStartingDistance = testStartingDistance;
    }

    public String getTestEndingDistance() {
        return testEndingDistance;
    }

    public void setTestEndingDistance(String testEndingDistance) {
        this.testEndingDistance = testEndingDistance;
    }

    public String getTestLength() {
        return testLength;
    }

    public void setTestLength(String testLength) {
        this.testLength = testLength;
    }

    public String getWallRockType() {
        return wallRockType;
    }

    public void setWallRockType(String wallRockType) {
        this.wallRockType = wallRockType;
    }

    public String getSupportTickness() {
        return supportTickness;
    }

    public void setSupportTickness(String supportTickness) {
        this.supportTickness = supportTickness;
    }

    public String getSeparationDistance() {
        return separationDistance;
    }

    public void setSeparationDistance(String separationDistance) {
        this.separationDistance = separationDistance;
    }

    public String getMeshDistance() {
        return meshDistance;
    }

    public void setMeshDistance(String meshDistance) {
        this.meshDistance = meshDistance;
    }

    public String getAnnularBarDistance() {
        return annularBarDistance;
    }

    public void setAnnularBarDistance(String annularBarDistance) {
        this.annularBarDistance = annularBarDistance;
    }

    public String getReinforPrtTickness() {
        return reinforPrtTickness;
    }

    public void setReinforPrtTickness(String reinforPrtTickness) {
        this.reinforPrtTickness = reinforPrtTickness;
    }

    public String getSecLineArchTickness() {
        return secLineArchTickness;
    }

    public void setSecLineArchTickness(String secLineArchTickness) {
        this.secLineArchTickness = secLineArchTickness;
    }

    public String getSecLineWallTickness() {
        return secLineWallTickness;
    }

    public void setSecLineWallTickness(String secLineWallTickness) {
        this.secLineWallTickness = secLineWallTickness;
    }

    public String getSecLineInverArchTickness() {
        return secLineInverArchTickness;
    }

    public void setSecLineInverArchTickness(String secLineInverArchTickness) {
        this.secLineInverArchTickness = secLineInverArchTickness;
    }

    public String getSecLineFilerTickness() {
        return secLineFilerTickness;
    }

    public void setSecLineFilerTickness(String secLineFilerTickness) {
        this.secLineFilerTickness = secLineFilerTickness;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getTunnelName() {
        return tunnelName;
    }

    public void setTunnelName(String tunnelName) {
        this.tunnelName = tunnelName;
    }

    public String getWorksiteName() {
        return worksiteName;
    }

    public void setWorksiteName(String worksiteName) {
        this.worksiteName = worksiteName;
    }

    public String getStatute() {
        return statute;
    }

    public void setStatute(String statute) {
        this.statute = statute;
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

}