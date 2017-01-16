package com.xx.jian;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 进入的一个两秒的动画界面
 * Created by xx1994 on 2016/12/27.
 */
public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private ImageView splashImg;
    private View textBgFragment;
    private TextView jianTextView, jiTextView, dianTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_view);
        splashImg = (ImageView) findViewById(R.id.splash_img);
        textBgFragment = findViewById(R.id.splash_text_bg);
        jianTextView = (TextView) findViewById(R.id.splash_text_jian);
        jiTextView = (TextView) findViewById(R.id.splash_text_ji);
        dianTextView = (TextView) findViewById(R.id.splash_text_dian);
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.splash_anim);
        animation.setDuration(3000);
        splashImg.startAnimation(animation);
        initFrist();
        setPropertyAnima();
    }

    private void initFrist() {
        /**2秒内进入登陆页面*/
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent2);
                finish();
            }
        }, 4000);
    }

    //設置属性动画
    private void setPropertyAnima() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(jianTextView, "alpha", 0f, 1f);
        animator.setDuration(1500);
        animator.start();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(jiTextView, "alpha", 0f, 1f);
        animator1.setDuration(2000);
        animator1.start();
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(dianTextView, "alpha", 0f, 1f);
        animator2.setDuration(3000);
        animator2.start();
    }
}
