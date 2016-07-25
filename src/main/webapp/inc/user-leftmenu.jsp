<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
//使对应的菜单处于选中状态
function activeUserLeftMenu(left_mnu_id){
	$("#"+left_mnu_id).addClass("active");
}
//去除选中状态
function inActiveUserLeftMenu(left_mnu_id){
	$("#"+left_mnu_id).removeClass("active");
}
</script>

<!--我的订单左侧-->
    <div class="my-order-menu">
        <!--帐户中心首页左侧添加  只有首页才有  暂时屏蔽-->
    	 <!-- <div class="edit-data">
    	 		<div class="edit-data-title">
    	 			<ul>
    	 				<li class=""><a href="#">编辑资料</a></li>
    	 				<li class="word">
    	 					<p class="small-word">下午好</p>
    	 					<p>熊二爷</p>
    	 				</li>
    	 			</ul>
    	 		</div>	
    	 		<div class="edit-data-list">
    	 			<ul>
    	 				<li>
    	 					<p>注册会员</p>
    	 					<p><img src="images/state-7.png"></p>
    	 				</li>
    	 				<li>
    	 					<p>安全等级:</p>
    	 					<p>中</p>
    	 					<p class="ash"><a href="#"><i class="icon-envelope-alt"></i></a><div class="onclick-email" style="display: none;">邮箱未绑定</div></p>
    	 					<p class="ash"><a href="#" class="current"><i class="icon-tablet"></i></a><div class="onclick" style="display: none;">手机已绑定</div></p>
    	 				</li>
    	 			</ul>
    	 		</div>
    	 	</div> -->
    		<!--/帐户中心首页左侧添加-->
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn1.png"></li>
            <li class="word">
                <p class="">订单中心</p>
                <p id="left_mnu_order_myorder" ><A href="${_base}/myorder/list">我的订单</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn2.png"></li>
            <li class="word">
                <p class="">资产中心</p>
                <p id="left_mnu_account_balance" ><A href="${_base}/account/balance/index">账户余额</A></p>
                <p><A href="javascript:void(0);">我的卡包</A></p>
            </li>
        </ul>
        <%-- <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn3.png"></li>
            <li class="word">
                <p class="">关注中心</p>
                <p><A href="#">收藏夹</A></p>
                <p><A href="#">我的足迹</A></p>
                <p><A href="#">新消息（0）</A></p>
            </li>
        </ul> --%>
        <c:if test="${sessionScope.user_session_key.userType=='11' || sessionScope.user_session_key.userType=='12' || sessionScope.user_session_key.userType=='13' }">
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn4.png"></li>
             <li class="word">
                <p class="">资质管理</p>
                 <c:choose>
	    			<c:when test="${sessionScope.user_session_key.userType=='11'}">
                	<p id="left_mnu_qualification"><A href="${_base}/user/qualification/toEnterprisePage">资质认证</A></p>
                 	</c:when>
	    			<c:when test="${sessionScope.user_session_key.userType=='12'}">
                	<p id="left_mnu_qualification"><A href="${_base}/user/qualification/toAgentSelectPage">资质认证</A></p>
                 	</c:when>
                 	<c:when test="${sessionScope.user_session_key.userType=='13'}">
                	<p id="left_mnu_qualification"><A href="${_base}/user/qualification/toSupplierPage">资质认证</A></p>
                 	</c:when>
	    			<c:otherwise>
                	<p id="left_mnu_qualification"><A href="javascript:void(0);">资质认证</A></p>
                 	</c:otherwise>
                 </c:choose>
            </li> 
        </ul>
        </c:if>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn5.png"></li>
            <li class="word">
                <p class="">账户设置</p>
                <%-- <c:if test="${sessionScope.user_session_key.userType=='10' }">
                  <p id="left_mnu_person_info"><A href="#">个人资料</A></p>
                </c:if> --%>
                <p id="left_mnu_phonebook"><A href="${_base}/account/phonebook/phonebookmgr">通讯录</A></p>
                <c:if test="${sessionScope.user_session_key.userType=='11' || sessionScope.user_session_key.userType=='12' || sessionScope.user_session_key.userType=='13' }">
                <p id="left_mnu_myapi"><A href="#">我的API</A></p>
                </c:if>
                <p id="left_mnu_security_set"><A href="${_base}/user/security/securitySettings" >安全设置</A></p>
            </li>
        </ul>
    </div>
 <!--／我的订单左侧结束-->  