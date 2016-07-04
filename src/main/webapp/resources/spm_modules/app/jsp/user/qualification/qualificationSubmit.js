define('app/jsp/user/qualification/qualificationSubmit', function (require, exports, module) {
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
    require("app/util/jsviews-ext");
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
      
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
    		"click [id='enterpriseToSave']":"_enterpriseSubmit"
        },
        init: function(){
        },
    	//重写父类
    	setup: function () {
    		QualificationSubmitPager.superclass.setup.call(this);
    		activeUserLeftMenu(QualificationSubmitPager.USER_LEFT_MNU_ID);
    	},
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
			var custNameFlag = $("#custNameFlag").val();
			var certAddrFlag = $("#certAddrFlag").val();
			var certNumFlag = $("#certNumFlag").val();
			var contactMpFlag = $("#contactMpFlag").val();
			var phoneCodeFlag = $("#phoneCodeFlag").val();
			var groupIndusteryFlag = $("#groupIndusteryFlag").val();
			var groupMemberScaleFlag = $("#groupMemberScaleFlag").val();
			var groupStypeFlag = $("#groupStypeFlag").val();
			var contactDeptFlag = $("#contactDeptFlag").val();
			
			if(custNameFlag!="0"&&certAddrFlag!="0"&&certNumFlag!="0"&&contactMpFlag!="0"&&phoneCodeFlag!="0"&&groupIndusteryFlag!="0"&&groupMemberScaleFlag!="0"&&groupStypeFlag!="0"&&contactDeptFlag!="0"){
				ajaxToSave();
			}
	}
    });
    
    module.exports = QualificationSubmitPager
});


//上传图片至服务器
function uploadImg(imageId,certPic,idpsId) {
	var image = document.getElementById(imageId).value;
	$("#imgErrShow").text("支持JPG/PNG/GIF格式，最大不超过3M");
	$("#imgErrShow").css("color","");
	if(image==""){
		$("#imgErrShow").text("图片不能为空");
		$("#imgErrShow").css("color","red");
		$("#imgErrShow").show();
		return false;
	}else if(!/\.(gif|jpg|png|GIF|JPG|PNG)$/.test(image)){
		$("#imgErrShow").text("格式不对");
		$("#imgErrShow").css("color","red"); 
		$("#imgErrShow").show();
		return false;
	}else if(document.getElementById(imageId).files[0].size>3*1024*1024){
		$("#imgErrShow").text("图片太大");
		$("#imgErrShow").css("color","red");
		$("#imgErrShow").show();
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
        		//$("#idpsId").val(data.idpsId);
        		document.getElementById(idpsId).value=data.idpsId;
        	 }
         },  
         error: function (data, status, e) {  
             alert(e);  
         }  
     });  
}

//删除服务器图片
function deleteImg(imageId,certPic,idpsId){
	var idpsIdValue = $("#idpsId").val();
	$("#imgErrShow").text("支持JPG/PNG/GIF格式，最大不超过3M");
	$("#imgErrShow").css("color","");
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
        success: function(data) {
        	if(data.responseHeader.resultCode=="000003"){
        	 	$("#newPhoneCodeErrMsg").show();
        		$('#newPhoneCodeErrMsgShow').text("短信验证码错误");
				$('#phoneCodeFlag').val("0");
				return false;
        	}else if(data.responseHeader.resultCode=="000004"){
        		$("#newPhoneCodeErrMsg").show();
        		$('#newPhoneCodeErrMsgShow').text("短信验证码已失效");
				$('#phoneCodeFlag').val("0");
				return false;
        	}else if(data.responseHeader.resultCode=="000007"){
        		$("#newPhoneCodeErrMsg").show();
        		$('#newPhoneCodeErrMsgShow').text("手机与发送短信手机不一致");
				$('#phoneCodeFlag').val("0");
				return false;
        	}else if(data.responseHeader.resultCode=="111111"){
        		alert("失败了");
        		return false;
        	}else if(data.responseHeader.resultCode=="000000"){
        		window.location.href=_base+"/user/qualification/toEnterprisePage";
        	}
            },
			error: function(error) {
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