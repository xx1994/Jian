package com.xx.jian.view.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xx.jian.BaseActivity;
import com.xx.jian.R;
import com.xx.jian.adapter.MyImageAdapter;
import com.xx.jian.bean.LastTimeDataBean;
import com.xx.jian.utils.Imager;
import com.xx.jian.view.fragment.MyDialogFragment;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * 添加上次内容
 */
public class LastTimeAddActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    //添加按钮
    private TextView addBtn;
    //添加图片
    private ImageView addImg;
    //填写文字内容
    private EditText editText;
    private GridView gridView;
    //规定相片张数
    private int imgMaxSize = 1;
    private ArrayList<String> mResults = new ArrayList<>();
    private MyImageAdapter myImageAdapter;
    private static final int REQUEST_CODE = 732;

    /**
     * 输入的内容备注
     */
    private String editDes;
    /**
     * 选择的图片路径
     */
    private String imgPath;
    private Realm realm;


    @Override
    protected int initContentView() {
        return R.layout.activity_last_time_add;
    }

    @Override
    protected void initView() {
        Fresco.initialize(LastTimeAddActivity.this);
        realm = Realm.getInstance(this);
        addBtn = (TextView) findViewById(R.id.sureBtn);
        addImg = (ImageView) findViewById(R.id.last_time_addimg);
        editText = (EditText) findViewById(R.id.add_text);
        gridView = (GridView) findViewById(R.id.gridview);
        toolbar = (Toolbar) findViewById(R.id.add_last_time_toobar);
        toolbar.setNavigationIcon(R.drawable.back);
    }

    @Override
    protected void initListener() {
        addBtn.setOnClickListener(this);
        addImg.setOnClickListener(this);
        gridView.setOnItemLongClickListener(this);
        gridView.setOnItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sureBtn:
                editDes = editText.getText().toString().trim();
                if (!editDes.isEmpty() && imgPath != null) {
                    realm.beginTransaction();
                    LastTimeDataBean lastTimeDataBean = realm.createObject(LastTimeDataBean.class);
                    lastTimeDataBean.setImgDes(editDes);
                    lastTimeDataBean.setImgPth(imgPath);
                    realm.commitTransaction();
                    finish();
                } else {
                    Toast.makeText(this, "描述和图片不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.last_time_addimg:     //打开进行选择图片
                Intent intent = new Intent(LastTimeAddActivity.this, ImagesSelectorActivity.class);
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, imgMaxSize);
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                imgPath = mResults.get(0);   //只放一张图片直接选择0位置的图片路径
                myImageAdapter = new MyImageAdapter(LastTimeAddActivity.this, mResults, imgMaxSize);
                addImg.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                gridView.setAdapter(myImageAdapter);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 长按点击删除选中图片
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MyDialogFragment myDialogFragment = new MyDialogFragment(mResults, position, myImageAdapter);
        myDialogFragment.show(getFragmentManager(), "dads");
        return true;
    }

    /**
     * gridView点击加号继续添加图片
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点到后面继续添加图片按钮
        if (position == mResults.size()) {
            Intent intent = new Intent(LastTimeAddActivity.this, ImagesSelectorActivity.class);
            intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, imgMaxSize);
            intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
            intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
            intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}
