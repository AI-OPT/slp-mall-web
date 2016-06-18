package com.ai.slp.mall.web.controller.account;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.user.api.ucuserphonebooks.interfaces.IUserPhoneBooksSV;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroup;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupMantainReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupQueryReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupQueryResp;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchAddReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchAddResp;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchData;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchDeleteReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksQueryReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UserPhonebook;
import com.alibaba.fastjson.JSON;

@RestController
public class UserPhoneBookController {

	@RequestMapping("/account/phonebook/phonebookmgr")
	public ModelAndView phonebookmgr(HttpServletRequest request) {
		SLPClientUser user = this.getUserId(request);
		request.setAttribute("userId", user.getUserId());
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookmgr");
		return view;
	}

	@RequestMapping("/account/phonebook/submitNewTelGroup")
	@ResponseBody
	public ResponseData<String> submitNewTelGroup(HttpServletRequest request, UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setUserId(user.getUserId());
			req.setTenantId(user.getTenantId());
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
	public ResponseData<List<UcTelGroup>> queryTelGroups(HttpServletRequest request, UcTelGroupQueryReq req) {
		ResponseData<List<UcTelGroup>> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setUserId(user.getUserId());
			req.setTenantId(user.getTenantId());
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
	public ResponseData<String> deleteUcTelGroup(HttpServletRequest request, UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setUserId(user.getUserId());
			req.setTenantId(user.getTenantId());
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
	public ResponseData<String> modifyUcTelGroup(HttpServletRequest request, UcTelGroupMantainReq req) {
		ResponseData<String> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setUserId(user.getUserId());
			req.setTenantId(user.getTenantId());
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

	@RequestMapping("/account/phonebook/phonebookdetail")
	public ModelAndView phonebookdetail(HttpServletRequest request) {
		String telGroupId = request.getParameter("telGroupId");
		if (StringUtil.isBlank(telGroupId)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请传入分组ID");
		}
		SLPClientUser user = this.getUserId(request);
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("telGroupId", telGroupId);
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookdetail");
		return view;
	}

	@RequestMapping("/account/phonebook/queryUserPhonebooks")
	@ResponseBody
	public ResponseData<PageInfo<UserPhonebook>> queryUserPhonebooks(HttpServletRequest request,
			UcUserPhonebooksQueryReq req) {
		ResponseData<PageInfo<UserPhonebook>> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setTenantId(user.getTenantId());
			PageInfo<UserPhonebook> pagInfo = DubboConsumerFactory.getService(IUserPhoneBooksSV.class)
					.queryUserPhonebooks(req);
			responseData = new ResponseData<PageInfo<UserPhonebook>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", pagInfo);
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<UserPhonebook>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/batchDeleteUserPhonebooks")
	@ResponseBody
	public ResponseData<String> batchDeleteUserPhonebooks(HttpServletRequest request, String recordIds) {
		ResponseData<String> responseData = null;
		try {
			UcUserPhonebooksBatchDeleteReq req = new UcUserPhonebooksBatchDeleteReq();
			List<String> list = new ArrayList<String>();
			String[] arr = recordIds.split(",");
			if (arr != null && arr.length > 0) {
				for (String id : arr) {
					list.add(id);
				}
			}
			SLPClientUser user = this.getUserId(request);
			req.setTenantId(user.getTenantId());
			req.setRecordIds(list);
			BaseResponse resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).batchDeleteUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", "");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/batchAddUserPhonebooks")
	@ResponseBody
	public ResponseData<String> batchAddUserPhonebooks(HttpServletRequest request, String datas) {
		ResponseData<String> responseData = null;
		try {
			List<UcUserPhonebooksBatchData> list = JSON.parseArray(datas, UcUserPhonebooksBatchData.class);
			UcUserPhonebooksBatchAddReq req = new UcUserPhonebooksBatchAddReq();
			req.setDatas(list);
			SLPClientUser user = this.getUserId(request);
			req.setTenantId(user.getTenantId());
			UcUserPhonebooksBatchAddResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class)
					.batchAddUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", resp.getResult());
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/uploadPhoneBooks")
	@ResponseBody
	public ResponseData<String> uploadPhoneBooks(HttpServletRequest request) {
		ResponseData<String> responseData = null;
		try {
			String telGroupId = request.getParameter("telGroupId");
			if (StringUtil.isBlank(telGroupId)) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "分组ID为空");
			}
			SLPClientUser user = this.getUserId(request);
			List<UcUserPhonebooksBatchData> list = new ArrayList<UcUserPhonebooksBatchData>();
			MultipartRequest multipartRequest = (MultipartRequest) request;
			MultipartFile uploadFile = multipartRequest.getFile("uploadFile");

			XSSFWorkbook workbook = new XSSFWorkbook(uploadFile.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				XSSFRow row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				XSSFCell cell0 = row.getCell(0);
				XSSFCell cell1 = row.getCell(1);
				String telName = cell0.getStringCellValue();
				String telMp = cell1.getStringCellValue();
				UcUserPhonebooksBatchData o = new UcUserPhonebooksBatchData();
				o.setTelGroupId(telGroupId);
				o.setUserId(user.getUserId());
				o.setTelName(telName);
				o.setTelMp(telMp);
				list.add(o);
			}
			UcUserPhonebooksBatchAddReq req = new UcUserPhonebooksBatchAddReq();
			req.setDatas(list);
			req.setTenantId(user.getTenantId());
			UcUserPhonebooksBatchAddResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class)
					.batchAddUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", resp.getResult());
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE,
						resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	private SLPClientUser getUserId(HttpServletRequest request) {
		SLPClientUser user = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		if (user == null) {
			throw new SystemException("您没有登录，请先登录");
		}
		return user;
	}

	@RequestMapping("/account/phonebook/getProvices")
	@ResponseBody
	public ResponseData<List<GnAreaVo>> getProvices() {
		ResponseData<List<GnAreaVo>> responseData = null;
		try {
			List<GnAreaVo> list = DubboConsumerFactory.getService(IGnAreaQuerySV.class).getProvinceList();
			responseData = new ResponseData<List<GnAreaVo>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", list);
		} catch (Exception e) {
			responseData = new ResponseData<List<GnAreaVo>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/account/phonebook/getBasicOrgs")
	@ResponseBody
	public ResponseData<List<SysParam>> getBasicOrgs() {
		ResponseData<List<SysParam>> responseData = null;
		try {
			List<SysParam> list = DubboConsumerFactory.getService(ICacheSV.class).getSysParams("SLP", "PRODUCT",
					"BASIC_ORG_ID");
			responseData = new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", list);
		} catch (Exception e) {
			responseData = new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

}
