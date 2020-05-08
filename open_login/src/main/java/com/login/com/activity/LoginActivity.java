package com.login.com.activity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.base.WDActivity;
import com.common.com.util.PermissionsUtils;
import com.common.com.util.updata.DialogUpdata;
import com.login.com.R;
import com.login.com.R2;
import com.common.com.util.Constant;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
/**
 *                      彼岸花
 *                      登录
 */
@Route(path = Constant.ACTIVITY_URL_LOGIN)
public class LoginActivity extends WDActivity implements View.OnClickListener {
    @BindView(R2.id.login_mobile)
    EditText mMobile;
    @BindView(R2.id.login_pas)
    EditText mPas;
    @BindView(R2.id.login_rem_pas)
    CheckBox mRemPas;
    @BindView(R2.id.login_btn)
    Button login_btn;
    @BindView(R2.id.login_pas_eye)
    ImageView login_pas_eye;
    private boolean pasVisibile = false;
   private int kk=101;
    private String flag="FQZ";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;//布局
    }
    @Override
    protected void initView() {
        //开启基础权限
        PermissionsUtils.requestPermissionselect(this);
        login_btn.setOnClickListener(this);
    }
    //点击事件
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {   //登录
            String replace = Constant.VERSION.replace(".", "");
            int ii = Integer.parseInt(replace);
            if (ii>=kk){
                intentByRouter(Constant.ACTIVITY_URL_MAIN);
            }else{
                if ("QZ".equals(flag)){
                    //强制更新
                    DialogUpdata.getInstance("退出","更新版本",1,"QZ").show(getSupportFragmentManager(),"updata");
                }else{
                    //非强制更新
                    DialogUpdata.getInstance("忽略更新","更新版本",1,"FQZ").show(getSupportFragmentManager(),"updata");//1版本号  FQZ：强制更新的标示
                }
            }
        }else if (view.getId()==R.id.login_pas_eye){         //密码的显示和隐藏

            if (pasVisibile) {//true密码显示，false隐藏
                mPas.setTransformationMethod(PasswordTransformationMethod.getInstance());
                pasVisibile = false;
            } else {
                mPas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                pasVisibile = true;
            }

        }

    }
}
