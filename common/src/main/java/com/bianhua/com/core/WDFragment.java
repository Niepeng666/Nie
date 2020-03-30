package com.bianhua.com.core;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bianhua.com.R;
import com.bianhua.com.core.db.DaoMaster;
import com.bianhua.com.core.db.UserInfoDao;
import com.bianhua.com.util.LogUtils;
import com.google.gson.Gson;

import com.bianhua.com.bean.UserInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class WDFragment extends Fragment {

	public Gson mGson = new Gson();
	public SharedPreferences mShare = WDApplication.getShare();

	private Unbinder unbinder;
	public UserInfo LOGIN_USER;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(getLayoutId(),container,false);
		unbinder = ButterKnife.bind(this,view);

		initView();
		//调用沉浸式
		setWindowStatusBarColor(getActivity(), R.color.transparent);
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}
	/**
	 * 设置页面名字 用于友盟统计
	 */
	public abstract String getPageName();
	/**
	 * 设置layoutId
	 * @return
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

	/**
	 * @param mActivity 传送Activity的
	 */
	public void intent(Class mActivity) {
		Intent intent = new Intent(getContext(), mActivity);
		startActivity(intent);
	}

	/**
	 * @param path 传送Activity的
	 */
	public void intentByRouter(String path) {
		ARouter.getInstance().build(path)
				.navigation(getContext());
	}

	/**
	 * @param mActivity 传送Activity的
	 * @param bundle
	 */
	public void intent(Class mActivity, Bundle bundle) {
		Intent intent = new Intent(getContext(), mActivity);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * @param path 传送Activity的
	 * @param bundle
	 */
	public void intentByRouter(String path, Bundle bundle) {
		ARouter.getInstance().build(path)
				.with(bundle)
				.navigation(getContext());
	}

	/**
	 * 沉浸式状态栏
	 */
	public void setWindowStatusBarColor(Activity activity, int colorResId) {
		try {
			if (Build.VERSION.SDK_INT >= 23) {
				Window window = activity.getWindow();
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
				window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(ContextCompat.getColor(activity, colorResId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
