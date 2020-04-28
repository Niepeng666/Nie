package com.main.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.common.com.util.PermissionsUtils;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.activity.ChoujiangActivity;
import com.main.com.activity.WebViewActivity;
import com.common.com.core.WDFragment;
import com.main.com.model.SendMessageCommunitor;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author 聂俊鹏
 * @date 首页
 * qq:2241659414
 */
public class HomeFragment extends WDFragment {
    @BindView(R2.id.text)
    TextView text;
    @BindView(R2.id.text_url)
    TextView textUrl;
    @BindView(R2.id.flybanner)
   FlyBanner flyBanner;
    @BindView(R2.id.text_cj)
    TextView text_cj;
    SendMessageCommunitor sendMessage;
    @BindView(R2.id.text_sys)
    TextView text_sys;
    @BindView(R2.id.text_ewm)
    TextView text_ewm;
    @BindView(R2.id.imageView_ewm)
    ImageView imageView_ewm;
    @Override
    public String getPageName() {
        return "首页Fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_main;
    }

    @Override
    protected void initView() {
        //沉浸式状态栏
      //  ImmersionBar.with(this).barAlpha(0.1f).init();

      ArrayList<Integer> listBanner=new ArrayList();
        listBanner.add(R.drawable.abc01);
        listBanner.add(R.drawable.abc02);

        flyBanner.setImages(listBanner);

        initData();//点击事件
    }

    private void initData() {

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage.sendMessage("mainselecet1");

            }
        });

        textUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WebViewActivity.class)
                        .putExtra("title","我的保单")
                        .putExtra("url","http://jucaibao.llyzf.cn:6442/lly-posp-proxy/jcb/helpCenter.html"));
            }
        });
        //轮盘
        text_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ChoujiangActivity.class));
            }
        });


        //banner的点击事件
        flyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        //扫一扫
        text_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PermissionsUtils.CAMERA(context)){
                    return;
                }
//                ZxingConfig config = new ZxingConfig();//
//                config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）//
//                config.setPlayBeep(true);//是否播放提示音//
//                config.setShake(true);//是否震动//
//                ZxingConfig config = new ZxingConfig();
//               config.setShowAlbum(true);//是否显示相册//
//                 config.setShowFlashLight(true);//是否显示闪光灯//
               //  intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
//                 startActivityForResult(intent, REQUEST_CODE_SCAN);




            }
        });
        //二维码
        text_ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // imageView_ewm.setImageBitmap(generateBitmap("zeze",600,600));
            }
        });

    }

//    private Bitmap generateBitmap(String content, int width, int height) {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        Map<EncodeHintType, String> hints = new HashMap<>();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        try {
//            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//            int[] pixels = new int[width * height];
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    if (encode.get(j, i)) {
//                        pixels[i * width + j] = 0x00000000;
//                    } else {
//                        pixels[i * width + j] = 0xffffffff;
//                    }
//                }
//            }
//            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sendMessage = (SendMessageCommunitor) activity;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("TAG",hidden+"");
        if (!hidden){
            Log.e("TAG","走方法了!");
        }
    }

}
