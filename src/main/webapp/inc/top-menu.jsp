<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js"></script>
 <!--顶部菜单-->
 <div class="top-menu">
     <div class="top-menu-main">
     <ul class="left">
         <li>所在地</li>
         <li class="city"><a href="javascript:void(0)" id="currentCity"><img src="${_slpbase}/images/open-a.png">
         
         </a>
         <!--选择所在地城市-->
                 <div class="city-hover" style="display:none;">
                      <!-- <ul class="title">
                          <li class="hot">热门城市</li>
                          <li><A href="#">北京</A></li>
                          <li><A href="#">上海</A></li>
                          <li><A href="#">广东</A></li>
                          <li><A href="#">浙江</A></li> 
                          <li><A href="#">江苏</A></li>                             
                      </ul> -->
                       <ul class="city-list" id="cityShowData">
                       <script id="cityTmpl" type="text/x-jsrender">
							<li><A href="javascript:void(0)" areaCodeId="{{:areaCode}}" areaNameId="{{:areaName}}"class="ATTS_BTN">{{:areaName}}</A></li>
					   </script>
                      </ul>
                  </div>
                  
         </li> 
       <!--选择所在地城市结束-->
         
         
     </ul>
     <ul class="right">
     	 <c:choose>
	         <c:when test="${empty sessionScope.user_session_key.userId }">
		         <li><A href="${slp_uac_host }/reg/toRegister?userType=10">免费注册</A></li>
		         <li><A href="${slp_uac_host}/login">登录</A>|</li>
	         </c:when>
	         <c:otherwise >
		         <li><A href="${_base}/myorder/list">${sessionScope.user_session_key.username }</A></li>
		         <li><A href="${_base}/ssologout">退出</A>|</li>
	         </c:otherwise>
         </c:choose>
         <li><A href="${_base}/shopcart/cartDetails"><i class="icon-shopping-cart"></i>购物车</A>|</li>
         <li><A href="${_base}/myorder/list">我的订单</A>|</li>
         <li class="use"><A href="${_base}/myorder/list">账户中心<img src="${_slpbase }/images/open-a.png"></A>|
             <!--账户展开-->
             <div class="use-hover" style=" display:none;">
                 <ul>
                     <li><A href="${_base}/myorder/list">我的订单</A></li>
                     <li><A href="${_base}/account/balance/index">账户余额</A></li>
                     <li><A href="javascript:void(0);">我的卡包</A></li>
                     <li>
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
                     </li>
                     <!-- <li><A href="#">收藏夹</A></li> -->
                     <li><A href="${_base}/account/phonebook/phonebookmgr">通讯录</A></li>
                     <li><A href="${_base}/user/security/securitySettings">安全设置</A></li>
                 </ul>
             </div>
             <!--账户展开结束-->
         </li>
         <li><A href="${slp_uac_host}/reg/toRegister?userType=11">企业采购</A>|</li>
         <li><A href="${slp_uac_host}/reg/toRegister?userType=12">代理商</A>|</li>
         <li><A href="${slp_uac_host}/reg/toRegister?userType=13">供货商</A>|</li>
         <li><A href="#">API</A>|</li>
         <li class="kefu"><A href="#">客户服务<img src="${_slpbase }/images/open-a.png"></A>
               <!--账户展开-->
             <div class="kefu-hover" style=" display:none;">
                 <ul>
                     <li><A href="#">帮助中心</A></li>
                     <li><A href="#">联系我们</A></li>
                     <li><A href="#">意见反馈</A></li>
                 </ul>
             </div>
             <!--账户展开结束-->
         </li>
     </ul>
     </div>
 
 </div>
<!--顶部菜单结束-->
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/top/top', function (TopPager) {
					pager = new TopPager({
						element: document.body
					});
					pager.render();
				});
			})();
			
</script>