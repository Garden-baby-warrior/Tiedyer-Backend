package com.cnzakii.tiedyer.common.validation.validator;


import com.cnzakii.tiedyer.common.validation.annotation.FixedStrValues;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * 注解FixedValues的Validator<br>
 * 校验Integer数值只能是某一组固定的数组中的某一元素
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-05-15
 **/
public class FixedStrValuesValidator implements ConstraintValidator<FixedStrValues, String> {
    private String[] allowedValues;

    @Override
    public void initialize(FixedStrValues constraintAnnotation) {
        allowedValues = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(String  value, ConstraintValidatorContext context) {
        return value != null && Arrays.asList(allowedValues).contains(value);
    }
}
