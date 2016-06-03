<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>订单详情-快充话费－已成功</title>
<link href="styles/modular.css" rel="stylesheet" type="text/css">
<link href="styles/global.css" rel="stylesheet" type="text/css">
<link href="styles/frame.css" rel="stylesheet" type="text/css">
<link href="styles/font-awesome.css" rel="stylesheet" type="text/css">
</head>

<body>
<!--弹出删除弹出框  中-->
<div class="eject-big">
		<div class="eject-medium">
			<div class="eject-medium-title">
				<p>明细</p>
				<p class="img"><A href="#"></A></p>
			</div>
			<div class="eject-medium-list">
				<div class="eject-contacts-list eject-bie-height">
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
					<p>135455555555 </p>
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
    <div class="big-wrapper" id="orderData"><!--内侧居中框架-->
    	<script id="orderTemple" type="text/x-jsrender">
			<div class="payment-title">
                 <p><a href="#">账户中心</a>&gt;</p>
                 <p><a href="#">我的订单</a>&gt;</p>
                 <p><a href="#">订单号:</a></p>
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
                         <li>
                             <p>支付时间:</p>
                             <p>{{:~timesToFmatter(payTime)}}</p>
                         </li>
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
                        <td>手机号/数量</td>
                        <td>充值额</td>
                        <td>单价</td>
                        <td>小计</td>
                      </tr>
					  {{for productList}}                                                                                                                                                                       
                      <tr class="click">
                        <td>{{:basicOrgName}}</td>
                        <td>{{:provinceName}}</td>
                        <td>{{:prodExtendInfo.split(",").length}}/<span class="batch">明细</span></td>
                        <td>{{:~liToYuan(chargeFee)}}元</td>
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
                          <p class="color">45个</p>
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
                     <ul>
                         <li>
                             <p>实付总金额:</p>
                             <p class="color">¥{{:~liToYuan(paidFee)}}</p>
                         </li>
                         <li>
                             <p>支付方式:</p>
                             <p>{{:payStyleName}}</p>
                         </li>
                         <li>
                             <p>余额:</p>
                             <p class="color">¥329.00</p>
                         </li>
                         <li>
                             <p>支付宝:</p>
                             <p class="color">¥329.00</p>
                         </li>
                     </ul>
                 </div> 
         </div> 
		</script>
    </div>
     </div>
 </div>
 <!--底部-->
<%@ include file="/inc/foot.jsp" %>
 <!--底部 结束-->

</body>
</html>
<script src="scripts/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="scripts/frame.js" type="text/javascript"></script>


