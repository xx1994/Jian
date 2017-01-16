package com.xx.jian.view.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.xx.jian.BaseActivity;
import com.xx.jian.R;
import com.xx.jian.bean.EventBusBean;
import com.xx.jian.bean.NewsDetialsBean;
import com.xx.jian.interf.RBResponse;
import com.xx.jian.interf.WebResponse;
import com.xx.jian.net.IPconfig;
import com.xx.jian.net.OKHttpUtils;
import com.xx.jian.utils.Imager;
import com.xx.jian.view.progress.MyProgress;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

/**
 * 新闻的详细界面
 */
public class MainNewsDetialActivity extends BaseActivity implements WebResponse, View.OnClickListener {
    private EventBusBean eventBusBean;

    //新闻的ID
    private int ID;
    private ImageView bgImg;
    private CollapsingToolbarLayout toolbarLayout;
    private Toolbar toolbar;
    private WebView webView;
    private FrameLayout webContainer;
    private NewsDetialsBean newsDetialsBean;

    private MyProgress myProgress;
    /**
     * 分享按钮
     */
    private FloatingActionButton fab;

    @Override
    protected int initContentView() {
        return R.layout.activity_main_news_detial;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        bgImg = (ImageView) findViewById(R.id.detail_img);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        webContainer = (FrameLayout) findViewById(R.id.web_container);
        fab = (FloatingActionButton) findViewById(R.id.fab);

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
        toolbarLayout.setTitle(eventBusBean.getTitle());
        ID = eventBusBean.getID();
        OKHttpUtils.getInstance().getStringWithGet(this, IPconfig.NEWS_DETIAL + ID, NewsDetialsBean.class, 0);


    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventBusBean eventBusBean) {
        this.eventBusBean = eventBusBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSuccessResponse(Call call, RBResponse resultBean, int tag) throws IOException {
        if (resultBean != null) {
            newsDetialsBean = (NewsDetialsBean) resultBean;
            Imager.loadWithHighPriority(newsDetialsBean.getImage(), bgImg);
            String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
            String html = "<html><head>" + css + "</head><body>" + newsDetialsBean.getBody() + "</body></html>";
            html = html.replace("<div class=\"img-place-holder\">", "");
            webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
            fab.setOnClickListener(this);
        }
    }

    @Override
    public void onFailResponse(Call call, IOException e, int requestCode) {

    }

    @Override
    public void onClick(View v) {
        //分享按钮的点击分享
        switch (v.getId()) {
            case R.id.fab:
                Log.i("TAG", "点击了分享");
                Share();
                break;
        }
    }

    /**
     * 开启分享功能
     */
    private void Share() {
        Log.i("TAG", "进行分享");
        Intent textIntent = new Intent();
        textIntent.setAction(Intent.ACTION_SEND);
        textIntent.putExtra(Intent.EXTRA_TEXT, newsDetialsBean.getShare_url());
        textIntent.setType("text/plain");
        MainNewsDetialActivity.this.startActivity(
                Intent.createChooser(textIntent,
                        "分享到..."));
    }
}
