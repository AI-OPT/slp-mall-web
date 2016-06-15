define('app/jsp/pay/paySuccess', function (require, exports, module) {
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
    var PaySuccessPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
        },
    	//重写父类
    	setup: function () {
    		PaySuccessPager.superclass.setup.call(this);
    		this._timeoutGo();
    	},
    	_timeoutGo: function(){
    		var i = 10;
    		var myTimer = setInterval(function(){
    			$('#timeAlartSp').text(i);
    			i--;
    			if(i == -1){
        			clearInterval(myTimer);
        			window.location.href = _base+"/myorder/list";
        		}
    		},1000);
    	}
    });
    
    module.exports = PaySuccessPager
});

