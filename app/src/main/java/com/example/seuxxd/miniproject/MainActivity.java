package com.example.seuxxd.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import adapter.MainViewPagerAdapter;
import diary.fragment.MainDiaryFragment;
import eventbusmodel.FirstLoginNotification;
import eventbusmodel.JumpToLookMeTab;
import eventbusmodel.UpdateProductAdapter;
import fragment.BaseFragment;
import fragment.CheckFragment;
import fragment.DiaryFragment;
import fragment.RecommendFragment;
import internetmodel.register.User;
import personal.fragment.MainLookSelfFragment;
import report.activity.ReportActivity;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "MainActivity";
//    三个fragment
    private MainLookSelfFragment mMainLookSelfFragment = new MainLookSelfFragment();
    private RecommendFragment mRecommendFragment = new RecommendFragment();
    private DiaryFragment mDiaryFragment = new DiaryFragment();


    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public static boolean isFirstLogin = true;

//    保存fragment的列表
    List<Fragment> mFragmentList;


//    ViewPagerAdapter自定义
    MainViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EventBus.getDefault().register(this);


        storeUserInfo();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(MainLookSelfFragment.newInstance(MainLookSelfFragment.class.getName()));
        mFragmentList.add(mDiaryFragment);
        String[] mTitle = {"定制推荐","看看自己","美丽日记"};
        FragmentManager mManager = getSupportFragmentManager();
        mViewPagerAdapter = new MainViewPagerAdapter(mManager,getApplicationContext(),mFragmentList,mTitle);


//        界面初始化
        mTabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.fragment_container);

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0 ; i < mTitle.length ; i ++){
            TabLayout.Tab mTab = mTabLayout.getTabAt(i);
            mTab.setCustomView(mViewPagerAdapter.getCustomView(i));
        }

        //初始化在第二页
        TabLayout.Tab mTab =  mTabLayout.getTabAt(1);
        if (mTab != null){
            mTab.select();
            TextView mText = (TextView) mTab.getCustomView().findViewById(R.id.tab_text);
            ImageView mImage = (ImageView) mTab.getCustomView().findViewById(R.id.tab_image);
            mText.setTextColor(Color.RED);
            mImage.setImageResource(R.drawable.p2);
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.i(TAG, "onTabSelected: " + position);
                if (position == 0){
                    if (isFirstLogin){
                        EventBus.getDefault().post(new FirstLoginNotification());
                        Log.i(TAG, "onTabSelected: first");
                    }
                    else{
                        EventBus.getDefault().post(new UpdateProductAdapter());
                        Log.i(TAG, "onTabSelected: update");
                    }

                }

                TextView mTextView = tab.getCustomView().findViewById(R.id.tab_text);
                ImageView mImage = (ImageView) tab.getCustomView().findViewById(R.id.tab_image);
                int[] mInts = {R.drawable.p4,R.drawable.p2,R.drawable.p6};
                mImage.setImageResource(mInts[position]);
                mTextView.setTextColor(Color.RED);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView mTextView = tab.getCustomView().findViewById(R.id.tab_text);
                mTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                ImageView mImage = (ImageView) tab.getCustomView().findViewById(R.id.tab_image);
                int[] mInts = {R.drawable.p3,R.drawable.p1,R.drawable.p5};
                mImage.setImageResource(mInts[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private void storeUserInfo() {
        Intent mIntent = getIntent();
        User mUser = mIntent.getParcelableExtra("user");
        SharedPreferences mSP = getSharedPreferences("user",MODE_PRIVATE);
        if (mUser != null){
            mSP.edit()
                    .putString("username",mUser.getUsername())
                    .putString("password",mUser.getPassword())
                    .putString("nickname",mUser.getNickname())
                    .putString("sex",mUser.getSex())
                    .putString("birthday",mUser.getBirthday())
                    .apply();
        }

    }

    /**
     *
     * 连续两下后退退出APP
     */
    private int mCount = 0;
    private long mFirstTime = 0;
    private long mSecondTime = 0;

    @Override
    public void onBackPressed() {
        if (mCount == 0){
            mCount ++;
            mFirstTime = System.currentTimeMillis();
            Toast.makeText(this, "再点击一下退出应用", Toast.LENGTH_SHORT).show();
        }
        else if (mCount == 1){
            mSecondTime = System.currentTimeMillis();
            if (mSecondTime - mFirstTime > 1000){
                mCount = 1;
                mFirstTime = mSecondTime;
                mSecondTime = 0;
            }
            else {
                mCount = 0;
                mFirstTime = 0;
                mSecondTime = 0;
                finish();
            }
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent: ");
        if (intent != null){
            String mDest = intent.getStringExtra("dest");
            if (mDest != null){
                switch (mDest){
                    case ReportActivity.JUMP_TO_DIARY:
                        Log.i(TAG, "onNewIntent: diary");
                        mTabLayout.getTabAt(2).select();
                        break;
                    case ReportActivity.JUMP_TO_RECOMMEND:
                        Log.i(TAG, "onNewIntent: recommend");
                        mTabLayout.getTabAt(0).select();
                        EventBus.getDefault().post(new UpdateProductAdapter());
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void jumpToLookMe(JumpToLookMeTab jumpToLookMeTab){
        mTabLayout.getTabAt(1).select();
    }

    public TabLayout getTabLayout(){
        return mTabLayout;
    }
}
