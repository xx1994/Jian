package com.xx.jian.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.xx.jian.NetApplication;
import com.xx.jian.interf.RBResponse;
import com.xx.jian.interf.WebResponse;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/27.
 */
public class OKHttpUtils {
    private static OKHttpUtils okHttpUtils = null;
    private static Handler mainHandler;

    public static synchronized OKHttpUtils getInstance() {
        if (okHttpUtils == null) {
            okHttpUtils = new OKHttpUtils();
            //更新UI线程
            mainHandler = new Handler(Looper.getMainLooper());
        }
        return okHttpUtils;
    }

    /**
     * Gson解析
     *
     * @param jsonString 传入的json String字符串
     * @param beanObj    进行Gson解析的Bean
     * @return
     */
    private static RBResponse getJson(String jsonString, Class beanObj) {
        Gson gson = new Gson();
        RBResponse resultBean = (RBResponse) gson.fromJson(jsonString, beanObj);
        return resultBean;
    }

    public void getStringWithGet(final WebResponse mResponse, String url, final Class jsonBean, final int getTag) {
        //添加缓存,没网时直接读取缓存
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(new File(NetApplication.getApplication().getCacheDir(), "responses"), cacheSize);
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().cache(cache).build();
        Request request = new Request.Builder().url(url).build();
        //判断网络是否连接
        boolean connected = NetworkUtil.isOpenNetwork(NetApplication.getApplication());
        if (!connected) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final RBResponse result = getJson(response.body().string(), jsonBean);
                //UI线程 回调出去可以直接更新UI
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mResponse.onSuccessResponse(call, result, getTag);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

/*
    *//**
     * 不带参数的POST请求网络
     *
     * @param mResponse   实现网络请求接口，调用此工具类进行网络访问的主类
     * @param url         网络请求url
     * @param requestCode 鉴别请求的请求码（区分同一个类中多次网络请求）
     * @param jsonBean    解析json数据的Bean
     *//*
    public void getString(final WebResponse mResponse, String url, final int requestCode, final Class jsonBean) {
        //添加缓存,没网时直接读取缓存
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(10, TimeUnit.MILLISECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        CacheControl cache = builder.build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .build();
        Request request = new Request.Builder().cacheControl(cache).url(url).post(formBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mResponse.onFailResponse(call, e, requestCode);
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                Log.i("TAG", "返回结果" + response);
                final RBResponse result = getJson(response.body().string(), jsonBean);
                //UI线程 回调出去可以直接更新UI
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mResponse.onSuccessResponse(call, result, requestCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }*/

  /*  *//**
     * 带参数的请求网络,传入Map<String,String>
     *
     * @param mResponse   实现网络请求接口，调用此工具类进行网络访问的主类
     * @param url         网络请求url
     * @param requestCode 鉴别请求的请求码（区分同一个类中多次网络请求）
     * @param jsonBean    解析json数据的Bean
     * @param map         带参数请求网络的参数 Map<String,String>
     *//*
    public void getStringWithParam(final WebResponse mResponse, String url, final int requestCode, final Class jsonBean, Map<String, String> map) {
        //添加缓存,没网时直接读取缓存
        final CacheControl.Builder cachebuilder = new CacheControl.Builder();
        cachebuilder.maxAge(10, TimeUnit.MILLISECONDS);//指示客户机可以接收生存期不大于指定时间的响应。
        CacheControl cache = cachebuilder.build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        Iterator i = map.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next().toString();
            builder.add(key, map.get(key));
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().cacheControl(cache).url(url).post(formBody).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mResponse.onFailResponse(call, e, requestCode);
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final RBResponse result = getJson(response.body().string(), jsonBean);
                //UI线程 回调出去可以直接更新UI
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mResponse.onSuccessResponse(call, result, requestCode);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }*/
}
