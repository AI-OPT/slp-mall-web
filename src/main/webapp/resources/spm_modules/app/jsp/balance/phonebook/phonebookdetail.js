define('app/jsp/balance/phonebook/phonebookdetail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index'),
    Calendar = require('arale-calendar/1.1.2/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    require("valuevalidator/jquery.valuevalidator.js");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var PhoneBookDetailPager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//查询
            "click [id='BTN_QUERY']":"_queryPhoneBooks",
            "click [id='BTN_REFRESH']":"_queryPhoneBooks",
            "click [id='BTN_DELETE']":"_deletePhoneBooks",
            "click [id='BTN_BATCH_IMPORT']":"_showBatchImportWindow",
            "click [id='BTN_ADD_PHONEBOOK']":"_showAddPhoneBookWindow",
            "click [id='CHECK_ALL']":"_checkAll",
            "click [id='HREF_ADD_ONE']":"_addOnePhoneBook",
            "click [id='HREF_ADD_BATCH']":"_showAddBatchPhoneBook",
            "click [id='BTN_INPUT_ROW']":"_confirmInputRow",
            "click [id='BTN_SAVE_BATCH_ADD']":"_submitBatchSaveEdit"	
            	
        },
    	//重写父类
    	setup: function () {
    		PhoneBookDetailPager.superclass.setup.call(this);
    		this._init(); 
    	},
    	
    	_init: function(){ 
    		var _this = this;
    		$('.trash-close').click(function(){
    			$('.eject-mask').fadeIn(100);
    			$('.eject-samll').slideDown(200);
    		});
    		$('.eject-samll-title .img').click(function(){
    			$('.eject-mask').fadeOut(100);
    			$('.eject-samll').slideUp(150);
    		});
    		$('.eject-samll-confirm .close-btn').click(function(){
    			$('.eject-mask').fadeOut(100);
    			$('.eject-samll').slideUp(150);
    		}); 
    	},
    	
    	_delBatchEditRow: function(index){
    		var data = this.batcheditdata?this.batcheditdata:[];
    		if(data.length-1>=index){
    			data.splice(index,1);
    		}
    		this.renderBatchEditPhoneBooks(data);
    	},
    	
    	_confirmInputRow: function(){
    		var row = $.trim($("#INPUT_ROW").val());
    		if(isNaN(row)){
    			alert("请输入数字");
    			return;
    		}
    		if(row<1 || row>100){
    			alert("请输入数字[2~100]");
    			return;
    		}
    		
    		var data = this.batcheditdata?this.batcheditdata:[];
    		for(var i=0;i<row;i++){
    			data.push({
        			telName: "",
        			telMp: ""
        		});
    		}
    		this.renderBatchEditPhoneBooks(data);
    		
    	},
    	
    	_addOnePhoneBook: function(){
    		var data = this.batcheditdata?this.batcheditdata:[];
    		data.push({
    			telName: "",
    			telMp: ""
    		});
    		this.renderBatchEditPhoneBooks(data);
    	},
    	
    	renderBatchEditPhoneBooks: function(data){
    		var _this=this;
    		var template = $.templates("#PhoneBooksBatchEditImpl");
            var htmlOutput = template.render(data);
            $("#TBODY_PhoneBooksBatchEdit").html(htmlOutput);
            
            $("[name='DEL_BATCH_EDIT_ROW']").bind("click",function(){
    			var index = $(this).attr("index");
    			_this._delBatchEditRow(index);
    		});
            $("[name='BATCH_TEL_NAME']").bind("blur",function(){
            	var telName=$(this).val();
            	var index = $(this).attr("index");
            	_this._updateTelNameBatchEditData(index,telName);
            });
            
            $("[name='BATCH_TEL_MP']").bind("blur",function(){
            	var telMap=$(this).val();
            	var index = $(this).attr("index");
            	_this._updateTelMpBatchEditData(index,telMap);
            });
    	},
    	
    	_updateTelNameBatchEditData: function(index,telName){
    		var _this = this;
    		var arr=$.grep(_this.batcheditdata,function(d,i){
    			return i==index;
    		});
    		if(!arr || arr.length==0){
    			return;
    		}
    		arr[0].telName = telName;
    	},
    	
    	_updateTelMpBatchEditData: function(index,telMp){
    		var _this = this;
    		var arr=$.grep(_this.batcheditdata,function(d,i){
    			return i==index;
    		});
    		if(!arr || arr.length==0){
    			return;
    		}
    		arr[0].telMp = telMp;
    	},
    	
    	_showAddBatchPhoneBook: function(){
    		
    	},
    	
    	_renderBatchEditPhoneBooks: function(){
    		
    	},
    	
    	_checkAll: function(){ 
    		if ($("#CHECK_ALL").prop("checked")) {
    			$("input[name='CHEK_TEL_NO']").prop("checked", true);  
    	    } else {  
    	    	$("input[name='CHEK_TEL_NO']").prop("checked", false);  
    	    }
    	},
    	
    	_showBatchImportWindow: function(){
    		
    	},
    	
    	_showAddPhoneBookWindow: function(){
    		this.batcheditdata = [];
    		this.renderBatchEditPhoneBooks(this.batcheditdata);
    		$("#INPUT_ROW").val("");
    	},
    	
    	_submitBatchSaveEdit: function(){
    		var _this = this;
    		var arr = this.batcheditdata?this.batcheditdata:[];
    		if(arr.length==0){
    			alert("没有需要保存的通信录");
    			return ;
    		}
    		$.each(arr,function(i,o){
    			o.userId = _this.get("userId");
    			o.telGroupId = _this.get("telGroupId");
    		});
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/batchAddUserPhonebooks",
				data: {
					datas: JSON.stringify(arr)
				},
				success: function(data){
					alert("删除成功");  
					_this._queryPhoneBooks();
				}
			});
    		
    	},
    	
    	_deletePhoneBooks: function(){
    		var _this = this;
    		var checkboxs=$("input[name='CHEK_TEL_NO']:checked");
    		if(checkboxs.length==0){
    			alert("请选择删除记录");
    			return;
    		}
    		var recordIds = "";
    		$.each(checkboxs,function(i,o){
    			var telNo = $(o).val();
    			recordIds+=telNo+","
    		});
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: true,
				message: "正在处理...",
				url: _base+"/account/phonebook/batchDeleteUserPhonebooks",
				data: {
					recordIds: recordIds
				},
				success: function(data){
					alert("删除成功");  
					_this._queryPhoneBooks();
				}
			});
    		
    	},
    	
    	_queryPhoneBooks: function(){
    		var _this = this;
    		$("#pagination-ul").runnerPagination({
    			url: _base+"/account/phonebook/queryUserPhonebooks",
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	 			message: "正在查询",
	            data : {
					userId: this.get("userId"),
					telGroupId: this.get("telGroupId"),
					provinceCode: $("#provinceCode").val(),
					basicOrgId: $("#basicOrgId").val(),
					telName: $("#telName").val(),
					telMp: $("#telMp").val()
				},
	           	pageSize: PhoneBookDetailPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#PhoneBooksImpl");
	                    var htmlOutput = template.render(data);
	                    $("#TBODY_PHONEBOOKS").html(htmlOutput);
	            	}else{
    					$("#TBODY_PHONEBOOKS").html("没有搜索到相关信息");
	            	}
	            }
    		}); 
    	},
    	
    });
    
    module.exports = PhoneBookDetailPager
});

