package com.ai.slp.mall.web.test.servicenum;

import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.common.api.servicenum.interfaces.IServiceNumSV;
import com.ai.slp.common.api.servicenum.param.ServiceNum;
import com.alibaba.fastjson.JSON;

public class ServiceNumTest {
	@Test
	public void testServiceNum(){
		IServiceNumSV sv = DubboConsumerFactory.getService(IServiceNumSV.class);
		ServiceNum result = sv.getServiceNumByPhone("1381109");
		System.out.println("result="+JSON.toJSONString(result));
	}
}
