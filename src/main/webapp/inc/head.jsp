<%@page import="com.ai.baas.op.web.constants.BaaSOPConstants"%>
<%@page import="com.ai.opt.sso.client.filter.SSOClientUtil"%>
<%@ page import="com.ai.opt.sdk.components.ccs.CCSClientFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String uacBase=SSOClientUtil.getCasServerUrlPrefixRuntime(request);
	request.setAttribute("_uacBase", uacBase);
	
	String baas_pt_index_url = CCSClientFactory.getDefaultConfigClient().get(BaaSOPConstants.URLConstant.BAAS_PT_INDEX_URL_KEY);
	request.setAttribute("baas_pt_index_url",baas_pt_index_url );
%>
<script type="text/javascript">
function setBreadCrumb(_first_breadcrumb_title,_second_breadcrumb_title){
	$("#first_breadcrumb_title").html(_first_breadcrumb_title);
	$("#second_breadcrumb_title").html(_second_breadcrumb_title);
}
</script>
<div class="header">
   <div class="logo">
    <a href="${baas_pt_index_url }/login">LOGO</a>
    <!-- <i class="icon-angle-down"></i>  -->
   </div>
   <div class="subnav">
   <div class="nav">
      <ul>
      <li id="mnu_bmc_config">
	      	<a href="javascript:void(0);">计费配置管理</a>
	      	<div class="current-cnt" style="display:none">
	      		<a href="${_base}/standardFee/list">标准资费管理</a>
	      		<a href="${_base}/salableProduct/list">可销售产品管理</a>
	      		<a href="${_base}/billDiscount/list">账单优惠管理</a>
	      		<a href="${_base}/preferentialProduct/list">优惠产品管理</a>
	      		<a href="${_base}/drSubject/list">详单科目管理</a>
	      		<a href="${_base}/billSubject/list">账单科目管理</a>
	      	</div>
	  </li>
	  <li id="mnu_smc_config">
	      	<a href="javascript:void(0);">结算配置管理</a>
	      	<div class="current-cnt" style="display:none">
		         <a href="${_base}/account/list">结算查询</a>
		         <a href="${_base}/account/upload">结算文件上传</a>
	       	</div>
	  </li>
	  <li id="mnu_amc_config">
	      	<a href="javascript:void(0);">账单配置管理</a>
	      	<div class="current-cnt" style="display:none">
	      	</div>
	  </li>
      <li  id="mnu_omc_config">
	      	<a href="javascript:void(0);">信控配置管理</a>
	      	<div class="current-cnt" style="display:none">
		         <a href="${_base}/creditControl/standard/list">信控规则管理</a>
	       	</div>
      </li>
      <li id="mnu_query_mng">
	      	<a href="javascript:void(0);">查询管理</a>
	      	<div class="current-cnt" style="display:none">
		         <a href="${_base}/search/bill/list">账单查询</a>
		         <a href="${_base}/arrearage/toQueryArrearage">欠费查询</a>
	       		 <a href="${_base}/recharge/toQueryCharge">充值缴费查询</a>
	       		 <a href="${_base}/balance/toQueryBalance">账户余额查询</a>
	       		 <a href="${_base}/billdetail/toList">详单查询</a>
	       	</div>
      </li>
     </ul>
     </div>
    </div>
   <div class="breadcrumb">
    <ul>
     <!-- 面包屑一级导航 -->
     <li><span id="first_breadcrumb_title"></span></li>
     <!-- 面包屑二级导航 -->
     <li><i class="icon-angle-right"></i><span id="second_breadcrumb_title"></span></li>
    </ul>
   </div>
   <div class="user">
    <div class="user-cnt">
     <c:choose>
	    <c:when test="${sessionScope.user_session_key.shortNickName==''}">
	      <p><img src="${_base}/resources/baasop/images/login_user.png"><span>${sessionScope.user_session_key.nickName}</span><i class="icon-angle-down"></i></p>
	    </c:when>
	    <c:when test="${sessionScope.user_session_key.shortNickName==null}">
	        <p><img src="${_base}/resources/baasop/images/login_user.png"><span>${sessionScope.user_session_key.nickName}</span><i class="icon-angle-down"></i></p>
	    </c:when>
	    <c:otherwise>
	         <p><img src="${_base}/resources/baasop/images/login_user.png"><span>${sessionScope.user_session_key.shortNickName}</span><i class="icon-angle-down"></i></p>
	    </c:otherwise>
	</c:choose>
     
     <ul style="display:none;">
      <li><a href="${_uacBase}/center/baseInfo/getAccountInfo">个人中心</a></li>
      <li><a href="${_uacBase}/center/password/confirminfo">修改密码</a></li>
      <li><a href="${_base}/ssologout">退出</a></li>
     </ul>
    </div>
   </div>
</div>

<div class="navbg"></div>

