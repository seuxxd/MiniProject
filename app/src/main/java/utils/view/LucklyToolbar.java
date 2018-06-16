package utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seuxxd.miniproject.R;


public class LucklyToolbar extends RelativeLayout {

    ImageView leftImage;
    TextView leftTv;

    TextView title;
    ImageView rightImg;
    TextView rightTv;
    RelativeLayout toolbar;


    public LucklyToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public LucklyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.title_toolbar, this);
        leftImage = $(this, R.id.leftImage);
        leftTv = $(this, R.id.leftTv);
        title = $(this, R.id.title);
        rightImg = $(this, R.id.rightImg);
        rightTv = $(this, R.id.rightTv);
        toolbar = $(this, R.id.toolbar);
        leftImage.setVisibility(INVISIBLE);
        rightImg.setVisibility(INVISIBLE);
    }


    public void setLeftImageResource(int resourceId) {
        leftImage.setImageResource(resourceId);

    }

    public void setRightImageResource(int resourceid) {
        rightImg.setImageResource(resourceid);

    }

    public void setLeftText(String leftText) {
        leftTv.setText(leftText);
    }


    public void setLeftTextColor(int color) {
        leftTv.setTextColor(getResources().getColor(color));

    }

    public void setLeftTextSize(float size) {
        leftTv.setTextSize(size);

    }

    public void setRightText(String leftText) {
        rightTv.setText(leftText);
    }

    public void setRightTextColor(int color) {
        rightTv.setTextColor(getResources().getColor(color));

    }

    public void setRightTextSize(float size) {
        rightTv.setTextSize(size);

    }

    public void setTitleColor(int color) {
        title.setTextColor(getResources().getColor(color));
    }

    public void setTitle(String titleString) {
        title.setText(titleString);

    }

    public void setTitleSize(float size) {
        title.setTextSize(size);

    }



    public void setBackImageListener(OnClickListener clickListener) {
        leftImage.setVisibility(VISIBLE);
        leftTv.setVisibility(GONE);
        leftImage.setOnClickListener(clickListener);
    }

    public void setLeftTextListener(OnClickListener clickListener) {
        leftTv.setOnClickListener(clickListener);
    }

    public void setRightImageListener(OnClickListener clickListener) {
        rightImg.setOnClickListener(clickListener);
    }

    public void setRightTextListener(OnClickListener clickListener) {
        rightTv.setOnClickListener(clickListener);
    }


    public void setLeftImageVisiable(boolean visiable) {
        if (visiable) {
            leftImage.setVisibility(VISIBLE);
            leftTv.setVisibility(GONE);
        } else {
            leftImage.setVisibility(GONE);
            leftTv.setVisibility(VISIBLE);
        }
    }

    public void setRightAllInVisiable(boolean inVisiable){
        if (inVisiable) {
            rightImg.setVisibility(GONE);
            rightTv.setVisibility(GONE);
        }
    }
    public void setLeftAllInVisiable(boolean inVisiable){
        if (inVisiable) {
            leftImage.setVisibility(GONE);
            leftTv.setVisibility(GONE);
        }
    }

    public void setRightImageVisiable(boolean visiable) {
        if (visiable) {
            rightImg.setVisibility(VISIBLE);
            rightTv.setVisibility(GONE);
        } else {
            rightImg.setVisibility(GONE);
            rightTv.setVisibility(VISIBLE);
        }
    }

    public static <T extends View> T $(View view, int resourceId) {
        if (view != null) {
            return (T) view.findViewById(resourceId);
        } else {
            return null;
        }
    }

    public TextView getTitle() {
        return title;
    }
}
