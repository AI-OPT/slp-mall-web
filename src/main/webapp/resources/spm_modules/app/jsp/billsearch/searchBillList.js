define('app/jsp/billsearch/searchBillList', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
    Calendar = require('arale-calendar/1.1.2/index-month'),
    Dialog = require("artDialog/src/dialog"),
    Uploader = require('arale-upload/1.2.0/index'),
    AjaxController=require('opt-ajax/1.0.0/index');

    require("bootstrap-paginator/bootstrap-paginator.min");
	require("twbs-pagination/jquery.twbsPagination.min");
	require('opt-paging/aiopt.pagination');
	
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("treegrid/js/jquery.treegrid.min");
    require("app/util/jsviews-ext");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    //定义页面组件类
    var SearchBillListPager = Widget.extend({
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 5
    	},
    	//事件代理
    	events: {
    		//key的格式: 事件+空格+对象选择器;value:事件方法
    		"click [id='searchBtn']":"_searchBillList",
    		"click [id='exportBtn']":"_exportExcel"
        },
    	//重写父类
    	setup: function () {
    		SearchBillListPager.superclass.setup.call(this);
    		this._initPage();
    		//this._searchBillList();
    		this._bindCalendar();
    		this._bindSelect();
    	},
    	_initPage: function(){
         	//导航
    		setBreadCrumb("查询管理","账单查询");
      		//左侧菜单选中样式
      		$("#mnu_query_mng").addClass("current");
      	},
      	_bindCalendar:function(){
      		var calendar = new Calendar({trigger: '#queryTimeEvent',output:"#queryTimeQ"});
      		var startDate = this._getStartDate();
    		var endDate = this._getEndDate();
      		//设置选择范围
      		calendar.range([startDate,endDate]);
      		//设置初始焦点
      		calendar.focus(endDate);
      	},
      	//获取选择范围开始时间
      	_getStartDate:function(){
      		var sysDate = new Date();
  			var year = sysDate.getFullYear();    //获取完整的年份(4位,1970-????)
  			var month = sysDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
  			if(month>6){
  				var startMonth = month-6;
  				startMonth = "0"+startMonth;
  				return year+"-"+startMonth+"-01";
  			}else{
  				var startMonth = 12+(month-6);
  				if(startMonth <10){
  					startMonth = "0"+startMonth;
  				}
  				var startYear = year-1;
  				return startYear+"-"+startMonth+"-01";
  			}
      	},
      	//获取选择范围终止时间
      	_getEndDate:function(){
      		var sysDate = new Date();
  			var year = sysDate.getFullYear();    //获取完整的年份(4位,1970-????)
  			var month = sysDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
  			var endMonth = month-1;
  			var endYear = year;
  			if(endMonth == 0){
  				endMonth =12;
  				endYear = year -1;
  			}
  			if(endMonth<10){
  				endMonth = "0"+endMonth;
  			}
  			return endYear+"-"+endMonth+"-01";
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
						if(data.data != null){
							var d=data.data.paramList;
							$.each(d,function(index,item){
								var paramName = d[index].paramName;
								var paramCode = d[index].paramCode;
								$("#custGradeQ").append('<option value="'+paramCode+'">'+paramName+'</option>');
							})
						}
					}
				});
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
			}
		},
		//检查身份信息
		_searchBillList:function(){
			var _this = this;
			var isOk = this._checkQueryParams();
			if(!isOk){
				return false;
			}
			var queryParams = this._getQueryParams();
			$("#pagination").runnerPagination({
				url: _base+"/search/bill/searchList",
				method: "POST",
				dataType: "json",
				processing: true,
				data : queryParams,
				pageSize: SearchBillListPager.DEFAULT_PAGE_SIZE,
				visiblePages:5,
				message: "正在为您查询数据..",
				render: function (data) {
					if(data&&data.length>0){
						var template = $.templates("#standardDataTmpl");
						var htmlOut = template.render(data);
						$("#billListData").html(htmlOut);
					}else{
						$("#billListData").html("未搜索到信息");
					}
				},
				callback: function (data) {
					_this.billCount = data.count;
				}
			});
		},
		_exportExcel:function(){
			if(this.billCount > excelMaxRow){
				var msgDialog = Dialog({
					title: '提示',
					content: "该导出功能最多导出"+2+"条数据,请限制导出条数",
					ok: function () {
						this.close();
					}
				});
	        	msgDialog.showModal();
			}else{
				window.location.href=_base+'/search/bill/exportExcel?custName='+this.custNameQ+'&custGrade='+this.custGradeQ+'&queryTime='+this.queryTimeQ
			}
		},
		//检查查询参数规格
		_checkQueryParams:function(){
			var checName = this._checkCustName();
			var checkQueryTime = this._checkQueryTime();
			return checName&&checkQueryTime;
		},
		_checkCustName:function(){
			var custName = jQuery.trim($("#custNameQ").val());
			//待处理
			return true;
		},
		_checkQueryTime:function(){
			var queryTime = jQuery.trim($("#queryTimeQ").val());
			if(queryTime == "" || queryTime == null || queryTime == undefined){
				this._controlMsgText("queryMsg","查询参数【账单生成月份】不能为空");
    			this._controlMsgAttr("queryMsgDiv",2);
    			return false;
			}else{
				this._controlMsgText("queryMsg","");
    			this._controlMsgAttr("queryMsgDiv",1);
    			return true;
			}
		},
		//获取查询参数
		_getQueryParams:function(){
			var _this = this;
			return{
				"custName":function () {
					//导出条件
					_this.custNameQ = jQuery.trim($("#custNameQ").val());
			        return _this.custNameQ;
			    },
				"custGrade":function () {
					//导出条件
					_this.custGradeQ = jQuery.trim($("#custGradeQ").val());
			        return _this.custGradeQ;
			    },
			    "queryTime":function () {
			    	//导出条件
			    	_this.queryTimeQ = jQuery.trim($("#queryTimeQ").val());
			        return _this.queryTimeQ;
			    }
			}
		},
		_searchBillDetil:function(billDuration,custId){
			var year = billDuration.substring(0, 4);
			var month = billDuration.substring(5, 7);
			window.location.href=_base+"/search/bill/info?queryTime="+year+month+"&custId="+custId;
		},
		_queryBillDetailParams:function(index,custId){
			var custName = document.getElementById("custName"+index).innerText+"";
			var custGrade = document.getElementById("custGrade"+index).innerText+"";
			var billDuration = document.getElementById("billDuration"+index).innerText+"";
			var totalFee = document.getElementById("totalFee"+index).innerText+"";
			var disFee = document.getElementById("disFee"+index).innerText+"";
			var adjustFee = document.getElementById("adjustFee"+index).innerText+"";
			return {
				"custId":custId,
				"custName":custName,
				"custGrade":custGrade,
				"queryTime":billDuration,
				"totalFee":totalFee,
				"disFee":disFee,
				"adjustFee":adjustFee
			}
		}
		
    });
    module.exports = SearchBillListPager
});
