define('app/jsp/order/orderList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var OrderListPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5,
    		USER_LEFT_MNU_ID: "left_mnu_order_myorder"
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='searchOrderBtn']":"_searchOrderList",
            "click [id='changeSearchType']":"_changeSearchType",
            "blur [id='orderTimeBeginQ']":"_changeBeginDate",
            "blur [id='orderTimeEndQ']":"_changeEndDate"
        },
    	//重写父类
    	setup: function () {
    		OrderListPager.superclass.setup.call(this);
    		activeUserLeftMenu(OrderListPager.USER_LEFT_MNU_ID);
    		this._initSearchType();
    		this._initOrderType();
    		this._initPayStyle();
    		this._searchOrderList();
    		this._bindCalendar();
    		
    	},
    	_initSearchType:function(){
    		$("#searchType").val("1");
    	},
    	_initOrderType:function(){
    		var orderTypeElmt = document.getElementById("orderTypeQ");
    		for(var i=0;i<orderStyleParams.length;i++){
    			orderTypeElmt.options.add(new Option(orderStyleParams[i].columnDesc,orderStyleParams[i].columnValue));
    		}
    	},
    	_initPayStyle:function(){
    		var orderTypeElmt = document.getElementById("payStyleQ");
    		for(var i=0;i<payStyleParams.length;i++){
    			orderTypeElmt.options.add(new Option(payStyleParams[i].columnDesc,payStyleParams[i].columnValue));
    		}
    	},
    	_bindCalendar:function(){
    		var beginCalendar = new Calendar({trigger: '#timeBeginId',output:"#orderTimeBeginQ"});
    		var endCalendar = new Calendar({trigger: '#timeEndId',output:"#orderTimeEndQ"});
		},
		_changeBeginDate:function(){
			var sysDataStr = this._getSysDate();
			var beginDate = $("#orderTimeBeginQ").val();
			var endCalendar = new Calendar({trigger: '#timeEndId',output:"#orderTimeEndQ"});
			if(beginDate == null || beginDate == "" || beginDate == undefined){
				endCalendar.range([null,null]);
			}else{
				endCalendar.range([beginDate,null]);
			}
		},
		_changeEndDate:function(){
			var sysDataStr = this._getSysDate();
			var endDate = $("#orderTimeEndQ").val();
			var beginCalendar = new Calendar({trigger: '#timeBeginId',output:"#orderTimeBeginQ"});
			if(endDate == null || endDate == "" || endDate == undefined){
				beginCalendar.range([null,null]);
			}else{
				beginCalendar.range([null,endDate]);
			}
		},
		_changeOrderState:function(orderStateDiv,state){
			$(".order-list-table a").removeClass("current");
			orderStateDiv.className="current";
			$("#searchOrderState").val(state);
			this._searchOrderList();
		},
		_getSysDate:function(){
			var sysDate = new Date();
  			var year = sysDate.getFullYear();    //获取完整的年份(4位,1970-????)
  			var month = sysDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
  			var day = sysDate.getDate();
  			if(month<10){
  				month = "0"+month;
  			}
  			if(day<10){
  				day = "0"+day;
  			}
  			return year+"-"+month+"-"+day;
		},
    	//我的订单 点击展开
    	_changeSearchType:function () {
    			$("#changeSearchType").children('i').toggleClass("icon-angle-down  icon-angle-up");
    			$("#changeSearchType").parents().children('.open-gaoj').slideToggle(100);
    			
    			$("#payStyleQ").val("");
    			$("#orderTimeBeginQ").val("");
    			$("#orderTimeEndQ").val("");
    			var searchType = $("#searchType").val();
    			if(searchType == "1"){
    				$("#searchType").val("2");
    				$("#selectTimeQ").val("");
    				this._visibilityHideUI("selectTimeDiv");
    			}else{
    				$("#searchType").val("1");
    				$("#selectTimeQ").val("1");
    				this._visibilityShowUI("selectTimeDiv");
    			}
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
    	_searchOrderList: function(){
    		var _this = this;
    		var url = _base+"/myorder/getOrderListData";
    		var queryData = this._getSearchParams();
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : queryData,
	           	pageSize: OrderListPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            renderId:"orderListData",
	            messageId:"showMessageDiv",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#orderListTemple");
    					var htmlOutput = template.render(data);
    					$("#orderListData").html(htmlOutput);
	            	}else{
    					$("#orderListData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	_getSearchParams:function(){
    		return {
    			"searchType":jQuery.trim($("#searchType").val()),
    			"selectTime":jQuery.trim($("#selectTimeQ option:selected").val()),
    			"orderType":jQuery.trim($("#orderTypeQ option:selected").val()),
    			"orderId":jQuery.trim($("#orderIdQ").val()),
    			"orderTimeBegin":jQuery.trim($("#orderTimeBeginQ").val()),
    			"orderTimeEnd":jQuery.trim($("#orderTimeEndQ").val()),
    			"payStyle":jQuery.trim($("#payStyleQ option:selected").val()),
    			"states":jQuery.trim($("#searchOrderState").val())
    		}
    	},
    	_buyAgain:function(orderId){
    		$.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "处理中，请等待...",
				url: _base+"/myorder/checkOrderProduct",
				data:{"orderId":orderId},
				success: function(data){
					if(data){
						window.location.href=_base+"/shopcart/buyAgain?orderId="+orderId;
					}else{
						var msgDialog = Dialog({
							title: '提示',
							content: "此订单下的商品已经下架或暂时无货，不能再次购买",
							ok: function () {
								this.close();
							}
						});
			        	msgDialog.show();
					}
				}
			});
    	}
    });
    
    module.exports = OrderListPager
});

