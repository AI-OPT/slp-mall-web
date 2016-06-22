define('app/jsp/top/top', function (require, exports, module) {
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
    var TopPager = Widget.extend({
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//查询
    		   //"click #BTN_SEARCH":"_searchBtnClick"
        },
    	//重写父类
    	setup: function () {
    		TopPager.superclass.setup.call(this);
    		this._initCity();
    		this._getCity();
    		this._topChange();
    	},
    	_topChange:function(){
    		  var st = 100;
    		    $('.city').mouseenter(function () {
    				$(this).children('a').addClass('b');
    				$(this).children('.city-hover').show(1);  
    		    })
    				$('.city').mouseleave(function () {
    				$(this).children('a').removeClass('b');
    				$(this).children('.city-hover').hide(1);  
    		  
    		    });	
    		    $('.use').mouseenter(function () {
    				$('.use a').addClass('b');
    				$('.use .use-hover').show(1);
    		    })
    				$(".use .use-hover").click(function () {
    		                $(this).hide(1);
    		            });
    					
    				$('.use').mouseleave(function () {
    		        $('.use .use-hover').hide(1);
    				 $('.use a').removeClass('b');
    		    });	
    				$('.kefu').mouseenter(function () {
    					$('.kefu a').addClass('b');
    					$('.kefu .kefu-hover').show(1);
    			    })
    					$(".kefu .kefu-hover").click(function () {
    			                $(this).hide(1);
    			            });
    						
    					$('.kefu').mouseleave(function () {
    			        $('.kefu .kefu-hover').hide(1);
    					 $('.kefu a').removeClass('b');
    			    });	
    	},
    	_initCity: function(){
    		var _this = this;
    		//从session获取数据
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/head/getSessionData",
				data:'',
				success: function(data){
					if(data.data.areaCode!=null && data.data.areaCode!="" && data.data.areaCode!=undefined){
						var code = data.data.areaCode;
						var name = data.data.areaName;
						$("#currentCity").attr("currentCityCode",code);
						$("#currentCity").attr("currentCityName",name);
			    		document.getElementById("currentCity").innerHTML=name+"<img id='imgId'>";
			    		var bigimg = document.getElementById("imgId");
			    		bigimg.src=_slpbase+"/images/open-a.png";
					}else{
						_this._getIpAddr();
					}
					
				}
			})
    	},
    	
    	_setSessionData: function(code,name){
    		//将值存入session
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/head/setSessionData",
				data:{code:code,name:name},
				success: function(data){
				}
			})
    	},
    	//获取ip所在地区的地址
    	_getIpAddr: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/head/getIpAddr",
				data : {name:remote_ip_info.province},
				success: function(data){
					if(data.data.areaCode!=null && data.data.areaCode!=""){
						//设置所在地区
						$("#currentCity").attr("currentCityCode",data.data.areaCode);
						$("#currentCity").attr("currentCityName",data.data.areaName);
			    		document.getElementById("currentCity").innerHTML=data.data.areaName+"<img id='imgId'>";
			    		var bigimg = document.getElementById("imgId");
			    		bigimg.src=_slpbase+"/images/open-a.png";
			    		_this._setSessionData(data.data.areaCode,data.data.areaName);
					}else{
						//设置所在地区
						$("#currentCity").attr("currentCityCode","11");
						$("#currentCity").attr("currentCityName","北京");
			    		document.getElementById("currentCity").innerHTML="北京";
			    		_this._setSessionData("11","北京");
					}
				}
			})
    	},
    	_changeCity : function() {
			$(".ATTS_BTN").bind(
				"click",
				function() {
					var _this = this;
					var cityCode = $(_this).attr('areaCodeId');
					var cityName = $(_this).attr('areaNameId');
					$("#currentCity").attr("currentCityCode",cityCode);
					$("#currentCity").attr("currentCityName",cityName);
		    		document.getElementById("currentCity").innerHTML=cityName+"<img id='imgId'>";
		    		var bigimg = document.getElementById("imgId");
		    		bigimg.src=_slpbase+"/images/open-a.png";
		    		//将值存入session
		    		ajaxController.ajax({
						type: "post",
						dataType: "json",
						url: _base+"/head/setSessionData",
						data:{code:cityCode,name:cityName},
						success: function(data){
							window.location.reload();
						}
					})
				})
		},

    	_getCity: function(){
    		var _this = this;
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/head/getArea",
				data:'',
				success: function(data){
					if(data.data){
						var template = $.templates("#cityTmpl");
						var htmlOut = template.render(data.data);
						$("#cityShowData").html(htmlOut);
						_this._changeCity();
					}
				}
			}
		);
      }
    	
    });
    
    module.exports = TopPager
});

