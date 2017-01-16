package com.xx.jian.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xx.jian.BaseFragment;
import com.xx.jian.R;
import com.xx.jian.bean.LastTimeDataBean;
import com.xx.jian.view.card.CardDataItem;
import com.xx.jian.view.card.CardSlidePanel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by xx1994 on 2017/1/9.
 * 卡片页面
 */
public class LastTimeCardFragment extends BaseFragment {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private Realm realm;
    private List<CardDataItem> dataList = new ArrayList<CardDataItem>();

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getInstance(getActivity());
        getDataFromRealm();
        View view;
        if (dataList.size() <= 3) {
            view = inflater.inflate(R.layout.null_layout, container, false);
            TextView textView = (TextView) view.findViewById(R.id.null_text);
            textView.setText("至少需要4条才能看到效果哦，快去添加吧");
        } else {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.card_layout, null);
            initCardView(view);
        }
        return view;
    }

    private void initCardView(View rootView) {
        CardSlidePanel slidePanel = (CardSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
//                Log.d("CardFragment", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
//                Log.d("CardFragment", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
            }

            @Override
            public void onItemClick(View cardView, int index) {
//                Log.d("CardFragment", "卡片点击-" + dataList.get(index).userName);
            }
        };
        slidePanel.setBtnClickListener(new CardSlidePanel.BtnClickListener() {
            @Override
            public void onClick(View view, int index) {
                deleteData(index);    //点击 删除按钮时  进行数据删除
            }
        });
        slidePanel.setCardSwitchListener(cardSwitchListener);
        slidePanel.fillData(dataList);
    }

    @Override
    public void initData() {
    }

    /**
     * 从数据库获取数据
     */
    private void getDataFromRealm() {
        dataList.clear();
        RealmResults<LastTimeDataBean> results = realm.where(LastTimeDataBean.class).findAll();
        if (results != null) {
            for (LastTimeDataBean lastTimeDataBean : results) {
                CardDataItem dataItem = new CardDataItem();
                dataItem.userName = lastTimeDataBean.getImgDes();
                dataItem.imagePath = lastTimeDataBean.getImgPth();
                dataList.add(dataItem);
            }
        }
    }

    /**
     * 根据位置删除数据库
     *
     * @param position
     */
    private void deleteData(final int position) {
        RealmResults<LastTimeDataBean> dataBeen = realm.where(LastTimeDataBean.class).findAll();
        realm.beginTransaction();
        dataBeen.remove(position);
        realm.commitTransaction();
    }
}
