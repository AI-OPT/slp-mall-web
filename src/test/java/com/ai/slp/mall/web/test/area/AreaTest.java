package com.ai.slp.mall.web.test.area;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.common.api.area.interfaces.IGnAreaQuerySV;
import com.ai.slp.common.api.area.param.GnAreaVo;
import com.alibaba.fastjson.JSON;

public class AreaTest {
	@Ignore
	@Test
	public void testGetProvinceList(){
		IGnAreaQuerySV sv=DubboConsumerFactory.getService(IGnAreaQuerySV.class);
		List<GnAreaVo> list=sv.getProvinceList();
		System.out.println("result="+JSON.toJSONString(list));
	}
	
}
