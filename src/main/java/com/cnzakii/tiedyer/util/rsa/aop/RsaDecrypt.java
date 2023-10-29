package com.cnzakii.tiedyer.util.rsa.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解，用于标记需要在方法执行前对参数进行RSA解密的方法。
 * 需要和 {@link RsaDecryptAspect} 搭配使用。
 *
 * @author Zaki
 * @since 2023-09-02
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RsaDecrypt {

}
