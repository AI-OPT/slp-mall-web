define('app/jsp/producthome/productHome', function (require, exports, module) {
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
    var ProductHomePager = Widget.extend({
    	
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
            //"click #BTN_SEARCH":"_searchBtnClick"
            //"click #thumbnailId":"_changeImage",
            "click #moreproduct":"_getMore"
        },
    	//重写父类
    	setup: function () {
    		ProductHomePager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._getPhoneProduct();
    		this._getFlowProduct();
    		this._getHotProduct();
    	},
    	_getPhoneProduct:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/getPhoneBill",
						data:'',
						success: function(data){
							if(data.data){
								var template = $.templates("#phoneBillTmpl");
								var htmlOut = template.render(data.data);
								$("#phoneBillData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	_getFlowProduct: function(){
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/getFlow",
				data:'',
				success: function(data){
					if(data.data){
						var template = $.templates("#flowTmpl");
						var htmlOut = template.render(data.data);
						$("#flowData").html(htmlOut);
					}
				}
			}
		);
      	},
      	_getMore: function(){
      		window.location.href = _base + '/search/list';
      	},
      	_getHotProduct: function(){
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/getHotProduct",
				data:'',
				success: function(data){
					if(data.data){
						var template = $.templates("#hotTmpl");
						var htmlOut = template.render(data.data);
						$("#hotData").html(htmlOut);
					}
				}
			}
		);
      	}
    	
    });
    
    module.exports = ProductHomePager
});

