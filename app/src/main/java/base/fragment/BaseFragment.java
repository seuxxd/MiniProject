package base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import base.activity.BaseActivity;
import base.presenter.IView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.base.fragment
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */


public abstract class BaseFragment extends Fragment implements IView {

    public static String FRAGMENT_ID = "fragment_id";

    public BaseActivity mBaseActivity = null;
    /**
     * 根view
     */
    private View mRootView;

    /**
     * 是否对用户可见
     */
    private boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完oncreatview,View的初始化方法后方法后即为true
     */
    protected boolean mIsPrepare = false;

    private boolean mIsFirstLoading = true;

    public boolean mActivityCreate = false;

    private Unbinder mUnbinder = null;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doSomethingInCreate(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(setLayoutResouceId(), container, false);
        }

        mUnbinder = ButterKnife.bind(this, mRootView);
        initView(mRootView);
        mIsPrepare = true;
        mIsFirstLoading = true;
        onVisibleToUser();
        return mRootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            onVisibleToUser();
        }
    }


    /**
     * 用户可见时执行的操作
     */
    public void onVisibleToUser() {
        if (mIsPrepare && mIsVisible && mBaseActivity != null) {
            onLazyLoad();
        }

        if (mIsPrepare && mIsVisible && mIsFirstLoading && mBaseActivity != null) {
            firstLazyLoad();
            mIsFirstLoading = false;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivityCreate = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepare = false;
        mIsFirstLoading = false;
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

    }

    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 设置资源id
     *
     * @return
     */
    public abstract int setLayoutResouceId();

    /**
     * 懒加载，只有当Fragment对用户可见的时候才加载数据
     */
    public abstract void onLazyLoad();

    /**
     * fragment第一次可见
     */
    public abstract void firstLazyLoad();

    /**
     * 在onCreateView中初始化一些view
     */
    public abstract void initView(View rootView);

    /**
     * 在onCreate中加载数据
     *
     * @param arguments
     */
    public abstract void doSomethingInCreate(Bundle arguments);


}
