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
					<li><a href="${_base}"><img
							src="${_slpbase }/images/hnlogo.png"></a></li>
					<li>订单确认</li>
				</ul>
			</div>
			<div class="fsast-search">
				<ul>
					 <li><input type="text" class="fsast-xlarge" id="serachName"></li>
                  	 <li><A href="javascript:void(0);" id="BTN_SEARCH"><i class="icon-search"></i></A></li>
				</ul>
			</div>

		</div>
	</div>
	<!--导航区域结束-->
	<form id="orderSubmitForm" action="${_base}/pay/orderPay" target="_blank" method="post" >
	
	</form>
	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>
	<!--底部 结束-->
	<script type="text/javascript">
		var pager;
		var orderSubmitJson = $.parseJSON('${orderSubmitJson}');
		(function() {
			seajs.use('app/jsp/order/orderSubmit', function(OrderSubmitPager) {
				pager = new OrderSubmitPager({
					element : document.body
				});
				pager.render();
			});
		})();
		
		
		var index_search_pager;
		(function () {
			seajs.use('app/jsp/search/search', function (SearchPager) {
				index_search_pager = new SearchPager({element: document.body});
				index_search_pager.render();
			});
		})();
	</script>
	<script id="orderSubmitTemplate" type="text/x-jsrender">
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
						{{for ordProductResList}}
						<tr>
							<td class="sp">
								<table width="100%" border="0">
									<tr>
										<td class="word" width="25%"><img src="{{:imageUrl}}"></td>
										<td><A href="${_base}/product/detail?skuId={{:skuId}}">{{:skuName}}</A></td>
									</tr>
								</table>
							</td>
							<td class="ash">¥{{:~liToYuan(salePrice)}}</td>
							<td>{{:buySum}}</td>
							<td class="bold">¥{{:~liToYuan(skuTotalFee)}}</td>
						</tr>
						{{/for}}
					</table>
				</div>

				<div class="total-amount">
					<ul>
						<li>
							<p class="word">{{:ordProductResList.length}}件商品总计:</p>
							<p class="right">¥{{:~liToYuan(ordFeeInfo.totalFee) }}</p>
						</li>
						<li>
							<p class="word">运费:</p>
							<p class="right">＋¥{{:~liToYuan(expFee)}}</p>
						</li>
						<li>
							<p class="word">活动优惠:</p>
							<p class="right">－¥{{:~liToYuan(ordFeeInfo.discountFee)}}</p>
						</li>
						<li>
							<p class="word">账户余额:</p>
							<p class="right">－¥{{:~liToYuan(balanceFee) }}</p>
						</li>
						<li>
							<p class="word">实付款:</p>
							<p class="right">
								<span id="adjustFee">¥{{:~liToYuan(ordFeeInfo.totalFee) }}</span>
							</p>
						</li>
					</ul>

				</div>

			</div>
	
			<c:choose>
				<c:when test="${empty user}"></c:when>
					<c:otherwise>
						<div class="recharge-bj-tow">
							<!--白色背景-->
							<div class="balance-title">
								<p>
									<input id="useBalanceChk" type="checkbox" class="checkbox">
								</p>
								<p>使用账户余额 （{{:~liToYuan(balance)}}元可用）</p>
							</div>
					</c:otherwise>
			</c:choose>
				<div class="balance-table" style="display: none;">
					<ul>
						<li>
							<p>本次使用余额</p>
							<p>
								<input id="useBalance" type="text" class="int-mini" disabled="true">
							</p>
							<p>元</p>
						</li>
						<li>
							<p>账户支付密码</p>
							<p>
								<input id="userPassword" type="password" class="int-small">
							</p>
							<p>
								<input id="useBalanceBtn" type="button"
									class="slp-btn immedtl-btn" value="确认使用">
							</p>
							<p class="color">
								<A href="${_base}/user/payPassword/updatePayPassword">忘记密码/未设置密码？</A>
							</p>
						</li>
					</ul>
				</div>
			</div>
		
			<div class="recharge-bj-tow recharge-bj-three">
				<!--白色背景-->
				<div class="right-btn">
					<input id="orderId" name="orderId"  type=hidden value={{:orderId}}>
					<input id="abalance" name="abalance"  type=hidden value={{:~liToYuan(balance)}}>
					<input id="bamount" name="bamount"  type=hidden value={{:~liToYuan(ordFeeInfo.totalFee) }}>
					<input id="gotoPayBtn" type="submit" class="slp-btn topay-btn"
						value="去支付">
				</div>
			</div>

		</div>
	</div>
	</script>
</body>
</html>
