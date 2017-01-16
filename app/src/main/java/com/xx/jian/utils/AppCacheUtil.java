package com.xx.jian.utils;

import java.io.File;
import java.math.BigDecimal;

import android.util.Log;

import com.xx.jian.NetApplication;

public class AppCacheUtil {

    /**
     * 获取应用缓存数据
     */
    public static String getCache() {
        String cacheStr = null;
        // 获取缓存路径
//        Log.i("TAG", "缓存-->" + NetApplication.getApplication().getCacheDir().toString());
        try {
            cacheStr = getFormatSize(getFolderSize(NetApplication.getApplication().getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheStr;
    }

    /**
     * 递归删除文件
     */

    public static void deleteCache(File fileDir) {
        File[] file = fileDir.listFiles();
        if (file != null) {
            for (File f : file) {
                if (f.isDirectory()) {
                    deleteCache(f);
                } else {
                    f.delete();
                }
            }
        }
        fileDir.delete();
    }

    /**
     * 格式化单位
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    // 获取文件
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
}
