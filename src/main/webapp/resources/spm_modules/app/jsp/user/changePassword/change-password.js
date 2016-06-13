define(
		'app/jsp/user/changePassword/change-password',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), Validator = require('arale-validator/0.10.2/index'), Calendar = require('arale-calendar/1.1.2/index'), Widget = require('arale-widget/1.2.0/widget'), Dialog = require("artDialog/src/dialog"), AjaxController = require('opt-ajax/1.0.0/index');

			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var ChangePasswordPager = Widget.extend({
				// 属性，使用时由类的构造函数传入
				attrs : {},
				Statics: {
		    		DEFAULT_PAGE_SIZE: 5,
		    		USER_LEFT_MNU_ID: "left_mnu_security_set"
		    	},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				// "click [id='randomImg']":"_refrashVitentify",
				},
				init : function() {
					_hideErroText();
				},
				// 重写父类
				setup : function() {
					ChangePasswordPager.superclass.setup.call(this);
		    		activeUserLeftMenu(ChangePasswordPager.USER_LEFT_MNU_ID);
					this._bindHandle();
				},
				//_hideInfo : function() {},
				// 带下划线的方法，约定为内部私有方法
				_bindHandle : function() {
					$("#password").on("blur", this._checkPasswordEmpty);
					$("#password").on("focus", this._hidePassword);
					$("#newPassword").on("blur", this._checkNewPasswordEmpty);
					$("#newPassword").on("blur", this._checkNewPasswordEmpty);
					$("#newPassword").on("blur", this._validServicePaw);
					$("#newPassword").on("focus", this._hideNewPassword);
					$("#newPasswordConfirm").on("blur", this._checkNewPasswordConfirmEmpty);
					$("#newPasswordConfirm").on("blur", this._passwordConfirmation);
					$("#newPasswordConfirm").on("focus", this._hideNewPasswordConfirm);
					$("#next").on("click", this._next);
					$("#submit").on("click", this._submit);
					},
						//检查密码是否为空
						_checkPasswordEmpty : function(){
							$("#passwordErrMsg").attr("style", "display:none");
							var password = $('#password').val();
							if(password==""){
								$("#passwordErrMsgShow").text("密码不能为空");
								$("#passwordErrMsg").show();
								$("#passwordEmptyFlag").val("0");
							}
						},
						//检查新密码是否为空
						_checkNewPasswordEmpty : function(){
							$("#newPasswordErrMsg").attr("style", "display:none");
							var newPassword = $('#newPassword').val();
							if(newPassword==""){
								$("#newPasswordErrMsgShow").text("密码不能为空");
								$("#newPasswordErrMsg").show();
								$("#newPasswordEmptyFlag").val("0");
							}
						},
						//检查新密码确认是否为空
						_checkNewPasswordConfirmEmpty : function(){
							$("#newPasswordConfirmErrMsg").attr("style", "display:none");
							var newPasswordConfirm = $('#newPasswordConfirm').val();
							if(newPasswordConfirm==""){
								$("#newPasswordConfirmErrMsgShow").text("密码不能为空");
								$("#newPasswordConfirmErrMsg").show();
								$("#newPasswordConfirmEmptyFlag").val("0");
							}
						},
						//隐藏确认密码错误提示
						_hidePassword : function(){
							$("#passwordErrMsg").attr("style", "display:none");
						},
						//隐藏新密码错误提示
						_hideNewsPassword : function(){
							$("#newPasswordErrMsg").attr("style", "display:none");
						},
						//隐藏新密码确认错误提示
						_hideNewsPasswordConfirm : function(){
							$("#newPasswordConfirmErrMsg").attr("style", "display:none");
						},
						
						// 校验密码
						_validServicePaw : function() {
							var password = $('#newPassword').val();
							if (password == "") {
								$('#newPasswordErrMsgShow').text("请输入密码");
								$("#newPasswordErrMsg").show();
								return false;
							} else if (/[\x01-\xFF]*/.test(password)) {
							  if (/^\S*$/.test(password)) {
								if (/^[\x21-\x7E]{6,20}$/.test(password)) {
								$("#newPasswordErrMsg").hide();
								$('#newPasswordErrFlag').val("1");
								} else {
								$("#newPasswordErrMsg").show();
								$('#newPasswordErrMsgShow').text("6-20个字符，可用字母、数字及符号的组合 ");
								$('#newPasswordErrFlag').val("0");
								return false;
								}
								} else {
									$('#newPasswordErrMsgShow').text("不允许有空格 ");
									$("#newPasswordErrMsg").attr("style", "display:");
									$('#newPasswordErrFlag').val("0");
									return false;
								}
							} else {
								$('#newPasswordErrMsgShow').text("支持数字、字母、符号组合 ");
								$("#newPasswordErrMsg").attr("style", "display:");
								$('#newPasswordErrFlag').val("0");
								return false;
							}
						},

						// 点击下一步用户信息显示
						_next : function() {
							$("#passwordEmptyFlag").val("1");
							$("#passwordErrFlag").val("1");
							if($('#password').val()==""){
								alert($('#password').val());
								$("#passwordErrMsgShow").text("密码不能为空");
								$("#passwordErrMsg").show();
								$("#passwordEmptyFlag").val("0");
								return false;
							}
							var param = {
									password : hex_md5($("#password").val())
								};
								ajaxController.ajax({
									type : "post",
									processing : false,
									async: false, 
									url : _base + "/user/password/validatePassword",
									dataType : "json",
									data : param,
									message : "正在加载数据..",
									success : function(data) {
										if (data.responseHeader.resultCode == "11110") {
											$("#passwordErrMsgShow").text("原密码错误");
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
							$("#newPasswordConfirmEmptyFlag").val("1");
							
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
							if(newPasswordEmptyFlag!='0'&&newPasswordErrFlag!='0'&&newPasswordConfirmEmptyFlag!='0'&&passwordNotEqualFlag!='0'){
							var param = {
									password : hex_md5($("#newPassword").val())
							};
							ajaxController.ajax({
								type : "post",
								processing : false,
								async: false, 
								url : _base + "/user/password/updatePassword",
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
								$("#newPasswordConfirmErrMsgShow").text("两次输入的密码不一致");
								$("#newPasswordConfirmErrMsg").show();
								$("#passwordNotEqualFlag").val("0");
								return false;
							} else {
								$("#newPasswordConfirmErrMsg").hide();
								$("#passwordNotEqualFlag").val("1");
								return true;
							}
						}
			});

			module.exports = ChangePasswordPager
		});
