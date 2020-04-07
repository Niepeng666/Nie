package com.common.com.util.utils;

import android.text.TextUtils;

import com.common.com.util.CommonUtils;
import com.common.com.util.LogUtil;

import java.util.HashMap;

public class Constant {
        /*外网*/
    public static final String BASE_URL = "http://120.79.247.217:6442"; //生产
    public static final String REQUEST_API = BASE_URL + "/lly-posp-proxy-sfzf/request.app?";   //生产
    public static final String UPLOADIMAGE = BASE_URL + "/lly-posp-proxy-sfzf/uploadImage.app?";
    public static final String mainKey = "21E4ACD4CD5D4619B063F40C5A454F7D";
    public static String VERSION = "KHB-A-1.1.2";
    public static String AGENCY_CODE44 = "2800001";
    /*内网*/
  /*  public static final String BASE_URL = "http://192.168.88.172"; //生产
    public static final String REQUEST_API = BASE_URL + ":8080/khb-posp-proxy/request.app?";   //生产
    public static final String UPLOADIMAGE = BASE_URL + ":8080/khb-posp-proxy/uploadImage.app?";
    public static final String mainKey = "21E4ACD4CD5D4619B063F40C5A454F7D";
    public static String VERSION = "KHB-A-1.1.0";
    public static String AGENCY_CODE44 = "2800001";*/


    /**
     * APP下载地址
     */
    public static String DOWNLOAD_APK = "http://kahuanbao.llyzf.cn:6442/lly-posp-proxy-sfzf/khb.apk";

    /**
     * app启动图片地址
     */
    public static String LAUNCH_IAMGE = Constant.BASE_URL+"/tyjf.png";

    /**
     * 产品名称
     */
    public static String PRODUCT_SHORT = "KHB";
    public static String CONSUME = "consume";

    /**
     * 根据参数获取get请求的链接
     *
     * @param parameterMap
     * @return
     */

    public static String getUrl(HashMap<Integer, String> parameterMap) {
        StringBuffer urlBuffer = new StringBuffer();
        if (!TextUtils.isEmpty(parameterMap.get(65))) {//特殊的请求链接 如上传图片
            urlBuffer.append(parameterMap.get(65));
        } else {
            urlBuffer.append(REQUEST_API);
        }
        for (int i = 0; i <= 64; i++) {
            if (!TextUtils.isEmpty(parameterMap.get(i)))
                if (i == 0) {
                    urlBuffer.append(i + "=" + parameterMap.get(i));
                } else {
                    urlBuffer.append("&" + i + "=" + parameterMap.get(i));
                }
        }
     LogUtil.i("requestUrl", "requestUrl==" + urlBuffer.toString());
        return urlBuffer.toString();
    }


    /**
     * 根据参数获取get请求的链接
     *
     * @param parameterMap
     * @return
     */
    public static String getUrl2(HashMap<String, String> parameterMap) {
        StringBuffer urlBuffer = new StringBuffer();
        if (!TextUtils.isEmpty(parameterMap.get(65))) {//特殊的请求链接 如上传图片
            urlBuffer.append(parameterMap.get(65));
        } else {
            urlBuffer.append(REQUEST_API);
        }
        for (int i = 0; i <= 64; i++) {
            if (!TextUtils.isEmpty(parameterMap.get(i + "")))
                if (i == 0) {
                    urlBuffer.append(i + "=" + parameterMap.get(i + ""));
                } else {
                    urlBuffer.append("&" + i + "=" + parameterMap.get(i + ""));
                }
        }
       LogUtil.i("requestUrl", "requestUrl==" + urlBuffer.toString());
        return urlBuffer.toString();
    }

    /**
     * 根据参数获取签名
     *
     * @param parameterMap
     * @return
     */
    public static String getMacData(HashMap<Integer, String> parameterMap) {
        StringBuffer urlBuffer = new StringBuffer();
        for (int i = 0; i <= 63; i++) {
            if (!TextUtils.isEmpty(parameterMap.get(i))) urlBuffer.append(parameterMap.get(i));
        }
       LogUtil.i("macData", "macData==" + urlBuffer.toString());
        return CommonUtils.Md5(urlBuffer.toString() + mainKey);
    }


    /**
     * 根据参数获取签名
     *
     * @param parameterMap
     * @return
     */
    public static String getMacDatabyString(HashMap<String, String> parameterMap) {
        StringBuffer urlBuffer = new StringBuffer();
        for (int i = 0; i <= 63; i++) {
            if (!TextUtils.isEmpty(parameterMap.get(i + "")))
                urlBuffer.append(parameterMap.get(i + ""));
        }
        LogUtil.i("macData", "macData==" + urlBuffer.toString());
        return CommonUtils.Md5(urlBuffer.toString() + mainKey);
    }

}
