package com.cnzakii.tiedyer.common.validation.annotation;


import com.cnzakii.tiedyer.common.validation.validator.FixedStrValuesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * 校验String数值只能是某一组固定的数组
 *
 * @author Zaki
 * @version 1.0
 * @since 2023-05-15
 **/
@Documented
@Constraint(validatedBy = {FixedStrValuesValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedStrValues {
    String[] values();

    String message() default "Invalid value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        FixedStrValues[] value();
    }
}
