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
		
		function clearPasswordInputValue(){
			$("#passwordInput").val("");
		}
		//关闭提示
		$(function(){
			$(".prompt-risk .img").click(function () {
				$(this).parent('.prompt-risk').hide();
				});
			}); 
  </script>
</head>
 

<body onload="clearPasswordInputValue()">
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
    
       <!--提示风险-->
        <div class="prompt-risk">
            <p>提示：在使用账户余额等资产时，需要输入支付密码，提升账户安全度。支付密码不允许与登录密码相同！</p>
            <p class="img" style="margin-top:15px;"><img src="${_slpbase }/images/yue-1.png"/></p>
        </div>
      
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
                     <li class="yellow-word">设置支付密码</li>
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
                        <li class="word">请输入支付密码:</li>
                        <li><input type="password" class="int-medium" placeholder="请输入支付密码"  id="passwordInput" value=""/></li>
                        <li class="lable" id="errorPawMsg" style="display: none;">
                        	<img src="${_slpbase }/images/icon-a.png" id="passwordImage"/><span id="showPawMsg">6-20个字符，可用字母、数字及符号的组合</span>
                        </li>
                        <label>
                          <span id="strength_L" style="display: none;"><img src="${_slpbase }/images/pass-a.png" />有被盗风险,建议使用字母、数字和符号两种及以上组合</span>
                          <span id="strength_M" style="display: none;"><img src="${_slpbase }/images/pass-b.png" />安全强度适中，可以使用三种以上的组合来提高安全强度</span>
                          <span id="strength_H" style="display: none;"><img src="${_slpbase }/images/pass-c.png" id="strength_H"/>你的密码很安全</span>
                         </label>
                    </ul>
                    <ul>
                        <li class="word">确认支付密码:</li>
                        <li><input type="password" class="int-medium" placeholder="再次确认密码" id="confirmationPassword"/></li>
                        <li class="lable" id="errorPasswordMsg" style="display: none;"><img src="${_slpbase }/images/icon-a.png" id="confirmationPasswordImage"/><span id="showPasswordMsg">两次输入的密码不一致</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word">
                          <input type="button" class="slp-btn regsiter-btn" id="submitBtn" value="提交"/>
                          <input type="hidden" id="passwordFlag" value=""/>
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

