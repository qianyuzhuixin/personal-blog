package com.xiaoyang.scheduled;

import cn.hutool.core.collection.CollUtil;
import com.xiaoyang.constant.HomeConstant;
import com.xiaoyang.utils.CommonUtils;
import com.xiaoyang.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ScheduledTasks {


    @Autowired
    private RedisCache redisCache;

    // 你也可以使用 cron 表达式来定义更复杂的执行计划
    // HomeConstant.HOT_ARTICLE_LIST_NUMBER 为热门文章数量常量，在constant中的HomeConstant类中定义
    // @Scheduled(fixedRate = 1000 * 5)
    @Scheduled(cron = "0 0 6-20/5 * * ?") // 早上6点到晚上8点，每5小时执行一次
    public void getHotArticleList() {
        Map<String, Integer> articleGoodNumsInScheduledTime = redisCache.getCacheMap("articleGoodNumsInScheduledTime");
        Map<String, Integer> articleCollectNumsInScheduledTime = redisCache.getCacheMap("articleCollectNumsInScheduledTime");
        // 使用 TreeMap 对 Map 进行值排序
        List<String> hotArticleIdList = CommonUtils.sortMapByValue(articleGoodNumsInScheduledTime);

        if (Objects.isNull(hotArticleIdList) || hotArticleIdList.size() == 0) {
            hotArticleIdList = new ArrayList<>();
        }
        if (hotArticleIdList.size() > HomeConstant.HOT_ARTICLE_LIST_NUMBER) {
            for (int i = HomeConstant.HOT_ARTICLE_LIST_NUMBER; i < hotArticleIdList.size(); i++) {
                hotArticleIdList.remove(i);
            }
        } else if (hotArticleIdList.size() < HomeConstant.HOT_ARTICLE_LIST_NUMBER) {
            List<String> temp = CommonUtils.sortMapByValue(articleCollectNumsInScheduledTime);
            // 去重
            hotArticleIdList.addAll(temp);
            HashSet<String> set = new HashSet<>(hotArticleIdList);
            hotArticleIdList = new ArrayList<>(set);
            // 如果热门文章数量超过限制，则只保留前 HOT_ARTICLE_LIST_NUMBER 个
            if (hotArticleIdList.size() > HomeConstant.HOT_ARTICLE_LIST_NUMBER) {
                for (int i = HomeConstant.HOT_ARTICLE_LIST_NUMBER; i < hotArticleIdList.size(); i++) {
                    hotArticleIdList.remove(i);
                }
            }
        }
        if (CollUtil.isNotEmpty(hotArticleIdList)) {
            redisCache.setCacheList("hotArticleIdList", hotArticleIdList);
        }
        redisCache.deleteObject("articleGoodNumsInScheduledTime");
        redisCache.deleteObject("articleCollectNumsInScheduledTime");
    }
}