package com.cnzakii.tiedyer.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Question;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.mapper.QuestionBankMapper;
import com.cnzakii.tiedyer.model.dto.game.AnswerDTO;
import com.cnzakii.tiedyer.model.dto.game.QuestionDTO;
import com.cnzakii.tiedyer.service.QuestionGameService;
import com.cnzakii.tiedyer.service.UserQuestionHistoryService;
import com.cnzakii.tiedyer.service.UserService;
import com.cnzakii.tiedyer.util.MyDateTimeUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
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


    @Resource
    private UserService userService;


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
     * 验证用户答案,并获取正确答案和解析
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @param answer     用户答案
     * @return 正确答案和解析
     */
    @Transactional
    @Override
    public AnswerDTO verifyUerAnswer(Long userId, Long questionId, String answer) {
        // 根据用户id和问题id获取正确答案和解析
        AnswerDTO answerDTO = getAnswerAndAnalysis(userId, questionId);

        int isCorrect = 0;

        // 验证用户答案，如果正确则添加积分
        if (Objects.equals(answer, answerDTO.getAnswer())) {
            int points = 1;
            // 添加积分
            userService.increasePoints(userId, points, "用户每日答题正确");
            isCorrect = 1;
        }

        // 将本题添加进用户答题记录
        historyService.saveHistory(userId, questionId, isCorrect);

        // 删除用户答题列表中对应的题目
        stringRedisTemplate.opsForHash().delete(DAILY_QUESTION_GAME_LIST + userId, String.valueOf(questionId));

        // 查看用户的剩余题目
        Map<Object, Object> questionsMap = stringRedisTemplate.opsForHash().entries(DAILY_QUESTION_GAME_LIST + userId);
        if (CollectionUtils.isEmpty(questionsMap)) {
            // 如果为空，则说明用户已经答题完成
            // 删除用户今日答题列表
            stringRedisTemplate.delete(DAILY_QUESTION_GAME_LIST + userId);
            // 将用户id添加进今日已经答题的列表中
            stringRedisTemplate.opsForSet().add(DAILY_ANSWERED_USERS_LIST, String.valueOf(userId));
            // 计算时间距离当天午夜（24点）还有多少秒
            long second = MyDateTimeUtils.secondsUntilMidnight();
            // 设置过期时间
            stringRedisTemplate.expire(DAILY_ANSWERED_USERS_LIST, second, TimeUnit.SECONDS);
        }


        return answerDTO;
    }


    /**
     * 根据用户id和问题id获取正确答案和解析
     *
     * @param userId     用户ID
     * @param questionId 问题ID
     * @return 正确答案和解析
     */
    @Override
    public AnswerDTO getAnswerAndAnalysis(Long userId, Long questionId) {
        // 尝试从Redis中获取用户答题列表
        Map<Object, Object> questionsMap = stringRedisTemplate.opsForHash().entries(DAILY_QUESTION_GAME_LIST + userId);
        if (CollectionUtils.isEmpty(questionsMap)) {
            throw new BusinessException(ResponseStatus.FAIL);
        }

        // 根据questionId获取对应题目
        String json = (String) questionsMap.get(String.valueOf(questionId));
        if (StringUtils.isBlank(json)) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "题目不存在");
        }

        QuestionDTO questionDTO = JSONUtil.toBean(json, QuestionDTO.class);

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswer(questionDTO.getAnswer());
        answerDTO.setAnalysis(questionDTO.getAnalysis());

        return answerDTO;
    }


    /**
     * 根据userId获取用户每日问题
     *
     * @param userId 用户id
     * @return 题目集合
     */
    @Override
    public QuestionDTO[] getDailyQuestionByUserId(Long userId) {
        Map<String, QuestionDTO> resultMap;

        // 尝试从Redis中获取用户答题列表
        Map<Object, Object> questionsMap = stringRedisTemplate.opsForHash().entries(DAILY_QUESTION_GAME_LIST + userId);
        if (!CollectionUtils.isEmpty(questionsMap)) {
            // 直接获取resultMap
            resultMap = questionsMap.entrySet().stream().collect(Collectors.toMap(
                    entry -> (String) entry.getKey(),
                    entry -> JSONUtil.toBean((String) entry.getValue(), QuestionDTO.class)
            ));
        } else {
            // 如果为空，则初始化用户答题列表
            Map<String, String> initMap = initDailyQuestionByUserId(userId);
            // 获取题目
            resultMap = initMap.entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> JSONUtil.toBean(entry.getValue(), QuestionDTO.class)
            ));
        }

        //  返回题目集合
        return resultMap.values().toArray(new QuestionDTO[0]);
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
        // 计算时间距离当天午夜（24点）还有多少秒
        long second = MyDateTimeUtils.secondsUntilMidnight();
        // 设置过期时间
        stringRedisTemplate.expire(DAILY_QUESTION_GAME_LIST + userId, second, TimeUnit.SECONDS);

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
        questionDTO.setImage(question.getImage());
        questionDTO.setOptions(optionMap);
        questionDTO.setAnswer(answerIndex);
        questionDTO.setAnalysis(question.getAnalysis());


        return questionDTO;
    }
}
