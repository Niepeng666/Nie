package com.common.com.base;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.common.com.util.Constant;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.ButterKnife;

/**
 * @author 聂俊鹏
 * @date 2018/12/29 14:00
 * qq:2241659414
 */
public abstract class WDActivity extends AppCompatActivity {
    public Activity context;
    /**
     * 记录处于前台的Activity
     */
    private static WDActivity mForegroundActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        //沉浸式状态栏
        ImmersionBar.with(this).barAlpha(0.1f).init();

        setContentView(getLayoutId());

        ARouter.getInstance().inject(this);

        ButterKnife.bind(this);//ButterKnife，绑定布局

        initView();

        //全局设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置layoutId
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化视图
     */
    protected abstract void initView();




    /**
     * @param path 传送Activity的
     */
    public void intentByRouter(String path) {
        ARouter.getInstance().build(path)
                .navigation(this);
    }
    /**
     * @param mActivity 传送Activity的
     * @param bundle
     */
    public void intent(Class mActivity, Bundle bundle) {
        Intent intent = new Intent(this, mActivity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    @Override
    protected void onStart() {
        super.onStart();
        mForegroundActivity = this;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static WDActivity getForegroundActivity() {
        return mForegroundActivity;
    }




}
