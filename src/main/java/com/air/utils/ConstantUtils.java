package com.air.utils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by linux on 2017年04月01日.
 * Time 16:14
 * 放置一些静态属性
 */
public class ConstantUtils {
    //初始化一个列表队列,用户将相应车次的库存信息放入到队列中,保证多人购票时数据一致
    public static ConcurrentHashMap<Long, Long> trainTicketRepoArrayQueue = new ConcurrentHashMap();

    /**
     * 减库存
     *
     * @param key
     */
    public static void subTicketCountFromQuque(Long key) {
        trainTicketRepoArrayQueue.put(key, trainTicketRepoArrayQueue.get(key) - 1);
    }

    /**
     * 加库存
     *
     * @param key
     */
    public static void addTicketCountFromQuque(Long key) {
        trainTicketRepoArrayQueue.put(key, trainTicketRepoArrayQueue.get(key) + 1);
    }
}
