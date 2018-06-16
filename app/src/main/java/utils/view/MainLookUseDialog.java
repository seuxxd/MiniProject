package utils.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.seuxxd.miniproject.R;

import utils.measure.DpUtils;


/**
 * Created by mr.gao on 2018/6/13.
 * Package:    com.mrwho.skindetection.utils.view
 * Create Date:2018/6/13
 * Project Name:SkinDetection
 * Description:
 */

public class MainLookUseDialog extends PopupWindow implements View.OnClickListener {
    private View mView;
    private ImageView mCloseImg;
    private TextView mTextView;
    private Context mContext;
    private float mDarkBackgroundDegree = 0.6f;


    public MainLookUseDialog(Context context) {
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.main_look_use_dialog, null);
        mCloseImg = $(mView, R.id.closeImg);
        mTextView = $(mView, R.id.useContextTv);


        setContentView(mView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(DpUtils.dip2px(context, 200));
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(R.style.TranslatePopupWindow);
        mCloseImg.setOnClickListener(this);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
    }


    /**
     * @param rootView
     * @param resourceId
     * @param <T>
     * @return
     */
    private <T extends View> T $(View rootView, int resourceId) {
        return (T) rootView.findViewById(resourceId);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.closeImg:
                dismiss();
                break;
        }
    }


    public void setContent(String content) {
        mTextView.setText(content);

    }

    public void show(View rootView) {
        darkenBackground(mDarkBackgroundDegree);//设置背景框为灰色
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);

    }
}
