package com.cnzakii.tiedyer.util.rsa;

import lombok.Data;

/**
 * AbstractEncryptedDataContainer 抽象类定义了具有加密数据字段的对象的通用行为契约。<br/>
 * 所有需要进行加密或者解密的类都需要继承该抽象类
 *
 * @author Zaki
 * @since 2023-09-03
 */
@Data
public abstract class AbstractEncryptedDataContainer {

    /**
     * 加密数据
     */
    private String encryptedData;
}