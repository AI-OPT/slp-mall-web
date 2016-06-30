define('app/jsp/order/orderInfoDetail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OrderInfoDetailPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='colsePhoneDialog']":"_closePhoneData"
        },
    	//重写父类
    	setup: function () {
    		OrderInfoDetailPager.superclass.setup.call(this);
    		this._renderOrderInfo();
    	},
    	//隐藏区域
    	_displayHideUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="none";  
    	},
    	//显示区域
    	_displayShowUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="block";//display为空的话会好使，为block会使后边的空间换行  
    	},
    	_visibilityHideUI:function(id)
    	{
    		var ui =document.getElementById(id);
    		ui.style.visibility="hidden";
    	},
    	_visibilityShowUI:function(id)
    	{
    		var ui =document.getElementById(id);
    		ui.style.visibility="visible";
    	},
    	_renderOrderInfo: function(){
	        if(orderDetail != null && orderDetail != 'undefined'){
	        	var template = $.templates("#orderTemple");
    			var htmlOutput = template.render(orderDetail);
    			$("#orderData").html(htmlOutput);
	        }else{
    			$("#orderData").html("没有搜索到相关信息");
	        }
    	},
    	//显示手机号
    	_showPhoneInfo:function(prodExtendInfo){
    		if(prodExtendInfo != null){
    			var phoneArray = prodExtendInfo.split(",");
    			var html = "";
    			for(var i=0;i<phoneArray.length;i++){
    				html += "<p>"+phoneArray[i]+" </p>";
    			}
    			$("#phoneListData").html(html);
    		}
    		$('.eject-mask').fadeIn(100);
    		$('.eject-medium').slideDown(200);
    	},
    	_closePhoneData:function(){
			$('.eject-mask').fadeOut(100);
			$('.eject-medium').slideUp(150);
    	}
    });
    
    module.exports = OrderInfoDetailPager
});

