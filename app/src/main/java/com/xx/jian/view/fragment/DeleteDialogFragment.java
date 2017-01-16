package com.xx.jian.view.fragment;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.xx.jian.R;
import com.xx.jian.adapter.LastTimeListAdapter;
import com.xx.jian.adapter.MyImageAdapter;
import com.xx.jian.bean.LastTimeDataBean;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 上次删除提示对话框
 * Created by Administrator on 2016/12/13.
 */
@SuppressLint("ValidFragment")
public class DeleteDialogFragment extends DialogFragment {

    private TextView cancleBtn, sureBtn, deleteText;
    private List<Map<String, String>> resultList;
    private int position;
    private LastTimeListAdapter lastTimeListAdapter;

    public DeleteDialogFragment() {
    }

    public DeleteDialogFragment(List<Map<String, String>> resultList, int position, LastTimeListAdapter lastTimeListAdapter) {
        this.resultList = resultList;
        this.position = position;
        this.lastTimeListAdapter = lastTimeListAdapter;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialogfragment, null);
        deleteText = (TextView) view.findViewById(R.id.delete_text);
        deleteText.setText("确定删除这条记忆?");
        cancleBtn = (TextView) view.findViewById(R.id.cancle);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        sureBtn = (TextView) view.findViewById(R.id.sure);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
                lastTimeListAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        return view;
    }

    /**
     * 根据位置删除数据库
     *
     * @param position
     */
    public void deleteData(final int position) {
        Realm realm = Realm.getInstance(getActivity());
        final RealmResults<LastTimeDataBean> dataBeen = realm.where(LastTimeDataBean.class).findAll();
        realm.beginTransaction();
        dataBeen.remove(position);
        realm.commitTransaction();
        resultList.remove(position);
    }

}
