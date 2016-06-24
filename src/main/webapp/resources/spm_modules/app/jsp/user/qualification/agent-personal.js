define(
		'app/jsp/user/qualification/agent-personal',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), 
			Widget = require('arale-widget/1.2.0/widget'), 
			AjaxController = require('opt-ajax/1.0.0/index');
			require('app/jsp/user/qualification/birthday')
			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var QualificationPager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				"blur [id='realName']":"_checkRealName",
	    		"focus [id='realName']":"_showRealNameTip",
	    		"focus [id='idNumber']":"_showIdNumberTip",
	    		"blur [id='idNumber']":"_checkIdNumber",
	    		"change [id='education']":"_checkEducation",
	    		"blur [id='countryCode']":"_checkContactAddress",
	    		"blur [id='dd']":"_checkBithday",
	    		"change [id='inCome']":"_checkInCome",
	    		"click [id='savePersonalQualification']":"_submit",
				},
				init : function() {
					//_hideErroText();
				},
				// 重写父类
				setup : function() {
					QualificationPager.superclass.setup.call(this);
					birth.init('yy_mm_dd');
				},
				_showRealNameTip:function(){
					$("#realNameErrMsg").show();
					$("#realNameText").show();
					$("#realNameText").text('4-24个字符，可用汉字或英语字母');
		    		$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_showIdNumberTip:function(){
					$("#idNumberErrMsg").show();
					$("#idNumberText").show();
					$("#idNumberImage").show();
					$("#idNumberText").text('18位数字');
		    		$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_checkRealName:function(){
					var name = $("#realName").val();
					var reg = /^[\u4e00-\u9fa5a-zA-Z]{4,24}$/;
					if(name==null||name==""){
						$('#realNameErrMsg').show();
	    				$("#realNameImage").show();
	        			$('#realNameText').text("4-24个字符，可用汉字或英语字母");
	        			$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	        			$("#realNameFlag").val("0");
					}else{
						if(name.match(reg)){
		    				$('#realNameErrMsg').show();
		    				$('#realNameText').hide();
		    				$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
		    				$("#realNameFlag").val("1");
		    			}else{
		    				$('#realNameErrMsg').show();
		    				$("#realNameImage").show();
		        			$('#realNameText').text("4-24个字符，可用汉字或英语字母");
		        			$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
		        			$("#realNameFlag").val("0");
		    			}
					}
				},
				_checkEducation:function(){
					var educationVal = $("#education").val();
					if(educationVal=="0"){
						$("#educationErrMsg").show();
						$("#educationFlag").val("0");
					}else{
						$("#educationErrMsg").hide();
						$("#educationFlag").val("1");
					}
				},
				_checkContactAddress:function(){
					//校验联系地址
					var princeCode = $("#provinceCode").val();
					var cityCode = $("#cityCode").val();
					var countryCode = $("#countryCode").val();
					if(princeCode=="0"||princeCode==null||cityCode=="0"||cityCode==null||countryCode=="0"||countryCode==null){
						$("#registerAddrErrMsg").show();
						$("#provinceCodeFlag").val("0");
					}else{
						$("#registerAddrErrMsg").hide();
						$("#provinceCodeFlag").val("1");
					}
				},
				_checkInCome:function(){
					var income = $("#inCome").val();
					if(income=="0"){
						$("#idComeErrMsg").show();
						$("#inComeFlag").val("0");
					}else{
						$("#idComeErrMsg").hide();
						$("#inComeFlag").val("1");
					}
				},
				_checkIdNumber:function(){
					var idNumber = $("#idNumber").val();
					if(idNumber==null|idNumber==""){
						$('#idNumberErrMsg').show();
	    				$("#idNumberImage").show();
	        			$('#idNumberText').text("请填写正确的身份证号");
	        			$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	        			$("#idNumberFlag").val("0");
					}else{
						var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
						if(!reg.test(idNumber)){
							$('#idNumberErrMsg').show();
		    				$("#idNumberImage").show();
		    				$("#realNameText").show();
		        			$('#idNumberText').text("18位数字");
		        			$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
		        			$("#idNumberFlag").val("0");
						}else{
							$('#idNumberErrMsg').show();
		    				$('#idNumberText').hide();
		    				$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
		    				$("#idNumberFlag").val("1");
						}
					}
				},
				_checkBithday:function(){
					//校验生日
					var year = $("#yy_mm_dd").val();
					var mm = $("#mm").val();
					var dd = $("#dd").val();
					if(year=="0"||mm=="0"||dd=="0"){
						$("#bithdayErrMsg").show();
						$("#bithdayFlag").val("0");
					}else{
						$("#bithdayErrMsg").hide();
						$("#bithdayFlag").val("1");
					}
				},
				_submit:function(){
					//校验姓名
					this._checkRealName();
					//校验学历
					this._checkEducation();
					this._checkContactAddress();
					//校验街道地址
					checkCertAddr();
					this._checkInCome();
					//校验省份证
					this._checkIdNumber();
					this.__checkBithday();
					var realNameFlag = $("#realNameFlag").val();
					var educationFlag = $("#educationFlag").val();
					var certAddrFlag =  $("#certAddrFlag").val();
					var provinceCodeFlag =  $("#provinceCodeFlag").val();
					var bithdayFlag =  $("#bithdayFlag").val();
					var inComeFlag =  $("#inComeFlag").val();
					var idNumberFlag =  $("#idNumberFlag").val();
					
					if(realNameFlag!="0"&&educationFlag!="0"&&certAddrFlag!="0"&&provinceCodeFlag!="0"&&bithdayFlag!="0"&&inComeFlag!="0"&&idNumberFlag!="0"){
						
					}
				}
			
		});
			module.exports = QualificationPager
		});

