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
		seajs.use([ 'app/jsp/user/bandemail/setEmail' ], function(BandEmailPager) {
			var pager = new BandEmailPager({
				element : document.body
			});
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
          <p><a >绑定邮箱</a></p>
      </div>
      <div class="account-bj">
      <c:if test="${confirminfo!=''&&confirminfo=='fail'}">
    	 <!--提示验证失败提示-->
        <div class="prompt-risk">
            <p>提示：邮箱验证失败，请重新填写邮箱地址进行验证</p>
            <p class="img"><img src="${_slpbase }/images/yue-1.png"/></p>
        </div>
      </c:if>
       <!--步骤-->
          <div class="steps">
                     <ul>
	                     <li class="yellow-border"></li>
	                     <li class="yellow-yuan">1</li>
	                     <li class="yellow-word">填写邮箱</li>
                     </ul>
                     <ul>
	                     <li class="ash-border"></li>
	                     <li class="ash-yuan">2</li>
	                     <li class="ash-word">验证邮箱</li>
                     </ul>
                      <ul>
	                     <li class="ash-border"></li>
	                     <li class="ash-yuan">3</li>
	                     <li class="ash-word">完成绑定</li>
                     </ul>                                              
           </div>                                          
          <!--/步骤结束-->
              <div class="list-int">
                    <ul>
                        <li class="word">我的邮箱:</li>
                        <li><input type="text" class="int-medium" placeholder="请填写本人邮箱" id="email" value="${email}"/></li>
                        <li class="lable" id="emailMsgError" style="display: none"><img id="emailImages" src="${_slpbase }/images/icon-a.png"/><span id="emailMsg">使用您常用的邮箱</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" id="sendEmailBtn" value="发送验证邮件"/></li>
                    </ul>
                
                </div>
      </div>

  </div>  
     
     
    </div>
     </div>
   <!--底部-->
    <%@ include file="/inc/foot.jsp" %>
    </div>
   <!--底部 结束-->

</body>
</html>

