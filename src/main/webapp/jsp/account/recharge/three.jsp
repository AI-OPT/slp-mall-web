<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>账户余额-在线支付</title>
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
						<a href="#">在线支付</a>
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
							<li class="ash-border"></li>
							<li class="ash-yuan"><i class="icon-ok"></i></li>
							<li>充值完成</li>
						</ul>
					</div>
					<!--/步骤结束-->
					<div class="list-int">
						<ul>
							<li class="word">充值金额:</li>
							<li><span class="jine">¥98.00</span></li>
						</ul>
						<ul>
							<li class="word1">请选择支付方式:</li>
							<li class="current"><A href="#"><img
									src="${_slpbase }/images/kc-1.png"></A></li>
							<li><A href="#"><img src="${_slpbase }/images/kc-2.png"></A></li>
							<li><A href="#"><img src="${_slpbase }/images/kc-3.png"></A></li>
						</ul>
						<ul class="int-mar">
							<li class="word">已选:</li>
							<li>支付宝</li>
						</ul>
						<ul>
							<li class="checx-word"><input type="button"
								class="slp-btn regsiter-btn" value="下一步"
								onclick="location.href='${_base}/account/recharge/four'"></li>
						</ul>
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

