define('app/jsp/preferentialproduct/editProduct', function (require, exports, module) {
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
		 var productList=[];
		 var presentList=[];
		 var mjproductList=[];
	    //定义页面组件类
	    var EditProductPager = Widget.extend({
	    	//属性，使用时由类的构造函数传入
	    	attrs: {
	    	},
	    	Statics: {
	    		DEFAULT_PAGE_SIZE: 5
	    	},
	    	_initPage: function(){
	      		//面包屑导航
	      		setBreadCrumb("优惠产品管理","优惠产品修改");
	      		//左侧菜单选中样式
	      		$("#mnu_bmc_config").addClass("current");
	      	},
	    	//事件代理
	    	events: {
	    		//key的格式: 事件+空格+对象选择器;value:事件方法
	    		 "click #SAVEP_BTN":"_updateMZClick",
	    		 "click #MJ_SAVE_BTN":"_updateMjClick",
	    		 "click #PP_BTN":"_searchBtnClick",
	             "click #MJ_PP_BTN":"_searchmjProductsClick",
	             "click #pp_checkAll":"_ppcheckall",//产品全选
	    		 "click #gp_checkAll":"_gpcheckall",  //赠品产品全选
	    		 "click #mj_checkAll":"_mjcheckall",
	    		 "click .BACK_BTN":"_goBack",
	    		 "click #MZ_GP_BTN":"_searchGiftClick",
	    		 
	    		 
	    		//校验数据
	    		 "blur [id='activityName']":"_checkName",
	    		 "blur [id='ruleAmount']":"_checkRule",
	    		 "blur [id='pUnit']":"_checkpUnit",
	    		 "blur [id='pOnTime']":"_checkPOntime",
	    		 "blur [id='pOffTime']":"_checkpOffTime",
	    		 "blur [id='activeType']":"_checkactiveType",
	    		 "blur [id='cycleUnit']":"_checkcycleUnit",
	    		 "blur [id='comments']":"_checkComments",
	    		 //满减
	    		 "blur [id='programName']":"_checkprogramName",
	    		 "blur [id='rruleAmount']":"_checkrruleAmount",
	    		 "blur [id='mj_unit']":"_checkmjunit",
	    		 "blur [id='mj_reduceAmount']":"_checkmjreduceAmount",
	    		 "blur [id='rActiveTime']":"_checkrActiveTime",
	    		 "blur [id='rInvalidTime']":"_checkrInvalidTime",
	    		 "blur [id='rcomments']":"_checkrComments",
	        },
	    	//重写父类
	    	setup: function () {
	    		EditProductPager.superclass.setup.call(this);
	    		this._getPageInfo();
	    		this._bindUnitSelect();
	    		this._bindServiceSelect();
	    		this._bindActiveSelect();
	    		this._bindCycleSelect();
	    		this._bindCalendar();
	    		this._searchProducts(1,EditProductPager.DEFAULT_PAGE_SIZE);
	    		this._searchmjProducts(1,EditProductPager.DEFAULT_PAGE_SIZE);
	    		this._searchProducts1(1,EditProductPager.DEFAULT_PAGE_SIZE);
	    		 this._initPage();
	    	},
	    	// 日期
			_bindCalendar : function() {
				new Calendar({
					trigger : '#pOnTime'
				});
				new Calendar({
					trigger : '#pOffTime'
				});
				new Calendar({
					trigger : '#gOntime'
				});
				new Calendar({
					trigger : '#rActiveTime'
				});
				new Calendar({
					trigger : '#rInvalidTime'
				});


			},
			 _checkrComments:function(){
		    		var ruleName = jQuery.trim($("#rcomments").val());
		    		if(ruleName != "" && ruleName!= null && ruleName != undefined&& ruleName.replace(/[^\x00-\xff]/g,"aaa").length>1024){
		    			this._controlMsgText("rdescriptionMsg","备注长度不能大于1024");
		    			this._controlMsgAttr("rdescriptionMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("rdescriptionMsg","");
		    			this._controlMsgAttr("rdescriptionMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkComments:function(){
		    		var ruleName = jQuery.trim($("#comments").val());
		    		if(ruleName != "" && ruleName!= null && ruleName != undefined&& ruleName.replace(/[^\x00-\xff]/g,"aaa").length>1024){
		    			this._controlMsgText("descriptionMsg","备注长度不能大于1024");
		    			this._controlMsgAttr("descriptionMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("descriptionMsg","");
		    			this._controlMsgAttr("descriptionMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
			 _checkrInvalidTime:function(){
		    		var ruleName = jQuery.trim($("#rActiveTime").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("rInvalidTimeMsg","失效日期不能为空");
		    			this._controlMsgAttr("rInvalidTimeMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("rInvalidTimeMsg","");
		    			this._controlMsgAttr("rInvalidTimeMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkrActiveTime:function(){
		    		var ruleName = jQuery.trim($("#rActiveTime").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("rActiveTimeMsg","生效日期不能为空");
		    			this._controlMsgAttr("rActiveTimeMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("rActiveTimeMsg","");
		    			this._controlMsgAttr("rActiveTimeMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkmjreduceAmount:function(){
		    		var ruleName = jQuery.trim($("#mj_reduceAmount").val());
		    		 var reg = /^\d+(\.\d+)?$/;
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("mjRuleMsg","优惠金额不能为空");
		    			this._controlMsgAttr("mjRuleMsgDiv",2);
		    			return false;
		    		}else if(!reg.test(ruleName)){
		    			this._controlMsgText("mjRuleMsg","优惠金额为非负数");
		    			this._controlMsgAttr("mjRuleMsgDiv",2);
		    			return false;
		    		}else{
		    		
		    			this._controlMsgText("mjRuleMsg","");
		    			this._controlMsgAttr("mjRuleMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkmjunit:function(){
		    		var ruleName = jQuery.trim($("#mj_unit option:selected").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("mjRuleMsg","单位不能为空");
		    			this._controlMsgAttr("mjRuleMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("mjRuleMsg","");
		    			this._controlMsgAttr("mjRuleMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkrruleAmount:function(){
		    		var ruleName = jQuery.trim($("#rruleAmount").val());
		    		 var reg = /^\d+(\.\d+)?$/;
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("mjRuleMsg","满减金额不能为空");
		    			this._controlMsgAttr("mjRuleMsgDiv",2);
		    			return false;
		    		}else if(!reg.test(ruleName)){
		    			this._controlMsgText("mjRuleMsg","满减金额为非负数");
		    			this._controlMsgAttr("mjRuleMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("mjRuleMsg","");
		    			this._controlMsgAttr("mjRuleMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkprogramName:function(){
		    		var ruleName = jQuery.trim($("#programName").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("programNameMsg","活动名称不能为空");
		    			this._controlMsgAttr("programNameMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("programNameMsg","");
		    			this._controlMsgAttr("programNameMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
			  _checkcycleUnit:function(){
		    		var ruleName = jQuery.trim($("#cycleUnit option:selected").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("cycleUnitMsg","业务周期不能为空");
		    			this._controlMsgAttr("cycleUnitMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("cycleUnitMsg","");
		    			this._controlMsgAttr("cycleUnitMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkactiveType:function(){
		    		var ruleName = jQuery.trim($("#activeType option:selected").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("activeTypeMsg","生效方式不能为空");
		    			this._controlMsgAttr("activeTypeMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("activeTypeMsg","");
		    			this._controlMsgAttr("activeTypeMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		        _checkpUnit:function(){
		    		var ruleName = jQuery.trim($("#pUnit option:selected").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("ruleMsg","满减单位不能为空");
		    			this._controlMsgAttr("ruleMsgDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("ruleMsg","");
		    			this._controlMsgAttr("ruleMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
		    	_checkpOffTime:function(){
		     		var ruleName = jQuery.trim($("#pOffTime").val());
		     		if(ruleName == "" || ruleName == null || ruleName == undefined){
		     			this._controlMsgText("pOffTimeMsg","失效日期不能为空");
		     			this._controlMsgAttr("pOffTimeDiv",2);
		     			return false;
		     		}else{
		     			this._controlMsgText("pOffTimeMsg","");
		     			this._controlMsgAttr("pOffTimeDiv",1);
		     			return true;
		     		}
		     		
		     	},
		        _checkPOntime:function(){
		    		var ruleName = jQuery.trim($("#pOnTime").val());
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("pOnTimeMsg","生效时间不能为空");
		    			this._controlMsgAttr("pOnTimeDiv",2);
		    			return false;
		    		}else{
		    			this._controlMsgText("pOnTimeMsg","");
		    			this._controlMsgAttr("pOnTimeDiv",1);
		    			return true;
		    		}
		    		
		    	},
		    	_checkRule:function(){
		    		var ruleName = jQuery.trim($("#ruleAmount").val());
		    		 var reg = /^\d+(\.\d+)?$/;
		    		if(ruleName == "" || ruleName == null || ruleName == undefined){
		    			this._controlMsgText("ruleMsg","满减金额不能为空");
		    			this._controlMsgAttr("ruleMsgDiv",2);
		    			return false;
		    		}else if(!reg.test(ruleName)){
		    			this._controlMsgText("ruleMsg","满减金额为非负数");
		    			this._controlMsgAttr("ruleMsgDiv",2);
		    			return false;
		    		}
		    		else{
		    			this._controlMsgText("ruleMsg","");
		    			this._controlMsgAttr("ruleMsgDiv",1);
		    			return true;
		    		}
		    		
		    	},
	      _checkName:function(){
	    		var ruleName = jQuery.trim($("#activityName").val());
	    		if(ruleName == "" || ruleName == null || ruleName == undefined){
	    			this._controlMsgText("ruleNameMsg","活动名称不能为空");
	    			this._controlMsgAttr("ruleNameMsgDiv",2);
	    			return false;
	    		}else{
	    			this._controlMsgText("ruleNameMsg","");
	    			this._controlMsgAttr("ruleNameMsgDiv",1);
	    			return true;
	    		}
	    		
	    	},
	    	//控制显示内容
			_controlMsgText: function(id,msg){
				var doc = document.getElementById(id+"");
				doc.innerText=msg;
			},
			//控制显隐属性 1:隐藏 2：显示
			_controlMsgAttr: function(id,flag){
				var doc = document.getElementById(id+"");
				if(flag == 1){
					doc.setAttribute("style","display:none");
				}else if(flag == 2){
					doc.setAttribute("style","display");
				}
			},
			_searchmjProductsClick:function(){
				this._searchmjProducts(1,EditProductPager.DEFAULT_PAGE_SIZE);
			},
			_searchBtnClick: function(){
	    		this._searchProducts(1,EditProductPager.DEFAULT_PAGE_SIZE);	
	    	},
	    	_searchGiftClick:function(){
				this._searchGiftProducts(1,EditProductPager.DEFAULT_PAGE_SIZE);
			},
			timestampToDate :function(format, timestamp){
				if(timestamp!=null){
					return (new Date(parseFloat(timestamp))).format(format);
				}else{
					return null;
				}
			},
			_goBack:function(){
				var this_=this;
				
				window.location.href=_base+"/preferentialProduct/list";
			},
			 _searchGiftProducts: function(pageNo,pageSize){
		      		
		      		//入参
		  			var productId = $("#pId").val();
		  			var productName = $("#pName").val();
		  			var serviceType = $("#sType").val();
		  			var _this = this;
		  			var url = _base+ "/preferentialProduct/getProductList";
		  			$("#pagination-ul1").runnerPagination({
		  								url : url,
		  								method : "POST",
		  								dataType : "json",
		  								processing : true,
		  								data : {
		  									productId:$.trim(productId),
		  									productName:$.trim(productName),
		  									serviceType:serviceType
		  								},
		  								pageSize : EditProductPager.DEFAULT_PAGE_SIZE,
		  								visiblePages : EditProductPager.DEFAULT_PAGE_SIZE,
		  								message : "正在为您查询数据..",
		  								render : function(data) {
		  									if (data != null&& data != 'undefined'&& data.length > 0) {
		  										var template = $.templates("#listDataTmpl1");
		  										var htmlOutput = template.render(data);
		  										$("#listData1").html(htmlOutput);
		  										_this._bindmjProductClick();
		  									} else {
		  										$("#listData1").html(null);
		  										$("#listData1").html("没有搜索到相关信息");
		  									}
		  										
		  									}
		  								
		  							});
		  			
		      	},
			_updateMjClick : function() {
				var this_=this;
				var productType="dr_minus";
				var programName=$("#programName").val();
				var ruleAmount=$("#rruleAmount").val();
				var ruleUnit=$("#mj_unit").val();
				var reduceAmount=$("#mj_reduceAmount").val();
				var activeDate=$("#rActiveTime").val();
				var invalidDate=$("#rInvalidTime").val();
				var comments=$("#rcomments").val();
				
				if(!this_._checkprogramName()){
					return;
				}
				
				
				if(!this_._checkrruleAmount()){
					return;
				}
				if(!this_._checkmjunit()){
					return;
				}
				if(!this_._checkmjreduceAmount()){
					return;
				}
				if(!this_._checkrActiveTime()){
					return;
				}
				if(!this_._checkrInvalidTime()){
					return;
				}
				 if(!this_._checkrComments()){
					 	return;
					 }
					$.ajax({
						type : "post",
						processing : false,
						url : _base+ "/preferentialProduct/editProduct",
						dataType : "json",
						data : {
							productId:this.get('productId'),
							productType:productType,
							programName:programName,
							ruleAmount:ruleAmount,
							ruleUnit:ruleUnit,
							activeDate:activeDate,
							invalidDate:invalidDate,
							comments:comments,
							reduceAmount:reduceAmount,
							productList:JSON.stringify(mjproductList).replace('[', '').replace(']', ''),
							priceCode:this.get('priceCode')
							},
						message : "正在加载数据..",
						success : function(data) {
							Dialog({
								title : '提示',
								width : '200px',
								height : '50px',
								content : data.statusInfo,
								okValue: "确定",
								ok:function(){
									this.close();
									this_._goBack();
								}
							}).showModal();
						}
					});

			},
			_updateMZClick : function() {
				var this_=this;
				var productType="dr_offer";
				var programName=$("#activityName").val();
				var ruleAmount=$("#ruleAmount").val();
				var ruleUnit=$("#pUnit").val();
				var activeDate=$("#pOnTime").val();
				var invalidDate=$("#pOffTime").val();
				var comments=$("#comments").val();
				
				var activeFlag=$("#activeType").val();
				var pactiveTime=$("#gOntime").val();
				var activeCycle=$("#cycleUnit").val();
				if(!this_._checkName()){
					return;
				}
				if(!this_._checkRule()){
					return;
				}
			  if(!this_._checkpUnit()){
				return;
		    	}
		    	if(!this_._checkPOntime()){
				return;
			 }
			 if(!this_._checkpOffTime()){
			 	return;
			 }
			 if(!this_._checkComments()){
				 	return;
				 }
					$.ajax({
						type : "post",
						processing : false,
						url : _base+ "/preferentialProduct/editProduct",
						dataType : "json",
						data : {
							productId:this.get('productId'),
							productType:productType,
							programName:programName,
							ruleAmount:ruleAmount,
							ruleUnit:ruleUnit,
							activeDate:activeDate,
							invalidDate:invalidDate,
							comments:comments,
							productList:JSON.stringify(productList).replace('[', '').replace(']', ''),
							presentList:JSON.stringify(presentList).replace('[', '').replace(']', ''),
							pactiveTime:pactiveTime,
							activeCycle:activeCycle,
							activeFlag:activeFlag,
							priceCode:this.get('priceCode')
							},
						message : "正在加载数据..",
						success : function(data) {
							Dialog({
								title : '提示',
								width : '200px',
								height : '50px',
								content : data.statusInfo,
								okValue: "确定",
								ok:function(){
									this.close();
									this_._goBack();
								}
							}).showModal();
						}
					});

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
			//产品全选
	    	_ppcheckall: function() {
	    		var _this=this; 
				var chkflag=$("#pp_checkAll").prop("checked");
				$('input[type=checkbox][name=pp_checkbox]').prop("checked",chkflag);
				  _this._bindProductClick();
	         },
	         _gpcheckall: function() {
	        	 var _this=this; 
	 			var chkflag=$("#gp_checkAll").prop("checked");
	 			$('input[type=checkbox][name=gp_checkbox]').prop("checked",chkflag);
	 			 _this._bindProductClick1();
	          },
	          _mjcheckall: function() {
	         	 var _this=this; 
	  			var chkflag=$("#mj_checkAll").prop("checked");
	  			$('input[type=checkbox][name=mj_checkbox]').prop("checked",chkflag);
	  			 _this._bindmjProductClick();
	           },
	           _bindmjProductClick: function() {
	          	 
	          	 var _this = this;
	          	 $('input[type=checkbox][name=mj_checkbox]').bind('change', function(){
	          		
	       			var checkFlag = $(this).prop("checked");//true选中，false取消
	       			if(checkFlag){
	       				//展示所选的产品
	       				
	       				var trHtml = $(this).parent().parent('tr').clone();
	       				trHtml.children().first().remove();//删除第一个选择框
	       				trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
	       				var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
	       				tableHtml.append(trHtml);
	       				$('#mj_products').append(tableHtml);
	       				mjproductList.push($.trim($(this).parent().parent('tr').children('td').get(1).innerHTML));
	       				
	       				//绑定方法：删除选中的产品
	       				_this._bindmjProductRemove();
	       			}
	          	 });
	           },
	       //产品绑定复选框选中事件
	         _bindProductClick: function() {
	        	 
	        	 var _this = this;
	        	 $('input[type=checkbox][name=pp_checkbox]').bind('change', function(){
	        		
	     			var checkFlag = $(this).prop("checked");//true选中，false取消
	     			if(checkFlag){
	     				//展示所选的产品
	     				
	     				var trHtml = $(this).parent().parent('tr').clone();
	     				trHtml.children().first().remove();//删除第一个选择框
	     				trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
	     				var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
	     				tableHtml.append(trHtml);
	     				$('#selectedProduct').append(tableHtml);
	     				productList.push($.trim($(this).parent().parent('tr').children('td').get(1).innerHTML));
	     				
	     				//绑定方法：删除选中的产品
	     				_this._bindProductRemove();
	     			}
	        	 });
	         },
	       //  var list=[];
	         _bindProductClick1: function() {
	        	 var _this = this;
	        	 $('input[type=checkbox][name=gp_checkbox]').bind('change', function(){
	     			var checkFlag = $(this).prop("checked");//true选中，false取消
	     			if(checkFlag){
	     				//展示所选的产品
	     				var trHtml = $(this).parent().parent('tr').clone();
	     				trHtml.children().first().remove();//删除第一个选择框
	     				trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
	     				var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
	     				tableHtml.append(trHtml);
	     				$('#selectedProduct1').append(tableHtml);
	     				presentList.push($.trim($(this).parent().parent('tr').children('td').get(1).innerHTML));
	     				
	     				//绑定方法：删除选中的产品
	     				_this._bindProductRemove1();
	     			}
	        	 });
	         },
	         _bindmjProductRemove: function() {
	        	 $('.img_remove_class').bind('click', function(){
	        		 $(this).parent().parent().parent().parent().remove();
	        		 mjproductList.pop($.trim($(this).parent().parent('tr').children('td').get(0).innerHTML));
	        		 
	        	 });
	        	 $('.img_remove_class2').bind('click', function(){
	        		 $(this).parent().parent().remove();
	        		 mjproductList.pop($.trim($(this).parent().parent('tr').children('td').get(0).innerHTML));
	        		
	        	 });
	         },
	         //删除选中的产品
	         _bindProductRemove: function() {
	        	 $('.img_remove_class').bind('click', function(){
	        		 $(this).parent().parent().parent().parent().remove();
	        		 productList.pop($.trim($(this).parent().parent('tr').children('td').get(0).innerHTML));
	        		 
	        	 });
	        	 $('.img_remove_class0').bind('click', function(){
	        		 $(this).parent().parent().remove();
	        		 productList.pop($.trim($(this).parent().parent('tr').children('td').get(0).innerHTML));
	        		 
	        	 });
	         },
	         _bindProductRemove1: function() {
	        	 $('.img_remove_class').bind('click', function(){
	        		 $(this).parent().parent().parent().parent().remove();
	        		 presentList.pop($.trim($(this).parent().parent('tr').children('td').get(0).innerHTML));
	        		 
	        	 });
	        	 $('.img_remove_class1').bind('click', function(){
	        		 $(this).parent().parent().remove();
	        		 presentList.pop($.trim($(this).parent().parent('tr').children('td').get(0).innerHTML));
	        		 
	        	 });
	         },
			_getPageInfo:function(){
				var _this=this;
				if(this.get('detailType')==="dr_offer"){
					$("#MJ").hide();
					$("#MZ").show();
					$("#MZ").addClass("current");
					$("#MZ").click();
					
				}else{
					$("#MJ").addClass("current");
					$("#MJ").show();
					$("#MZ").hide();
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
							if(d.productType==="dr_offer"){
								$("#activityName").val(d.programName);
							    $("#ruleAmount").val(d.ruleAmount);
							    $("#pUnit").val(d.ruleUnit);
							   
							    $("#pOnTime").val(_this.timestampToDate('yyyy-MM-dd', d.activeDate));
							    $("#pOffTime").val(_this.timestampToDate('yyyy-MM-dd', d.invalidDate));
							    $("#comments").val(d.comments);
							    var list=d.preList;
							   $("#activeType").val(list[0].activeFlag);
							   $("#gOntime").val(_this.timestampToDate('yyyy-MM-dd', list[0].giftActiveDate));
							   $("#cycleUnit").val(list[0].activeCycle);
							    
							    
							   if(d.proList.length!=0){
								   var template = $.templates("#show_table");
									var htmlOutput = template.render(d.proList);
									$("#mz_spro").html(htmlOutput);
									_this._bindProductRemove();
									$.each(d.proList,function(index,item){
										var prId = d.proList[index].productId;
										productList.push(prId);
										
									})
							    }
							    
							    if(list[0].giftProList.length!=0){
							    	 var template = $.templates("#show_table1");
										var htmlOutput = template.render(list[0].giftProList);
										$("#mz_giftP").html(htmlOutput);
										_this._bindProductRemove1();
										$.each(list[0].giftProList,function(index,item){
											var prId = list[0].giftProList[index].productId;
											presentList.push(prId);
											
										})
							    }
							    
							    
							    
							    
							    
							   
							  
							}else{
								$("#programName").val(d.programName);
							    $("#rruleAmount").val(d.ruleAmount);
							    $("#mj_unit").val(d.ruleUnit);
							    $("#mj_reduceAmount").val(d.reduceAmount);
							  
							    $("#rActiveTime").val(_this.timestampToDate('yyyy-MM-dd', d.activeDate));
							    $("#rInvalidTime").val(_this.timestampToDate('yyyy-MM-dd', d.invalidDate));
							    $("#rcomments").val(d.comments);
								
							    if(d.proList.length!=0){
									   var template = $.templates("#show_table2");
										var htmlOutput = template.render(d.proList);
										$("#mj_select").html(htmlOutput);
										_this._bindmjProductRemove();
										$.each(d.proList,function(index,item){
											var prId = d.proList[index].productId;
											mjproductList.push(prId);
											
										})
								    }
							}
						
						
					}
				});
			},
			
	    	//数据调用
			//选择参加产品
	    	_searchProducts: function(pageNo,pageSize){
	    		//入参
				var productId = $("#proId").val();
				var productName = $("#productName").val();
				var serviceType = $("#serviceType").val();
				var _this = this;
				var url = _base+ "/preferentialProduct/getProductList";
				$("#pagination-ul").runnerPagination({
									url : url,
									method : "POST",
									dataType : "json",
									processing : true,
									data : {
										productId:$.trim(productId),
										productName:$.trim(productName),
										serviceType:serviceType
									},
									pageSize : EditProductPager.DEFAULT_PAGE_SIZE,
									visiblePages : EditProductPager.DEFAULT_PAGE_SIZE,
									message : "正在为您查询数据..",
									render : function(data) {
										if (data != null&& data != 'undefined'&& data.length > 0) {
											var template = $.templates("#listDataTmpl");
											var htmlOutput = template.render(data);
											$("#listData").html(htmlOutput);
											_this._bindProductClick();
										} else {
											$("#listData").html(null);
											$("#listData").html("没有搜索到相关信息");
										}
											
										}
									
								});
				
	    	},
	    	//赠品产品
	    	_searchProducts1: function(pageNo,pageSize){
	    		//入参
	    		
	    		var productId = $("#pId").val();
				var productName = $("#pName").val();
				var serviceType = $("#sType").val();
				
				var _this = this;
				var url = _base+ "/preferentialProduct/getProductList";
				$("#pagination-ul1").runnerPagination({
									url : url,
									method : "POST",
									dataType : "json",
									processing : true,
									data : {
										productName:productName,
										productId:productId,
										serviceType:serviceType
									},
									pageSize : EditProductPager.DEFAULT_PAGE_SIZE,
									visiblePages : EditProductPager.DEFAULT_PAGE_SIZE,
									message : "正在为您查询数据..",
									render : function(data) {
										if (data != null&& data != 'undefined'&& data.length > 0) {
											var template = $.templates("#listDataTmpl1");
											var htmlOutput = template.render(data);
											$("#listData1").html(htmlOutput);
											_this._bindProductClick1();
										} else {
											$("#listData1").html(null);
											$("#listData1").html("没有搜索到相关信息");
										}
											
										}
									
								});
				
	    	},
	    	//业务类型
			_bindServiceSelect : function() {
				var this_=this;
				
					$.ajax({
						type : "post",
						processing : false,
						url : _base+ "/preferentialProduct/getSelect",
						dataType : "json",
						data : {
							paramType:"SERVICE_TYPE"
							},
						message : "正在加载数据..",
						success : function(data) {
							var d=data.data.paramList;
							$.each(d,function(index,item){
								var paramName = d[index].paramName;
								var paramCode = d[index].paramCode;
								$("#serviceType").append('<option value="'+paramCode+'">'+paramName+'</option>');
								$("#sType").append('<option value="'+paramCode+'">'+paramName+'</option>');
							    $("#mj_serviceType").append('<option value="'+paramCode+'">'+paramName+'</option>');
							})
							
								
						}
					});

			},
	    	//选择满减产品
	     	_searchmjProducts: function(pageNo,pageSize){
	     		
	     		//入参
	 			var productId = $("#mj_proId").val();
	 			var productName = $("#mj_productName").val();
	 			var serviceType = $("#mj_serviceType").val();
	 			var _this = this;
	 			var url = _base+ "/preferentialProduct/getProductList";
	 			$("#pagination-mj").runnerPagination({
	 								url : url,
	 								method : "POST",
	 								dataType : "json",
	 								processing : true,
	 								data : {
	 									productId:$.trim(productId),
	 									productName:$.trim(productName),
	 									serviceType:serviceType
	 								},
	 								pageSize : EditProductPager.DEFAULT_PAGE_SIZE,
	 								visiblePages : EditProductPager.DEFAULT_PAGE_SIZE,
	 								message : "正在为您查询数据..",
	 								render : function(data) {
	 									if (data != null&& data != 'undefined'&& data.length > 0) {
	 										var template = $.templates("#mj_table");
	 										var htmlOutput = template.render(data);
	 										$("#rBody").html(htmlOutput);
	 										_this._bindmjProductClick();
	 									} else {
	 										$("#rBody").html(null);
	 										$("#rBody").html("没有搜索到相关信息");
	 									}
	 										
	 									}
	 								
	 							});
	 			
	     	}
	    	
	    });
	 
    module.exports = EditProductPager
});
