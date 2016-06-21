<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>账户余额-充值完成</title>
<%@ include file="/inc/inc.jsp"%>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet"
	type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet"
	type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet"
	type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet"
	type="text/css">

</head>

<body>
	<!--顶部菜单-->
	<%@ include file="/inc/top-menu.jsp"%>
	<!--顶部菜单结束-->

	<!--导航区域-->
	<%@ include file="/inc/user-nav.jsp"%>
	<!--导航区域结束-->
	<!--订单详情-->
	<div class="fsast-charge">
		<div class="big-wrapper">
			<!--内侧居中框架-->
			<!--我的订单-->
			<!--我的订单左侧-->
			<%@ include file="/inc/user-leftmenu.jsp"%>
			<!--／我的订单左侧结束-->
			<!--账户余额右侧-->
			<div class="my-order-cnt">
				<div class="payment-title">
					<p>
						<a href="#">账户中心</a>&gt;
					</p>
					<p>
						<a href="#">资产中心</a>&gt;
					</p>
					<p>
						<a href="${accountBalanceLink }">账户余额</a>&gt;
					</p>
					<p>
						<a href="${accountRechargeOneLink }">充值</a>&gt;
					</p>
					<p>
						<a href="#">充值完成</a>
					</p>
				</div>
				<div class="account-bj">
					<!--步骤-->
					<div class="steps steps-four">
						<ul>
							<li class="yellow-border"></li>
							<li class="yellow-yuan">1</li>
							<li class="yellow-word">填写充值金额</li>
						</ul>
						<ul>
							<li class="yellow-border"></li>
							<li class="yellow-yuan">2</li>
							<li class="yellow-word">充值确认</li>
						</ul>
						<ul>
							<li class="yellow-border"></li>
							<li class="yellow-yuan">3</li>
							<li class="yellow-word">在线支付</li>
						</ul>
						<ul>
							<li class="yellow-border"></li>
							<li class="yellow-yuan"><i class="icon-ok"></i></li>
							<li class="yellow-word">充值完成</li>
						</ul>
					</div>
					<!--/步骤结束-->
					<div class="recharge-success"><c:if  test="${payStates eq '00'}"> 
							<p> 
						    <img src="${_slpbase }/images/succ.png">
							</p>
							<p class="word">
								您已经充值成功，充值金额<span class="jine">￥${orderAmount }</span>
							</p>
						</c:if>  
						<c:if  test="${payStates eq '01'}"> 
						 	<p> 
						     <img src="${_slpbase }/images/fail.png" width="73px" height="73px">
							</p>
							<p class="word">
								对不起，充值失败<span class="jine">！</span>
							</p>
						</c:if> 
						<p class="success-box">
							<a href="${_base}/account/balance/detail">查看余额明细</a><a href="${_base }/user/payPassword/updatePayPassword">设置支付密码</a>
						</p>
					</div>
				</div>

			</div>

		</div>
	</div>
	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>

	<!--底部 结束-->
</body>

</html>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

