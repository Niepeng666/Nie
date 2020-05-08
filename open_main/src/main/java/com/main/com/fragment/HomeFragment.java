package com.main.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.common.com.util.PermissionsUtils;
import com.common.com.util.ViewUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.main.com.R;
import com.main.com.R2;
import com.main.com.activity.ChoujiangActivity;
import com.main.com.activity.MainActivity;
import com.main.com.activity.WebViewActivity;
import com.common.com.core.WDFragment;
import com.main.com.model.SendMessageCommunitor;
import com.recker.flybanner.FlyBanner;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.android.Intents;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import org.json.JSONArray;

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
    @BindView(R2.id.edit_text)
    EditText edit_text;
    @BindView(R2.id.text_zhi)
    TextView text_zhi;
    private AlphaAnimation enterAnimation;
    private AlphaAnimation exitAnimation;
    private TextView text_tishi;
    private TextView text_tiao;
    private Controller build;

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

                Intent intent = new Intent(context, CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
                config.setShowAlbum(true);//是否显示相册
                config.setShowFlashLight(true);//是否显示闪光灯
                startActivityForResult(intent, 100);

            }
        });
        //二维码
        text_ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = edit_text.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    imageView_ewm.setImageBitmap(generateBitmap("您没有输入内容！",400,400));
                    return;
                }
                imageView_ewm.setImageBitmap(generateBitmap(trim,400,400));
            }
        });
        text_zhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                build.show();
            }
        });


        yindao();//新手指引
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


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

        if (!hidden){

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                ViewUtils.makeToast(context,"扫描结果为:"+content,500);
            }
        }
    }

    private void yindao() {
        enterAnimation = new AlphaAnimation(0f, 0.5f);//进场动画
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);
        exitAnimation = new AlphaAnimation(0.5f, 0f);//退场动画
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);
        build = NewbieGuide.with(this)
                .setLabel("guide1")
                //.setShowCounts(3)//控制次数
                .alwaysShow(true)//总是显示，调试时可以打开
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(flyBanner)
                        .setLayoutRes(R.layout.layout_yindao)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                text_tishi = (TextView) view.findViewById(R.id.text_tishi);
                                text_tishi.setText("这是banner轮播图");
                                text_tiao = (TextView) view.findViewById(R.id.text_tiao);
                                text_tiao.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        build.remove();
                                    }
                                });
                            }
                        })
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画
                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(textUrl)

                        .setLayoutRes(R.layout.layout_yindao)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                text_tishi = (TextView) view.findViewById(R.id.text_tishi);
                                text_tishi.setText("点击我可以跳转到webView页面");
                            }
                        })
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画

                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(text)
                        .setLayoutRes(R.layout.layout_yindao)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                text_tishi = (TextView) view.findViewById(R.id.text_tishi);
                                text_tishi.setText("我这是要跳到别的fragment里呢");
                            }
                        })
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画

                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(text_cj)
                        .setLayoutRes(R.layout.layout_yindao)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                text_tishi = (TextView) view.findViewById(R.id.text_tishi);
                                text_tishi.setText("幸运轮盘要不要走一波！");

                            }
                        })
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画

                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(text_sys)
                        .setLayoutRes(R.layout.layout_yindao)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                text_tishi = (TextView) view.findViewById(R.id.text_tishi);
                                text_tishi.setText("可以让你原形毕露！");
                            }
                        })
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画

                )
                .addGuidePage(GuidePage.newInstance()
                        .addHighLight(text_ewm)
                        .setLayoutRes(R.layout.layout_yindao)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view) {
                                text_tishi = (TextView) view.findViewById(R.id.text_tishi);
                                text_tishi.setText("扫呀，扫呀，快扫呀!");
                            }
                        })
                        .setEnterAnimation(enterAnimation)//进入动画
                        .setExitAnimation(exitAnimation)//退出动画

                )

                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {

                    }

                    @Override
                    public void onRemoved(Controller controller) {//引导层消失，就可以干想干的事了
                        ViewUtils.makeToast(context, "终于完了，好麻烦呀！", 500);
                    }
                })
                .build();

    }


}
