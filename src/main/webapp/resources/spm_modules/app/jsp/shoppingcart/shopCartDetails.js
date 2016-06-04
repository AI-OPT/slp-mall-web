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
    
    // 实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    // 定义页面组件类
    var shopCartDetailsPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	// 属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    	},
    	// 事件代理
    	events: {
            // 全选
    		"click input[name='checkOne']":"_checkOne",
        },
    	// 重写父类
    	setup: function () {
    		shopCartDetailsPager.superclass.setup.call(this);
    		this._renderCartProdTemple();
    	},
    	// 渲染商品信息
    	_renderCartProdTemple:function(){
    		var template = $.templates("#cartProdTemple");
			var htmlOutput = template.render(cartProdList);
			$("#cartProdData").html(htmlOutput);
    	},
    	// 修改数量
    	_changeProdNum:function(prodId,prodNum,salePrice){
    		// 获取当前商品数量
			var proNum = $("#"+prodId+"_prodnum");
    		var oldNum = parseInt(proNum.val());
    		// 判断数量
    		if(oldNum<=1){
    			return;
    		}
    		// 重新赋值
    		oldNum+=prodNum;
			proNum.val(oldNum);
    		this._computedPrice(prodId,oldNum,salePrice);
    	},
    	//计算价格
    	_computedPrice:function(prodId,num,salePrice){
    		// 计算金额
    		var moneyLi = this._productNum(salePrice,num);
    		// 获取金额元素转成元
    		var money = this._liToYuan(moneyLi);
    		// 改变金额小计
    		var td = $("#"+prodId+"_prodPriceSubtotal");
    		alert(td);
    		td.html("￥"+money);
    		// 求和
    	},
    	 // 修改数量
        _modifyCartProdQty:function(prodId,btn,salePrice){
        	var qty = parseInt(btn.value);
        	if(!this._isPosNum(qty)){
				qty = 1;
        	}
			btn.value=qty;
			this._computedPrice(prodId,qty,salePrice);
        },
        // 是否为正整数
        _isPosNum:function(num){
        	var re = /^[1-9][0-9]*$/ ; 
        	return re.test(num) 
        },
        // 格式化金额
    	_fmoneyOf2:function (s, n) {
    		n = n > 0 && n <= 20 ? n : 2;
    		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    		var l = s.split(".")[0].split("").reverse(),
    		r = s.split(".")[1];
    		t = "";
    		for(i = 0; i < l.length; i ++ ){   
    			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    		}
    		return t.split("").reverse().join("") + "." + r;
    	},
    	// 删除单个
    	_delCartProd:function(btn){
    		btn.parentNode.parentNode.parentNode.parentNode.remove();
    		// 获取ID调用AJAX删除商品服务
    		// 求和
    	},
    	//金额转换（元->厘）
    	_yuanToLi: function(yuan){
    		 var result = '0';
    		 if(isNaN(yuan) || !yuan){
    			 return result;
    		 }
    		 return yuan*1000;
    	},
    	//金额转换（厘->元）
    	_liToYuan:function(li){
    		var result = '0.00';
    		if(isNaN(li) || !li){
    			return result;
    		}
            return this._fmoneyOf2(parseInt(li)/1000, 2);
    	},
    	// 全选
    	// 删除选中
    	_delPitchOn:function(){
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				// message: "查询中，请等待...",
				url: _base+"/search/getHotProduct",
				data:{areaCode:"81"},
				success: function(data){
					if(data.data){
						var template = $.templates("#hotProductListTmpl");
						var htmlOut = template.render(data.data);
						$("#hotProductData").html(htmlOut);
					}
				}
			});
    	},
    	
    	// 精确计算两数乘积
    	_productNum:function(arg1, arg2) {
    	    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    	    try {
    	        m += s1.split(".")[1].length;
    	    }
    	    catch (e) {
    	    }
    	    try {
    	        m += s2.split(".")[1].length;
    	    }
    	    catch (e) {
    	    }
    	    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    	},
    	
    	// 全选反选
    	_checkAll:function(btn){
    		if(btn.checked){
    		  $("input[name='checkAll']").prop("checked",true);
    		  $("input[name='checkOne']").prop("checked",true);
    		  
//    		  $("#chkAll").html(""); 
    		}else{
    		  $("input[name='checkAll']").prop("checked",false);
    		  $("input[name='checkOne']").prop("checked",false);
//    		  $("#chkAll").html("");
    		}
    		//求和
    	},
    	//单个选中或取消
    	_checkOne:function(){
    		alert(this);
    		if(this.checked){
    			
    		}else{
    			
    		}
    	}
    });
    
    module.exports = shopCartDetailsPager
});

