package dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.seuxxd.miniproject.R;


public class EvaluationDialog extends DialogFragment {

    private Context mContext;
    private View mView;
    private int mGravity;
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
                .Builder(mContext, R.style.Dialog_FullScreen)
                .setView(mContentView)
                .create();
        return mDialog;
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
    public void onResume() {
        super.onResume();
        Window mWindow = getDialog().getWindow();
        WindowManager.LayoutParams l = mWindow.getAttributes();
        l.width = ViewGroup.LayoutParams.MATCH_PARENT;
        l.height = ViewGroup.LayoutParams.MATCH_PARENT;
        l.gravity = mGravity;
        mWindow.setAttributes(l);
    }
}
