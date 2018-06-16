package recommend.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.seuxxd.miniproject.R;

import base.fragment.BaseFragment;


/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.modules.recommend.fragment
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */

public class MainRecommendFragment extends BaseFragment {

    public static MainRecommendFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_ID, id);
        MainRecommendFragment fragment = new MainRecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutResouceId() {
        return R.layout.fragment_main_recommend;
    }

    @Override
    public void onLazyLoad() {

    }

    @Override
    public void firstLazyLoad() {

    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    public void doSomethingInCreate(Bundle arguments) {

    }
}
