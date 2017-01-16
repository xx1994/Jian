package com.xx.jian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by xx1994 on 2016/12/27.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //有EditView的界面，默认不弹窗软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(initContentView());
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 返回界面的布局
     *
     * @return layout id
     */
    protected abstract int initContentView();

    /**
     * 初始化控件，findViewById()
     */
     protected abstract void initView();

    /**
     * 给控件设置监听器，初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据（网络加载、本地加载数据等操作）
     */
    protected abstract void initData();
}
