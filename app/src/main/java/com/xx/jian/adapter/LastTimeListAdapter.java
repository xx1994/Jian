package com.xx.jian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.xx.jian.R;
import com.xx.jian.interf.RecyclerItemLongClickListener;
import com.xx.jian.utils.ImgCompressUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by xx1994 on 2017/1/9.
 * 上次 的列表页的Adapter
 */
public class LastTimeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnLongClickListener {
    private Context context;
    private List<Map<String, String>> mList;
    private RecyclerItemLongClickListener recyclerItemLongClickListener;

    public LastTimeListAdapter(Context context, List<Map<String, String>> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setOnItemLongClickListener(RecyclerItemLongClickListener recyclerItemLongClickListener) {
        this.recyclerItemLongClickListener = recyclerItemLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_main_news_item, null);
        itemView.setOnLongClickListener(this);
        ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        int imgViewHeight = itemViewHolder.imageView.getLayoutParams().height;
        int imgViewWidth = itemViewHolder.imageView.getLayoutParams().width;
        itemViewHolder.imageView.setImageBitmap(ImgCompressUtil.getSmallBitmap(mList.get(position).get("imgPth"), imgViewHeight, imgViewWidth));
        itemViewHolder.textView.setText(mList.get(position).get("imgDes"));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public boolean onLongClick(View v) {
        if (recyclerItemLongClickListener != null) {
            recyclerItemLongClickListener.onItemLongClick(v, (int) v.getTag());
        }
        return true;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.news_img);
            textView = (TextView) itemView.findViewById(R.id.news_title);
        }
    }
}
