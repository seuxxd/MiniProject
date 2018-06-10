package dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.seuxxd.miniproject.R;


public class EvaluationDialog extends DialogFragment {

    private Context mContext;
    private View mView;
    private int mGravity = Gravity.BOTTOM;
    private Dialog mDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View mContentView = LayoutInflater
                .from(mContext)
                .inflate(R.layout.page_evaluation,null);

        mDialog = new AlertDialog
                .Builder(mContext)
                .setView(mContentView)
                .setCancelable(false)
                .create();
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return mDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.Dialog_FullScreen);
    }

    public void setContentView(View view){
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void setGravity(int gravity){
        mGravity = gravity;
    }


    @Override
    public void onStart() {
        super.onStart();
//        Window mWindow = getDialog().getWindow();
        //可以通过mMetrics获取屏幕信息
//        DisplayMetrics mMetrics = new DisplayMetrics();
//        mWindow.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
//        mWindow.setLayout(mMetrics.widthPixels,mWindow.getAttributes().height);

    }

    @Override
    public void onResume() {
        super.onResume();
        Window mWindow = getDialog().getWindow();
//        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        mWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams l = mWindow.getAttributes();
        l.width = ViewGroup.LayoutParams.MATCH_PARENT;
        l.height = ViewGroup.LayoutParams.MATCH_PARENT;
        l.gravity = mGravity;
        mWindow.setAttributes(l);
    }

}
