package com.ai.slp.mall.web.controller.account;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
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
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamMultiCond;
import com.ai.slp.user.api.ucuserphonebooks.interfaces.IUserPhoneBooksSV;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroup;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupMantainReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupQueryReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcTelGroupQueryResp;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchAddReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchAddResp;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchData;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksBatchDeleteReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksModifyReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UcUserPhonebooksQueryReq;
import com.ai.slp.user.api.ucuserphonebooks.param.UserPhonebook;
import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("/account/phonebook")
public class UserPhoneBookController {

	private static final Logger LOG = LoggerFactory.getLogger(UserPhoneBookController.class);

	@RequestMapping("/phonebookmgr")
	public ModelAndView phonebookmgr(HttpServletRequest request) {
		SLPClientUser user = this.getUserId(request);
		request.setAttribute("userId", user.getUserId());
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookmgr");
		return view;
	}

	@RequestMapping("/submitNewTelGroup")
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
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/queryTelGroups")
	@ResponseBody
	public ResponseData<List<UcTelGroup>> queryTelGroups(HttpServletRequest request, UcTelGroupQueryReq req) {
		ResponseData<List<UcTelGroup>> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setUserId(user.getUserId());
			req.setTenantId(user.getTenantId());
			UcTelGroupQueryResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).getUcTelGroups(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", resp.getGroups());
			} else {
				responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			responseData = new ResponseData<List<UcTelGroup>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/deleteUcTelGroup")
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
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/modifyUcTelGroup")
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
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}

		} catch (Exception e) {
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/phonebookdetail")
	public ModelAndView phonebookdetail(HttpServletRequest request) {
		String telGroupId = request.getParameter("telGroupId");
		String telGroupName = request.getParameter("telGroupName");
		SLPClientUser user = this.getUserId(request);
		request.setAttribute("userId", user.getUserId());
		request.setAttribute("telGroupId", telGroupId);
		if (StringUtil.isBlank(telGroupId)) {
			throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "请传入分组ID");
		}
		try {
			String nameChinese = URLDecoder.decode(telGroupName,"UTF-8");
			request.setAttribute("telGroupName", nameChinese);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookdetail");
		return view;
	}

	@RequestMapping("/queryUserPhonebooks")
	@ResponseBody
	public ResponseData<PageInfo<UserPhonebook>> queryUserPhonebooks(HttpServletRequest request, UcUserPhonebooksQueryReq req) {
		ResponseData<PageInfo<UserPhonebook>> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			req.setTenantId(user.getTenantId());
			PageInfo<UserPhonebook> pagInfo = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).queryUserPhonebooks(req);
			responseData = new ResponseData<PageInfo<UserPhonebook>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", pagInfo);
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<UserPhonebook>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/batchDeleteUserPhonebooks")
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
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/batchAddUserPhonebooks")
	@ResponseBody
	public ResponseData<UcUserPhonebooksBatchAddResp> batchAddUserPhonebooks(HttpServletRequest request, String datas) {
		ResponseData<UcUserPhonebooksBatchAddResp> responseData = null;
		try {
			List<UcUserPhonebooksBatchData> list = JSON.parseArray(datas, UcUserPhonebooksBatchData.class);
			SLPClientUser user = this.getUserId(request);
			if(list != null && list.size()>0){
				for(UcUserPhonebooksBatchData phoneBookData : list){
					phoneBookData.setTenantId(user.getTenantId());
					phoneBookData.setUserId(user.getUserId());
				}
			}
			UcUserPhonebooksBatchAddReq req = new UcUserPhonebooksBatchAddReq();
			req.setDatas(list);
			req.setTenantId(user.getTenantId());
			UcUserPhonebooksBatchAddResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).batchAddUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<UcUserPhonebooksBatchAddResp>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", resp);
			} else {
				responseData = new ResponseData<UcUserPhonebooksBatchAddResp>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<UcUserPhonebooksBatchAddResp>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/modifyPhonebook")
	@ResponseBody
	public ResponseData<String> modifyPhonebook(HttpServletRequest request, UcUserPhonebooksModifyReq phonebooksModifyReq) {
		ResponseData<String> responseData = null;
		try {
			SLPClientUser user = this.getUserId(request);
			phonebooksModifyReq.setTenantId(user.getTenantId());
			BaseResponse modifyResponse = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).modifyUserPhonebook(phonebooksModifyReq);
			if (modifyResponse.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功");
			} else {
				responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, modifyResponse.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/uploadPhoneBooks")
	@ResponseBody
	public ResponseData<UcUserPhonebooksBatchAddResp> uploadPhoneBooks(HttpServletRequest request) {
		ResponseData<UcUserPhonebooksBatchAddResp> responseData = null;
		try {
			String telGroupId = request.getParameter("telGroupId");
			if (StringUtil.isBlank(telGroupId)) {
				throw new BusinessException(ExceptCodeConstants.Special.PARAM_IS_NULL, "分组ID为空");
			}
			SLPClientUser user = this.getUserId(request);
			List<UcUserPhonebooksBatchData> list = new ArrayList<UcUserPhonebooksBatchData>();
			MultipartRequest multipartRequest = (MultipartRequest) request;
			MultipartFile uploadFile = multipartRequest.getFile("uploadFile");
			InputStream inputStream = uploadFile.getInputStream();
			Workbook workbook = createWorkbook(new BufferedInputStream(inputStream));
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				
				UcUserPhonebooksBatchData o = new UcUserPhonebooksBatchData();
				o.setTelGroupId(telGroupId);
				o.setUserId(user.getUserId());
				o.setTenantId(user.getTenantId());
				
				Cell cell0 = row.getCell(0);
				if(cell0 != null){
					cell0.setCellType(Cell.CELL_TYPE_STRING);
					String telName = cell0.getStringCellValue();
					o.setTelName(telName);
				}
				Cell cell1 = row.getCell(1);
				if(cell1 != null){
					cell1.setCellType(Cell.CELL_TYPE_STRING);
					String telMp = cell1.getStringCellValue();
					o.setTelMp(telMp);
				}
				list.add(o);
			}
			UcUserPhonebooksBatchAddReq req = new UcUserPhonebooksBatchAddReq();
			req.setDatas(list);
			req.setTenantId(user.getTenantId());
			System.out.println(JSonUtil.toJSon(req));
			UcUserPhonebooksBatchAddResp resp = DubboConsumerFactory.getService(IUserPhoneBooksSV.class).batchAddUserPhonebooks(req);
			if (resp.getResponseHeader().isSuccess()) {
				responseData = new ResponseData<UcUserPhonebooksBatchAddResp>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", resp);
			} else {
				responseData = new ResponseData<UcUserPhonebooksBatchAddResp>(ResponseData.AJAX_STATUS_FAILURE, resp.getResponseHeader().getResultMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseData = new ResponseData<UcUserPhonebooksBatchAddResp>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}
	
	private Workbook createWorkbook(InputStream in) throws IOException,InvalidFormatException {
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(in)) {
            return new HSSFWorkbook(in);
        }
        if (POIXMLDocument.hasOOXMLHeader(in)) {
            return new XSSFWorkbook(OPCPackage.open(in));
        }
        throw new IllegalArgumentException("你的excel版本目前poi解析不了");

    }

	private SLPClientUser getUserId(HttpServletRequest request) {
		SLPClientUser user = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
		if (user == null) {
			throw new SystemException("您没有登录，请先登录");
		}
		return user;
	}

	@RequestMapping("/getProvices")
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

	@RequestMapping("/getBasicOrgs")
	@ResponseBody
	public ResponseData<List<SysParam>> getBasicOrgs() {
		ResponseData<List<SysParam>> responseData = null;
		try {
			SysParamMultiCond param = new SysParamMultiCond("SLP", "PRODUCT", "BASIC_ORG_ID");
			List<SysParam> list = DubboConsumerFactory.getService(ICacheSV.class).getSysParamList(param);
			responseData = new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_SUCCESS, "处理成功", list);
		} catch (Exception e) {
			responseData = new ResponseData<List<SysParam>>(ResponseData.AJAX_STATUS_FAILURE, "处理失败");
		}
		return responseData;
	}

	@RequestMapping("/download/template")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();

		OutputStream os = null;
		try {
			os = response.getOutputStream();// 取得输出流
			String exportFileName = "template.xlsx";
			response.reset();// 清空输出流
			response.setContentType("application/x-xls");// 定义输出类型
			response.setHeader("Content-disposition", "attachment; filename=" + exportFileName);// 设定输出文件头
			String filePath = "/resources/template/phonebook.xlsx";
			String realPath = servletContext.getRealPath(filePath);
			FileInputStream fis = new FileInputStream(realPath);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				os.write(b, 0, i);
			}
			os.flush();
			os.close();
			fis.close();
		} catch (Exception e) {
			LOG.error("下载文件失败", e);
			if (os != null) {
				try {
					os.close();
				} catch (IOException e1) {
					LOG.error("操作异常", e1);
				}
			}
		}

	}// end of download

}
