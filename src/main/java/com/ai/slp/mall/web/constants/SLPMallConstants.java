package com.ai.slp.mall.web.constants;

public final class SLPMallConstants {
    private SLPMallConstants() {
    }

    /**
     * 通用租户标识
     */
    public static final String COM_TENANT_ID = "SLP";
    /**
     * 购物车下单类型
     */
    public static final String SHOP_CART_ORDE_TYPE = "100000";
    /**
     * 错误信息标记
     */
    public static final String ERR_MSG_TAG = "errMsg";

    public static final String SYSTEM_ID = "opt-uac";
    
    public static final String PAGESIZE = "pageSize";

    public static final String PAGENO = "pageNo";

    
    /** 短信验证码失效ID */
    public static final String SSM_OVERTIME_ERROR = "000004";
    
    /** 发送短信手机号不一致错误 */
    public static final String SSM_DUMPHONE_ERROR = "000007";
    
    /** 短信验证码错误ID */
    public static final String SSM_ERROR = "000003";
    
    public static final class ExceptionCode {
        private ExceptionCode() {
        }

        public static final java.lang.String SUCCESS = "000000";// 成功

        public static final java.lang.String SYSTEM_ERROR = "999999";// 系统错误

        public static final java.lang.String PARAM_IS_NULL = "888888";// 参数为空

        public static final java.lang.String NO_RESULT = "000001";// 无结果

        public static final java.lang.String PARAM_TYPE_NOT_RIGHT = "000002";// 参数类型错误

        public static final java.lang.String NO_DATA_OR_CACAE_ERROR = "000003";// 无数据或缓存错误

        public static final java.lang.String PARAM_VALUE_NOT_RIGHT = "000004";// 参数取值错误

        public static final java.lang.String PARAM_VALUE_EXIST_ERROR = "000005";// 参数值重复错误

        public static final java.lang.String RESULT_IS_NULL = "000006";// 结果为空
    }

    public static final class SubjectCode {
        public static final String SUBJECT_INDUSTRY_CODE = "1";

        public static final String BILL_SUBJECT = "21";

        public static final String DR_SUBJECT = "2";
    }

    public static final class FeedbackState {

        public static final String INIT = "01";

        public static final String DONE = "02";

    }

    public static final class UpgState {

        // 进行中
        public static final String ING = "1";

        // 取消
        public static final String CANCEL = "2";
    }

    /**
     * 账单优惠管理属性名称
     */
    public static final class BDAttrName {
        private BDAttrName() {
        }

        /** 优惠科目编码 */
        public static final String BILL_SUBJECT_CODE = "A5";

        /** 参考科目编码 */
        public static final String REFER_SUBJECT_CODE = "A6";

        /** 优惠条件 */
        public static final String PREFERENTIAL_TERMS_CODE = "C6";

        /** 扣减金额 */
        public static final String DISCOUNT_AMOUNT = "discount_amount";

        /** 折扣比例 */
        public static final String DISCOUNT_PERCENT = "discount_percent";

        /** 满赠（满减）到达金额 */
        public static final String FULL_COST_AMOUNT = "full_cost_amount";

        /** 保底金额 */
        public static final String BD_AMOUNT = "bd_amount";

        /** 封顶金额 */
        public static final String FD_AMOUNT = "fd_amount";

        /** 生效方式 */
        public static final String ACTIVE_MODE = "active_mode";

        /** 赠送业务周期 */
        public static final String ACTIVE_PERIOD = "active_period";

        /** 赠品生效时间 */
        public static final String EFFECT_DATE = "effect_date";

        /** 折扣开始时间 */
        public static final String DISCOUNT_START_TIME = "discount_start_time";

        /** 折扣结束时间 */
        public static final String DISCOUNT_END_TIME = "discount_end_time";
    }

    /**
     * 账单优惠管理：优惠类型
     */
    public static final class DiscountType {
        private DiscountType() {
        }

        public static final String MZ = "mz"; // 满赠

        public static final String MJ = "mj"; // 满减

        public static final String DZ = "dz"; // 折扣

        public static final String BD = "bd"; // 保底

        public static final String FD = "fd"; // 封顶
    }

    /**
     * 账单优惠管理：计费类型
     */
    public static final class BillingType {
        private BillingType() {
        }

        public static final String STEP_GROUP_TYPE = "STEP_GROUP_TYPE"; // 阶梯组合套餐产品

        public static final String STANDARD_GROUP_TYPE = "STANDARD_GROUP_TYPE"; // 标准组合套餐产品
    }

    public static final class ProferName {
        private ProferName() {
        }

        /**
         * 赠送业务类型：赠送业务
         */
        public static final String SERVICE_OFFER = "service_offer";

        /**
         * 详单科目类型
         */
        public static final String DR_SUBJECT = "2";

    }

    public static final class URLConstant {
        private URLConstant() {
        }

        public static final String BAAS_PT_INDEX_URL_KEY = "/baas_pt_index_url";
        public static final String INDEX_URL_KEY = "/slp_mall_web_index_url";
    }

    public static final class ProductImageConstant {
        private ProductImageConstant() {
        }

        public static final String IDPSNS = "slp-mall-web-idps";
    }

    // 绑定邮箱
    public static final class BandEmail {
        private BandEmail() {
        }

        /** 手机验证方式 */
        public static final String CHECK_TYPE_PHONE = "1";

        /** 邮件验证方式 */
        public static final String CHECK_TYPE_EMAIL = "2";
        /**邮件主题*/
        public static final String EMAIL_SUBJECT="绑定邮箱地址";
       
        /**绑定邮箱邮箱模板路径*/
        public static final String TEMPLATE_BAND_EMAIL_URL = "email/template/user-bandemail-mail.xml";
        
        /**设置邮箱模板路径*/
        public static final String TEMPLATE_SETEMAIL_URL = "email/template/user-setemail-mail.xml";
        
        /**修改邮箱模板路径*/
        public static final String TEMPLATE_UPDATE_EMAIL_URL = "email/template/user-updateemail-mail.xml";
        
        /** 缓存命名空间*/
        public static final String CACHE_NAMESPACE = "com.ai.opt.uac.bandemail.cache";

        /** 邮箱验证码缓存key */
        public static final String CACHE_KEY_VERIFY_EMAIL = "band-email-verify-email";

        /** 设置新邮箱验证码缓存key */
        public static final String CACHE_KEY_VERIFY_SETEMAIL = "band-setemail-verify-email";

        /** 手机验证码缓存key */
        public static final String CACHE_KEY_VERIFY_PHONE = "band-email-verify-phone";

        /** 图片验证码缓存key */
        public static final String CACHE_KEY_VERIFY_PICTURE = "band-email-verify-picture";

        /** 身份认证发送邮箱次数key */
        public static final String CACHE_KEY_CONFIRM_SEND_EMAIL_NUM = "band-email-confirm-send-email-num";

        /** 身份认证发送手机次数key */
        public static final String CACHE_KEY_CONFIRM_SEND_PHONE_NUM = "band-email-confirm-send-phone-num";

        /** 修改邮箱发送手机次数key */
        public static final String CACHE_KEY_UPDATE_SEND_EMAIL_NUM = "band-email-update-send-phone-num";

        /** IP发送手机次数key */
        public static final String CACHE_KEY_IP_SEND_PHONE_NUM = "band-email-ip-send-phone-num";

        /** IP发送邮件次数key */
        public static final String CACHE_KEY_IP_SEND_EMAIL_NUM = "band-email-ip-send-email-num";
        
       /** 邮件未认证标志*/
        public static final String EMAIL_NOT_CERTIFIED = "11";
        /** 邮件已认证标志*/
        public static final String EMAIL_CERTIFIED = "10";
        /**
         * 手机号已经注册
         */
        public static final String PHONE_NOTONE_ERROR="10003";
        /** 邮箱已经注册ID */
        public static final String EMAIL_NOTONE_ERROR = "10004";
    }
    
    /**
     * 支付终端来源 Date: 2016年5月31日 <br>
     * Copyright (c) 2016 asiainfo.com <br>
     * 
     * @author zhangxw
     */
    public static final class RequestSource {
        private RequestSource() {
        }

        public static final String WEB = "1"; // web

        public static final String WAP = "2";// wap

        public static final String APP = "3";// APP

        public static final String WEIXIN = "4";// 微信
    }

    /**
     * 支付请求来源 Date: 2016年5月31日 <br>
     * Copyright (c) 2016 asiainfo.com <br>
     * 
     * @author zhangxw
     */
    public static final class PayChannel {
        private PayChannel() {
        }

        public static final String BSS_SK = "2"; // BSS网上营业厅收款（订单类）

        public static final String BSS_JF = "4";// BSS网上营业厅充值缴费（缴费类）

        public static final String XSDL = "5";// 线上代理

        public static final String APK = "6";// 爱倍科

        public static final String PAY_CHANNEL = "8";// 优酷虚商

    }

    /**
     * 支付请求来源 Date: 2016年6月1日 <br>
     * Copyright (c) 2016 asiainfo.com <br>
     * 
     * @author zhangxw
     */
    public static class PayState {
        public static final String PAY_SUCCESS = "00"; // BSS网上营业厅收款（订单类）

        public static final String PAY_FAILED = "01";// BSS网上营业厅充值缴费（缴费类）
    }

    /**
     * 订单支付支付方式
     * 
     * @Description
     * @author Administrator
     * @date 2015年6月1日 下午4:29:23
     * @version V1.3.1
     */
    public static class OrderPayType {
        /** 余额 */
        public static final String COUNTER_PAY = "1";

        /** 在线支付 */
        public static final String ONLINE_PAY = "2";

        /** 充值卡 */
        public static final String RECHARGE_CARD_PAY = "3";

        /** 赠送预存 */
        public static final String GIVE_DESPOIT_PAY = "4";

        /** 积分 */
        public static final String ACCUMULATE_PAY = "5";

        /** POS机 */
        public static final String POS_PAY = "6";

        /** 货到付款 */
        public static final String DELI_PAY = "7";

        /** 优惠券 */
        public static final String COUPONS_PAY = "8";
    }
    /**
     * 后场返回code
     * Date: 2016年6月1日 <br>
     * Copyright (c) 2016 asiainfo.com <br>
     * 
     * @author zhangxw
     */
    public static class DUBBO {
        //调用后场返回的成功code
        public static final String SUCCESS = "000000";

        //调用后场查询无记录code
        public static final String NO_DATA = "000001";
        
        //号码已经被预占
        public static final String NUMBER_RESERVE_OLREADY="105011";
        
    }
    
    public static final class UUID{
        private UUID(){}
        /*** 失效时间*/
        public static final int OVERTIME = 300;
        /*** 失效时间*/
        public static final String KEY_NAME = "k";
    }
    public static final class Order{
    	private Order(){}
    	//订单的命名空间
    	public static final String CACHE_NAMESPACE="com.ai.slp.mall.order.cache";
    	
    	public static final String VISITUSERID="900000000000000000";
    	
    	public static final String BATCH_FLAG="0";
    	
    	public static final String USERTYPE_VISITOR="10";
    	
    }
    public static final class PayPassword{
        private PayPassword(){}
        /**
         * 支付密码和登录密码相同
         */
        public static final String PASSWORD_EQUALS="10003";
        /**
         * 修改密码失败
         */
        public static final String UPDATE_PASSWORD_ERROR="000001";
        /**
         * 修改密码成功
         */
        public static final String UPDATE_PASSWORD_SUCCESS="000000";
        
    }
    
    public static final class Qualification{
        private Qualification(){}
        /**
         * 认证成功
         */
        public static final String QUALIFICATION_SUCCESS="00000";
        /**
         * 认证失败
         */
        public static final String QUALIFICATION_ERROR="00001";
        
    }
    
    public static final class ChangePhone{
        private ChangePhone(){}
        /**
         * 修改成功
         */
        public static final String ChangePhone_SUCCESS="11112";
        /**
         * 修改失败
         */
        public static final String ChangePhone_ERROR="11111";
        /**
         * 手机号已注册
         */
        public static final String PHONE_ERROR="10003";
        
        /**
         * 手机号未注册
         */
        public static final String PHONE_SUCCESS="success";
        
    }
        
        
        public static final class ValidatePassword{
            private ValidatePassword(){}
            /**
             * 校验成功
             */
            public static final String ChangePhone_SUCCESS="1111";
            /**
             * 校验失败
             */
            public static final String ChangePhone_ERROR="1112";
            
        
    }
        
        public static final class UploadImg{
            private UploadImg(){}
            /**
             * 上传成功
             */
            public static final String UploadImg_SUCCESS="success";
            /**
             * 上传失败
             */
            public static final String UploadImg_ERROR="fail";
        }
        
        public static final class UserState{
        	private UserState(){}
        	/**
        	 * 注册
        	 */
        	public static final String UserState_register="11";
        	/**
        	 * 正常
        	 */
        	public static final String UserState_Normal="10";
        	/**
        	 * 冻结
        	 */
        	public static final String UserState_Fail="12";
        }
        
        public static final class AuditState{
        	private AuditState(){}
        	/**
        	 * 待审核
        	 */
        	public static final String UserState_ready="10";
        	/**
        	 * 审核通过
        	 */
        	public static final String UserState_paas="11";
        	/**
        	 * 审核失败
        	 */
        	public static final String UserState_Fail="12";
        }
}
