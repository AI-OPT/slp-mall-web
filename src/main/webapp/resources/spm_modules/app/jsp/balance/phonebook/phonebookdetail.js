define('app/jsp/balance/phonebook/phonebookdetail', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    Uploader = require('arale-upload/1.2.0/index'),
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
            "click [id='BTN_DELETE']":"_checkDeleteData",
            "click [id='deletePhone']":"_deletePhoneBooks",
            "click [id='BTN_BATCH_IMPORT']":"_showBatchImportWindow",
            "click [id='BTN_ADD_PHONEBOOK']":"_showAddPhoneBookWindow",
            "click [id='CHECK_ALL']":"_checkAll",
            "click [id='HREF_ADD_ONE']":"_addOnePhoneBook",
            "click [id='HREF_ADD_BATCH']":"_showAddBatchPhoneBook",
            "click [id='BTN_INPUT_ROW']":"_confirmInputRow",
            "click [id='BTN_SAVE_BATCH_ADD']":"_submitBatchSaveEdit",
            "change [id='uploadFile']":"_setUploadFile",
            "click [id='uploadBtn']":"_uploadFile"
            	
        },
    	//重写父类
    	setup: function () {
    		PhoneBookDetailPager.superclass.setup.call(this);
    		this._init(); 
    		this._initProvices();
    		this._initBasicOrgs();
    		this._queryPhoneBooks();
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
    	
    	_initProvices: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/getProvices", 
				success: function(data){
					var arr = data.data?data.data:[];
					$.each(arr,function(i,d){
						$("#provinceCode").append("<option value='"+ d.areaCode+"'>"+d.areaName+"</option>");
					});
				}
			});
    	},
    	
    	_initBasicOrgs: function(){
    		var _this = this;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/getBasicOrgs", 
				success: function(data){
					var arr = data.data?data.data:[];
					$.each(arr,function(i,d){
						$("#basicOrgId").append("<option value='"+ d.columnValue+"'>"+d.columnDesc+"</option>");
					});
				}
			});
    	},
    	
    	_setUploadFile: function(){
    		var fileName = $("#uploadFile").val();
    		$("#TEXT_FILE_NAME").val(fileName);
    	},
    	_checkUploadFile: function(){
    		var fileName = $("#uploadFile").val();
    		if(fileName==""){
    			alert("请选择文件");
    			return ;
    		}
    		var FileListType="xls,xlsx";
    		var destStr = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)
    		if(FileListType.indexOf(destStr) == -1){
    		  alert("只允许上传EXCEL文件。格式支撑xls,xlsx");
    		  return false;
    		} 
    		return true;
    	},
    	
		_uploadFile:function(){
			var _this = this;
			
			var valid = _this._checkUploadFile();
			if(!valid){
				$("#uploadFile").val("");
				$("#TEXT_FILE_NAME").val("");
				return ;
			}
			var form = new FormData();
		    form.append("uploadFile", document.getElementById("uploadFile").files[0]); 
			
			// XMLHttpRequest 对象
		     var xhr = new XMLHttpRequest();
		     xhr.upload.addEventListener("progress", uploadProgress, false);
		     var uploadURL = _base+"/account/phonebook/uploadPhoneBooks?telGroupId="+this.get("telGroupId");
		     xhr.open("post", uploadURL, true);
		     
			 xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {// 4 = "loaded"
					if (xhr.status == 200) {
						var responseData = $.parseJSON(xhr.response);
						if(responseData.statusCode=="1"){
							//alert(responseData.data);
							$('.eject-mask').fadeOut(100);
							$('.eject-medium').slideUp(150);
							_this._queryPhoneBooks();
						}
					}  
				}
			 };
			xhr.send(form);
		},
		
		/**
		 * 上传进度
		 */
		_uploadProgress:function(evt) {
			if (evt.lengthComputable) {
			var percentComplete = Math.round(evt.loaded * 100 / evt.total);
			document.getElementById('progressNumber').innerHTML = '<font color=red>当前进度:'+percentComplete.toString() + '%</font>';
			}
			else {
			document.getElementById('progressNumber').innerHTML = 'unable to compute';
			}
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
    		
    		$('#samll-block').slideUp(150);
    		
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
    		$("#uploadFile").val("");
			$("#TEXT_FILE_NAME").val("");
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
    		
    		var validPass= true;
    		$.each(arr,function(i,o){
    			var telName = o.telName;
    			var telMp =o.telMp;
    			var onepass=true;
    			if(_this.checkBlank(telName)){
    				o.error = "请输入姓名";
    				$("#SPAN_ERROR_"+i).html("请输入姓名");
    				onepass = false;
    			}
    			if(_this.checkBlank(telMp)){
    				o.error = "请输入手机号码";
    				$("#SPAN_ERROR_"+i).html("请输入手机号码");
    				onepass = false;
    			}
    			if(!_this.checkMobilePhone(telMp)){
    				o.error = "手机号码格式有误";
    				$("#SPAN_ERROR_"+i).html("手机号码格式有误");
    				onepass = false;
    			}
    			if(onepass){
    				o.error = "";
    				$("#SPAN_ERROR_"+i).html("");
    			}else{
    				validPass = false;
    			}
    			o.userId = _this.get("userId");
    			o.telGroupId = _this.get("telGroupId");
    		});
    		if(!validPass){
    			return ;
    		}
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/batchAddUserPhonebooks",
				data: {
					datas: JSON.stringify(arr)
				},
				success: function(data){
					//alert(data.data);  
					$('.eject-mask').fadeOut(100);
					$('.eject-large').slideUp(150);
					_this._queryPhoneBooks();
				}
			});
    		
    	},
    	
    	checkBlank: function(value){
    		var v = $.trim(value);
    		return v==""?true:false;
    	},
    	
    	checkMobilePhone: function(value){
    		var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;
			var valid =  (re.test(value))?true:false;	
			return valid;
    	},
    	
    	_checkDeleteData:function(){
    		var checkboxs=$("input[name='CHEK_TEL_NO']:checked");
    		if(checkboxs.length==0){
    			$('.eject-mask').fadeIn(100);
    			$('.eject-samll-icon').slideDown(200);
    			return;
    		}
    		$('.eject-mask').fadeIn(100);
    		$('.eject-samll').slideDown(200);
    	},
    	
    	_deletePhoneBooks: function(){
    		var _this = this;
    		var recordIds = "";
    		var checkboxs=$("input[name='CHEK_TEL_NO']:checked");
    		$.each(checkboxs,function(i,o){
    			var telNo = $(o).val();
    			recordIds+=telNo+","
    		});
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				message: "正在处理...",
				url: _base+"/account/phonebook/batchDeleteUserPhonebooks",
				data: {
					recordIds: recordIds
				},
				success: function(data){
					//alert("删除成功");  
					$('.eject-mask').fadeOut(100);
					$('.eject-samll').slideUp(150);
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
	 			processing: false,
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

