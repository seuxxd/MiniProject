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

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import base.activity.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;
import personal.fragment.MainLookSelfFragment;
import utils.tools.GetJsonByImage;
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
    ImageView userImage;     //用户自拍头像
    @BindView(R.id.closeImg)
    ImageView closeImg;      //关闭头像
    @BindView(R.id.poetryTv)
    TextView poetryTv;     //诗句
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;    //肌肤状况，5颗星
    @BindView(R.id.progressBarOne)
    RoundCornerProgressBar progressBarOne;      //色斑程度  0-100
    @BindView(R.id.progressBarTwo)
    RoundCornerProgressBar progressBarTwo;    //痘痘程度 0-100
    @BindView(R.id.progressBarThree)
    RoundCornerProgressBar progressBarThree;    //黑眼圈程度 0-100
    @BindView(R.id.reportSelf)
    Button reportSelf;    //专属定制
    @BindView(R.id.importDrary)
    Button importDrary;   //导入日记

    @BindView(R.id.stainTextView)
    TextView stainTextView;
    @BindView(R.id.acneTextView)
    TextView acneTextView;
    @BindView(R.id.dark_circleTextView)
    TextView dark_circleTextView;


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
        String imagePath = MainLookSelfFragment.ImagePath;
        File imageFile = MainLookSelfFragment.ImageFile;

        System.out.println("imagePath: " + imagePath + " imageFile :" + imageFile);

        //部分测试的数据
        //Glide.with(this).load(R.drawable.main_placeholder).into(userImage);
        Glide.with(this).load(imagePath).into(userImage);

        HashMap<String,String> resultMap = null;

        try {
            resultMap = GetJsonByImage.getJsonByFaceApi(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


        float healthValue = Float.parseFloat(resultMap.get("health"));   //健康
        float stainValue =  Float.parseFloat(resultMap.get("stain"));    //色斑
        float acneValue =  Float.parseFloat(resultMap.get("acne"));      //青春痘
        float dark_circle =  Float.parseFloat(resultMap.get("dark_circle"));     //黑眼圈

        float numstart = 3.0f;
        numstart += (healthValue / 25);
        if(numstart >= 5) numstart = 5;

        System.out.println("getDetail: " + numstart + " " + healthValue + " " + stainValue + " " + acneValue + " " + dark_circle);

        headerView.setImageResource(R.drawable.main_placeholder);

        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        timeTv.setText(format.format(date));

        ratingBar.setRating(numstart);
        progressBarOne.setProgress(stainValue);     //色斑程度
        stainTextView.setText((int)(stainValue) + "%");
        progressBarTwo.setProgress(acneValue);   //痘痘程度
        acneTextView.setText((int)(acneValue) + "%");
        progressBarThree.setProgress(dark_circle);     //黑眼圈程度
        dark_circleTextView.setText((int)(dark_circle) + "%");
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
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        String sdCardPath = Environment.getExternalStorageDirectory().getPath();
//                imagePath = sdCardPath + File.separator + "DCIM/" + System.currentTimeMillis()+ "screenshot.png";
//                imagePath = sdCardPath + File.separator + "DCIM/" + "screenshot.png";
        String imagePath = sdCardPath + File.separator + "screenshot.png";
        Log.e("开始截图","需要点运气"+imagePath);
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
