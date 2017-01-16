package com.xx.jian.view.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.xx.jian.BaseActivity;
import com.xx.jian.R;
import com.xx.jian.bean.LastTimeDataBean;
import com.xx.jian.view.fragment.LastTimeCardFragment;
import com.xx.jian.view.fragment.LastTimeListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 上次展示主界面
 */
public class LastTimeMainActivity extends FragmentActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private ImageView addBtn, choiceBtn;
    private Realm realm;
    //记录当前选择的页面
    private boolean isListFrame = true;
    //列表页Fragment
    private LastTimeListFragment lastTimeListFragment;
    //卡片也Fragment
    private LastTimeCardFragment lastTimeCardFragment;

    private List<Map<String, String>> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_time_main);
        initView();
    }

    private void initView() {
        realm = Realm.getInstance(this);
        toolbar = (Toolbar) findViewById(R.id.add_main_toobar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addBtn = (ImageView) findViewById(R.id.add_last_time);
        choiceBtn = (ImageView) findViewById(R.id.choice);
        addBtn.setOnClickListener(this);
        choiceBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromRealm();
        //初始化为列表页的Fragment
        choiceBtn.setImageResource(R.drawable.choice_normal);
        isListFrame = true;
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        lastTimeListFragment = new LastTimeListFragment();
        ft.replace(R.id.last_time_content, lastTimeListFragment);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_last_time:
                Intent intent = new Intent(LastTimeMainActivity.this, LastTimeAddActivity.class);
                startActivity(intent);
                break;
            case R.id.choice:
                FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
                if (isListFrame) {
                    isListFrame = false;
                    choiceBtn.setImageResource(R.drawable.choice_pressed);
                    if (lastTimeCardFragment == null) {
                        lastTimeCardFragment = new LastTimeCardFragment();
                    }
                    ft.replace(R.id.last_time_content, lastTimeCardFragment);
                } else {
                    isListFrame = true;
                    choiceBtn.setImageResource(R.drawable.choice_normal);
                    if (lastTimeListFragment == null) {
                        lastTimeListFragment = new LastTimeListFragment();
                    }
                    ft.replace(R.id.last_time_content, lastTimeListFragment);
                }
                ft.commit();
                break;
        }
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
