<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>快充</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase}/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script src="${_slpbase}/scripts/frame.js" type="text/javascript"></script>

</head>

<body>
<!--顶部菜单开始-->
<%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
<div class="mainbav-bj">
 <div class="mainbav">
       <!-- 主导航 -->
    	<%@ include file="/inc/logo-nav-menu.jsp" %>
    	<!-- 结束 -->
 </div>
 </div>
<!--导航区域结束-->
     
    <h1>订单失败</h1>
   
   
<%@ include file="/inc/foot.jsp" %>
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/order/orderfail', function (OrderFailPager) {
					pager = new OrderFailPager({element: document.body});
					pager.render();
				});
			})();
			
		</script>
</body>
</html>



