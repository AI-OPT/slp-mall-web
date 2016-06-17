<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--导航区域-->
<div class="fsast-bj">
 <div class="fsast-head">
        <div class="fsast-logo">
            <ul>
                <li><a href="${_base}"><img src="${_slpbase }/images/login-logo.png"></a></li>
                <li>账户中心</li>
            </ul>
        </div>
        <div class="fsast-bav">
            <ul>
                <li><a href="${_base}/myorder/list">首页</a></li>
                <li><a href="${_base}/myorder/list">我的订单</a></li>
                <li class="shez"><a href="#">账户设置<i class="icon-angle-down"></i></a>
                <div class="setgs" style=" display:none;">
                    <ul>
                        <c:if test="${sessionScope.user_session_key.userType=='11' || sessionScope.user_session_key.userType=='12' || sessionScope.user_session_key.userType=='13' }">
                        	<c:choose>
				    			<c:when test="${sessionScope.user_session_key.userType=='11'}">
			                	<li id="left_mnu_qualification_identify"><A href="#">资质认证</A></li>
			                 	</c:when>
				    			<c:when test="${sessionScope.user_session_key.userType=='12'}">
			                	<li id="left_mnu_qualification_identify"><A href="#">资质认证</A></li>
			                 	</c:when>
			                 	<c:when test="${sessionScope.user_session_key.userType=='13'}">
			                	<li id="left_mnu_qualification_identify"><A href="#">资质认证</A></li>
			                 	</c:when>
				    			<c:otherwise>
			                	<li id="left_mnu_qualification_identify"><A href="javascript:void(0);">资质认证</A></li>
			                 	</c:otherwise>
			                </c:choose>
                        </c:if>
                        <li><a href="${_base}/user/security/securitySettings">安全设置</a></li>
                        <li><a href="#">登录密码</a></li>
                        <li><a href="#">支付密码</a></li>
                        <li><a href="#">手机绑定</a></li>
                        <li><a href="#">邮箱绑定</a></li>
                    </ul>
                </div>
                </li>
                <li><a href="#">消息</a><p class="icon">4</p></li>                          
            </ul>
        </div>
        <div class="fsast-search">
             <ul>
                  <li><input type="text" class="fsast-xlarge"></li>
                  <li><A href="#"><i class="icon-search"></i></A></li>
             </ul> 
        </div>
    </div>
 </div>
<!--导航区域结束-->