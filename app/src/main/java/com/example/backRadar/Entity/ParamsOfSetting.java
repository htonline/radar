package com.example.backRadar.Entity;

import java.io.Serializable;
import java.util.Arrays;

public class ParamsOfSetting implements Serializable {
    private static final long serialVersionUID = 10086L;
    String paramsName;

    public String getParamsName() {
        return paramsName;
    }

    public void setParamsName(String paramsName) {
        this.paramsName = paramsName;
    }
    float[] xRaw;
    float[] bRaw;

    public float[] getxRaw() {
        return xRaw;
    }

    public void setxRaw(float[] xRaw) {
        this.xRaw = xRaw;
    }

    public float[] getbRaw() {
        return bRaw;
    }

    public void setbRaw(float[] bRaw) {
        this.bRaw = bRaw;
    }

    int frequency;
    int timeWindow;
    int delay;
    boolean saveRaw;
    boolean gain;
    float gainCoe;
    int[] gainData;
    int[] gainData1024;
    boolean debackgrd;
    int[] backgrdData;
    int[] backgrdData1024;
    boolean filter;
    int filterNum;
    float filterstart;
    float filterstop;
    public int[] getGainData1024() {
        return gainData1024;
    }

    public void setGainData1024(int[] gainData1024) {
        this.gainData1024 = gainData1024;
    }

    public int[] getBackgrdData1024() {
        return backgrdData1024;
    }

    public void setBackgrdData1024(int[] backgrdData1024) {
        this.backgrdData1024 = backgrdData1024;
    }



    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getTimeWindow() {
        return timeWindow;
    }

    public void setTimeWindow(int timeWindow) {
        this.timeWindow = timeWindow;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public boolean isSaveRaw() {
        return saveRaw;
    }

    public void setSaveRaw(boolean saveRaw) {
        this.saveRaw = saveRaw;
    }

    public boolean isGain() {
        return gain;
    }

    public void setGain(boolean gain) {
        this.gain = gain;
    }

    public float getGainCoe() {
        return gainCoe;
    }

    public void setGainCoe(float gainCoe) {
        this.gainCoe = gainCoe;
    }

    public int[] getGainData() {
        return gainData;
    }

    public void setGainData(int[] gainData) {
        this.gainData = gainData;
    }

    public boolean isDebackgrd() {
        return debackgrd;
    }

    public void setDebackgrd(boolean debackgrd) {
        this.debackgrd = debackgrd;
    }

    public int[] getBackgrdData() {
        return backgrdData;
    }

    public void setBackgrdData(int[] backgrdData) {
        this.backgrdData = backgrdData;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public int getFilterNum() {
        return filterNum;
    }

    public void setFilterNum(int filterNum) {
        this.filterNum = filterNum;
    }

    public float getFilterstart() {
        return filterstart;
    }

    public void setFilterstart(float filterstart) {
        this.filterstart = filterstart;
    }

    public float getFilterstop() {
        return filterstop;
    }

    public void setFilterstop(float filterstop) {
        this.filterstop = filterstop;
    }

    @Override
    public String toString() {
        return "ParamsOfSetting{" +
                "paramsName='" + paramsName + '\'' +
                ", xRaw=" + Arrays.toString(xRaw) +
                ", bRaw=" + Arrays.toString(bRaw) +
                ", frequency=" + frequency +
                ", timeWindow=" + timeWindow +
                ", delay=" + delay +
                ", saveRaw=" + saveRaw +
                ", gain=" + gain +
                ", gainCoe=" + gainCoe +
                ", gainData=" + Arrays.toString(gainData) +
                ", gainData1024=" + Arrays.toString(gainData1024) +
                ", debackgrd=" + debackgrd +
                ", backgrdData=" + Arrays.toString(backgrdData) +
                ", backgrdData1024=" + Arrays.toString(backgrdData1024) +
                ", filter=" + filter +
                ", filterNum=" + filterNum +
                ", filterstart=" + filterstart +
                ", filterstop=" + filterstop +
                '}';
    }
}
