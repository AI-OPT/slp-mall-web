define('app/jsp/product/searchProduct', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    //require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var QueryProductPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 12
    	},
    	//事件代理
    	events: {
    		//查询
            "click #BTN_SEARCH":"_search",
            "click #moreId":"_more",
            "click #icon-rm":"_removeIcon",
            "click #icon-rm1":"_removePrice",
            "click #icon-rm2":"_removeAgent",
            "click #generayId":"_changeGeneral"
        },
    	//重写父类
    	setup: function () {
    		QueryProductPager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._getsessionData();
    		this._getDispatchCity();
    		
    		//获取运营商
    		var agent = $("#orgired").val();
    		if(agent==10){
    			this._flowCurrent(agent,"中国移动");
    		}else if(agent==11){
    			this._flowCurrent(agent,"中国电信");
    		}else if(agent==12){
    			this._flowCurrent(agent,"中国联通");
    		}
    		var name = $("#skuName").val();
    		$("#serachName").val(name);
    		this._hideArrow();
    	},
    	_getsessionData: function(){
    		var _this = this;
    		//从session获取数据
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: _base+"/head/getSessionData",
				data:'',
				success: function(data){
					if(data.data.areaCode!=null && data.data.areaCode!="" &&　data.data.areaCode!=undefined){
						_this._setArea(data.data.areaCode,data.data.areaName);
					}else{
						_this._setArea("11","北京");
					}
					var sourceFlag = $("#sourceFlag").val();
					if(sourceFlag=="00"){
		    			_this._search();
		    		}else{
		    			_this._searchBtnClick();
		    		}
					_this._getHotProduct();
				}
			})
    	},
    	//设置当前地区
    	_setArea:function(code,name){
    		 $("#currentCity").attr("currentCityCode",code);
    		$("#currentCity").attr("currentCityName",name);
    		//document.getElementById("currentCity").innerHTML=name;
    		//级联修改送货地区
    		$("#currentDispatch").attr("currentDispatchCode",code);
			$("#currentDispatch").attr("currentDispatchName",name);
    		//document.getElementById("currentDispatch").innerHTML=name;
    		$("#currentDispatch").text(name);
    	},
    	//搜索操作
    	_search: function(){
    		var _this = this;
    		var code =$("#currentCity").attr("currentCityCode");
    		//获取公共数据
			_this._getCommonBySearch();
    		var	param={
					areaCode:code,  
					skuName:$("#serachName").val()
				   };
    		var url = _base+"/search/commonSearch";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: false,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            resultImageType:"1",
	            messageId:"productData",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					//设置title
    					var type = $("#catType").val();
    					if(type=="10000010010000"){
    						//document.getElementById("typeTitleId").innerHTML="话费充值";
    						$("#typeTitleId").text("话费充值");
    					}else{
    						//document.getElementById("typeTitleId").innerHTML="流量充值";
    						$("#typeTitleId").text("流量充值");
    					}
    					$("#isHaveDataFlag").val("11");
	            	}else{
	            		//隐藏公共信息
	            		$("#commonId").attr("style","display: none");
	            		//$("#commonData").attr("style","display: none");
    					//$("#productData").html("抱歉没有找到相关商品，更换搜索词试一试吧");
    					$("#isHaveDataFlag").val("00");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
					 
				},
    		});
    	},
    	
    	//首页搜索跳转操作
    	_searchBtnClick: function(){
    		var _this = this;
    		//获取公共数据
			_this._getCommonProduct();
    		//设置title
			var type = $("#billType").val();
			if(type=="10000010010000"){
				//document.getElementById("typeTitleId").innerHTML="话费充值";
				$("#typeTitleId").html("话费充值");
			}else{
				$("#xsWord").html("流量:");
				//document.getElementById("xsWord").innerHTML="流量:";
				//document.getElementById("typeTitleId").innerHTML="流量充值";
				$("#typeTitleId").html("流量充值");
			}
			//获取所在地code
			var code =$("#currentCity").attr("currentCityCode");
			var name ="地域:"+$("#currentCity").attr("currentCityName");
			//document.getElementById("areaTile").innerHTML=name;
    		var	param={
					areaCode:code,  
					productCatId: $("#billType").val(),
					basicOrgIdIs: $("#orgired").val(),
					attrDefId:$("#priceId").val()
				   };
    		var url = _base+"/search/getProduct";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: false,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            resultImageType:"1",
	            messageId:"productData",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					//添加样式
    		    		var attrDefId = $("#priceId").val();
    		    		var basic = $("#orgired").val();
    		    		var newAgent = "#"+basic;
    		    		var newAttrDefId = "#"+attrDefId;
    		    		$(newAgent).addClass("current");
    		    		$(newAttrDefId).addClass("current");
    		    		$("#isHaveDataFlag").val("11");
	            	}else{
	            		$("#isHaveDataFlag").val("00");
	            		$("#commonId").attr("style","display: none");
	            		//$("#commonData").attr("style","display: none");
    					//$("#productData").html("抱歉没有找到相关商品，更换搜索词试一试吧");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
				},
    		});
    	},
    	//地区显示
		_more: function(){
			var isCmcc = $("#lastArea").is(":visible");
			if(isCmcc){
				$("#lastArea").attr("style","display:none");
			}else{
				$("#lastArea").attr("style","display:");
			}
			
		},
		//热门推荐
    	_getHotProduct:function(){
    		//获取所在地code
			var code =$("#currentCity").attr("currentCityCode");
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						message: "查询中，请等待...",
						url: _base+"/search/getHotProduct",
						data:{areaCode:code},
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
      	//搜索获取公共数据
      	_getCommonBySearch:function(){
      		var code =$("#currentCity").attr("currentCityCode");
      		var	param={
					areaCode:code,  
					skuName:$("#serachName").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						message: "查询中，请等待...",
						url: _base+"/search/getCommonBySearch",
						data:param,
						pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
						success: function(data){
							var template = $.templates("#agentTmpl");
							var htmlOut = template.render(data.data);
							$("#agentData").html(htmlOut);
							var template1 = $.templates("#accountTmpl");
							var htmlOut1 = template1.render(data.data);
							$("#accountData").html(htmlOut1);
							var template2 = $.templates("#areaTmpl");
							var htmlOut2 = template2.render(data.data);
							$("#areaData").html(htmlOut2);
							var template3 = $.templates("#lastAreaTmpl");
							var htmlOut3 = template3.render(data.data);
							$("#lastAreaData").html(htmlOut3);
						}
					}
      		);
      	},
      	//获取公共数据
      	_getCommonProduct:function(){
      		var code =$("#currentCity").attr("currentCityCode");
      		var	param={
					areaCode:code,  
					productCatId: $("#billType").val(),
					basicOrgIdIs:$("#orgired").val(),
					attrDefId:$("#priceId").val()
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						processing: false,
						message: "查询中，请等待...",
						url: _base+"/search/getCommon",
						data:param,
						pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
						success: function(data){
							var template = $.templates("#agentTmpl");
							var htmlOut = template.render(data.data);
							$("#agentData").html(htmlOut);
							var template1 = $.templates("#accountTmpl");
							var htmlOut1 = template1.render(data.data);
							$("#accountData").html(htmlOut1);
							var template2 = $.templates("#areaTmpl");
							var htmlOut2 = template2.render(data.data);
							$("#areaData").html(htmlOut2);
							var template3 = $.templates("#lastAreaTmpl");
							var htmlOut3 = template3.render(data.data);
							$("#lastAreaData").html(htmlOut3);
						}
					}
      		);
      	},
      //详情页面
    	_detailPage: function(skuId){
    		window.location.href = _base +'/product/detail?skuId='+skuId;
    	},
    	//流量更多按钮，代理商初始化选中
    	_flowCurrent: function(agentId,agentName){
    		var _this = this;
    		if($.trim(agentName).length==0){
    			//$("#areaTile").html("");
    		}else{
    			 var aname="<p id='agentV'></p><p id='agentN' class='close'>"+agentName+"<A href='javascript:void(0);'><i id='icon-rm2' class='icon-remove'></i></A></p>";
    	    		$("#areaTile").append(aname);
    		}
    		$("#agentSearch").val(agentId);
    		var newAgent="#"+agentId;
    		$(newAgent).addClass("current");
    	},
    	//改变运营商查询条件
    	_changeAgent: function(agentId,agentName){
    		var _this = this;
    		//删除原来样式
    		var oldAgent = $("#agentSearch").val();
    		var oldAgentId = "#"+oldAgent;
    		$(oldAgentId).removeClass("current");
    		if($.trim(agentName).length==0){
    			//$("#areaTile").html("");
    			$("#agentV").remove();
    			$("#agentN").remove();
    		}else{
    			$("#agentV").remove();
    			$("#agentN").remove();
    			 var aname="<p id='agentV'></p><p id='agentN' class='close'>"+agentName+"<A href='javascript:void(0);'><i id='icon-rm2' class='icon-remove'></i></A></p>";
    	    		$("#areaTile").append(aname);
    		}
    		//document.getElementById(oldAgent).className="";
    		$("#agentSearch").val(agentId);
    		var newAgent="#"+agentId;
    		$(newAgent).addClass("current");
    		_this._changeDataClick();
    		
    	},
    	_removeAgent:function(){
    		var _this=this;
            $("#agentData p").removeClass("current");
            _this._changeAgent("","");
    	},
    	//改变面额
    	_changePrice: function(priceId,priceName){
    		var _this = this;
    		//删除原来样式
    		var oldPrice = $("#priceSearch").val();
    		var oldPriceId = "#"+oldPrice;
    		$(oldPriceId).removeClass("current");
    		if($.trim(priceName).length==0){
    			//$("#areaTile").html("");
    			$("#priceV").remove();
    			$("#priceN").remove();
    		}else{
    			$("#priceV").remove();
    			$("#priceN").remove();
    			var aname="<p id='priceV'></p><p id='priceN' class='close'>"+priceName+"<A href='javascript:void(0);'><i id='icon-rm1' class='icon-remove'></i></A></p>";
    	    	$("#areaTile").append(aname);
    		}
    		
    		$("#priceSearch").val(priceId);
    		var newPrice=  "#"+priceId;
    		$(newPrice).addClass("current");
    		_this._changeDataClick();
    	},
    	_removePrice:function(){
    		var _this=this;
    		
            $("#accountData p").removeClass("current");
            _this._changePrice("","");
    	},
    	//改变地区
    	_changeArea: function(areaId,areaName){
    		var _this = this;
    		//删除原来样式
    		var oldArea = $("#areaSearch").val();
    		var oldAreaId = "#"+oldArea;
    		$(oldAreaId).removeClass("current");
    		//document.getElementById(oldArea).className="";
    		if($.trim(areaName).length==0){
    			//$("#areaTile").html("");
    			$("#area1").remove();
    			$("#aname").remove();
    			
    		}else{
    			 $("#area1").remove();
 				$("#aname").remove();
    			 var aname="<p id='area1'></p><p id='aname' class='close'>"+areaName+"<A href='javascript:void(0);'><i id='icon-rm' class='icon-remove'></i></A></p>";
    	    		$("#areaTile").append(aname);
    		}
    		
    		
    		$("#areaSearch").val(areaId);
    		var newArea=  "#"+areaId;
    		$(newArea).addClass("current");
    		_this._changeDataClick();
    	},
    	_removeIcon:function(){
    		var _this=this;
            $("#areaData p").removeClass("current");
            _this._changeArea("","");
    	},
    	 _changeDispath : function() {
    		 var _wthis = this;
 			$(".DSP_BTN").bind(
 				"click",
 				function() {
 					var _this = this;
 					var cityCode = $(_this).attr('areaCodeId');
 					var cityName = $(_this).attr('areaNameId');
 					$("#currentDispatch").attr("currentDispatchCode",cityCode);
 					$("#currentDispatch").attr("currentDispatchName",cityName);
 		    		//document.getElementById("currentDispatch").innerHTML=cityName;
 		    		$("#currentDispatch").text(cityName);
 		    		_wthis._changeDataClick();
 				})
 		},
    	//根据选择条件进行查询
    	_changeDataClick: function(){
    		var _this = this;
    		//获取公共数据
			//_this._getCommonBySearch();
    		//如果首页跳转的查询条件为首页传入参数，如果是搜索页面，使用默认查询条件
    		var sourceFlag = $("#sourceFlag").val();
    			var	productCatId = $("#catType").val();
    			if(productCatId==null || productCatId==""){
    				//var title =document.getElementById('typeTitleId').innerText
    				var title = $("#typeTitleId").text();
    				if(title=="话费充值"){
    					var	productCatId="10000010010000"
    				}else{
    					var	productCatId="10000010020000"
    				}
    			}
    			var priceId = $("#priceSearch").val();
    			var orgired = $("#agentSearch").val();
    		var disapatch = $("#currentDispatch").attr("currentDispatchCode");
    		if(disapatch=="" || disapatch==null){
    			var disapatch="11";
    		}else{
    			var disapatch= $("#currentDispatch").attr("currentDispatchCode");
    		}
    		var areaCode = $("#areaSearch").val();
    		var areaCodeSerch = $("#areaSearch").val();
			var priceSearch = $("#priceSearch").val();
			var orgiredSearch = $("#agentSearch").val();
    		if(orgiredSearch=="" && priceSearch=="" && areaCodeSerch==""){
    			var url=_base+"/search/commonSearch";
    		}else{
    			var url=_base+"/search/getProduct";
    		}
    		var	param={
					areaCode:disapatch,
					skuName:$("#serachName").val(),
					productCatId: productCatId,
					basicOrgIdIs: orgired,
					attrDefId:priceId,
					priceOrderFlag:$("#priceOrder").attr("value"),
					saleNumOrderFlag:$("#saleOrder").attr("value"),
					distributionArea:areaCode
				   };
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: false,
	            data : param,
	           	pageSize: QueryProductPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            resultImageType:"1",
	            messageId:"productData",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#productListTemple");
    					var htmlOutput = template.render(data);
    					$("#productData").html(htmlOutput);
    					$("#isHaveDataFlag").val("11");
	            	}else{
	            		//$("#commonId").attr("style","display: none");
	            		//$("#commonData").attr("style","display: none");
	            		$("#isHaveDataFlag").val("00");
    					//$("#productData").html("抱歉没有找到相关商品，更换搜索词试一试吧");
	            	}
	            },
	            callback: function(data){
					 $("#totalcount").text(data.count);
					 $("#pageno").text(data.pageNo);
					 $("#pagecount").text(data.pageCount);
				},
    		});
    	},
    	//获取配送地区
    	_getDispatchCity: function(){
    		var _this = this;
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "查询中，请等待...",
				url: _base+"/head/getArea",
				data:'',
				success: function(data){
					if(data.data){
						var template = $.templates("#dispatchCityTmpl");
						var htmlOut = template.render(data.data);
						$("#dispatchCityShowData").html(htmlOut);
						_this._changeDispath();
					}
				}
			}
		);
      },
     
    	//点击销量触发的事件
		_changeSaleOrder: function(){
			var _this = this;
			//删除综合排序
			$("#zhOrderId").removeClass("color");
			$("#saleNumX").attr("style","display:");
			//隐藏价格图标
			$("#priceX").attr("style","display:none");
			$("#priceS").attr("style","display:none");
			var flag = $("#isHaveDataFlag").val();
			if(flag=="00"){
				return;
			}else{
				$("#priceOrder").attr("value","");
				$("#saleOrder").attr("value","ASC");
				_this._changeDataClick();
			}
			
		 },
		 //初始化隐藏价格销量图标
		 _hideArrow: function(){
			 $("#saleNumX").attr("style","display:none");
			 $("#priceX").attr("style","display:none");
			 
		 },
		 _changeGeneral: function(){
			 var _this = this;
			 //综合排序
			 $("#zhOrderId").addClass("color");
			 //隐藏销量、价格图标
			 this._hideArrow();
			 //删除价格、销量排序取值
			 $("#priceOrder").attr("value","");
			 $("#saleOrder").attr("value","");
			 _this._changeDataClick();
		 },
		//点击价格排序触发事件
		_changePriceOrder: function(){
			var _this = this;
			//删除综合排序
			$("#zhOrderId").removeClass("color");
			//展示价格图标
			$("#priceX").attr("style","display:");
			$("#priceS").attr("style","display:");
			//隐藏销量图标
			$("#saleNumX").attr("style","display:none");
			var flag = $("#isHaveDataFlag").val();
			if(flag=="00"){
				return;
			}else{
				$("#saleOrder").attr("value","");
				var isSprice = $("#spriceId").is(":visible");
				if(isSprice){
					//切换升序、降序图标
					$("#xpriceId").attr("style","display:");
					 $("#priceX").attr("style","display:");
					$("#spriceId").attr("style","display:none");
					$("#priceOrder").attr("value","DESC");
				}else{
					//切换升序、降序图标
					 $("#spriceId").attr("style","display:");
					 $("#xpriceId").attr("style","display:none");
					$("#priceOrder").attr("value","ASC");
				}
				_this._changeDataClick();
			}
		 }
    });
    
    module.exports = QueryProductPager
});

