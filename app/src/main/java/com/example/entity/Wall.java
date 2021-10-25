package com.example.entity;

public class Wall {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;
    private String dangqiangId;
    private String dangqiangBianhao;
    private String dangqiangLeixing;

    public String getDangqiangWeizhi() {
        return dangqiangWeizhi;
    }

    public void setDangqiangWeizhi(String dangqiangWeizhi) {
        this.dangqiangWeizhi = dangqiangWeizhi;
    }

    private String dangqiangWeizhi;
    @Override
    public String toString() {
        return "Wall{" +
                "dangqiangId='" + dangqiangId + '\'' +
                ", dangqiangBianhao='" + dangqiangBianhao + '\'' +
                ", dangqiangLeixing='" + dangqiangLeixing + '\'' +
                ", dangqiangGao='" + dangqiangGao + '\'' +
                ", dangqiangKuan='" + dangqiangKuan + '\'' +
                ", dangqiangHoudu='" + dangqiangHoudu + '\'' +
                ", cexianBianhao='" + cexianBianhao + '\'' +
                ", cexianFangxiang='" + cexianFangxiang + '\'' +
                ", cexianQidian='" + cexianQidian + '\'' +
                ", cexianZhongdian='" + cexianZhongdian + '\'' +
                ", zhujiXuhao='" + zhujiXuhao + '\'' +
                ", jiancerenyuanName='" + jiancerenyuanName + '\'' +
                ", shujuwenjianName='" + shujuwenjianName + '\'' +
                ", zhaopianOne='" + zhaopianOne + '\'' +
                ", zhaopianTwo='" + zhaopianTwo + '\'' +
                ", zhaopianThree='" + zhaopianThree + '\'' +
                ", beizhu='" + beizhu + '\'' +
                '}';
    }

    private String dangqiangGao;
    private String dangqiangKuan;
    private String dangqiangHoudu;
    private String cexianBianhao;

    public String getZhujiXuhao() {
        return zhujiXuhao;
    }

    public void setZhujiXuhao(String zhujiXuhao) {
        this.zhujiXuhao = zhujiXuhao;
    }

    public String getJiancerenyuanName() {
        return jiancerenyuanName;
    }

    public void setJiancerenyuanName(String jiancerenyuanName) {
        this.jiancerenyuanName = jiancerenyuanName;
    }

    public String getShujuwenjianName() {
        return shujuwenjianName;
    }

    public void setShujuwenjianName(String shujuwenjianName) {
        this.shujuwenjianName = shujuwenjianName;
    }

    public String getZhaopianOne() {
        return zhaopianOne;
    }

    public void setZhaopianOne(String zhaopianOne) {
        this.zhaopianOne = zhaopianOne;
    }

    public String getZhaopianTwo() {
        return zhaopianTwo;
    }

    public void setZhaopianTwo(String zhaopianTwo) {
        this.zhaopianTwo = zhaopianTwo;
    }

    public String getZhaopianThree() {
        return zhaopianThree;
    }

    public void setZhaopianThree(String zhaopianThree) {
        this.zhaopianThree = zhaopianThree;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    private String cexianFangxiang;
    private String cexianQidian;
    private String cexianZhongdian;

    private String zhujiXuhao;
    private String jiancerenyuanName;
    private String shujuwenjianName;
    private String zhaopianOne;
    private String zhaopianTwo;
    private String zhaopianThree;
    private String beizhu;


    public String getDangqiangId() {
        return dangqiangId;
    }

    public void setDangqiangId(String dangqiangId) {
        this.dangqiangId = dangqiangId;
    }

    public String getDangqiangBianhao() {
        return dangqiangBianhao;
    }

    public void setDangqiangBianhao(String dangqiangBianhao) {
        this.dangqiangBianhao = dangqiangBianhao;
    }

    public String getDangqiangLeixing() {
        return dangqiangLeixing;
    }

    public void setDangqiangLeixing(String dangqiangLeixing) {
        this.dangqiangLeixing = dangqiangLeixing;
    }

    public String getDangqiangGao() {
        return dangqiangGao;
    }

    public void setDangqiangGao(String dangqiangGao) {
        this.dangqiangGao = dangqiangGao;
    }

    public String getDangqiangKuan() {
        return dangqiangKuan;
    }

    public void setDangqiangKuan(String dangqiangKuan) {
        this.dangqiangKuan = dangqiangKuan;
    }

    public String getDangqiangHoudu() {
        return dangqiangHoudu;
    }

    public void setDangqiangHoudu(String dangqiangHoudu) {
        this.dangqiangHoudu = dangqiangHoudu;
    }

    public String getCexianBianhao() {
        return cexianBianhao;
    }

    public void setCexianBianhao(String cexianBianhao) {
        this.cexianBianhao = cexianBianhao;
    }

    public String getCexianFangxiang() {
        return cexianFangxiang;
    }

    public void setCexianFangxiang(String cexianFangxiang) {
        this.cexianFangxiang = cexianFangxiang;
    }

    public String getCexianQidian() {
        return cexianQidian;
    }

    public void setCexianQidian(String cexianQidian) {
        this.cexianQidian = cexianQidian;
    }

    public String getCexianZhongdian() {
        return cexianZhongdian;
    }

    public void setCexianZhongdian(String cexianZhongdian) {
        this.cexianZhongdian = cexianZhongdian;
    }
}
