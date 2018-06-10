package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;


import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import imageloader.GlideImageLoader;


public class StartActivity extends AppCompatActivity{

//    private TextView mUnLoginJumpTextView;
    private ImageButton mQQLoginButton;
    private Banner mBanner;

    public static final int GUEST_LOGIN = 1;
    public static final int QQ_LOGIN = 2;

    private static final String TAG = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(TAG, "onCreate: ");

        initView();

        initListener();

        initBanner();
    }




    /**
     * 初始化所有的UI组件
     */
    private void initView(){

//        mUnLoginJumpTextView = (TextView) findViewById(R.id.unLogin_jump);
        mQQLoginButton = (ImageButton) findViewById(R.id.qq_login);
        mBanner = (Banner) findViewById(R.id.banner);

//        mUnLoginJumpTextView.setClickable(true);
    }

    /**
     * 初始化事件监听器，可能banner会拦截事件，所以要在banner之前设置好监听器
     */
    private void initListener(){
//        mUnLoginJumpTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mUnLoginIntent = new Intent(StartActivity.this,MainActivity.class);
//                mUnLoginIntent.putExtra("username","guest");
//                mUnLoginIntent.putExtra("type",GUEST_LOGIN);
//                Log.i(TAG, "onClick: unlogin");
//                startActivity(mUnLoginIntent);
//                finish();
//            }
//        });

        mQQLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,QQLoginSuccessActivity.class));
                finish();
            }
        });
    }

    /**
     * 初始化首页banner的各项属性
     */
    private void initBanner() {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setImageLoader(new GlideImageLoader());
        List<String> mImageList = new ArrayList<>();
        mImageList.add("http://101.132.154.189/test/1.jpg");
        mImageList.add("http://101.132.154.189/test/2.jpg");
        mImageList.add("http://101.132.154.189/test/3.jpg");
        mBanner.setImages(mImageList);
        mBanner.setBannerAnimation(Transformer.CubeIn);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(6000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
    }


}
