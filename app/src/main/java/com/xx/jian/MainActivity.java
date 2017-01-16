package com.xx.jian;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.xx.jian.net.NetworkUtil;
import com.xx.jian.view.fragment.LeftMenuFragmet;
import com.xx.jian.view.fragment.MainContentFragment;

public class MainActivity extends SlidingFragmentActivity {

    private LeftMenuFragmet leftMenuFragmet;
    private Toolbar toolbar;
    private SlidingMenu slidingMenu;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new MainContentFragment());
        // 初始化SlideMenu
        initLeftMenu();
        checkUserPermision();

        //检查网络不可用进行提示
        if (!NetworkUtil.isOpenNetwork(this)) {
            Toast.makeText(this, "网络不可用,请重新连接网络！", Toast.LENGTH_SHORT).show();
        }
        toolbar = (Toolbar) findViewById(R.id.main_toobar);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.showMenu();
            }
        });
    }

    //替换主界面显示内容
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, fragment);
        fragmentTransaction.commit();
    }

    //左边侧拉栏内容
    private void initLeftMenu() {
        setBehindContentView(R.layout.left_menu_frame);
        slidingMenu = getSlidingMenu();
        /**
         * 设置侧拉模式
         * sliding.TOUCHMODE_FULLSCREEN  全屏有效
         * sliding.TOUCHMODE_MARGIN    边缘有效
         * sliding.TOUCHMODE_NONE      侧拉不出来
         * */
        slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_MARGIN);
        //左侧栏目的宽度
        slidingMenu.setBehindWidth((int) (MainActivity.this.getWindowManager().getDefaultDisplay().getWidth() * 0.8));
        /**设置拉出的模式*/
        slidingMenu.setMode(slidingMenu.LEFT);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        //  画出左边的线
        slidingMenu.setShadowDrawable(R.drawable.shapd);
        /**线的宽度*/
        slidingMenu.setShadowWidthRes(R.dimen.base3dp);
        /**fragment替换左侧菜单*/
        leftMenuFragmet = new LeftMenuFragmet();
        getSupportFragmentManager().beginTransaction().replace(R.id.left_menu_frame, leftMenuFragmet).commit();
    }

    private void checkUserPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    PERMISSIONS, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
