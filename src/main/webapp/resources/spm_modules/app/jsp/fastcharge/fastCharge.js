define('app/jsp/fastcharge/fastCharge', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
    Widget = require('arale-widget/1.2.0/widget'),
   // Dialog = require("artDialog/src/dialog"),
    Dialog = require("optDialog/src/dialog"),
    Paging = require('paging/0.0.1/paging-debug'),
    AjaxController = require('opt-ajax/1.0.0/index');
    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");
    require("bootstrap-paginator/bootstrap-paginator.min");
    require("app/util/jsviews-ext");
    
    require("opt-paging/aiopt.pagination");
    require("twbs-pagination/jquery.twbsPagination.min");
   
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    //定义页面组件类
    var FastchargePager = Widget.extend({
    	
    	
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	//事件代理
    	events: {
    		//查询
    		
    		"click #phoneBill":"_switchHf",
    		"click #flowBill":"_switchLf",
    		"click input[name='flowRadio']":"_getBDFlow",
    		"click #ORD_BTN":"_submitOrder",
    		"click #GP_BTN":"_submitGprs"
    		//"click #LQG":"_getQGFlow"
        },
    	//重写父类
    	setup: function () {
    		FastchargePager.superclass.setup.call(this);
    		//初始化
    		this._chagePage();
    	//	this._hfeeChange();
    		// this._lfeeChange()1;
    		this._initpage();
    		this._getPhoneInfo();
    		this._getFlowInfo();
    	},
    	_chagePage: function(){
    		var isFlow = $("#flowFastFlag").val();
    		if(isFlow==="true"){
    			$("#phoneBill").removeClass("current");
    			//document.getElementById("phoneBill").className="";
    			$("#flowBill").attr("class","current");
    			$("#regeiter-date1").hide();
    			$("#regeiter-date2").show();
    		}else{
    			$("#flowBill").removeClass("current");
    			//document.getElementById("flowBill").className="";
    			$("#phoneBill").attr("class","current");
    			$("#phoneBill").click();
    			$("#regeiter-date1").show();
    			$("#regeiter-date2").hide();
    		}
    		
    	},
    	_getBDFlow:function(){
    		var _this=this;

			if($.trim($("#phoneNum2").val()).length==0){
				$("#listLfee").html("");
				_this._initLf();
				return false;
			}
			
			//如果等于11去查询，如果小于11把之前查询出来的信息清除
    		if($.trim($("#phoneNum2").val()).length==11){
    			 var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
    			 if(mobileReg.test($.trim($("#phoneNum2").val()))==false){
    				 $("#listLfee").html("");
    					_this._initLf();
    	    			return false;

    			 }
    			
    			   //需要拿到入参
    			    var provCode=$("#pcode").val();
					var basicOrgId=$("#orgcode").val();
					
					ajaxController.ajax({
						type: "post",
						dataType: "json",
					
						url: _base+"/getFastGprs",
						data:{
							provCode:provCode,
							basicOrgId:basicOrgId,
							location:$("input[name='flowRadio']:checked").val()
							},
						success: function(data){
							var d=data.data;
							 $("#listLfee").html("");
							 $("#lPrice").text("");
							if(d){
								if(d.phoneFee != null&& d.phoneFee != 'undefined'&& d.phoneFee.length > 0){
									 var template = $.templates("#lfeeDataTmpl");
									 var htmlOutput = template.render(d.phoneFee);
									 $("#listLfee").html(htmlOutput);
									 _this._lfeeChange();
									 $("#listLfee p:first").click();
								}else{
									 $("#listLfee").html("无适用面额");
								}
							}else{
								$("#listLfee").html("无适用面额");
							}
							
							
							
						}
					});
    			 
    			 
    			 
    			 
    			 
    			 
    			 
    		}
    	
			
    		
    	},
    	_getQGFlow:function(){
    		var _this=this;
    		
    		
    	},
    	_switchHf:function(){
    		var _this=this;
    		$("#phoneNum1").val("");
    		$("#gsd1").html("");
    		_this._initHf();
    	},
    	_switchLf:function(){
    		var _this=this;
    		$("#gsd2").html("");
    		$("#LQG").prop("checked",true)
    		$("#phoneNum2").val("");
    		_this._initLf();
    	},
    	_initpage:function(){
    		var _this=this;
    		_this._initHf();
    		_this._initLf();
    	
    	},
    	_initLf:function(){
    		var _this=this;
    	
   		 $("#listLfee").html("<p salePrice='45.50-49.50元' class='lfee'><a href='javascript:void(0);'>1G</a></p><p salePrice='49.00-50.00元' class='lfee current'><a href='javascript:void(0);'>500M</a></p>" +
   		 		"<p saleprice='29.00-29.90元' class='lfee'><a href='javascript:void(0);'>300M</a></p><p class='lfee' salePrice='9.95-20.00元'><a href='javascript:void(0);'>100M</a>");
   		 _this._lfeeChange1();
   		 $("#listLfee p:first").click();
    	},
    	_initHf:function(){
    		var _this=this;
    		 $("#listHfee").html("<p salePrice='490.00-500.00元' class='hfee current'><a href='javascript:void(0);'>500元</a></p><p salePrice='196.00-200.00元' class='hfee'><a href='javascript:void(0);'>200元</a></p><p salePrice='294.00-300.00元' class='hfee'>" +
     		 		"<a href='javascript:void(0);'>300元</a></p><p salePrice='98.00-10.00元' class='hfee'><a href='javascript:void(0);'>100元</a></p><p salePrice='49.00-500.00元' class='hfee'><a  href='javascript:void(0);'>50元</a></p>" +
     		 		"<p salePrice='29.40-30.00元' class='hfee'><a href='javascript:void(0);'>30元</a></p><p class='hfee' salePrice='19.60-20.00元'><a href='javascript:void(0);'>20元</a></p><p  salePrice='9.80-11.00元' class='hfee'><a href='javascript:void(0);'>10元</a></p>");
 		 _this._hfeeChange1();
 		 $("#listHfee p:first").click();
    	},
    	_submitGprs:function(){
    		var _this=this;
    		var phoneNum=$.trim($("#phoneNum2").val());
    		if(phoneNum==null||phoneNum==""||phoneNum==undefined){
    			Dialog({
							title : '提示',
							content : "手机号不能为空",
							okValue : "确定",
							ok : function() {
								this.close;
							}
						}).showModal();
    			return false;
    		}
    		var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
			 if(mobileReg.test(phoneNum)==false){
				 Dialog({
						title : '提示',
						content : "手机号格式不对，请重新输入",
						okValue : "确定",
						ok : function() {
							this.close;
						}
					}).showModal();
	    			return false;

			 }
    		
			 if($.trim($("#listLfee").html()).length==0){
				 Dialog({
						title : '提示',
						content : "抱歉，暂时不支持此号码的充值",
						okValue : "确定",
						ok : function() {
							this.close;
						}
					}).showModal();
	    			return false;
			 }
			 
			 
			var chargeFee= $(".lfee.current a").text();
			if($.trim(chargeFee).length===0){
				 Dialog({
						title : '提示',
						content : "抱歉，暂时不支持此号码的充值",
						okValue : "确定",
						ok : function() {
							this.close;
						}
					}).showModal();
	    			return false;
			}
			var skuId=$(".lfee.current").attr('skuId');
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				async: false,
				url: _base+"/order/orderCommit",
				data:{
					orderType:"100010",//暂时传运营商的县官信息
					skuId:skuId,
					buySum:"1",
					basicOrgId:$("#orgcode").val(),
					provinceCode:$("#pcode").val(),
					chargeFee:chargeFee,
					phoneNum:$("#phoneNum2").val()
					},
				success: function(data){
					var key=data.data;
					
					var url= _base+ "/order/toOrderPay?orderKey="+key;
					$("#submitGpBtn").attr('href',url);
				}
			});
    		$("#submitGpSpan").trigger("click");
    	},
    	_submitOrder:function(){//话费的
    		try{
    		var _this=this;
    		var phoneNum=$.trim($("#phoneNum1").val());
    		if(phoneNum==null||phoneNum==""||phoneNum==undefined){
    			Dialog({
							title : '提示',
							content : "手机号不能为空",
							okValue : "确定",
							ok : function() {
								this.close;
							}
						}).showModal();
    			return false;
    		}
    		var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
			 if(mobileReg.test(phoneNum)==false){
				 Dialog({
						title : '提示',
						content : "手机号格式不对，请重新输入",
						okValue : "确定",
						ok : function() {
							this.close;
						}
					}).showModal();
	    			return false;

			 }
			 
            if($.trim($("#listHfee").html()).length==0){
            	 Dialog({
						title : '提示',
						content : "抱歉，暂不支持此号码的充值",
						okValue : "确定",
						ok : function() {
							this.close;
						}
					}).showModal();
            	 return false;
			 }
			 
			 var phoneFee= $(".hfee.current").attr('salePrice')
    		if(typeof($(".hfee.current").attr("skuId"))=="undefined"){
    			Dialog({
					title : '提示',
					content : "抱歉，暂不支持此号码的充值",
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
    			return false;
    		}
			 
    		//$("#uuu").click();
    		
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				async: false,
				url: _base+"/order/orderCommit",
				data:{
					orderType:"100010",//暂时传运营商的县官信息
					skuId:$(".hfee.current").attr('skuId'),
					buySum:"1",
					basicOrgId:$("#orgcode1").val(),
					provinceCode:$("#pcode1").val(),
					chargeFee:$(".hfee.current a").html(),
					phoneNum:$("#phoneNum1").val()
					},
				success: function(data){
					var key=data.data;
				
					var url=_base+ "/order/toOrderPay?orderKey="+key;
					$("#okey").val(key);
					$("#submitOdrBtn").attr('href',url);
					
				}
					
			});
    		}catch(err){
    			
    		}finally{
    			$("#submitOdrSpan").trigger("click");
    		}
    		
    		
    	},
    	_getFlowInfo:function(){
    		
    		var _this=this;
    		
    		
    		$("#phoneNum2").bind('input propertychange',function(){
    			$("#submitGpBtn").removeAttr('href');
    			$("#gsd2").html("");
    			$("#gsd2").css("color","");
    			$("#pcode").val("");
				$("#orgcode").val("");
    			if($.trim($("#phoneNum2").val()).length==0){
    				$("#listLfee").html("");
    				_this._initLf();
    			}
    			
    			//如果等于11去查询，如果小于11把之前查询出来的信息清除
        		if($.trim($("#phoneNum2").val()).length==11){
        			 var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
        			 if(mobileReg.test($.trim($("#phoneNum2").val()))==false){
        				 $("#gsd2").css("color","#f4792c");
        				 $("#gsd2").html("号码格式有误");
        				/* Dialog({
        						title : '提示',
        						content : "手机号格式不对，请重新输入",
        						okValue : "确定",
        						ok : function() {
        							this.close;
        						}
        					}).showModal();*/
        	    			return false;

        			 }
        			ajaxController.ajax({
    					type: "post",
    					dataType: "json",
    				
    					url: _base+"/getPhoneInfo",
    					data:{
    						phoneNum:$.trim($("#phoneNum2").val()).substr(0,7)
    						},
    					success: function(data){
    						var d=data.data;
    						if(d){
    							//var productCatId="10000010010000";
    							var gsd=d.provinceName+d.basicOrgName;
       							$("#gsd2").html(gsd);
    							$("#pcode").val(d.provinceCode);
    							$("#orgcode").val(d.basicOrgCode);
    							var provCode=d.provinceCode;
    							var basicOrgId=d.basicOrgCode;
    							//userType 
    							//userId
    							ajaxController.ajax({
    								type: "post",
    								dataType: "json",
    							
    								url: _base+"/getFastGprs",
    								data:{
    									provCode:provCode,
    									basicOrgId:basicOrgId,
    									location:$("input[name='flowRadio']:checked").val()
    									},
    								success: function(data){
    									var d=data.data;
    									$("#listLfee").html("");
    									$("#lPrice").html("");
    									if(d){
    										if(d.phoneFee != null&& d.phoneFee != 'undefined'&& d.phoneFee.length > 0){
       										 var template = $.templates("#lfeeDataTmpl");
       										 var htmlOutput = template.render(d.phoneFee);
       										 $("#listLfee").html(htmlOutput);
       										 _this._lfeeChange();
       										 $("#listLfee p:first").click();
       									}else{
       										 $("#listLfee").html("无适用面额");
       									}
    									}else{
    										 $("#listLfee").html("无适用面额");
    									}
    									
    									
    									
    								}
    							});
    							
    						}else{
    							$("#gsd2").html("未知运营商");
    							 $("#listLfee").html("无适用面额");
    							 $("#lPrice").html("");
    						}
    					}
    				});
        		}
        	
    			
    			
    		});
    		
    	},
    	_getPhoneInfo:function(){
    		
    		var _this=this;
    		
    		$("#phoneNum1").bind('input propertychange',function(){
    			$("#submitOdrBtn").removeAttr('href');
    			$("#gsd1").html("");
    			 $("#gsd1").css("color","");
    			if($.trim($("#phoneNum1").val()).length==0){
       			 $("#listHfee").html("");
       			 
   				 _this._initHf();
   			//	 $("#listHfee p:first").click();
          		}
    			//如果等于11去查询，如果小于11把之前查询出来的信息清除
        		if($.trim($("#phoneNum1").val()).length==11){
        			 var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
        			 if(mobileReg.test($.trim($("#phoneNum1").val()))==false){
        				/* Dialog({
        						title : '提示',
        						content : "手机号格式不对，请重新输入",
        						okValue : "确定",
        						ok : function() {
        							this.close;
        						}
        					}).showModal();*/
        				 $("#gsd1").css("color","#f4792c");
        				 $("#gsd1").html("号码格式有误");
        	    			return false;

        			 }
        			ajaxController.ajax({
    					type: "post",
    					dataType: "json",
    				
    					url: _base+"/getPhoneInfo",
    					data:{
    						phoneNum:$.trim($("#phoneNum1").val()).substr(0,7)
    						},
    					success: function(data){
    						var d=data.data;
    						if(d){
    							//var productCatId="10000010010000";
    							var gsd=d.provinceName+d.basicOrgName;
       							$("#gsd1").html(gsd);
    							$("#orgcode1").val(d.basicOrgCode);
    							$("#pcode1").val(d.provinceCode);
    							var provCode=d.provinceCode;
    							var basicOrgId=d.basicOrgCode;
    							//userType 
    							//userId
    							ajaxController.ajax({
    								type: "post",
    								dataType: "json",
    							
    								url: _base+"/getFastInfo",
    								data:{
    									provCode:provCode,
    									basicOrgId:basicOrgId
    									},
    								success: function(data){
    									var d=data.data;
    									$("#listHfee").html("");
    									$("#hPrice").text("");
    									if(d){
    										if(d.phoneFee != null&& d.phoneFee != 'undefined'&& d.phoneFee.length > 0){
       										 var template = $.templates("#hfeeDataTmpl");
       										 var htmlOutput = template.render(d.phoneFee);
       										 $("#listHfee").html(htmlOutput);
       										 _this._hfeeChange();
       										 $("#listHfee p:first").click();
       									}else{
       										 $("#listHfee").html("无适用面额");
       									}
    									}else{
    										 $("#listHfee").html("无适用面额");
    									}
    									
    									
    									
    								}
    							});
    							
    						}else{
    							$("#gsd1").html("未知运营商");
    							 $("#listHfee").html("无适用面额");
    							 $("#hPrice").text("");
    						}
    						
    					}
    				});
        		}
    		});
    		
    	
    	},
    	
    	_hfeeChange:function(){
    		var _this=this;
    		$("#submitOdrBtn").removeAttr('href');
    		$(".hfee").bind("click",function(){
    			var this_=this;
    			$(".hfee").removeClass("current");
    			$(this_).addClass("current");
    			$("#hPrice").text(_this._liToYuan($(this_) .attr('saleprice')));
    		})
    		
    	},
    	_hfeeChange1:function(){
    		var _this=this;
    		$("#submitOdrBtn").removeAttr('href');
    		$(".hfee").bind("click",function(){
    			var this_=this;
    			$(".hfee").removeClass("current");
    			$(this_).addClass("current");
    			$("#hPrice").text($(this_) .attr('saleprice'));
    		})
    		
    	},
    	_lfeeChange:function(){
    		var _this=this;
    		$("#submitGpBtn").removeAttr('href');
    		$(".lfee").bind("click",function(){
    			var this_=this;
    			$(".lfee").removeClass("current");
    			$(this_).addClass("current");
    			$("#lPrice").text(_this._liToYuan($(this_) .attr('salePrice')));
    		})
    		
    	},
    	_lfeeChange1:function(){
    		var _this=this;
    		$("#submitGpBtn").removeAttr('href');
    		$(".lfee").bind("click",function(){
    			var this_=this;
    			$(".lfee").removeClass("current");
    			$(this_).addClass("current");
    			$("#lPrice").text($(this_) .attr('salePrice'));
    		})
    		
    	},
    	_liToYuan:function(li){
  			var result = '0.00';
  			if(isNaN(li) || !li){
  				return result;
  			}
  	        return "￥"+this._fmoney(parseInt(li)/1000, 2);
  		},
    	_fmoney:function(s, n) {
      		n = n > 0 && n <= 20 ? n : 2;
      		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
      		var l = s.split(".")[0].split("").reverse(),
      		r = s.split(".")[1];
      		var t = "";
      		for(var i = 0; i < l.length; i ++ ){   
      			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
      		}
      		return t.split("").reverse().join("") + "." + r;
      	}
      		
    	
    });
    
    module.exports = FastchargePager
});

