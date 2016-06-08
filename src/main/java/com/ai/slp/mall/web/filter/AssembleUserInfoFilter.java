package com.ai.slp.mall.web.filter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.accountquery.param.CustIdParam;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserResponse;
import com.alibaba.fastjson.JSON;


public class AssembleUserInfoFilter implements Filter {
    private String[] ignor_suffix = {};
    private static final Logger LOG = LoggerFactory.getLogger(AssembleUserInfoFilter.class);
    private static final String TENANT_ID_DEFAULT="0";
    private static final String INDUSTRY_CODE_DEFAULT="0";

    public void init(FilterConfig filterConfig) throws ServletException {
        String ignore_res = filterConfig.getInitParameter("ignore_suffix");
        if (!"".equals(ignore_res)) {
            this.ignor_suffix = filterConfig.getInitParameter("ignore_suffix").split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (!shouldFilter(req)) {
            chain.doFilter(req, response);
            return;
        }
        HttpSession session = req.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (user == null) {
            user = assembleUser(req);
            if(user!=null){
            	session.setAttribute(SSOClientConstants.USER_SESSION_KEY, user);
            	LOG.info("已封装的用户信息为：" + user.toString());
            }
            chain.doFilter(req, response);

        } else {
        	//刷新用户
        	//refreshUser(user);
        	LOG.info("【slp-mall-web】user="+JSON.toJSONString(user));
            chain.doFilter(req, response);
        }
    }

    @Override
    public void destroy() {

    }
    
    public void refreshUser(SLPClientUser user){
    	try{
    		IUcUserSV accSv=DubboConsumerFactory.getService(IUcUserSV.class);
    		SearchUserRequest req=new SearchUserRequest();
    		req.setUserId(user.getUserId());
    		SearchUserResponse resp=accSv.queryBaseInfo(req);
    		if(resp.getResponseHeader().isSuccess()){
    			BeanUtils.copyProperties(user, resp);
    		}
    	}
    	catch(Exception e){
    		LOG.error("刷新用户信息失败", e);
    	}
    }

    /**
     * 封装用户信息
     *
     * @param request
     * @return
     */
    private SLPClientUser assembleUser(HttpServletRequest request) {
    	SLPClientUser user = null;
        try {
            Principal principal = request.getUserPrincipal();
            if (principal != null) {
                user = new SLPClientUser();
                AttributePrincipal attributePrincipal = (AttributePrincipal) principal;
                Map<String, Object> attributes = attributePrincipal.getAttributes();
                Field[] fields = SLPClientUser.class.getDeclaredFields();
                for (Field field : fields) {
                    String value = (String) attributes.get(field.getName());
                    if (value != null) {
                        field.setAccessible(true);
                        if ("long".equalsIgnoreCase(field.getType().toString())) {
                            field.set(user, Long.parseLong(value));
                        } else {
                            field.set(user, value);
                        }
                    }
                }
                //获取账户的acctId
                fillAcctIdByUserId(user);
                
            }
        } catch (Exception e) {
            LOG.error("封装用户信息失败", e);
        }
        return user;
    }

	private void fillAcctIdByUserId(SLPClientUser user) {
		CustIdParam param=new CustIdParam();
		param.setTenantId(SLPMallConstants.COM_TENANT_ID);
		param.setCustId(user.getUserId());
		IAccountQuerySV accSv=DubboConsumerFactory.getService(IAccountQuerySV.class);
		List<AccountInfoVo> resList=accSv.queryAccontByCustId(param);
		if(!CollectionUtil.isEmpty(resList)){
			user.setAcctId(resList.get(0).getAcctId());
		}
	}

    private boolean shouldFilter(HttpServletRequest req) {
        if (ignor_suffix != null && ignor_suffix.length > 0) {
            String uri = req.getRequestURI().toLowerCase();
            for (String suffix : ignor_suffix) {
                if (uri.endsWith(suffix)) {
                    return false;
                }
            }
        }
        return true;
    }
}
