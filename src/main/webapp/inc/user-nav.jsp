<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--导航区域-->
<div class="fsast-bj">
 <div class="fsast-head">
        <div class="fsast-logo">
            <ul>
                <li><a href="${_base}"><img src="${_slpbase }/images/hnlogo.png"></a></li>
                <li>账户中心</li>
            </ul>
        </div>
        <div class="fsast-bav">
            <ul>
                <li><a href="${_base}/myorder/list">账户首页</a></li>
                <li><a href="${_base}/myorder/list">我的订单</a></li>
                <li class="shez"><a href="#">账户设置<i class="icon-angle-down"></i></a>
                <div class="setgs" style="display:none;">
                    <ul>
                        <c:if test="${sessionScope.user_session_key.userType=='11' || sessionScope.user_session_key.userType=='12' || sessionScope.user_session_key.userType=='13' }">
                        	<c:choose>
				    			<c:when test="${sessionScope.user_session_key.userType=='11'}">
			                	<li id="left_mnu_qualification_identify"><A href="${_base}/user/qualification/toEnterprisePage">资质认证</A></li>
			                 	</c:when>
				    			<c:when test="${sessionScope.user_session_key.userType=='12'}">
			                	<li id="left_mnu_qualification_identify"><A href="${_base}/user/qualification/toAgentSelectPage">资质认证</A></li>
			                 	</c:when>
			                 	<c:when test="${sessionScope.user_session_key.userType=='13'}">
			                	<li id="left_mnu_qualification_identify"><A href="${_base}/user/qualification/toSupplierPage">资质认证</A></li>
			                 	</c:when>
				    			<c:otherwise>
			                	<li id="left_mnu_qualification_identify"><A href="javascript:void(0);">资质认证</A></li>
			                 	</c:otherwise>
			                </c:choose>
                        </c:if>
                        <li><a href="${_base}/user/security/securitySettings">安全设置</a></li>
                        <li><a href="${_base}/user/password/toChangePassword">登录密码</a></li>
                        <li><a href="${_base}/user/phone/toChangePhone">手机绑定</a></li>
                        <li><a href="${_base}/user/bandEmail/getBandEmailView">邮箱绑定</a></li>
                        <li><a href="${_base}/user/payPassword/updatePayPassword">支付密码</a></li>
                    </ul>
                </div>
                </li>
                <li><a href="#">消息</a><p class="icon">4</p></li>                          
            </ul>
        </div>
        <div class="fsast-search">
             <ul>
                  <li><input type="text" class="fsast-xlarge" id="serachName" onkeypress="if (event.keyCode == 13) index_search_pager._searchBtnClick();"></li>
                  <li><A href="javascript:void(0);" id="BTN_SEARCH"><i class="icon-search"></i></A></li>
             </ul> 
        </div>
    </div>
 </div>
 <script type="text/javascript">
			var index_search_pager;
			(function () {
				seajs.use('app/jsp/search/search', function (SearchPager) {
					index_search_pager = new SearchPager({element: document.body});
					index_search_pager.render();
				});
			})();
			
</script>
<!--导航区域结束-->