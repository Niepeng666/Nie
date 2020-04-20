package com.login.com.activity;
import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.bean.UserInfo;
import com.common.com.core.DataCall;
import com.common.com.core.WDActivity;
import com.common.com.core.db.DaoMaster;
import com.common.com.core.db.UserInfoDao;
import com.common.com.core.exception.ApiException;
import com.common.com.util.ActivityManager;
import com.common.com.util.Md5;
import com.common.com.util.PermissionsUtils;
import com.login.com.R;
import com.login.com.R2;
import com.login.com.presenter.LoginPresenter;
import com.common.com.util.Constant;
import com.common.com.util.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 *                      彼岸花
 *                      登录
 */
@Route(path = Constant.ACTIVITY_URL_LOGIN)
public class LoginActivity extends WDActivity {
    LoginPresenter requestPresenter;
    @BindView(R2.id.login_mobile)
    EditText mMobile;
    @BindView(R2.id.login_pas)
    EditText mPas;
    @BindView(R2.id.login_rem_pas)
    CheckBox mRemPas;
    private boolean pwd_checked;
    private long firstime;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;//布局
    }
    @Override
    protected void destoryData() {
        requestPresenter.unBind();

    }

    @Override
    protected void initView() {
        //开启基础权限
        PermissionsUtils.requestPermissionselect(this);
        //设置activity的启动模式


        //创建P层
        requestPresenter = new LoginPresenter(new LoginCall());



    }

    @OnClick(R2.id.login_btn)
    public void login() {

        intentByRouter(Constant.ACTIVITY_URL_MAIN);
        finish();


        String m = mMobile.getText().toString();
        String p = mPas.getText().toString();



        requestPresenter.reqeust(m, Md5.GetMD5Code(p));
    }

    /**
     * 重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void cancelLoadDialog() {
        super.cancelLoadDialog();
        requestPresenter.cancelRequest();
    }
    //设置记住密码
    @OnClick(R2.id.login_rem_pas)
    public void remPas() {


    }
    //密码的显示和隐藏
    private boolean pasVisibile = false;
    @OnClick(R2.id.login_pas_eye)
    public void eyePas() {
        if (pasVisibile) {//true密码显示，false隐藏
            mPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        } else {
            mPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }
    //去注册
    @OnClick(R2.id.register_text)
    public void register() {
        intentByRouter(Constant.ACTIVITY_URL_REGISTER);
    }


    /**
     * @author bianhua
     * @date 2018/12/28 10:44 AM
     *  实现成功、失败
     *  UserInfo 登录成功后传入的商户信息
     */
    class LoginCall implements DataCall<UserInfo> {

        @Override
        public void success(UserInfo result, Object... args) {

            result.setStatus(1);//设置登录状态，保存到数据库
            UserInfoDao userInfoDao = DaoMaster.newDevSession(getBaseContext(), UserInfoDao.TABLENAME).getUserInfoDao();
            userInfoDao.insertOrReplace(result);//保存用户数据
            pwd_checked = mRemPas.isChecked();



            intentByRouter(Constant.ACTIVITY_URL_MAIN);
            finish();
        }

        @Override
        public void fail(ApiException e, Object... args) {

            ViewUtils.makeToast(context,"欢迎来到我的world",1500);
        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            /** 设置双击退出 */
//            long secondtime = System.currentTimeMillis();
//            if (secondtime - firstime > 3000) {
//                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
//
//                firstime = System.currentTimeMillis();
//                return true;
//            } else {
//                finish();
//                ActivityManager.exit();
//                System.exit(0);
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }

}
