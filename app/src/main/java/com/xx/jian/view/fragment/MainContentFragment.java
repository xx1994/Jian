package com.xx.jian.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.jian.BaseFragment;
import com.xx.jian.R;
import com.xx.jian.adapter.MainNewsAdapter;
import com.xx.jian.bean.EventBusBean;
import com.xx.jian.bean.MainContentNewsBean;
import com.xx.jian.interf.RBResponse;
import com.xx.jian.interf.RecyclerItemClickListener;
import com.xx.jian.interf.WebResponse;
import com.xx.jian.net.IPconfig;
import com.xx.jian.net.OKHttpUtils;
import com.xx.jian.view.Activity.MainNewsDetialActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by xx1994 on 2016/12/28.
 */
public class MainContentFragment extends BaseFragment implements WebResponse {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    //自定义带刷新的Adapter
    private MainNewsAdapter mainNewsAdapter;
    //保存上次可见Item的位置
    private int lastVisibleItem = 0;
    private LinearLayoutManager linearLayoutManager;
    //标记是否正在加载
    private boolean isLoading = false;

    //内容结果List
    private List<MainContentNewsBean.StoriesBean> newsList = new ArrayList<>();
    //轮播图List
    private List<MainContentNewsBean.TopStoriesBean> bannerList = new ArrayList<>();
    //目前加载的新闻的日期
    private String nowDate;
    private EventBusBean eventBusBean;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.activity_main_recycle_fragment, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.swiprefresh);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsList.clear();
                getData(IPconfig.NEWS_LATEST, "", 0);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //上拉加载
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mainNewsAdapter != null) {    //判断是否为空 防止在网络无连接情况下 resultBean为空 没有对mainNewsAdaper初始化报空指针异常
                    if (lastVisibleItem + 1 == mainNewsAdapter.getItemCount()) {
                        boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                        if (isRefreshing) {
                            mainNewsAdapter.notifyItemRemoved(mainNewsAdapter.getItemCount());
                            return;
                        }
                        if (!isLoading) {
                            mainNewsAdapter.changeMoreStatus(MainNewsAdapter.LOADING_MORE);
                            isLoading = true;
                            getData(IPconfig.NEWS_BEFORE, nowDate, 1);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        return view;
    }

    @Override
    public void initData() {
        getData(IPconfig.NEWS_LATEST, "", 0);
    }

    /**
     * 获取测试数据
     */
    private void getData(String url, String date, int getTag) {
        OKHttpUtils.getInstance().getStringWithGet(MainContentFragment.this, url + date, MainContentNewsBean.class, getTag);
    }


    /**
     * 网络访问成功回调
     *
     * @param call
     * @param resultBean 结果Bean
     * @param tag        请求是刷新 还是加载的类型
     * @throws IOException
     */
    @Override
    public void onSuccessResponse(Call call, RBResponse resultBean, int tag) throws IOException {
        if (resultBean != null) {
            if (0 == tag) {     //tag标记是刷新请求还是加载请求  0为刷新  1位加载
                MainContentNewsBean mainContentNewsBean = (MainContentNewsBean) resultBean;
                nowDate = mainContentNewsBean.getDate();   //更新当前加载新闻的日期
                newsList = mainContentNewsBean.getStories();
                bannerList = mainContentNewsBean.getTop_stories();
                mainNewsAdapter = new MainNewsAdapter(getActivity(), newsList, bannerList);
                setItemClickListener();
                recyclerView.setAdapter(mainNewsAdapter);
                swipeRefreshLayout.setRefreshing(false);
            } else if (1 == tag) {
                MainContentNewsBean mainContentNewsBean = (MainContentNewsBean) resultBean;
                nowDate = mainContentNewsBean.getDate();
                List<MainContentNewsBean.StoriesBean> tempList = mainContentNewsBean.getStories();
                newsList.addAll(tempList);
                mainNewsAdapter.notifyDataSetChanged();
                isLoading = false;
                mainNewsAdapter.changeMoreStatus(MainNewsAdapter.PULLUP_LOAD_MORE);
            }
        }
    }

    /**
     * 网络访问失败回调
     *
     * @param call
     * @param e
     * @param requestCode
     */
    @Override
    public void onFailResponse(Call call, IOException e, int requestCode) {

    }


    /**
     * 设置列表点击事件监听
     */
    private void setItemClickListener() {
        mainNewsAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(), MainNewsDetialActivity.class);
                eventBusBean = new EventBusBean();
                eventBusBean.setImgUrl(newsList.get(postion - 1).getImages().get(0));
                eventBusBean.setTitle(newsList.get(postion - 1).getTitle());
                eventBusBean.setID(newsList.get(postion - 1).getId());
                EventBus.getDefault().postSticky(eventBusBean);

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        view, getString(R.string.shared_img));
                ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
            }
        });
    }
}