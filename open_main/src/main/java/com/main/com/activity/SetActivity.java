package com.main.com.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.core.WDActivity;
import com.common.com.core.db.DaoMaster;
import com.common.com.core.db.UserInfoDao;
import com.common.com.util.Constant;
import com.common.com.util.utils.StorageCustomerInfo02Util;
import com.common.com.util.utils.StorageCustomerInfoUtil;
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
public class SetActivity extends WDActivity {
    @BindView(R2.id.relat_layout)
    RelativeLayout relat_layout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initView() {
        relat_layout.setBackgroundColor(Color.parseColor("#C69AEC52"));
    }

    @Override
    protected void destoryData() {

    }

    @SuppressLint("WrongConstant")
    @OnClick(R2.id.logout_btn)
    public void logout(){
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        userInfoDao.delete(LOGIN_USER);

        //清除存储终端信息的缓存数据
        StorageCustomerInfoUtil.clearKey(this);
        StorageCustomerInfo02Util.clearKey(this);
        intentByRouter(Constant.ACTIVITY_URL_LOGIN);

    }
}
