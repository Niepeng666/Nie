package com.common.com.base;

import com.alibaba.android.arouter.launcher.ARouter;

import com.common.com.WDApplication;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class WDFragment extends Fragment {
	public Activity context;
	private Unbinder unbinder;

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
	 * 设置layoutId
	 * @return
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();
	/**
	 * @param path 传送Activity
	 */
	public void intentByRouter(String path) {
		ARouter.getInstance().build(path)
				.navigation(getContext());
	}







}
