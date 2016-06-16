package com.ai.slp.mall.web.controller.user;

import java.io.IOException;
import java.util.HashMap;
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
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.ai.slp.common.api.industry.interfaces.IIndustrySV;
import com.ai.slp.common.api.industry.param.IndustryQueryResponse;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.user.api.keyinfo.interfaces.IUcKeyInfoSV;
import com.ai.slp.user.api.keyinfo.param.InsertGroupKeyInfoRequest;
 
@RequestMapping("/user/qualification")
@Controller
public class QualificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandEmailController.class);

    //代理商选择页面
    @RequestMapping("/toAgentSelectPage")
    public ModelAndView toAgentSelectPage() {
        return new ModelAndView("jsp/user/qualification/agent-select");
    }
    //代理商个人页面
    @RequestMapping("/toAgentPersonalPage")
    public ModelAndView toAgentPersonalPage() {
        return new ModelAndView("jsp/user/qualification/agent-personal");
    }
    //代理商企业页面
    @RequestMapping("/toAgentEnterprisePage")
    public ModelAndView toAgentEnterprisePage() {
        return new ModelAndView("jsp/user/qualification/agent-enterprise");
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
    
    @RequestMapping("/uploadImg")
    @ResponseBody
    public ResponseData<String> uploadImg(MultipartFile file,HttpServletRequest request) {
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        String idpsns = "slp-mall-web-idps";
        // 获取imageClient
        IImageClient im = IDPSClientFactory.getImageClient(idpsns);
        //获取图片信息
        String imgName = request.getParameter("imgName");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile imgFile = multipartRequest.getFile("file");
        String url="";
        
        try {
            url = ImageUtil.AddImg(imgFile.getBytes(),imgName);
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
            responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        } catch (IOException e) {
            LOGGER.error("保存失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setData(url);
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    @RequestMapping("/saveEnterprise")
    @ResponseBody
    public ResponseData<String> saveEnterprise(HttpServletRequest request,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);

        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("更新失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
    @RequestMapping("/saveAgentEnterprise")
    @ResponseBody
    public ResponseData<String> saveAgentEnterprise(HttpServletRequest request ,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);

        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("更新失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
    @RequestMapping("/savePersonalEnterprise")
    @ResponseBody
    public ResponseData<String> savePersonalEnterprise(HttpServletRequest request ,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);

        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("更新失败");
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
}