package com.example;


import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.multidex.MultiDex;

import com.example.tinker.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.entry.DefaultApplicationLike;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.loader.shareutil.ShareConstants;

@DefaultLifeCycle(application = "com.example.SampleApplication", flags = ShareConstants.TINKER_ENABLE_ALL, loadVerifyFlag = false)
public class SampleApplicationLike extends DefaultApplicationLike {
    private static final String TAG = "Tinker.SampleApplicationLike";

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                                 long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 这里实现SDK初始化，


    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);

        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.setUpgradeRetryEnable(true);
        TinkerManager.installTinker(this);
        Tinker.with(getApplication());
    }
}


