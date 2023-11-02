package com.cnzakii.tiedyer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cnzakii.tiedyer.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 答题游戏-题库 Mapper 接口
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Mapper
public interface QuestionBankMapper extends BaseMapper<Question> {

    /**
     * 随机生成答题列表
     *
     * @param questionNum 生成的题目数
     * @param exclude     排除的题目
     * @return 题目列表
     */
    List<Question> randomSelectQuestionList(@Param("questionNum") int questionNum, @Param("exclude") List<Long> exclude);
}
