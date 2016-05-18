package com.ai.slp.mall.web.test.ccs;

import static org.junit.Assert.assertEquals;

import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.constants.Constants.UploadFile;
import com.ai.slp.mall.web.constants.VerifyConstants.PhoneVerifyConstants;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.constants.SDKConstants;

public class IConfigCenterClientTest {

    private IConfigClient client;

    @Before
    public void initData() {
        this.client = CCSClientFactory.getDefaultConfigClient();
    }
    
    @Test
    //@Ignore
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
    
    //@Ignore
    @Test
    public void readMcsConfig() throws ConfigException {
    	
    	String cachesns=client.get(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);

    	System.out.println("cachesns:"+cachesns);

    }
    
    @Test
    public void addServiceIdPwdMap() throws ConfigException {
        String cachesnsConfig = "{\"MCS004\":\"" + "123456"
                + "\"}";

        // paas serviceid password 映射配置
        if (!client.exists(SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH))
            client.add(SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH,
                    cachesnsConfig);
        else {
            client.modify(SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH,
                    cachesnsConfig);
        }
    }

/*    @Ignore
    @Test
    public void testGetConfig() throws Exception {
        client.add("/test", "test");
        assertEquals("test", client.get("/test"));
        System.out.println("aaaaaa");
    }

   

    @Test
    //@Ignore
    public void addSendVerifyTimesConfig() throws ConfigException {
        System.out.println("addSendVerifyTimesConfig ... start");
        
        String PHONE_VERIFY_OVERTIME = "300";
        String PHONE_SEND_VERIFY_MAX_TIME = "60";
        
        if (!client.exists(VerifyConstants.PhoneVerifyConstants.SEND_VERIFY_MAX_TIME_KEY)) {
            client.add(PhoneVerifyConstants.SEND_VERIFY_MAX_TIME_KEY, PHONE_SEND_VERIFY_MAX_TIME);
        } else {
            client.modify(PhoneVerifyConstants.SEND_VERIFY_MAX_TIME_KEY, PHONE_SEND_VERIFY_MAX_TIME);
        }
        if (!client.exists(VerifyConstants.PhoneVerifyConstants.VERIFY_OVERTIME_KEY)) {
            client.add(PhoneVerifyConstants.VERIFY_OVERTIME_KEY, PHONE_VERIFY_OVERTIME);
        } else {
            client.modify(PhoneVerifyConstants.VERIFY_OVERTIME_KEY, PHONE_VERIFY_OVERTIME);
        }

        System.out.println("addSendVerifyTimesConfig ... end");
    }
    
    @Test
    public void addUploadFileConfig() throws ConfigException {
    	 System.out.println("addUploadFileConfig ... start");
    	 //String url = "http://127.0.0.1:8080/baas-file/upload/";
         String url = "http://10.1.235.245:14121/baas-file/upload/";
         if (!client.exists(UploadFile.SAVE_LOCATION_KEY)) {
             client.add(UploadFile.SAVE_LOCATION_KEY, url);
         } else {
             client.modify(UploadFile.SAVE_LOCATION_KEY, url);
         }

         System.out.println("addUploadFileConfig ... end");
    }
    
    @Test
    public void addBaaSPTUrlConfig() throws ConfigException {
   	 System.out.println("url config ... start");
   	 String indexUrl = "http://10.1.235.245:14101/baas-pt";
   	 if (!client.exists(BaaSOPConstants.URLConstant.BAAS_PT_INDEX_URL_KEY)) {
            client.add(BaaSOPConstants.URLConstant.BAAS_PT_INDEX_URL_KEY, indexUrl);
        } else {
            client.modify(BaaSOPConstants.URLConstant.BAAS_PT_INDEX_URL_KEY, indexUrl);
        }

   	 System.out.println("url config ... end");
    }

   */
}