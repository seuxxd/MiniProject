package tencent;

import android.content.Context;

import com.tencent.tauth.Tencent;

public class SingleTencent {
    private Context mContext;
    private static final String APP_ID = "1106890263";
    volatile static Tencent mTencent;
    public static Tencent getInstance(Context context){
        if (mTencent == null){
            synchronized (Tencent.class){
                if (mTencent == null){
                    mTencent = Tencent.createInstance(APP_ID,context);
                }
            }
        }
        return mTencent;
    }
}
