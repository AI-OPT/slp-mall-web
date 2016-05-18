package com.ai.slp.mall.web.util;

import java.util.UUID;

public class TradeSeqUtil {
    
    /**
     * 消息流水号 
     * 组成：租户ID + YYMMDDHH24MISS + SSS(毫秒) + 9位序列号
     * @param tenantId 租户ID
     * @return 流水号 
     */
    public static String newTradeSeq(String tenantId){
        String tradeSeq = null;
        synchronized (TradeSeqUtil.class) {
            //消息流水 组成：租户ID + YYMMDDHH24MISS + SSS(毫秒) + 9位序列号
            tradeSeq = tenantId + DateUtil.getDateString(DateUtil.yyyyMMddHHmmssSSS) + UUID.randomUUID();
        }
        return tradeSeq;
    }
    
}
