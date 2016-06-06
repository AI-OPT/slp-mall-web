define('app/jsp/balance/phonebook/phonebookmgr', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    require("valuevalidator/jquery.valuevalidator.js");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var PhoneBookMgrPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='HREF_ADD_TEL_GROUP']":"_showAddTelGroupWindow",
            "click [id='BTN_ADD_TEL_GROUP']": "_submitNewTelGroup"
        },
    	//重写父类
    	setup: function () {
    		PhoneBookMgrPager.superclass.setup.call(this);
    		this._init(); 
    	},
    	
    	_init: function(){
    		
    	},
    	
    	_showAddTelGroupWindow: function(){
    		//alert(this.get("userId"));
    		$('.trash-close').click(function(){
    			$('.eject-mask').fadeIn(100);
    			$('.eject-samll').slideDown(200);
    		});
    		$('.eject-samll-title .img').click(function(){
    			$('.eject-mask').fadeOut(100);
    			$('.eject-samll').slideUp(150);
    		});
    		$('.eject-samll-confirm .close-btn').click(function(){
    			$('.eject-mask').fadeOut(100);
    			$('.eject-samll').slideUp(150);
    		});
    	},
    	
    	_submitNewTelGroup: function(){
    		var validator = new $.ValueValidator();
    		validator.addRule({
				labelName: "通信录组",
				fieldName: "TEL_GROUP_NAME",
				getValue: function(){
					var v = $("#TEL_GROUP_NAME").val();
					return v;
				},
				fieldRules: {
					required: true
				},
				ruleMessages: {
					required: "请输入通讯录组名称"
				}
			});
			var res=validator.fireRulesAndReturnFirstError();
			if(res){
				$("#LBL_ADD_TEL_GROUP").show().find("#SPAN_ADD_TEL_GROUP_TIP").html(res);
				return;
			}else{
				$("#LBL_ADD_TEL_GROUP").hide().find("#SPAN_ADD_TEL_GROUP_TIP").html("");
			}
			ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/submitNewTelGroup",
				data: {
					userId: this.get("userId"),
					telGroupName: $.trim($("#TEL_GROUP_NAME").val())
				},
				success: function(data){
					if(data.data){
						
					}
				}
			});
    	}
    	
    	
    });
    
    module.exports = PhoneBookMgrPager
});

