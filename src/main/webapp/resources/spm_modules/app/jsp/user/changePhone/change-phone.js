define(
		'app/jsp/user/changePhone/change-phone',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), Validator = require('arale-validator/0.10.2/index'), Calendar = require('arale-calendar/1.1.2/index'), Widget = require('arale-widget/1.2.0/widget'), Dialog = require("artDialog/src/dialog"), AjaxController = require('opt-ajax/1.0.0/index');

			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var ChangePhonePager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				// "click [id='randomImg']":"_refrashVitentify",
				},
				init : function() {
					//_hideErroText();
				},
				// 重写父类
				setup : function() {
					ChangePhonePager.superclass.setup.call(this);
					//this._hideErroText();
					this._bindHandle();
				},
				//_hideInfo : function() {},
				// 带下划线的方法，约定为内部私有方法
				_bindHandle : function() {
					$("#password").on("blur", this._checkPassword);
					$("#password").on("focus", this._hidePassword);
					$("#newPhone").on("blur", this._validServiceNewPho);
					$("#newPhone").on("focus", this._hideNewPhone);
					$("#validateCode").on("blur", this._checkValidateCodeEmpty);
					$("#validateCode").on("focus", this._hideValidateCode);
					$("#phoneCode").on("blur", this._checkPhoneCodeEmpty);
					$("#phoneCode").on("focus", this._HidePhoneCode);
					$("#PHONE_IDENTIFY1").on("click", this._getPhoneVitentify1);
					$("#PHONE_IDENTIFY2").on("click", this._getPhoneVitentify2);
					$("#next1").on("click", this._next1);
					$("#next1").on("click", this._next2);
					$("#submit").on("click", this._submit);
					},
						//检查验证码是否为空
						_checkValidateCodeEmpty : function(){
							$("#validateCodeErrMsg").attr("style", "display:none");
							var validateCode = $('#validateCode').val();
							if(validateCode==""){
								$("#validateCodeErrMsgShow").text("验证码不能为空");
								$("#validateCodeErrMsg").show();
								$("#validateCodeEmptyFlag").val("0");
							}
						},
						//检查验证码是否为空
						_checkPhoneCodeEmpty : function(){
							$("#phoneCodeErrMsg").attr("style", "display:none");
							var phoneCode = $('#phoneCode').val();
							if(phoneCode==""){
								$("#phoneCodeErrMsgShow").text("手机验证码不能为空");
								$("#phoneCodeErrMsg").show();
								$("#phoneCodeEmptyFlag").val("0");
							}
						},
						//检查支付密码是否为空
						_checkPasswordEmpty : function(){
							$("#passwordErrMsg").attr("style", "display:none");
							var password = $('#password').val();
							if(password==""){
								$("#passwordErrMsgShow").text("支付密码不能为空");
								$("#passwordErrMsg").show();
								$("#passwordEmptyFlag").val("0");
							}
						},
						//隐藏手机号错误提示
						_hidePhone : function(){
							$("#phoneErrMsg").attr("style", "display:none");
						},
						//隐藏新手机号错误提示
						_hideNewPhone : function(){
							$("#newPhoneErrMsg").attr("style", "display:none");
						},
						//隐藏验证码错误提示
						_hideValidateCode : function(){
							$("#validateCodeErrMsg").attr("style", "display:none");
						},
						//隐藏验证码错误提示
						_hidePhoneCode : function(){
							$("#phoneCodeErrMsg").attr("style", "display:none");
						},
						//隐藏密码错误提示
						_hidePassword : function(){
							$("#passwordErrMsg").attr("style", "display:none");
						},

						// 点击下一步用户信息显示
						_next : function() {
							$("#passwordEmptyFlag").val("1");
							$("#passwordErrFlag").val("1");
							if($('#password').val()==""){
								$("#passwordErrMsgShow").text("密码不能为空");
								$("#passwordErrMsg").show();
								$("#passwordEmptyFlag").val("0");
							}
							var param = {
									password : hex_md5($("#password").val())
								};
								ajaxController.ajax({
									type : "post",
									processing : false,
									async: false, 
									url : _base + "/user/validatePassword",
									dataType : "json",
									data : param,
									message : "正在加载数据..",
									success : function(data) {
										if (data.responseHeader.resultCode == "11110") {
											$("#passwordErrMsg").show();
											$("#passwordErrFlag").val("0");
											return false;
										} else if (data.responseHeader.resultCode == "11111") {
											$("#passwordErrFlag").val("1");
										}
									},
										error : function(XMLHttpRequest,
												textStatus, errorThrown) {
											alert(XMLHttpRequest.status);
											alert(XMLHttpRequest.readyState);
											alert(textStatus);
											}
										});
								var passwordEmptyFlag = $("#passwordEmptyFlag").val();
								var passwordErrFlag = $("#passwordErrFlag").val();
								if(passwordEmptyFlag!=0&&passwordErrFlag!=0){
								$("#changePasswordBorder2").removeClass()
								.addClass("yellow-border");
								$("#changePasswordYuan2").removeClass().addClass(
										"yellow-yuan");
								$("#changePasswordWord2").removeClass().addClass(
										"yellow-word");
								$("#change-password1").hide();
								$("#change-password2").show();
								$("#change-password3").hide();
						}
						},

						// 点击下一步用户信息显示
						_submit : function() {
							$("#newPasswordEmptyFlag").val("1");
							$("#newPasswordErrFlag").val("1");
							$("#newPasswordConfirmEmptyFlag").val("1");
							$("#passwordNotEqualFlag").val("1");
							
							if($('#newPassword').val()==""){
								$("#newPasswordErrMsgShow").text("密码不能为空");
								$("#newPasswordErrMsg").show();
								$("#newPasswordEmptyFlag").val("0");
							}
							if($('#newPasswordConfirm').val()==""){
								$("#newPasswordConfirmErrMsgShow").text("密码不能为空");
								$("#newPasswordConfirmErrMsg").show();
								$("#newPasswordConfirmEmptyFlag").val("0");
							}
							var newPasswordEmptyFlag=$("#newPasswordEmptyFlag").val();
							var newPasswordErrFlag=$("#newPasswordErrFlag").val();
							var newPasswordConfirmEmptyFlag=$("#newPasswordConfirmEmptyFlag").val();
							var passwordNotEqualFlag=$("#passwordNotEqualFlag").val();
							if(newPasswordEmptyFlag!=0&&newPasswordErrFlag!=0&&newPasswordConfirmEmptyFlag!=0&&passwordNotEqualFlag!=0){
							var param = {
									password : hex_md5($("#newPassword").val())
							};
							ajaxController.ajax({
								type : "post",
								processing : false,
								async: false, 
								url : _base + "/user/updatePassword",
								dataType : "json",
								data : param,
								message : "正在加载数据..",
								success : function(data) {
									if (data.responseHeader.resultCode == "11112") {
										$("#changePasswordBorder3").removeClass()
										.addClass("yellow-border");
										$("#changePasswordYuan3").removeClass().addClass(
										"yellow-yuan");
										$("#changePasswordWord3").removeClass().addClass(
										"yellow-word");
										$("#change-password1").hide();
										$("#change-password2").hide();
										$("#change-password3").show();
									}
								},
								error : function(XMLHttpRequest,
										textStatus, errorThrown) {
									alert(XMLHttpRequest.status);
									alert(XMLHttpRequest.readyState);
									alert(textStatus);
								}
							});
						}
						},
						// 密码校验
						_passwordConfirmation : function() {
							var inputPassword = $("#newPassword").val();
							var confirmationPassword = $("#newPasswordConfirm").val();
							
							if (inputPassword != confirmationPassword) {
								/*$("#confirmationPasswordImage").attr('src',
										_base + '/theme/slp/images/icon-a.png');*/
								$("#newPasswordConfirmErrMsgShow").text("两次输入的密码不一致");
								$("#newPasswordConfirmErrMsg").show();
								$("#passwordNotEqualFlag").val("0");
								return false;
							} else {
								$("#newPasswordConfirmErrMsg").hide();
								$("#passwordNotEqualFlag").val("1");
								return true;
							}
						},
			
			
			// 获取绑定手机短信验证码
			_getPhoneVitentify1 : function() {
				$("#phoneCodeErrMsg").attr("style", "display:none");
				var phoneFlag = $('#phoneFlag').val();
				if (phoneFlag != "0") {
					var step = 59;
					$('#PHONE_IDENTIFY').val('重新发送60');
					$("#PHONE_IDENTIFY").attr("disabled", true);
					var _res = setInterval(
						function() {
						$("#PHONE_IDENTIFY").attr(
								"disabled", true);// 设置disabled属性
						$('#PHONE_IDENTIFY').val(
								step + 's后重新发送');
						step -= 1;
						if (step <= 0) {
							$("#PHONE_IDENTIFY")
									.removeAttr("disabled"); // 移除disabled属性
							$('#PHONE_IDENTIFY').val(
									'获取验证码');
							clearInterval(_res);// 清除setInterval
							}
						}, 1000);
					var param = {
						phone : $("#phone").val()
					};
					ajaxController.ajax({
						type : "post",
						processing : false,
						url : _base + "/user/verify/sendPhoneVerify",
						dataType : "json",
						data : param,
						message : "正在加载数据..",
						success : function(data) {
							if (data.responseHeader.resultCode == "9999") {
								$('#phoneCodeErrMsg').text("1分钟后可重复发送 ");
								$("#phoneCodeErrMsg").attr("style","display:");
								$("#phoneCodeFlag").val("0");
								return false;
							} else if (data.responseHeader.resultCode == "100002") {
								var msg = data.statusInfo;
								$('#phoneCodeErrMsg').text(msg);
								$("#phoneCodeErrMsg").attr("style","display:");
								return false;
							}
						},
							error : function(XMLHttpRequest,
									textStatus, errorThrown) {
								alert(XMLHttpRequest.status);
								alert(XMLHttpRequest.readyState);
								alert(textStatus);
								}

							});
				}

			},
			// 获取新手机短信验证码
			_getPhoneVitentify2 : function() {
				$("#phoneCodeErrMsg").attr("style", "display:none");
				var phoneFlag = $('#phoneFlag').val();
				if (phoneFlag != "0") {
					var step = 59;
					$('#PHONE_IDENTIFY').val('重新发送60');
					$("#PHONE_IDENTIFY").attr("disabled", true);
					var _res = setInterval(
							function() {
								$("#PHONE_IDENTIFY").attr(
										"disabled", true);// 设置disabled属性
								$('#PHONE_IDENTIFY').val(
										step + 's后重新发送');
								step -= 1;
								if (step <= 0) {
									$("#PHONE_IDENTIFY")
									.removeAttr("disabled"); // 移除disabled属性
									$('#PHONE_IDENTIFY').val(
									'获取验证码');
									clearInterval(_res);// 清除setInterval
								}
							}, 1000);
					var param = {
							phone : $("#newPhone").val()
					};
					ajaxController.ajax({
						type : "post",
						processing : false,
						url : _base + "/user/toSendPhone",
						dataType : "json",
						data : param,
						message : "正在加载数据..",
						success : function(data) {
							if (data.responseHeader.resultCode == "9999") {
								$('#phoneCodeErrMsg').text("1分钟后可重复发送 ");
								$("#phoneCodeErrMsg").attr("style","display:");
								$("#phoneCodeFlag").val("0");
								return false;
							} else if (data.responseHeader.resultCode == "100002") {
								var msg = data.statusInfo;
								$('#phoneCodeErrMsg').text(msg);
								$("#phoneCodeErrMsg").attr("style","display:");
								return false;
							}
						},
						error : function(XMLHttpRequest,
								textStatus, errorThrown) {
							alert(XMLHttpRequest.status);
							alert(XMLHttpRequest.readyState);
							alert(textStatus);
						}
						
					});
				}
				
			},
			
			// 校验手机
			_validServicePho : function() {
				$("#phoneErrMsg").attr("style", "display:none");
				var phone = $('#phone').val();
				if (phone == "") {
					$("#phoneErrMsg").attr("style", "display:");
					$('#phoneErrMsg').attr('src',
							_base + '/theme/slp/images/icon-a.png');
					$('#phoneErrMsgShow').text("手机号不能为空");
					$("#phoneErrMsg").show();
					$('#phoneErrFlag').val("0");
					return false;
				} else if (!(/^0?1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
					$("#phoneErrMsg").attr("style", "display:");
					$('#phoneErrMsg').attr('src',
							_base + '/theme/slp/images/icon-a.png');
					$('#phoneErrMsgShow').text("请输入正确有效的手机号码");
					$("#phoneErrMsg").show();
					$('#phoneErrFlag').val("0");
					return false;
				} 
					
			},
			
			// 校验新手机
			_validServiceNewPho : function() {
				$("#newPhoneErrMsg").attr("style", "display:none");
				var phone = $('#newPhone').val();
				if (phone == "") {
					$("#newPhoneErrMsg").attr("style", "display:");
					$('#newPhoneErrMsg').attr('src',
							_base + '/theme/slp/images/icon-a.png');
					$('#newPhoneErrMsgShow').text("手机号不能为空");
					$("#newPhoneErrMsg").show();
					$('#newPhoneErrFlag').val("0");
					return false;
				} else if (!(/^0?1[3|4|5|8][0-9]\d{8}$/.test(phone))) {
					$("#newPhoneErrMsg").attr("style", "display:");
					$('#newPhoneErrMsg').attr('src',
							_base + '/theme/slp/images/icon-a.png');
					$('#newPhoneErrMsgShow').text("请输入正确有效的手机号码");
					$("#newPhoneErrMsg").show();
					$('#newPhoneErrFlag').val("0");
					return false;
				} 
					
			}
		});
			module.exports = ChangePhonePager
		});
