<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<meta charset="UTF-8">
<title>修改邮箱－邮箱验证－绑定新邮箱</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css"/>
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css"/>
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css"/>
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css"/>
</head>
<script type="text/javascript">
		var uuid = "${uuid}";
		(function() {
			seajs.use([ 'app/jsp/user/bandemail/setEmail'], function(ConfirmInfoPager) {
				var pager = new ConfirmInfoPager({
					element : document.body
				});
				pager.render();
			});
		})(); 
  </script>
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
          <p><a>绑定邮箱</a></p>
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
	                     <li class="yellow-word">绑定新邮箱</li>
                     </ul>
                      <ul>
	                     <li class="ash-border"></li>
	                     <li class="ash-yuan">3</li>
	                     <li class="ash-word">完成修改</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
              <div class="list-int">
                    <ul>
                        <li class="word">新邮箱:</li>
                        <li><input type="text" class="int-medium" placeholder="请填写本人邮箱" id="email"/></li>
                        <li class="lable" id="emailMsgError" style="display: none"><img id="emailImages" src="${_slpbase }/images/icon-a.png"/><span id="emailMsg">使用您常用的邮箱</span></li>
                    </ul>
                    <ul>
                        <li class="word">验证码:</li>
                        <li><input type="text" class="int-small" id="verifyCode"></li>
                        <li><img id="random_img" src="${_base}/user/verify/getImageVerifyCode"></li>
                        <li ><a id="changeImage"  class="alink" href="" >换一张</a></li>
                        <li class="lable" id="verifyCodeErrorMsg" style="display: none;"><img src="${_slpbase }/images/icon-a.png"/><span class="red" id="verifyCodeMsg">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word">
                        	<input type="button" class="slp-btn regsiter-btn" id="bandNewEmail" value="发送验证邮件">
                        	<input type="hidden" id="bandNewEmail" value="bandNewEmail"/>
                        </li>
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

