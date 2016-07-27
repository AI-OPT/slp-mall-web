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
			var AgentPersonalPager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				Statics: {
		    		DEFAULT_PAGE_SIZE: 5,
		    		USER_LEFT_MNU_ID: "left_mnu_qualification"
		    	},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				//真实姓名校验
				"blur [id='realName']":"_checkcustName",
	    		"focus [id='realName']":"_showcustNameTip",
	    		//身份证校验
	    		"focus [id='idNumber']":"_showIdNumberTip",
	    		"blur [id='idNumber']":"_checkIdNumber",
	    		//学历校验
	    		//"change [id='custEducation']":"_checkcustEducation",
	    		"blur [id='countyCode']":"_checkContactAddress",
	    		//生日校验
	    		//"blur [id='dd']":"_checkBithday",
	    		//收入校验
	    		//"change [id='inCome']":"_checkInCome",
	    		"focus [id='introduce']":"_showIntroduceTip",
	    		"blur [id='introduce']":"_checkIntroduceTip",
	    		
	    		"click [id='man']":"_checkCustSex",
	    		"click [id='woman']":"_checkCustSex",
	    		
	    		"click [id='updateAgengPersonal']":"_updateAgengPersonal",
				},
				init : function() {
				},
				// 重写父类
				setup : function() {
					$("#provinceCode").val("0");
					$("#cityCode").val("0");
					$("#countyCode").val("0");
					AgentPersonalPager.superclass.setup.call(this);
					birth.init('yy_mm_dd');
					this._birthday();
					this._gender();
					activeUserLeftMenu(AgentPersonalPager.USER_LEFT_MNU_ID);
				},
				
				_gender:function(){
					if(gender=='0')
						$("#man").attr("checked","checked");
					if(gender=='1')
						$("#woman").attr("checked","checked");
				},
				_birthday:function(){
					if(year!=""){
					$("#yy_mm_dd").val(year);
					$("#mm").val(month);
					var t = ((year % 4)==0) && ((year % 100)!=0) || ((year % 400)==0);
					var d;
					 switch( parseInt(month) ){
			            case 1:
			            case 3:
			            case 5:
			            case 7:
			            case 8:
			            case 10:
			            case 12:
			                d = 31;
			                break;
			            case 4:
			            case 6:
			            case 9:
			            case 11:
			                d = 30;
			                break;
			            case 2:
			                d = t ? 29 : 28;
			        }
				 for(var j = 0; j < d; j++) {
			          document.getElementById('dd').options.add(new Option(j + 1, j + 1));
			        }
					$("#dd").val(day);
					}
				},
				_showcustNameTip:function(){
					$("#realNameErrMsg").show();
					$("#realNameText").text('2-24个字符，可用汉字或英语字母');
					$("#realNameText").show();
		    		$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_showIdNumberTip:function(){
					$("#idNumberErrMsg").show();
					$("#idNumberText").show();
					$("#idNumberImage").show();
					$("#idNumberText").text('18位数字');
		    		$('#idNumberImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_showIntroduceTip:function(){
					$("#personalRemarkErrMsg").show();
					$("#personalRemarkText").show();
					$("#personalRemarkImage").show();
					$("#personalRemarkText").text('20-300个字符');
		    		$('#personalRemarkImage').attr('src',_base+'/resources/slpmall/images/icon-d.png');
				},
				_checkcustName:function(){
					var name = $("#realName").val();
					var reg = /^[\u4e00-\u9fa5a-zA-Z]{2,24}$/;
					if(name==null||name==""){
						$('#realNameErrMsg').show();
	    				$("#realNameImage").show();
	        			$('#realNameText').text("2-24个字符，可用汉字或英语字母");
	        			$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	        			$("#realNameFlag").val("0");
					}else{
						if(name.match(reg)){
							$('#realNameErrMsg').show();
		    				$('#realNameText').hide();
		    				$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
		    				$("#realNameImage").show();
		    				$("#realNameFlag").val("1");
		    			}else{
		    				$('#realNameErrMsg').show();
		    				$("#realNameImage").show();
		        			$('#realNameText').text("2-24个字符，可用汉字或英语字母");
		        			$('#realNameImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
		        			$("#realNameFlag").val("0");
		    			}
					}
				},
				_checkcustEducation:function(){
					var custEducationVal = $("#custEducation").val();
					if(custEducationVal=="0"){
						$("#custEducationErrMsg").show();
						$("#custEducationFlag").val("0");
					}else{
						$("#custEducationErrMsg").hide();
						$("#custEducationFlag").val("1");
					}
				},
				_checkContactAddress:function(){
					//校验联系地址
					var princeCode = $("#provinceCode").val();
					var cityCode = $("#cityCode").val();
					var countyCode = $("#countyCode").val();
					if(princeCode=="0"||princeCode==null||cityCode=="0"||cityCode==null||countyCode=="0"||countyCode==null){
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
						var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X)$)/;
						if(!reg.test(idNumber)){
							$('#idNumberErrMsg').show();
		    				$("#idNumberImage").show();
		    				$("#custNameText").show();
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
				_updateAgengPersonal:function(){
					$("#qf-edit").show(10);
					$("#qf-browse").hide(10);
				},
				_checkIntroduceTip:function(){
					var introduce = $("#introduce").val();
					if(introduce!=""){
						if(introduce.length>=20&&introduce.length<=300){
							$('#personalRemarkErrMsg').show();
		    				$('#personalRemarkText').hide();
		    				$('#personalRemarkImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
						}else{
							$('#personalRemarkErrMsg').show();
		    				$("#personalRemarkImage").show();
		    				$("#personalRemarkText").show();
		        			$('#personalRemarkText').text("20-300个字符");
		        			$('#personalRemarkImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
						}
					}else{
						$('#personalRemarkErrMsg').hide();
	    				$('#personalRemarkText').hide();
	    				$("#personalRemarkImage").hide();
					}
					
				},
				_checkCustSex:function(){
					var val=$('input:radio[name="custSex"]:checked').val();
					if(typeof(val)=="undefined"){ 
						$('#custSexErrMsg').show();
	    				$("#custSexImage").show();
	    				$("#custSexText").show();
	        			$('#custSexText').text("请选择性别");
	        			$('#custSexImage').attr('src',_base+'/resources/slpmall/images/icon-a.png');
	        			$('#custSexFlag').val("0");
					}else{
						$('#custSexErrMsg').show();
	    				$('#custSexText').hide();
	    				$('#custSexImage').attr('src',_base+'/resources/slpmall/images/icon-b.png');
	    				$('#custSexFlag').val("1");
					}
				}
		});
			module.exports = AgentPersonalPager
		});

