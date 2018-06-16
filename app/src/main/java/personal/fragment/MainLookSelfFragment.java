package personal.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seuxxd.miniproject.R;
import com.example.seuxxd.miniproject.StartActivity;


import java.util.ArrayList;

import base.fragment.BaseFragment;
import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import personal.contact.LookView;
import report.activity.ReportActivity;
import utils.view.MainLookUseDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.modules.personal.fragment
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */

public class MainLookSelfFragment extends BaseFragment implements LookView {


    @BindView(R.id.headViewImg)
    ImageView headViewImg;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.useCameraBtn)
    Button useCameraBtn;
    @BindView(R.id.makeReportBtn)
    Button makeReportBtn;
    @BindView(R.id.generation_ReportLayout)
    LinearLayout generationReportLayout;
    @BindView(R.id.lookMainImg)
    ImageView lookMainImg;

    private boolean mSelectPic = false;

    public static MainLookSelfFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(FRAGMENT_ID, id);
        MainLookSelfFragment fragment = new MainLookSelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayoutResouceId() {
        return R.layout.fragment_main_lookfor;
    }

    @Override
    public void onLazyLoad() {

    }

    @Override
    public void firstLazyLoad() {

    }

    @Override
    public void initView(View rootView) {

        //后期可以根据后台传递的参数进行设置
        showCenterPic("");
        showHeaderView("");

    }

    @Override
    public void doSomethingInCreate(Bundle arguments) {

    }


    @Override
    public void showDialog() {

        PhotoPicker.builder()
                .setPhotoCount(1)
                .setPreviewEnabled(true)
                .setShowGif(false)
                .setShowCamera(true)
                .start(getContext(), this, PhotoPicker.REQUEST_CODE);


    }

    @Override
    public void dissmisDialog() {

    }

    @Override
    public void showCenterPic(String path) {

        //图片的加载我没有统一封装  就麻烦你帮忙封装一下了;圆角也没做哦  哈哈哈
        Glide.with(getContext()).load(path).apply(new RequestOptions().error(R.drawable.main_placeholder)).into(lookMainImg);



    }

    @Override
    public void showHeaderView(String path) {

        Glide.with(getContext()).load(path).into(headViewImg);

    }

    @Override
    public void showHowToUse() {
        MainLookUseDialog dialog = new MainLookUseDialog(getContext());
        dialog.setContent("这是一些测试的数据哦");
        dialog.show(getRootView());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                if (photos.size() > 0) {
                    showCenterPic(photos.get(0));
                    mSelectPic = true;
                }
            }
        }
    }


    @OnClick({R.id.headViewImg, R.id.useCameraBtn, R.id.makeReportBtn, R.id.lookMainImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headViewImg:
                break;
            case R.id.useCameraBtn:
                showDialog();
                break;
            case R.id.makeReportBtn:
                if (mSelectPic) {
//                    showToast("图片选好了");
                    startActivity(new Intent(getActivity(), ReportActivity.class));
                } else {
                    showToast(getString(R.string.please_select_pic));
                }
                break;
            case R.id.lookMainImg:
                break;
        }
    }
}
