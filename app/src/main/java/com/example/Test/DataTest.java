package com.example.Test;

import com.example.helper.Dataprocess;


import org.junit.Test;

public class DataTest {
    public static void main(String[] args) {
        short test[]=new short[512];
        for (int i =0;i<512;i++){
            test[i]=2;
        }
        Dataprocess.init();
        Dataprocess.setM_iffilter(true);
        Dataprocess.m_filterP.setM_high_f(150);
        short ls[]=Dataprocess.dodataprocess(test);

    }
}
