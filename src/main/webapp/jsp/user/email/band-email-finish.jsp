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
	(function() {
		seajs.use([ 'app/jsp/user/bandemail/setEmail' ], function(BandEmailPager) {
			var pager = new BandEmailPager({
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
	                     <li class="yellow-word">填写邮箱</li>
                     </ul>
                     <ul>
	                     <li class="yellow-border"></li>
	                     <li class="yellow-yuan">2</li>
	                     <li class="yellow-word">绑定新邮箱</li>
                     </ul>
                      <ul>
	                     <li class="yellow-border"></li>
	                     <li class="yellow-yuan">3</li>
	                     <li class="yellow-word">完成修改</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
            <div class="recharge-success">
                 <p><img src="${_slpbase }/images/succ.png"/></p>
                 <p class="word">邮箱 ${email}绑定成功！!</p>
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

