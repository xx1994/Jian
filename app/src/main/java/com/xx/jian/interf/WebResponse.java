package com.xx.jian.interf;

import android.os.Handler;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by XuXiang on 2016/11/8.
 * 网络请求 请求结果返回Bean接口
 */
public interface WebResponse {
    /**
     * 成功结果返回
     *
     * @param call
     * @param resultBean 结果Bean
     * @param tag        请求是刷新 还是加载的类型
     * @throws IOException
     */
    public void onSuccessResponse(Call call, RBResponse resultBean, int tag) throws IOException;

    /**
     * 失败结果返回
     *
     * @param call
     * @param e
     */
    public void onFailResponse(Call call, IOException e, int requestCode);
}
