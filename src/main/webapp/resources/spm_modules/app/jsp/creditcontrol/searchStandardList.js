define('app/jsp/creditcontrol/searchStandardList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index');

    require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require('opt-paging/aiopt.pagination');
	
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var SearchStandardListPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		//"click [id='addBtn']":"_addStanderd",
        },
    	//重写父类
    	setup: function () {
    		SearchStandardListPager.superclass.setup.call(this);
    		this._initPage();
    		this._searchStanderdList();
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("信控配置管理","信控规则管理");
      		//左侧菜单选中样式
      		$("#mnu_omc_config").addClass("current");
      	},
    	//控制显示内容
		_controlMsgText: function(id,msg){
			var doc = document.getElementById(id+"");
			doc.innerText=msg;
		},
		//控制显隐属性 1:隐藏 2：显示
		_controlMsgAttr: function(id,flag){
			var doc = document.getElementById(id+"");
			if(flag == 1){
				doc.setAttribute("style","display:none");
			}else if(flag == 2){
				doc.setAttribute("style","display");
			}
		},
		//检查身份信息
    	_searchStanderdList:function(){
			$("#pageview").runnerPagination({
				url: _base+"/creditControl/standard/searchList",
				method: "POST",
				dataType: "json",
				processing: true,
				data : {},
				pageSize: SearchStandardListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#standardDataTmpl");
						var htmlOut = template.render(data);
						$("#standardData").html(htmlOut);
					}else{
						$("#standardData").html("未搜索到信息");
					}
				}
			});
		},
		_deleteStandard:function(ruleId){
			var _this = this;
			Dialog({
    	        title: '提示',
    	        content: '确定要删除该规则吗?',
    	        width: '300px',
    	        height: '60px',
    	        okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
					ajaxController.ajax({
						type : "POST",
						data : {"ruleId":ruleId},
						dataType: 'json',
						url :_base+"/creditControl/standard/deleteStandard",
						processing: true,
						message : "正在处理中，请稍候...",
						success : function(data) {
							var isSuccess = data.responseHeader.success;
							if(isSuccess){
								var msgDialog = Dialog({
					    	        title: '提示',
					    	        content: '删除成功',
					    	        ok: function () {
					    	        	this.close();
										_this._searchStanderdList();
					    	        }
								});
								msgDialog.showModal();
							}
						}
					});
    	        },
    	        cancelValue: '取消',
    	        cancel: function () {}
			}).show();
		}
		
    });
    module.exports = SearchStandardListPager
});
