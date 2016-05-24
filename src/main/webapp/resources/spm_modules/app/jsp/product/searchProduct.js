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
            "click #BTN_SEARCH":"_searchBtnClick"
        },
    	//重写父类
    	setup: function () {
    		QueryProductPager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._searchBtnClick(1,QueryProductPager.DEFAULT_PAGE_SIZE);
    		this._getHotProduct();
    	},
    	_searchBtnClick: function(){
    		var data = $("#ba-form :input,#cg-Form select").serializeArray();
    		var _this = this;
    		var url = _base+"/search/getProduct";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : {},
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
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
    	
    	_getHotProduct:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/search/getHotProduct",
						data:'',
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
    	
    });
    
    module.exports = QueryProductPager
});

