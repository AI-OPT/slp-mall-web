define(
		'app/jsp/preferentialproduct/discountProductList',
		function(require, exports, module) {
			'use strict';
			var $ = require('jquery'), 
			Widget = require('arale-widget/1.2.0/widget'), 
			Calendar = require('arale-calendar/1.1.2/index'),
			Select = require('arale-select/0.11.1/index'), 
			Dialog = require("artDialog/src/dialog"),

			// Paging = require('paging/0.0.1/paging'),
			AjaxController = require('opt-ajax/1.0.0/index'), SendMessageUtil = require("app/util/sendMessage");
			require("jsviews/jsrender.min");
			require("jsviews/jsviews.min");
			require("bootstrap-paginator/bootstrap-paginator.min");
			require("twbs-pagination/jquery.twbsPagination.min");
			require("opt-paging/aiopt.pagination");
			require("app/util/jsviews-ext");

			// 实例化AJAX控制处理对象
			// var ajaxController = new AjaxController();

			// 定义页面组件类
			var DiscProductPager = Widget
					.extend({
						Implements : SendMessageUtil,

						// 属性，使用时由类的构造函数传入
						attrs : {},
						Statics : {
							DEFAULT_PAGE_SIZE : 10
						},
						// 事件代理
						events : {
							// key的格式: 事件+空格+对象选择器;value:事件方法
							"click #BTN_SEARCH" : "_searchBtnClick",
							// "click #CHANGE_BTN" : "_changeStatus",
							// "click .DEL_BTN" : "_delProductClick"
							// "click #DETAIL_BTN" : "_productDetail" //查看详情
							"click #PHONE_IDENTIFY" : "_getPhoneVerifyCode",// 此方法在SendMessageUtil里
							"click .pop-close" : "_closeMessage"// 此方法在SendMessageUtil里
						},
						_initPage : function() {
							// 面包屑导航
							setBreadCrumb("优惠产品管理", "优惠产品查询");
							// 左侧菜单选中样式
							$("#mnu_bmc_config").addClass("current");
						},
						// 重写父类
						setup : function() {
							DiscProductPager.superclass.setup.call(this);
							this._bindEvents();
							this._bindCalendar();
							this._bindSelect();
							this._bindModel();
							this._bindDel();
							this._productDetail();
							this._editProduct();
							// 初始化执行搜索
							this._searchProducts(1,
									DiscProductPager.DEFAULT_PAGE_SIZE);
							this._bindUpdateStatusClick();
							this._initPage();
							
						},
						// 日期
						_bindCalendar : function() {
							new Calendar({
								trigger : '#activeTime'
							});
							new Calendar({
								trigger : '#invalidTime'
							});

						},
						_bindUpdateStatusClick : function() {
							var _this = this;

							var func = function() {
								$.ajax({
											type : "post",
											processing : false,
											url : _base
													+ "/preferentialProduct/changeStatus",
											dataType : "json",
											data : {
												productId : $("#pId").val(),
												status : $(
														"input[type='radio'][name='rd']:checked")
														.val()
											},
											message : "正在加载数据..",
											success : function(data) {
												$("#popModel").attr("style",
														"display:none");
												_this
														._searchProducts(
																1,
																DiscProductPager.DEFAULT_PAGE_SIZE);
												Dialog({
													title : '提示',
													width : '200px',
													height : '50px',
													content : data.statusInfo
												}).show();
												$("#PHONE_IDENTIFY")
														.removeAttr("disabled"); // 移除disabled属性
												$('#PHONE_IDENTIFY').val(
														'获取验证码');
												$('#phoneVerifyCode').val('');
											}
										});
							}

							$('#CHANGE_BTN').bind("click", function() {
								
								_this._verifyPhoneCode(func);// 此方法在SendMessageUtil里

							});
						},

						// 下拉
						_bindSelect : function() {
							var this_ = this;

							$.ajax({
								type : "post",
								processing : false,
								url : _base + "/preferentialProduct/getSelect",
								dataType : "json",
								data : {
									paramType : "PROFER_TYPE"
								},
								message : "正在加载数据..",
								success : function(data) {
									var d = data.data.paramList;
									$.each(d, function(index, item) {
										var paramName = d[index].paramName;
										var paramCode = d[index].paramCode;
										$("#chargeType").append(
												'<option value="' + paramCode
														+ '">' + paramName
														+ '</option>');
									})

								}
							});

						},
						// 分页控件

						_bindEvents : function() {
							var _this = this;
							$('#API_KEY')
									.bind(
											'keypress',
											function(event) {
												if (event.keyCode == "13") {
													_this
															._searchProducts(
																	1,
																	DiscProductPager.DEFAULT_PAGE_SIZE);
												}
											});
						},
						_searchBtnClick : function() {
							this._searchProducts(1,
									DiscProductPager.DEFAULT_PAGE_SIZE);
						},
						_bindRelate : function() {
							$(".ATT_BTN")
									.bind(
											"click",
											function() {
												var _this = this;
												window.location.href = _base
														+ "/preferentialProduct/toRelate?productId="
														+ $(_this).attr(
																'guanId')
														+ "&productName="
														+ encodeURIComponent(encodeURIComponent($(
																_this).attr(
																'guanName')))
														+ "&productType="
														+ $(_this).attr(
																'guanType');

											})
						},
						_bindModel : function() {

							$(".CHANGE").bind("click", function() {
								var _this = this;
								$('#popModel').show();
								$("#pId").val($(_this).attr('proId'));

							})
						},
						_bindDel : function() {
							var this_ = this;
							$(".DEL_BTN").bind("click",function() {
												var _this = this;

												Dialog(
														{
															width : '200px',
															height : '50px',
															content : "确定要删除吗？",
															okValue : "确定",
															ok : function() {
																$.ajax({
																			type : "post",
																			processing : false,
																			url : _base
																					+ "/preferentialProduct/delProduct",
																			dataType : "json",
																			data : {
																				productId : $(
																						_this)
																						.attr(
																								'productId')
																			},
																			message : "正在加载数据..",
																			success : function(
																					data) {
																				Dialog(
																						{
																							title : '提示',
																							width : '200px',
																							height : '50px',
																							content : data.statusInfo,
																							okValue : "确定",
																							ok : function() {
																								this.close;
																								this_._searchProducts(1,DiscProductPager.DEFAULT_PAGE_SIZE);
																							}
																						}).showModal();
																			}
																		});
															},
															cancelValue : "取消",
															cancel : function() {
																this.close;
															}
														}).showModal();

											})

						},
						_productDetail : function() {
							var this_ = this;

							$(".DETAIL_BTN")
									.bind(
											"click",
											function() {
												var _this = this;

												window.location.href = _base
														+ "/preferentialProduct/toProductDetail?productId="
														+ $(_this).attr(
																'detailId')
														+ "&productType="
														+ $(_this).attr(
																'detailType')
														+ "&priceCode="
														+ $(_this).attr(
																'priceCode');

											})

						},
						_editProduct : function() {
							var this_ = this;

							$(".EDIT_BTN").bind("click",function() {
												var _this = this;

												window.location.href = _base
														+ "/preferentialProduct/toProductEdit?productId="+ $(_this).attr('editId')+ "&productType="+ $(_this).attr('editType')+"&priceCode="+ $(_this).attr('priceCode');

											})

						},

						/*
						 * _changeStatus : function() { var _this = this;
						 * $.ajax({ type : "post", processing : false, url :
						 * _base + "/preferentialProduct/changeStatus", dataType :
						 * "json", data : { productId : $("#pId").val(), status :
						 * $("input[type='radio'][name='rd']:checked").val() },
						 * message : "正在加载数据..", success : function(data) {
						 * $("#popModel").attr("style","display:none");
						 * _this._searchProducts(1,DiscProductPager.DEFAULT_PAGE_SIZE);
						 * Dialog({ title : '提示', width : '200px', height :
						 * '50px', content : data.statusInfo }).show();
						 *  } }); },
						 */
						_searchProducts : function(pageNo, pageSize) {

							// 入参，获取入参数据
							var _this = this;
							var productId = $("#productId").val();
							var productName = $("#pName").val();
							var charType = $("#chargeType").val();
							var activeTime = $("#activeTime").val();
							var invalidTime = $("#invalidTime").val();

							// 封装入参
							var _this = this;
							var url = _base
									+ "/preferentialProduct/getPageList";
							$("#pagination-ul")
									.runnerPagination(
											{
												url : url,
												method : "POST",
												dataType : "json",
												processing : true,
												data : {
													productId : productId,
													productName : productName,
													proferType : charType,
													activeDate : activeTime,
													invalidDate : invalidTime
												},
												pageSize : DiscProductPager.DEFAULT_PAGE_SIZE,
												visiblePages :5,
												message : "正在为您查询数据..",
												render : function(data) {
													if (data != null
															&& data != 'undefined'
															&& data.length > 0) {
														var template = $
																.templates("#listDataTmpl");
														var htmlOutput = template
																.render(data);
														$("#listData").html(
																htmlOutput);
														_this._bindModel();
														_this._bindDel();
														_this._productDetail();
														_this._editProduct();
														_this._bindRelate();
													} else {
														$("#listData").html(
																null);
														$("#listData").html(
																"没有搜索到相关信息");
													}
												}
											});
						}
					});

			module.exports = DiscProductPager
		});
