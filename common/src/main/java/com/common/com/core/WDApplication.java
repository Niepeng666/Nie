package com.common.com.core;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.core.content.FileProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.util.List;


/**
 * @name: MyApplication
 * @remark:
 */
public class WDApplication extends Application {
    /**
     * 主线程ID
     */
    private static int mMainThreadId = -1;
    /**
     * 主线程ID
     */
    private static Thread mMainThread;
    /**
     * 主线程Handler
     */
    private static Handler mMainThreadHandler;
    /**
     * 主线程Looper
     */
    private static Looper mMainLooper;

    private static int MAX_MEM = 30 * ByteConstants.MB;

    /**
     * context 全局唯一的上下文
     */
    private static Context context;

    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        sharedPreferences = getSharedPreferences("share.xml", MODE_PRIVATE);
        ARouter.openLog();     // Print log
        ARouter.openDebug();
        ARouter.init(this);//阿里路由初始化

        Fresco.initialize(this,getConfigureCaches(this));//图片加载框架初始化



    }
    //安装apk
    public static void install(Context context, String filePath,boolean force) {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        } else {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        if(force){

            System.exit(0);

        }
    }
    //安装apk
    private void installApk(File file) {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri apkUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                apkUri = FileProvider.getUriForFile(context
                        , "com.bianhua.com.FileProvider"
                        , file);
            } else {
                apkUri = Uri.fromFile(file);
            }
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            // 查询所有符合 intent 跳转目标应用类型的应用，注意此方法必须放置在 setDataAndType 方法之后
            List<ResolveInfo> resolveLists = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            // 然后全部授权
            for (ResolveInfo resolveInfo : resolveLists){
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, apkUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    public static SharedPreferences getShare() {
        return sharedPreferences;
    }


    public static Context getContext() {
        return context;
    }

    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /**
     * 获取主线程
     */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /**
     * 获取主线程的looper
     */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    private ImagePipelineConfig getConfigureCaches(Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEM,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEM,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams);
        return builder.build();
    }

}