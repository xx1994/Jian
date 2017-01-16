package com.xx.jian.net;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by xx1994 on 2016/12/27.
 */
public class NetworkUtil {
    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    public static boolean isOpenNetwork(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }
}
