package com.lad.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Andy
 * @date 2023-6-26 026 22:15
 */
public class TimestampUtils {
    public static String toString(Date date){


        // 创建SimpleDateFormat对象，指定日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 将Date对象格式化为字符串
        return formatter.format(date);
    }
}
