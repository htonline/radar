package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;


public class TestInformation implements Serializable {

    @SerializedName("test_infor_id")
    private Long testInforId;//id

    @SerializedName("test_id")
    private String testId;//报检号

    @SerializedName("test_time")
    private Timestamp testTime;//申请检测时间

    @SerializedName("test_starting_distance")
    private String testStartingDistance;//待检区段起始里程

    @SerializedName("test_ending_distance")
    private String testEndingDistance;//待检区段结束里程

    @SerializedName("test_length")
    private String testLength;//待检区段长度

    @SerializedName("wall_rock_type")
    private String wallRockType;//围岩类型

    @SerializedName("support_tickness")
    private String supportTickness;//初支厚度

    @SerializedName("separation_distance")
    private String separationDistance;//间距(m/榀)

    @SerializedName("mesh_distance")
    private String meshDistance;//钢筋网间距

    @SerializedName("annular_bar_distance")
    private String annularBarDistance;//环向钢筋间距

    @SerializedName("reinfor_prt_tickness")
    private String reinforPrtTickness;//钢筋保护厚度

    @SerializedName("sec_line_arch_tickness")
    private String secLineArchTickness;//二次衬砌厚度-拱部

    @SerializedName("sec_line_wall_tickness")
    private String secLineWallTickness;//二次衬砌厚度-边墙

    @SerializedName("sec_line_inver_arch_tickness")
    private String secLineInverArchTickness;//二次衬砌厚度-仰拱

    @SerializedName("sec_line_filer_tickness")
    private String secLineFilerTickness;//二次衬砌厚度-填充

    @SerializedName("project_name")
    private String projectName;//项目名称

    @SerializedName("section_name")
    private String sectionName;//标段名称

    @SerializedName("tunnel_name")
    private String tunnelName;//隧道名称

    @SerializedName("worksite_name")
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

}