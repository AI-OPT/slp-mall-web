define('app/jsp/drSubject/drSubjectConnect', function (require, exports, module) {
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
    var DrSubjectConnectPager = Widget.extend({
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
    		DrSubjectConnectPager.superclass.setup.call(this);
    		this._bindEvents();
    		this._setMenu();
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
    	_setMenu:function () {
			setBreadCrumb("计费配置管理", "关联详单科目");
			$("#mnu_bmc_config").addClass("current");
		},
    	_searchBtnClick: function(){
    		var url = _base+"/billSubject/getConnectList";
    		
    		ajaxController.ajax({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data: {    	            	
	            	subjectId:$('#subjectId').val(),
	            	industryCode:$('#industryCode').val()
	            },
	            message: "正在为您查询数据..",
	            success: function (data) {
	            	var data = data.data;
	            	$('#subjectName').val(data.subjectName);
	            	
	            	var subjectIds = new Array();
	            	if(data.relatedList!=null && data.relatedList.length>0){
	            		var html = "";
	            		for(var i in data.relatedList){
	            			subjectIds[i] = data.relatedList[i].subjectId;
	            			html += '<span class="list-cj" onclick="pager._checkSubject(this)"><a href="javascript:void(0);"  subjectid="'+data.relatedList[i].subjectId+'">'+data.relatedList[i].subjectName+'</a></span>'; 
	            		}
	            		$("#connectSubjectData").append(html);
	            	}
	            	
	            	if(data.mayRelatedList!=null && data.mayRelatedList.length>0){
	            		var html = "";
	            		for(var i in data.mayRelatedList){
	            			if(subjectIds.indexOf(data.mayRelatedList[i].subjectId)<0){
	            				html += '<span class="list-cj" onclick="pager._checkSubject(this)"><a href="javascript:void(0);"  subjectid="'+data.mayRelatedList[i].subjectId+'">'+data.mayRelatedList[i].subjectName+'</a></span>';
	            			}
	            		}
	            		$('#billSubjectData').html(html);
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
    		if(""==html){
    			Dialog({
                    content:"请选择需要关联的科目",
                    ok:function(){
                        this.close();
                    }
                }).showModal();
    		}
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
    		if(""==html){
    			Dialog({
                    content:"请选择取消关联的科目",
                    ok:function(){
                        this.close();
                    }
                }).showModal();
    		}
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
                 url: _base+"/billSubject/saveConnectSubject",
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
                                 window.location.href = _base+"/billSubject/list";
                             }
                         });
                         d.showModal();
                     }
                 }
             });
    	},
    	
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
    
    module.exports = DrSubjectConnectPager
});

