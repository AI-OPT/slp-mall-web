define('app/jsp/creditcontrol/editStandard', function (require, exports, module) {
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
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var editStandardPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"click [id='editBtn']":"_editStanderd",
    		"click [id='cancelBtn']":"_cancelEdit",
    		"blur [id='ruleName']":"_checkRuleName",
    		"blur [id='pressPayment']":"_checkPressPayment",
    		"blur [id='description']":"_checkDescription",
        },
    	//重写父类
    	setup: function () {
    		editStandardPager.superclass.setup.call(this);
    		this._initPage();
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("信控配置管理","修改信控规则");
      		//左侧菜单选中样式
      		$("#mnu_omc_config").addClass("current");
      	},
      	_checkRuleName:function(){
    		var ruleName = jQuery.trim($("#ruleName").val());
    		if(ruleName == "" || ruleName == null || ruleName == undefined){
    			this._controlMsgText("ruleNameMsg","信控规则名称不能为空");
    			this._controlMsgAttr("ruleNameMsgDiv",2);
    			return false;
    		}
    		if(ruleName.replace(/[^\x00-\xff]/g,"aaa").length>40){
    			this._controlMsgText("ruleNameMsg","信控规则名称长度不能大于40字符");
    			this._controlMsgAttr("ruleNameMsgDiv",2);
    			return false;
    		}else{
    			this._controlMsgText("ruleNameMsg","");
    			this._controlMsgAttr("ruleNameMsgDiv",1);
    			return true;
    		}
    	},
    	_checkPressPayment:function(){
    		var pressPayment = jQuery.trim($("#pressPayment").val());
    		if(pressPayment == "" || pressPayment == null || pressPayment == undefined){
    			this._controlMsgText("pressPaymentMsg","催缴值金额不能为空");
    			this._controlMsgAttr("pressPaymentMsgDiv",2);
    			return false;
    		}else if(!/^(0|[1-9][0-9]*)$/.test(pressPayment)){
    			this._controlMsgText("pressPaymentMsg","催缴值金额只能为非负整数");
    			this._controlMsgAttr("pressPaymentMsgDiv",2);
    			return false;
    		}else if(pressPayment.length>18){
    			this._controlMsgText("pressPaymentMsg","催缴值金额不能大于18位");
    			this._controlMsgAttr("pressPaymentMsgDiv",2);
    			return false;
    		}else{
    			this._controlMsgText("pressPaymentMsg","");
    			this._controlMsgAttr("pressPaymentMsgDiv",1);
    			return true;
    		}
    	},
    	_checkDescription:function(){
    		var description = jQuery.trim($("#description").val());
    		if(description != "" && description != null && description != undefined && description.replace(/[^\x00-\xff]/g,"aaa").length>128){
    			this._controlMsgText("descriptionMsg","信控规则描述不能大于128个字符");
    			this._controlMsgAttr("descriptionMsgDiv",2);
    			return false;
    		}else{
    			this._controlMsgText("descriptionMsg","");
    			this._controlMsgAttr("descriptionMsgDiv",1);
    			return true;
    		}
    	},
    	//控制显示内容
		_controlMsgText: function(id,msg){
			var doc = document.getElementById(id+"");
			doc.innerText=msg;
		},
		//控制显隐属性 1:隐藏 2：显示
		_controlMsgAttr: function(id,flag){
			var doc = document.getElementById(id+"");
			if(flag == 1){
				doc.setAttribute("style","display:none");
			}else if(flag == 2){
				doc.setAttribute("style","display");
			}
		},
    	_checkStandardData:function(){
    		var checkName = this._checkRuleName();
    		var checkPay = this._checkPressPayment();
    		var checkDesc = this._checkDescription();
    		return checkName&&checkPay&&checkDesc;
    	},
		//检查身份信息
    	_editStanderd:function(){
			var _this = this;
			var checkStandardData = this._checkStandardData();
			if(!checkStandardData){
    			return false;
    		}
			ajaxController.ajax({
				type : "POST",
				data : _this._getStandardData(),
				dataType: 'json',
				url :_base+"/creditControl/standard/modifyStandard",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var isSuccess = data.responseHeader.success;
					if(isSuccess){
						var msgDialog = Dialog({
			    	        title: '提示',
			    	        content: '修改信控规则成功',
			    	        ok: function () {
			    	        	this.close();
								var url = data.data;
								window.location.href = _base+"/creditControl/standard/list";
			    	        }
						});
						msgDialog.showModal();
					}
				}
			});
		},
		//获取界面填写信息
		_getStandardData:function(){
			return{
				"ruleId":function () {
			        return jQuery.trim($("#ruleId").val())
			    },
				"ruleName":function () {
			        return jQuery.trim($("#ruleName").val())
			    },
				"pressPayment":function () {
			        return jQuery.trim($("#pressPayment").val())
			    },
				"description":function () {
			        return jQuery.trim($("#description").val())
			    }
			}
		},
		_cancelEdit:function(){
			window.history.back(-1);
		}
		
    });
    module.exports = editStandardPager
});
