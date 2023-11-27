package com.cnzakii.tiedyer.model.dto.badge;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询徽章数据结果的封装。
 *
 * @author Zaki
 * @since 2023-11-25
 **/
@Data
public class BadgeResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 1071705896753132233L;

    public BadgeResult() {
        this.result = new ArrayList<>();
        this.redeemedNum = 0;
    }

    public BadgeResult(List<BadgeDTO> result, Integer redeemedNum) {
        this.result = result;
        this.redeemedNum = redeemedNum;
    }

    /**
     * 查询结果
     */
    private List<BadgeDTO> result;

    /**
     * 已经兑换的徽章数量
     */
    private Integer redeemedNum;

}
