package com.ai.slp.mall.web.filter;

import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.mall.web.constants.LoginConstants;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ajax异步登录检查
 * Created by jackieliu on 16/6/15.
 */
public class AjaxLoginFilter implements Filter {
    private String redirectUrl = "";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        redirectUrl = filterConfig.getInitParameter("redirect_url");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String url = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession();
        //若已经登录,则继续执行
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        //若重定向标记不为空,且当前地址已重定向标记结尾,则删除标记,进行重定向
        if (StringUtils.isNotBlank(redirectUrl) && url.endsWith(redirectUrl)){
            int lastIndex = url.lastIndexOf(redirectUrl);
            url = url.substring(0,lastIndex) + "?" + httpRequest.getQueryString();
            httpResponse.sendRedirect(url);
            return;
        }
        if (user!=null){
            chain.doFilter(request,response);
            return;
        }
        //检查是否有登录信息,若没有则返回登录提示
        ResponseData<CartProdOptRes> responseData = new ResponseData<CartProdOptRes>(LoginConstants.AJAX_STATUS_LOGIN, "未登录");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(responseData));
        writer.close();
    }

    @Override
    public void destroy() {

    }
}
