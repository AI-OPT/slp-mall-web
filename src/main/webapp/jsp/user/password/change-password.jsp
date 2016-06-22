<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>修改密码</title>
<script type="text/javascript" src="${_base }/resources/spm_modules/app/jsp/user/changePassword/md5.js"></script>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	(function() {
		seajs.use('app/jsp/user/changePassword/change-password', function(
				ChangePasswordPager) {
			var pager = new ChangePasswordPager();
			pager.render();
		});
	})();
	//关闭提示
	$(function(){
		$(".prompt-risk .img").click(function () {
			$(this).parent('.prompt-risk').hide();
			});
		}); 
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
          <p>修改密码</a></p>
      </div>
      <div class="account-bj">
   			 <div class="prompt-risk">
	            <p>提示：为确保您账户的安全性，请设置至少6位的密码，您可使用字母、数字、符合及下划线的组合</p>
	            <p class="img" style="margin-top:15px;"><img src="${_slpbase }/images/yue-1.png"></p>
	        </div>
       <!--步骤-->
          <div class="steps">
                     <ul>
	                     <li class="yellow-border" id="changePasswordBorder1"></li>
	                     <li class="yellow-yuan" id="changePasswordYuan1">1</li>
	                     <li class="yellow-word" id="changePasswordWord1">验证身份</li>
                     </ul>
                     <ul>
	                     <li class="ash-border" id="changePasswordBorder2"></li>
	                     <li class="ash-yuan" id="changePasswordYuan2">2</li>
	                     <li class="ash-word" id="changePasswordWord3">设置新密码</li>
                     </ul>
                      <ul>
	                     <li class="ash-border" id="changePasswordBorder3"></li>
	                     <li class="ash-yuan" id="changePasswordYuan3">3</li>
	                     <li class="ash-word" id="changePasswordWord3">完成修改</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
          <div class="center-main">
                <div class="list-int" id="change-password1">
                    <ul>
                        <li class="word">请输入原密码:</li>
                        <li><input type="password" id="password" class="int-medium" placeholder=""></li>
                        <li class="lable" style="display:none" id="passwordErrMsg"><img src="${_slpbase }/images/icon-e.png"><span class="red" id="passwordErrMsgShow">原密码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" id="next" class="slp-btn regsiter-btn" value="下一步"></li>
                    </ul>
                </div>
                
                <div class="list-int" id="change-password2" style="display:none">
                   <ul>
                        <li class="word">新密码:</li>
                        <li><input type="password" id="newPassword" class="int-medium" placeholder="请输入密码"></li>
                        <li class="lable" style="display:none" id="newPasswordErrMsg"><img id="passwordImg" src="${_slpbase }/images/icon-c.png"><span id="newPasswordErrMsgShow">6-20个字符，可用字母、数字及符号的组合</span></li>
                       <%--  <li style="display:none" id="newPasswordErrMsg"><img src="${_slpbase }/images/pass-a.png"><img src="${_slpbase }/images/pass-b.png"><img src="${_slpbase }/images/pass-c.png">有被盗风险,建议使用字母、数字和符号两种及以上组合</li> --%>
                    </ul>
                    <ul>
                        <li class="word">确认新密码:</li>
                        <li><input type="password" id="newPasswordConfirm" class="int-medium" placeholder="再次确认密码"></li>
                        <li class="lable" style="display:none" id="newPasswordConfirmErrMsg"><img id="confirmPasswordImg" src="${_slpbase }/images/icon-e.png"><span id="newPasswordConfirmErrMsgShow">两次输入的密码不一致</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" id="submit" class="slp-btn regsiter-btn" value="保存修改"></li>
                    </ul>
                </div>
                <div class="recharge-success" id="change-password3" style="display:none">
                 <p><img src="${_slpbase }/images/succ.png"></p>
                 <p class="word">密码修改成功！请牢记您的新密码</p>
              </div>
                <ul>
					<li class="checx-word">
					<input type="hidden" id="passwordEmptyFlag"/>
					<input type="hidden" id="passwordErrFlag"/>
					<input type="hidden" id="newPasswordEmptyFlag"/>
					<input type="hidden" id="newPasswordErrFlag"/>
					<input type="hidden" id="newPasswordConfirmEmptyFlag"/>
					<input type="hidden" id="passwordNotEqualFlag"/>
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
