package com.main.com.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.common.com.base.WDActivity;
import com.common.com.util.Constant;
import com.common.com.util.DataCleanManager;
import com.common.com.util.StorageCustomerInfo02Util;
import com.common.com.util.StorageCustomerInfoUtil;
import com.common.com.util.tusi.ViewUtils;
import com.google.android.material.tabs.TabLayout;
import com.main.com.R;
import com.main.com.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 聂俊鹏
 * @date 2019/1/8 14:22
 * qq:2241659414
 */
@Route(path = Constant.ACTIVITY_URL_SET)
public class SetActivity extends WDActivity  {
    @BindView(R2.id.relat_layout)
    RelativeLayout relat_layout;
    @BindView(R2.id.clne_btn)
    Button clne_btn;
    @BindView(R2.id.shoushi_pwd)
    Button shoushi_pwd;
    @BindView(R2.id.tab_layout)
    TabLayout tab_layout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        relat_layout.setBackgroundColor(Color.parseColor("#C69AEC52"));

        //清除缓存
        clne_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCleanManager.clearAllCache(context);
                ViewUtils.makeToast(context, "缓存清除成功", 500);


            }
        });
        //手势密码
        shoushi_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Frist_shoushi_pwdActivity.class));
            }
        });

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition()==0){
                        ViewUtils.makeToast(context,"这是左边的",500);
                    }else if (tab.getPosition()==1){
                        ViewUtils.makeToast(context,"这是右边的",500);
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint("WrongConstant")
    @OnClick(R2.id.logout_btn)
    public void logout(){

        //清除存储终端信息的缓存数据
        StorageCustomerInfoUtil.clearKey(this);
        StorageCustomerInfo02Util.clearKey(this);
        //Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
        //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
        ARouter.getInstance().build(Constant.ACTIVITY_URL_LOGIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();

    }


}
