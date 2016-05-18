define(
		'app/jsp/preferentialproduct/relateCharge',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), Widget = require('arale-widget/1.2.0/widget'),

			Select = require('arale-select/0.11.1/index'), Dialog = require("artDialog/src/dialog"),

			// Paging = require('paging/0.0.1/paging'),
			AjaxController = require('opt-ajax/1.0.0/index');

			require("jsviews/jsrender.min");
			require("jsviews/jsviews.min");
			require("bootstrap-paginator/bootstrap-paginator.min");
			require("twbs-pagination/jquery.twbsPagination.min");
			require("opt-paging/aiopt.pagination");
			require("app/util/jsviews-ext");
			// 实例化AJAX控制处理对象
			// var ajaxController = new AjaxController();

			// 定义页面组件类
			var ChargeProductPager = Widget.extend({

				// 属性，使用时由类的构造函数传入
				attrs : {},
				Statics : {
					DEFAULT_PAGE_SIZE : 10
				},
				// 事件代理
				events : {
				// key的格式: 事件+空格+对象选择器;value:事件方法
				"click #SAVE_BTN" : "_saveRelate",
				"click #back" : "_goBack",
				
				},
				_initPage: function(){
		      		//面包屑导航
		      		setBreadCrumb("优惠产品管理","关联详单科目");
		      		//左侧菜单选中样式
		      		$("#mnu_bmc_config").addClass("current");
		      	},
				// 重写父类
				setup : function() {
					ChargeProductPager.superclass.setup.call(this);
					this._getChargeSelect();
					//this._getRelateSelect();
					this._initPage();
				},
				_goBack:function(){
					window.location.href = _base+"/preferentialProduct/list";
					
				},
				_saveRelate:function(){
					var this_ = this;

					$.ajax({
						type : "post",
						processing : false,
						url : _base + "/preferentialProduct/saveRelated",
						dataType : "json",
						data : {
							productId : this.get('relateId'),
							productType:this.get('relateType'),
							accountId:$("#chargeType").val()
						},
						message : "正在加载数据..",
						success : function(data) {
							Dialog({
								title : '提示',
								width : '200px',
								height : '50px',
								content : data.statusInfo,
								okValue: "确定",
								ok:function(){
									this.close();
									this_._goBack();
								}
							}).showModal();
							
						}
					});
				},
				_getChargeSelect : function() {
					var this_ = this;

					$.ajax({
						type : "post",
						processing : false,
						url : _base + "/preferentialProduct/getChargeSelect",
						dataType : "json",
						data : {},
						message : "正在加载数据..",
						success : function(data) {
							var d = data.data;
							$.each(d, function(index, item) {
								var paramCode = d[index].subjectId;
								var paramName = d[index].subjectName;
								$("#chargeType").append(
										'<option value="' + paramCode + '">'
												+ paramName + '</option>');
								this_ ._getRelateSelect();
							})

						}
					});
				},
				_getRelateSelect : function() {
					var this_ = this;

					$.ajax({
						type : "post",
						processing : false,
						url : _base + "/preferentialProduct/getRelated",
						dataType : "json",
						data : {
							productId : this.get('relateId')
						// $(_this_).attr("productId")
						},
						message : "正在加载数据..",
						success : function(data) {
							var d = data.data;
							if (d.accounts!=null&&d.accounts != 'undefined'
									&& d.accounts.length != 0) {
								$("#chargeType").val(d.accounts[0]);
								
							}
						}

					})

				},

			});

			module.exports = ChargeProductPager
		});
