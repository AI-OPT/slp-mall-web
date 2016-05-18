define('app/jsp/billdiscount/addBillDiscount', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Calendar = require('arale-calendar/1.1.2/index'),
    Dialog = require("artDialog/src/dialog"),
    //Validator = require('arale-validator/0.10.2/index-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("app/util/jsviews-ext");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    var viewOrUpdateflag = null;
    var mz_effectDate_calendar, mz_expireDate_calendar, mz_effectiveDate_calendar, 
	  	mj_effectDate_calendar, mj_expireDate_calendar,
	  	dz_effectDate_calendar, dz_expireDate_calendar,
	  	bd_effectDate_calendar, bd_expireDate_calendar,
	  	fd_effectDate_calendar, fd_expireDate_calendar = null;
    //定义页面组件类
    var AddBillDiscountPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 6
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"click .cancel":"_goBackBtnClick",										//返回方法
            "click #mz_bs_checkAll":"_mz_bscheckall",									//满赠-账单科目全选
            "click #mz_sp_checkAll":"_mz_spcheckall",									//满赠-赠品全选
            "click #mz_btn_bill_subject_query":"_mz_searchBillSubjectBtnClick",			//满赠-账单科目查询
            "click #mz_btn_salable_product_query":"_mz_searchSalableProductBtnClick",	//满赠-赠品查询
            "click #mz_btn_bill_discount_save":"_mz_saveBillDiscountClick",				//满赠-保存
            	
        	"click #mj_bs_checkAll":"_mj_bscheckall",							//满减-账单科目全选
        	"click #mj_btn_bill_subject_query":"_mj_searchBillSubjectBtnClick",	//满减-账单科目查询
        	"click #mj_btn_bill_discount_save":"_mj_saveBillDiscountClick",		//满减-保存
        		
    		"click #dz_bs_checkAll":"_dz_bscheckall",							//折扣-账单科目全选
    		"click #dz_btn_bill_subject_query":"_dz_searchBillSubjectBtnClick",	//折扣-账单科目查询
    		"click #dz_btn_bill_discount_save":"_dz_saveBillDiscountClick",		//折扣-保存
    			
			"click #bd_bs_checkAll":"_bd_bscheckall",							//保底-账单科目全选
			"click #bd_btn_bill_subject_query":"_bd_searchBillSubjectBtnClick",	//保底-账单科目查询
			"click #bd_btn_bill_discount_save":"_bd_saveBillDiscountClick",		//保底-保存
				
			"click #fd_bs_checkAll":"_fd_bscheckall",							//封顶-账单科目全选
			"click #fd_btn_bill_subject_query":"_fd_searchBillSubjectBtnClick",	//封顶-账单科目查询
			"click #fd_btn_bill_discount_save":"_fd_saveBillDiscountClick"		//封顶-保存
        },
    	setup: function () {
    		AddBillDiscountPager.superclass.setup.call(this);
    		viewOrUpdateflag = $("input[name='viewOrUpdateflag']").val();
    		this._initPage();
    		this._bindEvents();
    		this._bindCalendar();
    		this._getRelatedSubjecSelect();	//下拉菜单：加载关联科目（满减、保底、封顶）
    		this._mz_getServiceType();		//下拉菜单：满赠-加载业务类型（满减）
    		this._mz_getGroupBillingType();	//下拉菜单：满赠-加载计费类型（满减）
    		this._mz_getGiftActiveMode();	//下拉菜单：加载赠送业务生效方式（满减）
    		this._mz_getGiftActivePeriod();	//下拉菜单：加载赠送业务周期（满减）
    		this._dz_bindTimePeriodClick();	//折扣：选择时段
    		if(viewOrUpdateflag != null && viewOrUpdateflag != ''){
    			this._getBillDiscountInfo();//数据回填（查看、修改）
    		}
    		//this._bindValidator();
    	},
    	_bindEvents: function(){
    		var _this = this;
    		$('#API_KEY').bind('keypress',function(event){
				if(event.keyCode == "13"){
				}
			});
    	},
    	_initPage: function(){
         	//导航
    		if($("input[name='productId']").val()){
    			if($("input[name='viewOrUpdateflag']").val() == 'v'){
    				setBreadCrumb("账单优惠管理","查看账单优惠");
    			}else{
    				setBreadCrumb("账单优惠管理","编辑账单优惠");
    			}
    		}else{
    			setBreadCrumb("账单优惠管理","新增账单优惠");
    		}
      		//左侧菜单选中样式
      		$("#mnu_bmc_config").addClass("current");
      	},
    	//绑定日期控件
    	_bindCalendar: function(){
    		//满赠-生效日期
    		mz_effectDate_calendar = new Calendar({
    			trigger: '#mz_effectDate',
    			output: '#mz_effectDate_be',
    			align: {selfXY: [0, 0], baseElement: '#mz_effectDate_be', baseXY: [0, '100%']}
    		});
    		//满赠-失效日期
    		mz_expireDate_calendar = new Calendar({
    			trigger: '#mz_expireDate',
    			output: '#mz_expireDate_be',
    			align: {selfXY: [0, 0], baseElement: '#mz_expireDate_be', baseXY: [0, '100%']}
    		});
    		//满赠-赠送业务生效时间
    		mz_effectiveDate_calendar = new Calendar({
    			trigger: '#mz_effectiveDate',
    			output: '#mz_effectiveDate_be',
    			align: {selfXY: [0, 0], baseElement: '#mz_effectiveDate_be', baseXY: [0, '100%']
        		}
    		});
    		
    		//满减-生效日期
    		mj_effectDate_calendar = new Calendar({
    			trigger: '#mj_effectDate',
    			output: '#mj_effectDate_be',
    			align: {selfXY: [0, 0], baseElement: '#mj_effectDate_be', baseXY: [0, '100%']}
    		});
    		//满减-失效日期
    		mj_expireDate_calendar = new Calendar({
    			trigger: '#mj_expireDate',
    			output: '#mj_expireDate_be',
    			align: {selfXY: [0, 0], baseElement: '#mj_expireDate_be', baseXY: [0, '100%']}
    		});
    		
    		//折扣-折扣日期
    		/**new Calendar({
    			trigger: '#dz_discountDate',
    			output: '#dz_discountDate_be',
    			align: {selfXY: [0, 0], baseElement: '#dz_discountDate_be', baseXY: [0, '100%']}
    		});*/
    		//折扣-生效日期
    		dz_effectDate_calendar = new Calendar({
    			trigger: '#dz_effectDate',
    			output: '#dz_effectDate_be',
    			align: {selfXY: [0, 0], baseElement: '#dz_effectDate_be', baseXY: [0, '100%']}
    		});
    		///折扣-失效日期
    		dz_expireDate_calendar = new Calendar({
    			trigger: '#dz_expireDate',
    			output: '#dz_expireDate_be',
    			align: {selfXY: [0, 0], baseElement: '#dz_expireDate_be', baseXY: [0, '100%']}
    		});
    		
    		//保底-生效日期
    		bd_effectDate_calendar = new Calendar({
    			trigger: '#bd_effectDate',
    			output: '#bd_effectDate_be',
    			align: {selfXY: [0, 0], baseElement: '#bd_effectDate_be', baseXY: [0, '100%']}
    		});
    		///保底-失效日期
    		bd_expireDate_calendar = new Calendar({
    			trigger: '#bd_expireDate',
    			output: '#bd_expireDate_be',
    			align: {selfXY: [0, 0], baseElement: '#bd_expireDate_be', baseXY: [0, '100%']}
    		});
    		
    		//封顶-生效日期
    		fd_effectDate_calendar = new Calendar({
    			trigger: '#fd_effectDate',
    			output: '#fd_effectDate_be',
    			align: {selfXY: [0, 0], baseElement: '#fd_effectDate_be', baseXY: [0, '100%']}
    		});
    		///封顶-失效日期
    		fd_expireDate_calendar = new Calendar({
    			trigger: '#fd_expireDate',
    			output: '#fd_expireDate_be',
    			align: {selfXY: [0, 0], baseElement: '#fd_expireDate_be', baseXY: [0, '100%']}
    		});
    	},
    	_goBackBtnClick: function() {
    		window.history.back(-1);//返回
    	},
    	//公共方法，加载关联科目（满减、保底、封顶）
		_getRelatedSubjecSelect: function() {
			this._setSelectValue(_base + '/param/getRelatedSubjectList', {subjectType:'21'}, function(data){
				if(data){
					var data = eval(data);
					$.each(data, function(index, item) {
						// 循环获取数据
						var subjectName = data[index].subjectName;
						var subjectId = data[index].subjectId;
						$("#mj_form, #bd_form, #fd_form").find("select[name='relatedSubject']").append('<option value="'+subjectId+'">'+subjectName+'</option>');
					});
				}
			});
		},
    	//公共方法，加载下拉菜单
    	_setSelectValue: function(url, data, callback){
			$.ajax({
					url : url,
					type : "post",
					async : false,
					data : data,
					dataType : "html",
					timeout : "10000",
					success : function(data) {
						callback.call(this, data);
					}
				});
		},
		//公共方法，保存前校验
		_saveValidateFunc: function(formId) {
			var _this_ = this;
			var flag = true;
			$.each($(formId).find(".nonull-flag"), function(i){
				var _this = this;
				if($.trim($(this).val())==""){
					_this_._showDialog($(this).attr("tip")+"不能为空", $(_this))
					flag = false;
					return flag;
				}
			});
			return flag;
		},
		//公共方法，页面弹框
		_showDialog: function(contentStr, focusId) {
			Dialog({
 				width: '200px',
 				height: '50px',
				content: contentStr,
				okValue: "确定",
				ok:function () {
					this.close();
					if(focusId!='' && focusId!=null){
						$(focusId).focus();
					}
				}
			}).showModal();
		},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满赠-账单科目 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//满赠-账单科目全选
    	_mz_bscheckall: function() {
    		var _this = this;
    		var checkbox = $("#mz_billSubjectData").find('input[type=checkbox][name=bs_checkbox]');
			var chkFlag = $("#mz_bs_checkAll").prop("checked");
			checkbox.prop("checked", chkFlag);
			if(chkFlag){
				$.each(checkbox, function(i, iteam){
					_this._mz_showSelectedBillSubject(this);//展示所选的账单科目
				});
			}
         },
         //满赠-账单科目绑定复选框选中事件
         _mz_bindBillSubjectClick: function() {
        	 var _this = this;
        	 $('#mz_billSubjectData').find("input[name='bs_checkbox']").bind('click', function(){
     			var chkFlag = $(this).prop("checked");//true选中，false取消
     			if(chkFlag){
     				 _this._mz_showSelectedBillSubject(this);//展示所选的账单科目
     			}
        	 });
         },
         //满赠-展示所选的账单科目
         _mz_showSelectedBillSubject: function(curr) {
        	if($('#mz_selectedBillSubject').find('#'+$(curr).attr('id')).length <= 0){//不存在，才添加
        		//展示所选的账单科目
        		var trHtml = $(curr).parent().parent('tr').clone();
        		trHtml.children().first().hide();//trHtml.children().first().remove();//删除第一个选择框
        		trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
        		var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
        		tableHtml.append(trHtml);
        		$('#mz_selectedBillSubject').append(tableHtml);
        		
        		//绑定方法：删除选中的账单科目
        		this._mz_bindBillSubjectRemove();
        	}
         },
         //满赠-删除选中的账单科目
         _mz_bindBillSubjectRemove: function() {
        	 if(viewOrUpdateflag != 'v'){
        		 $('.img_remove_class').bind('click', function(){
        			 $(this).parent().parent().parent().parent().remove();
        		 });
        	 }
         },
    	//满赠-账单科目查询
    	_mz_searchBillSubjectBtnClick: function(){
    		var _this = this;
    		if(viewOrUpdateflag != 'v'){
    			$("#mz_billSubject-page").runnerPagination({
    				url: _base + "/billDiscount/getRelatedSubjectList",
    				method: "POST",
    				dataType: "json",
    				processing: true,
    				data: {
    					subjectId: $('#mz_form').find("input[name='subjectId']").val().trim(),
    					subjectName: $('#mz_form').find("input[name='subjectName']").val().trim()
    				},
    				pageSize: AddBillDiscountPager.DEFAULT_PAGE_SIZE,
    				visiblePages: 5,
    				message: "正在为您查询数据..",
    				render: function (data) {
    					if(data != null && data != 'undefined' && data.length>0){
    						var template = $.templates("#mz_billSubjectDataTmpl");
    						var htmlOutput = template.render(data);
    						$("#mz_billSubjectData").html(htmlOutput);
    						_this._mz_bindBillSubjectClick();//账单科目绑定复选框选中事件
    					}else{
    						$("#mz_billSubjectData").html("没有搜索到相关信息");
    					}
    				}
    			});
    		}
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满赠-账单科目  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满赠-赠品查询 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//满赠-赠品全选
    	_mz_spcheckall: function() {
    		var _this = this;
    		var checkbox = $("#mz_salableProductData").find('input[type=checkbox][name=sp_checkbox]');
    		var chkFlag = $("#mz_sp_checkAll").prop("checked");
			checkbox.prop("checked", chkFlag);
			if(chkFlag){
				$.each(checkbox, function(i, iteam){
					_this._mz_showSelectedSalableProduct(this);//展示所选的赠品
				});
			}
         },
         //满赠-赠品绑定复选框选中事件
         _mz_bindSalableProductClick: function() {
        	 var _this = this;
        	 $('#mz_salableProductData').find("input[name='sp_checkbox']").bind('click', function(){
     			var chkFlag = $(this).prop("checked");//true选中，false取消
     			if(chkFlag){
     				_this._mz_showSelectedSalableProduct(this);
     			}
        	 });
         },
         //满赠-展示所选的赠品
         _mz_showSelectedSalableProduct: function(curr) {
        	if($('#mz_selectedSalableProduct').find('#'+$(curr).attr('id')).length <= 0){//不存在，才添加
        		//展示所选的赠品
        		var trHtml = $(curr).parent().parent('tr').clone();
        		trHtml.children().first().hide();//trHtml.children().first().remove();//删除第一个选择框
        		trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
        		var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
        		tableHtml.append(trHtml);
        		$('#mz_selectedSalableProduct').append(tableHtml);
        		
        		//绑定方法：删除选中的赠品
        		this._mz_bindSalableProductRemove();
        	}
         },
         //满赠-删除选中的赠品
         _mz_bindSalableProductRemove: function() {
        	 $('.img_remove_class').bind('click', function(){
        		 $(this).parent().parent().parent().parent().remove();
        	 });
         },
    	//满赠-赠品查询（可销售产品）
    	_mz_searchSalableProductBtnClick: function(){
    		var _this = this;
    		if(viewOrUpdateflag != 'v'){
    			$("#mz_salableProduct-page").runnerPagination({
    				url: _base + "/salableProduct/getSalableProductList",
    				method: "POST",
    				dataType: "json",
    				processing: true,
    				data : $("#mz_sp-form :input, #mz_sp-form select").serializeArray(),
    				pageSize: AddBillDiscountPager.DEFAULT_PAGE_SIZE,
    				visiblePages:5,
    				message: "正在为您查询数据..",
    				render: function (data) {
    					if(data&&data.length>0){
    						var template = $.templates("#mz_salableProductDataTmpl");
    						var htmlOut = template.render(data);
    						$("#mz_salableProductData").html(htmlOut);
    						_this._mz_bindSalableProductClick();//赠品绑定复选框选中事件
    					}else{
    						$("#mz_salableProductData").html("没有搜索到相关信息");
    					}
    				}
    			});
    		}
    	},
    	//满赠-加载业务类型下拉菜单
    	_mz_getServiceType: function() {
			this._setSelectValue(_base + '/param/getServiceType', null, function(data){
				$("#serviceType").html('<option value="">请选择</option>');
				if(data){
					var json = eval(data);
					$.each(json,function(index, item) {
						// 循环获取数据
						var paramName = json[index].paramName;
						var paramCode = json[index].paramCode;
						$("#serviceType").append('<option cid="'+json[index].id+'" value="'+paramCode+'">'+paramName+'</option>');
					});
				}
			});
		},
    	//满赠-加载计费类型下拉菜单
		_mz_getGroupBillingType: function() {
			this._setSelectValue(_base + '/param/getGroupBillingType', null, function(data){
				if(data){
					var json = eval(data);
					$.each(json,function(index, item) {
						// 循环获取数据
						var paramName = json[index].paramName;
						var paramCode = json[index].paramCode;
						if(paramCode != 'STEP_GROUP_TYPE'){//阶梯产品不能赠送
							$("#groupBillingType").append('<option value="'+paramCode+'">'+paramName+'</option>');
						}
					});
				}
			});
		},
		//满赠-加载赠送业务生效方式下拉菜单
		_mz_getGiftActiveMode: function() {
			this._setSelectValue(_base + '/param/getGiftActiveMode', null, function(data){
				if(data){
					var json = eval(data);
					$.each(json,function(index, item) {
						// 循环获取数据
						var paramName = json[index].paramName;
						var paramCode = json[index].paramCode;
						$("#mz_gift_active_mode").append('<option value="'+paramCode+'">'+paramName+'</option>');
					});
				}
			});
		},
		//满赠-加载赠送业务周期下拉菜单
		_mz_getGiftActivePeriod: function() {
			this._setSelectValue(_base + '/param/getGiftActivePeriod', null, function(data){
				if(data){
					var json = eval(data);
					$.each(json,function(index, item) {
						// 循环获取数据
						var paramName = json[index].paramName;
						var paramCode = json[index].paramCode;
						$("#mz_gift_active_period").append('<option value="'+paramCode+'">'+paramName+'</option>');
					});
				}
			});
		},
        //＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满赠-赠品查询  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
      	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满赠-保存方法 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
		//满赠-保存方法
      	_mz_saveBillDiscountClick: function() {
     		if(!this._saveValidateFunc("#mz_form")){return false;}//不为空校验
     		
     		//获取选中的账单科目
     		var billSubjectMap = $('#mz_selectedBillSubject').find("input[name='bs_checkbox']").map(function(){return $(this).attr('id')}).get();
     		if(billSubjectMap.length < 1){
     			this._showDialog("参与优惠活动的账单科目不能为空", '#mz_btn_bill_subject_query');
     			return false;
     		}
     		//获取选中的赠品
     		var salableProductMap = $('#mz_selectedSalableProduct').find("input[name='sp_checkbox']").map(function(){return $(this).attr('id')}).get();
     		if(salableProductMap.length < 1){
     			this._showDialog("满赠活动的赠品不能为空", '#mz_btn_salable_product_query');
     			return false;
     		}
     		
     		//满赠送业务生效方式选择指定日期赠送业务的生效时间为必填项
     		var giftActiveMode = $('#mz_form').find("select[name='activeMode']").val().trim();
     		if(giftActiveMode == 'special_date'){
     			var giftEffectDate = $('#mz_form').find("input[name='giftEffectDate']").val().trim();
     			if(giftEffectDate == '' || giftEffectDate == null){
     				this._showDialog("您选择了指定日期赠送业务，所以赠送业务生效时间为必填项", null);
     				$('#mz_form').find("input[name='giftEffectDate']").focus();
         			return false;
     			}
     		}
     		
     		var paramData = {
     			discountType: 'mz',
     			productId: $("input[name='productId']").val().trim(),
 				productName: $('#mz_form').find("input[name='productName']").val().trim(),
             	fullCostAmount: $('#mz_form').find("input[name='fullCostAmount']").val().trim(),
             	effectDate: $('#mz_form').find("input[name='effectDate']").val().trim(),
             	expireDate: $('#mz_form').find("input[name='expireDate']").val().trim(),
             	remark: $('#mz_form').find("textarea[name='remark']").val().trim(),
             	billSubjectList: JSON.stringify(billSubjectMap).replace('[', '').replace(']', '').replace(/"/g, ''),//账单科目列表
             	giftProductIdList: JSON.stringify(salableProductMap).replace('[', '').replace(']', '').replace(/"/g, ''),//赠品列表
             	giftActiveMode: $('#mz_form').find("select[name='activeMode']").val().trim(),
             	giftEffectDate: $('#mz_form').find("input[name='giftEffectDate']").val().trim(),
             	giftActivePeriod: $('#mz_form').find("select[name='activePeriod']").val().trim()
     		};
         	
     		ajaxController.ajax({
                 type: "post",
                 dataType : "json",
                 url: _base + "/billDiscount/save",
                 processing: true,
                 message: "正在加载，请等待...",
                 data: paramData,
                 success: function(data){
                     if(data){
                     	Dialog({
         					width: '200px',
         					height: '50px',
         					content: data.statusInfo,
         					okValue:"确定",
                            ok:function(){
                            	if(data.data=='000000'){
                            		window.history.back(-1);//返回
                            	}
                            }
         				}).showModal();
                     }
                 }
             });
     	},
     	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满赠-保存方法  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
     	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
     	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满减-账单科目 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//满减-账单科目全选
    	_mj_bscheckall: function() {
    		var _this = this;
    		var checkbox = $("#mj_billSubjectData").find('input[type=checkbox][name=bs_checkbox]');
			var chkFlag = $("#mj_bs_checkAll").prop("checked");
			checkbox.prop("checked", chkFlag);
			if(chkFlag){
				$.each(checkbox, function(i, iteam){
					_this._mj_showSelectedBillSubject(this);//展示所选的账单科目
				});
			}
         },
         //满减-账单科目绑定复选框选中事件
         _mj_bindBillSubjectClick: function() {
        	 var _this = this;
        	 $('#mj_billSubjectData').find("input[name='bs_checkbox']").bind('click', function(){
     			var chkFlag = $(this).prop("checked");//true选中，false取消
     			if(chkFlag){
     				 _this._mj_showSelectedBillSubject(this);//展示所选的账单科目
     			}
        	 });
         },
         //满减-展示所选的账单科目
         _mj_showSelectedBillSubject: function(curr) {
        	if($('#mj_selectedBillSubject').find('#'+$(curr).attr('id')).length <= 0){//不存在，才添加
        		//展示所选的账单科目
        		var trHtml = $(curr).parent().parent('tr').clone();
        		trHtml.children().first().hide();//trHtml.children().first().remove();//删除第一个选择框
        		trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
        		var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
        		tableHtml.append(trHtml);
        		$('#mj_selectedBillSubject').append(tableHtml);
        		
        		//绑定方法：删除选中的账单科目
        		this._mj_bindBillSubjectRemove();
        	}
         },
         //满减-删除选中的账单科目
         _mj_bindBillSubjectRemove: function() {
        	 if(viewOrUpdateflag != 'v'){
        		 $('.img_remove_class').bind('click', function(){
        			 $(this).parent().parent().parent().parent().remove();
        		 });
        	 }
         },
    	 //满减-账单科目查询
         _mj_searchBillSubjectBtnClick: function(){
    		var _this = this;
    		if(viewOrUpdateflag != 'v'){
    			$("#mj_billSubject-page").runnerPagination({
    				url: _base + "/billDiscount/getRelatedSubjectList",
    				method: "POST",
    				dataType: "json",
    				processing: true,
    				data: {
    					subjectId: $('#mj_form').find("input[name='subjectId']").val().trim(),
    					subjectName: $('#mj_form').find("input[name='subjectName']").val().trim()
    				},
    				pageSize: AddBillDiscountPager.DEFAULT_PAGE_SIZE,
    				visiblePages: 5,
    				message: "正在为您查询数据..",
    				render: function (data) {
    					if(data != null && data != 'undefined' && data.length>0){
    						var template = $.templates("#mj_billSubjectDataTmpl");
    						var htmlOutput = template.render(data);
    						$("#mj_billSubjectData").html(htmlOutput);
    						_this._mj_bindBillSubjectClick();//账单科目绑定复选框选中事件
    					}else{
    						$("#mj_billSubjectData").html("没有搜索到相关信息");
    					}
    				}
    			});
    		}
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满减-账单科目  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满减-保存方法 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
     	//满减-保存方法
      	_mj_saveBillDiscountClick: function() {
      		if(!this._saveValidateFunc("#mj_form")){return false;}//不为空校验
     		//获取选中的账单科目
     		var billSubjectMap = $('#mj_selectedBillSubject').find("input[name='bs_checkbox']").map(function(){return $(this).attr('id')}).get();
     		if(billSubjectMap.length < 1){
     			this._showDialog("参与优惠活动的账单科目不能为空", '#mj_btn_bill_subject_query');
     			return false;
     		}
     		
     		var paramData = {
     			discountType: 'mj',
     			productId: $("input[name='productId']").val().trim(),
 				productName: $('#mj_form').find("input[name='productName']").val().trim(),
             	fullCostAmount: $('#mj_form').find("input[name='fullCostAmount']").val().trim(),
             	discountAmount: $('#mj_form').find("input[name='discountAmount']").val().trim(),
             	effectDate: $('#mj_form').find("input[name='effectDate']").val().trim(),
             	expireDate: $('#mj_form').find("input[name='expireDate']").val().trim(),
             	remark: $('#mj_form').find("textarea[name='remark']").val().trim(),
             	billSubjectList: JSON.stringify(billSubjectMap).replace('[', '').replace(']', '').replace(/"/g, ''),//账单科目列表
             	relatedSubjectList: $('#mj_form').find("select[name='relatedSubject']").val().trim()//满减关联的账单科目
     		};
     		
     		ajaxController.ajax({
                 type: "post",
                 dataType : "json",
                 url: _base + "/billDiscount/save",
                 processing: true,
                 message: "正在加载，请等待...",
                 data: paramData,
                 success: function(data){
                     if(data){
                     	Dialog({
         					width: '200px',
         					height: '50px',
         					content: data.statusInfo,
         					okValue:"确定",
                            ok:function(){
                            	if(data.data=='000000'){
                            		window.history.back(-1);//返回
                            	}
                            }
         				}).showModal();
                     }
                 }
             });
     	},
     	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝满减-保存方法  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝折扣-账单科目 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
     	//折扣-选择时段
    	_dz_bindTimePeriodClick: function() {
    		var _this = this;
    		$('#dz_form').find("input[name='preferentialRules']").bind('click', function(){
    			if($(this).prop("checked")){
    				$('#dz_form').find("input[name='startTime'], input[name='endTime']").removeAttr("readOnly");
    			}else{
    				$('#dz_form').find("input[name='startTime'], input[name='endTime']").val('').attr("readOnly", true);
    			}
    		});
    	},
     	//折扣-账单科目全选
    	_dz_bscheckall: function() {
    		var _this = this;
    		var checkbox = $("#dz_billSubjectData").find('input[type=checkbox][name=bs_checkbox]');
    		var chkFlag = $("#dz_bs_checkAll").prop("checked");
    		checkbox.prop("checked", chkFlag);
    		if(chkFlag){
    			$.each(checkbox, function(i, iteam){
    				_this._dz_showSelectedBillSubject(this);//展示所选的账单科目
    			});
    		}
    	},
    	//折扣-账单科目绑定复选框选中事件
    	_dz_bindBillSubjectClick: function() {
    		var _this = this;
    		$('#dz_billSubjectData').find("input[name='bs_checkbox']").bind('click', function(){
    			var chkFlag = $(this).prop("checked");//true选中，false取消
    			if(chkFlag){
    				_this._dz_showSelectedBillSubject(this);//展示所选的账单科目
    			}
    		});
    	},
    	//折扣-展示所选的账单科目
    	_dz_showSelectedBillSubject: function(curr) {
    		if($('#dz_selectedBillSubject').find('#'+$(curr).attr('id')).length <= 0){//不存在，才添加
    			//展示所选的账单科目
    			var trHtml = $(curr).parent().parent('tr').clone();
    			trHtml.children().first().hide();//trHtml.children().first().remove();//删除第一个选择框
    			trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
    			var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
    			tableHtml.append(trHtml);
    			$('#dz_selectedBillSubject').append(tableHtml);
    			
    			//绑定方法：删除选中的账单科目
    			this._dz_bindBillSubjectRemove();
    		}
    	},
    	//折扣-删除选中的账单科目
    	_dz_bindBillSubjectRemove: function() {
    		if(viewOrUpdateflag != 'v'){
    			$('.img_remove_class').bind('click', function(){
    				$(this).parent().parent().parent().parent().remove();
    			});
    		}
    	},
    	//折扣-账单科目查询
    	_dz_searchBillSubjectBtnClick: function(){
    		var _this = this;
    		if(viewOrUpdateflag != 'v'){
    			$("#dz_billSubject-page").runnerPagination({
    				url: _base + "/billDiscount/getRelatedSubjectList",
    				method: "POST",
    				dataType: "json",
    				processing: true,
    				data: {
    					subjectId: $('#dz_form').find("input[name='subjectId']").val().trim(),
    					subjectName: $('#dz_form').find("input[name='subjectName']").val().trim()
    				},
    				pageSize: AddBillDiscountPager.DEFAULT_PAGE_SIZE,
    				visiblePages: 5,
    				message: "正在为您查询数据..",
    				render: function (data) {
    					if(data != null && data != 'undefined' && data.length>0){
    						var template = $.templates("#dz_billSubjectDataTmpl");
    						var htmlOutput = template.render(data);
    						$("#dz_billSubjectData").html(htmlOutput);
    						_this._dz_bindBillSubjectClick();//账单科目绑定复选框选中事件
    					}else{
    						$("#dz_billSubjectData").html("没有搜索到相关信息");
    					}
    				}
    			});
    		}
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝折扣-账单科目  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝折扣-保存方法 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//折扣-保存方法
    	_dz_saveBillDiscountClick: function() {
    		if(!this._saveValidateFunc("#dz_form")){return false;}//不为空校验
    		
    		//选择时段
    		var startTime = '';
    		var endTime = '';
    		$.each($('#dz_form').find("input[name='preferentialRules']"), function(i){
    			if($(this).prop("checked")){
    				startTime = $('#dz_form').find("input[name='startTime']").val().trim();
    				endTime = $('#dz_form').find("input[name='endTime']").val().trim();
    			}
    		});
    		
    		//获取选中的账单科目
    		var billSubjectMap = $('#dz_selectedBillSubject').find("input[name='bs_checkbox']").map(function(){return $(this).attr('id')}).get();
    		if(billSubjectMap.length < 1){
     			this._showDialog("参与优惠活动的账单科目不能为空", '#dz_btn_bill_subject_query');
     			return false;
     		}
    		
    		var paramData = {
				discountType: 'dz',
     			productId: $("input[name='productId']").val().trim(),
				productName: $('#dz_form').find("input[name='productName']").val().trim(),
				startTime: startTime,
				endTime: endTime,
				discountPercent: $('#dz_form').find("input[name='discountPercent']").val().trim(),
				effectDate: $('#dz_form').find("input[name='effectDate']").val().trim(),
				expireDate: $('#dz_form').find("input[name='expireDate']").val().trim(),
				remark: $('#dz_form').find("textarea[name='remark']").val().trim(),
				billSubjectList: JSON.stringify(billSubjectMap).replace('[', '').replace(']', '').replace(/"/g, '')//账单科目列表
    		};
    		
    		ajaxController.ajax({
    			type: "post",
    			dataType : "json",
    			url: _base + "/billDiscount/save",
    			processing: true,
    			message: "正在加载，请等待...",
    			data: paramData,
    			success: function(data){
    				if(data){
    					Dialog({
    						width: '200px',
    						height: '50px',
    						content: data.statusInfo,
         					okValue:"确定",
                            ok:function(){
                            	if(data.data=='000000'){
                            		window.history.back(-1);//返回
                            	}
                            }
    					}).showModal();
    				}
    			}
    		});
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝折扣-保存方法  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝保底-账单科目 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//保底-账单科目全选
    	_bd_bscheckall: function() {
    		var _this = this;
    		var checkbox = $("#bd_billSubjectData").find('input[type=checkbox][name=bs_checkbox]');
    		var chkFlag = $("#bd_bs_checkAll").prop("checked");
    		checkbox.prop("checked", chkFlag);
    		if(chkFlag){
    			$.each(checkbox, function(i, iteam){
    				_this._bd_showSelectedBillSubject(this);//展示所选的账单科目
    			});
    		}
    	},
    	//保底-账单科目绑定复选框选中事件
    	_bd_bindBillSubjectClick: function() {
    		var _this = this;
    		$('#bd_billSubjectData').find("input[name='bs_checkbox']").bind('click', function(){
    			var chkFlag = $(this).prop("checked");//true选中，false取消
    			if(chkFlag){
    				_this._bd_showSelectedBillSubject(this);//展示所选的账单科目
    			}
    		});
    	},
    	//保底-展示所选的账单科目
    	_bd_showSelectedBillSubject: function(curr) {
    		if($('#bd_selectedBillSubject').find('#'+$(curr).attr('id')).length <= 0){//不存在，才添加
    			//展示所选的账单科目
    			var trHtml = $(curr).parent().parent('tr').clone();
    			trHtml.children().first().hide();//trHtml.children().first().remove();//删除第一个选择框
    			trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
    			var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
    			tableHtml.append(trHtml);
    			$('#bd_selectedBillSubject').append(tableHtml);
    			
    			//绑定方法：删除选中的账单科目
    			this._bd_bindBillSubjectRemove();
    		}
    	},
    	//保底-删除选中的账单科目
    	_bd_bindBillSubjectRemove: function() {
    		if(viewOrUpdateflag != 'v'){
    			$('.img_remove_class').bind('click', function(){
    				$(this).parent().parent().parent().parent().remove();
    			});
    		}
    	},
    	//保底-账单科目查询
    	_bd_searchBillSubjectBtnClick: function(){
    		var _this = this;
    		if(viewOrUpdateflag != 'v'){
    			$("#bd_billSubject-page").runnerPagination({
    				url: _base + "/billDiscount/getRelatedSubjectList",
    				method: "POST",
    				dataType: "json",
    				processing: true,
    				data: {
    					subjectId: $('#bd_form').find("input[name='subjectId']").val().trim(),
    					subjectName: $('#bd_form').find("input[name='subjectName']").val().trim()
    				},
    				pageSize: AddBillDiscountPager.DEFAULT_PAGE_SIZE,
    				visiblePages: 5,
    				message: "正在为您查询数据..",
    				render: function (data) {
    					if(data != null && data != 'undefined' && data.length>0){
    						var template = $.templates("#bd_billSubjectDataTmpl");
    						var htmlOutput = template.render(data);
    						$("#bd_billSubjectData").html(htmlOutput);
    						_this._bd_bindBillSubjectClick();//账单科目绑定复选框选中事件
    					}else{
    						$("#bd_billSubjectData").html("没有搜索到相关信息");
    					}
    				}
    			});
    		}
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝保底-账单科目  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝保底-保存方法 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//保底-保存方法
    	_bd_saveBillDiscountClick: function() {
    		if(!this._saveValidateFunc("#bd_form")){return false;}//不为空校验
    		//获取选中的账单科目
    		var billSubjectMap = $('#bd_selectedBillSubject').find("input[name='bs_checkbox']").map(function(){return $(this).attr('id')}).get();
    		if(billSubjectMap.length < 1){
     			this._showDialog("参与优惠活动的账单科目不能为空", '#bd_btn_bill_subject_query');
     			return false;
     		}
     		
    		var paramData = {
				discountType: 'bd',
     			productId: $("input[name='productId']").val().trim(),
				productName: $('#bd_form').find("input[name='productName']").val().trim(),
				bottomAmount: $('#bd_form').find("input[name='bottomAmount']").val().trim(),
				effectDate: $('#bd_form').find("input[name='effectDate']").val().trim(),
				expireDate: $('#bd_form').find("input[name='expireDate']").val().trim(),
				remark: $('#bd_form').find("textarea[name='remark']").val().trim(),
				billSubjectList: JSON.stringify(billSubjectMap).replace('[', '').replace(']', '').replace(/"/g, ''),//账单科目列表
				relatedSubjectList: $('#bd_form').find("select[name='relatedSubject']").val().trim()//保底关联的账单科目
    		};
    		
    		ajaxController.ajax({
    			type: "post",
    			dataType : "json",
    			url: _base + "/billDiscount/save",
    			processing: true,
    			message: "正在加载，请等待...",
    			data: paramData,
    			success: function(data){
    				if(data){
    					Dialog({
    						width: '200px',
    						height: '50px',
    						content: data.statusInfo,
         					okValue:"确定",
                            ok:function(){
                            	if(data.data=='000000'){
                            		window.history.back(-1);//返回
                            	}
                            }
    					}).showModal();
    				}
    			}
    		});
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝保底-保存方法  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝封顶-账单科目 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//封顶-账单科目全选
    	_fd_bscheckall: function() {
    		var _this = this;
    		var checkbox = $("#fd_billSubjectData").find('input[type=checkbox][name=bs_checkbox]');
    		var chkFlag = $("#fd_bs_checkAll").prop("checked");
    		checkbox.prop("checked", chkFlag);
    		if(chkFlag){
    			$.each(checkbox, function(i, iteam){
    				_this._fd_showSelectedBillSubject(this);//展示所选的账单科目
    			});
    		}
    	},
    	//封顶-账单科目绑定复选框选中事件
    	_fd_bindBillSubjectClick: function() {
    		var _this = this;
    		$('#fd_billSubjectData').find("input[name='bs_checkbox']").bind('click', function(){
    			var chkFlag = $(this).prop("checked");//true选中，false取消
    			if(chkFlag){
    				_this._fd_showSelectedBillSubject(this);//展示所选的账单科目
    			}
    		});
    	},
    	//封顶-展示所选的账单科目
    	_fd_showSelectedBillSubject: function(curr) {
    		if($('#fd_selectedBillSubject').find('#'+$(curr).attr('id')).length <= 0){//不存在，才添加
    			//展示所选的账单科目
    			var trHtml = $(curr).parent().parent('tr').clone();
    			trHtml.children().first().hide();//trHtml.children().first().remove();//删除第一个选择框
    			trHtml.append($('<td><img class="img_remove_class" src="'+_base+'/resources/baasop/images/stepclose.png"></td>'));//增加最后一个删除的差号
    			var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
    			tableHtml.append(trHtml);
    			$('#fd_selectedBillSubject').append(tableHtml);
    			
    			//绑定方法：删除选中的账单科目
    			this._fd_bindBillSubjectRemove();
    		}
    	},
    	//封顶-删除选中的账单科目
    	_fd_bindBillSubjectRemove: function() {
    		if(viewOrUpdateflag != 'v'){
    			$('.img_remove_class').bind('click', function(){
    				$(this).parent().parent().parent().parent().remove();
    			});
    		}
    	},
    	//封顶-账单科目查询
    	_fd_searchBillSubjectBtnClick: function(){
    		var _this = this;
    		if(viewOrUpdateflag != 'v'){
    			$("#fd_billSubject-page").runnerPagination({
    				url: _base + "/billDiscount/getRelatedSubjectList",
    				method: "POST",
    				dataType: "json",
    				processing: true,
    				data: {
    					subjectId: $('#fd_form').find("input[name='subjectId']").val().trim(),
    					subjectName: $('#fd_form').find("input[name='subjectName']").val().trim()
    				},
    				pageSize: AddBillDiscountPager.DEFAULT_PAGE_SIZE,
    				visiblePages: 5,
    				message: "正在为您查询数据..",
    				render: function (data) {
    					if(data != null && data != 'undefined' && data.length>0){
    						var template = $.templates("#fd_billSubjectDataTmpl");
    						var htmlOutput = template.render(data);
    						$("#fd_billSubjectData").html(htmlOutput);
    						_this._fd_bindBillSubjectClick();//账单科目绑定复选框选中事件
    					}else{
    						$("#fd_billSubjectData").html("没有搜索到相关信息");
    					}
    				}
    			});
    		}
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝封顶-账单科目  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝封顶-保存方法 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//封顶-保存方法
    	_fd_saveBillDiscountClick: function() {
    		if(!this._saveValidateFunc("#fd_form")){return false;}//不为空校验
    		//获取选中的账单科目
    		var billSubjectMap = $('#fd_selectedBillSubject').find("input[name='bs_checkbox']").map(function(){return $(this).attr('id')}).get();
    		if(billSubjectMap.length < 1){
     			this._showDialog("参与优惠活动的账单科目不能为空", '#fd_btn_bill_subject_query');
     			return false;
     		}
    		
    		var paramData = {
				discountType: 'fd',
     			productId: $("input[name='productId']").val().trim(),
				productName: $('#fd_form').find("input[name='productName']").val().trim(),
				topAmount: $('#fd_form').find("input[name='topAmount']").val().trim(),
				effectDate: $('#fd_form').find("input[name='effectDate']").val().trim(),
				expireDate: $('#fd_form').find("input[name='expireDate']").val().trim(),
				remark: $('#fd_form').find("textarea[name='remark']").val().trim(),
				billSubjectList: JSON.stringify(billSubjectMap).replace('[', '').replace(']', '').replace(/"/g, ''),//账单科目列表
				relatedSubjectList: $('#fd_form').find("select[name='relatedSubject']").val().trim()//封顶关联的账单科目
    		};
    		
    		ajaxController.ajax({
    			type: "post",
    			dataType : "json",
    			url: _base + "/billDiscount/save",
    			processing: true,
    			message: "正在加载，请等待...",
    			data: paramData,
    			success: function(data){
    				if(data){
    					Dialog({
    						width: '200px',
    						height: '50px',
    						content: data.statusInfo,
         					okValue:"确定",
                            ok:function(){
                            	if(data.data=='000000'){
                            		window.history.back(-1);//返回
                            	}
                            }
    					}).showModal();
    				}
    			}
    		});
    	},
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝封顶-保存方法  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝全部-修改方法 begin ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	//数据回填（查看、修改）
    	_getBillDiscountInfo: function(){
    		var _this = this;
    		var productId = $("input[name='productId']").val().trim();
    		if(productId){
    			$.ajax({
    				type: "post",
    				processing: false,
    				url: _base + "/billDiscount/getByProductId",
    				dataType: "json",
    				data: {productId: productId },
    				message: "正在加载数据..",
    				success: function (data) {
    					if(data && data.statusCode==1){
    						var vo = data.data;
    						var discountType = vo.discountType;//优惠类型
    						
    						_this._showDiscountCnt(discountType);
    						
    						var formId = '#' + discountType + '_form';
    						if(viewOrUpdateflag == 'v'){
    							$('#' + discountType + '_btn_bill_discount_save').remove();//remove保存按钮
    							$(formId).find(".flag").find("input[name='productName']").val(vo.productName).attr('readonly', true);
    							$(formId).find(".flag").find("input[name='fullCostAmount']").val(new RegExp().liToYuan(vo.fullCostAmount)).attr('readonly', true);
    							$(formId).find(".flag").find("input[name='discountAmount']").val(new RegExp().liToYuan(vo.discountAmount)).attr('readonly', true);
    							$(formId).find(".flag").find("input[name='discountPercent']").val(vo.discountPercent).attr('readonly', true);
    							$(formId).find(".flag").find("input[name='bottomAmount']").val(new RegExp().liToYuan(vo.bottomAmount)).attr('readonly', true);
    							$(formId).find(".flag").find("input[name='topAmount']").val(new RegExp().liToYuan(vo.topAmount)).attr('readonly', true);
    							$(formId).find(".flag").find("select[name='relatedSubject']").val(vo.relatedSubjectId).attr('disabled', true);
    							$(formId).find(".flag").find("input[name='effectDate']").val(_this._timestampToDate('yyyy-MM-dd', vo.effectDate));
    							$(formId).find(".flag").find("input[name='expireDate']").val(_this._timestampToDate('yyyy-MM-dd', vo.expireDate));
    							$(formId).find(".flag").find("textarea[name='remark']").val(vo.remark).attr('readonly', true);
    							_this._generateBillSubjectDiv(discountType, vo.billSubjectList);//账单科目
    							
    							if(discountType == "mz"){
    								_this._generategiftProductDiv(discountType, vo.giftProductList);//赠品
    								$("select[name='activeMode']").val(vo.giftActiveMode).attr('disabled', true);
    								$("input[name='giftEffectDate']").val(vo.giftEffectDate!=null ? vo.giftEffectDate.substring(0, 10) : null);
    								$("select[name='activePeriod']").val(vo.giftActivePeriod).attr('disabled', true);
    								mz_effectDate_calendar.disable();
    								mz_expireDate_calendar.disable();
    								mz_effectiveDate_calendar.disable();
    							}else if(discountType == "mj"){
    								mj_effectDate_calendar.disable();
    								mj_expireDate_calendar.disable();
    							}else if(discountType == "dz"){
    								$(formId).find("input[name='preferentialRules']").attr('disabled', true);
    								$(formId).find("input[name='startTime']").val(vo.startTime).attr('readonly', true);
    								$(formId).find("input[name='endTime']").val(vo.endTime).attr('readonly', true);
    								dz_effectDate_calendar.disable();
    								dz_expireDate_calendar.disable();
    							}else if(discountType == "bd"){
    								bd_effectDate_calendar.disable();
    								bd_expireDate_calendar.disable();
    							}else if(discountType == "fd"){
    								fd_effectDate_calendar.disable();
    								fd_expireDate_calendar.disable();
    							}
    							
    						}else{
    							$(formId).find(".flag").find("input[name='productName']").val(vo.productName);
    							$(formId).find(".flag").find("input[name='fullCostAmount']").val(new RegExp().liToYuan(vo.fullCostAmount));
    							$(formId).find(".flag").find("input[name='discountAmount']").val(new RegExp().liToYuan(vo.discountAmount));
    							$(formId).find(".flag").find("input[name='discountPercent']").val(vo.discountPercent);
    							$(formId).find(".flag").find("input[name='bottomAmount']").val(new RegExp().liToYuan(vo.bottomAmount));
    							$(formId).find(".flag").find("input[name='topAmount']").val(new RegExp().liToYuan(vo.topAmount));
    							$(formId).find(".flag").find("select[name='relatedSubject']").val(vo.relatedSubjectId);
    							$(formId).find(".flag").find("input[name='effectDate']").val(_this._timestampToDate('yyyy-MM-dd', vo.effectDate));
    							$(formId).find(".flag").find("input[name='expireDate']").val(_this._timestampToDate('yyyy-MM-dd', vo.expireDate));
    							$(formId).find(".flag").find("textarea[name='remark']").val(vo.remark);
    							_this._generateBillSubjectDiv(discountType, vo.billSubjectList);//账单科目
    							
    							if(discountType == "dz"){
    								$(formId).find("input[name='startTime']").val(vo.startTime);
    								$(formId).find("input[name='endTime']").val(vo.endTime);
    							}
    							
    							if(discountType == "mz"){
    								_this._generategiftProductDiv(discountType, vo.giftProductList);//赠品
    								$("select[name='activeMode']").val(vo.giftActiveMode);
    								$("input[name='giftEffectDate']").val(vo.giftEffectDate!=null ? vo.giftEffectDate.substring(0, 10) : null);
    								$("select[name='activePeriod']").val(vo.giftActivePeriod);
    							}
    						}
    						
    					}
    				}
    			}); 
    		}
    	},
    	//生成账单科目div（查看、修改）
    	_generateBillSubjectDiv :function(discountType, billSubjectList){
    		var _this = this;
    		var subjectList = eval(billSubjectList);
	        if(subjectList){
	        	$.each(subjectList, function(i){
	        		var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
	        		var trHtml = $("<tr></tr>");
	        		var td1 = $("<td style='display: none;'><input type='checkbox' id='"+this.subjectId+"' name='bs_checkbox' class='checkbox'></td>");
	        		var td2 = $("<td>"+this.subjectId+"</td>");
	        		var td3 = $("<td>"+this.subjectName+"</td>");
	        		var td4 = $("<td>"+this.subjectDesc+"</td>");
	        		var td5 = $("<td><img class='img_remove_class' src='"+_base+"/resources/baasop/images/stepclose.png'></td>");
	        		trHtml.append(td1).append(td2).append(td3).append(td4).append(td5);
	        		tableHtml.append(trHtml);
	        		$("#"+discountType+"_selectedBillSubject").append(tableHtml);
	        	});
	        	_this._mz_bindBillSubjectRemove();//绑定删除方法
	        }
    	},
    	//生成赠品div（查看、修改）
    	_generategiftProductDiv :function(discountType, giftProductList){
    		var _this = this;
    		var productList = eval(giftProductList)
			if(productList){
				$.each(productList, function(i){
					var _this = this;
					var tableHtml = $("<table width='100%' border='0' cellspacing='0' cellpadding='0'></table>");
	        		var trHtml = $("<tr></tr>");
	        		var td1 = $("<td style='display: none;'><input type='checkbox' id='"+_this.productId+"' name='sp_checkbox' class='checkbox'></td>");
	        		var td2 = $("<td>"+_this.productId+"</td>");
	        		var td3 = $("<td>"+_this.productName+"</td>");
	        		var td4 = $("<td>"+_this.tradeSeq+"</td>");//计费类型用tradeSeq暂存
	        		var td5 = $("<td style='padding:0px;'></td>");
	        			var td5_table = $("<table width='100%' border='0' class='height-small'>");
		        		$.each(eval(_this.usageList), function(i){
		        			var td5_tr = $("<tr></tr>");
		        			var td5_td = $("<td style='height:30px;'>"+this.serviceTypeDetail+"</td>");//业务类型
		        			td5_tr.append(td5_td);
		        			td5_table.append(td5_tr);
		        		});
		        		td5.append(td5_table);
		        	var td6 = $("<td style='padding:0px;'></td>");
	        			if(_this.billingType == 'STANDARD_GROUP_TYPE'){//标准组合套餐产品
	        				td6 = $("<td style='padding:0px;'>"+_this.totalPrice+"</td>");
	        			}else{//阶梯组合套餐产品
	        				td6 = $("<td style='padding:0px;'></td>");
	        				var td6_table = $("<table width='100%' border='0' class='height-small'>");
	        				$.each(eval(_this.usageList), function(i){
	        					var td6_tr = $("<tr></tr>");
	        					var td6_td = $("<td style='height:30px;'>"+this.price+"</td>");//单价／总价
	        					td6_tr.append(td6_td);
	        					td6_table.append(td6_tr);
	        				});
	        				td6.append(td6_table);
	        			}
		        	var td7 = $("<td><img class='img_remove_class' src='"+_base+"/resources/baasop/images/stepclose.png'></td>");
	        		trHtml.append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7);
	        		tableHtml.append(trHtml);
	        		$("#"+discountType+"_selectedSalableProduct").append(tableHtml);
				});
				_this._mz_bindBillSubjectRemove();//绑定删除方法
			}
    	},
    	//格式化时间
    	_timestampToDate :function(format, timestamp){
			if(timestamp!=null){
				return (new Date(parseFloat(timestamp))).format(format);
			}else{
				return null;
			}
		},
		//当前tab切换（查看、修改）
		_showDiscountCnt: function(index){
			if(index=='mz'){
				$('.Discountb, .Discountc, .Discountd, .Discounte').parent().remove();
				$('.Discount-cnt2, .Discount-cnt3, .Discount-cnt4, .Discount-cnt5').remove();
    			$('.Discount-cnt1').show();
			}
			if(index=='mj'){
				$('.Discounta, .Discountc, .Discountd, .Discounte').parent().remove();
				$('.Discount-cnt1, .Discount-cnt3, .Discount-cnt4, .Discount-cnt5').remove();
				$('.Discount-cnt2').show();
			}
			if(index=='dz'){
				$('.Discounta, .Discountb, .Discountd, .Discounte').parent().remove();
				$('.Discount-cnt1, .Discount-cnt2, .Discount-cnt4, .Discount-cnt5').remove();
				$('.Discount-cnt3').show();
			}
			if(index=='bd'){
				$('.Discounta, .Discountb, .Discountc, .Discounte').parent().remove();
				$('.Discount-cnt1, .Discount-cnt2, .Discount-cnt3, .Discount-cnt5').remove();
				$('.Discount-cnt4').show();
			}
			if(index=='fd'){
				$('.Discounta, .Discountb, .Discountc, .Discountd').parent().remove();
				$('.Discount-cnt1, .Discount-cnt2, .Discount-cnt3, .Discount-cnt4').remove();
				$('.Discount-cnt5').show();
			}
    	}
    	//＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝全部-修改方法  end  ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
    	/**_bindValidator: function(){
   		    var validator = new Validator({
   		        element: '#discount-step2',
   		        onFormValidated: function(err, results, form) {
   		            window.console && console.log && console.log(err, results, form);
   		        },
   		        failSilently: true
   		    });
   		    
   		    validator.addItem({
   		        element: '#discountName',
   		        required: true,
   		        rule: 'minlength{min: 5} maxlength{max:20}',
   		        alertFlag: true
   		    })
   		    .addItem({
   		        element: '#effectiveDate',
   		        required: true,
   		        errormessage:"请选择生失效日期"
   		    })
    	}*/
    });
    
    module.exports = AddBillDiscountPager
});