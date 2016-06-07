define('app/jsp/account/balance_detail/balanceDetailList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    Calendar = require('arale-calendar/1.1.2/index'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");

    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var BalanceDetailListPager = Widget.extend({
    	
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
            //"click #BTN_SEARCH":"_search",
    		
    		//"click #search_button_id":"_queryParam",
    		//"click #pay_id":"_queryParam",
    		//"click #charge_id":"_queryParam",
    		//"click #all_id":"_queryParam"
        },
    	//重写父类
    	setup: function () {
    		BalanceDetailListPager.superclass.setup.call(this);
    		//
    		this._queryAccountBalanceDetailList();
    		this._queryParam();
    		this._bindCalendar();
    	},
    	//日期
    	_bindCalendar: function(){
    		new Calendar({
    			trigger: '#startDate',
    			output: '#startDate_be',
    			align: {
			      selfXY: [0, 0],
			      baseElement: '#startDate_be',
			      baseXY: [0, '100%']
        		}
    		});
    		new Calendar({
    			trigger: '#endDate',
    			output: '#endDate_be',
    			align: {
			      selfXY: [0, 0],
			      baseElement: '#endDate_be',
			      baseXY: [0, '100%']
        		}
    		});
    	},
    	_queryParam:function(){
    		var _this = this;
    		
    		
    		//
    		$("#search_button_id").click(function () {
				var objId = $(this).attr("id");
				var busiTypeValue = $('#busiType_id').val();
				if(busiTypeValue == '1'){
					$("#pay_id").click();
				}
				if(busiTypeValue == '2'){
					$("#charge_id").click();
				}
				if(busiTypeValue == ''){
					$("#all_id").click();
				}
				
			});
    		$("#pay_id").click(function () {
				var objId = $(this).attr("id");
				$('#busiType_id').val("1");
				_this._queryAccountBalanceDetailList(objId);
//				alert(objId);
			});
    		$("#charge_id").click(function () {
				var objId = $(this).attr("id");
				$('#busiType_id').val("2");
				_this._queryAccountBalanceDetailList(objId);
//				alert(objId);
			});
    		$("#all_id").click(function () {
				var objId = $(this).attr("id");
				$('#busiType_id').val("");
				_this._queryAccountBalanceDetailList(objId);
				//alert(objId);
			});
    		
    		//刷新页面的时候调用
    		var busiTypeValue = $('#busiType_id').val();
//    		alert('busiTypeValue:'+busiTypeValue);
			if(busiTypeValue == '1'){
				$("#pay_id").click();
				$("#pay_id").attr("class","current");
				$("#charge_id").attr("class","");
				$("#all_id").attr("class","");
				
			}
			if(busiTypeValue == '2'){
				$("#charge_id").click();
				$("#pay_id").attr("class","");
				$("#charge_id").attr("class","current");
				$("#all_id").attr("class","");
			}
			if(busiTypeValue == ''){
				$("#all_id").click();
				$("#pay_id").attr("class","");
				$("#charge_id").attr("class","");
				$("#all_id").attr("class","current");
			}
    	},
    	_queryAccountBalanceDetailList:function(objId){
    		//alert('objId'+objId);
    		$("#pagination").runnerPagination({
				url: _base+"/account/queryAccountBalanceDetailList",
				method: "POST",
				dataType: "json",
				processing: true,
				data : {"startTime":$('#startDate_be').val(),
					"endTime":$('#endDate_be').val(),
					"busiType":$('#busiType_id').val()
				},
				pageSize: 10,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data){
						var template = $.templates("#balanceSevenDaysAgoTmpl");
						var htmlOut = template.render(data);
						//alert(data.result);
						if(objId == undefined){
							$("#table_info_id_pay_id").html(htmlOut);
						}else{
							$("#table_info_id_"+objId).html(htmlOut);
						}
					}
					
					
				}
			});
      	}
      	
    	
    });
    
    module.exports = BalanceDetailListPager
});

