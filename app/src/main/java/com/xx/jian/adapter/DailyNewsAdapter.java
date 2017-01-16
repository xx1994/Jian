package com.xx.jian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xx.jian.R;
import com.xx.jian.bean.DailyNewsBean;
import com.xx.jian.interf.RecyclerItemClickListener;
import com.xx.jian.utils.Imager;

import java.util.List;

/**
 * 每周日报内容的Adapter
 * Created by xx1994 on 2017/1/3.
 */
public class DailyNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<DailyNewsBean.StoriesBean> mList;
    private DailyNewsBean dailyNewsBean;

    private int TYPE_HEADER = 0x1111;
    private int TYPE_ITEM = 0x2222;

    private RecyclerItemClickListener recyclerItemClickListener;

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    public DailyNewsAdapter(Context context, List<DailyNewsBean.StoriesBean> mList, DailyNewsBean dailyNewsBean) {
        this.context = context;
        this.mList = mList;
        this.dailyNewsBean = dailyNewsBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(context).inflate(R.layout.activity_left_theme_img, null);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(headerView);
            return headerViewHolder;
        } else if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.activity_main_news_item, null);
            itemView.setOnClickListener(this);
            ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
            return itemViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position - 1);
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (null != mList.get(position - 1).getImages()) {
                Imager.loadWithHighPriority(mList.get(position - 1).getImages().get(0), itemViewHolder.imageView);
            } else {
                itemViewHolder.imageView.setVisibility(View.GONE);
            }
            itemViewHolder.textView.setText(mList.get(position - 1).getTitle());
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.textView.setText(dailyNewsBean.getDescription());
            Imager.loadWithHighPriority(dailyNewsBean.getImage(), headerViewHolder.imageView);
        }
    }

    @Override
    public void onClick(View v) {
        if (recyclerItemClickListener != null) {
            recyclerItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.news_img);
            textView = (TextView) itemView.findViewById(R.id.news_title);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.daily_news_img);
            textView = (TextView) itemView.findViewById(R.id.daily_news_text);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() == 0 ? 0 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }
}
