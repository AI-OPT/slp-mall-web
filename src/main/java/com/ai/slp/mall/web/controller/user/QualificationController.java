package com.ai.slp.mall.web.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamMultiCond;
import com.ai.slp.common.api.industry.interfaces.IIndustrySV;
import com.ai.slp.common.api.industry.param.IndustryQueryResponse;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.model.user.CustFileListVo;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProdCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatQuery;
import com.ai.slp.user.api.bankinfo.interfaces.IUcBankInfoSV;
import com.ai.slp.user.api.bankinfo.param.InsertBankInfoRequest;
import com.ai.slp.user.api.bankinfo.param.QueryBankInfoSingleRequest;
import com.ai.slp.user.api.bankinfo.param.QueryBankInfoSingleResponse;
import com.ai.slp.user.api.bankinfo.param.UpdateBankInfoRequest;
import com.ai.slp.user.api.contactsinfo.interfaces.IUcContactsInfoSV;
import com.ai.slp.user.api.contactsinfo.param.InsertContactsInfoRequest;
import com.ai.slp.user.api.contactsinfo.param.QueryContactsInfoSingleRequest;
import com.ai.slp.user.api.contactsinfo.param.QueryContactsInfoSingleResponse;
import com.ai.slp.user.api.contactsinfo.param.UpdateContactsInfoRequest;
import com.ai.slp.user.api.keyinfo.interfaces.IUcKeyInfoSV;
import com.ai.slp.user.api.keyinfo.param.CmCustFileExtVo;
import com.ai.slp.user.api.keyinfo.param.InsertCustFileExtRequest;
import com.ai.slp.user.api.keyinfo.param.InsertCustKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.InsertGroupKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.QueryCustFileExtRequest;
import com.ai.slp.user.api.keyinfo.param.QueryCustFileExtResponse;
import com.ai.slp.user.api.keyinfo.param.SearchCustKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.SearchCustKeyInfoResponse;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoResponse;
import com.ai.slp.user.api.keyinfo.param.UpdateCustFileExtRequest;
import com.ai.slp.user.api.keyinfo.param.UpdateCustKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.UpdateGroupKeyInfoRequest;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.UpdateUserInfoRequest;
import com.alibaba.fastjson.JSON;

@RequestMapping("/user/qualification")
@Controller
public class QualificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QualificationController.class);

    // 代理商选择页面
    @RequestMapping("/toAgentSelectPage")
    public ModelAndView toAgentSelectPage(HttpServletRequest request) {
    	SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取个人客户信息
         */
    	 SearchCustKeyInfoResponse custKeyInfoResponse = getCustKeyBaseinfo(userId);
         if(!StringUtil.isBlank(custKeyInfoResponse.getUserId())){
            return  new ModelAndView("redirect:/user/qualification/editAgentPersonal");
         }
         /**
          * 获取企业客户信息
          */
         SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
         if(!ExceptCodeConstants.Special.NO_RESULT.equals(grouKeyInfoResponse.getResponseHeader().getResultCode())){
             return new ModelAndView("redirect:/user/qualification/editAgentEnterprise");
         }
        return new ModelAndView("jsp/user/qualification/agent-select");
    }

    // 代理商个人页面
    @RequestMapping("/toAgentPersonalPage")
    public ModelAndView toAgentPersonalPage(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取个人客户信息
         */
        SearchCustKeyInfoResponse custKeyInfoResponse = getCustKeyBaseinfo(userId);
        if(!StringUtil.isBlank(custKeyInfoResponse.getUserId())){
           return  new ModelAndView("redirect:/user/qualification/editAgentPersonal");
        }
         
        //获取注册地址
        List<GnAreaVo> provinceList = getProvinceList();
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        //获取学历信息
        Map<String,String> educationMap = getCustEducationMap();
        //获取收入信息
        Map<String,String> incomeLevelMap = getIncomeLevelMap();
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("provinceList", provinceList);
        model.put("groupMember", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
        model.put("educationMap", educationMap);
        model.put("incomeLevelMap", incomeLevelMap);
        
        return new ModelAndView("jsp/user/qualification/agent-personal", model);
    }

    // 供应商页面
    @RequestMapping("/toSupplierPage")
    public ModelAndView toSupplierPage(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession()
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        if(!ExceptCodeConstants.Special.NO_RESULT.equals(grouKeyInfoResponse.getResponseHeader().getResultCode())){
           return  new ModelAndView("redirect:/user/qualification/editSupplier");
        }
        //获取地区信息
        List<GnAreaVo> provinceList = getProvinceList();
        //获取行业数据
        Map<String,String> industryMap = getIndustry();
        //获取纳税人类型
        Map<String,String> taxpayerTypeMap = getTaxpayerTypeMap();
        //获取纳税类型税码信息
        Map<String,String> taxpayerTypeCodeMap = getTaxpayerTypeCodeMap();
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        //获取商品信息
        Map<String,String> prodCatMap = getProdCatInfo();
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("provinceList", provinceList);
        model.put("industryMap", industryMap);
        model.put("taxpayerTypeMap", taxpayerTypeMap);
        model.put("taxpayerTypeCodeMap", taxpayerTypeCodeMap);
        model.put("groupMemberMap", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
        model.put("prodCatMap", prodCatMap);
        
        return new ModelAndView("jsp/user/qualification/supplier", model);
    }

    // 代理商企业页面
    @RequestMapping("/toAgentEnterprisePage")
    public ModelAndView toAgentEnterprisePage(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession()
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        if(!ExceptCodeConstants.Special.NO_RESULT.equals(grouKeyInfoResponse.getResponseHeader().getResultCode())){
            return new ModelAndView("redirect:/user/qualification/editAgentEnterprise");
        }
        //获取地区信息
        List<GnAreaVo> provinceList = getProvinceList();
        //获取行业信息
        Map<String,String> industryMap = getIndustry();
        //获取纳税人类型
        Map<String,String> taxpayerTypeMap = getTaxpayerTypeMap();
        //获取纳税类型税码信息
        Map<String,String> taxpayerTypeCodeMap = getTaxpayerTypeCodeMap();
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("provinceList", provinceList);
        model.put("industryMap", industryMap);
        model.put("taxpayerTypeMap", taxpayerTypeMap);
        model.put("taxpayerTypeCodeMap", taxpayerTypeCodeMap);
        model.put("groupMemberMap", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
       
        return new ModelAndView("jsp/user/qualification/agent-enterprise", model);
    }

    // 企业页面
    @RequestMapping("/toEnterprisePage")
    public ModelAndView toEnterprisePage(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession()
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        if(!ExceptCodeConstants.Special.NO_RESULT.equals(grouKeyInfoResponse.getResponseHeader().getResultCode())){
            return new ModelAndView("redirect:/user/qualification/editEnterprise");
        }
        
        //获取注册地址
        List<GnAreaVo> provinceList = getProvinceList();
        //获取行业信息
        Map<String,String> industryMap = getIndustry();
        Map<String, Object> model = new HashMap<String, Object>();
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        model.put("provinceList", provinceList);
        model.put("industryMap", industryMap);
        model.put("groupMember", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
        
        return new ModelAndView("jsp/user/qualification/enterprise", model);
    }

    // 保存企业申请信息
    @RequestMapping(value = "/saveEnterprise")
    @ResponseBody
    public ResponseData<String> saveEnterprise(HttpServletRequest request,
            InsertGroupKeyInfoRequest insertGroupKeyInfoRequest,
            InsertContactsInfoRequest insertContactsInfoRequest, CustFileListVo custFileListVo)
            throws UnsupportedEncodingException {

        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        // establishTime
        // 判断手机验证码
        if (validatePhoneCode(request) != null) {
            return validatePhoneCode(request);
        } else {
            HttpSession session = request.getSession();
            SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
            // 企业关键信息
            if (request.getParameter("establishTime") != null) 
                insertGroupKeyInfoRequest.setCertIssueDate(DateUtil.getTimestamp(request.getParameter("establishTime")));            insertGroupKeyInfoRequest.setTenantId(user.getTenantId());            insertGroupKeyInfoRequest.setUserType(user.getUserType());
            insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
            insertGroupKeyInfoRequest.setUserType(user.getUserType());
            insertGroupKeyInfoRequest.setUserId(user.getUserId());
            insertGroupKeyInfoRequest.setAuditState(SLPMallConstants.AuditState.UserState_ready);
            // 附件信息
            for (CmCustFileExtVo cmCustFileExtVo : custFileListVo.getList()) {
                cmCustFileExtVo.setTenantId(user.getTenantId());
                cmCustFileExtVo.setUserId(user.getUserId());
            }
            InsertCustFileExtRequest insertCustFileExtRequest = new InsertCustFileExtRequest();
            insertCustFileExtRequest.setList(custFileListVo.getList());

            // 联系人信息
            insertContactsInfoRequest.setTenantId(user.getTenantId());
            insertContactsInfoRequest.setUserId(user.getUserId());
            IUcBankInfoSV ucBankInfoSV = null;
            InsertBankInfoRequest insertBankInfoRequest = null;
            
            // 保存银行信息
            if (request.getParameter("bankName") != null
                    && request.getParameter("bankAccount") != null) {
                insertBankInfoRequest = new InsertBankInfoRequest();
                insertBankInfoRequest.setTenantId(user.getTenantId());
                insertBankInfoRequest.setUserId(user.getUserId());
                insertBankInfoRequest.setBankName(request.getParameter("bankName"));
                insertBankInfoRequest.setAcctNo(request.getParameter("bankAccount"));
                insertBankInfoRequest.setSubBranchName(request.getParameter("subbranchName"));
                ucBankInfoSV = DubboConsumerFactory.getService(IUcBankInfoSV.class);
            }
            IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
            IUcContactsInfoSV contactsInfoSV = DubboConsumerFactory.getService(IUcContactsInfoSV.class);

            try {
            	SearchGroupKeyInfoRequest searchGroupKeyInfoRequest = new SearchGroupKeyInfoRequest();
            	searchGroupKeyInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
            	searchGroupKeyInfoRequest.setUserId(user.getUserId());
            	if(ucKeyInfoSV.searchGroupKeyInfo(searchGroupKeyInfoRequest).getUserId()!=null){
            		   responseData = new ResponseData<String>(VerifyConstants.QualificationConstants.ERROR_CODE, "操作失败", null);
                       responseHeader = new ResponseHeader(false,VerifyConstants.QualificationConstants.ERROR_CODE, "操作失败");
            	}else{
                ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
                ucKeyInfoSV.insertCustFileExt(insertCustFileExtRequest);
                //更改用户账户状态
                updateUserState(user, SLPMallConstants.UserState.UserState_register);
                contactsInfoSV.insertContactsInfo(insertContactsInfoRequest);
                // 判断是否保存银行信息
                if (ucBankInfoSV != null) {
                    ucBankInfoSV.insertBankInfo(insertBankInfoRequest);
                }
                responseData = new ResponseData<String>(VerifyConstants.QualificationConstants.SUCCESS_CODE, "操作成功", null);
                responseHeader = new ResponseHeader(true,VerifyConstants.QualificationConstants.SUCCESS_CODE, "操作成功");
            	}
            } catch (Exception e) {
                LOGGER.error("操作失败");
                responseData = new ResponseData<String>(VerifyConstants.QualificationConstants.ERROR_CODE, "操作失败", null);
                responseHeader = new ResponseHeader(false,VerifyConstants.QualificationConstants.ERROR_CODE, "操作失败");
            }
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }

    // 校验手机验证码
    private ResponseData<String> validatePhoneCode(HttpServletRequest request) {
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        String sessionId = request.getSession().getId();
        String userMp = request.getParameter("contactMp");
        String phoneCode = request.getParameter("phoneCode");
        SafetyConfirmData safetyConfirmData = new SafetyConfirmData();
        safetyConfirmData.setUserMp(userMp);
        safetyConfirmData.setVerifyCode(phoneCode);
        // 验证短信验证码
        ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(sessionId, cacheClient,
                safetyConfirmData);
        String resultCode = phoneCheck.getResponseHeader().getResultCode();
        if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(resultCode))
            return phoneCheck;
        return null;
    }

    @RequestMapping("/savePersonalInfo")
    @ResponseBody
    public ResponseData<String> savePersonalInfo(HttpServletRequest request,
            InsertCustKeyInfoRequest insertCustKeyInfoRequest, CustFileListVo custFileListVo) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;

        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        // 个人信息
        insertCustKeyInfoRequest.setTenantId(user.getTenantId());
        insertCustKeyInfoRequest.setUserType(user.getUserType());
        insertCustKeyInfoRequest.setUserId(user.getUserId());
        if(request.getParameter("yy_mm_dd")!=null&&request.getParameter("mm")!=null&&request.getParameter("dd")!=null)
        insertCustKeyInfoRequest.setCustBirthday(DateUtil.getTimestamp(request.getParameter("yy_mm_dd") + "-"+ request.getParameter("mm") + "-" + request.getParameter("dd")));
        insertCustKeyInfoRequest.setAuditState(SLPMallConstants.AuditState.UserState_ready);
        // 附件信息
        for (CmCustFileExtVo cmCustFileExtVo : custFileListVo.getList()) {
            cmCustFileExtVo.setTenantId(user.getTenantId());
            cmCustFileExtVo.setUserId(user.getUserId());
        }
        InsertCustFileExtRequest insertCustFileExtRequest = new InsertCustFileExtRequest();
        insertCustFileExtRequest.setList(custFileListVo.getList());
        // 联系人信息
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try {
        	SearchCustKeyInfoRequest searchCustKeyInfoRequest = new SearchCustKeyInfoRequest();
        	searchCustKeyInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        	searchCustKeyInfoRequest.setUserId(user.getUserId());
        	//判断数据库是否存在记录
        	//若存在则保存失败
        	if(ucKeyInfoSV.searchCustKeyInfo(searchCustKeyInfoRequest).getUserId()!=null){
        		 responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
                 responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败");
        	}else{
            ucKeyInfoSV.insertCustKeyInfo(insertCustKeyInfoRequest);
            ucKeyInfoSV.insertCustFileExt(insertCustFileExtRequest);
            //更改用户账户状态
            updateUserState(user, SLPMallConstants.UserState.UserState_register);
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
            responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功");
        	}
        } catch (Exception e) {
            LOGGER.error("操作失败");
            responseData = new ResponseData<String>(
                    SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,
                    SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }

    @RequestMapping("/getCityListByProviceCode")
    @ResponseBody
    public ResponseData<String> getCityListByProviceCode(HttpServletRequest request,
            String provinceCode) {
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> list = areaQuerySV.getCityListByProviceCode(provinceCode);
        String str = "";
        ResponseData<String> responseData = null;
        if (!CollectionUtil.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                GnAreaVo gnAreaVo = list.get(i);
                str = str + "<option value=" + gnAreaVo.getCityCode() + ">" + gnAreaVo.getAreaName()
                        + "</option>";
            }
            str = "<option value='0'>请选择</option>" + str;
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", str);
        } else {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "没有查询结果",
                    str);
        }

        return responseData;
    }

    @RequestMapping("/getStreetListByCountyCode")
    @ResponseBody
    public ResponseData<String> getStreetListByCountyCode(HttpServletRequest request,
            String countyCode) {
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> list = areaQuerySV.getCountyListByCityCode(countyCode);
        String str = "";
        ResponseData<String> responseData = null;
        if (!CollectionUtil.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                GnAreaVo gnAreaVo = list.get(i);
                str = str + "<option value=" + gnAreaVo.getAreaCode() + ">" + gnAreaVo.getAreaName()
                        + "</option>";
            }
            str = "<option value='0'>请选择</option>" + str;
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", str);
        } else {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "没有查询结果",
                    str);
        }

        return responseData;
    }

    // 获取图片
    @RequestMapping("/getImg")
    @ResponseBody
    public ResponseData<String> getImg(HttpServletRequest request, String idpsId) {
        ResponseData<String> responseData = null;
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        try {
            String url = im.getImageUrl(idpsId, ".jpg");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功");
            responseData.setData(url);
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "没有查询结果");
        }
        return responseData;
    }

    // 上传图片
    @RequestMapping(value = "/uploadImg", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> uploadImg(HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        String imageId = request.getParameter("imageId");
        MultipartHttpServletRequest file = (MultipartHttpServletRequest) request;
        MultipartFile image = file.getFile(imageId);
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        // 获取图片信息
        try {
            String idpsId = im.upLoadImage(image.getBytes(), UUIDUtil.genId32() + ".png");
            String url = im.getImageUrl(idpsId, ".jpg", "80x80");
            map.put("isTrue", true);
            map.put("idpsId", idpsId);
            map.put("url", url);
        } catch (IOException e) {
            LOGGER.error("保存失败");
            map.put("isTrue", false);
        }
        LOGGER.info("Map:---->>" + JSON.toJSONString(map));
        return map;
    }

    // 删除服务器图片
    @RequestMapping(value = "/deleteImg")
    @ResponseBody
    public Map<String, Object> deleteImg(String idpsId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        // 获取图片信息
        try {
            im.deleteImage(idpsId);
            map.put("isTrue", true);
        } catch (Exception e) {
            LOGGER.error("保存失败");
            map.put("isTrue", false);
        }
        LOGGER.info("Map:---->>" + JSON.toJSONString(map));
        return map;
    }

    @RequestMapping("/editEnterprise")
    public ModelAndView editEnterprise(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取联系人信息
         */
        QueryContactsInfoSingleResponse contactsInfoInfoResponse = getContactsInfo(userId);
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        /**
         * 获取图片信息
         */
        QueryCustFileExtResponse custFileResponse = getCustFileExt(userId);
        List<CmCustFileExtVo> custFileExtVoList = custFileResponse.getList();
        List<String> idpsIdList = new ArrayList<String>();
        for (CmCustFileExtVo cmCustFileExtVo : custFileExtVoList) {
			idpsIdList.add(cmCustFileExtVo.getAttrValue());
		}
        Map<String,String> imageMap = getImageUrl(custFileExtVoList);
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        //获取地区信息
        List<GnAreaVo> provinceList = getProvinceList();
        //获取行业信息
        Map<String,String> industryMap = getIndustry();
        
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> cityList = areaQuerySV.getCityListByProviceCode(grouKeyInfoResponse.getProvinceCode());
        List<GnAreaVo> countyList = areaQuerySV.getCountyListByCityCode(grouKeyInfoResponse.getCityCode());
        //获取下拉Code
        Map<String,Object> codeMap = new HashMap<String,Object>();
        codeMap.put("provinceCode", grouKeyInfoResponse.getProvinceCode());
        codeMap.put("cityCode", grouKeyInfoResponse.getCityCode());
        codeMap.put("countyCode", grouKeyInfoResponse.getCountyCode());
        codeMap.put("certAddr", grouKeyInfoResponse.getCertAddr());
        codeMap.put("groupIndustry", grouKeyInfoResponse.getGroupIndustry());
        codeMap.put("groupMemberScale", grouKeyInfoResponse.getGroupMemberScale());
        codeMap.put("groupType", grouKeyInfoResponse.getGroupType());
        codeMap.put("contactDept", contactsInfoInfoResponse.getContactDept());
        codeMap.put("idpsIdList", idpsIdList);
        
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        String provinceName = cacheSv.getAreaName(grouKeyInfoResponse.getProvinceCode());
        String cityCode = cacheSv.getAreaName(grouKeyInfoResponse.getCityCode());
        String county = cacheSv.getAreaName(grouKeyInfoResponse.getCountyCode());
        
        grouKeyInfoResponse.setProvinceCode(provinceName+" "+cityCode+" "+county+" "+grouKeyInfoResponse.getCertAddr());
        grouKeyInfoResponse.setGroupIndustry(industryMap.get(grouKeyInfoResponse.getGroupIndustry()));
        grouKeyInfoResponse.setGroupMemberScale(groupMemberMap.get(grouKeyInfoResponse.getGroupMemberScale()));
        grouKeyInfoResponse.setGroupType(groupTypeMap.get(grouKeyInfoResponse.getGroupType()));
        contactsInfoInfoResponse.setContactDept(contactDeptMap.get(contactsInfoInfoResponse.getContactDept()));
        
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("contactsInfo", contactsInfoInfoResponse);
        model.put("codeMap", codeMap);
        model.put("cityList", cityList);
        model.put("countyList", countyList);
        model.put("groupKeyInfo", grouKeyInfoResponse);
        model.put("custFileResponse", custFileResponse);
        model.put("provinceList", provinceList);
        model.put("industryMap", industryMap);
        model.put("imageMap", imageMap);
        model.put("groupMemberMap", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
        return new ModelAndView("jsp/user/qualification/enterprise_edit", model);
    }
    
    
    
    @RequestMapping("/editAgentPersonal")
    public ModelAndView editAgentPersonal(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取个人客户信息
         */
        SearchCustKeyInfoResponse custKeyInfoResponse = getCustKeyBaseinfo(userId);
        
        /**
         * 获取图片信息
         */
        QueryCustFileExtResponse custFileResponse = getCustFileExt(userId);
        List<String> idpsIdList = new ArrayList<String>();
        List<CmCustFileExtVo> custFileExtVoList = custFileResponse.getList();
        for (CmCustFileExtVo cmCustFileExtVo : custFileExtVoList) {
			idpsIdList.add(cmCustFileExtVo.getAttrValue());
		}
        Map<String,String> imageMap = getImageUrl(custFileExtVoList);
        //获取学历信息
        Map<String,String> educationMap = getCustEducationMap();
        //获取收入信息
        Map<String,String> incomeLevelMap = getIncomeLevelMap();
        
        //获取地区信息
        List<GnAreaVo> provinceList = getProvinceList();
        
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> cityList = areaQuerySV.getCityListByProviceCode(custKeyInfoResponse.getCustProvinceCode());
        List<GnAreaVo> countyList = areaQuerySV.getCountyListByCityCode(custKeyInfoResponse.getCustCityCode());
        
        //格式化生日的日期格式
        SimpleDateFormat fomate = new SimpleDateFormat("yyyy-MM-dd");
        String birthday = "";
        if(custKeyInfoResponse.getCustBirthday()!=null){
            birthday = fomate.format(custKeyInfoResponse.getCustBirthday());
        }
        
        
        Map<String,Object> codeMap = new HashMap<String,Object>();
        codeMap.put("provinceCode", custKeyInfoResponse.getCustProvinceCode());
        codeMap.put("cityCode", custKeyInfoResponse.getCustCityCode());
        codeMap.put("countyCode", custKeyInfoResponse.getCustCountyCode());
        codeMap.put("certAddr", custKeyInfoResponse.getCustAddr());
        codeMap.put("incomeLevel", custKeyInfoResponse.getIncomeLevel());
        codeMap.put("custEducation", custKeyInfoResponse.getCustEducation());
        if(custKeyInfoResponse.getCustBirthday()!=null){
            codeMap.put("year", birthday.substring(0, 4));
            codeMap.put("month", birthday.substring(5,7));
            codeMap.put("day", birthday.substring(8, 10));
        }
        codeMap.put("idpsIdList", idpsIdList);
        custKeyInfoResponse.setIncomeLevel(incomeLevelMap.get(custKeyInfoResponse.getIncomeLevel()));
        custKeyInfoResponse.setCustEducation(educationMap.get(custKeyInfoResponse.getCustEducation()));
        
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        String provinceName = cacheSv.getAreaName(custKeyInfoResponse.getCustProvinceCode());
        String cityCode = cacheSv.getAreaName(custKeyInfoResponse.getCustCityCode());
        String county = cacheSv.getAreaName(custKeyInfoResponse.getCustCountyCode());
        custKeyInfoResponse.setProvinceCode(provinceName+" "+cityCode+" "+county+" "+custKeyInfoResponse.getCustAddr());
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("custKeyInfo", custKeyInfoResponse);
        model.put("codeMap", codeMap);
        model.put("cityList", cityList);
        model.put("countyList", countyList);
        model.put("imageMap", imageMap);
        model.put("educationMap", educationMap);
        model.put("incomeLevelMap", incomeLevelMap);
        model.put("provinceList", provinceList);
        model.put("birthday", birthday);
        return new ModelAndView("jsp/user/qualification/agent-personal-edit", model);
    }
    
    
    //更新用户信息
    @RequestMapping("/updatePersonalInfo")
    @ResponseBody
    public ResponseData<String> updatePersonalInfo(HttpServletRequest request,
            UpdateCustKeyInfoRequest updateCustKeyInfoRequest, CustFileListVo custFileListVo) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;

        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        
        // 个人信息
        updateCustKeyInfoRequest.setTenantId(user.getTenantId());
        updateCustKeyInfoRequest.setUserType(user.getUserType());
        updateCustKeyInfoRequest.setUserId(user.getUserId());
        updateCustKeyInfoRequest.setAuditState(SLPMallConstants.AuditState.UserState_ready);
        if(request.getParameter("yy_mm_dd")!=null&&request.getParameter("mm")!=null&&request.getParameter("dd")!=null)
        updateCustKeyInfoRequest.setCustBirthday(DateUtil.getTimestamp(request.getParameter("yy_mm_dd") + "-"+ request.getParameter("mm") + "-" + request.getParameter("dd")));
        // 附件信息
        for (CmCustFileExtVo cmCustFileExtVo : custFileListVo.getList()) {
            cmCustFileExtVo.setTenantId(user.getTenantId());
            cmCustFileExtVo.setUserId(user.getUserId());
        }
        UpdateCustFileExtRequest updateCustFileExtRequest = new UpdateCustFileExtRequest();
        updateCustFileExtRequest.setList(custFileListVo.getList());
        // 联系人信息
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try {
            ucKeyInfoSV.updateCustKeyInfo(updateCustKeyInfoRequest);
            ucKeyInfoSV.updateCustFileExt(updateCustFileExtRequest);
            //更改用户账户状态
            updateUserState(user, SLPMallConstants.UserState.UserState_register);
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
            responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功");
        } catch (Exception e) {
            LOGGER.error("操作失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    @RequestMapping("/editAgentEnterprise")
    public ModelAndView editAgentEnterprise(HttpServletRequest request){
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取联系人信息
         */
        QueryContactsInfoSingleResponse contactsInfoInfoResponse = getContactsInfo(userId);
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        /**
         * 获取图片信息
         */
        QueryCustFileExtResponse custFileResponse = getCustFileExt(userId);
        List<CmCustFileExtVo> custFileExtVoList = custFileResponse.getList();
        List<String> idpsIdList = new ArrayList<String>();
        for (CmCustFileExtVo cmCustFileExtVo : custFileExtVoList) {
			idpsIdList.add(cmCustFileExtVo.getAttrValue());
		}
        Map<String,String> imageMap = getImageUrl(custFileExtVoList);
        /**
         * 银行信息
         */
        QueryBankInfoSingleResponse bankInfoResponse = getBankInfo(userId);
        
        //获取纳税人类型
        Map<String,String> taxpayerTypeMap = getTaxpayerTypeMap();
        //获取纳税类型税码信息
        Map<String,String> taxpayerTypeCodeMap = getTaxpayerTypeCodeMap();
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        //获取地区信息
        List<GnAreaVo> provinceList = getProvinceList();
        //获取行业信息
        Map<String,String> industryMap = getIndustry();
        
        //获取下拉Code
        Map<String,Object> codeMap = new HashMap<String,Object>();
        codeMap.put("provinceCode", grouKeyInfoResponse.getProvinceCode());
        codeMap.put("cityCode", grouKeyInfoResponse.getCityCode());
        codeMap.put("countyCode", grouKeyInfoResponse.getCountyCode());
        codeMap.put("certAddr", grouKeyInfoResponse.getCertAddr());
        codeMap.put("groupIndustry", grouKeyInfoResponse.getGroupIndustry());
        codeMap.put("taxpayerType", grouKeyInfoResponse.getTaxpayerType());
        codeMap.put("taxpayerTypeCode", grouKeyInfoResponse.getTaxpayerTypeCode());
        codeMap.put("groupMemberScale", grouKeyInfoResponse.getGroupMemberScale());
        codeMap.put("groupType", grouKeyInfoResponse.getGroupType());
        codeMap.put("contactDept", contactsInfoInfoResponse.getContactDept());
        codeMap.put("certIssueDate", grouKeyInfoResponse.getCertIssueDate().toString().substring(0, 10));
        codeMap.put("idpsIdList", idpsIdList);
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> cityList = areaQuerySV.getCityListByProviceCode(grouKeyInfoResponse.getProvinceCode());
        List<GnAreaVo> countyList = areaQuerySV.getCountyListByCityCode(grouKeyInfoResponse.getCityCode());
        
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        String provinceName = cacheSv.getAreaName(grouKeyInfoResponse.getProvinceCode());
        String cityCode = cacheSv.getAreaName(grouKeyInfoResponse.getCityCode());
        String county = cacheSv.getAreaName(grouKeyInfoResponse.getCountyCode());
        
        grouKeyInfoResponse.setProvinceCode(provinceName+" "+cityCode+" "+county+" "+grouKeyInfoResponse.getCertAddr());
        grouKeyInfoResponse.setGroupMemberScale(groupMemberMap.get(grouKeyInfoResponse.getGroupMemberScale()));
        grouKeyInfoResponse.setGroupType(groupTypeMap.get(grouKeyInfoResponse.getGroupType()));
        contactsInfoInfoResponse.setContactDept(contactDeptMap.get(contactsInfoInfoResponse.getContactDept()));
        grouKeyInfoResponse.setTaxpayerType(taxpayerTypeMap.get(grouKeyInfoResponse.getTaxpayerType()));
        grouKeyInfoResponse.setTaxpayerTypeCode(taxpayerTypeCodeMap.get(grouKeyInfoResponse.getTaxpayerTypeCode()));
        grouKeyInfoResponse.setGroupIndustry(industryMap.get(grouKeyInfoResponse.getGroupIndustry()));
        
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("contactsInfo", contactsInfoInfoResponse);
        model.put("codeMap", codeMap);
        model.put("cityList", cityList);
        model.put("countyList", countyList);
        model.put("groupKeyInfo", grouKeyInfoResponse);
        model.put("custFileResponse", custFileResponse);
        model.put("provinceList", provinceList);
        model.put("industryMap", industryMap);
        model.put("bankInfo", bankInfoResponse);
        model.put("imageMap", imageMap);
        model.put("taxpayerTypeMap", taxpayerTypeMap);
        model.put("taxpayerTypeCodeMap", taxpayerTypeCodeMap);
        model.put("groupMemberMap", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
        return new ModelAndView("jsp/user/qualification/agent-enterprise-edit",model);
    }
    
    
    @RequestMapping("/editSupplier")
    public ModelAndView editSupplier(HttpServletRequest request){
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取联系人信息
         */
        QueryContactsInfoSingleResponse contactsInfoInfoResponse = getContactsInfo(userId);
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        /**
         * 获取图片信息
         */
        QueryCustFileExtResponse custFileResponse = getCustFileExt(userId);
        List<CmCustFileExtVo> custFileExtVoList = custFileResponse.getList();
        List<String> idpsIdList = new ArrayList<String>();
        for (CmCustFileExtVo cmCustFileExtVo : custFileExtVoList) {
			idpsIdList.add(cmCustFileExtVo.getAttrValue());
		}
        Map<String,String> imageMap = getImageUrl(custFileExtVoList);
        /**
         * 银行信息
         */
        QueryBankInfoSingleResponse bankInfoResponse = getBankInfo(userId);
        
        //获取纳税人类型
        Map<String,String> taxpayerTypeMap = getTaxpayerTypeMap();
        //获取纳税类型税码信息
        Map<String,String> taxpayerTypeCodeMap = getTaxpayerTypeCodeMap();
        //获取公司人数
        Map<String,String> groupMemberMap = getGroupMemberScaleMap();
        //获取公司性质
        Map<String,String> groupTypeMap = getGroupTypeMap();
        //获取所属部门
        Map<String,String> contactDeptMap = getContactDeptMap();
        //获取地区信息
        List<GnAreaVo> provinceList = getProvinceList();
        //获取行业信息
        Map<String,String> industryMap = getIndustry();
        //获取地址编码
        Map<String,String> addressMap = new HashMap<String,String>();
        addressMap.put("provinceCode", grouKeyInfoResponse.getProvinceCode());
        addressMap.put("cityCode", grouKeyInfoResponse.getCityCode());
        addressMap.put("countyCode", grouKeyInfoResponse.getCountyCode());
        addressMap.put("certAddr", grouKeyInfoResponse.getCertAddr());
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> cityList = areaQuerySV.getCityListByProviceCode(grouKeyInfoResponse.getProvinceCode());
        List<GnAreaVo> countyList = areaQuerySV.getCountyListByCityCode(grouKeyInfoResponse.getCityCode());
        
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        String provinceName = cacheSv.getAreaName(grouKeyInfoResponse.getProvinceCode());
        String cityCode = cacheSv.getAreaName(grouKeyInfoResponse.getCityCode());
        String county = cacheSv.getAreaName(grouKeyInfoResponse.getCountyCode());
        
        Map<String,Object> codeMap = new HashMap<String,Object>();
        codeMap.put("groupMemberScale", grouKeyInfoResponse.getGroupMemberScale());
        codeMap.put("groupType", grouKeyInfoResponse.getGroupType());
        codeMap.put("contactDept", contactsInfoInfoResponse.getContactDept());
        codeMap.put("taxpayerCode", grouKeyInfoResponse.getTaxpayerType());
        codeMap.put("taxpayerTypeCode", grouKeyInfoResponse.getTaxpayerTypeCode());
        codeMap.put("groupIndustry", grouKeyInfoResponse.getGroupIndustry());
        codeMap.put("productCat", grouKeyInfoResponse.getProductCat());
        codeMap.put("certIssueDate", grouKeyInfoResponse.getCertIssueDate().toString().substring(0, 10));
        codeMap.put("idpsIdList", idpsIdList);
        
        grouKeyInfoResponse.setProvinceCode(provinceName+" "+cityCode+" "+county+" "+grouKeyInfoResponse.getCertAddr());
        grouKeyInfoResponse.setGroupMemberScale(groupMemberMap.get(grouKeyInfoResponse.getGroupMemberScale()));
        grouKeyInfoResponse.setGroupType(groupTypeMap.get(grouKeyInfoResponse.getGroupType()));
        contactsInfoInfoResponse.setContactDept(contactDeptMap.get(contactsInfoInfoResponse.getContactDept()));
        grouKeyInfoResponse.setTaxpayerType(taxpayerTypeMap.get(grouKeyInfoResponse.getTaxpayerType()));
        grouKeyInfoResponse.setTaxpayerTypeCode(taxpayerTypeCodeMap.get(grouKeyInfoResponse.getTaxpayerTypeCode()));
        grouKeyInfoResponse.setGroupIndustry(industryMap.get(grouKeyInfoResponse.getGroupIndustry()));
        
        //获取商品信息
        Map<String,String> prodCatMap = getProdCatInfo();
        grouKeyInfoResponse.setProductCat(prodCatMap.get(grouKeyInfoResponse.getProductCat()));
        
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("contactsInfo", contactsInfoInfoResponse);
        model.put("codeMap", codeMap);
        model.put("addressMap", addressMap);
        model.put("groupKeyInfo", grouKeyInfoResponse);
        model.put("custFileResponse", custFileResponse);
        model.put("provinceList", provinceList);
        model.put("cityList", cityList);
        model.put("countyList", countyList);
        model.put("industryMap", industryMap);
        model.put("bankInfo", bankInfoResponse);
        model.put("imageMap", imageMap);
        model.put("taxpayerTypeMap", taxpayerTypeMap);
        model.put("taxpayerTypeCodeMap", taxpayerTypeCodeMap);
        model.put("groupMemberMap", groupMemberMap);
        model.put("groupTypeMap", groupTypeMap);
        model.put("contactDeptMap", contactDeptMap);
        model.put("prodCatMap", prodCatMap);
        return new ModelAndView("jsp/user/qualification/supplier-edit",model);
    }
    
   //更新企业关键信息
    @RequestMapping("/updateEnterpriseInfo")
    @ResponseBody
    public ResponseData<String> updateEnterpriseInfo(HttpServletRequest request,
            UpdateGroupKeyInfoRequest updateGroupKeyInfoRequest, CustFileListVo custFileListVo) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;

        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        
        // 企业信息
        updateGroupKeyInfoRequest.setTenantId(user.getTenantId());
        updateGroupKeyInfoRequest.setUserType(user.getUserType());
        updateGroupKeyInfoRequest.setUserId(user.getUserId());
        updateGroupKeyInfoRequest.setAuditState(SLPMallConstants.AuditState.UserState_ready);
        if (request.getParameter("establishTime") != null) {
            updateGroupKeyInfoRequest.setCertIssueDate(DateUtil.getTimestamp(request.getParameter("establishTime")));
        }
        // 附件信息
        for (CmCustFileExtVo cmCustFileExtVo : custFileListVo.getList()) {
        	if(cmCustFileExtVo.getAttrValue()==null){
        		cmCustFileExtVo.setAttrValue("");
        	}
            cmCustFileExtVo.setTenantId(user.getTenantId());
            cmCustFileExtVo.setUserId(user.getUserId());
        }
        
        //判断银行信息是否存在
        IUcBankInfoSV ucBankInfoSV=null;
        UpdateBankInfoRequest updateBankInfoRequest=null;
        if (request.getParameter("bankName") != null&& request.getParameter("bankAccount") != null) {
            updateBankInfoRequest = new UpdateBankInfoRequest();
            updateBankInfoRequest.setTenantId(user.getTenantId());
            updateBankInfoRequest.setUserId(user.getUserId());
            updateBankInfoRequest.setBankName(request.getParameter("bankName"));
            updateBankInfoRequest.setAcctNo(request.getParameter("bankAccount"));
            updateBankInfoRequest.setSubBranchName(request.getParameter("subbranchName"));
            ucBankInfoSV = DubboConsumerFactory.getService(IUcBankInfoSV.class);
        }
        UpdateCustFileExtRequest updateCustFileExtRequest = new UpdateCustFileExtRequest();
        updateCustFileExtRequest.setList(custFileListVo.getList());
        // 获取服务
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try {
            ucKeyInfoSV.updateGroupKeyInfo(updateGroupKeyInfoRequest);
            ucKeyInfoSV.updateCustFileExt(updateCustFileExtRequest);
            if(ucBankInfoSV!=null)
                ucBankInfoSV.updateBankInfo(updateBankInfoRequest);
            //更改用户账户状态
            updateUserState(user, SLPMallConstants.UserState.UserState_register);
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
            responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功");
        } catch (Exception e) {
            LOGGER.error("操作失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
    //更新联系人关键信息
    @RequestMapping("/updateContactsInfo")
    @ResponseBody
    public ResponseData<String> updateContactsInfo(HttpServletRequest request,
           UpdateContactsInfoRequest updateContactsInfoRequest) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;

        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (validatePhoneCode(request) != null) {
            return validatePhoneCode(request);
        }else{
        // 联系人信息
        updateContactsInfoRequest.setTenantId(user.getTenantId());
        updateContactsInfoRequest.setUserId(user.getUserId());
        //修改审核状态
        UpdateGroupKeyInfoRequest updateGroupKeyInfoRequest = new UpdateGroupKeyInfoRequest();
        updateGroupKeyInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        updateGroupKeyInfoRequest.setUserId(user.getUserId());
        //updateGroupKeyInfoRequest.setAuditState(SLPMallConstants.AuditState.UserState_ready);
        // 获取服务
        IUcContactsInfoSV ucContactsInfoSV = DubboConsumerFactory.getService(IUcContactsInfoSV.class);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try {
            ucContactsInfoSV.updateContactsInfo(updateContactsInfoRequest);
            //修改认证状态
            ucKeyInfoSV.updateGroupKeyInfo(updateGroupKeyInfoRequest);
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
            responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功");
        } catch (Exception e) {
            LOGGER.error("操作失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败");
        }
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    // 校验企业名称唯一性
    @RequestMapping("/checkCustName")
    @ResponseBody
    public ResponseData<String> checkCustName(String custName,HttpServletRequest request) {
        IUcKeyInfoSV ucKeyInfoSv = DubboConsumerFactory.getService("iUcKeyInfoSV");
        SearchGroupKeyInfoRequest keyInfoReqeust = new SearchGroupKeyInfoRequest();
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        keyInfoReqeust.setCustName(custName);
        keyInfoReqeust.setTenantId(SLPMallConstants.COM_TENANT_ID);
        keyInfoReqeust.setAuditState(SLPMallConstants.AuditState.UserState_paas);
        //keyInfoReqeust.setUserType(user.getUserType());
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        try {
            SearchGroupKeyInfoResponse keyInfoResponse = ucKeyInfoSv.searchGroupKeyInfo(keyInfoReqeust);
            String resultCode = keyInfoResponse.getResponseHeader().getResultCode();
            //凑合着看吧
            if(user.getUserId().equals(keyInfoResponse.getUserId())||ExceptionCode.NO_RESULT.equals(resultCode)){
            	header = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE,"成功");
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功",null);
                responseData.setResponseHeader(header);
            } else {
                header = new ResponseHeader(false,VerifyConstants.ResultCodeConstants.CUST_NAME_NOONE_ERROR, "企业名称已注册");
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "企业名称已注册",null);
                responseData.setResponseHeader(header);
            }
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "企业名称校验失败",null);
        }
        return responseData;
    }

    /**
     * 获取地区信息
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public List<GnAreaVo> getProvinceList() {
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> list = areaQuerySV.getProvinceList();
        return list;
    }
    /**
     * 获取行业信息
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    List<IndustryQueryResponse> getIndustryList() {
        IIndustrySV industrySV = DubboConsumerFactory.getService("iIndustrySV");
        List<IndustryQueryResponse> list = industrySV.queryIndustryList();
        return list;
    }
    /**
     * 获取个人信息
     * @param userId
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public SearchCustKeyInfoResponse getCustKeyBaseinfo(String userId) {
        SearchCustKeyInfoRequest custKeyInfRequest = new SearchCustKeyInfoRequest();
        custKeyInfRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        custKeyInfRequest.setUserId(userId);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService("iUcKeyInfoSV");
        SearchCustKeyInfoResponse response = ucKeyInfoSV.searchCustKeyInfo(custKeyInfRequest);
        return response;
    }
    /**
     * 获取企业信息
     * @param userId
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public SearchGroupKeyInfoResponse getGroupKeyBaseinfo(String userId) {
        SearchGroupKeyInfoRequest groupKeyInfRequest = new SearchGroupKeyInfoRequest();
        groupKeyInfRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        groupKeyInfRequest.setUserId(userId);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService("iUcKeyInfoSV");
        SearchGroupKeyInfoResponse response = ucKeyInfoSV.searchGroupKeyInfo(groupKeyInfRequest);
        return response;
    }
    /**
     * 获取图片信息
     * @param userId
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public QueryCustFileExtResponse getCustFileExt(String userId) {
        QueryCustFileExtRequest custFileExtInfoRequest = new QueryCustFileExtRequest();
        custFileExtInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        custFileExtInfoRequest.setUserId(userId);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService("iUcKeyInfoSV");
        QueryCustFileExtResponse response = ucKeyInfoSV.queryCustFileExt(custFileExtInfoRequest);
        return response;
    }
    /**
     * 获取联系人信息
     * @param userId
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public QueryContactsInfoSingleResponse getContactsInfo(String userId) {
        QueryContactsInfoSingleRequest contactsInfoRequest = new QueryContactsInfoSingleRequest();
        contactsInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        contactsInfoRequest.setUserId(userId);
        IUcContactsInfoSV ucContactsInfoSV = DubboConsumerFactory.getService("iUcContactsInfoSV");
        QueryContactsInfoSingleResponse response = ucContactsInfoSV
                .queryContactsInfoSingle(contactsInfoRequest);
        return response;
    }

    /**
     * 获取银行信息
     * @param userId
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public QueryBankInfoSingleResponse getBankInfo(String userId) {
        QueryBankInfoSingleRequest bankInfoRequest = new QueryBankInfoSingleRequest();
        bankInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        bankInfoRequest.setUserId(userId);
        IUcBankInfoSV ucBankInfoSV = DubboConsumerFactory.getService("iUcBankInfoSV");
        QueryBankInfoSingleResponse response = ucBankInfoSV.queryBankInfoSingle(bankInfoRequest);
        return response;
    }
    
    
    public Map<String,String> getImageUrl(List<CmCustFileExtVo> custFileExtVoList){
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        Map<String,String> map = new HashMap<String,String>();
        try {
            for(CmCustFileExtVo cmUcstFile:custFileExtVoList){
            	if(cmUcstFile.getAttrValue()!=""){
                String url = im.getImageUrl(cmUcstFile.getAttrValue(), ".jpg");
                map.put(cmUcstFile.getInfoItem(), url);
            	}else
            		map.put(cmUcstFile.getInfoItem(), "/slp-mall/resources/slpmall/images/fom-t.png");
            }
           
        } catch (Exception e) {
            LOGGER.error("获取图片错误",e);
        }
        return map;
    }
    /**
     *获取公司人数
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getGroupMemberScaleMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("groupMemberScale");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> groupMemberScaleSysParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> groupMemberScaleMap = new LinkedHashMap<String,String>();
        for(SysParam param:groupMemberScaleSysParam){
            groupMemberScaleMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return groupMemberScaleMap;
    }
    /**
     * 获取公司类型
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getGroupTypeMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("groupType");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> groupTypeParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> groupTypeMapMap = new LinkedHashMap<String,String>();
        for(SysParam param:groupTypeParam){
            groupTypeMapMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return groupTypeMapMap;
    }
    /**
     * 获取部门
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getContactDeptMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("contactDept");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> contactDeptParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> contactDeptMap = new LinkedHashMap<String,String>();
        for(SysParam param:contactDeptParam){
            contactDeptMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return contactDeptMap;
    }
    
    /**
     * 获取纳税人类型
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getTaxpayerTypeMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("taxpayerType");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> taxpayerTypeParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> taxpayerTypeMap = new LinkedHashMap<String,String>();
        for(SysParam param:taxpayerTypeParam){
            taxpayerTypeMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return taxpayerTypeMap;
    }
    
    /**
     * 获取纳税人编码
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getTaxpayerTypeCodeMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("taxpayerTypeCode");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> taxpayerCodeParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> taxpayerCodeMap = new LinkedHashMap<String,String>();
        for(SysParam param:taxpayerCodeParam){
            taxpayerCodeMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return taxpayerCodeMap;
    }
    
    /**
     * 获取学历信息
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getCustEducationMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("education");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> educationParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> educationParamMap = new LinkedHashMap<String,String>();
        for(SysParam param:educationParam){
            educationParamMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return educationParamMap;
    }
    
    /**
     * 获取收入信息
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getIncomeLevelMap(){
        SysParamMultiCond sysParam = new SysParamMultiCond();
        sysParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        sysParam.setTypeCode("USER");
        sysParam.setParamCode("incomeLevel");
        ICacheSV cacheSv = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> incomeLevelParam = cacheSv.getSysParamList(sysParam);
        Map<String,String> incomeLevelMap = new LinkedHashMap<String,String>();
        for(SysParam param:incomeLevelParam){
            incomeLevelMap.put(param.getColumnValue(), param.getColumnDesc());
        }
        return incomeLevelMap;
    }
    
    /**
     * 获取行业信息
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getIndustry() {
        IIndustrySV industrySV = DubboConsumerFactory.getService("iIndustrySV");
        List<IndustryQueryResponse> list = industrySV.queryIndustryList();
        Map<String,String> industryMap = new LinkedHashMap<String,String>();
        for(int i=0;i<list.size();i++){
            IndustryQueryResponse response = list.get(i);
            industryMap.put(response.getIndustryCode(), response.getIndustryName());
        }
        return industryMap;
    }
    
    /**
     * 获取商品信息
     * @return
     * @author zhangyh7
     * @ApiDocMethod
     */
    public Map<String,String> getProdCatInfo() {
        IProductCatSV productCatSV = DubboConsumerFactory.getService("iProductCatSV");
        ProductCatQuery catQuery = new ProductCatQuery();
        catQuery.setTenantId(SLPMallConstants.COM_TENANT_ID);
        List<ProdCatInfo> prodCatInfoList = productCatSV.queryCatByNameOrFirst(catQuery);
        Map<String,String> prodCatInfoMap = new HashMap<String,String>();
        for(ProdCatInfo catInfo : prodCatInfoList){
            prodCatInfoMap.put(catInfo.getProductCatId(), catInfo.getProductCatName());
        }
        return prodCatInfoMap;
    }
    
    public static void updateUserState(SLPClientUser user,String userState){
    	 IUcUserSV ucUserSV = DubboConsumerFactory.getService(IUcUserSV.class);
    	 UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
    	 updateUserInfoRequest.setTenantId(user.getTenantId());
    	 updateUserInfoRequest.setUserId(user.getUserId());
    	 updateUserInfoRequest.setUserState(userState);
    	 ucUserSV.updateBaseInfo(updateUserInfoRequest);
    }
    
}