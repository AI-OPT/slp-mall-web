define('app/jsp/billdetail/billDetailList',function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), 
			Widget = require('arale-widget/1.2.0/widget'), 
			Calendar = require('arale-calendar/1.1.2/index'),
			Select = require('arale-select/0.11.1/index'), 
			Dialog = require("artDialog/src/dialog"),

			// Paging = require('paging/0.0.1/paging'),
			AjaxController = require('opt-ajax/1.0.0/index');

			require("jsviews/jsrender.min");
			require("jsviews/jsviews.min");
			require("bootstrap-paginator/bootstrap-paginator.min");
			require("twbs-pagination/jquery.twbsPagination.min");
			require("opt-paging/aiopt.pagination");
			//require("app/util/jsviews-ext");

			// 实例化AJAX控制处理对象
			// var ajaxController = new AjaxController();

			// 定义页面组件类
			var BillDetailPager = Widget.extend({
						// 属性，使用时由类的构造函数传入
						attrs : {},
						Statics : {
							DEFAULT_PAGE_SIZE : 10
						},
						// 事件代理
						events : {
							// key的格式: 事件+空格+对象选择器;value:事件方法
						"click #BTN_SEARCH" : "_searchBtnClick",
						
						//证件号校验
						 "blur [id='idNumber']":"_checkIdNumber",
						
						},
						_initPage : function() {
							// 面包屑导航
							setBreadCrumb("查询管理", "详单查询");
							// 左侧菜单选中样式
							$("#mnu_query_mng").addClass("current");
						},
						// 重写父类
						setup : function() {
							BillDetailPager.superclass.setup.call(this);
							
							this._initPage();
							this._getCustGrade();
							
						},
						_checkIdNumber:function(){
							var idNumber = jQuery.trim($("#idNumber").val());
				    		if(idNumber == "" || idNumber == null || idNumber == undefined){
				    			this._controlMsgText("idNumberMsg","客户证件号不能为空");
				    			this._controlMsgAttr("idNumberMsgDiv",2);
				    			return false;
				    		}else{
				    			this._controlMsgText("idNumberMsg","");
				    			this._controlMsgAttr("idNumberMsgDiv",1);
				    			return true;
				    		}
						},
						//控制显示内容
						_controlMsgText: function(id,msg){
							var doc = document.getElementById(id+"");
							doc.innerText=msg;
						},
						//控制显隐属性 1:隐藏 2：显示
						_controlMsgAttr: function(id,flag){
							var doc = document.getElementById(id+"");
							if(flag == 1){
								doc.setAttribute("style","display:none");
							}else if(flag == 2){
								doc.setAttribute("style","display");
								doc.setAttribute("style","min-width:200px");
							}
						},
						_searchBtnClick : function() {
							this._searchBills(1,BillDetailPager.DEFAULT_PAGE_SIZE);
						},
						_searchBills:function(pageNo, pageSize){
							// 入参，获取入参数据
							var _this = this;
							var custName = $("#custName").val();
							var custGrade = $("#custGrade").val();
							var serviceId = $("#serviceId").val();
							var idNumber = $("#idNumber").val();
							if(!_this._checkIdNumber()){
								return;
							}
							// 封装入参
							var _this = this;
							var url = _base+ "/billdetail/custInfoList";
							$("#pagination-ul").runnerPagination({
												url : url,
												method : "POST",
												dataType : "json",
												processing : true,
												data : {
													custName : custName,
													custGrade : custGrade,
													serviceId : serviceId,
													idNumber : idNumber
												},
												pageSize : BillDetailPager.DEFAULT_PAGE_SIZE,
												visiblePages : 5,
												message : "正在为您查询数据..",
												render : function(data) {
													if (data != null&& data != 'undefined'&& data.length > 0) {
														var template = $.templates("#listDataTmpl");
														var htmlOutput = template.render(data);
														$("#listData").html(htmlOutput);
													} else {
														$("#listData").html(null);
														$("#listData").html("没有搜索到相关信息");
													}
												}
											});
						},
						_getCustGrade:function(){
							// 入参，获取入参数据
							var _this = this;

							var url = _base+ "/billdetail/getCustGrade";
							$.ajax({
								type : "post",
								processing : false,
								url : url,
								dataType : "json",
								data : {},
								message : "正在加载数据..",
								success : function(data) {
									var d=data.data.paramList;
									$.each(d,function(index,item){
										var paramName = d[index].paramName;
										var paramCode = d[index].paramCode;
										$("#custGrade").append('<option value="'+paramCode+'">'+paramName+'</option>');
									})
									
										
								}
							});
						}
						
						
						
					});

			module.exports = BillDetailPager
		});
