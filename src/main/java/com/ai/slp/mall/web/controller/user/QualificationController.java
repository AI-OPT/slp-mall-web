package com.ai.slp.mall.web.controller.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.ai.slp.common.api.industry.interfaces.IIndustrySV;
import com.ai.slp.common.api.industry.param.IndustryQueryResponse;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.model.user.CustFileListVo;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.user.api.bankinfo.interfaces.IUcBankInfoSV;
import com.ai.slp.user.api.bankinfo.param.InsertBankInfoRequest;
import com.ai.slp.user.api.contactsinfo.interfaces.IUcContactsInfoSV;
import com.ai.slp.user.api.contactsinfo.param.InsertContactsInfoRequest;
import com.ai.slp.user.api.keyinfo.interfaces.IUcKeyInfoSV;
import com.ai.slp.user.api.keyinfo.param.CmCustFileExtVo;
import com.ai.slp.user.api.keyinfo.param.InsertCustFileExtRequest;
import com.ai.slp.user.api.keyinfo.param.InsertCustKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.InsertGroupKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.SearchCustKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.SearchCustKeyInfoResponse;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoResponse;
import com.alibaba.fastjson.JSON;
 
@RequestMapping("/user/qualification")
@Controller
public class QualificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QualificationController.class);

    //代理商选择页面
    @RequestMapping("/toAgentSelectPage")
    public ModelAndView toAgentSelectPage() {
        return new ModelAndView("jsp/user/qualification/agent-select");
    }
    //代理商个人页面
    @RequestMapping("/toAgentPersonalPage")
    public ModelAndView toAgentPersonalPage() {
        List<GnAreaVo> provinceList = getProvinceList();
        List<IndustryQueryResponse> industryList = getIndustryList();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("provinceList", provinceList);
        model.put("industryList", industryList);
        return new ModelAndView("jsp/user/qualification/agent-personal",model);
    }
    //供应商页面
    @RequestMapping("/toSupplierPage")
    public ModelAndView toSupplierPage() {
        List<GnAreaVo> provinceList = getProvinceList();
        List<IndustryQueryResponse> industryList = getIndustryList();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("provinceList", provinceList);
        model.put("industryList", industryList);
        return new ModelAndView("jsp/user/qualification/supplier",model);
    }
    
    //代理商企业页面
    @RequestMapping("/toAgentEnterprisePage")
    public ModelAndView toAgentEnterprisePage() {
        List<GnAreaVo> provinceList = getProvinceList();
        List<IndustryQueryResponse> industryList = getIndustryList();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("provinceList", provinceList);
        model.put("industryList", industryList);
        return new ModelAndView("jsp/user/qualification/agent-enterprise",model);
    }
    
    //企业页面
    @RequestMapping("/toEnterprisePage")
    public ModelAndView toEnterprisePage() {
        List<GnAreaVo> provinceList = getProvinceList();
        List<IndustryQueryResponse> industryList = getIndustryList();
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("provinceList", provinceList);
        model.put("industryList", industryList);
        return new ModelAndView("jsp/user/qualification/enterprise",model);
    }
    
    //保存企业申请信息
    @RequestMapping(value="/saveEnterprise")
    @ResponseBody
    public ResponseData<String> saveEnterprise(HttpServletRequest request
            ,InsertBankInfoRequest insertBankInfoRequest
            ,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest
            ,InsertContactsInfoRequest insertContactsInfoRequest
            ,CustFileListVo custFileListVo ,Model model) throws UnsupportedEncodingException{
        
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
       // establishTime
        //判断手机验证码
        if(validatePhoneCode(request)!=null){
            return validatePhoneCode(request);
        }else{
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        //企业关键信息
        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        //附件信息
        for (CmCustFileExtVo cmCustFileExtVo : custFileListVo.getList()) {
           cmCustFileExtVo.setTenantId(user.getTenantId());
           cmCustFileExtVo.setUserId(user.getUserId());
            }
        InsertCustFileExtRequest insertCustFileExtRequest = new InsertCustFileExtRequest();
        insertCustFileExtRequest.setList(custFileListVo.getList());
        //联系人信息
        insertContactsInfoRequest.setTenantId(user.getTenantId());
        insertContactsInfoRequest.setUserId(user.getUserId());
        
        IUcBankInfoSV ucBankInfoSV = DubboConsumerFactory.getService(IUcBankInfoSV.class);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        IUcContactsInfoSV contactsInfoSV = DubboConsumerFactory.getService(IUcContactsInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        ucKeyInfoSV.insertCustFileExt(insertCustFileExtRequest);
        ucBankInfoSV.insertBankInfo(insertBankInfoRequest);
        contactsInfoSV.insertContactsInfo(insertContactsInfoRequest);
        responseData = new ResponseData<String>(VerifyConstants.QualificationConstants.SUCCESS_CODE, "操作成功", null);
        responseHeader = new ResponseHeader(true,VerifyConstants.QualificationConstants.SUCCESS_CODE,"操作成功");
        }catch(Exception e){
            LOGGER.error("操作失败");
            responseData = new ResponseData<String>(VerifyConstants.QualificationConstants.ERROR_CODE, "操作失败", null);
            responseHeader = new ResponseHeader(false,VerifyConstants.QualificationConstants.ERROR_CODE,"操作失败");
        }
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
        }
    
    
    //校验手机验证码
    private ResponseData<String> validatePhoneCode(HttpServletRequest request){
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        String sessionId = request.getSession().getId();
        String userMp = request.getParameter("contactMp");
        String phoneCode = request.getParameter("phoneCode");
        SafetyConfirmData safetyConfirmData = new SafetyConfirmData();
        safetyConfirmData.setUserMp(userMp);
        safetyConfirmData.setVerifyCode(phoneCode);
        //验证短信验证码
        ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(sessionId, cacheClient, safetyConfirmData);
        String resultCode = phoneCheck.getResponseHeader().getResultCode();
        if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(resultCode))
            return phoneCheck;
            return null;
    }
    
    @RequestMapping("/savePersonalInfo")
    @ResponseBody
    public ResponseData<String> savePersonalInfo(HttpServletRequest request 
            ,InsertCustKeyInfoRequest insertCustKeyInfoRequest
            ,CustFileListVo custFileListVo){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        //个人信息
        insertCustKeyInfoRequest.setTenantId(user.getTenantId());
        insertCustKeyInfoRequest.setUserType(user.getUserType());
        insertCustKeyInfoRequest.setUserId(user.getUserId());
        insertCustKeyInfoRequest.setCustBirthday(DateUtil.getTimestamp(request.getParameter("yy_mm_dd")+"-"+request.getParameter("mm")+"-"+request.getParameter("dd")));
        //附件信息
        for (CmCustFileExtVo cmCustFileExtVo : custFileListVo.getList()) {
            cmCustFileExtVo.setTenantId(user.getTenantId());
            cmCustFileExtVo.setUserId(user.getUserId());
        }
        InsertCustFileExtRequest insertCustFileExtRequest = new InsertCustFileExtRequest();
        insertCustFileExtRequest.setList(custFileListVo.getList());
        //联系人信息
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertCustKeyInfo(insertCustKeyInfoRequest);
        ucKeyInfoSV.insertCustFileExt(insertCustFileExtRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("操作失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
     
    @RequestMapping("/getCityListByProviceCode")
    @ResponseBody
    public ResponseData<String> getCityListByProviceCode(HttpServletRequest request ,String provinceCode){
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> list = areaQuerySV.getCityListByProviceCode(provinceCode);
        String str = "";
        ResponseData<String> responseData = null;
        if(!CollectionUtil.isEmpty(list)){
            for(int i=0;i<list.size();i++){
                GnAreaVo gnAreaVo = list.get(i);
                str = str+"<option value="+gnAreaVo.getCityCode()+">"+gnAreaVo.getAreaName()+"</option>";
            }
            str="<option value='0'>请选择</option>"+str;
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS,"查询成功",str);
        }else{
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS,"没有查询结果",str);
        }
        
        return responseData;
    }
    
    @RequestMapping("/getStreetListByCountyCode")
    @ResponseBody
    public ResponseData<String> getStreetListByCountyCode(HttpServletRequest request ,String countyCode){
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> list = areaQuerySV.getCountyListByCityCode(countyCode);
        String str = "";
        ResponseData<String> responseData = null;
        if(!CollectionUtil.isEmpty(list)){
            for(int i=0;i<list.size();i++){
                GnAreaVo gnAreaVo = list.get(i);
                str = str+"<option value="+gnAreaVo.getCityCode()+">"+gnAreaVo.getAreaName()+"</option>";
            }
            str="<option value='0'>请选择</option>"+str;
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS,"查询成功",str);
        }else{
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS,"没有查询结果",str);
        }
        
        return responseData;
    }
    
    //获取图片
    @RequestMapping("/getImg")
    @ResponseBody
    public ResponseData<String> getImg(HttpServletRequest request ,String idpsId){
        ResponseData<String> responseData = null;
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
       try{
            String url = im.getImageUrl(idpsId, ".jpg");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS,"查询成功");
            responseData.setData(url);
       }catch(Exception e){
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS,"没有查询结果");
       }
        return responseData;
    }
    
    //上传图片
    @RequestMapping(value = "/uploadImg", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, Object> uploadImg(HttpServletRequest request) {
        
        Map<String,Object> map = new HashMap<String,Object>();
        String imageId = request.getParameter("imageId");
        MultipartHttpServletRequest file = (MultipartHttpServletRequest)request;
        MultipartFile image = file.getFile(imageId);
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        //获取图片信息
        try {
            String idpsId = im.upLoadImage(image.getBytes(), UUIDUtil.genId32()+".png");
            String url = im.getImageUrl(idpsId, ".jpg", "80x80");
            map.put("isTrue", true);
            map.put("idpsId", idpsId);
            map.put("url", url);
        } catch (IOException e) {
            LOGGER.error("保存失败");
            map.put("isTrue", false);
        }
        LOGGER.info("Map:---->>"+JSON.toJSONString(map));
        return map;
    }
    
    //删除服务器图片
    @RequestMapping(value = "/deleteImg")
    @ResponseBody
    public Map<String, Object> deleteImg(String idpsId, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        //获取图片信息
        try {
            im.deleteImage(idpsId);
            map.put("isTrue", true);
        } catch (Exception e) {
            LOGGER.error("保存失败");
            map.put("isTrue", false);
        }
        LOGGER.info("Map:---->>"+JSON.toJSONString(map));
        return map;
    }
    
    
    @RequestMapping("/updateEnterprise")
    public ModelAndView updateEnterprise(HttpServletRequest request){
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userId = userClient.getUserId();
        /**
         * 获取个人客户关键信息
         */
        SearchCustKeyInfoResponse custKeyInfoResponse = getCustKeyBaseinfo(userId);
        /**
         * 获取企业客户信息
         */
        SearchGroupKeyInfoResponse grouKeyInfoResponse = getGroupKeyBaseinfo(userId);
        
        Map<String,Object> model = new HashMap<String,Object>();
        model.put("custKeyInfo", custKeyInfoResponse);
        model.put("groupKeyInfo", grouKeyInfoResponse);
        return new ModelAndView("jsp/user/qualification/enterprise",model);
    }
    
    public List<GnAreaVo> getProvinceList(){
        IGnAreaQuerySV areaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
        List<GnAreaVo> list = areaQuerySV.getProvinceList();
        return list;
    }
    
    List<IndustryQueryResponse> getIndustryList(){
        IIndustrySV  industrySV = DubboConsumerFactory.getService("iIndustrySV");
        List<IndustryQueryResponse> list = industrySV.queryIndustryList();
        return list;
    }
    
    public SearchCustKeyInfoResponse getCustKeyBaseinfo(String userId){
        SearchCustKeyInfoRequest custKeyInfRequest = new SearchCustKeyInfoRequest();
        custKeyInfRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        custKeyInfRequest.setUserId(userId);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService("IUcKeyInfoSV");
        SearchCustKeyInfoResponse response = ucKeyInfoSV.searchCustKeyInfo(custKeyInfRequest);
        return response;
    }
    
    public SearchGroupKeyInfoResponse getGroupKeyBaseinfo(String userId){
        SearchGroupKeyInfoRequest groupKeyInfRequest = new SearchGroupKeyInfoRequest();
        groupKeyInfRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        groupKeyInfRequest.setUserId(userId);
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService("IUcKeyInfoSV");
        SearchGroupKeyInfoResponse response = ucKeyInfoSV.searchGroupKeyInfo(groupKeyInfRequest);
        return response;
    }
}