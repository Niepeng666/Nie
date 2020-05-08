package com.common.com.util.tusi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.common.com.R;
import com.common.com.util.ActivityManager;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.Timer;
import java.util.TimerTask;


public class ViewUtils {
    /**
     * 自定义吐司
     */
    public static void makeToast01(Context context, String text, int duration) {
        Toast result = new Toast(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) layout.findViewById(R.id.toast_text);
        textView.setText(text);
        result.setView(layout);
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);
        result.show();
    }
    public static Dialog showChoseDialog01(Context context, boolean isCanceledOnTouchOutside,
                                           String msg, String cancelDes, String confrimDes,
                                           final OnChoseDialogClickCallback onChoseDialogClickCallback) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.chose_dialoga);
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        Button confirmBt = (Button) dialog.findViewById(R.id.bt_cancelPlan);
        Button cancelBt = (Button) dialog.findViewById(R.id.bt_suspendCancel);
        TextView tv_dialogTitle = (TextView) dialog.findViewById(R.id.tv_dialogTitle);
        tv_dialogTitle.setText(msg);
        confirmBt.setText(confrimDes);
        cancelBt.setText(cancelDes);

        cancelBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChoseDialogClickCallback != null) {
                    dialog.dismiss();
                    onChoseDialogClickCallback.clickCancel();
                } else {
                    dialog.dismiss();
                }
            }
        });
        confirmBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChoseDialogClickCallback != null) {
                    dialog.dismiss();
                    onChoseDialogClickCallback.clickOk();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        return dialog;
    }
    /**
     * 自定义吐司
     *
     * @param context  上下文
     * @param text     吐司内容
     * @param duration 显示时长
     */
    public static void makeToast(Context context,
                                 String text, int duration) {
        /*Toast result = new Toast(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.toast_layout, null);
		TextView textView = (TextView) layout.findViewById(R.id.toast_text);
		textView.setText(text);
		result.setView(layout);
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);
		result.show();*/

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_toast_layout, null);
        TextView chapterNameTV = (TextView) view.findViewById(R.id.chapterName);
        chapterNameTV.setText(text);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    /**
     * 显示文字的 Toast,以及Intent跳转
     *
     * @param context
     * @param text
     * @param duration
     * @param intentClassName 不为空表示不关闭当前
     * @param intentType      为""表示不跳转
     */
    public static void makeToast2(final Activity context,
                                  CharSequence text, int duration,
                                  final Class<?> intentClassName, final String intentType) {
        if (!TextUtils.isEmpty(text)) {
            Toast result = new Toast(context);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.toast_layout, null);
            TextView textView = (TextView) layout.findViewById(R.id.toast_text);
            textView.setText(text);

            result.setView(layout);
            result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            result.setDuration(duration);
            result.show();
        }


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {

                if (null == intentClassName) {
                    context.finish();
                    return;
                }
                if (!(null == intentClassName) && !"".equals(intentType)) {
                    Intent intent = context
                            .getPackageManager()
                            .getLaunchIntentForPackage(context.getPackageName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                    ActivityManager.exit2();
                    return;
                }
            }
        }, 2000);
    }




    /**
     * 得到自定义的progressDialog
     *
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg, boolean isCancel) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
      CircleProgressBar img = (CircleProgressBar) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
       img.setColorSchemeResources(Color.RED);

        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.MyProgressDialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(isCancel);// 可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;

    }


    /**
     * 选择提示框
     *
     * @param context                    上下文
     * @param isCanceledOnTouchOutside   是否可以点击其他区域
     * @param msg                        提示内容
     * @param onChoseDialogClickCallback 点击事件的回调
     * @param cancelVisibale             取消按键是否可视
     * @return
     */
    public static Dialog showChoseDialog(Context context, boolean isCanceledOnTouchOutside, String msg, int cancelVisibale,
                                         final OnChoseDialogClickCallback onChoseDialogClickCallback) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.chose_dialog);
        dialog.setCanceledOnTouchOutside(false);
        Button confirmBt = (Button) dialog.findViewById(R.id.left_bt);
        Button cancleBt = (Button) dialog.findViewById(R.id.right_bt);
        dialog.findViewById(R.id.verticalbars_iv).setVisibility(cancelVisibale);
        cancleBt.setVisibility(cancelVisibale);
        ((TextView) dialog.findViewById(R.id.title_text)).setText(msg);
        cancleBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChoseDialogClickCallback != null) {
                    dialog.dismiss();
                    onChoseDialogClickCallback.clickCancel();
                } else {
                    dialog.dismiss();
                }
            }
        });
        confirmBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChoseDialogClickCallback != null) {
                    dialog.dismiss();
                    onChoseDialogClickCallback.clickOk();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        return dialog;
    }

    /**
     * 选择提示框
     *
     * @param context                    上下文
     * @param isCanceledOnTouchOutside   是否可以点击其他区域
     * @param msg                        提示内容
     * @param onChoseDialogClickCallback 点击事件的回调
     * @param cancelDes 取消按钮文字设置
     * @param confrimDes 确定按钮文字设置
     * @return
     */
    public static Dialog showChoseDialog02(Context context, boolean isCanceledOnTouchOutside, String msg, String cancelDes, String confrimDes,
                                           final OnChoseDialogClickCallback onChoseDialogClickCallback) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.chose_dialog);
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

        Button confirmBt = (Button) dialog.findViewById(R.id.left_bt);
        Button cancleBt = (Button) dialog.findViewById(R.id.right_bt);
        ((TextView) dialog.findViewById(R.id.title_text)).setText(msg);
        confirmBt.setText(confrimDes);
        cancleBt.setText(cancelDes);

        cancleBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChoseDialogClickCallback != null) {
                    dialog.dismiss();
                    onChoseDialogClickCallback.clickCancel();
                } else {
                    dialog.dismiss();
                }
            }
        });
        confirmBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChoseDialogClickCallback != null) {
                    dialog.dismiss();
                    onChoseDialogClickCallback.clickOk();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        return dialog;
    }

    public interface OnChoseDialogClickCallback {
        public void clickOk();

        public void clickCancel();
    }

    public static Dialog showHintDialog(Context context, String msg) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.hint_dialog);
        dialog.setCanceledOnTouchOutside(true);
        ImageButton ib_close = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tv_hint_des = (TextView) dialog.findViewById(R.id.tv_hint_des);
        tv_hint_des.setText(msg);
        ib_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }



    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }




    public static Dialog showValidatePhoto(Context context) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.dialog_show_validate_photo);
        dialog.findViewById(R.id.ll_showValidatePhoto).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }


    public static Dialog showCvvPhoto(Context context) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.dialog_show_cvv_photo);
        dialog.findViewById(R.id.ll_showValidatePhoto).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }



    public static Dialog showTipDialog(Context context,String title,String content) {
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.mainpage_dialog);
        TextView  tv_dialogTitle = (TextView) dialog.findViewById(R.id.tv_dialogTitle);
        TextView  tv_content = (TextView) dialog.findViewById(R.id.tv_content);
        Button bt_known = (Button) dialog.findViewById(R.id.bt_known);

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)){
            tv_dialogTitle.setText(title);
            tv_content.setText(content);
        }

        dialog.setCanceledOnTouchOutside(true);
        bt_known.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog ShowCardAddDialog(final Context context, final OnShowCardAddOkListener onShowCardAddOkListener){
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.showcard_dialog);
        final EditText et_number = (EditText) dialog.findViewById(R.id.et_number);
        Button bt_buy = (Button) dialog.findViewById(R.id.bt_buy);
        bt_buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onShowCardAddOkListener!=null){
                    if (!TextUtils.isEmpty(et_number.getText().toString())){
                        long i = Long.parseLong(et_number.getText().toString());
                        if (i>=5 && i<=500){
                            onShowCardAddOkListener.clickOk(i);
                            dialog.dismiss();
                        }else{
                            ViewUtils.makeToast(context,"最低10个起购,最高500个",1200);
                        }
                    }else{
                        ViewUtils.makeToast(context,"请输入数量",1200);
                    }
                }else{
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        return dialog;
    }
    public interface OnShowCardAddOkListener {
        public void clickOk(long string);
    }

    public static Dialog ExitShowY(final Context context, final ExitOnLinstener exitOnLinstener){
        final Dialog dialog = new Dialog(context, R.style.MyProgressDialog);
        dialog.setContentView(R.layout.exitapp);
        Button bt_exit = (Button) dialog.findViewById(R.id.bt_exit);
        Button bt_diss = (Button) dialog.findViewById(R.id.bt_diss);
        bt_diss.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        bt_exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exitOnLinstener!=null){
                    exitOnLinstener.clickOk();
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        return dialog;
    }
    public interface ExitOnLinstener {
        public void clickOk();
    }

}
