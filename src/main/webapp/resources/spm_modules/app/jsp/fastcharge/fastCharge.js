define('app/jsp/fastcharge/fastCharge', function (require, exports, module) {
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
    var FastchargePager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//查询
        },
    	//重写父类
    	setup: function () {
    		FastchargePager.superclass.setup.call(this);
    		//初始化
    		this._chagePage();
    	},
    	_chagePage: function(){
    		var isFlow = $("#flowFastFlag").val();
    		if(isFlow=="true"){
    			//$("#phoneBill")removeClass();
    			document.getElementById("phoneBill").className="";
    			$("#flowBill").addClass("current");
    		}
    		
    		if(isFlow=="false"){
    			//$("#flowBill")removeClass();
    			document.getElementById("flowBill").className="";
    			$("#phoneBill").addClass("current");
    		}
    	},
    	
    });
    
    module.exports = FastchargePager
});

