package diary.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.seuxxd.miniproject.R;

import base.fragment.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import diary.view.AddDiaryPopupwindow;

/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.modules.diary.fragment
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */

public class MainDiaryFragment extends BaseFragment {

    @BindView(R.id.addDiaryBtn)
    Button addDiaryBtn;
    @BindView(R.id.lookDiaryBtn)
    Button lookDiaryBtn;
    @BindView(R.id.likeDiaryBtn)
    Button likeDiaryBtn;
    private AddDiaryPopupwindow mAddDiaryPopupwindow;

    public static MainDiaryFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(FRAGMENT_ID, id);
        MainDiaryFragment fragment = new MainDiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int setLayoutResouceId() {
        return R.layout.fragment_main_diary;
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


    @OnClick({R.id.addDiaryBtn, R.id.lookDiaryBtn, R.id.likeDiaryBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addDiaryBtn:
                showAddDiary();
                break;
            case R.id.lookDiaryBtn:
                break;
            case R.id.likeDiaryBtn:
                break;
        }
    }


    public void showAddDiary() {
        mAddDiaryPopupwindow = new AddDiaryPopupwindow(getContext());
        mAddDiaryPopupwindow.show(getRootView());
        mAddDiaryPopupwindow.setSubmitListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAddDiaryPopupwindow.dismiss();
            }
        });
    }
}
