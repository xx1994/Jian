package com.xx.jian.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.xx.jian.BaseFragment;
import com.xx.jian.R;
import com.xx.jian.adapter.NewsThemeAdapter;
import com.xx.jian.bean.EventBusBean;
import com.xx.jian.bean.NewsThemeBean;
import com.xx.jian.interf.RBResponse;
import com.xx.jian.interf.WebResponse;
import com.xx.jian.net.IPconfig;
import com.xx.jian.net.OKHttpUtils;
import com.xx.jian.view.Activity.LastTimeMainActivity;
import com.xx.jian.view.Activity.LeftThemeActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 左侧侧拉栏的Fragment
 * Created by Administrator on 2016/12/27.
 */
public class LeftMenuFragmet extends BaseFragment implements WebResponse, AdapterView.OnItemClickListener, View.OnClickListener {
    private NewsThemeBean newsThemeBean;
    private ListView newsThemeListView;
    private View view;
    //上次按钮
    private TextView lastTimeText;
    private TextView cleanCache;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.left_menu_frame_content, null);
        newsThemeListView = (ListView) view.findViewById(R.id.menu_list);
        newsThemeListView.setOnItemClickListener(this);
        lastTimeText = (TextView) view.findViewById(R.id.last_time_view);
        lastTimeText.setOnClickListener(this);
        cleanCache = (TextView) view.findViewById(R.id.clean_cache);
        cleanCache.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        //获取网络数据
        OKHttpUtils.getInstance().getStringWithGet(LeftMenuFragmet.this, IPconfig.NEWS_THEME, NewsThemeBean.class, 0);
    }

    @Override
    public void onSuccessResponse(Call call, RBResponse resultBean, int tag) throws IOException {
        if (resultBean != null) {
            newsThemeBean = (NewsThemeBean) resultBean;
            NewsThemeAdapter newsThemeAdapter = new NewsThemeAdapter(getActivity(), newsThemeBean.getOthers());
            newsThemeListView.setAdapter(newsThemeAdapter);
        } else {
            Log.i("TAG", "resultBean为空");
        }
    }

    @Override
    public void onFailResponse(Call call, IOException e, int requestCode) {
        Log.i("TAG", "请求网络失败");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<NewsThemeBean.OthersBean> mList = newsThemeBean.getOthers();
        EventBusBean eventBusBean = new EventBusBean();
        eventBusBean.setID(mList.get(position).getId());
        eventBusBean.setTitle(mList.get(position).getName());
        eventBusBean.setImgUrl(mList.get(position).getThumbnail());
        eventBusBean.setDescribe(mList.get(position).getDescription());
        EventBus.getDefault().postSticky(eventBusBean);
        Intent intent = new Intent(getActivity(), LeftThemeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last_time_view:   //点击上次按钮
                Intent intent = new Intent(getActivity(), LastTimeMainActivity.class);
                startActivity(intent);
                break;
            case R.id.clean_cache:    //清理缓存
                CleanCacheDiallogFragment cleanCacheDiallogFragment = new CleanCacheDiallogFragment();
                cleanCacheDiallogFragment.show(getActivity().getFragmentManager(), "cleanCahce");
                break;
        }
    }
}
