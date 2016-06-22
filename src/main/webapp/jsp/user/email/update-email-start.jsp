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
		function radioChange(){
			$("#phoneVerifyCode").val("");
			var radioValue = $('input:radio:checked').val();
			if(radioValue=="2"){
				$("#emailType").attr("checked",'checked');
				$("#emailValidate").show();
				$("#phoneValidate").hide();
				$("#paymentPassword").hide();
			}
			if(radioValue=="1"){
				$("#phoneType").attr("checked",'checked');
				$("#emailValidate").hide();
				$("#phoneValidate").show();
				$("#paymentPassword").hide();
			}
			if(radioValue=="3"){
				$("#passwordType").attr("checked",'checked');
				$("#paymentPassword").show();
				$("#emailValidate").hide();
				$("#phoneValidate").hide();
			}
		}
		//关闭提示
		$(function(){
			$(".prompt-risk .img").click(function () {
				$(this).parent('.prompt-risk').hide();
				});
			}); 
  </script>
</head>
 

<body onload="radioChange()">
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
     <c:if test="${confirminfo!=''&&confirminfo=='fail'}">
    	 <!--提示验证失败提示-->
        <div class="prompt-risk">
            <p>提示：邮箱验证失败，请重新填写邮箱地址进行验证</p>
            <p class="img" style="margin-top:15px;"><img src="${_slpbase }/images/yue-1.png"/></p>
        </div>
      </c:if>
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
	                     <li class="ash-word">绑定新邮箱</li>
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
                        <li class="word">身份验证方式:</li>
                        <li class="checkbox-box">
                           <span><input name="radio" class="int-chec radioa" type="radio" name="confirmType1" id="emailType" checked="checked" value="2"/>邮箱验证</span>
                           <span><input name="radio" class="int-chec radiob" type="radio" name="confirmType1" id="phoneType" value="1"/>手机号验证</span>
                           <!-- <span><input name="radio" class="int-chec radioc" type="radio" name="confirmType1" id="passwordType" value="3"/>支付密码验证</span> -->
                        </li>
               	 	</ul>
               	 	<div class="phone" id="emailValidate">
                    <ul>
                        <li class="word">当前邮箱:</li>
                        <li id="email">${userInfo.userEmail}</li>
                    </ul>
                    <ul>
                        <li class="word">验证码:</li>
                        <li><input type="text" class="int-small" id="pictureVerifyCode" /></li>
                         <li><img id="random_img" src="${_base}/user/verify/getImageVerifyCode"/></li>
                        <li class="lable" id="verifyCodeErrorMsg" style="display: none;"><img src="${_slpbase }/images/icon-a.png"/><span class="red" id="verifyCodeMsg">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word">
                        	<input type="button" class="slp-btn regsiter-btn" value="发送验证邮件"  id="sendEmailBtn"/>
                        	<input type="hidden" id="updateEmail" value="updateEmail"/>
                        </li>
                    </ul>
                 </div>
                <div class="mail" id="phoneValidate" style="display: none;">
                    <ul>
                        <li class="word">账户注册手机号:</li>
                        <li id="phone">${userInfo.userMp}</li>
                    </ul>
                    <ul>
                        <li class="word">手机验证码:</li>
                        <li><input type="text" class="int-small" id="phoneVerifyCode" value=""/></li>
                        <li class="re-btn"><input type="button" class="int-btn" id="sendVerify" value="获取短信验证码"/></li>
                        <li class="lable" style="display: none;" id="phoneVerifyCodeError"><img src="${_slpbase }/images/icon-a.png"/><span class="red" id="phoneVerifyCodeMsg">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" id="next" value="下一步"/></li>
                    </ul>
                 </div>
                <!--  <div class="password" style="display: none;" id="paymentPassword">
                    <ul>
                        <li class="word">支付密码:</li>
                        <li><input type="password" class="int-medium" placeholder=""/></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" value="发送验证邮件" id="submitBtn"/>
                        </li>
                    </ul>
                 </div> -->
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

