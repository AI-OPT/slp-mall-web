<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>资质申请－类型选择</title>
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
<!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资质认证</a></p>
      </div>
      <div class="account-bj account-border">
       			<div class="choice-title">
                   <p class="word">请选择您要进行资质认证的类型</p>
                   <p>选定身份并进行认证后不得随意更改</p>   
              </div>
              <div class="choice-list">
              <ul class="current"  id="personal" onclick="addPersonalClass();">
              <li><img src="${_slpbase}/images/choice-a.png"></li>
              <li>个人</li>
              </ul>
              <ul  id="enterprise" onclick="addEnterpriseClass();">
              <li><img src="${_slpbase}/images/choice-b.png"></li>
              <li>企业</li>
              </ul>
              </div>
              <div class="choice-title">
                   <p><input type="button" class="slp-btn details-btn" id="toIdentity" value="去认证"></p>
                   
              </div>
      		</div>
  		</div>  
   		</div>
   	</div>
		 <!--底部-->
    		<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
</body>
</html>
<script type="text/javascript">
	(function() {
		seajs.use('app/jsp/user/qualification/agent-select', function(AgengSelectPager) {
			var pager = new AgengSelectPager();
			pager.render();
		});
	})();
</script>
