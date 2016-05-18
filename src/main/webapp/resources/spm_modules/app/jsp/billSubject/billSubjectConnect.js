define('app/jsp/billSubject/drSubjectConnect', function (require, exports, module) {
    'use strict';
    var $ = require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
    AjaxController = require('opt-ajax/1.0.0/index');
    
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    
    require("twbs-pagination/jquery.twbsPagination.min");
    require("opt-paging/aiopt.pagination");
    
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var BillSubjectConnectPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		//查询
            "click #BTN_ADD":"_addSubject",
            "click #BTN_SAVE":"_saveConnect",
            "click #BTN_REMOVE":"_removeSubject"
        },
    	//重写父类
    	setup: function () {
    		BillSubjectConnectPager.superclass.setup.call(this);
    		this._bindEvents();
    		this._searchBtnClick();
    	},    		
    	_bindEvents: function(){
    		var _this = this;
    		$('#tabName').bind('keypress', function(event){
				if(event.keyCode == "13"){
					_this._searchBtnClick();	
				}
			});
    	},
    	_searchBtnClick: function(){
    		var url = _base+"/billSubject/getBillList";
    		$("#pagination-ul").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data: {    	            	
//	            	subjectId:$('#subjectId').val(),//搜索框里输入的值
//	            	subjectName:$("#subjectName").val(),
//    				subjectType:$("#subjectType").val()
	            },
	           	pageSize: BillSubjectConnectPager.DEFAULT_PAGE_SIZE,
	           	visiblePages:5,
	            message: "正在为您查询数据..",
	            render: function (data) {
	            	if(data != null && data != 'undefined' && data.length>0){
	            		var template = $.templates("#billSubjectDataTmpl");
    					var htmlOutput = template.render(data);
    					$("#billSubjectData").html(htmlOutput);
	            	}else{
    					$("#billSubjectData").html("没有搜索到相关信息");
	            	}
	            }
    		});
    	},
    	_addSubject: function(){
    		var html = "";
    		$("#billSubjectData").find("span").each(function(){
    			if($(this).find("a").hasClass("feiy-cj")){
    				html += '<span class="list-cj" onclick="pager._checkSubject(this)">'+$(this).html()+'</span>'; 
    				$(this).remove();
    			} 
    		});
    		$("#connectSubjectData").append(html);
    	},//
    	
    	_removeSubject: function(){
    		var html = "";
    		$("#connectSubjectData").find("span").each(function(){
    			if($(this).find("a").hasClass("feiy-cj")){
    				html += '<span class="list-cj" onclick="pager._checkSubject(this)">'+$(this).html()+'</span>'; 
    				$(this).remove();
    			} 
    		});
    		$("#billSubjectData").append(html);
    	},//
    	
    	
    	_saveConnect: function(){
    		var subjectIdArray = new Array();
    		var subjectId = $("#subjectId").val();
    		$("#connectSubjectData").find("span").each(function(i){
    			subjectIdArray[i] = $(this).find("a").attr("subjectid");
    		});
    		JSON.stringify(subjectIdArray),
    		 ajaxController.ajax({
                 type: "post",
                 dataType : "json",
                 url: _base+"/drSubject/saveConnectSubject",
                 processing: true,
                 message: "正在加载，请等待...",
                 data:{
                 	subjectId:subjectId,
                     subjectIds:JSON.stringify(subjectIdArray)
                 },
                 success: function(data){
                     if(data){
                         var d = Dialog({
                             content:"保存成功",
                             ok:function(){
                                 this.close();
                             }
                         });
                         d.showModal();
                     }
                 }
             });
    		
    		
    	},//
    	
    	
    	_checkSubject : function( curr){
    		if($(curr).find("a").hasClass("feiy-cj")){
    			$(curr).find("a").removeClass("feiy-cj");
    		}else{
    			$(curr).find("a").addClass("feiy-cj");
    		}
    	},//end of _shmDelete
    	
    	//删除
    	_deleteSubject: function(curr){
    		var _this = this;
    		Dialog({
    	        title: '提示',
    	        content: '确定要删除该表吗?',
    	        width: '300px',
    	        height: '60px',
    	        okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
    	            $.ajax({
        				type : "POST",
        				url :_base+"/drSubject/deleteSubject",
        				data: {
        					subjectId : $(curr).attr('subjectid')
        				},
        				processing: true,
        				message : "正在处理中，请稍候...",
        				success : function(data) {
        					pager._searchBtnClick();;//刷新页面
        					var str = (data.data=='000000'?'表删除成功':'由于参数错误表未能成功删除表');
        					alert(data.statusInfo );
        				}
        			});
    	        },
    	        cancelValue: '取消',
    	        cancel: function () {}
    	    }).show();
    	}//end of _deleteTable
    	
    	
    	
    	
    	
    });
    
    module.exports = BillSubjectConnectPager
});

