package com.ai.slp.mall.web.util;


/**
 * 验签工具类
 *
 * Date: 2015年11月2日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * @author fanpw
 */
public final class VerifyUtil {
    
    private VerifyUtil() {
        
    }

    /**
     * 默认编码
     */
    private static final String DEFAULT_CHARSET = "utf-8";
    
    /**
     * 加密组装分隔符
     */
    public static final String SEPARATOR = ";";
    
    /**
     * 检查参数
     * 
     * @param param
     * @param paramMd5
     * @param key
     * @return
     * @author LiangMeng
     */
    public static boolean checkParam(String param, String paramMd5, String key) {
        String paramMd5New = MD5.sign(param, key, DEFAULT_CHARSET);
        return paramMd5New.equals(paramMd5);
    }

    /**
     * 参数加密
     * 
     * @param param
     * @param key
     * @return
     * @author LiangMeng
     */
    public static String encodeParam(String param, String key) {
        return MD5.sign(param, key, DEFAULT_CHARSET);
    }
    
}
