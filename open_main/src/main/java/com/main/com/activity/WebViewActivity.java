package com.main.com.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Patterns;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.com.core.WDActivity;
import com.main.com.R;


public class WebViewActivity extends WDActivity {
    private WebView webview;
    String url;
    private ProgressBar pg1;
    private ImageView imageFinsh;
    private TextView textTitle;
    private String title;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {

        boolean qubie1 = getIntent().getBooleanExtra("qubie", false);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        setContentView(R.layout.activity_web_view);
        webview = (WebView) findViewById(R.id.webview);
        pg1 = (ProgressBar) findViewById(R.id.progressBar1);
        imageFinsh =(ImageView)findViewById(R.id.image_finsh);
        textTitle =(TextView)findViewById(R.id.text_title);
        textTitle.setText(title);
        //设置WebView属性，能够执行Javascript脚本
        //webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        // webview.loadUrl("http://fans.lanqt.com/index.php?
        // g=App&m=Index&a=xyk001");
        //判断是否设置成百分比缩放
        if ("我的保单".equals(title)||"常见问题".equals(title)){

        }else{
            webview.setInitialScale(100);//百分比缩放
        }
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);



        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setSupportZoom(true); // 支持缩放
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        String ua = webview.getSettings().getUserAgentString();
        webview.getSettings().setUserAgentString(ua+"; Android");
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url))
            webview.loadUrl(url);
        else webview.loadData(url, "text/html", "utf-8");
        //设置Web视图
        webview.setWebViewClient(new HelloWebViewClient());
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pg1.setVisibility(View.GONE);
                } else {
                    pg1.setVisibility(View.VISIBLE);
                    pg1.setProgress(newProgress);
                }
            }
        });
        initData();
    }

    private void initData() {
        imageFinsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onPause() {
        webview.reload();
        super.onPause();
    }

    public void CloseSpeaker() {

        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            if (audioManager != null) {
                if (audioManager.isSpeakerphoneOn()) {
                    audioManager.setSpeakerphoneOn(false);
                    audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, currVolume,
                            AudioManager.STREAM_VOICE_CALL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // 获取上下文, H5PayDemoActivity为当前页面
            final Activity context = WebViewActivity.this;
            // ------  对alipays:相关的scheme处理 -------
            if (url.startsWith("alipays:") || url.startsWith("alipay")) {
                try {
                    context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (Exception e) {
                    new AlertDialog.Builder(context)
                            .setMessage("未检测到支付宝客户端，请安装后重试。")
                            .setPositiveButton("立即安装", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Uri alipayUrl = Uri.parse("https://d.alipay.com");
                                    context.startActivity(new Intent("android.intent.action.VIEW", alipayUrl));
                                }
                            }).setNegativeButton("取消", null).show();
                }
                return true;
            }
            // ------- 处理结束 -------

            if (!(url.startsWith("http") || url.startsWith("https"))) {
                return true;
            }

            view.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
            handler.proceed();
        }
    }


}
