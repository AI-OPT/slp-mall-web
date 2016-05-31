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
            "click #moreproduct":"_getMore",
            "click #refresh":"_getHotProduct",
            "click #phoneBillCucc":"_getPhoneBill",
            "click #phoneBillCmcc":"_getPhoneBill",
            "click #phoneBillCtcc":"_getPhoneBill",
            "click #flowCtcc":"_getFlowProduct",
            "click #flowCtcc":"_getFlowProduct",
            "click #flowCucc":"_getFlowProduct"
            	
        },
    	//重写父类
    	setup: function () {
    		ProductHomePager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._getPhoneBill();
    		this._getFlowProduct();
    		this._getHotProduct();
    	},
    	_getPhoneBill:function(){
    		//类目
    		var oprator;
    		//获取运营商类目
      		var isCmcc = $("#phoneBillCmcc").attr("class");
      		var isCtcc = $("#phoneBillCtcc").attr("class");
      		var isCucc = $("#phoneBillCucc").attr("class");
      		if(isCmcc=="current"){
      			$("#phoneOprator").val($("#phoneBillCmcc").attr("opratorid"));
      		}else if(isCtcc){
      			$("#phoneOprator").val($("#phoneBillCtcc").attr("opratorid"));
      		}else if(isCucc){
      			$("#phoneOprator").val($("#phoneBillCucc").attr("opratorid"));
      		}
      		var	param={
					areaCode:"120000",  
					productCatId: "10000010010000",	   
					basicOrgIdIs:$("#phoneOprator").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: true,
						message: "查询中，请等待...",
						url: _base+"/getPhoneBill",
						data:param,
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
      		var _this=this;
      		//流量类目ID
      		var productId="10000010020000";
      		var oprator;
      		//获取运营商类目
          		var isCmcc = $("#flowCmcc").attr("class");
          		var isCtcc = $("#flowCtcc").attr("class");
          		var isCucc = $("#flowCucc").attr("class");
          		if(isCmcc=="current"){
          			$("#flowOprator").val($("#flowCmcc").attr("opratorid"));
          		}else if(isCtcc){
          			$("#flowOprator").val($("#flowCtcc").attr("opratorid"));
          		}else if(isCucc){
          			$("#flowOprator").val($("#flowCucc").attr("opratorid"));
          		}
          		var	param={
    					areaCode:"120000",  
    					productCatId: "10000010020000",	   
    					basicOrgIdIs:$("#flowOprator").val()
    				   };
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/getFlow",
				data:param,
				success: function(data){
					if(data.data){
						var template = $.templates("#flowTmpl");
						var htmlOut = template.render(data.data);
						$("#flowData").html(htmlOut);
					}
				}
			});
      	},
      	_getMore: function(){
      		window.location.href = _base + '/search/list';
      	},
      	_jumpToSearch: function(price,type){
      		var orgired ;
      		//获取运营商类目
      		var isCmcc = $("#cmccShowId").is(":visible");
      		var isCtcc = $("#ctccShowId").is(":visible");
      		var isCucc = $("#cuccShowId").is(":visible");
      		if(isCmcc){
      			orgired="CMCC";
      		}else if(isCtcc){
      			orgired="CTCC";
      		}else if(isCucc){
      			orgired="CUCC";
      		}
      		window.location.href = _base + '/search/list?price='+price+"&type="+type+"&isp="+orgired;
      	},
      	_getHotProduct: function(){
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "查询中，请等待...",
				url: _base+"/getHotProduct",
				data:{areaCode:"120000"},
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

