<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<%@ include file="/inc/inc.jsp"%>
   <title>绑定邮箱－填写邮箱</title>
	<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
	    var uuid = "${uuid}";
		(function() {
			seajs.use([ 'app/jsp/user/bandemail/confirmInfo'], function(ConfirmInfoPager) {
				var pager = new ConfirmInfoPager({
					element : document.body
				});
				pager.render();
			});
		})(); 
  </script>
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
          <p><a href="#">帐户设置</a>&gt;</p>
          <p>绑定邮箱</a></p>
      </div>
      <div class="account-bj">
    
       <!--步骤-->
          <div class="steps">
                     <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">1</li>
                     <li class="yellow-word">验证身份</li>
                     </ul>
                     <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">2</li>
                     <li class="yellow-word">验证邮箱</li>
                     </ul>
                      <ul>
                     <li class="ash-border"></li>
                     <li class="ash-yuan">3</li>
                     <li class="ash-word">完成修改</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
             <div class="verify-mailbox">
             	<ul>
             		<li><img src="${_slpbase }/images/icon-mail.png"/></li>
             		<li class="word">邮箱绑定邮件已发送至您的邮箱：${email}</li>
             		<li><a href="#">请在邮件中点击验证链接完成邮箱绑定<span>（验证链接30分钟内有效)</span></a></li>
             		<!-- <li class="vermail-btn"><input type="button" class="slp-btn regsiter-btn" value="查看验证邮件"/></li> -->	
             	</ul>
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

