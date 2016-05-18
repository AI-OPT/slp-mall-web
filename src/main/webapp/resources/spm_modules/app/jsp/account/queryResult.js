define('app/jsp/account/queryResult', function (require, exports, module) {
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
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
//    var ajaxController = new AjaxController();
    //定义页面组件类
    var QueryResultPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		
        },
    	//重写父类
    	setup: function () {
    		QueryResultPager.superclass.setup.call(this);
    		this._initPage();
    		//初始化执行搜索
    		this._searchBtnClick(1,QueryResultPager.DEFAULT_PAGE_SIZE);
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("结算配置管理","结算查询");
      		//左侧菜单选中样式
      		$("#mnu_smc_config").addClass("current");
      	},
    	_searchBtnClick: function(pageNo, pageSize){
    		var _this = this;
    		var url = _base+"/account/searchImportLogList";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : '',
	           	pageSize: QueryResultPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#accountListTemple");
    					var htmlOutput = template.render(data);
    					$("#accountData").html(htmlOutput);
	            	}else{
    					$("#accountData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	_bindEvents: function(){
    		var _this = this;
    		
    	},	
    });
    
    module.exports = QueryResultPager
});

