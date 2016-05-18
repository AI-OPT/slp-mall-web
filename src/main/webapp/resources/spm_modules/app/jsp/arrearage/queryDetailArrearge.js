define('app/jsp/arrearage/queryDetailArrearge', function (require, exports, module) {
    'use strict';
	var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
	AjaxController=require('opt-ajax/1.0.0/index'),
	Validator = require('arale-validator/0.10.2/index'),
	Calendar = require('arale-calendar/1.1.2/index');

	require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require('opt-paging/aiopt.pagination'),

    require("jsviews/jsrender.min");
	require("jsviews/jsviews.min");
	require("app/util/jsviews-ext");

	//实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QueryArrearageDetailPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
             "click #BTN_BACK":"_detailBack",
             "click #BTN_EXPORT":"_exportToExcel"
        },
    	//重写父类
    	setup: function () {
    		QueryArrearageDetailPager.superclass.setup.call(this);
    		this._initPage();
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("查询管理","欠费查询");
      		//左侧菜单选中样式
      		$("#mnu_query_mng").addClass("current");
      		this._renderListData();
      	},
      	_renderListData:function(){
      		
      		ajaxController.ajax(
      		{
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/arrearage/searchDtailArrearage",
						data:{
							"custId":custId
						},
						success: function(data){
							if(data.data){
								var template = $.templates("#oweDetailListTmpl");
								var htmlOut = template.render(data.data);
								$("#showData").html(htmlOut);
							}
						}
					}
      		
      		);
      	},
    	_detailBack:function() {
    		window.location.href = _base + "/arrearage/toQueryArrearage";
    	},
    	//导出到excel
		_exportToExcel:function(){
			window.location.href = _base + '/arrearage/exportArrearageDetailToExcel?custId=' + custId;
		}
    });
    module.exports = QueryArrearageDetailPager
});
