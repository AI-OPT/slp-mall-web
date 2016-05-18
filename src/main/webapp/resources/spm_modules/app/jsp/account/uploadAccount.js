define('app/jsp/account/uploadAccount', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    Calendar = require('arale-calendar/1.1.2/index-month'),
    AjaxController=require('opt-ajax/1.0.0/index');
    
    require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
    require('opt-paging/aiopt.pagination');
    
    require("app/util/jsviews-ext");
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("treegrid/js/jquery.cookie");
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var UploadAccountPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		FILE_MAX_SIZE: 10,
    		DEFAULT_PAGE_SIZE:5
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"blur [id='dataType']":"_checkDataType",
    		"blur [id='accountPeriod']":"_checkAccountPeriod",
    		"blur [id='dataObj']":"_checkDataObj",
    		"click [id='closeBtn']":"_closeDialog",
    		"click [id='uploadBtn']":"_uploadFile",
    		"click [id='uploadFile']":"_checkUploadData",
    		"change [id='uploadFile']":"_showFileMsg",
    		"click [id='dataObj']":"_checkServiceTypeSelect"
        },
    	//重写父类
    	setup: function () {
    		UploadAccountPager.superclass.setup.call(this);
    		this._bindCalendar();
    		this._searchImportLogs();
    		this._initPage();
    		this._initServiceType();
    	},
    	_initServiceType:function(){
    		if(serviceTypeList.length>0){
    			var objSelect = document.getElementById("dataObj").options;
    			for(var i=0;i<serviceTypeList.length;i++){
    				objSelect.add(new Option(serviceTypeList[i].columnDesc,serviceTypeList[i].columnValue));
    			}
    		}
    	},
    	_initPage: function(){
         	//导航
    		setBreadCrumb("结算配置管理","结算文件上传");
      		//左侧菜单选中样式
      		$("#mnu_smc_config").addClass("current");
      	},
    	_bindCalendar:function(){
      		new Calendar({trigger: '#accountPeriodEvent',output:"#accountPeriod"});
      	},
      	_checkServiceTypeSelect:function(){
      		if(serviceTypeList.length==0){
      			var msgDialog = Dialog({
					title: '提示',
					content: "【文件流水类型】不存在，请联系管理员配置相关信息"
				});
      			msgDialog.showModal();
      		}
      	},
    	_checkUploadDataValue:function(){
    		var dataType = jQuery.trim($("#dataType option:selected").val());
    		if(dataType == "" || dataType == null || dataType == undefined){
    			$("#uploadFile").attr("type","");
    			return false;
    		}
    		var accountPeriod = jQuery.trim($("#accountPeriod").val());
    		if(accountPeriod == "" || accountPeriod == null || accountPeriod == undefined){
    			$("#uploadFile").attr("type","");
    			return false;
    		}
    		var dataObj = jQuery.trim($("#dataObj option:selected").val());
    		if(dataObj == "" || dataObj == null || dataObj == undefined){
    			$("#uploadFile").attr("type","");
    			return false;
    		}
    		$("#uploadFile").attr("type","file");
    		return true;
    	},
    	_checkUploadData:function(){
    		var checkType = this._checkDataType();
    		var checkTime = this._checkAccountPeriod();
    		var checkObj = this._checkDataObj();
    		if(checkType&&checkTime&&checkObj){
    			$("#uploadFile").attr("type","file");
    			//$("#uploadFile").trigger("click");
    			return true;
    		}else{
    			$("#uploadFile").attr("type","");
    			return false;
    		}
    	},
    	_checkDataType:function(){
    		var dataType = jQuery.trim($("#dataType option:selected").val());
    		if(dataType == "" || dataType == null || dataType == undefined){
    			this._controlMsgText("dataTypeMsg","请选择【上传文件类型】");
    			this._controlMsgAttr("dataTypeMsgDiv",2);
    			return false;
    		}else{
    			this._controlMsgText("dataTypeMsg","");
    			this._controlMsgAttr("dataTypeMsgDiv",1);
    			return true;
    		}
    	},
    	_checkAccountPeriod:function(){
    		var accountPeriod = jQuery.trim($("#accountPeriod").val());
    		if(accountPeriod == "" || accountPeriod == null || accountPeriod == undefined){
    			this._controlMsgText("accountPeriodMsg","请选择【账期】");
    			this._controlMsgAttr("accountPeriodMsgDiv",2);
    			return false;
    		}else{
    			this._controlMsgText("accountPeriodMsg","");
    			this._controlMsgAttr("accountPeriodMsgDiv",1);
    			return true;
    		}
    	},
    	_checkDataObj:function(){
    		var dataObj = jQuery.trim($("#dataObj option:selected").val());
    		if(dataObj == "" || dataObj == null || dataObj == undefined){
    			this._controlMsgText("dataObjMsg","请选择【文件流水类型】");
    			this._controlMsgAttr("dataObjMsgDiv",2);
    			return false;
    		}else{
    			this._controlMsgText("dataObjMsg","");
    			this._controlMsgAttr("dataObjMsgDiv",1);
    			return true;
    		}
    	},
    	//控制显示内容
		_controlMsgText: function(id,msg){
			var doc = document.getElementById(id+"");
			doc.innerText=msg;
		},
		//控制显示内容
		_controlMsgHtml: function(id,msg){
			var doc = document.getElementById(id+"");
			doc.innerHTML=msg;
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
		//显示上传文件信息
		_showFileMsg:function(){
		    var fileupload = document.getElementById("uploadFile");
		    var fileLocation = fileupload.value;
		    if(fileLocation == "" || fileLocation == null || fileLocation == undefined){
		    	return false;
		    }
		    var fileType = fileLocation.substring(fileLocation.lastIndexOf("."));
		    var fileName,fileSize;
		    if (fileupload.files && fileupload.files[0]) {
		    	fileName = fileupload.files[0].name;
		        var size = fileupload.files[0].size;
		        fileSize = size/(1024 * 1024)
		    } else {
		        fileupload.select();
		        fileupload.blur();
		        var filepath = document.selection.createRange().text;
		        try {
		            var fso, f, fname, fsize;
		            fso = new ActiveXObject("Scripting.FileSystemObject");
		            f = fso.GetFile(filepath); //文件的物理路径
		            fileName = fso.GetFileName(filepath); //文件名（包括扩展名）
		            fsize = f.Size; //文件大小（bit）
		            fileSize = fsize / (1024*1024);
		        } catch (e) {
		        	var msgDialog = Dialog({
						title: '提示',
						content: e + "\n 跳出此消息框，是由于你的activex控件没有设置好,\n" +
						"你可以在浏览器菜单栏上依次选择\n" +
						"工具->internet选项->\"安全\"选项卡->自定义级别,\n" +
						"打开\"安全设置\"对话框，把\"对没有标记为安全的\n" +
						"ActiveX控件进行初始化和脚本运行\"，改为\"启动\"即可",
						ok: function () {
							this.close();
						}
					});
		        	msgDialog.showModal();
		            return false;
		        }
		    }
		    this._controlMsgAttr("uploadFileMsg",2);
		    this._controlMsgHtml("fileName",fileName);
		    fileSize = fileSize.toFixed(4);
		    if(fileSize > UploadAccountPager.FILE_MAX_SIZE){
		    	this._controlMsgHtml("fileSize","<span>"+fileSize+" M</span>");
		    }else{
		    	this._controlMsgHtml("fileSize",fileSize+" M");
		    }
		    this._controlMsgAttr("fileTypeError",1);
		    this._controlMsgAttr("fileSizeError",1);
		},
		//检查文件
		_checkFileData:function(){
		    var fileupload = document.getElementById("uploadFile");
		    var fileLocation = fileupload.value;
		    if(fileLocation == "" || fileLocation == null || fileLocation == undefined){
		    	return false;
		    }
		    var fileType = fileLocation.substring(fileLocation.lastIndexOf("."));
		    var fileName,fileSize;
		    if (fileupload.files && fileupload.files[0]) {
		    	fileName = fileupload.files[0].name;
		        var size = fileupload.files[0].size;
		        fileSize = size/(1024 * 1024)
		    } else {
		        fileupload.select();
		        fileupload.blur();
		        var filepath = document.selection.createRange().text;
		        try {
		            var fso, f, fname, fsize;
		            fso = new ActiveXObject("Scripting.FileSystemObject");
		            f = fso.GetFile(filepath); //文件的物理路径
		            fileName = fso.GetFileName(filepath); //文件名（包括扩展名）
		            fsize = f.Size; //文件大小（bit）
		            fileSize = fsize / (1024*1024);
		        } catch (e) {
		        	var msgDialog = Dialog({
						title: '提示',
						content: e + "\n 跳出此消息框，是由于你的activex控件没有设置好,\n" +
						"你可以在浏览器菜单栏上依次选择\n" +
						"工具->internet选项->\"安全\"选项卡->自定义级别,\n" +
						"打开\"安全设置\"对话框，把\"对没有标记为安全的\n" +
						"ActiveX控件进行初始化和脚本运行\"，改为\"启动\"即可",
						ok: function () {
							this.close();
						}
					});
		        	msgDialog.showModal();
		            return false;
		        }
		    }
		    var checkSize = true;
		    var checkType = true;
		    fileSize = fileSize.toFixed(4);
		    if(fileSize > UploadAccountPager.FILE_MAX_SIZE){
		    	//this._controlMsgHtml("fileSize","<span>"+fileSize+" M</span>");
		    	this._controlMsgAttr("fileSizeError",2);
		    	checkSize = false;
		    }else{
		    	//this._controlMsgHtml("fileSize",fileSize+" M");
		    	this._controlMsgAttr("fileSizeError",1);
		    	checkSize = true;
		    }
		    if(fileType == ".zip"){
		    	this._controlMsgAttr("fileTypeError",1);
		    	checkType = true;
		    }else{
		    	this._controlMsgAttr("fileTypeError",2);
		    	checkType = false; 
		    }
		    return checkSize&&checkType;
		},
		_closeDialog:function(){
			$("#uploadFile").val("");
			this._controlMsgAttr("uploadFileMsg",1);
		},
		//上传文件
		_uploadFile:function(){
			var _this = this;
			var checkUploadData = this._checkUploadData();
			var checkFileData = this._checkFileData();
			if(!(checkUploadData&&checkFileData)){
    			return false;
    		}
			var form = new FormData();
		    form.append("uploadFile", document.getElementById("uploadFile").files[0]); 
			
			// XMLHttpRequest 对象
		     var xhr = new XMLHttpRequest();
		     var uploadURL = _base+"/account/uploadToServer";
		     xhr.open("post", uploadURL, true);
		     
			 xhr.onreadystatechange = function() {
				if (xhr.readyState == 4) {// 4 = "loaded"
					if (xhr.status == 200) {
						var responseData = $.parseJSON(xhr.response);
						if(responseData.statusCode=="1"){
							var fileData = responseData.data;
							if(fileData){
								var filePosition = fileData.position;
								var fileName = fileData.name;
								_this._addUploadLog(filePosition,fileName);
								_this._closeDialog();
								return;
							}
						}
					} 
					var msgDialog = Dialog({
						title: '提示',
						content: "文件上失败",
						ok: function () {
							this.close();
						}
					});
					_this._closeDialog();
					msgDialog.showModal();
				}
			 };
			xhr.send(form);
		},
		_addUploadLog:function(filePosition,fileName){
			var _this = this;
			ajaxController.ajax({
				type : "POST",
				data : {
					"dataType":jQuery.trim($("#dataType option:selected").val()),
					"accountPeriod":jQuery.trim($("#accountPeriod").val()),
					"dataObj":jQuery.trim($("#dataObj option:selected").val()),
					"filePosition":filePosition,
					"fileName":fileName
				},
				dataType: 'json',
				url :_base+"/account/uploadFile",
				processing: true,
				message : "正在处理中，请稍候...",
				success : function(data) {
					var isSuccess = data.responseHeader.success;
					if(isSuccess){
						var msgDialog = Dialog({
			    	        title: '提示',
			    	        content: '文件上传成功',
			    	        ok: function () {
			    	        	_this._searchImportLogs();
			    	        	this.close();
			    	        }
						});
						msgDialog.showModal();
					}else{
						var msgDialog = Dialog({
			    	        title: '提示',
			    	        content: data.statusInfo
						});
						msgDialog.showModal();
					}
				}
			});
		},
		_searchImportLogs:function(){
			$("#pagination").runnerPagination({
				url: _base+"/account/searchImportLogList",
				method: "POST",
				dataType: "json",
				processing: true,
				data : {},
				pageSize: UploadAccountPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#importlogListTmpl");
						var htmlOut = template.render(data);
						$("#importlogList").html(htmlOut);
					}else{
						$("#importlogList").html("未搜索到信息");
					}
				}
			});
		}
		
    });
    module.exports = UploadAccountPager
});
