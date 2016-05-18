define('app/jsp/standardfee/addStandPostage', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging'),
//    Validator = require('arale-validator/0.10.2/index-debug'),
    AjaxController=require('opt-ajax/1.0.0/index');
    
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var AddStandPostagePager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"click #saveForm":"saveForm"
        },
    	//重写父类
    	setup: function () {
    		AddStandPostagePager.superclass.setup.call(this);
    		this._bindEvents();
    		this._setMenu();
    		this._bindDialog();
    		this._getServiceType();
    		this.getStandardUnit();
    	},
    	_setMenu:function () {
    		var tag = "标准资费添加";
    		if($('#standardId').val()!=null && $('#standardId').val()!='undefined' &&$('#standardId').val()!=""){
    			tag = '标准资费修改';
    		}
			setBreadCrumb("标准资费管理", tag);
			$("#mnu_bmc_config").addClass("current");
		},
    	saveForm:function(){
    		
    		//验证
    		var successFlag = true;
    		var msg = "";
    		if($('#priceName').val()==''){
    			msg = "标准业务资费名称不能为空";
    			successFlag = false;
    		}else if($('#priceName').val().length > 15){
    			msg = "标准业务资费长度必须小于15";
    			successFlag = false;
    		}else if($('#unit').val()==''){
    			msg = "请选择标准资费单位配置";
    			successFlag = false;
    		}else if($('#price').val()==''){
    			msg = "标准资费价格配置不能为空";
    			successFlag = false;
    		}
    		if(!successFlag){
    			 var d = Dialog({
                     content:msg,
                     ok:function(){
                         this.close();
                     }
                 });
                 d.showModal();
    			return;
    		}
    		//表单提交
    		var periods = [];
    		var Period = {
				serviceType : '',
				subServiceType : '',
				amountMin:'',
				amountMax:'',
				unit:''
    		}
    		for(var i=1; i<2; i++){
    			var period = {
					serviceType : $("#standardPeriod"+i).find("[name='serviceType']").val(),
					subServiceType : $("#standardPeriod"+i).find("[name='subServiceType']").val(),
					amount:$("#standardPeriod"+i).find("[name='amount']").val(),
					unit:$("#standardPeriod"+i).find("[name='unit']").val()
    			};
    			periods.push(period);
    		}
    		var periodsJsonStr = JSON.stringify(periods); 
    		$('#periodsJsonStr').val(periodsJsonStr);
    		
            if(!successFlag){
            	var msg = "";
                var d = Dialog({
                    content:"选择的列中至少有一列要作为索引",
                    ok:function(){
                        this.close();
                    }
                });
                d.showModal();
                return;
            }else{
                ajaxController.ajax({
                    type: "post",
                    dataType : "json",
                    url: _base+"/standardFee/save",
                    processing: true,
                    message: "正在加载，请等待...",
                    data:$('#addForm').serializeArray(),
                    success: function(data){
                        if(data){
                            var d = Dialog({
                                content:data.statusInfo,
                                ok:function(){
                                    this.close();
                                    history.go(-1);
                                }
                            });
                            d.showModal();
                        }
                    }
                });
            }
        },
    	_bindDialog:function(){
    		 $("#addStandCycel").click(function() {
    			 if(!$("#standardPeriod2").is(":visible")){
 		    		$("#standardPeriod2").show();
 		    	}else{
 		    		var d = Dialog({
 	                    content:"最多只能有两个标准资费周期",
 	                    ok:function(){
 	                        this.close();
 	                    }
 	                });
 	                d.showModal();
 	                return;
 		    	}
    		     });
    	},
    	_getServiceType: function() {
    		var json = this.getSelectValue(_base + '/param/getServiceType');
    		var pThis = this;
    		$("select[name='serviceType']").each(function(){
    			  var _this = this;
				  var selectedValue = $(this).attr('oldValue');
				  $.each(
							json,
							function(index, item) {
								// 循环获取数据
								var paramName = json[index].paramName;
								var paramCode = json[index].paramCode;
								var addHtml = "" ;
								if(selectedValue == paramCode){
									addHtml = 'selected="selected"';
								}
								$(_this)
										.append('<option cid="'+json[index].id+'" value="'+paramCode+'" '+addHtml+'>'+paramName+'</option>');
							});
				  pThis._getServiceDetail(_this);
					});
    	},
    	_getServiceDetail: function(obj) {
    		 var serverId = $(obj).find("option:selected").attr('cid');
			 var json = this.getSelectValue(_base + '/param/getServiceDetail?serverId='+serverId);
    		$("select[name='subServiceType']").each(function(){
    			  var _this = this;
    			  $(_this).html('<option value="">请选择</option>');
				  var selectedValue = $(this).attr('oldValue');
				  $.each(
							json,
							function(index, item) {
								// 循环获取数据
								var paramName = json[index].paramName;
								var paramCode = json[index].paramCode;
								var addHtml = "" ;
								if(selectedValue == paramCode){
									addHtml = 'selected="selected"';
								}
								$(_this)
										.append('<option value="'+paramCode+'" '+addHtml+'>'+paramName+'</option>');
							});
					});
    	},
    	getStandardUnit: function() {
    		var json = this.getSelectValue(_base + '/param/getStandardUnit');
    		var selectedValue = $('#unit').attr('oldValue');
    		$.each(
					json,
					function(index, item) {
						// 循环获取数据
						var paramName = json[index].paramName;
						var paramCode = json[index].paramCode;
						var addHtml = "" ;
						if(selectedValue == paramCode){
							addHtml = 'selected="selected"';
						}
						$('#unit')
								.append('<option value="'+paramCode+'" '+addHtml+'>'+paramName+'</option>');
					});
    	},
    	getSelectValue: function(url){
    		var json;
    		$
			.ajax({
				url : url,
				type : "post",
				dataType : "html",
				timeout : "10000",
				showWait : true,
				async : false,
				error : function() {
					alert("服务加载出错");
				},
				success : function(data) {
					json = eval(data);
				}
			});
    		return json;
    	},
    	_bindEvents: function(){
    		var _this = this;
    		
    	}
    });
    
    module.exports = AddStandPostagePager
});