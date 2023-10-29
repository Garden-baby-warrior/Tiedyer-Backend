package com.cnzakii.tiedyer.util.rsa.aop;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.cnzakii.tiedyer.util.MyJsonUtils;
import com.cnzakii.tiedyer.util.rsa.AbstractEncryptedDataContainer;
import jakarta.annotation.Resource;
import jakarta.validation.*;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * RSA解密切面类，用于解密被加密的请求参数<br/>
 * 该切面类能够正常运行的必要条件：<br/>
 * 1. 切面方法有且仅有一个参数<br/>
 * 2. 参数对象必须继承自{@link AbstractEncryptedDataContainer}抽象类<br/>
 * 3. 切面方法必须使用{@link RsaDecrypt}注解进行标记
 *
 * @author Zaki
 * @since 2023-09-02
 */

@Aspect
@Component
public class RsaDecryptAspect {

    @Resource
    private RSA rsa;

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    /**
     * 在使用{@linkplain RsaDecrypt}注解标记的方法执行前后，对参数进行解密操作和校验。
     *
     * @param joinPoint 切点对象，用于获取方法参数和执行方法。
     * @return 解密和校验后的结果对象。
     * @throws Throwable 可能抛出的异常。
     */
    @Around("@annotation(com.cnzakii.tiedyer.util.rsa.aop.RsaDecrypt)")
    public Object decrypt(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        // 进行参数校验
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("Parameter is null or empty");
        }

        Object arg0 = args[0];
        if (arg0 == null) {
            throw new IllegalArgumentException("Parameter is null");
        }

        if (!(arg0 instanceof AbstractEncryptedDataContainer encryptedDataContainer)) {
            throw new IllegalArgumentException("Parameter does not inherit from the AbstractEncryptedDataContainer class");
        }

        String encryptedData = encryptedDataContainer.getEncryptedData();
        if (StringUtils.isEmpty(encryptedData)) {
            throw new IllegalArgumentException("Parameter's encrypted data is empty");
        }

        // 解密
        String json = rsa.decryptStr(encryptedData, KeyType.PrivateKey);

        // 将json字符串转换成对应的Java对象
        Class<?> clazz = encryptedDataContainer.getClass();
        Object o = MyJsonUtils.parseObject(json, clazz);
        // 执行校验
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        if (!violations.isEmpty()) {
            // 校验不通过，抛出ConstraintViolationException异常
            throw new ConstraintViolationException(violations);
        }

        args[0] = o;
        // 调用目标方法
        return joinPoint.proceed(args);
    }


}
