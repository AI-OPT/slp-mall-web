package com.ai.slp.mall.web.filter;

import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.mall.web.constants.LoginConstants;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ajax异步登录检查
 * Created by jackieliu on 16/6/15.
 */
public class AjaxLoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();
        //若已经登录,则继续执行
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
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
