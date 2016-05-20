package com.buglai.rxrss;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.buglai.rxrss.utils.AppContextUtil;
import com.buglai.rxrss.utils.disk.DiskLruCacheHelper;
import com.buglai.rxrss.utils.imageloader.UniversalAndroidImageLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by buglai on 5/18/16.
 */
public class App extends MultiDexApplication {

    public List<Activity> mActivityList = new ArrayList<>();
    public static DiskLruCacheHelper cacheHelper;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UniversalAndroidImageLoader.init(this);
        AppContextUtil.init(this);
        try {
            cacheHelper = new DiskLruCacheHelper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addActivity(Activity activity) {
        mActivityList.add(activity);
    }

}
