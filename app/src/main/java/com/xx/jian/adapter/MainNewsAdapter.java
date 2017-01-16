package com.xx.jian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.xx.jian.R;
import com.xx.jian.bean.MainContentNewsBean;
import com.xx.jian.interf.RecyclerItemClickListener;
import com.xx.jian.utils.Imager;
import com.xx.jian.view.banner.BannerView;
import com.xx.jian.view.banner.ZhihuTop;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页新闻的Adapter
 * Created by xx1994 on 2016/12/28.
 */
public class MainNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    //新闻数据源
    private List<MainContentNewsBean.StoriesBean> mNewsList;
    //轮播图数据源
    private List<MainContentNewsBean.TopStoriesBean> mBannerList;
    private Context context;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;  //普通Item View
    private static final int TYPE_FOOTER = 2;  //顶部FootView
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0x222;
    //正在加载中
    public static final int LOADING_MORE = 0x333;
    //加载完成已经没有更多数据了
    public static final int NO_MORE_DATA = 0x444;
    //上拉加载更多状态-默认为0x111
    private int load_more_status = PULLUP_LOAD_MORE;

    //Banner图顶部
    public ConvenientBanner<ZhihuTop> banner;
    private List<ZhihuTop> tops;

    private RecyclerItemClickListener recyclerItemClickListener;

    public MainNewsAdapter(Context context, List<MainContentNewsBean.StoriesBean> mNewsList, List<MainContentNewsBean.TopStoriesBean> mBannerList) {
        this.context = context;
        this.mNewsList = mNewsList;
        this.mBannerList = mBannerList;

        getTopUrl();
    }

    private void getTopUrl() {
        tops = new ArrayList<>();
        for (int i = 0; i < mBannerList.size(); i++) {
            ZhihuTop zhihuTop = new ZhihuTop();
            zhihuTop.setImage(mBannerList.get(i).getImage());
            zhihuTop.setTitle(mBannerList.get(i).getTitle());
            zhihuTop.setId(mBannerList.get(i).getId());
            tops.add(zhihuTop);
        }
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size() == 0 ? 0 : mNewsList.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        // 最后一个item设置为footerView
        else if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同的类型返回不同的布局
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.activity_main_news_item, null);
            itemView.setOnClickListener(this);
            ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View footerView = LayoutInflater.from(context).inflate(R.layout.footer_loading, parent, false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(footerView);
            return footerViewHolder;
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(context).inflate(R.layout.activity_main_content_banner, parent, false);
            view.setOnClickListener(this);
            return new HeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).newsText.setText(mNewsList.get(position - 1).getTitle());
            Imager.loadWithHighPriority(mNewsList.get(position - 1).getImages().get(0), ((ItemViewHolder) holder).imageView);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footViewHolder = (FooterViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.loadText.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.progressBar.setVisibility(View.VISIBLE);
                    footViewHolder.loadText.setText("努力加载中...");
                    break;
                case NO_MORE_DATA:
                    footViewHolder.progressBar.setVisibility(View.GONE);
                    footViewHolder.loadText.setText("我也是有限度的");
                    break;
            }
        } else if (holder instanceof HeaderViewHolder) {
            final HeaderViewHolder itemHolder = (HeaderViewHolder) holder;
            itemHolder.banner.setPages(new CBViewHolderCreator<BannerView>() {
                @Override
                public BannerView createHolder() {
                    return new BannerView();
                }
            }, tops).setPageIndicator(new int[]{R.drawable.selector_banner_point, R.drawable.selector_bgabanner_point});
            banner = itemHolder.banner;
        }
    }

    @Override
    public void onClick(View v) {
        if (recyclerItemClickListener != null) {
            recyclerItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView newsText;
        ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.news_img);
            newsText = (TextView) itemView.findViewById(R.id.news_title);
        }
    }

    //顶部banner
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final ConvenientBanner<ZhihuTop> banner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner = (ConvenientBanner) itemView.findViewById(R.id.convenientBanner);
            banner.setScrollDuration(1000);
            banner.startTurning(3000);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView loadText;
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            loadText = (TextView) itemView.findViewById(R.id.more_data_msg);
        }
    }


    /**
     * //上拉加载更多
     * PULLUP_LOAD_MORE=0x222;
     * //正在加载中
     * LOADING_MORE=0x333;
     * //加载完成已经没有更多数据了
     * NO_MORE_DATA=0x444;
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

}

