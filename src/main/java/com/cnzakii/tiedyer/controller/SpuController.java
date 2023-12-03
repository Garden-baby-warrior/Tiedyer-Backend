package com.cnzakii.tiedyer.controller;

import com.cnzakii.tiedyer.common.http.ResponseResult;
import com.cnzakii.tiedyer.common.http.ResponseStatus;
import com.cnzakii.tiedyer.entity.Spu;
import com.cnzakii.tiedyer.exception.BusinessException;
import com.cnzakii.tiedyer.model.dto.PageBean;
import com.cnzakii.tiedyer.service.SpuService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

/**
 * 获取产品接口--预览
 *
 * @author zaki
 * @since 2023-11-09
 */
@RestController
@RequestMapping("/commodity/base")
public class SpuController {

    @Resource
    private SpuService spuService;


    /**
     * 获取推荐列表
     *
     * @param timestamp 限制时间戳,默认为当前时间戳
     * @param pageSize  限制个数，默认为5
     * @return 分页查询结果
     */
    @GetMapping("/list/recommend")
    public ResponseResult<PageBean<Spu>> getSpuResultByRecommend(@RequestParam(value = "timestamp", defaultValue = "-1") Long timestamp, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        if (pageSize <= 0 || pageSize >= 10) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "pageSize无效");
        }
        if (timestamp == -1) {
            timestamp = System.currentTimeMillis();
        }
        PageBean<Spu> result = spuService.getSpuResultByRecommend(timestamp, pageSize);
        return ResponseResult.success(result);
    }


    /**
     * 根据分类获取对应的Spu列表
     *
     * @param categoryId 分类id - 1-其他  2-衣服
     * @param timestamp  限制时间戳,默认为当前时间戳
     * @param pageSize   限制个数，默认为5
     * @return 分页查询结果
     */
    @GetMapping("/list/{categoryId}")
    public ResponseResult<PageBean<Spu>> getSpuResultByCategory(@PathVariable("categoryId") Integer categoryId, @RequestParam(value = "timestamp", defaultValue = "-1") Long timestamp, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        if (pageSize <= 0 || pageSize >= 10) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "pageSize无效");
        }
        if (timestamp == -1) {
            timestamp = System.currentTimeMillis();
        }

        PageBean<Spu> result = spuService.getSpuResultByCategory(categoryId, timestamp, pageSize);

        return ResponseResult.success(result);
    }

    /**
     * 全文检索
     *
     * @param key       检索关键词
     * @param timestamp 限制时间戳,默认为当前时间戳
     * @param pageSize  限制个数，默认为5
     * @return 分页查询结果
     */
    @GetMapping("/list/search")
    public ResponseResult<PageBean<Spu>> getSpuResultByRecommend(@PathParam("key") String key, @RequestParam(value = "timestamp", defaultValue = "-1") Long timestamp, @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        if (pageSize <= 0 || pageSize >= 10) {
            throw new BusinessException(ResponseStatus.REQUEST_ERROR, "pageSize无效");
        }
        if (timestamp == -1) {
            timestamp = System.currentTimeMillis();
        }

        PageBean<Spu> result = spuService.getSpuResultByFullTextSearch(key,timestamp, pageSize);
        return ResponseResult.success(result);
    }


}
