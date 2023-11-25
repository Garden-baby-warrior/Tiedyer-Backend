package com.cnzakii.tiedyer.model.dto.badge;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 徽章数据传输对象
 *
 * @author Zaki
 * @since 2023-11-25
 **/
@Data
public class BadgeDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2029510312811293402L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long badgeId;

    /**
     * 徽章名
     */
    private String name;

    /**
     * 徽章图片路径
     */
    private String image;

    /**
     * 徽章兑换需要的积分
     */
    private Integer points;

    /**
     * 徽章是否已经兑换过
     */
    private boolean collected;

    /**
     * 徽章被兑换的时间，当collected为true时，该字段才有值
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDateTime collectTime;

}
