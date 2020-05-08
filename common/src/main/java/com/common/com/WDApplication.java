package com.common.com;

import android.app.Application;
import android.content.Context;
import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;



/**
 * @name: MyApplication
 * @remark:
 */
public class WDApplication extends Application {


    /**
     * context 全局唯一的上下文
     */
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);//阿里路由初始化
        Fresco.initialize(this);//图片加载框架初始化
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    public static Context getContext() {
        return context;
    }
}