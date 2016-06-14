define('app/jsp/logonavmenu/logoNavMenu', function (require, exports, module) {
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
    var HeadPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//查询
            "click #logo_nav_phoneBillFastId":"_phoneBillfast",
            "click #logo_nav_flowFastId":"_flowFast"
        },
    	//重写父类
    	setup: function () {
    		HeadPager.superclass.setup.call(this);
    		//初始化执行搜索
    	},
    	_phoneBillfast: function(){
    		var isflowflag = false;
    		window.location.href = _base + '/head/fastCharge?flowFastFlag='+isflowflag;
    	},
    	_flowFast: function(){
    		var flag = true;
    		window.location.href = _base + '/head/fastCharge?flowFastFlag='+flag;
    	}
    	
    });
    
    module.exports = HeadPager
});

