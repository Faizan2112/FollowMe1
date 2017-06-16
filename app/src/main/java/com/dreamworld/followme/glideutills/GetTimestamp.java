package com.dreamworld.followme.glideutills;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class GetTimestamp {

    private static String defaultDateFormat = "yyyyMMddHHmmss";

    public static String getTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long time = timestamp.getTime();
        return String.valueOf(time);
    }

    public static String getTimestamp(String dateFormat) {
        if (!TextUtils.isEmpty(dateFormat)) {
            defaultDateFormat = dateFormat;
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long time = timestamp.getTime();
        SimpleDateFormat format = new SimpleDateFormat(defaultDateFormat);
        return format.format(time);
    }
    public static long getTimestampF() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long time = timestamp.getTime();
        return time;
    }

}
