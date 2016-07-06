package com.ai.slp.mall.web.controller.account;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.opt.base.vo.PageInfo;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.AccountIdParam;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.charge.api.paymentquery.interfaces.IPaymentQuerySV;
import com.ai.slp.charge.api.paymentquery.param.ChargeBaseInfo;
import com.ai.slp.charge.api.paymentquery.param.ChargeInfoQueryByAcctIdParam;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamMultiCond;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.mall.web.util.DateUtil;
import com.alibaba.fastjson.JSON;

@RestController
public class BalanceController {
	private static final Logger log = LoggerFactory.getLogger(BalanceController.class);
	//
	private static final String ACCOUNT_ID = "100001";
	private static final String TENANT_ID = "SLP";
	private static final int AMOUNT = -999;
	//private static final int 
	//
	@RequestMapping("/account/balance/index")
	public ModelAndView index(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
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
	
	//produces = "application/json;charset=UTF-8"
	//produces = "text/html;charset=UTF-8"
	@RequestMapping(value="/account/test",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String test(HttpServletRequest request) {
		String str2 = "str2";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("linkModel", "accountBalance");
		paramMap.put("testName", "zhangzd");
		paramMap.put("str2", str2);
		log.info("paramMapJson:-------------->>>>:"+JSON.toJSONString(paramMap));
		log.info("str2:-------------->>>>:"+str2);
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
		SLPClientUser user = this.getUserInfo(request);
		accountIdParam.setAccountId(user.getAcctId());//(new Long(ACCOUNT_ID));
		accountIdParam.setTenantId(user.getTenantId());//(TENANT_ID);
		//
		FundInfo fundInfo = DubboConsumerFactory.getService(IFundQuerySV.class).queryUsableFund(accountIdParam);
		double balance = 0;
		if(null != fundInfo){
			balance = ((double)fundInfo.getBalance())/1000;
		}
		
		log.info("账户余额："+balance);
		//
		return String.valueOf(balance);
    }
	/**
	 * 查询近七天收支记录
	 * @param request
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@RequestMapping(value="/account/queryChargeBaseInfoByAcctId",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public PageInfo<ChargeBaseInfo> queryChargeBaseInfoByAcctId(HttpServletRequest request) {
		ChargeInfoQueryByAcctIdParam chargeInfoQueryByAcctIdParam = new ChargeInfoQueryByAcctIdParam();
		SLPClientUser user = this.getUserInfo(request);
		chargeInfoQueryByAcctIdParam.setAccountId(user.getAcctId());//(new Long(ACCOUNT_ID));
		chargeInfoQueryByAcctIdParam.setTenantId(user.getTenantId());//(TENANT_ID);
		PageInfo<ChargeBaseInfo> chargeBaseInfoPageInfo = new PageInfo<ChargeBaseInfo>();
		chargeBaseInfoPageInfo.setPageNo(1);
		chargeBaseInfoPageInfo.setPageSize(1000);
		chargeInfoQueryByAcctIdParam.setPageInfo(chargeBaseInfoPageInfo);
		//
		Map<String,String> time = new HashMap<String,String>();
		time = DateUtil.getTimeInterval(Calendar.DATE, 7);
		//
		chargeInfoQueryByAcctIdParam.setStartTime(Timestamp.valueOf(time.get(DateUtil.KEY_START_TIME)));
		chargeInfoQueryByAcctIdParam.setEndTime(Timestamp.valueOf(time.get(DateUtil.KEY_END_TIME)));
		//
		PageInfo<ChargeBaseInfo> pageInfo = DubboConsumerFactory.getService(IPaymentQuerySV.class).queryChargeBaseInfoByAcctId(chargeInfoQueryByAcctIdParam);
		//
		pageInfo = this.getChargeBaseInfoPageInfo(pageInfo);
		//
		log.info("json:"+JSON.toJSONString(pageInfo));
		return pageInfo;
    }
	/**
	 * 检索字典表对应的信息 封装后返回
	 * @param pageInfo
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	public PageInfo<ChargeBaseInfo> getChargeBaseInfoPageInfo(PageInfo<ChargeBaseInfo> pageInfo){
		//
		PageInfo<ChargeBaseInfo> pageInfoNew = new PageInfo<ChargeBaseInfo>();
		pageInfoNew.setCount(pageInfo.getCount());
		pageInfoNew.setPageCount(pageInfo.getPageCount());
		pageInfoNew.setPageNo(pageInfo.getPageNo());
		pageInfoNew.setPageSize(pageInfo.getPageSize());
		List<ChargeBaseInfo> chargeBaseInfoList = pageInfo.getResult();
		//
		List<ChargeBaseInfo> chargeBaseInfoListNew = new ArrayList<ChargeBaseInfo>();
		//
		String typeCode = "BUSI_TYPE";
		String paramCode = "BUSI_TYPE_PARAM";
		//
		SysParamSingleCond sysParamSingleCond = new SysParamSingleCond();
		sysParamSingleCond.setTenantId(TENANT_ID);
		sysParamSingleCond.setTypeCode(typeCode);
		sysParamSingleCond.setParamCode(paramCode);
		//
		for(ChargeBaseInfo chargeBaseInfo : chargeBaseInfoList){
			sysParamSingleCond.setColumnValue(chargeBaseInfo.getBusiType());
			SysParam sysParam = DubboConsumerFactory.getService(ICacheSV.class).getSysParamSingle(sysParamSingleCond);//(TENANT_ID, typeCode, paramCode, chargeBaseInfo.getBusiType());
			
			chargeBaseInfo.setBusiType(sysParam.getColumnDesc());
			//
			chargeBaseInfoListNew.add(chargeBaseInfo);
		}
		pageInfoNew.setResult(chargeBaseInfoListNew);
		//
		return pageInfoNew;
	}
	
	
	/**
	 * 高级搜索 账户收支记录查询列表 分页
	 * @param request
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@RequestMapping(value="/account/queryAccountBalanceDetailList",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResponseData<PageInfo<ChargeBaseInfo>> queryAccountBalanceDetailList(HttpServletRequest request) {
		String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
		//
        String busiType = request.getParameter("busiType");
        //
        String selectDateId = request.getParameter("selectDateId");
        log.info("selectDateId:"+selectDateId);
        //
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		log.info("startTime:"+startTime);
		log.info("endTime:"+endTime);
        //
		ResponseData<PageInfo<ChargeBaseInfo>> responseData;
		//
		ChargeInfoQueryByAcctIdParam chargeInfoQueryByAcctIdParam = new ChargeInfoQueryByAcctIdParam();
		SLPClientUser user = this.getUserInfo(request);
		chargeInfoQueryByAcctIdParam.setAccountId(user.getAcctId());//(new Long(ACCOUNT_ID));
		chargeInfoQueryByAcctIdParam.setTenantId(user.getTenantId());//(TENANT_ID);
		//
		if(!StringUtil.isBlank(busiType)){
			chargeInfoQueryByAcctIdParam.setBusiType(busiType);
		}
		PageInfo<ChargeBaseInfo> chargeBaseInfoPageInfo = new PageInfo<ChargeBaseInfo>();
		chargeBaseInfoPageInfo.setPageNo(Integer.valueOf(strPageNo));
		chargeBaseInfoPageInfo.setPageSize(Integer.valueOf(strPageSize));
		chargeInfoQueryByAcctIdParam.setPageInfo(chargeBaseInfoPageInfo);
		//
		
		//快速检索 近三个月  近一个月 近七天 
		if(!StringUtil.isBlank(selectDateId)){
			//
			Map<String,String> time = new HashMap<String,String>();
			if(selectDateId.startsWith("MONTH_")){
				String monthAmount = selectDateId.replace("MONTH_", "");
				time = DateUtil.getTimeInterval(Calendar.MONTH, Integer.valueOf(monthAmount));
			}
			//
			if(selectDateId.startsWith("DAY_")){
				String dayAmount = selectDateId.replace("DAY_", "");
				time = DateUtil.getTimeInterval(Calendar.DATE, Integer.valueOf(dayAmount));
			}
			log.info("selectDate startTime:"+time);
			//
			if(!selectDateId.startsWith("ALL")){
				chargeInfoQueryByAcctIdParam.setStartTime(Timestamp.valueOf(time.get(DateUtil.KEY_START_TIME)));
				chargeInfoQueryByAcctIdParam.setEndTime(Timestamp.valueOf(time.get(DateUtil.KEY_END_TIME)));
			}
		}else{
			//如果开始时间和结束时间不为空
			if(!StringUtil.isBlank(startTime) && !StringUtil.isBlank(endTime)){
				chargeInfoQueryByAcctIdParam.setStartTime(DateUtil.getTimestamp(startTime+" 00:00:00",DateUtil.DATETIME_FORMAT));
				chargeInfoQueryByAcctIdParam.setEndTime(DateUtil.getTimestamp(endTime+" 23:59:59",DateUtil.DATETIME_FORMAT));
			}else{
				//默认查询7天前的记录
				Map<String,String> time = new HashMap<String,String>();
				time = DateUtil.getTimeInterval(Calendar.DATE, 7);
				log.info("selectDate default startTime:"+time);
				chargeInfoQueryByAcctIdParam.setStartTime(Timestamp.valueOf(time.get(DateUtil.KEY_START_TIME)));
				chargeInfoQueryByAcctIdParam.setEndTime(Timestamp.valueOf(time.get(DateUtil.KEY_END_TIME)));
			}
		}
		
		//
		PageInfo<ChargeBaseInfo> pageInfo = DubboConsumerFactory.getService(IPaymentQuerySV.class).queryChargeBaseInfoByAcctId(chargeInfoQueryByAcctIdParam);
		//
		pageInfo = this.getChargeBaseInfoPageInfo(pageInfo);
		//
		log.info(" queryAccountBalanceDetailList json:"+JSON.toJSONString(pageInfo));
		//
		responseData = new ResponseData<PageInfo<ChargeBaseInfo>>(ResponseData.AJAX_STATUS_SUCCESS,"success",pageInfo);
		//
		log.info(" ResponseData json:"+JSON.toJSONString(responseData));
		//
		return responseData;
    }
	
	@RequestMapping(value="/account/searchDateList",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<SysParam> searchDateList(HttpServletRequest request) {
		String typeCode = "SEARCH_DATE_TYPE";
		String paramCode = "DATE_TIME";
		SLPClientUser user = this.getUserInfo(request);
		//
		SysParamMultiCond sysParamMultiCond = new SysParamMultiCond();
		sysParamMultiCond.setTenantId(user.getTenantId());
		sysParamMultiCond.setTypeCode(typeCode);
		sysParamMultiCond.setParamCode(paramCode);
		//
		List<SysParam> sysParamList = DubboConsumerFactory.getService(ICacheSV.class).getSysParamList(sysParamMultiCond);
		//
		log.info("sysParamList:"+JSON.toJSONString(sysParamList));
		if(CollectionUtil.isEmpty(sysParamList)){
			sysParamList = new ArrayList<SysParam>();
			SysParam sysParam3Month = new SysParam();
			sysParam3Month.setTypeCode(typeCode);
			sysParam3Month.setParamCode(paramCode);
			sysParam3Month.setColumnValue("MONTH_3");
			sysParam3Month.setColumnDesc("近三个月");
			sysParamList.add(sysParam3Month);
			
		}
		
		
		return sysParamList;
	}
	/**
	 * 获取用户信息
	 * @param req
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	public SLPClientUser getUserInfo(HttpServletRequest req){
		 
		HttpSession session = req.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        //
        log.info("user account:"+user.getAcctId());
        log.info("user tenantId:"+user.getTenantId());
        return user;
	}
	/**
	 * 支付密码是否设置 0：未设置 1：已设置
	 * @param req
	 * @return
	 * @author zhangzd
	 * @ApiDocMethod
	 * @ApiCode
	 */
	@RequestMapping(value="/account/payPasswordIsSetting",method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String payPasswordIsSetting(HttpServletRequest req){
		SLPClientUser userInfo = this.getUserInfo(req);
		com.ai.slp.balance.api.accountquery.param.AccountIdParam accountIdParam = new com.ai.slp.balance.api.accountquery.param.AccountIdParam();
		accountIdParam.setTenantId(userInfo.getTenantId());
		accountIdParam.setAccountId(userInfo.getAcctId());
		//
		AccountInfoVo accountInfoVo = DubboConsumerFactory.getService(IAccountQuerySV.class).queryAccontById(accountIdParam);
		//
		return accountInfoVo.getPayCheck();
	}
}
