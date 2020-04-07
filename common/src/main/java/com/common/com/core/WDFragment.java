package com.common.com.core;

import com.alibaba.android.arouter.launcher.ARouter;

import com.common.com.R;
import com.common.com.bean.UserInfo;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import android.app.Activity;
import android.content.Context;
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

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class WDFragment extends Fragment {
	public Context context;
	public Gson mGson = new Gson();
	public SharedPreferences mShare = WDApplication.getShare();

	private Unbinder unbinder;
	public UserInfo LOGIN_USER;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//沉浸式状态栏
		ImmersionBar.with(this).barAlpha(0.1f).init();
		View view = inflater.inflate(getLayoutId(),container,false);
		unbinder = ButterKnife.bind(this,view);
		context=getActivity();
		initView();
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




}
