package com.ai.slp.mall.web.controller.head;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;

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
}
