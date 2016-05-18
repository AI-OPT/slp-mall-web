define('app/jsp/billsearch/queryBillDetail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Calendar = require('arale-calendar/1.1.2/index-month'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index');

    require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require('opt-paging/aiopt.pagination');
	
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("app/util/jsviews-ext");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QueryBillDetailPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"click [id='exportBtn']":"_exportExcel"
        },
    	//重写父类
    	setup: function () {
    		QueryBillDetailPager.superclass.setup.call(this);
    		this._searchBillDetail();
    		this._initPage();
    	},
    	_initPage: function(){
         	//导航
    		setBreadCrumb("查询管理","账单查询");
      		//左侧菜单选中样式
      		$("#mnu_query_mng").addClass("current");
      	},
		//检查身份信息
		_searchBillDetail:function(){
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/search/bill/searchDetailInfo",
				dataType : "json",
				data : {
					"custId":custId,
					"queryTime":queryTime
				},
				message : "正在加载数据..",
				success : function(data) {
					if(data.data){
						var template = $.templates("#billDetailListTmpl");
						var htmlOut = template.render(data.data);
						$("#showData").html(htmlOut);
					}
				}
			});
		},
		//导出文件
		_exportExcel:function(){
			window.location.href=_base+'/search/bill/exportDetailExcel?custId='+custId+'&queryTime='+queryTime;
		}
		
    });
    module.exports = QueryBillDetailPager
});
