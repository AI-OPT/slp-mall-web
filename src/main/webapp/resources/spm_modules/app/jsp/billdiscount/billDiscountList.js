define('app/jsp/billdiscount/billDiscountList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    //AjaxController=require('opt-ajax/1.0.0/index');
    Dialog = require("artDialog/src/dialog"),
    //Paging = require('paging/0.0.1/paging'),
    Calendar = require('arale-calendar/1.1.2/index'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    SendMessageUtil = require("app/util/sendMessage");
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("app/util/jsviews-ext");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    
    //实例化AJAX控制处理对象
    //var ajaxController = new AjaxController();
    if(this.options.startPage<1 || this.options.startPage>this.options.totalPages){
    	throw new Error("Start page option is incorrect")
    }
    //定义页面组件类
    var BillDiscountPager = Widget.extend({
    	Implements: SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 6
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
            "click #BTN_SEARCH":"_searchBtnClick",
            "click #PHONE_IDENTIFY":"_getPhoneVerifyCode",//此方法在SendMessageUtil里
            "click .pop-close":"_closeMessage"//此方法在SendMessageUtil里
        },
    	//重写父类
    	setup: function () {
    		BillDiscountPager.superclass.setup.call(this);
    		this._initPage();
    		this._bindEvents();
    		this._bindCalendar();
    		this._searchBtnClick();
    		this._getDiscountTypeSelect();//加载账单优惠类型下拉菜单
    		this._bindShowUpdateStatusDiv();
    		this._bindUpdateStatusCommitBtnClick();
    	},
    	_bindEvents: function(){
    		var _this = this;
    		$('#productId, #productName, #discountType, #date-nothing').bind('keypress',function(event){
				if(event.keyCode == "13"){
					_this._searchBtnClick();
				}
			});
    	},
    	_initPage: function(){
    		setBreadCrumb("账单优惠管理","账单优惠产品查询");//导航
      		$("#mnu_bmc_config").addClass("current");//左侧菜单选中样式
      	},
    	//日期
    	_bindCalendar: function(){
    		new Calendar({
    			trigger: '#effectDate',
    			output: '#effectDate_be',
    			align: {
			      selfXY: [0, 0],
			      baseElement: '#effectDate_be',
			      baseXY: [0, '100%']
        		}
    		});
    		new Calendar({
    			trigger: '#expireDate',
    			output: '#expireDate_be',
    			align: {
			      selfXY: [0, 0],
			      baseElement: '#expireDate_be',
			      baseXY: [0, '100%']
        		}
    		});
    	},
    	//公共方法，加载账单优惠类型下拉菜单
		_getDiscountTypeSelect: function() {
			
			this._setSelectValue(_base + '/param/getDiscountType', null, function(data){
				if(data){
					var data = eval(data);
					$.each(data, function(index, item) {
						var paramName = data[index].paramName;
						var paramCode = data[index].paramCode;
						$("#discountType").append('<option cid="'+data[index].id+'" value="'+paramCode+'">'+paramName+'</option>');
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
    	//列表查询
    	_searchBtnClick: function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
	 			url: _base + "/billDiscount/getList",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : $('#queryForm').serializeArray(),
	           	pageSize: BillDiscountPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#billDiscountListTemple");
    					var htmlOutput = template.render(data);
    					$("#billDiscountData").html(htmlOutput);
    					_this._bindShowUpdateStatusDiv();//绑定更改状态事件
    					_this._bindUpdateBtnClick();//绑定不允许修改提示事件
    					_this._bindDeleteBtnClick();//绑定删除事件
	            	}else{
    					$("#billDiscountData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	//绑定事件：显示更新产品状态弹出框
    	_bindShowUpdateStatusDiv: function(){
    		$(".force").bind("click", function(){
    			$("#tcc_div").find("input[name='productId']").val($(this).attr('productId'));//给弹出层赋值
    			
    			var status = $(this).attr('status');
				$.each($("#tcc_div").find("input[name='feeStatus']"), function(){
					$(this).prop('disabled', false);
					if($(this).val()==status){
						$(this).prop('checked', false);
						$(this).prop('disabled', true);
					}else{
						$(this).prop('checked', true);
					}
				});
				
    			$("#tcc_div").show();
    		});
    	},
    	//绑定事件：更新产品状态提交
    	_bindUpdateStatusCommitBtnClick: function(){
    		var _this = this;
    		var func = function(){
    			$.ajax({
					type : "POST",
					url : _base + "/billDiscount/updateStatus",
					data: {
						productId : $("#tcc_div").find("input[name='productId']").val(),
						feeStatus : $("#tcc_div").find("input[name='feeStatus']:checked").val()
					},
					processing: true,
					message : "正在处理中，请稍候...",
					success : function(data) {
						$("#tcc_div").hide();
						_this._searchBtnClick();
						
						Dialog({
							width: '200px',
							height: '50px',
							content: data.statusInfo,
							okValue:"确定",
                            ok:function(){
                            	this.close;
                            }
						}).showModal();
						
						$("#PHONE_IDENTIFY").removeAttr("disabled"); //移除disabled属性
	       	            $('#PHONE_IDENTIFY').val('获取验证码');
	       	            $('#phoneVerifyCode').val('');
					}
				});
    		}
    		$('#BTN_UPDATE_STATUS').bind("click", function(){
    			_this._verifyPhoneCode(func);//此方法在SendMessageUtil里
    		});
    	},
    	//不允许修改提示事件
    	_bindUpdateBtnClick: function(){
    		$(".updFlag").bind("click", function(){
    			if($(this).attr('status') == '1'){
    				Dialog({
    					width: '200px',
    					height: '50px',
    					content: '已生效的产品不允许编辑',
    					okValue:"确定",
    					ok:function(){
    						this.close;
    					}
    				}).showModal();
    				return false;
    			}
    		});
    	},
    	//删除方法
    	_bindDeleteBtnClick: function(){
    		var _this = this;
    		$(".delFlag").bind("click", function(){
    			var _this_ = this;
    			if($(_this_).attr('status') == '1'){
    				Dialog({
        				width: '200px',
        				height: '50px',
        				content: '已生效的产品不允许删除',
        				okValue:"确定",
                        ok:function(){
                        	this.close;
                        }
        			}).showModal();
    				return false;
    			}
				Dialog({
					width: '200px',
    				height: '50px',
                    content:"确定要删除吗？",
                    okValue:"确定",
                    ok:function(){
                    	$.ajax({
                    		type : "POST",
                    		url : _base + "/billDiscount/delete",
                    		data: {
                    			productId : $(_this_).attr("productId")
                    		},
                    		processing: true,
                    		message : "正在处理中，请稍候...",
                    		success : function(data) {
                    			_this._searchBtnClick();
                    			Dialog({
                    				width: '200px',
                    				height: '50px',
                    				content: data.statusInfo,
                    				okValue:"确定",
                                    ok:function(){
                                    	this.close;
                                    }
                    			}).showModal();
                    		}
                    	});
                    },
                    cancelValue:"取消",
                    cancel:function(){
                    	this.close;
                    }
                }).showModal();
    		});
    	}
    });
    
    module.exports = BillDiscountPager
});
