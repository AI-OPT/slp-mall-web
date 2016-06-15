package com.ai.slp.mall.web.controller.head;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.IPUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.ai.slp.common.api.ipaddr.interfaces.IIpAddrSV;
import com.ai.slp.common.api.ipaddr.param.IpAddr;

import net.sf.json.JSONArray;

@RestController
@RequestMapping("/head")
public class HeadController {
    private static final Logger LOG = Logger.getLogger(HeadController.class);
    @RequestMapping("/fastCharge")
    public ModelAndView fastChrge(HttpServletRequest request,String flowFastFlag) {
        request.setAttribute("flowFastFlag", flowFastFlag);
        ModelAndView view = new ModelAndView("jsp/fastcharge/fastCharge");
        return view;
    }
    /**
     * 所在地查询
     * @param request
     * @return
     */
    @RequestMapping("/getArea")
    @ResponseBody
    public ResponseData<List<GnAreaVo>> getArea(HttpServletRequest request){
        ResponseData<List<GnAreaVo>> responseData = null;
        try {
            IGnAreaQuerySV iGnAreaQuerySV = DubboConsumerFactory.getService("iGnAreaQuerySV");
            List<GnAreaVo> resultInfo = iGnAreaQuerySV.getProvinceList();
            LOG.debug("地区查询出参:"+JSONArray.fromObject(resultInfo).toString());
            responseData = new ResponseData<List<GnAreaVo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultInfo);
        } catch (Exception e) {
            responseData = new ResponseData<List<GnAreaVo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
    /**
     * ip所在地查询
     * @param request
     * @return
     */
    @RequestMapping("/getIpAddr")
    @ResponseBody
    public ResponseData<IpAddr> getAreaByIp(HttpServletRequest request){
        ResponseData<IpAddr> responseData = null;
        try {
            IIpAddrSV iIpAddrSV = DubboConsumerFactory.getService("ipAddrSV");
            //获取IP地址
            String ip =  IPUtil.getRealClientIpAddr(request);
            //IpAddr addr = iIpAddrSV.getIpAddrByIp(ip);
            IpAddr addr = new IpAddr();
            responseData = new ResponseData<IpAddr>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", addr);
        } catch (Exception e) {
            responseData = new ResponseData<IpAddr>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取ip信息出错：", e);
        }
        return responseData;
    }
    /**
     * 设置session
     * @param request
     * @return
     */
    @RequestMapping("/setSessionData")
    public ResponseData<String> setSessionData(HttpServletRequest request,String code,String name){
        ResponseData<String> responseData = null;
        try {
            //从session获取地区
            request.getSession().setAttribute("currentCityCode",code);
            request.getSession().setAttribute("currentCityName",name);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", null);
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取ip信息出错：", e);
        }
        return responseData;
    }
    /**
     * 获取session
     * @param request
     * @return
     */
    @RequestMapping("/getSessionData")
    @ResponseBody
    public ResponseData<GnAreaVo> getSessionData(HttpServletRequest request){
        ResponseData<GnAreaVo> responseData = null;
        try {
            //从session获取地区
            Object codeObj = request.getSession().getAttribute("currentCityCode");
            Object nameObj = request.getSession().getAttribute("currentCityName");
            GnAreaVo area = new GnAreaVo();
            if(codeObj!=null && nameObj!=null){
                area.setAreaCode(codeObj.toString());
                area.setAreaName(nameObj.toString());
            }
           
           
            responseData = new ResponseData<GnAreaVo>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", area);
        } catch (Exception e) {
            responseData = new ResponseData<GnAreaVo>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取ip信息出错：", e);
        }
        return responseData;
    }
}
