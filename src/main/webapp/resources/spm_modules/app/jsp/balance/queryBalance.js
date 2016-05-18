define('app/jsp/balance/queryBalance', function (require, exports, module) {
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
    var QueryBalancePager = Widget.extend({
    	
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
            "click #BTN_SEARCH":"_searchBtnClick",
            "click .peiz-btn":"_exportBtnClick"
        },
    	//重写父类
    	setup: function () {
    		QueryBalancePager.superclass.setup.call(this);
    		this._bindEvents();
    		this._bindSelect();
    		this._initPage();
    		//初始化执行搜索
    		this._searchBtnClick(1,QueryBalancePager.DEFAULT_PAGE_SIZE);
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("查询管理","账户余额查询");
      		//左侧菜单选中样式
      		$("#mnu_query_mng").addClass("current");
      	},
    	_searchBtnClick: function(){
    		this._getQueryParams();
    		var data = $("#ba-form :input,#cg-Form select").serializeArray();
    		var _this = this;
    		var url = _base+"/balance/getBalanceList";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : data,
	           	pageSize: QueryBalancePager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#balanceListTemple");
    					var htmlOutput = template.render(data);
    					$("#balanceData").html(htmlOutput);
	            	}else{
    					$("#balanceData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	//获取查询参数
		_getQueryParams:function(){
			$('#custNameQ').val(jQuery.trim($("#custName").val()));
			$('#custGradeQ').val(jQuery.trim($("#custGradeQ").val()));
		},
    	//导出
    	_exportBtnClick:function(){
    		window.open(_base + '/balance/exportBalanceList?custName='+$('#custNameQ').val()+'&custGrade='+$('#custGradeQ').val());
    	},
    	// 下拉
		_bindSelect : function() {
			var this_=this;
				$.ajax({
					type : "post",
					processing : false,
					url : _base+ "/base/getSelect",
					dataType : "json",
					data : {
						paramType:"CUST_LEVEL"
						},
					message : "正在加载数据..",
					success : function(data) {
						var d=data.data.paramList;
						$.each(d,function(index,item){
							var paramName = d[index].paramName;
							var paramCode = d[index].paramCode;
							$("#custGrade").append('<option value="'+paramCode+'">'+paramName+'</option>');
						})
					}
				});

		},
    	_bindEvents: function(){
    		var _this = this;
    		
    	},	
    });
    
    module.exports = QueryBalancePager
});

