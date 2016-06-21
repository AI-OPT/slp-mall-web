<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<%@ include file="/inc/inc.jsp"%>
   <title>设置支付密码</title>
	<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		(function() {
			seajs.use([ 'app/jsp/user/payPassword/payPasswordConfirmInfo'], function(ConfirmInfoPager) {
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
          <p><a>设置密码</a></p>
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
                     <li class="ash-border"></li>
                     <li class="ash-yuan">2</li>
                     <li class="ash-word">设置支付密码</li>
                     </ul>
                      <ul>
                     <li class="ash-border"></li>
                     <li class="ash-yuan">3</li>
                     <li class="ash-word">完成</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
              <div class="list-int">
                    <ul>
                        <li class="word">当前手机号:</li>
                        <li id="phone">${userInfo.userMp}</li>
                        <li class="lable" style="display: none;" id="errorPhoneMsg"><img src="${_slpbase }/images/icon-a.png" id="phoneImage"/><span id="phoneText">请输入正确有效的手机号</span></li>
                    </ul>
                    <ul>
                        <li class="word">手机验证码:</li>
                        <li><input type="text" class="int-small" id="phoneVerifyCode"/></li>
                        <li class="re-btn"><input type="button" class="int-btn" id="sendVerify" value="获取短信验证码"/></li>
                        <li class="lable" style="display: none;" id="phoneVerifyCodeError"><img src="${_slpbase }/images/icon-a.png"/><span class="red" id="verifyCodeMsg">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" id="passwordNext" class="slp-btn regsiter-btn" value="下一步"/></li>
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

