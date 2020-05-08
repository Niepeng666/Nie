package com.common.com.util.updata;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.common.com.R;
import com.common.com.util.CommonUtils;
import com.common.com.util.Constant;
import com.common.com.util.DeviceUtils;
import com.common.com.util.PermissionsUtils;
import com.common.com.util.tusi.ViewUtils;

import java.io.File;


public class DialogUpdata  extends DialogFragment {
        private Activity context;
    private LinearLayout ll_updateing;
    private ProgressBar pro_bar;
    private TextView tv_progress;
    private LinearLayout line_button;
    private TextView buttonUpdata1;
    private TextView buttonUpdata2;

    private File mFile;
    private String button2,button1,mark;
    private int KK;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (pro_bar == null) {
                    return;
                }
                pro_bar.setProgress(Integer.valueOf((Integer) msg.obj));
                tv_progress.setText("正在更新..." + Integer.valueOf((Integer) msg.obj) + "%");
            } else if (msg.what == 2) {
                if (buttonUpdata1 == null) {
                    return;
                }
                mFile = new File((String) msg.obj);
                buttonUpdata1.setText("安装");
                buttonUpdata2.setVisibility(View.GONE);
                line_button.setVisibility(View.VISIBLE);
                ll_updateing.setVisibility(View.GONE);

                installApk();
            } else if (msg.what == 0) {
                buttonUpdata1.setText("请重新登录下载");
            }
        }
    };
    private ImageView imageView_finsh;

    //使用静态类传值
    public static DialogUpdata getInstance(String button1, String button2,int kk,String mark) {
        DialogUpdata dialogUpdata = new DialogUpdata();
        Bundle bundle = new Bundle();
        bundle.putString("button1", button1);
        bundle.putString("button2", button2);
        bundle.putInt("haha",kk);
        bundle.putString("mark",mark);
        dialogUpdata.setArguments(bundle);
        return dialogUpdata;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.updata_layout, container);
        initView(view);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            button1 = getArguments().getString("button1");
            button2 = getArguments().getString("button2");
            mark = getArguments().getString("mark");
            KK = getArguments().getInt("KK");
        }
        context=getActivity();
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MyProgressDialog);
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setLayout((int) (DeviceUtils.getScreenWidth(context) * 0.70), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttp3Util.cancelTag(this);
    }

    private void initView(View view) {
        getDialog().setCanceledOnTouchOutside(false);
        ll_updateing =(LinearLayout)view.findViewById(R.id.ll_updateing);
        pro_bar = (ProgressBar)view.findViewById(R.id.pro_bar);
        tv_progress =(TextView)view.findViewById(R.id.tv_progress);
        line_button =(LinearLayout)view.findViewById(R.id.line_button);
        buttonUpdata1 =(TextView)view.findViewById(R.id.button_updata1);
        buttonUpdata2 =(TextView)view.findViewById(R.id.button_updata2);
        imageView_finsh =(ImageView)view.findViewById(R.id.imageView_finsh);
        buttonUpdata1.setText(button1);
        buttonUpdata2.setText(button2);
        initData();
    }



    private void initData() {
        buttonUpdata1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFile!=null){
                    installApk();
                    return;
                }
                if ("QZ".equals(mark)){
                    context.finish();
                }else{
                    ARouter.getInstance().build(Constant.ACTIVITY_URL_MAIN)
                            .navigation(context);
                }
            }
        });
        buttonUpdata2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_updateing.setVisibility(View.VISIBLE);
                line_button.setVisibility(View.GONE);
                downFile(Constant.DOWNLOAD_APK);
            }
        });
        imageView_finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }
    //下载apk
    private void downFile(String downloadApk) {
        ViewUtils.makeToast(getActivity(),""+CommonUtils.getConnectedType(context),500);
        if (CommonUtils.getConnectedType(context) == -1) {
            ViewUtils.makeToast(context,
                    "没有可用网络！", 500);
            return;
        }
        OkHttp3Util.download(context, downloadApk, "ftyj.apk", new OkHttp3Util.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = String.valueOf(file.getAbsolutePath());
                mHandler.sendMessage(msg);
            }
            @Override
            public void onDownloading(int progress) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = Integer.valueOf(progress);
                mHandler.sendMessage(msg);
            }
            @Override
            public void onDownloadFailed(Exception e) {
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        });
    }
        //安装apk
    private void installApk() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, "com.bianhua.com.fileprovider", mFile);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PermissionsUtils.INSTALL_PACKAGES(context);//可以安装未知应用的权限
            }
            startActivity(install);

        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
            startActivity(install);
        }
    }


}
