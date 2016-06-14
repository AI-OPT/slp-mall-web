<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=uft-8">
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<title>账户中心</title>
</head>

<script type="text/javascript">


	function showClass(){
		$('.active').removeClass('active');
   		$("#left_mnu_security_set").addClass("active");
	}

	function goToBandEmail(str){
		if(str=="bandEmail"){
		    window.location.href = _base+"/user/bandEmail/bandEmailStart";
		}else{
			window.location.href = _base+"/user/bandEmail/setEmail";
		}
	}
	function toChangePassword(){
		window.location.href = _base+"/user/password/toChangePassword";
	}
	
	function toChangePayPassword(){
		window.location.href = _base+"/user/payPassword/updatePayPassword";
	}
	
	function toChangePhone(){
		window.location.href = _base+"/user/phone/toChangePhone";
	}
</script>

<body onload="showClass()">
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
          <p><a href="#">账户设置</a>&gt;</p>
          <p><a href="#">账户安全</a></p>
      </div>
      <div class="order-list-bj">
        	<!--账户安全企业-->
        	<div class="account-security-title">
        		<!-- <ul>
        			<li>您的账户安全级别为:</li>
        			<li class="word-color">中</li>
        			<li class="bj-color">
        				<p></p>	
        			</li>
        			<li class="label">建议您启动以下安全设置，保障您的账户和资金安全</li>
        		</ul> -->	
        	</div>
        		<div class="account-security-list">
        			<div class="account-security-list-table">
        				<ul>
        					<li style="width:20%;">安全项</li>
        					<li style="width:20%;">状态</li>
        					<li style="width:40%;">安全建议</li>
        					<li style="width:20%;">操作</li>                      
        				</ul>
        			</div>
        			<!--账户安全list-->
        			<div class="state-list">
        				 <div class="state-list-icon">
        				 	<p><img src="${_slpbase }/images/state-1.png"></p>
        				 	<p>登录密码</p>
        				 </div>
        				<div class="state-list-strength">
        					<ul>
        						<li class="word">
        						<%--   <c:if test="${userInfo.pwdSafetyLevel=='10'}">
        							  <p><img src="${_slpbase }/images/pass-a.png"></p>
        							  <p>密码强度低</p>
        						  </c:if> --%>
        						  <c:if test="${userInfo.pwdSafetyLevel=='11'}">
        							  <p><img src="${_slpbase }/images/pass-b.png"></p>
        							  <p>密码强度中</p>
        						  </c:if>
        						  <p><img src="${_slpbase }/images/pass-b.png"></p>
        						  <p>密码强度中</p>
        						  <c:if test="${userInfo.pwdSafetyLevel=='10'}">
        							  <p><img src="${_slpbase }/images/pass-a.png"></p>
        							  <p>密码强度强</p>
        						  </c:if>
        						</li>
        						<li class="bj-color">
        						<p style="background: #f17a38 none repeat scroll 0 0;height: 6px;width: 50%;"></p>
        						</li>
        					</ul>
        				</div>
        				<div class="state-list-word">建议修改密码，使用大小写字母等特殊字符的组合</div>
        				<div class="state-list-btn"><input type="button" class="sta-btn" value="修改" onclick="toChangePassword();"></div>
        			</div>	
        			<div class="state-list">
        				 <div class="state-list-icon">
        				 	<p><img src="${_slpbase }/images/state-2.png"></p>
        				 	<p>手机绑定</p>
        				 </div>
        				<div class="state-list-strength">
        					<ul>
        						<li class="word">
        							<c:if test="${userInfo.userMp==null}">
        								<p><img src="${_slpbase }/images/icon-c.png"></p>
        								<p>未绑定</p>
        							</c:if>
        							<c:if test="${userInfo.userMp!=null}">
        								<p><img src="${_slpbase }/images/icon-b.png"></p>
        								<p>已绑定</p>
        							</c:if> 
        						</li>
        						<li>
        							<c:if test="${userInfo.userMp!=''}">
        								<p>${userInfo.userMp}</p>
        							</c:if>
        						</li>
        					</ul>
        				</div>
        				<div class="state-list-word">绑定您常用的手机号，确保能及时收到短信提醒</div>
        				<c:if test="${userInfo.userMp==null||userInfo.userMp==''}">
        				 <div class="state-list-btn"><input type="button" class="sta-btn" value="绑定手机"></div>
        				</c:if>
        				<c:if test="${userInfo.userMp!=null}">
        				 <div class="state-list-btn"><input type="button" class="sta-btn" onclick="toChangePhone();" value="修改"></div>
        				</c:if>
        			</div>
        			<div class="state-list">
        				 <div class="state-list-icon">
        				 	<p><img src="${_slpbase }/images/state-3.png"></p>
        				 	<p>邮箱绑定</p>
        				 </div>
        				<div class="state-list-strength">
        					<ul>
        						<li class="word word-margin">
        						 <c:if test="${userInfo.userEmail==null|| userInfo.userEmail==''}">
        							<p><img src="${_slpbase }/images/icon-c.png"></p>
        							<p>未绑定</p>
        						</c:if>
        						 <c:if test="${userInfo.userEmail!=null&&userInfo.emailValidateFlag=='10'}">
        							<p><img src="${_slpbase }/images/icon-b.png"></p>
        							<p>已验证</p>
        						</c:if>
        						 <c:if test="${userInfo.userEmail!=null&&userInfo.emailValidateFlag=='11'}">
        							<p><img src="${_slpbase }/images/icon-b.png"></p>
        							<p>未验证</p>
        						</c:if>
        						</li>
        						<li>
        							<c:if test="${userInfo.userEmail!=''}">
        								<p>${userInfo.userEmail}</p>
        							</c:if>
        						</li>
        					</ul>
        				</div>
        				<div class="state-list-word">绑定邮箱后，可用于密码重置及接受消息提醒</div>
        				<c:if test="${userInfo.userEmail==null||userInfo.userEmail==''}">
        					<div class="state-list-btn"><input type="button" class="sta-btn" value="绑定邮箱" onclick="goToBandEmail('bandEmail')"></div>
        				</c:if>
        				<c:if test="${userInfo.userEmail!=null&&userInfo.emailValidateFlag=='10'}">
        					<div class="state-list-btn"><input type="button" class="sta-btn" value="修改" onclick="goToBandEmail('updateEmail')"></div>
        				</c:if>
        				<c:if test="${userInfo.userEmail!=null&&userInfo.emailValidateFlag=='11'}">
        					<div class="state-list-btn"><input type="button" class="sta-btn" value="验证" onclick="goToBandEmail('bandEmail')"></div>
        				</c:if>
        			</div>
        			<div class="state-list">
        				 <div class="state-list-icon">
        				 	<p><img src="${_slpbase }/images/state-4.png"></p>
        				 	<p>支付密码</p>
        				 </div>
        				 <c:if test="${payCheckFlag=='0'}">
        				 	<div class="state-list-strength">
	        					<ul>
	        						<li class="word">
	        							<p><img src="${_slpbase }/images/icon-c.png"></p>
        								<p>未设置</p>
	        						</li>
	        					</ul>
        					</div>
        				 <div class="state-list-word">您还未启用支付密码，为保障您的账户资金，请完成设置。</div>
        				 </c:if>
        				 <c:if test="${payCheckFlag=='1'}">
        				  	<div class="state-list-strength">
	        					<%-- <ul>
	        						<li class="word">
	        							<p><img src="${_slpbase}/images/pass-c.png"></p>
	        							<p>密码强度高</p>
	        						</li>
	        						<li class="bj-color">
	        							<p class="green"></p>
	        						</li>
	        					</ul> --%>
	        					<ul>
	        						<li class="word">
	        							<p><img src="${_slpbase }/images/icon-b.png"></p>
        								<p>已设置</p>
	        						</li>
	        					</ul>
        					</div>
        				 <div class="state-list-word">建议您定期更改密码以保障账户资金安全。</div>
        				 </c:if>
        				 
        				<c:if test="${payCheckFlag=='0'}">
        					<div class="state-list-btn"><input type="button" class="sta-btn" id="updatePayPassword" value="设置密码" onclick="toChangePayPassword()"></div>
        				</c:if>
        				<c:if test="${payCheckFlag=='1'}">
        					<div class="state-list-btn"><input type="button" class="sta-btn" id="updatePayPassword" value="修改" onclick="toChangePayPassword()"></div>
        				</c:if>
        				
        			</div>
        		</div>
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
<script src="${_slpbase }/scripts/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
