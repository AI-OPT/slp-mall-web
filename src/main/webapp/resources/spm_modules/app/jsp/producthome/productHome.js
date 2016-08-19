define('app/jsp/producthome/productHome', function (require, exports, module) {
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
    require("app/util/fonts_num");
    var SendMessageUtil = require("app/util/sendMessage");
    
    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();
    
    
    
    //定义页面组件类
    var ProductHomePager = Widget.extend({
    	
    	Implements:SendMessageUtil,
    	//属性，使用时由类的构造函数传入
    	attrs: {
    	},
    	Statics: {
    		DEFAULT_PAGE_SIZE: 10
    	},
    	//事件代理
    	events: {
    		//查询
            //"click #BTN_SEARCH":"_searchBtnClick"
            //"click #thumbnailId":"_changeImage",
            "click #moreproduct":"_getMore",
            "click #moreId":"_getMore",
            "click #refresh":"_getHotProduct",
            "click #phoneBillCucc":"_getPhoneBill",
            "click #phoneBillCmcc":"_getPhoneBill",
            "click #phoneBillCtcc":"_getPhoneBill",
            "click #flowCmcc":"_getFlowProduct",
            "click #flowCtcc":"_getFlowProduct",
            "click #flowCucc":"_getFlowProduct",
            "change #phoneFee":"_changeHuafei",
            "change #location":"_changeLocation",
            "change #gprs":"_changeGprsValue",
            "click #CZ_BTN":"_submitOrder",
            "click #GPRS_BTN":"_submitGprs",
            "click #switchFL1":"_changeSwitch1",
            "click #switchFL2":"_changeSwitch2"
            
            
        },
    	//重写父类
    	setup: function () {
    		ProductHomePager.superclass.setup.call(this);
    		//初始化执行搜索
    		this._getsessionData();
    		this._initFastInfo();
    		this._getPhoneInfo();
    		this._getGprs();
    		this._fastSwchange1();
    		this._tpchange();
    		this._leftChange();
    	},
    	_leftChange:function(){
    		  var st = 100;
    		    $('.Mobile').mouseenter(function () {
    				$('.Mobile a').addClass('b');
    				$('.Mobile .Mobile-hover').show(1);
    		    })
    				$(".Mobile .Mobile-hover").click(function () {
    		                $(this).hide(1);
    		            });
    					
    				$('.Mobile').mouseleave(function () {
    		        $('.Mobile .Mobile-hover').hide(1);
    				 $('.Mobile a').removeClass('b');
    		    });		
    				$('.Unicom').mouseenter(function () {
    					$('.Unicom a').addClass('b');
    					$('.Unicom .Unicom-hover').show(1);
    			    })
    					$(".Unicom .Unicom-hover").click(function () {
    			                $(this).hide(1);
    			            });
    						
    					$('.Unicom').mouseleave(function () {
    			        $('.Unicom .Unicom-hover').hide(1);
    					 $('.Unicom a').removeClass('b');
    			    });	
    					 $('.telecom').mouseenter(function () {
    							$('.telecom a').addClass('b');
    							$('.telecom .telecom-hover').show(1);
    					    })
    							$(".telecom .telecom-hover").click(function () {
    					                $(this).hide(1);
    					            });
    								
    							$('.telecom').mouseleave(function () {
    					        $('.telecom .telecom-hover').hide(1);
    							 $('.telecom a').removeClass('b');
    					    });
    	},
    	_fastSwchange1:function(){
    		$(".charge-title ul li a").click(function () {
                $(".charge-title ul li a").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
            });
    		$('.charge-title ul li a').click(function(){
  			  var index=$('.charge-title ul li a').index(this);
  			      if(index==0){
  			     $('#date1').show();
  			  $('#date2').hide();
  			   }
  			   if(index==1){
  			     $('#date2').show();
  			  $('#date1').hide();
  			   }
  			  }); 
    	},
      
    	_tpchange:function(){
    		//图片table切换2
    		$(function(){
    		$(".plist-right-title ul li a").click(function () {
    		                $(".plist-right-title ul li a").each(function () {
    		                    $(this).removeClass("current");
    		                });
    		                $(this).addClass("current");
    		            });
    		$('.plist-right-title ul li a').click(function(){
    		  var index=$('.plist-right-title ul li a').index(this);
    		      if(index==0){
    		     $('#img-list1').show();
    		  $('#img-list2').hide();
    		   }
    		   if(index==1){
    		     $('#img-list2').show();
    		  $('#img-list1').hide();
    		   }
    		  }); 
    		});
    		//图片table切换3
    		$(function(){
    		$(".plist-right-title-tow ul li a").click(function () {
    		                $(".plist-right-title-tow ul li a").each(function () {
    		                    $(this).removeClass("current");
    		                });
    		                $(this).addClass("current");
    		            });
    		$('.plist-right-title-tow ul li a').click(function(){
    		  var index=$('.plist-right-title-tow ul li a').index(this);
    		      if(index==0){
    		     $('#img-list3').show();
    		  $('#img-list4').hide();
    		   }
    		   if(index==1){
    		     $('#img-list4').show();
    		  $('#img-list3').hide();
    		   }
    		  }); 
    		});
    		//table切换结束  

    	},
    	_changeSwitch1:function(){
    		var _this=this;
    		 _this._initHf();
    		 _this._changeHuafei();
 		  
    	},
	   _changeSwitch2:function(){
		   var _this=this;
		   $("#phoneNum2").val("");
		   $("#location").val("national");
		   _this._initLf();
		   _this._changeGprsValue();
    	},
    	_initFastInfo:function(){
    		//初始化话费
    		var _this=this;
    		_this._initHf();
    		_this._changeHuafei();
    		//初始化流量
    		$("#phoneNum2").val("");
    		_this._initLf();
    		_this._changeGprsValue();
    	},
    	_initHf:function(){
    		$("#phoneNum1").val("")
    		$("#phoneFee").html("");
    		$("#gsd1").html("");
    		$("#submitOdrBtn").removeAttr('href');
    		$("#phoneFee").append("<option value='￥49.00-￥50.00'>50元</option>");
    		$("#phoneFee").append("<option value='￥29.50-￥30.00'>30元</option>");
    		$("#phoneFee").append("<option value='￥19.50-￥20.00'>20元</option>");
    		$("#phoneFee").append("<option value='￥9.95-￥10.00'>10元</option>");
    		
    	},
    	_initLf:function(){
    		//$("#phoneNum2").val("");
    		$("#gprs").html("");
    		$("#gsd2").html("");
    		$("#gprs").append("<option value='￥49.00-￥50.00'>1G</option>");
    		$("#gprs").append("<option value='￥35.00-￥40.00'>500M</option>");
    		$("#gprs").append("<option value='￥29.50-￥29.90'>300M</option>");
    		$("#gprs").append("<option value='￥9.95-￥20.00'>100M</option>");
    	},
    	_submitGprs:function(){//话费的
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
    		var phoneFee=$.trim($("#gprs").val());
    		if(phoneFee==null||phoneFee==""||phoneFee==undefined){
    			Dialog({
					title : '提示',
					content : "请选择充值面额",
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
    			return false;
    		}
    		var start=$("#gprs").val().indexOf(";")+1;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				async: false,
				url: _base+"/order/orderCommit",
				data:{
					orderType:"100010",//暂时传运营商的县官信息
					skuId:$("#gprs").val().substr(start,$("#gprs").val().length),
					buySum:"1",
					basicOrgId:$("#gbasicOrgId").val(),
					provinceCode:$("#PCode1").val(),
					chargeFee:$("#gprs option:selected").text(),
					phoneNum:$("#phoneNum2").val()
					},
				success: function(data){
					var key=data.data;
					/*window.location.href = _base
					+ "/order/toOrderPay?orderKey="+key;*/
					//window.open(_base+ "/order/toOrderPay?orderKey="+key);
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
    		var phoneFee=$.trim($("#phoneFee").val());
    		if(phoneFee==null||phoneFee==""||phoneFee==undefined){
    			Dialog({
					title : '提示',
					content : "请选择充值面额",
					okValue : "确定",
					ok : function() {
						this.close;
					}
				}).showModal();
    			return false;
    		}
    		//$("#uuu").click();
    		var start=$("#phoneFee").val().indexOf(";")+1;
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				async: false,
				url: _base+"/order/orderCommit",
				data:{
					orderType:"100010",//暂时传运营商的县官信息
					skuId:$("#phoneFee").val().substr(start,$("#phoneFee").val().length),
					buySum:"1",
					basicOrgId:$("#basicOrgId1").val(),
					provinceCode:$("#PCode").val(),
					chargeFee:$("#phoneFee option:selected").text(),
					phoneNum:$("#phoneNum1").val()
					},
				success: function(data){
					var key=data.data;
					/*window.location.href = _base
					+ "/order/toOrderPay?orderKey="+key;*/
					var url=_base+ "/order/toOrderPay?orderKey="+key;
					
					/*$("#submitOdrBtn").attr("href",url);
					$("#submitOdrSpan").click();*/
					$("#okey").val(key);
					$("#submitOdrBtn").attr('href',url);
					/*$("#testForm").submit();*/
					//window.open(_base+ "/order/toOrderPay?orderKey="+key);
					//_this._takef();
				}
					
			});
    		}catch(err){
    			
    		}finally{
    			$("#submitOdrSpan").trigger("click");
    		}
    		
    		
    	},
    	
    	_getPhoneInfo:function(){
    		var _this=this;
    		$("#phoneNum1").bind('input propertychange',function(){
    			//var _this=this;
       		 $("#submitOdrBtn").removeAttr('href');
       		$("#gsd1").html("");
       		$("#gsd1").css("color","");
       		if($.trim($("#phoneNum1").val()).length==0){
       			$("#phoneFee").html("");
      			 $("#realFee").text("");
       			_this._initHf();
       			_this._changeHuafei();
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
       				$("#gsd1").css("color","#d4251f");
       				$("#gsd1").html("号码格式有误");
       	    			return false;

       			 }
       			
       			 $("#phoneFee").html("");
       			 $("#realFee").text("");
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
   							$("#basicOrgId1").val(d.basicOrgCode);
   							$("#PCode").val(d.provinceCode);
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
   									
   									 $("#phoneFee").html("");
   									if(d){
   										var phoneFee=d.phoneFee;
   									//	$("#submitOdrBtn").attr('href','#ttt');
   										$.each(phoneFee,function(index,item){
   											
   											var paramName = phoneFee[index].content;
   											var paramCode = phoneFee[index].skuInfo.salePrice+";"+phoneFee[index].skuInfo.skuId;
   											$("#phoneFee").append('<option value="'+paramCode+'">'+paramName+'</option>');
   											_this._changeHuafei();
   										})
   									}
   								}
   							});
   							
   						}else{
   							$("#gsd1").html("未知运营商");
   						}
   					}
   				});
       		}
        	});
    	
    	},
    	_changeHuafei:function(){
    	   $("#submitOdrBtn").removeAttr('href');
    		//console.log($("#phoneFee").val().substr(0,$("#phoneFee").val().indexOf(";")));
    	if($("#phoneFee").val().indexOf(";")<0){
    		$("#realFee").text($("#phoneFee").val());
    	}else{
    		$("#realFee").text(this._liToYuan($("#phoneFee").val().substr(0,$("#phoneFee").val().indexOf(";"))));//liToYuan
    	}
    		
    	},
    	
    	_getGprs:function(){
    		var _this=this;
    		$("#phoneNum2").bind('input propertychange',function(){
    			$("#submitGpBtn").removeAttr('href');
    			$("#gsd2").html("");
    			$("#gbasicOrgId").val("");
			    $("#PCode1").val("");
			    $("#gsd2").css("color","");
       		 if($.trim($("#phoneNum2").val()).length==0){
       			 $("#gprs").html("");
                	$("#realFee1").text("");
       			//初始化流量
       	    		_this._initLf();
       	    		_this._changeGprsValue();
       		 }
       		 
       		
               if($.trim($("#phoneNum2").val()).length==11){
               	var phoneNum=$.trim($("#phoneNum2").val());
           		var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
           		
           		 if(mobileReg.test(phoneNum)==false){
           			$("#gsd2").css("color","#d4251f");
           			$("#gsd2").html("号码格式有误");			
           			    return false;

           		 }
               	$("#gprs").html("");
               	$("#realFee1").text("");
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
   							$("#gbasicOrgId").val(d.basicOrgCode);
   							$("#PCode1").val(d.provinceCode);
   							
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
   									location:$("#location").val()
   									},
   								success: function(data){
   									var d=data.data;
   									 $("#gprs").html("");
   									if(d){
   										var phoneFee=d.phoneFee;
   										$.each(phoneFee,function(index,item){
   											var paramName = phoneFee[index].content;
   											var paramCode = phoneFee[index].skuInfo.salePrice+";"+phoneFee[index].skuInfo.skuId;
   											$("#gprs").append('<option value="'+paramCode+'">'+paramName+'</option>');
   											_this._changeGprsValue();
   										})
   									}
   								}
   							});
   							
   						}else{
   							$("#gsd2").html("未知运营商");
   						}
   					}
   				});
       		}
    			
    			
    		});
    		
    	},
    	_changeLocation:function(){
    		var _this=this;
    		$("#submitGpBtn").removeAttr('href');
    		$("#gprs").html("");
    		$("#realFee1").text("");
    		//如果手机号不合法就返回初始化数据
    		var phoneNum=$.trim($("#phoneNum2").val());
    		if(phoneNum==null||phoneNum==""||phoneNum==undefined){
    			$("#gprs").html("");
    			_this._initLf();
        		_this._changeGprsValue();
        		return false;
    		}
    		var mobileReg = /^0?1[3|4|5|8|7][0-9]\d{8}$/; 
			 if(mobileReg.test(phoneNum)==false){
				 $("#gprs").html("");
				 _this._initLf();
		    		_this._changeGprsValue();
		    		return false;
			 }
    		
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/getFastGprs",
				data:{
					provCode:$("#PCode1").val(),
					basicOrgId:$("#gbasicOrgId").val(),
					location:$("#location").val()
					},
				success: function(data){
					var d=data.data;
					if(d&&d.phoneFee){
						var phoneFee=d.phoneFee;
						$.each(phoneFee,function(index,item){
							var paramName = phoneFee[index].content;
							var paramCode = phoneFee[index].skuInfo.salePrice+";"+phoneFee[index].skuInfo.skuId;
							$("#gprs").append('<option value="'+paramCode+'">'+paramName+'</option>');
							_this._changeGprsValue();
						})
					}
				}
			});
    	},
    	_changeGprsValue:function(){
    		$("#submitGpBtn").removeAttr('href');
    		if($.trim($("#gprs").val()).indexOf(";")<0){
    			
        		$("#realFee1").text($("#gprs").val());//liToYuan

    		}else{
        		$("#realFee1").text(this._liToYuan($("#gprs").val().substr(0,$("#gprs").val().indexOf(";"))));//liToYuan
	
    		}
    	},
    	_getFastPhoneInfo:function(provCode,basicOrgId){
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
				}
			});
    	},
    	_getsessionData: function(){
    		var _this = this;
    		//从session获取数据
    		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/head/getSessionData",
				data:'',
				success: function(data){
					if(data.data.areaCode!=null && data.data.areaCode!="" &&　data.data.areaCode!=undefined){
						_this._setArea(data.data.areaCode,data.data.areaName);
			    		_this._getPhoneBill();
			    		_this._getFlowProduct();
			    		_this._getHotProduct();
					}else{
						_this._setArea("11","北京");
			    		_this._getPhoneBill();
			    		_this._getFlowProduct();
			    		_this._getHotProduct();
					}
					
				}
    		
			})
    	},
    	//设置当前地区
    	_setArea:function(code,name){
    		 $("#currentCity").attr("currentCityCode",code);
    		$("#currentCity").attr("currentCityName",name);
    	},
    	_chontrolWord: function(){
	       $(".plist-right-list .tit").fonts({
					fontNum:22		
			});
			$(".recommend-list .word").fonts({
					fontNum:24		
			});
    	},
    	_getPhoneBill:function(){
    		var _this=this;
    		//获取销售地区
    		var code = $("#currentCity").attr("currentCityCode");
    		//类目
    		var oprator;
    		/*//获取运营商类目
      		var isCmcc = $("#phoneBillCmcc").attr("class");
      		var isCtcc = $("#phoneBillCtcc").attr("class");
      		var isCucc = $("#phoneBillCucc").attr("class");
      		if(isCmcc=="current"){
      			$("#phoneOprator").val($("#phoneBillCmcc").attr("opratorid"));
      		}else if(isCtcc){
      			$("#phoneOprator").val($("#phoneBillCtcc").attr("opratorid"));
      		}else if(isCucc){
      			$("#phoneOprator").val($("#phoneBillCucc").attr("opratorid"));
      		}*/
      		var	param={
					areaCode:code,  
					productCatId: "10000010010000",	   
					basicOrgIdIs:'11'
				   };
      		ajaxController.ajax({
						type: "post",
						dataType: "json",
						url: _base+"/getPhoneBill",
						data:param,
						success: function(data){
							if(data.data){
								var template = $.templates("#phoneBillTmpl");
								var htmlOut = template.render(data.data);
								$("#phoneBillData").html(htmlOut);
								_this._chontrolWord();
								
							}
						}
					}
      		);
      	},
      	_getFlowProduct: function(){
      		var _this=this;
      		//获取销售地区
    		var code = $("#currentCity").attr("currentCityCode");
      		//流量类目ID
      		var productId="10000010020000";
      		/*//获取运营商类目
          		var isCmcc = $("#flowCmcc").attr("class");
          		var isCtcc = $("#flowCtcc").attr("class");
          		var isCucc = $("#flowCucc").attr("class");
          		if(isCmcc=="current"){
          			$("#flowOprator").val($("#flowCmcc").attr("opratorid"));
          		}else if(isCtcc){
          			$("#flowOprator").val($("#flowCtcc").attr("opratorid"));
          		}else if(isCucc){
          			$("#flowOprator").val($("#flowCucc").attr("opratorid"));
          		}*/
          		var	param={
    					areaCode:code,  
    					productCatId: "10000010020000",	   
    					basicOrgIdIs:'11'
    				   };
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				url: _base+"/getFlow",
				data:param,
				success: function(data){
					if(data.data){
						var template = $.templates("#flowTmpl");
						var htmlOut = template.render(data.data);
						$("#flowData").html(htmlOut);
						_this._chontrolWord();
					}
				}
			});
      	},
      	_getMore: function(){
      		//var agent = $("#flowOprator").val();
      		var agent = 10;
      		var productCatId ="10000010020000";
      		window.location.href = _base + '/search/list?billType='+productCatId+"&orgired="+agent;;
      	},
      //详情页面
    	_detailPage: function(skuId){
    		window.location.href = _base +'/product/detail?skuId='+skuId;
    	},
      	_jumpToSearch: function(price,type){
      		//获取运营商类目
      		var isCmcc = $("#cmccShowId").is(":visible");
      		var isCtcc = $("#ctccShowId").is(":visible");
      		var isCucc = $("#cuccShowId").is(":visible");
      		if(isCmcc){
      			//移动
      			var orgired="10";
      			window.location.href = _base + '/search/list?priceId='+price+"&billType="+type+"&orgired="+orgired;
      		}else if(isCtcc){
      			//电信
      			var orgired="11";
      			window.location.href = _base + '/search/list?priceId='+price+"&billType="+type+"&orgired="+orgired;
      		}else if(isCucc){
      			//联通
      			var orgired="12";
      			window.location.href = _base + '/search/list?priceId='+price+"&billType="+type+"&orgired="+orgired;
      		}
      	},
      	_fmoney:function(s, n) {
      		n = n > 0 && n <= 20 ? n : 2;
      		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
      		var l = s.split(".")[0].split("").reverse(),
      		r = s.split(".")[1];
      		t = "";
      		for(i = 0; i < l.length; i ++ ){   
      			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
      		}
      		return t.split("").reverse().join("") + "." + r;
      	},
      		_liToYuan:function(li){
      			var result = '0.00';
      			if(isNaN(li) || !li){
      				return result;
      			}
      	        return this._fmoney(parseInt(li)/1000, 2);
      		},
      	_getHotProduct: function(){
      		var _this=this;
      	//获取销售地区
    		var code = $("#currentCity").attr("currentCityCode");
      		ajaxController.ajax({
				type: "post",
				dataType: "json",
				processing: false,
				url: _base+"/getHotProduct",
				data:{areaCode:code},
				success: function(data){
					if(data.data){
						var template = $.templates("#hotTmpl");
						var htmlOut = template.render(data.data);
						$("#hotData").html(htmlOut);
						_this._chontrolWord();
					}
				}
			}
		);
      	}
    	
    });
    
    module.exports = ProductHomePager
});

