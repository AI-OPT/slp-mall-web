package com.ai.slp.mall.web.test.ccs;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;

public class IConfigCenterClientTest {

    private IConfigClient client;

    @Before
    public void initData() {
        this.client = CCSClientFactory.getDefaultConfigClient();
    }
    
    @Test
    @Ignore
    public void addMcsConfig() throws ConfigException {
        // 缓存服务主机
        String baasopwebRedisHost = "MCS004";
        // 缓存空间
        String cachesnsConfig = "{\"com.ai.opt.uni.session.sessionclient.slpmallweb\":\"" + baasopwebRedisHost
                //+ "\",\"com.ai.baas.op.register.cache\":\"" + baasopwebRedisHost
                + "\"}";
        

        // 缓存空间配置
        if (!client.exists(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH))
            client.add(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH, cachesnsConfig);
        else {
            client.modify(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH, cachesnsConfig);
        }
    }
    
    @Ignore
    @Test
    public void readMcsConfig() throws ConfigException {
    	
    	String cachesns=client.get(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);

    	System.out.println("cachesns:"+cachesns);

    }
    
    @Test
    @Ignore
    public void addServiceIdPwdMap() throws ConfigException {
        String cachesnsConfig = "{\"MCS004\":\"" + "123456"
                + "\"}";

        // paas serviceid password 映射配置
        if (!client.exists(BandEmail.CACHE_KEY_IP_SEND_PHONE_NUM))
            client.add(BandEmail.CACHE_KEY_IP_SEND_PHONE_NUM,
                    cachesnsConfig);
        else {
            client.modify(BandEmail.CACHE_KEY_IP_SEND_PHONE_NUM,
                    cachesnsConfig);
        }
    }

}