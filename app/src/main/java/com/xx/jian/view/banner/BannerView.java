package com.xx.jian.view.banner;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.xx.jian.MainActivity;
import com.xx.jian.bean.EventBusBean;
import com.xx.jian.utils.Imager;
import com.xx.jian.R;
import com.xx.jian.view.Activity.MainNewsDetialActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * deals with displaying the top banner
 */
public class BannerView implements Holder<ZhihuTop> {
    private View view;
    private EventBusBean eventBusBean;

    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.card_item_big, null);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, int position, final ZhihuTop entity) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.story_img);
        TextView textView = (TextView) view.findViewById(R.id.news_title);
        Imager.loadWithHighPriority(entity.getImage(), imageView);
        textView.setText(entity.getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //轮播图点击跳转
                Intent intent = new Intent(context, MainNewsDetialActivity.class);
                eventBusBean = new EventBusBean();
                eventBusBean.setImgUrl(entity.getImage());
                eventBusBean.setTitle(entity.getTitle());
                eventBusBean.setID(entity.getId());
                EventBus.getDefault().postSticky(eventBusBean);
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) context,
                        imageView, context.getString(R.string.shared_img));
                ActivityCompat.startActivity((MainActivity) context, intent, optionsCompat.toBundle());
            }
        });

    }
}