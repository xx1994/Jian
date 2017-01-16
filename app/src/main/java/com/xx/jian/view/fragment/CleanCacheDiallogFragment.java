package com.xx.jian.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.xx.jian.NetApplication;
import com.xx.jian.R;
import com.xx.jian.adapter.LastTimeListAdapter;
import com.xx.jian.bean.LastTimeDataBean;
import com.xx.jian.utils.AppCacheUtil;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 清理缓存页面
 * Created by xx1994 on 2017/1/10.
 */
public class CleanCacheDiallogFragment extends DialogFragment {

    private TextView cancleBtn, sureBtn, deleteText;

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
        String cacheSize = AppCacheUtil.getCache();
        deleteText = (TextView) view.findViewById(R.id.delete_text);
        deleteText.setText("清理缓存?    " + cacheSize);
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
                AppCacheUtil.deleteCache(getActivity().getCacheDir());
                dismiss();
            }
        });
        return view;
    }
}
