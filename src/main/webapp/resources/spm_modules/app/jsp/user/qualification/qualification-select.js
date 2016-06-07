define(
		'app/jsp/user/qualification/qualification-select',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), Validator = require('arale-validator/0.10.2/index'), Calendar = require('arale-calendar/1.1.2/index'), Widget = require('arale-widget/1.2.0/widget'), Dialog = require("artDialog/src/dialog"), AjaxController = require('opt-ajax/1.0.0/index');

			// 实例化AJAX控制处理对象
			var ajaxController = new AjaxController();

			// 定义页面组件类
			var QualificationPager = Widget.extend({
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
					QualificationPager.superclass.setup.call(this);
					//this._hideErroText();
					this._bindHandle();
				},
				//_hideInfo : function() {},
				// 带下划线的方法，约定为内部私有方法
				_bindHandle : function() {
					$("#toIdentity").on("click", this._toIdentity);
					},
				_toIdentity : function(){
					var personal = $("#personal");
					var enterprise = $("#enterprise")
					if(personal.hasClass("select")&&!enterprise.hasClass("select")){
						window.location.href = "geren";
					}
					if(!personal.hasClass("select")&&enterprise.hasClass("select")){
						window.location.href = "qiye";
					}
				}
			
		});
			module.exports = QualificationPager
		});

	function addPersonalClass(){
		$("#personal").removeClass("select").addClass("select");
		$("#enterprise").removeClass("select");
	}
	
	function addEnterpriseClass(){
		$("#personal").removeClass("select");
		$("#enterprise").removeClass("select").addClass("select");
	}
