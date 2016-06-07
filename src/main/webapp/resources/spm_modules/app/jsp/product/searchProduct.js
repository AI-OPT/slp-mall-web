define('app/jsp/product/searchProduct', function (require, exports, module) {
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
    var QueryProductPager = Widget.extend({
    	
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
            "click #BTN_SEARCH":"_search",
            "click #moreId":"_more"
        },
    	//重写父类
    	setup: function () {
    		QueryProductPager.superclass.setup.call(this);
    		//初始化执行搜索
    		var sourceFlag = $("#sourceFlag").val();
    		var name = $("#skuName").val();
    		$("#serachName").val(name);
    		if(sourceFlag=="00"){
    			this._search();
    		}else{
    			this._searchBtnClick();
    		}
    		this._getHotProduct();
    	},
    	//搜索操作
    	_search: function(){
    		var	param={
					areaCode:"81",  
					skuName:$("#serachName").val()
				   };
    		var _this = this;
    		var url = _base+"/search/commonSearch";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		//获取公共数据
    					_this._getCommonBySearch();
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
	            	}else{
    					$("#productData").html("没有搜索到相关信息");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
					 
				},
    		});
    	},
    	//首页搜索跳转操作
    	_searchBtnClick: function(){
    		var	param={
					areaCode:"81",  
					productCatId: $("#billType").val(),
					basicOrgIdIs:"12",
					attrDefId:$("#priceId").val()
				   };
    		var _this = this;
    		var url = _base+"/search/getProduct";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					//获取公共数据
    					_this._getCommonProduct();
	            	}else{
    					$("#productData").html("没有搜索到相关信息");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
				},
    		});
    	},
		_more: function(){
			var isCmcc = $("#lastArea").is(":visible");
			if(isCmcc){
				$("#lastArea").attr("style","display:none");
			}else{
				$("#lastArea").attr("style","display:");
			}
			
		},
    	_getHotProduct:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getHotProduct",
						data:{areaCode:"81"},
						success: function(data){
							if(data.data){
								var template = $.templates("#hotProductListTmpl");
								var htmlOut = template.render(data.data);
								$("#hotProductData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	//搜索获取公共数据
      	_getCommonBySearch:function(){
      		var	param={
					areaCode:"81",  
					skuName:$("#serachName").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getCommonBySearch",
						data:param,
						pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
						success: function(data){
							var template = $.templates("#agentTmpl");
							var htmlOut = template.render(data.data);
							$("#agentData").html(htmlOut);
							var template1 = $.templates("#accountTmpl");
							var htmlOut1 = template1.render(data.data);
							$("#accountData").html(htmlOut1);
							var template2 = $.templates("#areaTmpl");
							var htmlOut2 = template2.render(data.data);
							$("#areaData").html(htmlOut2);
							var template3 = $.templates("#lastAreaTmpl");
							var htmlOut3 = template3.render(data.data);
							$("#lastAreaData").html(htmlOut3);
						}
					}
      		);
      	},
      	//获取公共数据
      	_getCommonProduct:function(){
      		var	param={
					areaCode:"81",  
					productCatId: $("#billType").val(),
					basicOrgIdIs:"12",
					attrDefId:$("#priceId").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getCommon",
						data:param,
						pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
						success: function(data){
							var template = $.templates("#agentTmpl");
							var htmlOut = template.render(data.data);
							$("#agentData").html(htmlOut);
							var template1 = $.templates("#accountTmpl");
							var htmlOut1 = template1.render(data.data);
							$("#accountData").html(htmlOut1);
							var template2 = $.templates("#areaTmpl");
							var htmlOut2 = template2.render(data.data);
							$("#areaData").html(htmlOut2);
							var template3 = $.templates("#lastAreaTmpl");
							var htmlOut3 = template3.render(data.data);
							$("#lastAreaData").html(htmlOut3);
							
						}
					}
      		);
      	}
    	
    });
    
    module.exports = QueryProductPager
});

