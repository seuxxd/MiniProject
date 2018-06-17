package report.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.example.seuxxd.miniproject.R;

import java.io.File;
import java.io.FileOutputStream;

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

        toolbar.setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshot();
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

    private void screenshot()
    {
        // 获取屏幕
        String imagePath;
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
//                imagePath = sdCardPath + File.separator + "DCIM/" + System.currentTimeMillis()+ "screenshot.png";
//                imagePath = sdCardPath + File.separator + "DCIM/" + "screenshot.png";
        imagePath = sdCardPath + File.separator + "screenshot.png";
        Log.e("开始截图","需要点运气" + imagePath);
        File file = new File(imagePath);
        if (bmp != null)
        {
            try {
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                String mUri  = MediaStore.Images.Media.insertImage(getContentResolver(), file.getPath(), "name", null);
                Log.e("木日","需要点运气"+mUri);

                Context context =  getApplicationContext();

                if (mUri!=null)
                {
                    Toast.makeText(context,"保存至" + imagePath,Toast.LENGTH_SHORT).show();
                    // 最后通知图库更新
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(mUri));
                    intent.setData(uri);
                    sendBroadcast(intent);


                }else
                {
                    Toast.makeText(context,"截图失败",Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {

            }
        }
    }

}
