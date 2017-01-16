package com.xx.jian.adapter;

import android.content.Context;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xx.jian.R;
import com.xx.jian.bean.NewsThemeBean;

import java.util.List;

/**
 * 侧拉栏 的消息主题 Adapter
 * Created by Administrator on 2016/12/27.
 */
public class NewsThemeAdapter extends BaseAdapter {
    private Context context;
    private List<NewsThemeBean.OthersBean> mList;

    public NewsThemeAdapter(Context context, List<NewsThemeBean.OthersBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.left_menu_frame_content_item, null);
            viewHolder.themeText = (TextView) convertView.findViewById(R.id.content_item_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.themeText.setText(mList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView themeText;
    }
}
