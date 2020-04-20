package com.main.com.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.common.com.core.WDFragment;
import com.common.com.util.ChooseDialog;
import com.common.com.util.Constant;
import com.common.com.util.PermissionsUtils;
import com.main.com.R;
import com.main.com.R2;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author 聂俊鹏
 * @date 个人页面
 * qq:2241659414
 */
public class MeFragment extends WDFragment {
    @BindView(R2.id.text)
    TextView text;
    @BindView(R2.id.imageView)
    ImageView imageView;
    @BindView(R2.id.text_color)
    TextView text_color;
    @BindView(R2.id.text_diao)
    TextView text_diao;

    @Override
    public String getPageName() {
        return "我的Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_me;
    }

    @Override
    protected void initView() {

        initData();//点击事件



    }
    private void initData() {
        //设置页面
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentByRouter(Constant.ACTIVITY_URL_SET);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PermissionsUtils.CAMERA(context)){
                    return;
                }
                initPicture();// 相机、相册
            }
        });
        //自定义取色盘
        text_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                builder.setTitle("自定义取色器");
                builder.setPreferenceName("MyColorPickerDialog");
                builder.setFlagView(new CustomFlag(context, R.layout.layout_flag));
                builder.setPositiveButton("确定", new ColorListener() {
                    @Override
                    public void onColorSelected(ColorEnvelope colorEnvelope) {
//                        TextView textView = findViewById(R.id.textView);
//                        textView.setText("#" + colorEnvelope.getHtmlCode());
//
//                        LinearLayout linearLayout = findViewById(R.id.linearLayout);
                        text_color.setBackgroundColor(colorEnvelope.getColor());

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        //调用系统的分享
        text_diao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");
                startActivity(Intent.createChooser(textIntent, "分享"));
            }
        });

    }

    private void initPicture() {
        new ChooseDialog(context,"调用相机","调用相册").setOnChooseDialogListener(new ChooseDialog.OnChooseDialogListener() {
            @Override
            public void onCancle() {

            }

            @Override
            public void onSelect0() {
                //todo 使用拍照获取图片
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onSelect1() {
                /// todo 使用相册图片
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            }
        }).show();
    }
    //activityforresult返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            // 调用相机后返回
            case 1:
                if (resultCode == RESULT_OK) {
                    final Bitmap photo = intent.getParcelableExtra("data");
                    //也可以进行一些保存、压缩等操作后上传  保存
                    String path = saveImage("userHeader", photo);//两个参数一个是图片名字、一个是图片
                    File file = new File(path);
                   imageView.setImageBitmap(photo);

                    //上传
//                    Uri data = intent.getData();
//                    String image = new BitmapManage().getCompressedImgPath(Next_mainActivity.this, UriToPathUtil.getRealFilePath(Next_mainActivity.this, data));//压缩后的图片
//                    uploadImage(image);



                }
                break;
            //调用相册后返回
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    imageView.setImageURI(uri);
                  //cropPhoto(uri);//裁剪图片
                }
                break;
            //调用剪裁后返回
            case 3:
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    imageView.setImageBitmap(image);


                    //也可以进行一些保存、压缩等操作后上传
                    String path = saveImage("userHeader", image);
                    File file = new File(path);
                    /*
                     *这里可以做上传文件的额操作
                     */
                }
                break;
        }
    }
    /**
     * 保存图片到本地
     *
     * @param name
     * @param bmp
     * @return
     */
    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        Log.e("djkwa",appDir+"");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
}
