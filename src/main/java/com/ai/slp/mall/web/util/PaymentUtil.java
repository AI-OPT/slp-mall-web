package com.ai.slp.mall.web.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 支付中心支付相关工具类
 * 
 * Date: 2015年11月4日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * 
 * @author fanpw
 */
public final class PaymentUtil {

    private static final Logger LOG = Logger.getLogger(PaymentUtil.class);

    private PaymentUtil() {

    }

    /**
     * 在线支付生成form提交
     * 
     * @param actionUrl
     * @param paramMap
     * @return
     */
    public static String generateAutoSubmitForm(String actionUrl, Map<String, String> paramMap) {
        StringBuilder html = new StringBuilder();
        html.append("<script language=\"javascript\">window.onload=function(){document.pay_form.submit();}</script>\n");
        html.append("<form id=\"pay_form\" name=\"pay_form\" action=\"").append(actionUrl)
                .append("\" method=\"post\">\n");

        Set<Entry<String, String>> set = paramMap.entrySet();
        Iterator<Entry<String, String>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            html.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\""
                    + value + "\">\n");
        }
        html.append("</form>\n");
        LOG.info(html);
        return html.toString();
    }

    public static String getExternalId() {
        String dateString = DateUtil.getDateString(DateUtil.YYYYMMDDHHMMSS);
        int rannum = (int) (new Random().nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        String result = rannum + dateString;
        return result;
    }

}
