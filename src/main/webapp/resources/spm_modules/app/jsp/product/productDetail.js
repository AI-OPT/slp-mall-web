define('app/jsp/product/productDetail', function (require, exports, module) {
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
            "click #delQtyBtn":"_delProductQty",
            //修改数量
            "change #productQty":"_modifyProductQty",
            //增加数量
            "click #addQtyBtn":"_addProductQty",
            //加入购物车
            "click #joinShopCart":"_joinShopCartClick"
        },
    	//重写父类
    	setup: function () {
    		ProductDeatilPager.superclass.setup.call(this);
    		this._renderProducSKUTemple();
    		this._renderImageBigTemple();
    		this._renderImageSmallTemple();
    		this._controlActiveDate();
    		this._controlBtn();
    		this._getHotProduct();
    		this._getProductConfigParameter();
    	},
    	//渲染大图
    	_renderImageBigTemple:function(){
    		var template = $.templates("#bigImageTemple");
			var htmlOutput = template.render(imageArrayList);
			$("#bigpicarea").html(htmlOutput);
    	},
    	//渲染小图
    	_renderImageSmallTemple:function(){
    		var template = $.templates("#smallImageTemple");
			var htmlOutput = template.render(imageArrayList);
			$("#smallImageData").html(htmlOutput);
    	},
    	//渲染商品基本信息
    	_renderProducSKUTemple:function(){
    		var template = $.templates("#producSKUTemple");
			var htmlOutput = template.render(producSKU);
			$("#producSKUData").html(htmlOutput);
    	},
    	//控制按钮
    	_controlBtn:function(){
    		if(producSKU.state == 1){
    			this._displayShowUI("buyBtnId");
    			this._displayShowUI("addCarBtnId");
    			this._displayHideUI("invalidBtnId");
    		}else{
    			this._displayHideUI("buyBtnId");
    			this._displayHideUI("addCarBtnId");
    			this._displayShowUI("invalidBtnId");
    		}
    	},
    	//控制有效期
    	_controlActiveDate:function(){
    		if(activeDateValue != null && activeDateValue != undefined && activeDateValue != ""){
    			this._displayShowUI("activeDateDiv");
    			$("#activeDate").text(activeDateValue);
    		}else{
    			this._displayHideUI("activeDateDiv");
    			$("#activeDate").text("");
    		}
    	},
    	//隐藏区域
    	_displayHideUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="none";  
    	},
    	//显示区域
    	_displayShowUI:function(id)  
    	{  
    	    var ui =document.getElementById(id);  
    	    ui.style.display="";//display为空的话会好使，为block会使后边的空间换行  
    	},
    	//减少数量
        _delProductQty:function(){
        	var qty = Number($("#productQty").val());
        	if(qty>1){
        		qty = qty - 1;
        	}
        	$("#productQty").val(qty);
        },
        //修改数量
        _modifyProductQty:function(){
        	var qty = Number($("#productQty").val());
        	if(!this._isPositiveNum(qty)){
        		$("#productQty").val(1);
        	}else{
        		$("#productQty").val(qty);
        	}
        },
        //增加数量
        _addProductQty:function(){
        	var qty = Number($("#productQty").val());
        	qty = qty + 1;
        	$("#productQty").val(qty);
        },
        //是否为正整数 
        _isPositiveNum:function(num){
        	var re = /^[1-9][0-9]*$/ ; 
        	return re.test(num) 
        },
        _getHotProduct:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						//message: "查询中，请等待...",
						url: _base+"/product/getHotProduct",
						data:'',
						success: function(data){
							if(data.data){
								var template = $.templates("#hotProductListTmpl");
								var htmlOut = template.render(data.data);
								$("#hotProductData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	_getProductConfigParameter:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						//message: "查询中，请等待...",
						url: _base+"/product/getProductConfigParameter",
						data:'',
						success: function(data){
							if(data.data){
								var template = $.templates("#configParameterTemple");
								var htmlOut = template.render(data.data);
								$("#configParameterData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	_getProductConfigParameter:function(){
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						//message: "查询中，请等待...",
						url: _base+"/product/getProductConfigParameter",
						data:'',
						success: function(data){
							if(data.data){
								var template = $.templates("#configParameterTemple");
								var htmlOut = template.render(data.data);
								$("#configParameterData").html(htmlOut);
							}
						}
					}
      		);
      	},
      	//加入购物车
    	_joinShopCartClick:function(){
    		var skuId = $("#skuId").val();
    		var buyNum = Number($("#productQty").val());
    		$.ajax({
    			type: "post",
    			url: _base+"/shopcart/addProd",
    			data: {"skuId":skuId,"buyNum":buyNum},
    			dataType: "json",
    			success: function(data){
    				if(data){
    					var prodNum = data.prodNum;
    					var prodTotal = data.prodTotal;
                        var d = Dialog({
                            content:"添加成功",
                            ok:function(){
                                this.close();
                            }
                        	d.show();
                        });
    				}
    			}
    		});
    	},
    });
    
    module.exports = ProductDeatilPager
});
