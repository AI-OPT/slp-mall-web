<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>修改手机</title>
<script type="text/javascript" src="${_slpbase }/scripts/md5.js"></script>
<script src="${_slpbase }/scripts/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	(function() {
		seajs.use('app/jsp/user/changePhone/change-phone', function(
				ChangePhonePager) {
			var pager = new ChangePhonePager();
			pager.render();
		});
	})();
	$(function(){
		var phone=${phone};
		if(phone==''){
			phone='未绑定手机号';
		}
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
          <p>修改手机</p>
      </div>
      <div class="account-bj">
    
        <!--步骤-->
          <div class="steps">
               <ul>
                <li class="yellow-border" id="changePhoneBorder1"></li>
                <li class="yellow-yuan" id="changePhoneYuan1">1</li>
                <li class="yellow-word" id="changePhoneWord1">验证身份</li>
               </ul>
               <ul>
                <li class="ash-border" id="changePhoneBorder2"></li>
                <li class="ash-yuan" id="changePhoneYuan2">2</li>
                <li class="ash-word" id="changePhoneWord2">验证新手机号</li>
               </ul>
                <ul>
                <li class="ash-border"id="changePhoneBorder3"></li>
                <li class="ash-yuan" id="changePhoneYuan3">3</li>
                <li class="ash-word" id="changePhoneWord3">完成修改</li>
               </ul>
           </div>                                          
          <!--/步骤结束-->
              <div class="center-main">
          		<div class="center-table-list">
               	<div class="list-int">
                    <ul>
                        <li class="word">绑定手机号:</li>
                        <li><input type="text" id="phone" class="int-medium" placeholder="" disabled="disabled" value="${phone }"></li>
                     <!--    <li class="lable"><img src="../images/icon-c.png"><span>请输入正确有效的手机号</span></li> -->
                    </ul>
                    <ul>
                        <li class="word">手机验证码:</li>
                        <li><input type="text" class="int-small"></li>
                        <li class="re-btn"><input type="button" class="int-btn" value="获取短信验证码" id="PHONE_IDENTIFY1"></li>
                        <li class="lable" style="display: none" id="phoneVerifyMsg"><img src="${_slpbase }/images/icon-e.png"><span class="red">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" value="下一步"></li>
                    </ul>
                	</div>
                 </div>
                 
                  <div class="list-int" style="display:none;" id="changePhone2">
                    <ul>
                        <li class="word">请输入新绑定手机号:</li>
                        <li><input type="text" class="int-medium" placeholder="" id="newPhone"></li>
                        <li class="lable" id="newPhoneErrMsg" style="display:none;"><img src="${_slpbase }/images/icon-c.png"><span id="newPhoneErrMsgShow">请输入正确有效的手机号</span></li>
                    </ul>
                    <ul>
                        <li class="word">手机验证码:</li>
                        <li><input type="text" class="int-small" id="phoneCode"></li>
                        <li class="re-btn"><input type="button" class="int-btn" id="PHONE_IDENTIFY" value="获取短信验证码"></li>
                        <li class="lable" id="phoneCodeErrMsg" style="display:none;"><img src="${_slpbase }/images/icon-e.png"><span class="red" id="phoneCodeErrMsgShow">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" id="SENDEMAIL" class="slp-btn regsiter-btn" value="发送验证邮件"></li>
                    </ul>
                </div>
                 
                 <div class="recharge-success" style="display:none" id="changePhone3">
                 <p><img src="${_slpbase }/images/succ.png"></p>
                 <p class="word">您已经成功绑定新的手机号:</p>
                 <p class="success-box"><a href="#">查看个人资料</a><a href="#">查看账户安全</a><a href="#">账户中心</a></p>
              </div>
             	<ul>
					<li class="checx-word">
					<input type="hidden" id="phoneFlag"/>
					<input type="hidden" id="phoneCodeFlag"/>
		         	<input type="hidden" id="tenantId" value="0"/>
		         	</li>
				</ul>
           </div>
           </div>
           </div>
           </div>
           </div>
           </div>
   <!--底部-->
    <%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
</html>
