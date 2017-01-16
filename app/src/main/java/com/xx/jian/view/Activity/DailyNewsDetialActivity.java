package com.xx.jian.view.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.xx.jian.BaseActivity;
import com.xx.jian.R;
import com.xx.jian.bean.DailyNewsDetailBean;
import com.xx.jian.bean.EventBusBean;
import com.xx.jian.interf.RBResponse;
import com.xx.jian.interf.WebResponse;
import com.xx.jian.net.IPconfig;
import com.xx.jian.net.OKHttpUtils;
import com.xx.jian.view.progress.MyProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

public class DailyNewsDetialActivity extends BaseActivity implements WebResponse {
    private Toolbar toolbar;
    private FrameLayout webContainer;
    //新闻具体内容的ID
    private int dailyNewsID;

    private WebView webView;
    private MyProgress myProgress;

    @Override
    protected int initContentView() {
        return R.layout.activity_daily_news_detial;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        toolbar = (Toolbar) findViewById(R.id.dailty_news_detail_toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        webContainer = (FrameLayout) findViewById(R.id.dailty_news_detail_web_container);

        myProgress = (MyProgress) findViewById(R.id.my_progress_view);
        myProgress.setOriginalImage(R.drawable.progress);
        myProgress.setWaveColor(getResources().getColor(R.color.swiprefresh));

        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView = new WebView(this);
        webContainer.addView(webView);
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            myProgress.setVisibility(View.GONE);
                            view.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
            }
        });
    }

    @Override
    protected void initListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        OKHttpUtils.getInstance().getStringWithGet(this, IPconfig.NEWS_DETIAL + dailyNewsID, DailyNewsDetailBean.class, 0);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(Integer dailyNewsID) {
        this.dailyNewsID = dailyNewsID;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccessResponse(Call call, RBResponse resultBean, int tag) throws IOException {
        if (resultBean != null) {
            DailyNewsDetailBean dailyNewsDetailBean = (DailyNewsDetailBean) resultBean;
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
            String html = "<html><head>" + css + "</head><body>" + dailyNewsDetailBean.getBody() + "</body></html>";
            html = html.replace("<div class=\"img-place-holder\">", "");
            webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
        }
    }

    @Override
    public void onFailResponse(Call call, IOException e, int requestCode) {

    }
}
