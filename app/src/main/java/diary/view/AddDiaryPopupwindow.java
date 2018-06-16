package diary.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.seuxxd.miniproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.tools.DateUtils;

/**
 * Created by mr.gao on 2018/6/13.
 * Package:    com.mrwho.skindetection.modules.diary.view
 * Create Date:2018/6/13
 * Project Name:SkinDetection
 * Description:
 */

public class AddDiaryPopupwindow extends PopupWindow {
    private Context mContext;
    private View mView;
    private ViewHolder mViewHolder;
    private float mDarkBackgroundDegree = 0.6f;

    private SharedPreferences sp;
    public AddDiaryPopupwindow(Context context) {
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.add_diary, null);
        mViewHolder = new ViewHolder(mView);

        setContentView(mView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.TranslatePopupWindow);
        sp = context.getSharedPreferences("diary",Context.MODE_PRIVATE);
        restoreInfo(sp);
        mViewHolder.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeInfo(sp);
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
    }

    public void restoreInfo(SharedPreferences sp) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.CHINA);
        String now = format.format(date);
        if (now.equals(sp.getString("date","0000-00-00"))){
            mViewHolder.foodEdit.setText(sp.getString("food",""));
            mViewHolder.sportEdit.setText(sp.getString("sport",""));
            mViewHolder.otherEdit.setText(sp.getString("other",""));
            mViewHolder.moodRatingBar.setRating(sp.getFloat("mood",1));
            mViewHolder.skinRatingBar.setRating(sp.getFloat("skin",1));
        }
        else
            sp.edit().clear().apply();
    }

    public void storeInfo(SharedPreferences sp) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.CHINA);
        sp.edit()
                .putString("date",format.format(date))
                .putString("food",getFoodEditText())
                .putString("sport",getSportEditText())
                .putString("other",getOtherEditText())
                .putFloat("mood",getMoodStar())
                .putFloat("skin",getSkinStar()).apply();
        close();
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgcolor;
        if (bgcolor == 1) {
            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        ((Activity) mContext).getWindow().setAttributes(lp);
    }


    public void show(View rootView) {
        darkenBackground(mDarkBackgroundDegree);//设置背景框为灰色
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 获取饮食
     *
     * @return
     */
    public String getFoodEditText() {
        return mViewHolder.foodEdit.getText().toString().trim();
    }
    public void setFoodEditText(String s){
        mViewHolder.foodEdit.setText(s);
    }

    /**
     * 运动
     *
     * @return
     */
    public String getSportEditText() {
        return mViewHolder.sportEdit.getText().toString().trim();

    }
    public void setSportEditText(String s){
        mViewHolder.sportEdit.setText(s);
    }

    /**
     * 其他的
     *
     * @return
     */
    public String getOtherEditText() {
        return mViewHolder.otherEdit.getText().toString().trim();
    }
    public void setOtherEditText(String s){
        mViewHolder.otherEdit.setText(s);
    }
    /**
     * 心情的个数
     *
     * @return
     */
    public float getMoodStar() {
        return mViewHolder.moodRatingBar.getRating();
    }
    public void setMoodStar(float f){
        mViewHolder.moodRatingBar.setRating(f);
    }

    /**
     * 肌肤的个数
     *
     * @return
     */
    public float getSkinStar() {
        return mViewHolder.skinRatingBar.getRating();
    }
    public void setSkinStar(float f){
        mViewHolder.skinRatingBar.setRating(f);
    }

    /**
     * 监听提交
     *
     * @param listener
     */
    public void setSubmitListener(View.OnClickListener listener) {
        if (listener != null) {
            mViewHolder.submitBtn.setOnClickListener(listener);

        }
    }

    public void setTime(Date date){
        mViewHolder.time.setText(DateUtils.setDate(date));
    }

    /**
     * 关闭
     */
    public void close() {
        dismiss();
        mViewHolder.skinRatingBar.setRating(1);
        mViewHolder.moodRatingBar.setRating(1);
        mViewHolder.otherEdit.setText("");
        mViewHolder.foodEdit.setText("");
        mViewHolder.sportEdit.setText("");
    }

     static class ViewHolder {
        @BindView(R.id.back)
        ImageView back;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.foodEdit)
        EditText foodEdit;
        @BindView(R.id.sportEdit)
        EditText sportEdit;
        @BindView(R.id.otherEdit)
        EditText otherEdit;
        @BindView(R.id.moodRatingBar)
        RatingBar moodRatingBar;
        @BindView(R.id.skinRatingBar)
        RatingBar skinRatingBar;
        @BindView(R.id.submitBtn)
        Button submitBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
