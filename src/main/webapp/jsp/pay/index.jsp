
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String bp = request.getContextPath();
	request.setAttribute("_base", bp);
	
	response.setHeader("Cache-Control","no-cache");   
	response.setDateHeader("Expires",0);   
	response.setHeader("Pragma","No-cache");
	
%>	
<!DOCTYPE HTML>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="${_base }/ui/css/global.css" />
<link href="${_base}/ui/css/mian.css" rel="stylesheet" type="text/css" />
<title>支付中心</title>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

body {
	font-size: 12px;
}

#menu {
	width: 450px;
	height: 300px;
	overflow: hidden;
	margin: 100px auto;
	border: 1px solid #003C9D;
}

#menu #nav {
	display: block;
	width: 500%;
	padding: 0;
	margin: 0;
	list-style: none;
}

#menu #nav li {
	float: left;
	width: 120px;
}

#menu #nav li a {
	display: block;
	line-height: 27px;
	text-decoration: none;
	padding: 0 0 0 5px;
	text-align: center;
	color:  #000;
}

#menu_con {
	width: 358px;
	height: 135px;
	border-top: none
}

.tag {
	padding: 10px;
	overflow: hidden;
}

.selected {
	background: #003C9D;
	color: #fff;
}
</style>
<script type="text/javascript">
	function changeTag(num){
		for(i=0;i<4;i++){
			$("#btn_"+i).attr("class","");
			$("#tag_"+i).hide();
		}
		$("#btn_"+num).attr("class","selected");
		$("#tag_"+num).show();
	}
</script>
</head>
<body>

	<!--代码部分begin-->
	<div id="menu">
		<!--tag标题-->
		<ul id="nav">
			<li><a id="btn_0" style="cursor:pointer" onclick="changeTag(0)" class="selected">PC/WAP支付</a></li>
			<li><a id="btn_1" style="cursor:pointer" onclick="changeTag(1)" class="">移动APP支付</a></li>
			<li><a id="btn_2" style="cursor:pointer" onclick="changeTag(2)" class="">退款</a></li>
			<li><a id="btn_3" style="cursor:pointer" onclick="changeTag(3)" class="">提现</a></li>
		</ul>
		<!--二级菜单-->
		<div id="menu_con">
			<div class="tag"  id="tag_0" style="display: block">
				<form action="${_base}/pay/orderPay" target="_blank" method="post">
					<table>
						<tr>
							<td>租户ID:</td>
							<td><input name="tenantId" type="text"
								value="${tenantId }" ></td>
						</tr>
						<tr>
							<td>订单号:</td>
							<td><input name="orderId" type="text"
								value="${orderId }"></td>
						</tr>
						<tr>
							<td>订单描述:</td>
							<td><input name="subject" type="text" value="${subject }"></td>
						</tr>
						<tr>
							<td>订单金额:</td>
							<td><input name="orderAmount" type="text" value="${orderAmount }"></td>
						</tr>
						<tr>
							<td>前台通知:</td>
							<td><input name="returnUrl" type="text"
								value="${returnUrl }"></td>
						</tr>
						<tr>
							<td>后台台通知:</td>
							<td><input name="notifyUrl" type="text"
								value="${notifyUrl }"></td>
						</tr>
						<tr>
							<td>终端来源：</td>
							<td><select id="requestSource" name="requestSource">
									<option selected value="1">WEB</option>
									<option value="2">WAP</option>
									<option value="3">APP</option>
									<option value="4">微信</option>
							</select></td>
						</tr>
						<tr>
							<td><input type="submit" value="提交" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="tag" id="tag_1" style="display: none">
				<form action="${_base}/demo/appPay" target="_blank" method="post">
					<table>
						<tr>
							<td>租户ID:</td>
							<td><input name="tenantId" type="text"
								value="${tenantId }" ></td>
						</tr>
						<tr>
							<td>订单号:</td>
							<td><input name="orderId" type="text"
								value="${orderId }"></td>
						</tr>
						<tr>
							<td>订单描述:</td>
							<td><input name="subject" type="text" value="${subject }"></td>
						</tr>
						<tr>
							<td>订单金额:</td>
							<td><input name="orderAmount" type="text" value="${orderAmount }"></td>
						</tr>
						<tr>
							<td>前台通知:</td>
							<td><input name="returnUrl" type="text"
								value="${returnUrl }"></td>
						</tr>
						<tr>
							<td>后台台通知:</td>
							<td><input name="notifyUrl" type="text"
								value="${notifyUrl }"></td>
						</tr>
						<tr>
							<td>终端来源：</td>
							<td><select id="requestSource" name="requestSource">
									<option selected value="3">APP</option>
							</select></td>
						</tr>
						<tr>
							<td>第三方支付机构：</td>
							<td><select id="payOrgCode" name="payOrgCode">
									<option selected value="WEIXIN">微信</option>
									<option value="ZFB">支付宝</option>
									<option value="YL">银联</option>
							</select></td>
						</tr>
						<tr>
							<td><input type="submit" value="提交" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>