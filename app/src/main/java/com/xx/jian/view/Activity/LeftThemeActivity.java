package com.xx.jian.view.Activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xx.jian.BaseActivity;
import com.xx.jian.R;
import com.xx.jian.adapter.DailyNewsAdapter;
import com.xx.jian.bean.DailyNewsBean;
import com.xx.jian.bean.EventBusBean;
import com.xx.jian.interf.RBResponse;
import com.xx.jian.interf.RecyclerItemClickListener;
import com.xx.jian.interf.WebResponse;
import com.xx.jian.net.IPconfig;
import com.xx.jian.net.OKHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 每周日报的列表内容activity
 */
public class LeftThemeActivity extends BaseActivity implements WebResponse {
    private Toolbar toolbar;
    private TextView titleText;
    //主题日报内容数据源
    private List<DailyNewsBean.StoriesBean> mList;
    //对应的主题日报的ID
    private String themeID;
    //日报具体的ID 便于查看详细日报内容
    private int newsID;


    private RecyclerView recyclerView;
    private DailyNewsAdapter dailyNewsAdapter;

    private EventBusBean eventBusBean;

    @Override
    protected int initContentView() {
        return R.layout.activity_left_theme;
    }

    @Override
    protected void initView() {
        //注册EventBus
        EventBus.getDefault().register(this);
        toolbar = (Toolbar) findViewById(R.id.toobar);
        toolbar.setNavigationIcon(R.drawable.back);
        titleText = (TextView) findViewById(R.id.toobar_title);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        titleText.setText(eventBusBean.getTitle());
        themeID = eventBusBean.getID() + "";
        OKHttpUtils.getInstance().getStringWithGet(this, IPconfig.NEES_DAILY + themeID, DailyNewsBean.class, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(EventBusBean eventBusBean) {
        this.eventBusBean = eventBusBean;
    }

    @Override
    public void onSuccessResponse(Call call, RBResponse resultBean, int tag) throws IOException {
        if (resultBean != null) {
            DailyNewsBean dailyNewsBean = (DailyNewsBean) resultBean;
            mList = dailyNewsBean.getStories();
            dailyNewsAdapter = new DailyNewsAdapter(LeftThemeActivity.this, mList, dailyNewsBean);
            setOnItemClickListener();
            recyclerView.setAdapter(dailyNewsAdapter);
        }
    }

    @Override
    public void onFailResponse(Call call, IOException e, int requestCode) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 设置列表点击事件监听
     */
    private void setOnItemClickListener() {
        dailyNewsAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                newsID = mList.get(postion).getId();
                EventBus.getDefault().postSticky(new Integer(newsID));
                Intent intent = new Intent(LeftThemeActivity.this, DailyNewsDetialActivity.class);
                startActivity(intent);
            }
        });
    }
}
