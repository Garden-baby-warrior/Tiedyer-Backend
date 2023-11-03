package com.cnzakii.tiedyer.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * 时间，日期相关的工具类
 *
 * @author Zaki
 * @since 2023-11-03
 **/
public class MyDateTimeUtils {

    /**
     * 计算当前时间距离当天午夜（24点）还有多少秒。
     *
     * @return 距离午夜的秒数
     */
    public static long secondsUntilMidnight() {
        // 获取当前日期
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 获取下一天的午夜时间(0点)===》也就是当天的24点
        LocalDateTime nextMidnight = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
        // 计算当前时间距离午夜的持续时间
        Duration duration = Duration.between(currentDateTime, nextMidnight);
        // 获取距离午夜的秒数
        return duration.get(ChronoUnit.SECONDS);
    }
}
