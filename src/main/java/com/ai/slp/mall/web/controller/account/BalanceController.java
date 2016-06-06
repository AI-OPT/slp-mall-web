package com.ai.slp.mall.web.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.AccountIdParam;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.web.frame.velocity.VelocityBuilder;
import com.alibaba.fastjson.JSON;

@RestController
public class BalanceController {
	@Autowired
	private VelocityBuilder velocityBuilder;
	//
	private static final String ACCOUNT_ID = "";
	//
	@RequestMapping("/account/balance/index")
	public ModelAndView index(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
		String realPath = request.getRealPath("/jsp/account/balance/账户余额.html");
		String str2 = this.velocityBuilder.doBuildTemplate(paramMap, realPath);
		System.out.println("str2:-------------->>>>:"+str2);
		System.out.println("str2Json:-------------->>>>:"+JSON.toJSONString(str2));
        ModelAndView view = new ModelAndView("jsp/account/balance/index",paramMap);
        return view;
    }
	@RequestMapping("/account/balance/detail")
	public ModelAndView detail(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/balance_detail/index",paramMap);
        return view;
    }
	
	@RequestMapping("/account/recharge/one")
	public ModelAndView one(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/one",paramMap);
        return view;
    }
	@RequestMapping("/account/recharge/two")
	public ModelAndView two(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/two",paramMap);
        return view;
    }
	@RequestMapping("/account/recharge/three")
	public ModelAndView three(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/three",paramMap);
        return view;
    }
	@RequestMapping("/account/recharge/four")
	public ModelAndView four(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
        ModelAndView view = new ModelAndView("jsp/account/recharge/four",paramMap);
        return view;
    }
	//produces = "application/json;charset=UTF-8"
	//produces = "text/html;charset=UTF-8"
	@RequestMapping(value="/account/test",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String test(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
		String realPath = request.getRealPath("/jsp/account/balance/账户余额.html");
		String str2 = this.velocityBuilder.doBuildTemplate(paramMap, realPath);
		paramMap.put("str2", str2);
		System.out.println("paramMapJson:-------------->>>>:"+JSON.toJSONString(paramMap));
		System.out.println("str2:-------------->>>>:"+str2);
        return str2;
    }
	/**
	 * 查询可用余额
	 * @param request
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@RequestMapping(value="/account/queryUsableFund",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryUsableFund(HttpServletRequest request) {
		AccountIdParam accountIdParam = new AccountIdParam();
		accountIdParam.setAccountId(new Long(ACCOUNT_ID));
		
		//
		FundInfo fundInfo = DubboConsumerFactory.getService(IFundQuerySV.class).queryUsableFund(accountIdParam);
		Long balance = fundInfo.getBalance();
		//
		return balance.toString();
    }
	
}
