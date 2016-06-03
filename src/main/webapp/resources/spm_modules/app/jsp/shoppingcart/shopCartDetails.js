define('app/jsp/shoppingcart/shopCartDetails', function (require, exports, module) {
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
    var ProductDeatilPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	//事件代理
    	events: {
            //减少数量
//    		"click #delQtyBtn":"_delProductQty",
        },
    	//重写父类
    	setup: function () {
    		ProductDeatilPager.superclass.setup.call(this);
    		this._renderCartProdTemple();
//    		this._delQtyBtn(this);
    	},
    	//渲染商品信息
    	_renderCartProdTemple:function(){
    		var template = $.templates("#cartProdTemple");
			var htmlOutput = template.render(cartProdList);
			$("#shopCart").html(htmlOutput);
    	},
//    	//减少数量
//    	_delQtyBtn:function(this){
//    		var nowNum = btn.next().val();
//    		var oldNum = parseInt(nowNum);
//    		if(oldNum<=1){
//    			return;
//    		}
//    		var newNum = oldNum--;
//    		btn.next().val(newNum);
//    	},
//    	//增加数量
//    	_addQtyBtn:function(this){
//    		var oldNum = btn.prev().val();
//    		var newNum = oldNum++;
//    		btn.prev().val(newNum);
//    	}
    	
    });
    
    module.exports = ProductDeatilPager
});

