define('app/jsp/order/orderSubmit', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
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
    var OrderSubmitPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//查询
            "click #useBalanceChk":"_showBalanceBtnClick",
            "click #useBalanceBtn":"_useBalanceBtnClick"
        },
    	//重写父类
    	setup: function () {
    		OrderSubmitPager.superclass.setup.call(this);
    		this._renderOrderSubmitInfo();
    	},
    	_renderOrderSubmitInfo: function(){
	        if(orderSubmitJson != null && orderSubmitJson != 'undefined'){
	        	var template = $.templates("#orderSubmitTemplate");
    			var htmlOutput = template.render(orderSubmitJson);
    			$("#orderSubmitForm").html(htmlOutput);
	        }else{
    			$("#orderSubmitForm").html("没有相应的订单信息");
	        }
    	},
    	_showBalanceBtnClick:function(){
      		var balance=$("#abalance").val();
      		var orderAmount=$("#bamount").val();
      		if(balance<orderAmount){
      			alert("余额不足,请选择其它方式支付");
      			return;
      		}
    		$(".balance-table").slideToggle(100);
    		$(".balance-title").toggleClass("reorder remove");
    		document.getElementById("useBalance").value=orderAmount;
      	},
      	_useBalanceBtnClick:function(){
      		var url=_base+"/order/usebalance?orderId="+$("#orderId").val()+"&balance="+$("#useBalance").val()+"&userPassword="+$("#userPassword").val();
      		window.location.href=url;

      	}
    	
    });
    
    module.exports = OrderSubmitPager
});

