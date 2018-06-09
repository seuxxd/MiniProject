package com.example.seuxxd.miniproject;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import adapter.MainViewPagerAdapter;
import fragment.BaseFragment;
import fragment.CheckFragment;
import fragment.DiaryFragment;
import fragment.RecommendFragment;

public class MainActivity extends AppCompatActivity {

//    三个fragment
    private CheckFragment mCheckFragment = new CheckFragment();
    private RecommendFragment mRecommendFragment = new RecommendFragment();
    private DiaryFragment mDiaryFragment = new DiaryFragment();



//    保存fragment的列表
    List<BaseFragment> mFragmentList;


//    ViewPagerAdapter自定义
    MainViewPagerAdapter mViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(mRecommendFragment);
        mFragmentList.add(mCheckFragment);
        mFragmentList.add(mDiaryFragment);
        String[] mTitle = {"定制推荐","看看自己","美丽日记"};
        FragmentManager mManager = getSupportFragmentManager();
        mViewPagerAdapter = new MainViewPagerAdapter(mManager,getApplicationContext(),mFragmentList,mTitle);


//        界面初始化
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.fragment_container);

        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0 ; i < mTitle.length ; i ++){
            TabLayout.Tab mTab = mTabLayout.getTabAt(i);
            mTab.setCustomView(mViewPagerAdapter.getCustomView(i));
        }

        mTabLayout.getTabAt(1).select();

    }

    /**
     *
     * 连续两下后退退出APP
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getAction()){
            case KeyEvent.ACTION_DOWN:
        }
        return super.onKeyDown(keyCode, event);
    }
}
