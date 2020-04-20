package com.main.com.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.common.com.core.WDActivity;
import com.common.com.util.ActivityManager;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.fragment.CircleFragment;
import com.main.com.fragment.HomeFragment;
import com.main.com.fragment.MeFragment;
import com.main.com.fragment.SendFragment;
import com.main.com.fragment.ShareFragment;
import com.main.com.model.SendMessageCommunitor;
import com.common.com.util.Constant;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;

import butterknife.BindView;

@Route(path = Constant.ACTIVITY_URL_MAIN)
public class MainActivity extends WDActivity implements RadioGroup.OnCheckedChangeListener ,SendMessageCommunitor{

    @BindView(R2.id.bottom_menu)
    RadioGroup bottomMenu;
    @BindView(R2.id.home_btn)
    RadioButton home_btn;
    @BindView(R2.id.circle_btn)
    RadioButton circle_btn;
    @BindView(R2.id.cart_btn)
    RadioButton cart_btn;
    @BindView(R2.id.list_btn)
    RadioButton list_btn;
    @BindView(R2.id.me_btn)
    RadioButton me_btn;
    HomeFragment homeFragment;      //首页
    CircleFragment circleFragment;
    MeFragment meFragment;          //我的
     SendFragment sendFragment;
     ShareFragment shareFragment;
    SendMessageCommunitor sendMessage;
    public static MainActivity instance;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    protected void initView() {
        instance=this;
        bottomMenu.setOnCheckedChangeListener(this);
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
        meFragment = new MeFragment();
        sendFragment = new SendFragment();
        shareFragment = new ShareFragment();
        currentFragment = homeFragment;
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container, homeFragment)
                .show(homeFragment).commit();
    }
    @Override
    protected void destoryData() {

    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.home_btn) {
            showFragment(homeFragment);
        } else if (checkedId == R.id.circle_btn) {
            showFragment(circleFragment);
        }else if (checkedId == R.id.me_btn){
            showFragment(meFragment);
        }else if (checkedId==R.id.cart_btn){
            showFragment(sendFragment);
        }else if (checkedId== R.id.list_btn){
            showFragment(shareFragment);
        }
    }

    Fragment currentFragment;

    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.container, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }
    private long firstime;
    @Override
    public void sendMessage(String msg) {
        if (msg.equals("mainselecet1")){
            home_btn.setChecked(false);
            circle_btn.setChecked(false);
            cart_btn.setChecked(false);
            list_btn.setChecked(true);
            me_btn.setChecked(false);
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /** 设置双击退出 */
            long secondtime = System.currentTimeMillis();
            if (secondtime - firstime > 3000) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                firstime = System.currentTimeMillis();
                return true;
            } else {
                finish();
                ActivityManager.exit();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);

    }

}
