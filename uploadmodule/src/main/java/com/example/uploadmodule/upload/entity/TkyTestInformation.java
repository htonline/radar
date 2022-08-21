package com.example.uploadmodule.upload.entity;


import com.example.uploadmodule.upload.annotation.scaninfoanno;

import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author wuxiaoxuan
* @date 2022-05-31
**/

public class TkyTestInformation implements Serializable {

    @scaninfoanno(value = "报验单id")
    private String id;

    @scaninfoanno(value = "项目id")
    private String xmid;

    @scaninfoanno(value = "项目名称")
    private String xmname;

    @scaninfoanno(value = "标段id")
    private String bdid;

    @scaninfoanno(value = "标段名称")
    private String bdname;

    @scaninfoanno(value = "构筑物id")
    private String gzwid;

    @scaninfoanno(value = "构筑物名称")
    private String gzwname;

    @scaninfoanno(value = "工点id")
    private String gdid;

    @scaninfoanno(value = "工点名称")
    private String gdname;

    @scaninfoanno(value = "工程id")
    private String gcid;

    @scaninfoanno(value = "工程名称")
    private String gcname;

    @scaninfoanno(value = "施工单位id")
    private String sgdwid;

    @scaninfoanno(value = "施工单位名称")
    private String sgdwname;

    @scaninfoanno(value = "监理单位id")
    private String jldwid;

    @scaninfoanno(value = "监理单位名称")
    private String jldwname;

    @scaninfoanno(value = "检测单位id")
    private String jcdwid;

    @scaninfoanno(value = "检测单位名称")
    private String jcdwname;

    @scaninfoanno(value = "检测方法")
    private String jcff;

    @scaninfoanno(value = "试验方法")
    private String syff;

    @scaninfoanno(value = "联系人")
    private String lxr;

    @scaninfoanno(value = "联系电话")
    private String lxdh;

    @scaninfoanno(value = "预约检测日期")
    private String byrq;

    @scaninfoanno(value = "检测设备")
    private String jcsb;

    @scaninfoanno(value = "校验日期")
    private String jxrq;

    @scaninfoanno(value = "校验周期")
    private String jxzq;

    @scaninfoanno(value = "施工负责人")
    private String sgfzr;

    @scaninfoanno(value = "检测起始里程编码")
    private String jcqslcbm;

    @scaninfoanno(value = "检测起始里程1")
    private String jcqslc1;

    @scaninfoanno(value = "检测起始里程2")
    private String jcqslc2;

    @scaninfoanno(value = "检测起始里程编码")
    private String jcjslcbm;

    @scaninfoanno(value = "检测结束里程1")
    private String jcjslc1;

    @scaninfoanno(value = "检测结束里程2")
    private String jcjslc2;

    @scaninfoanno(value = "检测长度")
    private String jccd;

    @scaninfoanno(value = "数量")
    private String cqsl;

    @scaninfoanno(value = "检测部位")
    private String jcbw;

    @scaninfoanno(value = "检测方式")
    private String jcfs;

    @scaninfoanno(value = "检测类型")
    private String jclx;

    @scaninfoanno(value = "钻芯部位")
    private String zxbw;

    @scaninfoanno(value = "设计厚度(cm)/仰拱厚度")
    private String sjhd;

    @scaninfoanno(value = "填充厚度")
    private String tchd;

    @scaninfoanno(value = "设计强度")
    private String sjqd;

    @scaninfoanno(value = "锚杆编号")
    private String mgbh;

    @scaninfoanno(value = "锚杆直径(mm)")
    private String mgzj;

    @scaninfoanno(value = "锚杆类型")
    private String mglx;

    @scaninfoanno(value = "锚杆数量")
    private String mgsl;

    @scaninfoanno(value = "围岩等级")
    private String wydj;

    @scaninfoanno(value = "设计锚杆长度(m)")
    private String sjmgcd;

    @scaninfoanno(value = "设计锚杆抗拔力(KN)")
    private String sjmgkbl;

    @scaninfoanno(value = "浇筑日期(施工日期)")
    private String jzrq;

    @scaninfoanno(value = "设计锚杆长度（m)")
    private String shejmgcd;

    @scaninfoanno(value = "设计锚杆抗拔力（KN)")
    private String shejmgkbl;

    @scaninfoanno(value = "龄期强度报告")
    private String lqqdbg;

    @scaninfoanno(value = "示意图")
    private String syt;

    @scaninfoanno(value = "中间报告")
    private String zjbg;

    @scaninfoanno(value = "中间报告检测依据")
    private String zjbgjcyj;

    @scaninfoanno(value = "中间报告简介")
    private String zjbgjj;

    @scaninfoanno(value = "中间报告校验")
    private String zjbgjy;

    @scaninfoanno(value = "检测数据附件")
    private String jcsjfj;

    @scaninfoanno(value = "检测数据附件上传时间")
    private String jcsjfjscsj;

    @scaninfoanno(value = "施工单位发布状态")
    private String sgdwfbzt;

    @scaninfoanno(value = "流程状态")
    private String lczt;

    @scaninfoanno(value = "提交人（施工单位）")
    private String tjr;

    @scaninfoanno(value = "提交备注（施工单位）")
    private String tjbz;

    @scaninfoanno(value = "提交时间（施工单位）")
    private String tjsj;

    @scaninfoanno(value = "审批意见")
    private String spyj;

    @scaninfoanno(value = "审批人（监理单位）")
    private String spr;

    @scaninfoanno(value = "审批备注（监理单位）")
    private String spbz;

    @scaninfoanno(value = "审批时间（监理单位）")
    private String spsj;

    @scaninfoanno(value = "报检范围")
    private String bjfw;

    @scaninfoanno(value = "报检数量")
    private String bjsl;

    @scaninfoanno(value = "报检单编号")
    private String bydbh;

    @scaninfoanno(value = "检测分析时间")
    private String jcfxsj;

    @scaninfoanno(value = "检测分析人")
    private String jcfxr;

    @scaninfoanno(value = "检测意见")
    private String jcyj;

    @scaninfoanno(value = "检测人（检测单位）")
    private String jcr;

    @scaninfoanno(value = "检测备注（检测单位）")
    private String jcbz;

    @scaninfoanno(value = "检测时间（检测单位）")
    private String jcsj;

    @scaninfoanno(value = "检测进度状态")
    private String jcjdzt;

    @scaninfoanno(value = "检测结论备注")
    private String jcjlbz;

    @scaninfoanno(value = "检测结论")
    private String jcjl;

    @scaninfoanno(value = "检测结论附件")
    private String jcjlfj;

    @scaninfoanno(value = "复核检测结果")
    private String fhjcjg;

    @scaninfoanno(value = "复核审批意见")
    private String fhspyj;

    @scaninfoanno(value = "复核审批人（监理单位）")
    private String fhspr;

    @scaninfoanno(value = "复核审批备注（监理单位）")
    private String fhspbz;

    @scaninfoanno(value = "复核审批时间（监理单位）")
    private String fhspsj;

    @scaninfoanno(value = "发布状态")
    private String zt;

    @scaninfoanno(value = "发布时间")
    private String fbsj;

    @scaninfoanno(value = "发布人")
    private String fbr;

    @scaninfoanno(value = "报检单类型")
    private String testType;

    @scaninfoanno(value = "成孔方式")
    private String holeMethod;

    @scaninfoanno(value = "混凝土强度等级")
    private String concreteStrength;

    @scaninfoanno(value = "设计单位id")
    private String desighOrg;

    @scaninfoanno(value = "设计单位名称")
    private String desighOrgName;

    @scaninfoanno(value = "检测单位id")
    private String inspectionOrg;

    @scaninfoanno(value = "检测单位名称")
    private String inspectionOrgName;

    @scaninfoanno(value = "buildType")
    private String buildtype;

    @scaninfoanno(value = "createdate")
    private String createdate;

    @scaninfoanno(value = "createdatestr")
    private String createdatestr;

    @scaninfoanno(value = "中间报告检测")
    private String zjbgjc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getXmname() {
        return xmname;
    }

    public void setXmname(String xmname) {
        this.xmname = xmname;
    }

    public String getBdid() {
        return bdid;
    }

    public void setBdid(String bdid) {
        this.bdid = bdid;
    }

    public String getBdname() {
        return bdname;
    }

    public void setBdname(String bdname) {
        this.bdname = bdname;
    }

    public String getGzwid() {
        return gzwid;
    }

    public void setGzwid(String gzwid) {
        this.gzwid = gzwid;
    }

    public String getGzwname() {
        return gzwname;
    }

    public void setGzwname(String gzwname) {
        this.gzwname = gzwname;
    }

    public String getGdid() {
        return gdid;
    }

    public void setGdid(String gdid) {
        this.gdid = gdid;
    }

    public String getGdname() {
        return gdname;
    }

    public void setGdname(String gdname) {
        this.gdname = gdname;
    }

    public String getGcid() {
        return gcid;
    }

    public void setGcid(String gcid) {
        this.gcid = gcid;
    }

    public String getGcname() {
        return gcname;
    }

    public void setGcname(String gcname) {
        this.gcname = gcname;
    }

    public String getSgdwid() {
        return sgdwid;
    }

    public void setSgdwid(String sgdwid) {
        this.sgdwid = sgdwid;
    }

    public String getSgdwname() {
        return sgdwname;
    }

    public void setSgdwname(String sgdwname) {
        this.sgdwname = sgdwname;
    }

    public String getJldwid() {
        return jldwid;
    }

    public void setJldwid(String jldwid) {
        this.jldwid = jldwid;
    }

    public String getJldwname() {
        return jldwname;
    }

    public void setJldwname(String jldwname) {
        this.jldwname = jldwname;
    }

    public String getJcdwid() {
        return jcdwid;
    }

    public void setJcdwid(String jcdwid) {
        this.jcdwid = jcdwid;
    }

    public String getJcdwname() {
        return jcdwname;
    }

    public void setJcdwname(String jcdwname) {
        this.jcdwname = jcdwname;
    }

    public String getJcff() {
        return jcff;
    }

    public void setJcff(String jcff) {
        this.jcff = jcff;
    }

    public String getSyff() {
        return syff;
    }

    public void setSyff(String syff) {
        this.syff = syff;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getByrq() {
        return byrq;
    }

    public void setByrq(String byrq) {
        this.byrq = byrq;
    }

    public String getJcsb() {
        return jcsb;
    }

    public void setJcsb(String jcsb) {
        this.jcsb = jcsb;
    }

    public String getJxrq() {
        return jxrq;
    }

    public void setJxrq(String jxrq) {
        this.jxrq = jxrq;
    }

    public String getJxzq() {
        return jxzq;
    }

    public void setJxzq(String jxzq) {
        this.jxzq = jxzq;
    }

    public String getSgfzr() {
        return sgfzr;
    }

    public void setSgfzr(String sgfzr) {
        this.sgfzr = sgfzr;
    }

    public String getJcqslcbm() {
        return jcqslcbm;
    }

    public void setJcqslcbm(String jcqslcbm) {
        this.jcqslcbm = jcqslcbm;
    }

    public String getJcqslc1() {
        return jcqslc1;
    }

    public void setJcqslc1(String jcqslc1) {
        this.jcqslc1 = jcqslc1;
    }

    public String getJcqslc2() {
        return jcqslc2;
    }

    public void setJcqslc2(String jcqslc2) {
        this.jcqslc2 = jcqslc2;
    }

    public String getJcjslcbm() {
        return jcjslcbm;
    }

    public void setJcjslcbm(String jcjslcbm) {
        this.jcjslcbm = jcjslcbm;
    }

    public String getJcjslc1() {
        return jcjslc1;
    }

    public void setJcjslc1(String jcjslc1) {
        this.jcjslc1 = jcjslc1;
    }

    public String getJcjslc2() {
        return jcjslc2;
    }

    public void setJcjslc2(String jcjslc2) {
        this.jcjslc2 = jcjslc2;
    }

    public String getJccd() {
        return jccd;
    }

    public void setJccd(String jccd) {
        this.jccd = jccd;
    }

    public String getCqsl() {
        return cqsl;
    }

    public void setCqsl(String cqsl) {
        this.cqsl = cqsl;
    }

    public String getJcbw() {
        return jcbw;
    }

    public void setJcbw(String jcbw) {
        this.jcbw = jcbw;
    }

    public String getJcfs() {
        return jcfs;
    }

    public void setJcfs(String jcfs) {
        this.jcfs = jcfs;
    }

    public String getJclx() {
        return jclx;
    }

    public void setJclx(String jclx) {
        this.jclx = jclx;
    }

    public String getZxbw() {
        return zxbw;
    }

    public void setZxbw(String zxbw) {
        this.zxbw = zxbw;
    }

    public String getSjhd() {
        return sjhd;
    }

    public void setSjhd(String sjhd) {
        this.sjhd = sjhd;
    }

    public String getTchd() {
        return tchd;
    }

    public void setTchd(String tchd) {
        this.tchd = tchd;
    }

    public String getSjqd() {
        return sjqd;
    }

    public void setSjqd(String sjqd) {
        this.sjqd = sjqd;
    }

    public String getMgbh() {
        return mgbh;
    }

    public void setMgbh(String mgbh) {
        this.mgbh = mgbh;
    }

    public String getMgzj() {
        return mgzj;
    }

    public void setMgzj(String mgzj) {
        this.mgzj = mgzj;
    }

    public String getMglx() {
        return mglx;
    }

    public void setMglx(String mglx) {
        this.mglx = mglx;
    }

    public String getMgsl() {
        return mgsl;
    }

    public void setMgsl(String mgsl) {
        this.mgsl = mgsl;
    }

    public String getWydj() {
        return wydj;
    }

    public void setWydj(String wydj) {
        this.wydj = wydj;
    }

    public String getSjmgcd() {
        return sjmgcd;
    }

    public void setSjmgcd(String sjmgcd) {
        this.sjmgcd = sjmgcd;
    }

    public String getSjmgkbl() {
        return sjmgkbl;
    }

    public void setSjmgkbl(String sjmgkbl) {
        this.sjmgkbl = sjmgkbl;
    }

    public String getJzrq() {
        return jzrq;
    }

    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
    }

    public String getShejmgcd() {
        return shejmgcd;
    }

    public void setShejmgcd(String shejmgcd) {
        this.shejmgcd = shejmgcd;
    }

    public String getShejmgkbl() {
        return shejmgkbl;
    }

    public void setShejmgkbl(String shejmgkbl) {
        this.shejmgkbl = shejmgkbl;
    }

    public String getLqqdbg() {
        return lqqdbg;
    }

    public void setLqqdbg(String lqqdbg) {
        this.lqqdbg = lqqdbg;
    }

    public String getSyt() {
        return syt;
    }

    public void setSyt(String syt) {
        this.syt = syt;
    }

    public String getZjbg() {
        return zjbg;
    }

    public void setZjbg(String zjbg) {
        this.zjbg = zjbg;
    }

    public String getZjbgjcyj() {
        return zjbgjcyj;
    }

    public void setZjbgjcyj(String zjbgjcyj) {
        this.zjbgjcyj = zjbgjcyj;
    }

    public String getZjbgjj() {
        return zjbgjj;
    }

    public void setZjbgjj(String zjbgjj) {
        this.zjbgjj = zjbgjj;
    }

    public String getZjbgjy() {
        return zjbgjy;
    }

    public void setZjbgjy(String zjbgjy) {
        this.zjbgjy = zjbgjy;
    }

    public String getJcsjfj() {
        return jcsjfj;
    }

    public void setJcsjfj(String jcsjfj) {
        this.jcsjfj = jcsjfj;
    }

    public String getJcsjfjscsj() {
        return jcsjfjscsj;
    }

    public void setJcsjfjscsj(String jcsjfjscsj) {
        this.jcsjfjscsj = jcsjfjscsj;
    }

    public String getSgdwfbzt() {
        return sgdwfbzt;
    }

    public void setSgdwfbzt(String sgdwfbzt) {
        this.sgdwfbzt = sgdwfbzt;
    }

    public String getLczt() {
        return lczt;
    }

    public void setLczt(String lczt) {
        this.lczt = lczt;
    }

    public String getTjr() {
        return tjr;
    }

    public void setTjr(String tjr) {
        this.tjr = tjr;
    }

    public String getTjbz() {
        return tjbz;
    }

    public void setTjbz(String tjbz) {
        this.tjbz = tjbz;
    }

    public String getTjsj() {
        return tjsj;
    }

    public void setTjsj(String tjsj) {
        this.tjsj = tjsj;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    public String getSpr() {
        return spr;
    }

    public void setSpr(String spr) {
        this.spr = spr;
    }

    public String getSpbz() {
        return spbz;
    }

    public void setSpbz(String spbz) {
        this.spbz = spbz;
    }

    public String getSpsj() {
        return spsj;
    }

    public void setSpsj(String spsj) {
        this.spsj = spsj;
    }

    public String getBjfw() {
        return bjfw;
    }

    public void setBjfw(String bjfw) {
        this.bjfw = bjfw;
    }

    public String getBjsl() {
        return bjsl;
    }

    public void setBjsl(String bjsl) {
        this.bjsl = bjsl;
    }

    public String getBydbh() {
        return bydbh;
    }

    public void setBydbh(String bydbh) {
        this.bydbh = bydbh;
    }

    public String getJcfxsj() {
        return jcfxsj;
    }

    public void setJcfxsj(String jcfxsj) {
        this.jcfxsj = jcfxsj;
    }

    public String getJcfxr() {
        return jcfxr;
    }

    public void setJcfxr(String jcfxr) {
        this.jcfxr = jcfxr;
    }

    public String getJcyj() {
        return jcyj;
    }

    public void setJcyj(String jcyj) {
        this.jcyj = jcyj;
    }

    public String getJcr() {
        return jcr;
    }

    public void setJcr(String jcr) {
        this.jcr = jcr;
    }

    public String getJcbz() {
        return jcbz;
    }

    public void setJcbz(String jcbz) {
        this.jcbz = jcbz;
    }

    public String getJcsj() {
        return jcsj;
    }

    public void setJcsj(String jcsj) {
        this.jcsj = jcsj;
    }

    public String getJcjdzt() {
        return jcjdzt;
    }

    public void setJcjdzt(String jcjdzt) {
        this.jcjdzt = jcjdzt;
    }

    public String getJcjlbz() {
        return jcjlbz;
    }

    public void setJcjlbz(String jcjlbz) {
        this.jcjlbz = jcjlbz;
    }

    public String getJcjl() {
        return jcjl;
    }

    public void setJcjl(String jcjl) {
        this.jcjl = jcjl;
    }

    public String getJcjlfj() {
        return jcjlfj;
    }

    public void setJcjlfj(String jcjlfj) {
        this.jcjlfj = jcjlfj;
    }

    public String getFhjcjg() {
        return fhjcjg;
    }

    public void setFhjcjg(String fhjcjg) {
        this.fhjcjg = fhjcjg;
    }

    public String getFhspyj() {
        return fhspyj;
    }

    public void setFhspyj(String fhspyj) {
        this.fhspyj = fhspyj;
    }

    public String getFhspr() {
        return fhspr;
    }

    public void setFhspr(String fhspr) {
        this.fhspr = fhspr;
    }

    public String getFhspbz() {
        return fhspbz;
    }

    public void setFhspbz(String fhspbz) {
        this.fhspbz = fhspbz;
    }

    public String getFhspsj() {
        return fhspsj;
    }

    public void setFhspsj(String fhspsj) {
        this.fhspsj = fhspsj;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getFbsj() {
        return fbsj;
    }

    public void setFbsj(String fbsj) {
        this.fbsj = fbsj;
    }

    public String getFbr() {
        return fbr;
    }

    public void setFbr(String fbr) {
        this.fbr = fbr;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getHoleMethod() {
        return holeMethod;
    }

    public void setHoleMethod(String holeMethod) {
        this.holeMethod = holeMethod;
    }

    public String getConcreteStrength() {
        return concreteStrength;
    }

    public void setConcreteStrength(String concreteStrength) {
        this.concreteStrength = concreteStrength;
    }

    public String getDesighOrg() {
        return desighOrg;
    }

    public void setDesighOrg(String desighOrg) {
        this.desighOrg = desighOrg;
    }

    public String getDesighOrgName() {
        return desighOrgName;
    }

    public void setDesighOrgName(String desighOrgName) {
        this.desighOrgName = desighOrgName;
    }

    public String getInspectionOrg() {
        return inspectionOrg;
    }

    public void setInspectionOrg(String inspectionOrg) {
        this.inspectionOrg = inspectionOrg;
    }

    public String getInspectionOrgName() {
        return inspectionOrgName;
    }

    public void setInspectionOrgName(String inspectionOrgName) {
        this.inspectionOrgName = inspectionOrgName;
    }

    public String getBuildtype() {
        return buildtype;
    }

    public void setBuildtype(String buildtype) {
        this.buildtype = buildtype;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCreatedatestr() {
        return createdatestr;
    }

    public void setCreatedatestr(String createdatestr) {
        this.createdatestr = createdatestr;
    }

    public String getZjbgjc() {
        return zjbgjc;
    }

    public void setZjbgjc(String zjbgjc) {
        this.zjbgjc = zjbgjc;
    }

    @Override
    public String toString() {
        return "TkyTestInformation{" +
                "id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", xmname='" + xmname + '\'' +
                ", bdid='" + bdid + '\'' +
                ", bdname='" + bdname + '\'' +
                ", gzwid='" + gzwid + '\'' +
                ", gzwname='" + gzwname + '\'' +
                ", gdid='" + gdid + '\'' +
                ", gdname='" + gdname + '\'' +
                ", gcid='" + gcid + '\'' +
                ", gcname='" + gcname + '\'' +
                ", sgdwid='" + sgdwid + '\'' +
                ", sgdwname='" + sgdwname + '\'' +
                ", jldwid='" + jldwid + '\'' +
                ", jldwname='" + jldwname + '\'' +
                ", jcdwid='" + jcdwid + '\'' +
                ", jcdwname='" + jcdwname + '\'' +
                ", jcff='" + jcff + '\'' +
                ", syff='" + syff + '\'' +
                ", lxr='" + lxr + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", byrq='" + byrq + '\'' +
                ", jcsb='" + jcsb + '\'' +
                ", jxrq='" + jxrq + '\'' +
                ", jxzq='" + jxzq + '\'' +
                ", sgfzr='" + sgfzr + '\'' +
                ", jcqslcbm='" + jcqslcbm + '\'' +
                ", jcqslc1='" + jcqslc1 + '\'' +
                ", jcqslc2='" + jcqslc2 + '\'' +
                ", jcjslcbm='" + jcjslcbm + '\'' +
                ", jcjslc1='" + jcjslc1 + '\'' +
                ", jcjslc2='" + jcjslc2 + '\'' +
                ", jccd='" + jccd + '\'' +
                ", cqsl='" + cqsl + '\'' +
                ", jcbw='" + jcbw + '\'' +
                ", jcfs='" + jcfs + '\'' +
                ", jclx='" + jclx + '\'' +
                ", zxbw='" + zxbw + '\'' +
                ", sjhd='" + sjhd + '\'' +
                ", tchd='" + tchd + '\'' +
                ", sjqd='" + sjqd + '\'' +
                ", mgbh='" + mgbh + '\'' +
                ", mgzj='" + mgzj + '\'' +
                ", mglx='" + mglx + '\'' +
                ", mgsl='" + mgsl + '\'' +
                ", wydj='" + wydj + '\'' +
                ", sjmgcd='" + sjmgcd + '\'' +
                ", sjmgkbl='" + sjmgkbl + '\'' +
                ", jzrq='" + jzrq + '\'' +
                ", shejmgcd='" + shejmgcd + '\'' +
                ", shejmgkbl='" + shejmgkbl + '\'' +
                ", lqqdbg='" + lqqdbg + '\'' +
                ", syt='" + syt + '\'' +
                ", zjbg='" + zjbg + '\'' +
                ", zjbgjcyj='" + zjbgjcyj + '\'' +
                ", zjbgjj='" + zjbgjj + '\'' +
                ", zjbgjy='" + zjbgjy + '\'' +
                ", jcsjfj='" + jcsjfj + '\'' +
                ", jcsjfjscsj='" + jcsjfjscsj + '\'' +
                ", sgdwfbzt='" + sgdwfbzt + '\'' +
                ", lczt='" + lczt + '\'' +
                ", tjr='" + tjr + '\'' +
                ", tjbz='" + tjbz + '\'' +
                ", tjsj='" + tjsj + '\'' +
                ", spyj='" + spyj + '\'' +
                ", spr='" + spr + '\'' +
                ", spbz='" + spbz + '\'' +
                ", spsj='" + spsj + '\'' +
                ", bjfw='" + bjfw + '\'' +
                ", bjsl='" + bjsl + '\'' +
                ", bydbh='" + bydbh + '\'' +
                ", jcfxsj='" + jcfxsj + '\'' +
                ", jcfxr='" + jcfxr + '\'' +
                ", jcyj='" + jcyj + '\'' +
                ", jcr='" + jcr + '\'' +
                ", jcbz='" + jcbz + '\'' +
                ", jcsj='" + jcsj + '\'' +
                ", jcjdzt='" + jcjdzt + '\'' +
                ", jcjlbz='" + jcjlbz + '\'' +
                ", jcjl='" + jcjl + '\'' +
                ", jcjlfj='" + jcjlfj + '\'' +
                ", fhjcjg='" + fhjcjg + '\'' +
                ", fhspyj='" + fhspyj + '\'' +
                ", fhspr='" + fhspr + '\'' +
                ", fhspbz='" + fhspbz + '\'' +
                ", fhspsj='" + fhspsj + '\'' +
                ", zt='" + zt + '\'' +
                ", fbsj='" + fbsj + '\'' +
                ", fbr='" + fbr + '\'' +
                ", testType='" + testType + '\'' +
                ", holeMethod='" + holeMethod + '\'' +
                ", concreteStrength='" + concreteStrength + '\'' +
                ", desighOrg='" + desighOrg + '\'' +
                ", desighOrgName='" + desighOrgName + '\'' +
                ", inspectionOrg='" + inspectionOrg + '\'' +
                ", inspectionOrgName='" + inspectionOrgName + '\'' +
                ", buildtype='" + buildtype + '\'' +
                ", createdate='" + createdate + '\'' +
                ", createdatestr='" + createdatestr + '\'' +
                ", zjbgjc='" + zjbgjc + '\'' +
                '}';
    }
}