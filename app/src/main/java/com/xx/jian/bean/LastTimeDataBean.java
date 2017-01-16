package com.xx.jian.bean;

import io.realm.RealmObject;

/**
 * Created by xx1994 on 2017/1/9.
 * 上次 图片及描述bean
 */
public class LastTimeDataBean extends RealmObject {
    private String imgPth;
    private String imgDes;

    public String getImgDes() {
        return imgDes;
    }

    public void setImgDes(String imgDes) {
        this.imgDes = imgDes;
    }

    public String getImgPth() {
        return imgPth;
    }

    public void setImgPth(String imgPth) {
        this.imgPth = imgPth;
    }
}
