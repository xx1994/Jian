package com.xx.jian.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xx.jian.R;
import com.xx.jian.utils.ImgCompressUtil;

import java.util.ArrayList;

/**
 * Created by xx1994 on 2017/1/6.
 */
public class MyImageAdapter extends BaseAdapter {
    private int Type_add = 1;
    private int Type_img = 0;

    private Context context;
    private ArrayList<String> mResults;
    private int imgMaxSize;

    public MyImageAdapter(Context context, ArrayList<String> mResults, int imgMaxSize) {
        this.context = context;
        this.mResults = mResults;
        this.imgMaxSize = imgMaxSize;
    }

    @Override
    public int getCount() {
        if (mResults.size() < imgMaxSize) {
            return mResults.size() + 1;
        }
        return mResults.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        //小于最大张数限制才会有两种类型，否则就直接是最大张数的相片
        if (mResults.size() < imgMaxSize) {
            if (position == mResults.size()) {
                return Type_add;
            }
            return Type_img;
        }
        return Type_img;
    }

    @Override
    public int getViewTypeCount() {
        if (mResults.size() < imgMaxSize) {
            return 2;
        }
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = getItemViewType(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.image_item, null);
            viewHolder.imgView = (ImageView) convertView.findViewById(R.id.myImg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (itemType == Type_img) {
            int imgViewHeight = viewHolder.imgView.getLayoutParams().height;
            int imgViewWidth = viewHolder.imgView.getLayoutParams().width;
            viewHolder.imgView.setImageBitmap(ImgCompressUtil.getSmallBitmap(mResults.get(position), imgViewHeight, imgViewWidth));
        } else if (itemType == Type_add) {
            viewHolder.imgView.setImageResource(R.drawable.gridview_addpic);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imgView;
    }
}
