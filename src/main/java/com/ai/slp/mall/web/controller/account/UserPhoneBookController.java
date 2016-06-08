package com.ai.slp.mall.web.controller.account;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.user.api.ucuserphonebooks.interfaces.IUserPhoneBooksSV;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroup;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupMantainReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupQueryReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupQueryResp;

@RestController
public class UserPhoneBookController {

	@RequestMapping("/account/phonebook/phonebookmgr")
	public ModelAndView phonebookmgr(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookmgr");
		return view;
	}

	@RequestMapping("/account/phonebook/submitNewTelGroup")
	@ResponseBody
	public ResponseData<String> submitNewTelGroup(UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).addUcTelGroup(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/queryTelGroups")
	@ResponseBody
	public ResponseData<List<UcTelGroup>> queryTelGroups(UcTelGroupQueryReq req) {
		ResponseData<List<UcTelGroup>> responseData = null;
		try {
			UcTelGroupQueryResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).getUcTelGroups(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功",
						resp.getGroups());
			} else {
				responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/deleteUcTelGroup")
	@ResponseBody
	public ResponseData<String> deleteUcTelGroup(UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).deleteUcTelGroup(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/modifyUcTelGroup")
	@ResponseBody
	public ResponseData<String> modifyUcTelGroup(UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).modifyUcTelGroup(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

}
