package com.main.com.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.common.com.base.WDFragment;
import com.common.com.util.BitmapManage;
import com.common.com.util.ChooseDialog;
import com.common.com.util.Constant;
import com.common.com.util.PermissionsUtils;
import com.common.com.util.save_picture.Save_tupian;
import com.main.com.R;
import com.main.com.R2;

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
    @BindView(R2.id.text_diao)
    TextView text_diao;

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
                   imageView.setImageBitmap(photo);
                    String Strpicture = BitmapManage.BitmapToString(photo);//把biemap类型的图片转成String类型
                    Log.e("TAG",photo+"-------"+Strpicture);
                    Save_tupian.baocun(photo,context);//保存图片
                }
                break;
            //调用相册后返回
            case 2:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    imageView.setImageURI(uri);
                }
                break;

            case 3:
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    imageView.setImageBitmap(image);
                }
                break;
        }
    }



}
