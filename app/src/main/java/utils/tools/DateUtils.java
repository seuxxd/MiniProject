package utils.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mr.gao on 2018/6/13.
 * Package:    com.mrwho.skindetection.utils.tools
 * Create Date:2018/6/13
 * Project Name:SkinDetection
 * Description:
 */

public class DateUtils {


    public static String setDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }
}
