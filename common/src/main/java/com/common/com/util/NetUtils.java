//package com.bianhua.com.util.utils;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.text.TextUtils;
//
//import com.bianhua.com.R;
//import com.facebook.imagepipeline.request.ImageRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2015/9/8 0008.
// */
//public class NetUtils {
//    public static RequestQueue newRequestQueue;
//    static Dialog loading;
//
//    public static void sendStringRequest(final Context context, final String url, final String tag, final RequestCallBack callback) {
//        LogUtil.d(tag, url);
//        //检查网络状态
//        if (CommonUtils.getConnectedType(context) == -1) {
//            ViewUtils.makeToast(context, "没有可用的网络", 1500);
//            return;
//        }
//        newRequestQueue = Volley.newRequestQueue(context);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                LogUtil.d(tag, "onResponse===" + s);
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.successful(s);
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                ViewUtils.makeToast(context, context.getString(R.string.server_error), 1500);
//                LogUtil.e(tag, "onErrorResponse===" + volleyError.toString());
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.errored(volleyError.getMessage());
//            }
//        });
//        stringRequest.setTag(tag);
//        callback.loading();
//        Activity activity = (Activity) context;
//        if (!activity.isDestroyed() && !activity.isFinishing()) {
//            if (loading == null) {
//                loading = ViewUtils.createLoadingDialog(context, "请稍后...", true);
//            }
//            loading.show();
//        }
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,0,1f));
//        newRequestQueue.add(stringRequest);
//    }
//
//    public static void sendStringRequest_Post(final Context context, final String url, final Map<String, String> map, final String tag, final RequestCallBack callback) {
//        LogUtil.d(tag, url);
//        //检查网络状态
//        if (CommonUtils.getConnectedType(context) == -1) {
//            ViewUtils.makeToast(context, "没有可用的网络", 1500);
//            return;
//        }
//        newRequestQueue = Volley.newRequestQueue(context);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                LogUtil.d(tag, "onResponse===" + s);
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.successful(s);
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                ViewUtils.makeToast(context, context.getString(R.string.server_error), 1500);
//                LogUtil.e(tag, "onErrorResponse===" + volleyError.toString());
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.errored(volleyError.getMessage());
//            }
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                LogUtil.d(tag, "getParams===" + map.toString());
//                return map;
//            }
//        };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                60 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setTag(tag);
//        callback.loading();
//
//        if (loading == null) {
//            loading = ViewUtils.createLoadingDialog(context, "请稍后...", true);
//        }
//        loading.show();
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,0,1f));
//        newRequestQueue.add(stringRequest);
//    }
//
//    public static void sendJsonRequest_Post(final Context context, final String url, JSONObject jsonObject, final String tag, final RequestCallBack callback) {
//        LogUtil.d(tag, url);
//        //检查网络状态
//        if (CommonUtils.getConnectedType(context) == -1) {
//            ViewUtils.makeToast(context, "没有可用的网络", 1500);
//            return;
//        }
//        newRequestQueue = Volley.newRequestQueue(context);
//
//        JsonObjectRequest stringRequest = new JsonObjectRequest(url, jsonObject, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                LogUtil.d(tag, "onResponse===" + String.valueOf(jsonObject));
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.successful(String.valueOf(jsonObject));
//            }
//
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                ViewUtils.makeToast(context, context.getString(R.string.server_error), 1500);
//                LogUtil.e(tag, "onErrorResponse===" + volleyError.toString());
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.errored(volleyError.getMessage());
//            }
//
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
//                60 * 1000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setTag(tag);
//        callback.loading();
//
//        if (loading == null) {
//            loading = ViewUtils.createLoadingDialog(context, "请稍后...", true);
//        }
//        loading.show();
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30*1000,0,1f));
//        newRequestQueue.add(stringRequest);
//    }
//
//    public static void sendImageRequest(final Context context, final String url, final String tag, final RequestImageCallBack callback) {
//        LogUtil.d(tag, url);
//        //检查网络状态
//        if (CommonUtils.getConnectedType(context) == -1) {
//            ViewUtils.makeToast(context, "没有可用的网络", 1500);
//            return;
//        }
//        newRequestQueue = Volley.newRequestQueue(context);
//        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap bitmap) {
//                LogUtil.d(tag, "onResponse===Success");
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.successful(bitmap);
//            }
//        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                ViewUtils.makeToast(context, context.getString(R.string.server_error), 1500);
//                LogUtil.e(tag, "onErrorResponse===" + volleyError.toString());
//                if (loading != null) {
//                    loading.dismiss();
//                    loading = null;
//                }
//                callback.errored(volleyError.getMessage());
//            }
//        });
//        imageRequest.setTag(tag);
//        if (loading == null) {
//            loading = ViewUtils.createLoadingDialog(context, "请稍后...", true);
//        }
//        loading.show();
//        newRequestQueue.add(imageRequest);
//    }
//
//    public interface RequestCallBack {
//        void loading();
//
//        void successful(String response);
//
//        void errored(String response);
//    }
//
//    public interface RequestImageCallBack {
//        void successful(Bitmap response);
//
//        void errored(String response);
//    }
//
//
//    /**
//     * 判断通道是否需要绑卡,
//     * 不需要判断，执行callback
//     * 需要-通过，执行callback
//     * 需要-未通过，跳转至绑卡界面
//     *
//     * @param channel  通道属性
//     * @param bindCard 卡属性
//     */
//    public static void requestBindStatus(final Activity context, final ChannelBean.Channel channel, final BindCard bindCard, final BindStatusCallBack callBack) {
//        String customerNum = StorageCustomerInfoUtil.getInfo("customerNum", context);
//        HashMap<Integer, String> parms = new HashMap<>();
//        parms.put(0, "0700");
//        parms.put(3, "390021");
//        parms.put(42, customerNum);
//        parms.put(45, bindCard.getBANK_ACCOUNT());
//        parms.put(59, Constant.VERSION);
//        parms.put(64, Constant.getMacData(parms));
//        String url = Constant.getUrl(parms);
//        MyAsyncTask task = new MyAsyncTask(new MyAsyncTask.LoadResourceCall() {
//            @Override
//            public void isLoadedContent(String content) {
//                LogUtil.d(MakeDesignActivity.class.getSimpleName(), "390021  : " + content);
//                if (TextUtils.isEmpty(content)) {
//                    ViewUtils.makeToast(context, "数据异常", 1000);
//                    return;
//                }
//                try {
//                    JSONObject object = object = new JSONObject(content);
//                    String result = object.getString("39");
//                    String f36 = object.getString("36");
//                    JSONArray jsonArray = new JSONArray(f36);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject channelItem = jsonArray.getJSONObject(i);
//                        if (channelItem.getString("kstatus").contains(channel.getAcqcode())) {
//                            if (TextUtils.equals("开通", channelItem.getString("status"))) {
//                                //已开通，执行后续操作
//                                if (callBack != null)
//                                    callBack.onBindOK(channel, bindCard);
//                                return;
//                            } else {
//                                Intent intent = new Intent(context, BindCardActivity.class);
//                                intent.putExtra("bindId", bindCard.getID());
//                                intent.putExtra("limit", bindCard.getLIMIT_MONEY());
//                                intent.putExtra("billDay", bindCard.getBILL_DAY());
//                                intent.putExtra("payDay", bindCard.getREPAYMENT_DAY() + "");
//                                intent.putExtra("bankAccount", bindCard.getBANK_ACCOUNT());
//                                intent.putExtra("expiryDay", bindCard.getExpdate());
//                                intent.putExtra("cvn", bindCard.getCvn());
//                                intent.putExtra("phone", bindCard.getBANK_PHONE());
//                                intent.putExtra("type", channelItem.getString("type"));
//                                if (channelItem.has("category"))
//                                    intent.putExtra("category", channelItem.getString("category"));
//                                context.startActivity(intent);
//                                return;
//                            }
//                        } else {
//                            continue;
//                        }
//                    }
//                    //不在需要判断绑定状态的通道里，直接后续操作
//                    if (callBack != null)
//                        callBack.onBindOK(channel, bindCard);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void isLoadingContent() {
//
//            }
//        });
//
//        task.execute(url);
//    }
//
//    public interface BindStatusCallBack {
//        void onBindOK(ChannelBean.Channel channel, BindCard bindCard);
//    }
//}
