package utils.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mr.gao on 2018/6/13.
 * Package:    com.mrwho.skindetection.utils.tools
 * Create Date:2018/6/13
 * Project Name:SkinDetection
 * Description:
 */

public class DateUtils {


    public static String setDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }
}
