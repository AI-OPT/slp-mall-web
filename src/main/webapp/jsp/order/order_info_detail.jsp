<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>订单详情</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
</head>

<body>
<!--弹出框-->
<div class="eject-big">
		<div class="eject-medium">
			<div class="eject-medium-title">
				<p>明细</p>
				<p class="img"><a href="javaScript:void(0)" id="colsePhoneDialog"></a></p>
			</div>
			<div class="eject-medium-list">
				<div class="eject-contacts-list eject-bie-height" id="phoneListData">
				</div>
			</div>	
		</div>	
		<div class="eject-mask"></div>	
</div>
<!--弹出删除弹出框  中结束-->	
<!--顶部菜单-->
 <%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
 <%@ include file="/inc/user-nav.jsp" %>
<!--导航区域结束-->
     
     <!--订单详情-->
<div class="fsast-charge">
    <div class="big-wrapper" id="orderData"></div><!--内侧居中框架-->
    	<script id="orderTemple" type="text/x-jsrender">
			<div class="payment-title">
                 <p><a href="${_base}/myorder/list">账户中心</a>&gt;</p>
                 <p><a href="${_base}/myorder/list">我的订单</a>&gt;</p>
                 <p>订单号:</p>
                 <p>{{:orderId}}</p>
            </div>
             <!--订单状态-->
                 <div class="order-title">
                     <ul>
                         <li>
                             <p>订单号:</p>
                             <p>{{:orderId}}</p>
                         </li>
                         <li>
                             <p>订单状态:</p>
                             <p class="color">{{:stateName}}</p>
                         </li>
                         <li>
                             <p>下单时间:</p>
                             <p>{{:~timesToFmatter(orderTime)}}</p>
                         </li>
						{{if state !='11'&&state !='91'}}
                         <li>
                             <p>支付时间:</p>
                             <p>{{:~timesToFmatter(payTime)}}</p>
                         </li>
						{{/if}}
                     </ul>
                 </div>
           <!--订单状态结束-->    
          <div class="recharge-bj-tow"><!--白色背景-->
          		<div class="information-title"><p>交易信息</p></div>
               <div class="information-table">
                  <table width="100%" border="0">
                      <tr class="bj">
                        <td>运营商</td>
                        <td>归属地</td>
                        <td>数量/手机号</td>
                        <td>充值额</td>
                        <td>单价</td>
                        <td>小计</td>
                      </tr>
					  {{for productList}}                                                                                                                                                                       
                      <tr class="click">
                        <td>{{:basicOrgName}}</td>
                        <td>{{:provinceName}}</td>
                        <td>{{if prodExtendInfo==null || prodExtendInfo==""}}
								0/<span class="batch">明细</span></td>
							{{else}}
								{{:prodExtendInfo.split(",").length}}/<span class="batch" onclick="pager._showPhoneInfo('{{:prodExtendInfo}}')">明细</span></td>
							{{/if}}
                        <td>{{:chargeFee}}</td>
                        <td>￥{{:~liToYuan(salePrice)}}</td>
                        <td class="color">￥{{:~liToYuan(totalFee)}}</td>
                      </tr>
					  {{/for}} 
					</table>
                </div> 
          </div>
          
          <!--订单支付总价-->
                <div class="order-total">
            		<ul>
                      <li>
                          <p>充值手机:</p>
                          <p class="color">{{:phoneCount}}个</p>
                      </li>  
                      <li>
                          <p>需支付总额:</p>
                          <p class="color">¥{{:~liToYuan(totalFee)}}</p>
                      </li>  
                  </ul>
                </div>
         <!--订单支付总价结束-->
       
         <div class="recharge-bj-tow"><!--白色背景-->
          		<div class="information-title"><p>付款信息</p></div>
                 <div class="pay-list">
					{{if state=='111'}}
                     <ul>
                         <li>
                             <p>实付总金额:</p>
                             <p class="color">¥{{:~liToYuan(paidFee)}}</p>
                         </li>
						 <li>	
							 <p>支付方式:</p>
                             <p>{{:payStyleName}}</p>
                         </li>
						 {{for payDataList}}
                         <li>
                             <p>{{:payStyleName}}:</p>
                             <p class="color">¥{{:~liToYuan(paidFee)}}</p>
                         </li>
						 {{/for}}
                     </ul>
					{{else state=='11'}}
						<div class="pay-fail">您还没有完成付款！</div>
					{{else state=='91'}}
						<div class="pay-fail">订单已经关闭！</div>
					{{/if}}
                 </div> 
         </div> 
		</script>
  </div>
 <!--底部-->
<%@ include file="/inc/foot.jsp" %>
 <!--底部 结束-->
<script type="text/javascript">
var pager;
var orderDetail = $.parseJSON('${orderDetail}');
(function () {
	seajs.use('app/jsp/order/orderInfoDetail', function (OrderInfoDetailPager) {
		pager = new OrderInfoDetailPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>


