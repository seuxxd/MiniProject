package qqlistener;

import android.util.Log;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class BaseListener implements IUiListener {
    public static final String TAG = "BaseListener";
    @Override
    public void onComplete(Object o) {
        Log.i(TAG, "onComplete: ");
        Log.i(TAG, "onComplete: " + o);
    }

    @Override
    public void onError(UiError uiError) {
        Log.i(TAG, "onError: ");
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "onCancel: ");
    }
}
