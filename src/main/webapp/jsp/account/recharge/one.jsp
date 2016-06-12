<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>账户余额-填写充值金额</title>
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
						<a href="#">填写充值金额</a>
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
							<li class="ash-border"></li>
							<li class="ash-yuan">2</li>
							<li class="ash-word">充值确认</li>
						</ul>
						<ul>
							<li class="ash-border"></li>
							<li class="ash-yuan">3</li>
							<li class="ash-word">在线支付</li>
						</ul>
						<ul>
							<li class="ash-border"></li>
							<li class="ash-yuan"><i class="icon-ok"></i></li>
							<li>充值完成</li>
						</ul>
					</div>
					<!--/步骤结束-->
					<div class="list-int">
						<form name="oneForm" id="oneForm" method="post" action="${_base}/payment/recharge/two">
							<ul>
								<li class="word">充值金额:</li>
								<li><input type="text" class="int-medium" id="payAmount"  name="payAmount"
									placeholder="请输入金额"></li>
								<li class="lable"><img src="${_slpbase }/images/icon-c.png"><span>请填写不少于10元的整数金额</span></li>
							</ul>
							<ul>
								<li class="checx-word"><input type="button" 
									class="slp-btn regsiter-btn" value="下一步"
									onclick="pager._formSubmit()"></li>
							</ul>
						</form>
					</div>
				</div>

			</div>

		</div>
	</div>
	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>

	<!--底部 结束-->
</body><script type="text/javascript">
	var pager;
	(function() {
		seajs.use('app/jsp/account/recharge/one', function(OnePager) {
			pager = new OnePager({
				element : document.body
			});
		});
	})();
</script>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

</html>

