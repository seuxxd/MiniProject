package report.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.R;

import base.activity.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import utils.view.LucklyToolbar;

public class ReportActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    LucklyToolbar toolbar;
    @BindView(R.id.headerView)
    ImageView headerView;
    @BindView(R.id.userNameTv)
    TextView userNameTv;
    @BindView(R.id.timeTv)
    TextView timeTv;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.closeImg)
    ImageView closeImg;
    @BindView(R.id.poetryTv)
    TextView poetryTv;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.progressBarOne)
    RoundCornerProgressBar progressBarOne;
    @BindView(R.id.progressBarTwo)
    RoundCornerProgressBar progressBarTwo;
    @BindView(R.id.progressBarThree)
    RoundCornerProgressBar progressBarThree;
    @BindView(R.id.reportSelf)
    Button reportSelf;
    @BindView(R.id.importDrary)
    Button importDrary;

    @Override
    public int setLayoutResourceID() {
        return R.layout.activity_report;
    }

    @Override
    public void initView() {
        toolbar.setLeftImageVisiable(false);
        toolbar.setTitle(getString(R.string.report_title));
        toolbar.setLeftText(getString(R.string.report_reray));
        toolbar.setRightImageVisiable(false);
        toolbar.setRightText(getString(R.string.report_save));

        toolbar.setLeftTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //部分测试的数据
        Glide.with(this).load(R.drawable.main_placeholder).into(userImage);
        progressBarOne.setProgress(80);
        progressBarThree.setProgress(20);
        progressBarTwo.setProgress(40);
    }


    @OnClick({R.id.closeImg, R.id.reportSelf, R.id.importDrary})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.closeImg:
                break;
            case R.id.reportSelf:
                break;
            case R.id.importDrary:
                break;
        }
    }


}
