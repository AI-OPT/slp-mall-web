define('app/jsp/arrearage/queryArrearage', function (require, exports, module) {
    'use strict';
	var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
	AjaxController=require('opt-ajax/1.0.0/index'),
	Validator = require('arale-validator/0.10.2/index'),
	moment = require('moment/2.9.0/moment'),
	Calendar = require('arale-calendar/1.1.2/index-month');
	
	require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require('opt-paging/aiopt.pagination'),

    require("jsviews/jsrender.min");
	require("jsviews/jsviews.min");
	require("app/util/jsviews-ext");

	//实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QueryArrearagePager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		 "click [id='scleraId']":"_cleareStartDate",
    		 "click [id='ecleraId']":"_cleareEtartDate",
             "click #BTN_SEARCH":"_searchList",
             "click #BTN_EXPORT":"_exportToExcel"
        },
    	//重写父类
    	setup: function () {
    		QueryArrearagePager.superclass.setup.call(this);
    		this._bindValidator();
			this._bindCalendar();
			this._bindSelect();
			this._initPage();
			// 初始化执行搜索
			this._searchList(1,QueryArrearagePager.DEFAULT_PAGE_SIZE);
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("查询管理","欠费查询");
      		//左侧菜单选中样式
      		$("#mnu_query_mng").addClass("current");
      	},
		_bindCalendar:function(){
			new Calendar({trigger: '#startDate'});
			new Calendar({trigger: '#endDate'});
		},
		_cleareStartDate: function(){
			$("#startDate").val("");
		},
		_cleareEtartDate: function(){
			$("#endDate").val("");
		},
		_bindValidator:function(){
			var validator = new Validator({
				element:'#cg-Form',
				failSilently:true
			});

			validator.addItem({
				element:'#bottomAmount',
				rule:'number',
				display:'欠费金额',
				alertFlag:true,
				errormessageNumber:'{{display}}必须是数字格式'
			}).addItem({
				element:'#topAmount',
				rule:'number',
				display:'欠费金额',
				alertFlag:true,
				errormessageNumber:'{{display}}必须是数字格式'
			});
		},
		_checkDate: function(){
			var sTime = $("#startDate").val();
			var dTime = $("#endDate").val();
			if(sTime!="" && dTime!=""){
			      //js判断日期
			    var begin = moment(sTime,"YYYY-MM");
				var end = moment(dTime,"YYYY-MM");
				if(end.diff(begin)<0){
			         $('#showDateMsg').text("开始时间要在结束时间之前!");
			         return false;
			      }else{
			    	  $('#showDateMsg').text("");
			    	  return true;
			      }
			}else{
				return true;
			}
		},
		_checkMoney: function(){
			var botom = $("#bottomAmount").val();
			var top = $("#topAmount").val();
			if(botom!="" && top!=""){
				if(parseInt(botom)>parseInt(top)){
					//alert("欠费金额起始数值大于结束数值");
					$('#showAmonutMsg').text("欠费金额起始数值大于结束数值");
					return false;
				}else{
					$('#showAmonutMsg').text("");
					return true;
				}
			}else{
				return true;
			}
		},
		// 下拉
		_bindSelect : function() {
			var this_=this;
				$.ajax({
					type : "post",
					processing : false,
					url : _base+ "/base/getSelect",
					dataType : "json",
					data : {
						paramType:"CUST_LEVEL"
						},
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

		},
		_searchList:function(pageNo, pageSize){
			this._getQueryParams();//存储查询参数，用于excel导
			
			var this_=this;
			var moneFlag = this._checkMoney();
			var dateFlag = this._checkDate();
			if((!moneFlag)|| (!dateFlag)){
				return false;
			}
			var data = $("#cg-Form :input,#cg-Form select").serializeArray();
			
			$("#pagination-ul").runnerPagination({
				url: _base+"/arrearage/getArrearegeList",
				method: "POST",
				dataType: "json",
				processing: true,
				data : data,
				pageSize: QueryArrearagePager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#arrearageTemple");
						var htmlOut = template.render(data);
						$("#arrearageData").html(htmlOut);
					}else{
						$("#arrearageData").html("未搜索到信息");
					}
				}
			});
		},
		//存储查询参数，用于excel导出
		_getQueryParams:function(){
			this.custNameQ = jQuery.trim($("#custName").val());
			this.custGradeQ = jQuery.trim($("#custGrade").val());
			this.startDateQ = jQuery.trim($("#startDate").val());
			this.endDateQ = jQuery.trim($("#endDate").val());
			this.bottomAmountQ = jQuery.trim($("#bottomAmount").val());
			this.topAmountQ = jQuery.trim($("#topAmount").val());
		},
		//导出到excel
		_exportToExcel:function(){
			var param = 'custName='+this.custNameQ + '&custGrade='+this.custGradeQ 
					+ '&startDate='+this.startDateQ + '&endDate='+this.endDateQ
					+ '&bottomAmount='+this.bottomAmountQ + '&topAmount='+this.topAmountQ;
			window.location.href = _base + '/arrearage/exportArrearageListToExcel?' + param;
		}
    });

    module.exports = QueryArrearagePager
});
