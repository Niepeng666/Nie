//package com.bianhua.com.util.utils;
//
//import android.os.AsyncTask;
//
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.params.CoreConnectionPNames;
//import org.apache.http.params.HttpParams;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.KeyStore;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.http.HTTP;
//
///**
// * Created by Administrator on 2018/1/30.
// */
//
//public class MyAsyncTask2 extends AsyncTask<HashMap<Integer,String>, Void, String> {
//    HttpClient httpClient;
//    private HttpParams httpParams;
//
//    private LoadResourceCall loadResourceCall;
//
//    public MyAsyncTask2(LoadResourceCall loadResourceCall) {
//        super();
//        this.loadResourceCall = loadResourceCall;
//    }
//
//    public MyAsyncTask2() {
//    }
//
//    public interface LoadResourceCall{
//
//        public void isLoadedContent(String content);
//        public void isLoadingContent();
//
//    }
//
//
//    @Override
//    protected String doInBackground(HashMap<Integer,String>... params) {
//        HashMap<Integer,String> map = params[0];
//        httpClient = getNewHttpClient();
//        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 90*1000);
//        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 90*1000);
//
//        String content =null;
//		HttpPost httpPost = new HttpPost(Constant.REQUEST_API);
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        if(map!=null){
//            for (Map.Entry<Integer, String> entry : map.entrySet()) {
//                nvps.add(new BasicNameValuePair(entry.getKey() + "", entry.getValue()));
//            }
//        }
//
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        try {
//            HttpResponse response = httpClient.execute(httpPost);
//            int status = response.getStatusLine().getStatusCode();
//            if(status==200){
//                content = EntityUtils.toString(response.getEntity());
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return content;
//    }
//
//    @Override
//    protected void onPostExecute(String result) {
//        LogUtil.v("result==", result+"==");
//        loadResourceCall.isLoadedContent(result);
//        super.onPostExecute(result);
//    }
//
//    @Override
//    protected void onPreExecute() {
//        loadResourceCall.isLoadingContent();
//        super.onPreExecute();
//        LogUtil.v("doing==", "doing==");
//    }
//
//    public HttpClient getNewHttpClient() {
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//
//            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//            HttpParams params = new BasicHttpParams();
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//
//            return new DefaultHttpClient(ccm, params);
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//    }
//}
