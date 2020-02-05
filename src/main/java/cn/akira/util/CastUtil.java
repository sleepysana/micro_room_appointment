package cn.akira.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CastUtil {
    public static String formatDate(long timeStamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp * 1000));
    }
    public static String formatDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }
}
