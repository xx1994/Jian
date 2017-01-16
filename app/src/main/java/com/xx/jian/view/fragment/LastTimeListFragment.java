package com.xx.jian.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.jian.BaseFragment;
import com.xx.jian.R;
import com.xx.jian.adapter.LastTimeListAdapter;
import com.xx.jian.bean.LastTimeDataBean;
import com.xx.jian.interf.RecyclerItemLongClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by xx1994 on 2017/1/9.
 * 上次 的列表页
 */
public class LastTimeListFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private List<Map<String, String>> resultList;
    private Realm realm;
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        realm = Realm.getInstance(getActivity());
        getDataFromRealm();
        View view;
        if (resultList.size() == 0) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.null_layout, container, false);
        } else {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.last_time_list_fragment, null);
            recyclerView = (RecyclerView) view.findViewById(R.id.last_time_recycle_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            initRecyclerView();
        }
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            resultList.clear();
            getDataFromRealm();     //显示时  重新获取数据 便于数据及时更新
        }
    }

    @Override
    public void initData() {
    }

    private void initRecyclerView() {
        final LastTimeListAdapter lastTimeListAdapter = new LastTimeListAdapter(getActivity(), resultList);
        recyclerView.setAdapter(lastTimeListAdapter);
        lastTimeListAdapter.setOnItemLongClickListener(new RecyclerItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment(resultList, position, lastTimeListAdapter);
                deleteDialogFragment.show(getActivity().getFragmentManager(), "delete");
            }
        });
    }

    /**
     * 通过realm查找存储数据
     */
    private void getDataFromRealm() {
        resultList = new ArrayList<>();
        RealmResults<LastTimeDataBean> results = realm.where(LastTimeDataBean.class).findAll();
        if (results != null) {
            for (LastTimeDataBean lastTimeDataBean : results) {
                Map<String, String> map = new HashMap<>();
                map.put("imgDes", lastTimeDataBean.getImgDes());
                map.put("imgPth", lastTimeDataBean.getImgPth());
                resultList.add(map);
            }
        }
    }

}
