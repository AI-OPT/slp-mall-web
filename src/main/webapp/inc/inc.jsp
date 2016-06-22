<%@page import="java.util.Date"%>
<%@page import="com.ai.opt.sso.client.filter.SSOClientUtil"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String _base = request.getContextPath();
    request.setAttribute("_base", _base);
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setHeader("Pragma", "No-cache");
    
    String _slpbase=_base+"/resources/slpmall";
    request.setAttribute("_slpbase", _slpbase);
    
    String accountBalanceLink=_base+"/account/balance/index";
    request.setAttribute("accountBalanceLink", accountBalanceLink);

    String accountRechargeOneLink=_base+"/account/recharge/one";
    request.setAttribute("accountRechargeOneLink", accountRechargeOneLink);
    
    String slp_uac_host=SSOClientUtil.getCasServerUrlPrefixRuntime(request);
    request.setAttribute("slp_uac_host", slp_uac_host);
    String ssoLoginUrl=SSOClientUtil.getCasServerLoginUrlRuntime(request);
    request.setAttribute("ssoLoginUrl", ssoLoginUrl);
%>
<script>
    var _base = "${_base}";
    var _slpbase = "${_slpbase}";
    var slp_uac_host="${slp_uac_host}";
    var ssoLoginUrl="${ssoLoginUrl}";
</script>

<script src="${_base}/resources/spm_modules/jquery/1.9.1/jquery.js"></script>
<script src="${_base}/resources/spm_modules/bootstrap/dist/js/bootstrap.js"></script>
<script src="${_base}/resources/spm_modules/seajs/2.3.0/dist/sea.js"></script>
<script src="${_base}/resources/spm_modules/seajs/seajs-css.js"></script>
<script src="${_base}/resources/spm_modules/app/core/config.js"></script>
<!-- 公共事件 -->
<script src="${_base}/resources/spm_modules/app/util/common.js" type="text/javascript"></script>


<!-- <link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css"> -->

<img id="img_logincheck" style="display:none;" src="${_base}/logincheck?req=<%=new Date().getTime() %>">

<%-- <link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/font-awesome.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/frame.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/global.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/modular.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/index.css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/banner.css"> --%>

<!-- slp-mall -->
