<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>账户余额明细</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">

</head>

<body>
 <!--顶部菜单-->
 <%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
<%@ include file="/inc/user-nav.jsp" %>
<!--导航区域结束-->
 <!--订单详情-->
<div class="fsast-charge">
    <div class="big-wrapper"><!--内侧居中框架-->
    <!--我的订单-->
 <!--我的订单左侧-->
    <%@ include file="/inc/user-leftmenu.jsp" %>
 <!--／我的订单左侧结束-->  
 <!--账户余额右侧-->  
   <%@ include file="/jsp/account/balance_detail/balance-detail.jsp" %>
   
    </div>
     </div>
 </div>
   <!--底部-->
    <%@ include file="/inc/foot.jsp" %>
   
   <!--底部 结束-->
</body>

</html>
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

