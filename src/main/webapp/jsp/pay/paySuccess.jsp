<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>支付成功</title>
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
	<div class="fsast-bj">
		<div class="fsast-head">
			<div class="fsast-logo">
				<ul>
					<li><a href="${_base}"><img src="${_slpbase }/images/login-logo.png"></a></li>
					<li>快充支付</li>
				</ul>
			</div>
			<div class="fsast-search">
				<ul>
					<li><input type="text" class="fsast-xlarge"></li>
					<li><A href="#"><i class="icon-search"></i></A></li>
				</ul>
			</div>

		</div>
	</div>
	<!--导航区域结束-->

	<!--快充-->
	<div class="fsast-charge">
		<!--支付成功-->
		<div class="payment-success">
			<p>
				<img src="${_slpbase }/images/zhif-cg.png">
			</p>
			<p class="word">您的订单已经支付成功！</p>
			<p>订单号：${orderId} 实付金额：${orderAmount}元</p>
			<p class="alink">
				<A href="${_base}/myorder/detail?orderId=${orderId}&orderType=${orderType}">查看订单</A>|<A href="${_base}/myorder/list">账户中心</A>|<A href="${_base}/home">返回首页</A>
			</p>
			<p class="blod""><span id="timeAlartSp">10</span>秒后自动跳转至“我的订单”</p>
			</ul>
		</div>
	</div>

	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>
	<!--底部 结束-->
	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/pay/paySuccess', function(PaySuccessPager) {
				pager = new PaySuccessPager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>
</body>
</html>
