define('app/jsp/preferentialproduct/productView', function (require, exports, module) {
    'use strict';
    var $ = require('jquery'), 
	Widget = require('arale-widget/1.2.0/widget'), 
	Calendar = require('arale-calendar/1.1.2/index'), 
	Select = require('arale-select/0.11.1/index'), 
	Dialog = require("artDialog/src/dialog"),
	AjaxController = require('opt-ajax/1.0.0/index');
	require("jsviews/jsrender.min");
	require("jsviews/jsviews.min");
	require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require("opt-paging/aiopt.pagination");
	require("app/util/jsviews-ext");

    //定义页面组件类
    var ProductViewPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		 "click .BACK_BTN":"_goBack",
    		
        },
        _initPage: function(){
      		//面包屑导航
      		setBreadCrumb("优惠产品管理","优惠产品详情");
      		//左侧菜单选中样式
      		$("#mnu_bmc_config").addClass("current");
      	},
    	//重写父类
    	setup: function () {
    		ProductViewPager.superclass.setup.call(this);
    		this._getPageInfo();
    		this._bindUnitSelect();
    		this._bindActiveSelect();
    		this._bindCycleSelect();
    		this._initPage();
    	},
    	// 日期
		_bindCalendar : function() {
//			new Calendar({
//				trigger : '#pOnTime'
//			});


		},
		_goBack:function(){
			var this_=this;
			
			window.location.href=_base+"/preferentialProduct/list";
		},
		timestampToDate :function(format, timestamp){
			if(timestamp!=null){
				return (new Date(parseFloat(timestamp))).format(format);
			}else{
				return null;
			}
		},
		//满赠或者满减单位
    	_bindUnitSelect : function() {
			var this_=this;
			
				$.ajax({
					type : "post",
					processing : false,
					url : _base+ "/preferentialProduct/getSelect",
					dataType : "json",
					data : {
						paramType:"PRODUCT_UNIT"
						},
					message : "正在加载数据..",
					success : function(data) {
						var d=data.data.paramList;
						$.each(d,function(index,item){
							var paramName = d[index].paramName;
							var paramCode = d[index].paramCode;
							$("#pUnit").append('<option value="'+paramCode+'">'+paramName+'</option>');
							$("#mj_unit").append('<option value="'+paramCode+'">'+paramName+'</option>');

						})
						
							
					}
				});

		},
		//生效方式
    	_bindCycleSelect : function() {
			var this_=this;
			
				$.ajax({
					type : "post",
					processing : false,
					url : _base+ "/preferentialProduct/getSelect",
					dataType : "json",
					data : {
						paramType:"ACTIVE_CYCLE"
						},
					message : "正在加载数据..",
					success : function(data) {
						var d=data.data.paramList;
						$.each(d,function(index,item){
							var paramName = d[index].paramName;
							var paramCode = d[index].paramCode;
							$("#cycleUnit").append('<option value="'+paramCode+'">'+paramName+'</option>');
						})
						
							
					}
				});

		},
    	//生效方式
    	_bindActiveSelect : function() {
			var this_=this;
			
				$.ajax({
					type : "post",
					processing : false,
					url : _base+ "/preferentialProduct/getSelect",
					dataType : "json",
					data : {
						paramType:"ACTIVE_TYPE"
						},
					message : "正在加载数据..",
					success : function(data) {
						var d=data.data.paramList;
						$.each(d,function(index,item){
							var paramName = d[index].paramName;
							var paramCode = d[index].paramCode;
							$("#activeType").append('<option value="'+paramCode+'">'+paramName+'</option>');
						})
						
							
					}
				});

		},
		_getPageInfo:function(){
			var _this=this;
			var priceType=this.get('detailType');
			if(priceType==="dr_offer"){
				$("#MJ").hide();
				$("#MZ").show();
				$("#MZ").addClass("current");
				$("#MZ").click();
				
			}else{
				$("#MJ").addClass("current");
				$("#MZ").hide();
				$("#MJ").show();
				$("#MJ").click();
				
			}
			$.ajax({
				type : "post",
				processing : false,
				url : _base+ "/preferentialProduct/getProductDetail",
				dataType : "json",
				data : {
					productId:this.get("productId")
					},
				message : "正在加载数据..",
				success : function(data) {
						var d=data.data;
						if(priceType==="dr_offer"){
//							$("#MJ").hide();
//							$("#MZ").show();
//							$("#MZ").addClass("current");
							
							$("#activityName").val(d.programName);
						    $("#ruleAmount").val(d.ruleAmount);
						    $("#pUnit").val(d.ruleUnit);
						    $("#pUnit").attr("disabled",true);
						    $("#pOnTime").val(_this.timestampToDate('yyyy-MM-dd', d.activeDate));
						    $("#pOffTime").val(_this.timestampToDate('yyyy-MM-dd', d.invalidDate));
						    $("#comments").val(d.comments);
						    var list=d.preList;
						   $("#activeType").val(list[0].activeFlag);
						   $("#gOntime").val(_this.timestampToDate('yyyy-MM-dd', list[0].giftActiveDate));
						    $("#cycleUnit").val(list[0].activeCycle);
						    $("#activeType").attr("disabled",true);
						    $("#cycleUnit").attr("disabled",true);
						  
						    if(d.proList.length!=0){
						    	var template = $.templates("#listDataTmpl");
								var htmlOutput = template.render(d.proList);
								$("#listData").html(htmlOutput);
						    }
						    
						    if(list[0].giftProList.length!=0){
						    	
						    	var template = $.templates("#listDataTmpl1");
								var htmlOutput = template.render(list[0].giftProList);
								$("#listData1").html(htmlOutput);
						    }
						    
						    
						}else{
//							$("#MJ").addClass("current");
//							$("#MZ").css('display','none');
//							//removeClass
//							
//							$("#MJ").css('display','block');
//							$("#MJ").click();
							
							
							$("#programName").val(d.programName);
						    $("#rruleAmount").val(d.ruleAmount);
						    $("#mj_unit").val(d.ruleUnit);
						    $("#mj_reduceAmount").val(d.reduceAmount);
						    
						    
						    $("#mj_unit").attr("disabled",true);
						    $("#rActiveTime").val(_this.timestampToDate('yyyy-MM-dd', d.activeDate));
						    $("#rInvalidTime").val(_this.timestampToDate('yyyy-MM-dd', d.invalidDate));
						    $("#rcomments").val(d.comments);
						    if(d.proList.length!=0){
						    	
						    	var template = $.templates("#mj_table");
								var htmlOutput = template.render(d.proList);
								$("#rBody").html(htmlOutput);
						    }
							
						}
					
					
				}
			});
		}
		
    	//数据调用
    	
    });
    
    module.exports = ProductViewPager
});
