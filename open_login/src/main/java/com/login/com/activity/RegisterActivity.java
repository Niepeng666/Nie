package com.login.com.activity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.core.DataCall;
import com.common.com.core.WDActivity;
import com.common.com.core.exception.ApiException;
import com.common.com.util.Md5;
import com.login.com.R2;
import com.login.com.presenter.RegisterPresenter;
import com.common.com.util.Constant;
import com.common.com.util.UIUtils;
import com.login.com.R;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = Constant.ACTIVITY_URL_REGISTER)
public class RegisterActivity extends WDActivity {

    RegisterPresenter requestPresenter;
    @BindView(R2.id.login_mobile)
    EditText mMobile;

    @BindView(R2.id.login_pas)
    EditText mPas;

    @Override
    protected int getLayoutId() {
        return R.layout.chose_dialog;
    }

    @Override
    protected void destoryData() {
        requestPresenter.unBind();
    }

    @Override
    protected void initView() {
        requestPresenter = new RegisterPresenter(new RegisterCall());
    }

    @OnClick(R2.id.register_btn)
    public void login() {
        String m = mMobile.getText().toString();
        String p = mPas.getText().toString();
        if (TextUtils.isEmpty(m)) {
            UIUtils.showToastSafe("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(p)) {
            UIUtils.showToastSafe("请输入密码");
            return;
        }
        mLoadDialog.show();
        requestPresenter.reqeust(m, Md5.GetMD5Code(p));
    }

    private boolean pasVisibile = false;

    @OnClick(R2.id.login_pas_eye)
    public void eyePas() {
        if (pasVisibile) {//密码显示，则隐藏
            mPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
            pasVisibile = false;
        } else {//密码隐藏则显示
            mPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            pasVisibile = true;
        }
    }

    @OnClick(R2.id.login_text)
    public void remPas() {
        finish();
    }

    /**
     * @author bianhua
     * @date 2018/12/28 10:44 AM
     * 注册
     */
    class RegisterCall implements DataCall {

        @Override
        public void success(Object result, Object... args) {
            mLoadDialog.cancel();
            UIUtils.showToastSafe("注册成功，请登录");
            finish();
        }

        @Override
        public void fail(ApiException e, Object... arg) {
            mLoadDialog.cancel();
            UIUtils.showToastSafe(e.getCode() + " " + e.getDisplayMessage());
        }
    }
}
