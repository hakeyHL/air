package com.air.utils;

import java.math.BigDecimal;

/**
 * Created by linux on 2017年03月30日.
 * 计算折扣值
 * Time 01:52
 */
public class StringUtils {
    public static BigDecimal getTicketDiscount(int frequency) {
        if (frequency > 6) {
            return BigDecimal.valueOf(0.7);
        } else {
            BigDecimal bigDecimal = BigDecimal.ONE;
            if (frequency <= 0) {
                return BigDecimal.valueOf(0.95);
            } else {
                return bigDecimal.subtract(BigDecimal.valueOf(frequency).multiply(BigDecimal.valueOf(0.05)));
            }
        }
    }
}
