package com.example.helper;

import com.example.entity.FILTERPARA;

import org.junit.Test;

import dalvik.annotation.TestTarget;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Dataprocess {
    public static final int GENELENGTH=64;
    static char m_mode;//探测方法：0：地质雷达；1：地震
    static float  m_timelength;//探测时间长度（时间窗大小）
    //如果  m_mode =0  雷达采集，单位为ns（纳秒）
    //如果  m_mode =1  地震采集，单位为us（微秒）
    static int m_samplelength;//采样长度（样点数）
    static float m_sampletime;//采样时间间隔
    //如果  m_mode =0  雷达采集，单位为ns（纳秒）
    //如果  m_mode =1  地震采集，单位为us（微秒）

    static float m_fmax;//最大采样频率（考虑到正负两部分，针对最大正频率设计）
    //如果  m_mode =0  雷达采集，单位应该为MHz，
    //如果  m_mode =1  地震采集，单位应该为KHz，
    static float m_samplefre;//采样信号的频率间隔
    //如果  m_mode =0  雷达采集，单位应该为MHz，
    //如果  m_mode =1  地震采集，单位应该为KHz，
	/*
	计算方法：
	m_sampletime =  m_timelength / m_samplelength
	m_fmax =  1/(sampletime/1000.0)/2;
	m_samplefre =  m_fmax /(m_samplelength/2)

	*/
    static float[] m_gene;//一维滤波因子
    static float[] m_zhenfupu;//滤波因子的振幅谱

    short m_background[];//背景噪声
    float m_gainnoise[];//标准增益噪声曲线，数在0-1之间    注意：背景噪声和标准增益噪声曲线相乘才是需要去除的背景噪声
    static int   m_background_tracenum;  //计算背景噪声的道数
    static short m_dynamic_background;//注意在修改采样点参数和时间窗是应该初始化为零。同时背景噪声初始化为0

    float m_gaincurve[];//标准增益曲线，数在0-1之间
    static float   m_gainconst;//增益常量     标准增益曲线乘上增益常量才是最后的增益曲线
    int   m_gainnum;  //自动控制增点数量

    static boolean m_iffilter;//一维滤波


    public static boolean m_ifgain;//增益处理
    public static boolean m_ifback;//背景去噪
    boolean m_GainKey;//是否增益开关打开2019-8

    public static FILTERPARA m_filterP = new FILTERPARA();

    public static void calassist(){
        m_sampletime =  m_timelength / m_samplelength;
        m_fmax = (float) (1/(m_sampletime/1000.0)/2);
        m_samplefre =  m_fmax /(m_samplelength/2);
    }
    public static void init(){
        m_gene = new float[GENELENGTH];
        m_zhenfupu = new float[GENELENGTH];
        m_mode = 0;//探测方法：0：地质雷达；1：地震
        m_timelength=50;//探测时间长度（时间窗大小）
        //如果  m_mode =0  雷达采集，单位为ns（纳秒）
        //如果  m_mode =1  地震采集，单位为us（微秒）
        m_samplelength=512;//采样长度（样点数）
//        m_iffilter=false;//一维滤波
//        m_ifgain=false;//增益处理
//        m_ifback=false;//背景去噪
        m_gainconst = (float) 1.0;
        m_dynamic_background = 0;
        m_background_tracenum = 10;
        m_filterP.setM_high_f(1500);
        m_filterP.setM_low_f(100);
        m_filterP.setM_mode(1);
        calassist();
    }

    public float[] calm_gainnoiseb(short[] backgrd){
        float backDataOne[]=new float[512];
        for(int i =0;i<512;i++){
            backDataOne[i]= (float) (backgrd[i]/17.0);
        }
        return backDataOne;
    }

    public short[] backgroundRemoveProcess(int num, short datar[],float m_gainnoiseb[], short m_backgroundb[]){


        for (int i=0;i<num;i++)
        {
            datar[i] = (short)(datar[i] - m_gainnoiseb[i] * m_backgroundb[i]);// *  m_background_tracenumb / m_dynamic_backgroundb);
        }
        return datar;
    }

    public void clearbackground(){
        m_dynamic_background = 0;
        for(int i =0;i<m_samplelength;i++) m_background[i] = 0;
    }

    public short[] getdynamicbackground(short[] data){
        if( m_dynamic_background < m_background_tracenum )//如果背景道数不足
        {
            int ssum = m_dynamic_background + 1;
            for (int kk=0; kk<m_samplelength; kk++){
                m_background[kk] = (short) ((m_background[kk]*m_dynamic_background + data[kk])/ssum);
            }
            m_dynamic_background = (short) ssum;
            return m_background;
        }
        else//背景道数足够
        {
            int ssum = m_background_tracenum + 1;
//	  float rf1=(float)m_background_tracenum/ssum;
//	  float rf2=(float)1.0/ssum;
            for (int kk=0; kk<m_samplelength; kk++)
                m_background[kk] = (short) ((m_background[kk]*m_background_tracenum + data[kk])/ssum);
            return m_background;
        }

    }


    public short[] gainProcess(short rawData[],int gainData[],float coeGain,int const_numberofData){
        float gainDataOne[]=new float[const_numberofData];
        for(int i =0;i<const_numberofData;i++){
            gainDataOne[i]= (float) (gainData[i]/1.0);
        }

        for (int i=0;i<const_numberofData;i++){
            {
                int fd = (int) (rawData[i]* gainDataOne[i]*coeGain);
                if(fd >= 32767) fd = 32767;
                if(fd <= -32768) fd = -32767;
                rawData[i] = (short) fd;
            }
        }
        return rawData;
    }
    //功能：计算一维滤波因子和计算滤波因子振幅谱
    /*
       输入参数：m_high_f:高频截止
                 m_low_f:低频截止
                 m_fremode:滤波器类型，0-低通   1-高通   2-带通    3-带馅   注意低通和高通只参考“低频截止”参数
    */
    public static void calgene(float m_high_f, float m_low_f, int m_fremode){
        //the FFT operater will be write into the temp file zhj.flp
        //先对序列进行付氏变换，将振幅归一化再进行反付氏变换得到进行褶积
        //的序列，如果是带阻滤波用1-归一化的振幅谱再进行反付氏变换得到褶积

        float wh,wl;//高低圆周频率
        float pi=(float)3.1415926;

//	float fmax = 1/(sampleinte/1000.0)/2;
        float h[] = new float[GENELENGTH];
        float d[] = new float[GENELENGTH];
        wh = pi*m_high_f/m_fmax;
        wl = pi*m_low_f/m_fmax;

        float fm;
        int i;
        for(i=0 ; i<GENELENGTH ; i++)
        {
            fm = pi*(i-GENELENGTH/2);
            switch(m_fremode)
            {
                case 0:
                    if(fm == 0)
                        h[i] = (wl)/pi;
                    else
                        h[i] = (float) (sin((i-GENELENGTH/2)*wl) /fm);
                    break;
                case 1:
                    if(fm == 0)
                        h[i] = (pi-wl)/pi;
                    else
                        h[i] = (float) ((sin((i-GENELENGTH/2)*pi) - sin((i-GENELENGTH/2)*wl))/fm);
                    break;
                case 2:
                    if(fm == 0)
                        h[i] = (wh-wl)/pi;
                    else
                        h[i] = (float) ((sin((i-GENELENGTH/2)*wh) - sin((i-GENELENGTH/2)*wl))/fm);
                    break;
                case 3:
                    if(fm == 0)
                        h[i] = (wl+pi-wh)/pi;
                    else
                        h[i] = (float) ((sin((i-GENELENGTH/2)*wl)+ sin((i-GENELENGTH/2)*pi) - sin((i-GENELENGTH/2)*wh))/fm);

                    break;
            }
            d[i] = (float) (0.5 - 0.5*cos(2*pi*i/GENELENGTH));
            m_gene[i] = h[i]*d[i];
            //m_flt_gene[i] = d[i];
        }
        Complex xldata[]= new Complex[GENELENGTH];
        for(i=0;i<GENELENGTH;i++)
        {
            xldata[i] = new Complex();
            xldata[i].setRe(m_gene[i]);
            xldata[i].setIm(0);
        }
        Complex prgs_xldata[] = FFT.fft(xldata);
        for(i=0;i<GENELENGTH;i++)
        {
            m_zhenfupu[i]= (float) sqrt(prgs_xldata[i].getRe()*prgs_xldata[i].getRe()+prgs_xldata[i].getIm()*prgs_xldata[i].getIm());
        }

    }

    public static int corf(int dat_length, float data[], int op_length, float cordata[]){
        float dat[];
        float a;
        dat=new float[dat_length+op_length-1];
        for(int i=0;i<dat_length+op_length-1;i++)
        {
            dat[i]=0;
            for(int j=0;j<op_length;j++)
            {
                a=0;
                if(((i-j)>=0)&&((i-j)<dat_length))
                    a=data[i-j];
                //	          dat[i]=cordata[op_length-1-j]*a+dat[i];
                dat[i]=cordata[j]*a+dat[i];
            }
        }
        for(int i=op_length/2;i<dat_length+op_length/2;i++)
            data[i-(op_length/2)]=dat[i];
		/*      for(i=0;i<dat_length;i++)
		data[i]=dat[i];
	*/
        return (0);
    }

    public static boolean isM_iffilter() {
        return m_iffilter;
    }

    public static void setM_iffilter(boolean m_iffilter) {
        Dataprocess.m_iffilter = m_iffilter;
    }

    public static short[] dodataprocess(short rdata[]) {

//        if(m_ifback)
//        {
//            backgroundRemoveProcess(m_samplelength, rdata,m_gainnoise, m_background, m_background_tracenum, m_dynamic_background);
//        }

        //数据处理第二步。滤波处理
            calgene(m_filterP.getM_high_f(),m_filterP.getM_low_f(),m_filterP.getM_mode());
            float tmpdf[];
            tmpdf = new float[m_samplelength];
            for(int kk=0;kk<m_samplelength ; kk++)
            {
                tmpdf[kk] = (float)rdata[kk];
            }

            corf(m_samplelength,tmpdf,GENELENGTH,m_gene);

            floattoint(tmpdf,m_samplelength,1);
            for(int kk=0;kk<m_samplelength; kk++)
            {
                rdata[kk] = (short)tmpdf[kk];
            }
            return rdata;

    }

    public static void floattoint(float data[], int num, int sig){
        float max;
        int i;
        max=0;
        if(sig==0)
        {
            for(i=0;i<num-20;i++)
            {
                if(max<(int)abs(data[i]))
                    max=(int)abs(data[i]);
            }
            float  ff;
            ff=32760/max;
            for(i=20;i<num-20;i++)
            {
                data[i]=data[i]*ff;
            }
            for(i=0;i<20;i++)
            {
                data[i]=data[i]*ff;
                if(data[i]>32760)
                    data[i] = 32760;
                if(data[i]<-32760)
                    data[i] = -32760;
            }
            for(i=num-20;i<num;i++)
            {
                data[i]=data[i]*ff;
                if(data[i]>32760)
                    data[i] = 32760;
                if(data[i]<-32760)
                    data[i] = -32760;
            }


        }
        else if(sig==1)
        {
            for(i=0;i<num;i++)
            {
                if(data[i]>=32767)
                    data[i]=32767;
                else if(data[i]<=-32768)
                    data[i]=-32768;
            }

        }
    }


}
