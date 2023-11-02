package com.cnzakii.tiedyer.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Question;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.QuestionBankMapper;
import com.cnzakii.tiedyer.model.dto.game.QuestionDTO;
import com.cnzakii.tiedyer.model.dto.game.QuizInfo;
import com.cnzakii.tiedyer.service.QuestionGameService;
import com.cnzakii.tiedyer.service.UserQuestionHistoryService;
import com.cnzakii.tiedyer.util.MyJsonUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.cnzakii.tiedyer.common.constant.RedisConstants.DAILY_ANSWERED_USERS_LIST;
import static com.cnzakii.tiedyer.common.constant.RedisConstants.DAILY_QUESTION_GAME_LIST;

/**
 * <p>
 * 答题游戏-题库 服务实现类
 * </p>
 *
 * @author zaki
 * @since 2023-11-02
 */
@Service
public class QuestionGameServiceImpl extends ServiceImpl<QuestionBankMapper, Question> implements QuestionGameService {

    @Resource
    private QuestionBankMapper questionBankMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserQuestionHistoryService historyService;


    /**
     * 查看用户今日是否答过题
     *
     * @param userId 用户id
     * @return 是否答过题
     */
    @Override
    public boolean isParticipate(Long userId) {
        Boolean b = stringRedisTemplate.opsForSet().isMember(DAILY_ANSWERED_USERS_LIST, String.valueOf(userId));
        return BooleanUtils.isTrue(b);
    }


    /**
     * 根据userId获取用户每日问题
     *
     * @param userId 用户id
     * @return 题目
     */
    @Override
    public QuizInfo getDailyQuestionByUserId(Long userId) {
        QuestionDTO questionDTO;
        int remaining;

        // 尝试从Redis中获取用户答题列表
        Map<Object, Object> questionsMap = stringRedisTemplate.opsForHash().entries(DAILY_QUESTION_GAME_LIST + userId);

        if (CollectionUtils.isEmpty(questionsMap)) {
            // 如果为空，则初始化用户答题列表
            Map<String, String> initMap = initDailyQuestionByUserId(userId);
            Set<String> keys = initMap.keySet();
            // 获取题目
            questionDTO = MyJsonUtils.parseObject(initMap.get(keys.iterator().next()), QuestionDTO.class);
            remaining = initMap.size() - 1;
        } else {
            Set<Object> keys = questionsMap.keySet();
            questionDTO = MyJsonUtils.parseObject((String) questionsMap.get(keys.iterator().next()), QuestionDTO.class);
            remaining = questionsMap.size() - 1;
        }


        return new QuizInfo(questionDTO, remaining);
    }

    /**
     * 为用户初始化每日问题列表
     *
     * @param userId 用户ID
     * @return 每日问题map
     */
    @Override
    public Map<String, String> initDailyQuestionByUserId(Long userId) {
        int QUESTION_NUM = 3;

        // 获取用户历史答题id列表
        List<Long> history = historyService.getQuestionIdListByUserInd(userId);

        // 随机生成答题列表
        List<Question> list = questionBankMapper.randomSelectQuestionList(QUESTION_NUM, history);

        if (CollectionUtils.isEmpty(list) || list.size() < 3) {
            throw new BusinessException(ResponseStatus.SERVER_ERROR, "无法获取足够的问题");
        }


        // 转化成Question转换成DTO
        Map<String, String> questionMap = list.stream()
                .map(this::convertQuestionToDTO)
                .collect(Collectors.toMap(QuestionDTO::getId, JSONUtil::toJsonStr));

        // 存入Redis
        stringRedisTemplate.opsForHash().putAll(DAILY_QUESTION_GAME_LIST + userId, questionMap);

        return questionMap;
    }


    /**
     * 将Question转化成DTO
     *
     * @param question question对象
     * @return QuestionDTO
     */
    @Override
    public QuestionDTO convertQuestionToDTO(Question question) {
        String[] arr = new String[]{"A", "B", "C", "D"};

        Long id = question.getId();
        String title = question.getTitle();

        // 将选项List转化成选项Map
        List<String> optionList = Arrays.stream(question.getOption().split(",")).collect(Collectors.toList());
        String answer = optionList.get(question.getAnswer() - 1);
        String answerIndex = null;
        Map<String, String> optionMap = new HashMap<>();

        int size = optionList.size();
        for (int i = 0; i < size; i++) {
            // 生成一个随机索引
            int index = RandomUtils.nextInt(0, optionList.size());
            // 删除并获取选项
            String option = optionList.remove(index);
            // 存入map
            optionMap.put(arr[i], option);

            if (Objects.equals(option, answer)) {
                answerIndex = arr[i];
            }
        }

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(String.valueOf(id));
        questionDTO.setTitle(title);
        questionDTO.setOptions(optionMap);
        questionDTO.setAnswer(answerIndex);


        return questionDTO;
    }
}
