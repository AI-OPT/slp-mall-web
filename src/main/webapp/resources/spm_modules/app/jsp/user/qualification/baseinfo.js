define('app/jsp/user/qualification/baseinfo', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("treegrid/js/jquery.cookie");
    require("app/jsp/user/qualification/ajaxfileupload");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QualificationPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5,
    		USER_LEFT_MNU_ID: "left_mnu_qualification_identify"
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"blur [id='custName']":"_validateName",
    		"focus [id='custName']":"_showUserNameTip",
    		"blur [id='groupWebsite']":"_checkUrl",
    		"blur [id='certAddr']":"_checkCertAddr",
    		"blur [id='certNum']":"_checkCertNum",
    		"blur [id='contactMp']":"_checkPhone",
    		"blur [id='contactEmail']":"_checkEmailFormat",
    		"blur [id='sendPhoneCode']":"_sendVerify",
    		"change [id='princeCode']":"_princeCodeChange",
    		"change [id='cityCode']":"_cityCodeChange",
			"click [id='uploadImg1']":"_uploadImg1"
        },
        init: function(){
        },
    	//重写父类
    	setup: function () {
    		QualificationPager.superclass.setup.call(this);
    		activeUserLeftMenu(QualificationPager.USER_LEFT_MNU_ID);
    	},
    	
    	_uploadImg1:function(){
			if($("#image1").val()!=""){
				ajaxFileUpload("image1");
			}
		},
    	
    	_showUserNameTip:function(){
    		$("#custNameErrMsg").show();
    		$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
    	},
    	_validateName:function(){
			var name = $("#custName").val();
			var reg = /^[\u4e00-\u9fa5a-zA-Z0-9\-\_\(\)\（\）]{4,60}$/;
    		if(name==""){
    			$('#enterpriseErrMsgShow').text("请输入名称");
    			$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    		}else{
    			if(name.match(reg)){
    				var	param={
    						userLoginName:$("#custName").val()
        				   };
            		/*ajaxController.ajax({
        			        type: "post",
        			        processing: false,
        			        url: _base+"/reg/checkUserName",
        			        dataType: "json",
        			        data: param,
        			        message: "正在加载数据..",
        			        success: function (data) {
        			         if(data.responseHeader.resultCode=="10003"){
        			        	   $('#userNameImage').attr('src',_base+'/theme/slp/images/icon-a.png');
        			        		$('#userNameErrorMsgShow').text("用户名已注册");
        			        		$("#errorUserNameMsg").show();
        							$('#errorPhoneFlag').val("0");
        							return false;
        			        	}else if(data.responseHeader.resultCode=="000000"){
        			        		$('#userNameImage').attr('src',_base+'/theme/slp/images/icon-b.png');
        			        		$("#errorUserNameMsg").show();
        			        		$('#userNameErrorMsgShow').hide();
        			        		$('#errorUserNameFlag').val("1");
        							return true;
        			        	}
        			        	
        			        },
        			        error: function(XMLHttpRequest, textStatus, errorThrown) {
        						 alert(XMLHttpRequest.status);
        						 alert(XMLHttpRequest.readyState);
        						 alert(textStatus);
        						}
        			        
        			    }); */
    			}else{
    				$('#custNameErrMsg').show();
    				$('#custNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
    			}
    		}
		},
		_checkUrl:function(){
			 var regExp = "^((https|http|ftp|rtsp|mms)?://)"
			        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
			        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
			        + "|" // 允许IP和DOMAIN（域名）
			        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
			        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
			        + "[a-z]{2,6})" // first level domain- .com or .museum
			        + "(:[0-9]{1,4})?" // 端口- :80
			        + "((/?)|" // a slash isn't required if there is no file name
			        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
		    var urlValue = $("#groupWebsite").val();
	        if (urlValue.match(regExp)){
	        	$("#groupWebsitErrMsg").show();
	        	$("#groupWebsiteText").hide();
	        	$('#groupWebsiteImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
				return true;
	        }else{
	        	$("#groupWebsitErrMsg").show();
	        	$("#groupWebsiteText").show();
	        	$('#groupWebsiteImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	        	$("#groupWebsiteText").text("请输入正确的网址");
	        }
		},
		//街道地址校验
		_checkCertAddr:function(){
			var certAddr = $("#certAddr").val();
			if(certAddr==null||certAddr==""){
				$("#certAddrErrMsg").show();
				$("#certAddrImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
				$("#certAddrText").text("请输入正确街道地址");
			}else{
				if(certAddr.length>=5&&certAddr.length<=120){
					$("#certAddrErrMsg").show();
					$("#certAddrImage").attr("src",_base+'/resources/slpmall/images/icon-b.png');
					$("#certAddrText").hide();
				}else{
					$("#certAddrErrMsg").show();
					$("#certAddrText").show();
					$("#certAddrImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
					$("#certAddrText").text("5-120个字符");
				}
			}
		},
		//营业执照注册号校验
		_checkCertNum:function(){
			var certNum = $("#certNum").val();
			if(certAddr==null||certAddr==""){
				$("#certNumErrMsg").show();
				$("#certNumImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
				$("#certNumText").text("营业执照注册号");
				return false;
			}else{
				var reg = /^[\a-zA-Z0-9\-]{1,20}$/;
				if(certNum.match(reg)){
					$("#certNumErrMsg").show();
					$("#certNumImage").attr("src",_base+'/resources/slpmall/images/icon-b.png');
					$("#certNumText").hide();
				}else{
					$("#certNumErrMsg").show();
					$("#certNumText").show();
					$("#certNumImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
					$("#certAddrText").text("最多20个字符，允许使用英语字母（区分大小写）、数字及“-”");
				}
			}
		},
		_checkPhone: function(){
    		var phone = $('#contactMp').val();
    		if (phone==""){
    			$("#contactMpErrMsg").show();
    			$('#contactMpText').text("请输入手机号码");
    			$('#phoneImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
				return false;
			}else if( /^0?1[3|4|5|8][0-9]\d{8}$/.test(phone)){
				var	param={
    					userMp:$("#contactMp").val()
    				   };
        		ajaxController.ajax({
    			        type: "post",
    			        processing: false,
    			        url: _base+"/reg/checkPhone",
    			        dataType: "json",
    			        data: param,
    			        message: "正在加载数据..",
    			        success: function (data) {
    			         if(data.responseHeader.resultCode=="10003"){
    			        	    $("#errorPhoneMsg").show();
    			        	 	$("#phoneText").show();
    			        	 	$("#showPhoneMsg").show();
    			        	 	$("#phoneImage").show();
    			        		$('#phoneText').text("手机号码已注册");
    			        		$('#phoneImage').attr('src',_base+'/theme/slp/images/icon-a.png');
    							$('#errorPhoneFlag').val("0");
    							return false;
    			        	}else if(data.responseHeader.resultCode=="000000"){
    			        		$("#errorPhoneMsg").show();
    			        		$("#phoneText").hide();
    			        		$('#errorPhoneFlag').val("1");
    			        		$('#phoneImage').attr('src',_base+'/theme/slp/images/icon-b.png');
    			        	}
    			        	
    			        },
    			        error: function(XMLHttpRequest, textStatus, errorThrown) {
    						 alert(XMLHttpRequest.status);
    						 alert(XMLHttpRequest.readyState);
    						 alert(textStatus);
    						}
    			        
    			    }); 
			}else{
				$("#contactMpErrMsg").show();
    			$('#contactMpText').text("请输入正确的号码");
    			$('#phoneImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
				return false;
			}
    	},
    	//检查邮件格式
		_checkEmailFormat: function(){
			var email = jQuery.trim($("#contactEmail").val());
			if(email!=null&&email!=""){
				if(!/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/.test(email)){
					$("#emailMsgError").show();
					$("#contactEmailText").show();
					$("#emailMsgImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
					return false;
				}else{
					$("#emailMsgError").show();
					$("#contactEmailText").hide();
					$("#emailMsgImage").attr("src",_base+'/resources/slpmall/images/icon-b.png');
				}
			}else{
				$("#emailMsgError").hide();
				$("#emailMsgImage").hide();
				$("#contactEmailText").hide();
			}
		},
		_sendVerify:function(){
			var _this = this;
			$("#sendPhoneCode").attr("disabled", true);
			var	param={
					userMp:$("#contactMp").html()
				   };
			ajaxController.ajax({
				type : "POST",
				data : param,
				dataType: 'json',
				url :_base+"/user/verify/sendPhoneVerify",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var resultCode = data.responseHeader.resultCode;
					if(resultCode=="100000"){
						var url = data.data;
						window.location.href = _base+url;
					}else{
						if(resultCode=="000000"){
							var step = 59;
				            $('#sendPhoneCode').val('重新发送60');
				            $("#sendPhoneCode").attr("disabled", true);
				            var _res = setInterval(function(){
				                $("#sendPhoneCode").attr("disabled", true);//设置disabled属性
				                $('#sendPhoneCode').val(step+'s后重新发送');
				                step-=1;
				                if(step <= 0){
				                $("#sendPhoneCode").removeAttr("disabled"); //移除disabled属性
				                $('#sendPhoneCode').val('获取验证码');
				                clearInterval(_res);//清除setInterval
				                }
				            },1000);
						}else{
							$("#sendPhoneCode").removeAttr("disabled");
						}
						if(resultCode=="100002"){
							$("#phoneCodeErrMsg").show();
							$("#phoneCodeText").show();
							$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-a.png');
							$("#phoneCodeText").text(data.statusInfo);
			        	}else{
			        		$("#phoneCodeErrMsg").hide();
							$("#phoneCodeText").hide();
							$("#phoneCodeImage").attr("src",_base+'/resources/slpmall/images/icon-b.png');
			        	}
					}
				},
				failure : function(){
					$("#sendVerify").removeAttr("disabled"); //移除disabled属性
				},
				error : function(){
					alert("网络连接超时!");
				}
			});
		},
		_princeCodeChange:function(){
			var princeCodeVal = $("#princeCode").val();
			ajaxController.ajax({
				type : "POST",
				data : {
					provinceCode:princeCodeVal
				},
				dataType: 'json',
				url :_base+"/user/qualification/getCityListByProviceCode",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					$("#cityCode").html(data.data);
				}
			})
		},
		_cityCodeChange:function(){
			var cityCode = $("#cityCode").val();
			ajaxController.ajax({
				type : "POST",
				data : {
					countyCode:cityCode
				},
				dataType: 'json',
				url :_base+"/user/qualification/getStreetListByCountyCode",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					$("#countryCode").html(data.data);
				}
			})
		}
    });
    
    
    module.exports = QualificationPager
});

function ajaxFileUpload(imageId) {
	alert(imageId);
	 $.ajaxFileUpload({  
         url:_base+"/user/qualification/uploadImg",  
         secureuri:false,  
         fileElementId:imageId,//file标签的id  
         //dataType: 'json',//返回数据的类型  
         data:{imageId:imageId},//一同上传的数据  
         success: function (data) {  
        	 alert(data.statusInfo);
            document.getElementById("img").src=data.statusInfo;
         },  
         error: function (data, status, e) {  
             alert(e);  
         }  
     });  
}
