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
            "click #useBalanceBtn":"_useBalanceBtnClick",
            "click #gotoPayBtn":"_popSubmitWin"
        },
    	//重写父类
    	setup: function () {
    		OrderSubmitPager.superclass.setup.call(this);
    		this._renderOrderSubmitInfo();
    	},
    	_popSubmitWin: function(){
    		Dialog({
				title : '在线支付提示',
				width : '200px',
				height : '50px',
				content : "请在弹出的新页面中完成订单的支付！",
				cancel :false,
				button: [
					        {
					            value: '支付完成',
					            callback: function () {
					            	window.location.href = _base+"/myorder/list";
					            },
					            autofocus: true
					        },
					        {
					            value: '支付遇到问题',
					            callback: function () {
					            	window.location.href = _base+"/myorder/list";
					            }
					        }
					    ]
			}).showModal();
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
      		var temBalance=$("#abalance").val();
      		var temOrderAmount=$("#bamount").val();
      		var balance=parseFloat(temBalance.replace(/,/g,""));
      		var orderAmount=parseFloat(temOrderAmount.replace(/,/g,""));
      		if(balance<orderAmount){
      			//alert("余额不足,请选择其它方式支付");
      			Dialog({
					title : '提示',
					width : '200px',
					height : '50px',
					content : "余额不足,请选择其它方式支付",
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
      			return;
      		}
    		$(".balance-table").slideToggle(100);
    		$(".balance-title").toggleClass("reorder remove");
    		document.getElementById("useBalance").value=orderAmount;
      	},
      	_useBalanceBtnClick:function(){
      		var	param={
					balance: $("#useBalance").val(),
					userPassword:$("#userPassword").val(),
					orderId:$("#orderId").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "账户余额支付中，请等待...",
						url: _base+"/order/usebalance",
						data:param,
						success: function(data){
							var resultMessage = data.responseHeader.resultMessage;
							var success = data.responseHeader.success;
							var orderId=data.data.orderId;
							var serialNo=data.data.serialNo;
							if(!success){
								Dialog({
									title : '提示',
									width : '200px',
									height : '50px',
									content : "支付失败:"+resultMessage,
									okValue : "确定",
									ok : function() {
										this.close;
									}
								}).showModal();
				      			return;
							}else{
								window.location.href = _base+ "/order/balancePay?orderId="+orderId+"&serialNo="+serialNo;
							}
							
						}
					}
      		);
      	}
    	
    });
    
    module.exports = OrderSubmitPager
});

