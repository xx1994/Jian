package com.xx.jian.view.fragment;


import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.xx.jian.R;
import com.xx.jian.adapter.MyImageAdapter;

import java.util.List;

/**
 * 删除照片提示对话框
 * Created by Administrator on 2016/12/13.
 */
@SuppressLint("ValidFragment")
public class MyDialogFragment extends DialogFragment {

    private TextView cancleBtn, sureBtn;
    private List<String> mResult;
    private int position;
    private MyImageAdapter myImageAdapter;

    public MyDialogFragment(List<String> mResult, int position, MyImageAdapter myImageAdapter) {
        this.mResult = mResult;
        this.position = position;
        this.myImageAdapter = myImageAdapter;
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
                mResult.remove(position);
                myImageAdapter.notifyDataSetChanged();
                dismiss();
            }
        });
        return view;
    }

}
