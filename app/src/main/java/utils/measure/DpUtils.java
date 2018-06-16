package utils.measure;

import android.content.Context;

/**
 * Created by mr.gao on 2018/6/12.
 * Package:    com.mrwho.skindetection.utils.measure
 * Create Date:2018/6/12
 * Project Name:SkinDetection
 * Description:
 */

public class DpUtils {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
