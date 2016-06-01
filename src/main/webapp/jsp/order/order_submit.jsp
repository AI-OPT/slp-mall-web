<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>提交订单</title>
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
					<li><a href="#"><img src="images/login-logo.png"></a></li>
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

	<!--提交订单-->
	<div class="fsast-charge">
		<div class="big-wrapper">
			<!--内侧居中框架-->
			<div class="payment-title">
				<p>请确认您的商品及支付信息</p>
			</div>
			<div class="recharge-bj-tow">
				<!--白色背景-->

				<div class="information-title">
					<p>商品信息</p>
				</div>

				<div class="shopping-cart mar">
					<table width="100%" border="0">
						<tr class="bj-s">
							<td width="35%">商品信息</td>
							<td width="35%">单价</td>
							<td width="15%">数量</td>
							<td width="15%">小计</td>
						</tr>
						</table>
						</div>
						<tr>
							<td class="sp">
								<table width="100%" border="0">
									<tr>
										<td class="word" width="25%"><img src="images/sim.png"></td>
										<td><A href="#">中国电信手机充值北京手机充值10远电信手机充值电信手机充值</A></td>
									</tr>
								</table>
							</td>
							<td class="ash">¥300.00</td>
							<td>1</td>
							<td class="bold">¥300.00</td>
						</tr>
						<tr>
							<td class="sp">
								<table width="100%" border="0">
									<tr>
										<td class="word" width="25%"><img src="images/sim.png"></td>
										<td><A href="#">中国电信手机充值北京手机充值10远电信手机充值电信手机充值</A></td>
									</tr>
								</table>
							</td>
							<td class="ash">¥300.00</td>
							<td>1</td>
							<td class="bold">¥300.00</td>
						</tr>
						<tr>
							<td class="sp">
								<table width="100%" border="0">
									<tr>
										<td class="word" width="25%"><img src="images/sim.png"></td>
										<td><A href="#">中国电信手机充值北京手机充值10远电信手机充值电信手机充值</A></td>
									</tr>
								</table>
							</td>
							<td class="ash">¥300.00</td>
							<td>1</td>
							<td class="bold">¥300.00</td>
						</tr>
						<!--不可点击-->


					</table>
				</div>

				<div class="total-amount">
					<ul>
						<li>
							<p class="word">3件商品总计:</p>
							<p class="right">¥2300.00</p>
						</li>
						<li>
							<p class="word">运费:</p>
							<p class="right">＋¥20.00</p>
						</li>
						<li>
							<p class="word">活动优惠:</p>
							<p class="right">－¥20.00</p>
						</li>
						<li>
							<p class="word">账户余额:</p>
							<p class="right">－¥300.00</p>
						</li>
						<li>
							<p class="word">实付款:</p>
							<p class="right">
								<span>¥2300.00</span>
							</p>
						</li>
					</ul>

				</div>

			</div>
			<div class="recharge-bj-tow">
				<!--白色背景-->
				<div class="balance-title">
					<p>
						<input type="checkbox" class="checkbox">
					</p>
					<p>使用账户余额 （89.0元可用）</p>
				</div>
				<div class="balance-table" style="display: none;">
					<ul>
						<li>
							<p>本次使用余额</p>
							<p>
								<input type="text" class="int-mini">
							</p>
							<p>元</p>
						</li>
						<li>
							<p>账户支付密码</p>
							<p>
								<input type="password" class="int-small">
							</p>
							<p>
								<input type="button" class="slp-btn immedtl-btn" value="确认使用">
							</p>
							<p class="color">
								<A href="#">忘记密码</A>／<A href="#">未设置密码？</A>
							</p>
						</li>
					</ul>
				</div>
			</div>

			<div class="recharge-bj-tow recharge-bj-three">
				<!--白色背景-->
				<div class="right-btn">
					<input type="button" class="slp-btn topay-btn" value="去支付">
				</div>
			</div>

		</div>
	</div>
	</div>
	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>
	<!--底部 结束-->
	<script type="text/javascript">
		var pager;
		(function() {
			seajs.use('app/jsp/order/orderSubmit', function(OrderSubmitPager) {
				pager = new OrderSubmitPager({
					element : document.body
				});
				pager.render();
			});
		})();
	</script>
</body>
</html>
