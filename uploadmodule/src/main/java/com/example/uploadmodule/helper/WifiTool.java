package com.example.uploadmodule.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class WifiTool {
    // 上下文Context对象
    private Context mContext;
    // WifiManager对象
    private WifiManager mWifiManager;
    private ConnectivityManager mConnectivityManager;





    public WifiTool(Context mContext) {
        this.mContext = mContext;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mConnectivityManager= (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    /**
     * 判断手机是否连接在Wifi上
     */
    public boolean isConnectWifi() {
        // 获取ConnectivityManager对象
        ConnectivityManager conMgr = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取NetworkInfo对象
        NetworkInfo info = conMgr.getActiveNetworkInfo();
        // 获取连接的方式为wifi
        State wifi = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();

        if (info != null && info.isAvailable() && wifi == State.CONNECTED)

        {
            return true;
        } else {
            return false;
        }

    }
    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }


    // 打开GPRS



    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }
    /**
     * 获取当前手机所连接的wifi信息
     */
    public WifiInfo getCurrentWifiInfo() {
        return mWifiManager.getConnectionInfo();
    }

    /**
     * 添加一个网络并连接
     * 传入参数：WIFI发生配置类WifiConfiguration
     */
    public boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        return mWifiManager.enableNetwork(wcgID, true);
    }

    /**
     * 搜索附近的热点信息，并返回所有热点为信息的SSID集合数据
     */
    public List<String> getScanSSIDsResult() {
        // 扫描的热点数据
        List<ScanResult> resultList;
        // 开始扫描热点
        mWifiManager.startScan();
        resultList = mWifiManager.getScanResults();
        ArrayList<String> ssids = new ArrayList<String>();
        if (resultList != null) {
            for (ScanResult scan : resultList) {
                ssids.add(scan.SSID);// 遍历数据，取得ssid数据集
            }
        }
        return ssids;
    }

    /**
     * 得到手机搜索到的ssid集合，从中判断出设备的ssid（dssid）
     */
    public List<String> accordSsid() {
        List<String> s = getScanSSIDsResult();
        List<String> result = new ArrayList<String>();
        for (String str : s) {
            if (checkDssid(str)) {
                result.add(str);
            }
        }
        return result;
    }

    /**
     * 检测指定ssid是不是匹配的ssid，目前支持GBELL，TOP,后续可添加。
     *
     * @param ssid
     * @return
     */
    private boolean checkDssid(String ssid) {
        if (!TextUtils.isEmpty(ssid)) {
            //这里条件根据自己的需求来判断，我这里就是随便写的一个条件
            if (ssid.length() > 8 ) {
                return true;
            }
            else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean OpenWifi() {
        boolean bRet = true;
        if(!mWifiManager.isWifiEnabled()) {
            bRet = mWifiManager.setWifiEnabled(true);
        }
        return bRet;
    }



    /**
     * 连接wifi
     * 参数：wifi的ssid及wifi的密码
     */
    public boolean connectWifiTest(final String ssid, final String pwd) {
        boolean isSuccess = false;
        boolean flag = false;
        boolean flag2=false;
        if (mWifiManager.getWifiState()==1) {
            OpenWifi();
        }

        boolean addSucess=false;
        while (!flag2) {
            int a=mWifiManager.getWifiState();
            if (a==3) {
                flag2=true;
                addSucess = addNetwork(CreateWifiInfo(ssid, pwd, 3));
            }
        }



        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        mWifiManager.disconnect();
        if (addSucess) {
            while (!flag && !isSuccess) {
                //getCurrentWifiInfo()使用的前提是已经连接上wifi，所以前边睡了8s，等它连接上然后再判断
                String currSSID = getCurrentWifiInfo().getSSID();
                currSSID=getCurrentWifiInfo().getSSID();
                if (currSSID != null)
                    currSSID = currSSID.replace("\"", "");
//                int currIp = getCurrentWifiInfo().getIpAddress();
//                Log.d("----------",currIp+"" );
                if (currSSID != null && currSSID.equals(ssid)) {
                    //这里还需要做优化处理，增强结果判断
                    isSuccess = true;
                } else {
                    flag = true;
                }
            }
        }
        return isSuccess;

    }

    /**
     * 创建WifiConfiguration对象
     * 分为三种情况：1没有密码;2用wep加密;3用wpa加密
     * @param SSID
     * @param Password
     * @param Type
     * @return
     */
    public WifiConfiguration CreateWifiInfo(String SSID, String Password,
                                            int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1) // WIFICIPHER_NOPASS
        {
            config.wepKeys[0] = "";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 2) // WIFICIPHER_WEP
        {
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers
                    .set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        }
        if (Type == 3) // WIFICIPHER_WPA
        {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.TKIP);
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers
                    .set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    private WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager
                .getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }


}
