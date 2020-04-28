package com.main.com.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.common.com.core.WDActivity;
import com.common.com.util.ViewUtils;
import com.common.com.util.shoushi.GestureLockViewGroup;
import com.common.com.util.shoushi.listener.GestureEventListener;
import com.common.com.util.shoushi.listener.GesturePasswordSettingListener;
import com.main.com.R;
import com.main.com.R2;

import butterknife.BindView;

public class Frist_shoushi_pwdActivity extends WDActivity {
    @BindView(R2.id.shoushimima)
    GestureLockViewGroup shoushimima;
@BindView(R2.id.text_tishi)
    TextView text_tishi;
    private String inputGesture;
    private boolean isReset = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_frist_shoushi_pwd;
    }

    @Override
    protected void initView() {
        gestureEventListener();
        gesturePasswordSettingListener();
        gestureRetryLimitListener();
    }

    private void gestureRetryLimitListener() {

    }

    private void gesturePasswordSettingListener() {
        shoushimima.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public void onSuccess(String gestureLock) {
                if(!TextUtils.isEmpty(gestureLock)&&gestureLock.length()<4){
                    ViewUtils.makeToast(context,"请输入4位以上的手势密码",1000);
                    resetGesturePattern();
                    return;
                }
                if (TextUtils.isEmpty(inputGesture)) {
                    inputGesture = gestureLock;
                    text_tishi.setText("请确认手势密码");
                    resetGesturePattern();
                    return;
                }
                if (gestureLock.equals(inputGesture)) {
                    text_tishi.setText("");
                    submitGestureLock(gestureLock);
                }else {
                    inputGesture=null;
                    text_tishi.setText("请重新设置手势密码");
                    ViewUtils.makeToast(context,"两次密码设置不一致",1000);
                    resetGesturePattern();
                }
            }
        });
    }
    private void gestureEventListener() {
        shoushimima.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (!matched) {
                    text_tishi.setTextColor(Color.RED);
                    text_tishi.setText("手势密码错误");
                } else {
                    if (isReset) {
                        isReset = false;
                        Toast.makeText(context, "清除成功!", Toast.LENGTH_SHORT).show();
                        resetGesturePattern();
                    } else {
                        text_tishi.setTextColor(Color.WHITE);
                        text_tishi.setText("手势密码正确");
                    }
                }
            }
        });
    }

    private void resetGesturePattern() {
        shoushimima.removePassword();
        shoushimima.resetView();
    }
    private void submitGestureLock(String gestureLock) {

        ViewUtils.makeToast(context,"手势密码设置成功！"+gestureLock,500);
        finish();
    }

    @Override
    protected void destoryData() {

    }
}
