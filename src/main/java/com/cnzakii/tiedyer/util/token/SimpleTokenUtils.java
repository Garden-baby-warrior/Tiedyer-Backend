package com.cnzakii.tiedyer.util.token;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * 简单Token的工具类<br/>
 * 生成简单的Token值，这就是根据Token生成策略随机生成字符串
 *
 * @author Zaki
 * @since 2023-10-28
 **/
public class SimpleTokenUtils {
    /**
     * token值的生成策略
     *
     * @return token值
     */
    public static String createTokenValue() {
        return "Tr_" + RandomStringUtils.randomAlphanumeric(13) + "_" + RandomStringUtils.randomAlphanumeric(14) + "_";
    }
}
