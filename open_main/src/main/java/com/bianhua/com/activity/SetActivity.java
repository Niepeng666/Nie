package com.bianhua.com.activity;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bianhua.com.R;
import com.bianhua.com.R2;
import com.bianhua.com.core.WDActivity;
import com.bianhua.com.core.db.DaoMaster;
import com.bianhua.com.core.db.UserInfoDao;
import com.bianhua.com.util.Constant;

import butterknife.OnClick;

/**
 * @author 聂俊鹏
 * @date 2019/1/8 14:22
 * qq:2241659414
 */
@Route(path = Constant.ACTIVITY_URL_SET)
public class SetActivity extends WDActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void destoryData() {

    }

    @SuppressLint("WrongConstant")
    @OnClick(R2.id.logout_btn)
    public void logout(){
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        userInfoDao.delete(LOGIN_USER);
        //Intent清除栈FLAG_ACTIVITY_CLEAR_TASK会把当前栈内所有Activity清空；
        //FLAG_ACTIVITY_NEW_TASK配合使用，才能完成跳转
        ARouter.getInstance().build(Constant.ACTIVITY_URL_LOGIN)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .navigation();
    }
}
