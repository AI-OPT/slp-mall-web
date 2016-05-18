define('app/jsp/recharge/queryRecharge', function (require, exports, module) {
    'use strict';
	var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Dialog = require("artDialog/src/dialog"),
	AjaxController=require('opt-ajax/1.0.0/index'),
	Validator = require('arale-validator/0.10.2/index'),
	moment = require('moment/2.9.0/moment'),
	Calendar = require('arale-calendar/1.1.2/index');
	require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require('opt-paging/aiopt.pagination'),
	require("jsviews/jsrender.min");
	require("jsviews/jsviews.min");
	require("app/util/jsviews-ext");

	//实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var QueryRechargePager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"click [id='export']":"_exportExcel",
            "click #charge-search":"_searchList"
        },
    	//重写父类
    	setup: function () {
    		QueryRechargePager.superclass.setup.call(this);
			this._bindCalendar();
			this._bindValidator();
			this._bindSelect();
			this._initPage();
			// 初始化执行搜索
			this._searchList();
    	},
    	_initPage: function(){
         	//导航
    		 setBreadCrumb("查询管理","充值缴费查询");
      		//左侧菜单选中样式
      		$("#mnu_query_mng").addClass("current");
      	},
		_bindCalendar:function(){
			new Calendar({trigger: '#beginTime'});
			new Calendar({trigger: '#endTime'});
		},
		
		_bindValidator:function(){
			var validator = new Validator({
				element:'#cg-Form',
				failSilently:true
			});

			validator.addItem({
				element:'#bottomAmount',
				rule:'number',
				display:'充值金额',
				alertFlag:true,
				errormessageNumber:'{{display}}必须是数字格式'
			}).addItem({
				element:'#topAmount',
				rule:'number',
				display:'充值金额',
				alertFlag:true,
				errormessageNumber:'{{display}}必须是数字格式'
			});
		},
		_checkDate: function(){
			var sTime = $("#beginTime").val();
			var dTime = $("#endTime").val();
			if(sTime!="" && dTime!=""){
				var smonth = sTime.substring(0,7);
				var dmonth = dTime.substring(0,7);
				if(smonth!=dmonth){
					  $('#showDateMsg').text("缴费记录不能跨月查询!");
					  return false;
				}else{
					//var begin=new Date($("#beginTime").val().replace(/-/g,"/"));
				    //var end=new Date($("#endTime").val().replace(/-/g,"/"));
				      //js判断日期
					 var begin = moment(sTime,"YYYY-MM-DD");
						var end = moment(dTime,"YYYY-MM-DD");
						if(end.diff(begin)<0){
				      //if(begin-end>0){
				         $('#showDateMsg').text("开始日期要在截止日期之前!");
				         return false;
				      }else{
				    	  $('#showDateMsg').text("");
				    	  return true;
				      }
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
					$('#showAmonutMsg').text("充值金额起始数值大于结束数值");
					return false;
				}else{
					$('#showAmonutMsg').text("");
					return true;
				}
			}else{
				return true;
			}
		},
		_searchList:function(){
			var _this=this;
			var queryParams = this._getQueryParams();
			var moneFlag = this._checkMoney();
			var dateFlag = this._checkDate();
			if((!moneFlag)|| (!dateFlag)){
				return false;
			}
			//var data = $("#cg-Form :input,#cg-Form select").serializeArray();
			$("#pagination").runnerPagination({
				url: _base+"/recharge/getRechargeList",
				method: "POST",
				dataType: "json",
				processing: true,
				data : queryParams,
				pageSize: QueryRechargePager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#chargeListTemple");
						var htmlOut = template.render(data);
						$("#chargeData").html(htmlOut);
					}else{
						$("#chargeData").html("未搜索到信息");
					}
				},
				callback: function(data){
					 $("#totalcount").val(data.count);
				},
			});
		},
		//获取查询参数
		_getQueryParams:function(){
			var _this = this;
			return{
				"custName":function () {
					_this.custNameQ = jQuery.trim($("#custName").val());
			        return _this.custNameQ;
			    },
				"custGrade":function () {
					_this.custGradeQ = jQuery.trim($("#custGrade").val());
			        return _this.custGradeQ;
			    },
			    "startTime":function () {
			    	_this.startTime = jQuery.trim($("#beginTime").val());
			        return _this.startTime;
			    },
			    "endTime":function () {
			    	_this.endTime = jQuery.trim($("#endTime").val());
			        return _this.endTime;
			    },
			    "bottomAmount":function () {
			    	_this.bottomAmount = jQuery.trim($("#bottomAmount").val());
			        return _this.bottomAmount;
			    },
			    "topAmount":function () {
			    	_this.topAmount = jQuery.trim($("#topAmount").val());
			        return _this.topAmount;
			    },
			    "paySerialCode":function () {
			    	_this.paySerialCode = jQuery.trim($("#paySerialCode").val());
			        return _this.paySerialCode;
			    }
			}
		},
		
		_exprtMecel: function(){
			var _this=this;
			var maxcount = $("#maxRow").val();
			var totalcount = $("#totalcount").val();
			new Dialog({
    	        title: '提示',
    	        content: '该导出功能最多导出'+maxcount+"条数据",
    	        width: '300px',
    	        height: '60px',
    	        //hasMask: false,  //没有遮罩
    	        /*okValue: '确定',
    	        ok: function () {
    	            this.title('提交中…');
    	            window.location.href=_base+'/recharge/exportExcel?custName='+this.custNameQ+'&custGrade='+this.custGradeQ+'&startTime='+this.startTime+
    				'&endTime='+this.endTime+'&paySerialCode='+this.paySerialCode+'&topAmount='+this.topAmount+'&bottomAmount='+this.bottomAmount;
    	        },*/
    	        cancelValue: '取消',
    	        cancel: function () {}
    	    }).show();
			
		},
		_exportExcel:function(){
			var _this=this;
			var maxcount = $("#maxRow").val();
			var totalcount = $("#totalcount").val();
			if(parseInt(totalcount)>parseInt(maxcount)){
				this._exprtMecel();
			}else{
				window.location.href=_base+'/recharge/exportExcel?custName='+this.custNameQ+'&custGrade='+this.custGradeQ+'&startTime='+this.startTime+
				'&endTime='+this.endTime+'&paySerialCode='+this.paySerialCode+'&topAmount='+this.topAmount+'&bottomAmount='+this.bottomAmount;
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
    });


    module.exports = QueryRechargePager
});
