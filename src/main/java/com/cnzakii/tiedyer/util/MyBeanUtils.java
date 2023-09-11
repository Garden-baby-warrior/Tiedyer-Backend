package com.cnzakii.tiedyer.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * Bean工具类
 * <p>
 * 本类是对Spring{@link BeanUtils}的进一步封装
 * </p>
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-06-01
 **/
@Slf4j
public class MyBeanUtils {


    /**
     * 根据源对象属性，创建一个新的目标对象并复制属性。
     *
     * @param source 源对象
     * @param clazz  目标对象的类
     * @param <T>    目标对象的类型参数
     * @return 一个带有源对象属性复制的新目标对象，如果出现异常则返回 null
     */
    public static <T> T copyProperties(Object source, Class<T> clazz) {
        if (source == null || clazz == null) {
            return null;
        }

        try {
            // 创建新的目标对象
            T target = clazz.getDeclaredConstructor().newInstance();

            // 使用BeanUtils复制属性
            BeanUtils.copyProperties(source, target);

            return target;
        } catch (Exception e) {
            // 记录日志并抛出异常
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    /**
     * 复制对象属性，忽略为null的属性
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    /**
     * 获取对象为null的属性名
     *
     * @param source 源对象
     * @return 属性名集合
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        return Arrays.stream(pds)
                .map(FeatureDescriptor::getName)
                .filter(name -> src.getPropertyValue(name) == null)
                .toArray(String[]::new);
    }

}
