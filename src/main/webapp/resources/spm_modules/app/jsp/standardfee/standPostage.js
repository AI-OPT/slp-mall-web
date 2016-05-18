define('app/jsp/standardfee/standPostage', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
//    var ajaxController = new AjaxController();
    //定义页面组件类
    var StandPostagePager = Widget.extend({
    	
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
            "click #BTN_SEARCH":"_searchBtnClick",
//            "click .sp-setvalid":"showSetValidDiv"
//            "click .del-button":"_deleteStandFee",
            "click #PHONE_IDENTIFY":"_getPhoneVerifyCode",
            "click #CLOSE_POP":"_closeMessage"
        },
    	//重写父类
    	setup: function () {
    		StandPostagePager.superclass.setup.call(this);
    		this._bindEvents();
    		this._setMenu();
    		//初始化执行搜索
//    		this._searchStandPostage(1,StandPostagePager.DEFAULT_PAGE_SIZE);
    		this._searchBtnClick();
    		this._bindDeleteStandFee();
    		this._showSetValidDiv();
    		$("#PHONE_IDENTIFY").removeAttr("disabled"); //移除disabled属性
    		this._getServiceType();
//    		this.getServiceDetail();
    		this._getStandardStatus();
    		
    		this._bindSendMessage();
    	},
    	_setMenu:function () {
			setBreadCrumb("标准资费管理", "标准资费查询");
			$("#mnu_bmc_config").addClass("current");
		},
    	_bindSendMessage:function(){
    		var _this = this;
    		var func = function(){
    			$.ajax({
        			type : "POST",
        			url : _base + "/standardFee/updateStatus",
        			data: {
        				standardId : $("input[name='targetId']").val(),
        				status : $("input[name='feeStatus']:checked").val()
        			},
        			processing: true,
        			message : "正在处理中，请稍候...",
        			success : function(data) {
        				$("#tcc_div").hide();
        				var tip = Dialog({
        					title: '提示',
        					width: '200px',
        					height: '50px',
        					content: data.statusInfo
        				}).show();
        				
        	        	 $("#PHONE_IDENTIFY").removeAttr("disabled"); //移除disabled属性
        	             $('#PHONE_IDENTIFY').val('获取验证码');
        	             $('#phoneVerifyCode').val('');
        				
        				window.setTimeout(function(){
        					tip.close();
        					_this._searchBtnClick();
        				} ,1000);
        			}
        		});
    		};
    		$('#BTN_UPDATE_STATUS').bind("click", function(){
    			_this._verifyPhoneCode(func);
    		});
    	},
    	_bindDeleteStandFee:function(){
    		var pThis = this;
			$(".del-button").bind("click", function(){
				var _this = this;
				var status = $(_this).attr('status');
				if(status=='ACTIVE'){
					var da = Dialog({
						content:"生效状态下的产品无法删除",
						okValue:"确定",
						ok:function () {
							this.close();
						}
					});
					da.showModal();
					return;
				}
				var d = Dialog({
                    content:"确定删除？",
                    ok:function(){
                    	$.ajax({
                    	        method: "POST",
                    	        url:  _base+"/standardFee/delete",
                    	        dataType: "json",
                    	        showWait: true,
                    	        async: false,
                    	        data: {
                    	            "standardId": $(_this).attr('standardId'),
                    	            "comments": $(_this).attr('comments')
                    	        },
                    	        message: "正在加载数据..",
                    	        success: function (data) {
                    	        	 var d = Dialog({
                                         content: data.statusInfo,
                                         ok:function(){
                                             this.close();
                                         }
                                     });
                                     d.showModal();
                                     pThis._searchBtnClick();
                    	        }
                    	    });
                        this.close;
                    },
                    okValue:"确定",
                    cancel:function(){
                    	this.close;
                    },
                    cancelValue:"取消"
                });
                d.showModal();
			});
    	},
    	_bindEditStandFee:function(){
			$(".edit-button").bind("click", function(){
				var _this = this;
				var status = $(_this).attr('status');
				if(status=='ACTIVE'){
					var da = Dialog({
						content:"生效状态下的产品无法编辑",
						okValue:"确定",
						ok:function () {
							this.close();
						}
					});
					da.showModal();
					return;
				}
				window.location.href = _base+"/standardFee/toUpdate?standardId="+$(_this).attr('standardId');
			});
    	},
    	_bindRelateStandFee:function(){
			$(".relate-button").bind("click", function(){
				var _this = this;
				var status = $(_this).attr('status');
				if(status=='ACTIVE'){
					var da = Dialog({
						content:"生效状态下的产品无法关联详单科目",
						okValue:"确定",
						ok:function () {
							this.close();
						}
					});
					da.showModal();
					return;
				}
				window.location.href = _base+"/standardFee/toRelateSubject?standardId="+$(_this).attr('standardId');
			});
    	},
    	_showSetValidDiv:function(){
    		$(".sp-setvalid").bind("click", function(){
    			
                var status = $(this).attr('status');
                $("input[name='targetId']").val($(this).attr('sid'));
                $("#tcc_div input:radio").removeAttr("checked");
                $("#valid").removeAttr("disabled");
                $("#invalid").removeAttr("disabled");
                if(status=='ACTIVE'){
                    $("#invalid").prop("checked","checked");
                    $("#valid").attr("disabled","disabled");
                }else{
                    $("#valid").prop("checked","checked");
                    $("#invalid").attr("disabled","disabled");
                }
    			$('#tcc_div').show();
			});
		},
    	_searchBtnClick: function(){
    		var _this = this;
    		var url = _base+"/standardFee/getList";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data : $('#queryForm').serializeArray(),
	           	pageSize: StandPostagePager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#standardListTemple");
    					var htmlOutput = template.render(data);
    					$("#standardData").html(htmlOutput);
    					_this._bindDeleteStandFee();
    					_this._bindEditStandFee();
    					_this._bindRelateStandFee();
    					_this._showSetValidDiv();
	            	}else{
    					$("#standardData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	
    	_bindEvents: function(){
    		var _this = this;
    		
    	},
    	_getServiceType: function() {
    		var _this = this;
    		this.setSelectValue(_base + '/param/getServiceType', function(data){
    			var json = eval(data);
				$
						.each(
								json,
								function(index, item) {
									// 循环获取数据
									var paramName = json[index].paramName;
									var paramCode = json[index].paramCode;
									$("#serviceType")
											.append('<option cid="'+json[index].id+'" value="'+paramCode+'">'+paramName+'</option>');
								});
				$("#serviceType")
						.append("<label id='accesstype_error'></label>");
				_this._getServiceDetail();
    		});
    	},
    	_getServiceDetail: function() {
    		var serverId = $("#serviceType").find("option:selected").attr('cid');
    		if(serverId==undefined || serverId==''){
    			$("#serviceTypeDetail").val("");
    			$("#serviceTypeDetail").attr("disabled",true);
    		}else{
    			$("#serviceTypeDetail").removeAttr("disabled",true);
    			this.setSelectValue(_base + '/param/getServiceDetail?serverId='+serverId, function(data){
        			$("#serviceTypeDetail").html('<option value="">请选择</option>');
        			var json = eval(data);
    				$
    						.each(
    								json,
    								function(index, item) {
    									// 循环获取数据
    									var paramName = json[index].paramName;
    									var paramCode = json[index].paramCode;
    									$("#serviceTypeDetail")
    											.append('<option value="'+paramCode+'">'+paramName+'</option>');
    								});
    				$("#serviceTypeDetail")
    						.append("<label id='accesstype_error'></label>");
        		});
    		}
    	},
    	_getStandardStatus: function() {
    		this.setSelectValue(_base + '/param/getStandardStatus', function(data){
    			var json = eval(data);
				$
						.each(
								json,
								function(index, item) {
									// 循环获取数据
									var paramName = json[index].paramName;
									var paramCode = json[index].paramCode;
									$("#priceState")
											.append('<option value="'+paramCode+'">'+paramName+'</option>');
								});
				$("#priceState")
						.append("<label id='accesstype_error'></label>");
    		});
    	},
    	setSelectValue: function(url, func){
    		$
			.ajax({
				url : url,
				type : "post",
				async : true,
				dataType : "html",
				timeout : "10000",
				error : function() {
					alert("服务加载出错");
				},
				success : function(data) {
					func(data);
				}
			});
    	}
    	
    });
    
    module.exports = StandPostagePager
});

