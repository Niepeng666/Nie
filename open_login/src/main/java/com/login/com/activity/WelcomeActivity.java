package com.login.com.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.core.WDActivity;
import com.common.com.util.Constant;
import com.login.com.R;
import com.login.com.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author bianhua
 * @date 2018/12/29 16:29
 * qq:2241659414
 */
@Route(path = Constant.ACTIVITY_URL_WELCOME)
public class WelcomeActivity extends WDActivity {

    @BindView(R2.id.seek_text)
    TextView seekText;

    private int count = 3;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            count--;
            seekText.setText("跳过"+count+"s");
            if (count==0){
                if (LOGIN_USER!=null){
                    intentByRouter(Constant.ACTIVITY_URL_MAIN);//跳转到主页面
                }else{
                    intentByRouter(Constant.ACTIVITY_URL_LOGIN);//跳转到登录页
                }
                finish();
            }else{//消息不能复用，必须新建
               handler.sendEmptyMessageDelayed(1,1000);
            }
        }
    };

    @Override
    protected void initView() {
        seekText.setText("跳过"+count+"s");
        handler.sendEmptyMessageDelayed(1,1000);


    }

    @OnClick(R2.id.seek_text)
    public void seek(){
        handler.removeMessages(1);
        if (LOGIN_USER!=null){
            intentByRouter(Constant.ACTIVITY_URL_MAIN);
        }else{
            intentByRouter(Constant.ACTIVITY_URL_LOGIN);
        }
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
//        return R2.layout.activity_welcome;
    }

    @Override
    protected void destoryData() {

    }
}
