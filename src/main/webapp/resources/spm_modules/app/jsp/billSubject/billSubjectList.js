define('app/jsp/billSubject/billSubjectList', function (require, exports, module) {
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
    var BillSubjectPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 6
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		//查询
            "click #BTN_SEARCH":"_searchBtnClick"
        },
    	//重写父类
    	setup: function () {
    		BillSubjectPager.superclass.setup.call(this);
    		this._bindEvents();
    		this._searchBtnClick();
    		this._initPage();
    	},    		
    	_bindEvents: function(){
    		var _this = this;
    		$('#tabName').bind('keypress', function(event){
				if(event.keyCode == "13"){
					_this._searchBtnClick();	
				}
			});
    	},
    	_initPage: function(){
    		//面包屑导航
    		setBreadCrumb("计费配置管理","账单科目管理");
    		//左侧菜单选中样式
    		$("#mnu_bmc_config").addClass("current");
    	},
    	
    	
    	
    	_searchBtnClick: function(){
    		var url = _base+"/billSubject/getList";
    		$("#pageview").runnerPagination({
	 			url: url,
	 			method: "POST",
	 			dataType: "json",
	 			processing: true,
	            data: {    	            	
	            	subjectId:$('#subjectId').val(),//搜索框里输入的值
	            	subjectName:$("#subjectName").val(),
    				subjectType:$("#subjectType").val()
	            },
	           	pageSize: BillSubjectPager.DEFAULT_PAGE_SIZE,
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
    	_viewSubject: function( curr){
    		var subjectId = $(curr).attr('subjectid');
    		var url = _base+"/billSubject/viewSubject?subjectId="+subjectId;
    	},//end of _shmDelete
    	
    	_editSubject: function( curr){
    		var subjectId = $(curr).attr('subjectid');
    		var url = _base+"/billSubject/toEdit?subjectId="+subjectId;
    		window.location.href = url;
    	},//end of _shmDelete
    	//关联详单
    	_connectedSubject: function( curr){
    		var subjectId = $(curr).attr('subjectid');
    		var industryCode = $(curr).attr('industryCode');
    		var url = _base+"/billSubject/toConnected?subjectId="+subjectId+"&industryCode="+industryCode;
    		window.location.href = url;
    	},
    	//删除
    	_deleteSubject: function(curr){
    		var _this = this;
    		Dialog({
    	        title: '提示',
    	        content: '确定要删除该账单吗?',
    	        width: '300px',
    	        height: '60px',
    	        okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
    	            $.ajax({
        				type : "POST",
        				url :_base+"/billSubject/deleteSubject",
        				data: {
        					subjectId : $(curr).attr('subjectid')
        				},
        				processing: true,
        				message : "正在处理中，请稍候...",
        				success : function(data) {
        					pager._searchBtnClick();;//刷新页面
        					var str = (data.data=='000000'?'表删除成功':'由于参数错误表未能成功删除账单');
        					var d = Dialog({
                                content:data.statusInfo ,
                                ok:function(){
                                    this.close();
                                }
                            });
                            d.showModal();
        				}
        			});
    	        },
    	        cancelValue: '取消',
    	        cancel: function () {}
    	    }).show();
    	}//end of _deleteTable
    	
    	
    	
    	
    	
    });
    
    module.exports = BillSubjectPager
});

