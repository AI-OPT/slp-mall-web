define('app/jsp/user/qualification/qualificationSubmit', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("treegrid/js/jquery.cookie");
    require("app/jsp/user/qualification/ajaxfileupload");
    require("app/util/jsviews-ext");
    require("opt-paging/aiopt.pagination");
      
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QualificationSubmitPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5,
    		USER_LEFT_MNU_ID: "left_mnu_qualification_identify"
    	},
    	//事件代理
    	events: {
    		
    		//保存企业信息
    		"click [id='enterpriseToSave']":"_enterpriseSubmit",
    		//保存代理商信息
    		"click [id='agentEnterpriseSubmit']":"_agentEnterpriseSubmit",
    		//保存代理商个人信息
    		"click [id='savePersonalQualification']":"_agentPersonalSubmit",
    		//保存供货商信息
			"click [id='toSaveSuppliser']":"_toSaveSuppliser",
			
    		//修改代理商企业资质信息
    		"click [id='agentEnterpriseUpdate']":"_agentEnterpriseUpdate",
    		//修改代理商企业联系人信息
    		"click [id='agentEnterpriseContactsInfo']":"_updateAgentEnterpriseContactsInfo",
    		//修改个人代理信息
    		"click [id='updateAgentPersonal']":"_updatePersonalQualification",
    		//修改企业联系人信息
    		"click [id='updateContactsEnterprise']":"_updateEnterpriseContactsInfo",
    		//更新企业信息
    		"click [id='updateEnterprise']":"_updateEnterprise",
    		
    		//修改供货商联系人信息
    		"click [id='supplierContactsInfo']":"_updateSupplierContactsInfo",
    		//修改供货商信息
			"click [id='updateSupplierInfo']":"_updateSupplierInfo",
			
			//修改资质显示
			"click [id='editEnterprise']":"_editEnterprise",
			//修改联系人显示	
			"click [id='editContactInfo']":"_editContactInfo"
        },
        init: function(){
        },
    	//重写父类
    	setup: function () {
    		QualificationSubmitPager.superclass.setup.call(this);
    		//左侧相对应标签选中
    		activeUserLeftMenu(QualificationSubmitPager.USER_LEFT_MNU_ID);
    		this._addr();
    	},
    	
    	_addr(){
    		$("#provinceCode").val(provinceCode);
    		$("#cityCode").val(cityCode);
    		$("#countyCode").val(countyCode);
    	},
    	
    	/**
    	 * 企业提交方法
    	 */
    	_enterpriseSubmit:function(){
			//校验企业名称
			baseInfoPager._validateName();
			//校验注册地址
			var provinceCode = $("#provinceCode").val();
			var cityCode = $("#cityCode").val();
			var countyCode = $("#countyCode").val();
			if(provinceCode=="0"||provinceCode==null||cityCode=="0"||cityCode==null||countyCode=="0"||countyCode==null){
				$("#registerAddrErrMsg").show();
			}else{
				$("#registerAddrErrMsg").hide();
			}
			//校验图片是否上传
			baseInfoPager._checkPic('idpsId','imgErrShow');
			
			//校验街道地址
			baseInfoPager._checkCertAddr();
			//校验注册号
			baseInfoPager._checkCertNum();
			//检查手机号
			baseInfoPager._checkPhone();
			//校验行业
			baseInfoPager._checkGroupIndustery();
			//校验公司人数
			baseInfoPager._checkGroupMember();
			//校验公司性质
			baseInfoPager._checkGroupType();
			//校验所属部门
			baseInfoPager._checkContactDept();
			//校验联系人姓名
			baseInfoPager._checkContactName();
			//手机验证码是否为空校验
			baseInfoPager._checkPhoneCode();
			var custNameFlag = $("#custNameFlag").val();
			var certAddrFlag = $("#certAddrFlag").val();
			var certNumFlag = $("#certNumFlag").val();
			var contactMpFlag = $("#contactMpFlag").val();
			var phoneCodeFlag = $("#phoneCodeFlag").val();
			var groupIndusteryFlag = $("#groupIndusteryFlag").val();
			var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
			var groupStypeFlag = $("#groupStypeFlag").val();
			var contactDeptFlag = $("#contactDeptFlag").val();
			var picFlag = $("#picFlag").val();
			var phoneCodeFlag = $("#phoneCodeFlag").val();
			var contactNameFlag = $("#contactNameFlag").val();
			if(contactNameFlag!='0'&&custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&contactMpFlag!="0"&&phoneCodeFlag!="0"&&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"&&contactDeptFlag!="0"&&phoneCodeFlag!="0"){
				ajaxToSave();
			}
	},
	/**
	 * 企业、代理商、供应商在提交前对联系人的必要信息校验
	 */
	_checkEnterpriseContactValue:function(){
		//校验所属部门
		baseInfoPager._checkContactDept();
		//校验手机
		baseInfoPager._checkPhone();
		//校验手机验证码
		baseInfoPager._checkPhoneCode();
		//校验联系人姓名
		baseInfoPager._checkContactName();
	},
	/**
	 * 代理商企业、供应商企业在提交前对必填信息校验
	 */
	_checkEnterpriseValue:function(){
		//校验名称
		baseInfoPager._validateName();
		//校验注册地址
		baseInfoPager._checkContactAddress();
		//校验街道地址
		baseInfoPager._checkCertAddr();
		//营业执照注册号
		baseInfoPager._checkCertNum();
		//校验行业
		baseInfoPager._checkGroupIndustery();
		//校验公司人数
		baseInfoPager._checkGroupMember();
		//校验公司性质
		baseInfoPager._checkGroupType();
		//校验短信验证码
		//baseInfoPager._sendVerify();
		//校验注册日期
		enterprisePager._checkEstablishTime();
		//校验注册资本
		enterprisePager._checkCapitalValue();
		//校验经营范围
		enterprisePager._checkScopeValue();
		//校验法人姓名
		enterprisePager._checkCorporationNameValue();
		//校验法人身份证号
		enterprisePager._checkIdNumber();
		//校验纳税人识别号
		enterprisePager._checkIdentifyNumberValue();
		//校验纳税人类型
		enterprisePager._checkTaxpayerTypeValue();
		//校验纳税类型税码
		enterprisePager._checkTaxCodeValue();
		//校验开户行名称
		enterprisePager._checkBankNameValue();
		//校验支行名称
		enterprisePager._checkSubbranchNameValue();
		//校验公司账号
		enterprisePager._checkBankAccountValue();
		enterprisePager._checkOrganizationCodeValue();
	},
	/**
	 * 代理商企业提交
	 */
	_agentEnterpriseSubmit:function(){
		//提交前对资质必要信息进行校验
		this._checkEnterpriseValue();
		//提交前对联系人信息进行校验
		this._checkEnterpriseContactValue();
		//校验图片是否上传
		for(var i=1;i<=5;i++){
		baseInfoPager._checkPic('idpsId'+i,'imgErrShow'+i);
		}
		/**
		 * 获取标志信息
		 */
		var custNameFlag = $("#custNameFlag").val();
		var certAddrFlag = $("#certAddrFlag").val();
		var certNumFlag = $("#certNumFlag").val();
		var establishTimeFlag = $("#establishTimeFlag").val();
		var capitalFlag = $("#capitalFlag").val();
		var scopeFlag = $("#scopeFlag").val();
		var corporationNameFlag = $("#corporationNameFlag").val();
		var idNumberFlag = $("#idNumberFlag").val();
		var identifyNumberFlag = $("#identifyNumberFlag").val();
		var taxpayerTypeFlag = $("#taxpayerTypeFlag").val();
		var taxCodeFlag = $("#taxCodeFlag").val();
		var bankNameFlag = $("#bankNameFlag").val();
		var subbranchNameFlag = $("#subbranchNameFlag").val();
		var bankAccountFlag = $("#bankAccountFlag").val();
		var groupIndusteryFlag = $("#groupIndusteryFlag").val();
		var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
		var groupStypeFlag = $("#groupStypeFlag").val();
		var contactDeptFlag = $("#contactDeptFlag").val();
		var contactMpFlag = $("#contactMpFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		var organizationCodeFlag = $("#organizationCodeFlag").val();
		var provinceCodeFlag = $("#provinceCodeFlag").val();
		var picFlag = $("#picFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		var contactNameFlag = $("#contactNameFlag").val();
		if(contactNameFlag!='0'&&picFlag!="0"&&custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&establishTimeFlag!="0"
		  &&capitalFlag!="0"&&scopeFlag!="0"&&corporationNameFlag!="0"&&idNumberFlag!="0"&&identifyNumberFlag!="0"
		  &&taxpayerTypeFlag!="0"&&taxCodeFlag!="0"&&bankNameFlag!="0"&&subbranchNameFlag!="0"&&bankAccountFlag!="0"
		  &&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"&&contactDeptFlag!="0"&&
		  contactMpFlag!="0"&&phoneCodeFlag!="0"&&organizationCodeFlag!="0"&&provinceCodeFlag!="0"&&phoneCodeFlag!="0"){
			toAgentEnterpriseSave();
		}
		
	},
	/**
	 * 代理商个人提交方法
	 */
	_agentPersonalSubmit:function(){
		//校验姓名
		agentPersonalPager._checkcustName();
		//校验学历
		//agentPersonalPager._checkcustEducation();
		//校验联系地址
		agentPersonalPager._checkContactAddress();
		//校验街道地址
		baseInfoPager._checkCertAddr();
		//校验收入
		//agentPersonalPager._checkInCome();
		//校验省份证
		agentPersonalPager._checkIdNumber();
		//校验生日信息
		//agentPersonalPager._checkBithday();
		agentPersonalPager._checkCustSex();
		//校验图片是否上传
		for(var i=1;i<=3;i++){
			baseInfoPager._checkPic('idpsId'+i,'imgErrShow'+i);
		}
		var picFlag = $("#picFlag").val();
		var custNameFlag = $("#realNameFlag").val();
		//var custEducationFlag = $("#custEducationFlag").val();
		var certAddrFlag =  $("#certAddrFlag").val();
		var provinceCodeFlag =  $("#provinceCodeFlag").val();
		//var bithdayFlag =  $("#bithdayFlag").val();
		//var inComeFlag =  $("#inComeFlag").val();
		var idNumberFlag =  $("#idNumberFlag").val();
		var custSexFlag = $("#custSexFlag").val();
		if(custSexFlag!="0"&&picFlag!="0"&&custNameFlag!="0"&&certAddrFlag!="0"&&provinceCodeFlag!="0"&&idNumberFlag!="0"){
			toAgentPersonalSave();
		}
	},
	
	
	/**
	 * 企业用户修改
	 */
	
	_updateEnterprise:function(){
		//校验企业名称
		baseInfoPager._validateName();
		//校验注册地址
		var provinceCode = $("#provinceCode").val();
		var cityCode = $("#cityCode").val();
		var countyCode = $("#countyCode").val();
		if(provinceCode=="0"||provinceCode==null||cityCode=="0"||cityCode==null||countyCode=="0"||countyCode==null){
			$("#registerAddrErrMsg").show();
		}else{
			$("#registerAddrErrMsg").hide();
		}
		//校验图片是否上传
		baseInfoPager._checkPic('idpsId','imgErrShow');
		//校验街道地址
		baseInfoPager._checkCertAddr();
		//校验注册号
		baseInfoPager._checkCertNum();
		//校验行业
		baseInfoPager._checkGroupIndustery();
		//校验公司人数
		baseInfoPager._checkGroupMember();
		//校验公司性质
		baseInfoPager._checkGroupType();
		var picFlag = $("#picFlag").val();
		var custNameFlag = $("#custNameFlag").val();
		var certAddrFlag = $("#certAddrFlag").val();
		var certNumFlag = $("#certNumFlag").val();
		var contactMpFlag = $("#contactMpFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		var groupIndusteryFlag = $("#groupIndusteryFlag").val();
		var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
		var groupStypeFlag = $("#groupStypeFlag").val();
		var contactDeptFlag = $("#contactDeptFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		if(picFlag!="0"&&custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"){
			updateEnterpriseInfo(_base+"/user/qualification/editEnterprise");
		}
	},
	
	
	/**
	 * 代理商资质信息修改
	 */
	_agentEnterpriseUpdate:function(){
		/**
		 * 校验资质信息
		 */
		this._checkEnterpriseValue();
		/**
		 * 获取必要信息的标准信息
		 */
		//校验图片是否上传
		for(var i=1;i<=5;i++){
		baseInfoPager._checkPic('idpsId'+i,'imgErrShow'+i);
		}
		var picFlag = $("#picFlag").val();
		var custNameFlag = $("#custNameFlag").val();
		var certAddrFlag = $("#certAddrFlag").val();
		var certNumFlag = $("#certNumFlag").val();
		var establishTimeFlag = $("#establishTimeFlag").val();
		var capitalFlag = $("#capitalFlag").val();
		var scopeFlag = $("#scopeFlag").val();
		var corporationNameFlag = $("#corporationNameFlag").val();
		var idNumberFlag = $("#idNumberFlag").val();
		var identifyNumberFlag = $("#identifyNumberFlag").val();
		var taxpayerTypeFlag = $("#taxpayerTypeFlag").val();
		var taxCodeFlag = $("#taxCodeFlag").val();
		var bankNameFlag = $("#bankNameFlag").val();
		var subbranchNameFlag = $("#subbranchNameFlag").val();
		var bankAccountFlag = $("#bankAccountFlag").val();
		var groupIndusteryFlag = $("#groupIndusteryFlag").val();
		var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
		var groupStypeFlag = $("#groupStypeFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		if(picFlag!="0"&&custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&establishTimeFlag!="0"
			  &&capitalFlag!="0"&&scopeFlag!="0"&&corporationNameFlag!="0"&&idNumberFlag!="0"&&identifyNumberFlag!="0"
			  &&taxpayerTypeFlag!="0"&&taxCodeFlag!="0"&&bankNameFlag!="0"&&subbranchNameFlag!="0"&&bankAccountFlag!="0"
			  &&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"){
				/**
				 * 代理商资质修改
				 */
			updateEnterpriseInfo(_base+"/user/qualification/editAgentEnterprise");
			}
	},
	
	/**
	 * 供货商信息修改
	 */
	_updateSupplierInfo:function(){
		//提交前对资质必要信息进行校验
		this._checkEnterpriseValue();
		enterprisePager._checkSupplyGoodsValue();
		//校验图片是否上传
		for(var i=1;i<=5;i++){
			baseInfoPager._checkPic('idpsId'+i,'imgErrShow'+i);
		}
		/**
		 * 获取标志信息
		 */
		var custNameFlag = $("#custNameFlag").val();
		var certAddrFlag = $("#certAddrFlag").val();
		var certNumFlag = $("#certNumFlag").val();
		var establishTimeFlag = $("#establishTimeFlag").val();
		var capitalFlag = $("#capitalFlag").val();
		var scopeFlag = $("#scopeFlag").val();
		var corporationNameFlag = $("#corporationNameFlag").val();
		var idNumberFlag = $("#idNumberFlag").val();
		var identifyNumberFlag = $("#identifyNumberFlag").val();
		var taxpayerTypeFlag = $("#taxpayerTypeFlag").val();
		var taxCodeFlag = $("#taxCodeFlag").val();
		var bankNameFlag = $("#bankNameFlag").val();
		var subbranchNameFlag = $("#subbranchNameFlag").val();
		var bankAccountFlag = $("#bankAccountFlag").val();
		var groupIndusteryFlag = $("#groupIndusteryFlag").val();
		var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
		var groupStypeFlag = $("#groupStypeFlag").val();
		
		var organizationCodeFlag = $("#organizationCodeFlag").val();
		var provinceCodeFlag = $("#provinceCodeFlag").val();
		var supplyGoodsFlag = $("#supplyGoodsFlag").val();
		var picFlag=$("#picFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		if(picFlag!="0"&&custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&establishTimeFlag!="0"
		  &&capitalFlag!="0"&&scopeFlag!="0"&&corporationNameFlag!="0"&&idNumberFlag!="0"&&identifyNumberFlag!="0"
		  &&taxpayerTypeFlag!="0"&&taxCodeFlag!="0"&&bankNameFlag!="0"&&subbranchNameFlag!="0"&&bankAccountFlag!="0"
		  &&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"&&organizationCodeFlag!="0"&&provinceCodeFlag!="0"&&supplyGoodsFlag!="0"){
			/**
			 * 供货商资质修改
			 */
			updateEnterpriseInfo(_base+"/user/qualification/editSupplier");
		}
			
	},
		
		/**
		 * 代理商个人更新
		 */
		_updatePersonalQualification:function(){
			//校验姓名
			agentPersonalPager._checkcustName();
			//校验学历
			//agentPersonalPager._checkcustEducation();
			//校验联系地址
			agentPersonalPager._checkContactAddress();
			//校验街道地址
			baseInfoPager._checkCertAddr();
			//校验收入
			//agentPersonalPager._checkInCome();
			//校验省份证
			agentPersonalPager._checkIdNumber();
			//校验生日信息
			//agentPersonalPager._checkBithday();
			agentPersonalPager._checkCustSex();
			//校验图片是否上传
			for(var i=1;i<=3;i++){
			 baseInfoPager._checkPic('idpsId'+i,'imgErrShow'+i);
			}
			var picFlag = $("#picFlag").val();
			var custNameFlag = $("#realNameFlag").val();
			//var custEducationFlag = $("#custEducationFlag").val();
			var certAddrFlag =  $("#certAddrFlag").val();
			var provinceCodeFlag =  $("#provinceCodeFlag").val();
			//var bithdayFlag =  $("#bithdayFlag").val();
			//var inComeFlag =  $("#inComeFlag").val();
			var idNumberFlag =  $("#idNumberFlag").val();
			var custSexFlag = $("#custSexFlag").val();
			if(custSexFlag!="0"&&picFlag!="0"&&custNameFlag!="0"&&certAddrFlag!="0"&&provinceCodeFlag!="0"&&idNumberFlag!="0"){
				updatePersonalQualification();
			}
	},
	/**
	 * 企业联系人修改
	 */
	_updateEnterpriseContactsInfo:function(){
		/**
		 * 联系人信息校验
		 */
		this._checkEnterpriseContactValue();
		var contactDeptFlag = $("#contactDeptFlag").val();
		var contactMpFlag = $("#contactMpFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		var contactNameFlag = $("#contactNameFlag").val();
		if(contactNameFlag!='0'&&contactDeptFlag!="0"&&contactMpFlag!="0"&&phoneCodeFlag!="0"){
			/**
			 * 联系人修改
			 */
			updateContactInfo(_base+"/user/qualification/editEnterprise");
		}
		
	},
	/**
	 * 企业代理联系人修改
	 */
	_updateAgentEnterpriseContactsInfo:function(){
		/**
		 * 联系人信息校验
		 */
		this._checkEnterpriseContactValue();
		var contactDeptFlag = $("#contactDeptFlag").val();
		var contactMpFlag = $("#contactMpFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		if(contactDeptFlag!="0"&&contactMpFlag!="0"&&phoneCodeFlag!="0"){
			/**
			 * 联系人修改
			 */
			updateContactInfo(_base+"/user/qualification/editAgentEnterprise");
		}
		
	},
	/**
	 * 供货商联系人修改
	 */
	_updateSupplierContactsInfo:function(){
		/**
		 * 联系人信息校验
		 */
		this._checkEnterpriseContactValue();
		var contactDeptFlag = $("#contactDeptFlag").val();
		var contactMpFlag = $("#contactMpFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		if(contactDeptFlag!="0"&&contactMpFlag!="0"&&phoneCodeFlag!="0"){
			/**
			 * 联系人修改
			 */
			updateContactInfo(_base+"/user/qualification/editSupplier");
		}
		
	},
	
	_toSaveSuppliser:function(){
		//提交前对资质必要信息进行校验
		this._checkEnterpriseValue();
		//提交前对联系人信息进行校验
		this._checkEnterpriseContactValue();
		enterprisePager._checkSupplyGoodsValue();
		//校验图片是否上传
		for(var i=1;i<=5;i++){
			baseInfoPager._checkPic('idpsId'+i,'imgErrShow'+i);
		}
		/**
		 * 获取标志信息
		 */
		var picFlag = $("#picFlag").val();
		var custNameFlag = $("#custNameFlag").val();
		var certAddrFlag = $("#certAddrFlag").val();
		var certNumFlag = $("#certNumFlag").val();
		var establishTimeFlag = $("#establishTimeFlag").val();
		var capitalFlag = $("#capitalFlag").val();
		var scopeFlag = $("#scopeFlag").val();
		var corporationNameFlag = $("#corporationNameFlag").val();
		var idNumberFlag = $("#idNumberFlag").val();
		var identifyNumberFlag = $("#identifyNumberFlag").val();
		var taxpayerTypeFlag = $("#taxpayerTypeFlag").val();
		var taxCodeFlag = $("#taxCodeFlag").val();
		var bankNameFlag = $("#bankNameFlag").val();
		var subbranchNameFlag = $("#subbranchNameFlag").val();
		var bankAccountFlag = $("#bankAccountFlag").val();
		var groupIndusteryFlag = $("#groupIndusteryFlag").val();
		var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
		var groupStypeFlag = $("#groupStypeFlag").val();
		var contactDeptFlag = $("#contactDeptFlag").val();
		var contactMpFlag = $("#contactMpFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		var organizationCodeFlag = $("#organizationCodeFlag").val();
		var provinceCodeFlag = $("#provinceCodeFlag").val();
		var supplyGoodsFlag = $("#supplyGoodsFlag").val();
		var phoneCodeFlag = $("#phoneCodeFlag").val();
		var contactNameFlag = $("#contactNameFlag").val();
		if(contactNameFlag!='0'&&custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&establishTimeFlag!="0"
		  &&capitalFlag!="0"&&scopeFlag!="0"&&corporationNameFlag!="0"&&idNumberFlag!="0"&&identifyNumberFlag!="0"
		  &&taxpayerTypeFlag!="0"&&taxCodeFlag!="0"&&bankNameFlag!="0"&&subbranchNameFlag!="0"&&bankAccountFlag!="0"
		  &&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"&&contactDeptFlag!="0"&&
		  contactMpFlag!="0"&&phoneCodeFlag!="0"&&organizationCodeFlag!="0"&&provinceCodeFlag!="0"&&supplyGoodsFlag!="0"&&phoneCodeFlag!="0"){
			toSaveSuppliser();
		}
		
	},
	
	_editEnterprise:function(){
		
		if(auditState=='11'||auditState=='12'){
			$("#qf-browse").hide();
			$("#qf-edit").show();
		}
		if(auditState=='10'){
			var dialog = Dialog({
				title : '提示',
				content : "正在审核中,不能修改",
				okValue : "确定",
				ok : function() {
					this.close;
				}
			});
			dialog.show();
		}
	},
	
	_dialogErr:function(){
		var dialog = Dialog({
			title : '提示',
			content : "操作失败",
			okValue : "确定",
			ok : function() {
				this.close;
			}
		});
		dialog.show();
	},
	
	_editContactInfo:function(){
		$("#ct-edit").show(10);
		$("#ct-browse").hide(10);
		/*if(auditState=='11'||auditState=='12'){
			$("#ct-edit").show(10);
			$("#ct-browse").hide(10);
		}
		if(auditState=='10'){
			var dialog = Dialog({
				title : '提示',
				content : "正在审核中,不能修改",
				okValue : "确定",
				ok : function() {
					this.close;
				}
			});
			dialog.show();
	}*/
	}
	
    });
    
    module.exports = QualificationSubmitPager
});


//上传图片至服务器
function uploadImg(imageId,certPic,idpsId,imgErrShowId) {
	var image = document.getElementById(imageId).value;
	document.getElementById(imgErrShowId).innerHTML="";
	if(image==""){
		document.getElementById(imgErrShowId).innerHTML="图片不能为空";
		document.getElementById(imgErrShowId).style.color="red";
		document.getElementById(imgErrShowId).style.display="block";
		$("#picFlag").val("0");
		return false;
	}else if(!/\.(gif|jpg|png|GIF|JPG|PNG)$/.test(image)){
		document.getElementById(imgErrShowId).innerHTML="格式不对";
		document.getElementById(imgErrShowId).style.color="red";
		document.getElementById(imgErrShowId).style.display="block";
		$("#picFlag").val("0");
		return false;
		//这个图片
	}else if(document.getElementById(imageId).files[0].size>=(3.05*1024*1024)-1){
		document.getElementById(imgErrShowId).innerHTML="图片太大";
		document.getElementById(imgErrShowId).style.color="red";
		document.getElementById(imgErrShowId).style.display="block";
		$("#picFlag").val("0");
		return false;
	}
	 $.ajaxFileUpload({  
         url:_base+"/user/qualification/uploadImg",  
         secureuri:false,  
         fileElementId:imageId,//file标签的id  
         dataType: "json",//返回数据的类型  
         data:{imageId:imageId},//一同上传的数据  
         success: function (data, status) {
        	if(data.isTrue==true){
        		document.getElementById(certPic).src=data.url;
        		document.getElementById(idpsId).value=data.idpsId;
        		$("#picFlag").val("1");
        	 }
         },
         error: function (data, status, e) {  
             alert(e);  
         }
     });  
}

//删除服务器图片
function deleteImg(imageId,certPic,idpsId,imgErrShowId){
	var idpsIdValue = $("#idpsId").val();
	document.getElementById(imgErrShowId).innerHTML="";
	if(idpsIdValue!=""){
	$.ajax({
        type: "post",
        processing: false,
        url: _base+"/user/qualification/deleteImg",
        dataType: "json",
        data: {"idpsId":idpsId},
        message: "正在加载数据..",
        success: function (data) {
        	if(data.isTrue==true){
        		var url = getRealPath();
        		$("#picFlag").val("0");
        		document.getElementById(certPic).src=url+'/resources/slpmall/images/fom-t.png';
        		document.getElementById(idpsId).value="";
        	}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
			}
		    }); 
	}
}
	
	function ajaxToSave(){
	  $.ajax({
		type:"post",
		url:_base+"/user/qualification/saveEnterprise",
		dataType: "json",
		data:$("#qualificationEnterprise").serialize(),
		beforeSend: function(){
			document.getElementById("enterpriseToSave").disabled=true;
		},
        success: function(data) {
        	if(data.responseHeader.resultCode=="000003"){
        		$('#phoneCodeText').text("短信验证码错误");
        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
        		$("#phoneCodeText").show();
        		$('#phoneCodeErrMsg').show();
				$('#phoneCodeFlag').val("0");
				document.getElementById("enterpriseToSave").disabled=false;
				return false;
        	}else if(data.responseHeader.resultCode=="000004"){
        		$('#phoneCodeText').text("短信验证码已失效");
        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
        		$("#phoneCodeText").show();
        		$('#phoneCodeErrMsg').show();
				$('#phoneCodeFlag').val("0");
				document.getElementById("enterpriseToSave").disabled=false;
				return false;
        	}else if(data.responseHeader.resultCode=="000007"){
        		$('#phoneCodeText').text("手机与发送短信手机不一致");
        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
        		$("#phoneCodeText").show();
        		$('#phoneCodeErrMsg').show();
				$('#phoneCodeFlag').val("0");
				document.getElementById("enterpriseToSave").disabled=false;
				return false;
        	}else if(data.responseHeader.resultCode=="111111"){
        		qualificationSubmitPager._dialogErr();
        		document.getElementById("enterpriseToSave").disabled=false;
        		return false;
        	}else if(data.responseHeader.resultCode=="000000"){
        		window.location.href=_base+"/user/qualification/editEnterprise";
        	}
            },
			error: function(error) {
				document.getElementById("enterpriseToSave").disabled=false;
				alert("error:"+ error);
			}
		});
	}
	
	//获取当前项目根路径
	function getRealPath(){
		  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
		   var curWwwPath=window.document.location.href;
		   //获取主机地址之后的目录，如： myproj/view/my.jsp
		  var pathName=window.document.location.pathname;
		  var pos=curWwwPath.indexOf(pathName);
		  //获取主机地址，如： http://localhost:8083
		  var localhostPaht=curWwwPath.substring(0,pos);
		  //获取带"/"的项目名，如：/myproj
		  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		 
		 //得到了 http://localhost:8083/myproj
		  var realPath=localhostPaht+projectName;
		  return realPath;
		}
	/**
	 * 代理商企业保存方法
	 */
	function toAgentEnterpriseSave(){
		 $.ajax({
			type:"post",
			url:_base+"/user/qualification/saveEnterprise",
			dataType: "json",
			data:$("#agentEnterprise").serialize(),
			beforeSend: function(){
				document.getElementById("agentEnterpriseSubmit").disabled=true;
			},
	        success: function(data) {
	        	if(data.responseHeader.resultCode=="000003"){
	        		$('#phoneCodeText').text("短信验证码错误");
	        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
	        		$("#phoneCodeText").show();
	        		$('#phoneCodeErrMsg').show();
					$('#phoneCodeFlag').val("0");
					document.getElementById("agentEnterpriseSubmit").disabled=false;
					return false;
	        	}else if(data.responseHeader.resultCode=="000004"){
	        		$('#phoneCodeText').text("短信验证码已失效");
	        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
	        		$("#phoneCodeText").show();
	        		$('#phoneCodeErrMsg').show();
					$('#phoneCodeFlag').val("0");
					document.getElementById("agentEnterpriseSubmit").disabled=false;
					return false;
	        	}else if(data.responseHeader.resultCode=="000007"){
	        		$('#phoneCodeText').text("手机与发送短信手机不一致");
	        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
	        		$("#phoneCodeText").show();
	        		$('#phoneCodeErrMsg').show();
					$('#phoneCodeFlag').val("0");
					document.getElementById("agentEnterpriseSubmit").disabled=false;
					return false;
	        	}else if(data.responseHeader.resultCode=="111111"){
	        		qualificationSubmitPager._dialogErr();
	        		document.getElementById("agentEnterpriseSubmit").disabled=false;
	        		return false;
	        	}else if(data.responseHeader.resultCode=="000000"){
	        		window.location.href=_base+"/user/qualification/editAgentEnterprise";
	        	}
	            },
				error: function(error) {
					document.getElementById("agentEnterpriseSubmit").disabled=false;
					alert("error:"+ error);
				}
		});
	}
	/**
	 * 代理商个人保存方法
	 */
	function toAgentPersonalSave(){
		 $.ajax({
				type:"post",
				url:_base+"/user/qualification/savePersonalInfo",
				dataType: "json",
				data:$("#agentPersonal").serialize(),
				beforeSend: function(){
					document.getElementById("savePersonalQualification").disabled=true;
				},
		        success: function(data) {
		        	if(data.responseHeader.resultCode=="00001"){
		        		document.getElementById("savePersonalQualification").disabled=false;
		        		qualificationSubmitPager._dialogErr();
		        	}
		        	if(data.responseHeader.resultCode=="00000"){
		        		window.location.href=_base+"/user/qualification/editAgentPersonal";
		        	}
		            },
					error: function(error) {
						document.getElementById("savePersonalQualification").disabled=false;
						alert("error:"+ error);
					}
				});
	}
	
	/**
	 * 更新个人代理信息方法
	 */
	function updatePersonalQualification(){
		$.ajax({
			type:"post",
			url:_base+"/user/qualification/updatePersonalInfo",
			dataType: "json",
			data:$("#agentPersonal").serialize(),
			success: function(data) {
				if(data.responseHeader.resultCode=="00001"){
					qualificationSubmitPager._dialogErr();
				}
				if(data.responseHeader.resultCode=="00000"){
					window.location.href=_base+"/user/qualification/editAgentPersonal";
				}
			},
			error: function(error) {
				alert("error:"+ error);
			}
		});
	}
	
	//更新企业信息方法
	//根据url跳转成功页面
	function updateEnterpriseInfo(url){
		$.ajax({
	        type: "post",
	        processing: false,
	        url: _base+"/user/qualification/updateEnterpriseInfo",
	        dataType: "json",
	        data: $("#enterprise").serialize(),
	        message: "正在加载数据..",
	        success: function (data) {
	        	if(data.responseHeader.resultCode=='00000'){
	        		window.location.href=url;
	        	}
	        	if(data.responseHeader.resultCode=="00001"){
					qualificationSubmitPager._dialogErr();
				}
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
				}
			    }); 
	}
	
	
	function updateContactInfo(url){
		$.ajax({
	        type: "post",
	        async:false,
	        processing: false,
	        url: _base+"/user/qualification/updateContactsInfo",
	        dataType: "json",
	        data: $("#enterprise").serialize(),
	        message: "正在加载数据..",
	        success: function (data) {
	        	if(data.responseHeader.resultCode=="000003"){
	        		$('#phoneCodeText').text("短信验证码错误");
	        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
	        		$('#phoneCodeText').show();
	        		$('#phoneCodeErrMsg').show();
					$('#phoneCodeFlag').val("0");
					return false;
	        	}else if(data.responseHeader.resultCode=="000004"){
	        		$('#phoneCodeText').text("短信验证码已失效");
	        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
	        		$('#phoneCodeText').show();
	        		$('#phoneCodeErrMsg').show();
					$('#phoneCodeFlag').val("0");
					return false;
	        	}else if(data.responseHeader.resultCode=="000007"){
	        		$('#phoneCodeText').text("手机与发送短信手机不一致");
	        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
	        		$('#phoneCodeText').show();
	         		$('#phoneCodeErrMsg').show();
					$('#phoneCodeFlag').val("0");
					return false;
	        	}else if(data.responseHeader.resultCode=="00001"){
	        		qualificationSubmitPager._dialogErr();
	        		return false;
	        	}else if(data.responseHeader.resultCode=="00000"){
	        		window.location.href=url;
	        	}
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
				}
			    }); 
	}
	
	function toSaveSuppliser(){
	$.ajax({
        type: "post",
        processing: false,
        url: _base+"/user/qualification/saveEnterprise",
        dataType: "json",
        data: $("#enterprise").serialize(),
        message: "正在加载数据..",
		beforeSend: function(){
			document.getElementById("toSaveSuppliser").disabled=true;
		},
        success: function (data) {
        	if(data.responseHeader.resultCode=="000003"){
        		$('#newPhoneCodeErrMsgShow').text("短信验证码错误");
        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
        		$('#phoneCodeText').show();
        	 	$("#newPhoneCodeErrMsg").show();
				$('#phoneCodeFlag').val("0");
				document.getElementById("toSaveSuppliser").disabled=false;
				return false;
        	}else if(data.responseHeader.resultCode=="000004"){
        		$('#newPhoneCodeErrMsgShow').text("短信验证码已失效");
        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
        		$('#phoneCodeText').show();
        		$("#newPhoneCodeErrMsg").show();
				$('#phoneCodeFlag').val("0");
				document.getElementById("toSaveSuppliser").disabled=false;
				return false;
        	}else if(data.responseHeader.resultCode=="000007"){
        		$('#newPhoneCodeErrMsgShow').text("手机与发送短信手机不一致");
        		$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
        		$('#phoneCodeText').show();
        		$("#newPhoneCodeErrMsg").show();
				$('#phoneCodeFlag').val("0");
				document.getElementById("toSaveSuppliser").disabled=false;
				return false;
        	}else if(data.responseHeader.resultCode=="111111"){
        		document.getElementById("toSaveSuppliser").disabled=false;
					qualificationSubmitPager._dialogErr();
        		return false;
        	}else if(data.responseHeader.resultCode=="000000"){
        		window.location.href=_base+"/user/qualification/editSupplier";
        	}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        	 document.getElementById("toSaveSuppliser").disabled=false;
			 alert(XMLHttpRequest.status);
			 alert(XMLHttpRequest.readyState);
			 alert(textStatus);
			}
		    }); 
	}
	