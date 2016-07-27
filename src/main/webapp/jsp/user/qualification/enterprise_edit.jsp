<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>资质申请－企业</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">

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
 <!--资质申请右侧-->  
 <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资质认证</a></p>
      </div>
      <div class="account-bj">
      <div class="mar-account-title">
      <div class="account-title account-title-bjcolor">
      	<div class="title-bt">
			企业资质<span id=auditState style="display:none">(未认证)</span>
		</div>
      </div>
      </div>
     <!--标题-->  
     <div class="account-title">
     	<p>营业执照信息</p>
      	<p class="right"><i class="icon-edit qualifications" id="editEnterprise">修改</i></p>
     </div>
     <!--信息填写-->
     <div class="nav-form" id="qf-browse">
         <ul>
             <li>
                <p class="word">企业名称:</p>
                <p>${groupKeyInfo.custName}</p>
             </li>
         </ul>
       	  <ul>
             <li>
                <p class="word">企业注册地址:<p>
             	<p>${groupKeyInfo.provinceCode}</p>
             </li> 
         </ul>
   		  <ul>
             <li>
                <p class="word">营业执照注册号:</p>
                <p>${groupKeyInfo.certNum}</p> 
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">营业执照副本:</p>
                <p class="img"><img src="${imageMap['12001']}"></p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">行业:</p>
                <p>${groupKeyInfo.groupIndustry}</p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">官网:</p>
                <p>${groupKeyInfo.groupWebsite}</p>  
             </li>
         </ul>
          <ul>
             <li>
                <p class="word">公司人数:</p>
                <p>${groupKeyInfo.groupMemberScale}</p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">公司性质:</p>
                <p>${groupKeyInfo.groupType}</p>
             </li>
         </ul>
     </div>
         <!--信息填写-->
          <!--提示风险-->
       <div  id="qf-edit" style="display:none;">   
        <div class="prompt-risk small-risk mt-0">
            <p>您的资质信息修改后需人工审核才能生效，审核时间为3个工作日，确定要修改资质信息吗？</p>
            <p class="img"><img src="${_slpbase}/images/yue-1.png" style="margin-top: 10px"></p>
        </div>
        <form:form id="enterprise" method="post">
     	<div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>企业名称:</p>
                <p><input type="text" class="int-xlarge" placeholder="请填写营业执照上的注册企业名称" id="custName" name="custName" value="${groupKeyInfo.custName}"></p>
                 <label id="custNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="custNameImage"><span class="ash" id="enterpriseErrMsgShow">4-60个字符，可用中英文、数字、“-”、”_”、“（）”及”( )”</span></label>
             </li>
         </ul>
       	  <ul>
            <li>
                <p class="word"><b class="red">*</b>企业注册地址:</p>
                <p>
                
                <select class="select-xmini" id="provinceCode" name="provinceCode" >
                	<option value="0">请选择</option>
                	<c:forEach items="${provinceList}" var="record">
                		<option value="${record.provinceCode}">${record.areaName}</option>
                	</c:forEach>
                </select>
                
                </p>
                <p>
                 <select class="select-xmini" id="cityCode" name="cityCode">
                	<c:forEach items="${cityList}" var="record">
                		<option value="${record.cityCode}">${record.areaName}</option>
                	</c:forEach>
                 </select>
                </p>
                <p><select class="select-xmini" id="countyCode" name="countyCode">
                                	<c:forEach items="${countyList}" var="record">
                		<option value="${record.areaCode}">${record.areaName}</option>
                	</c:forEach>
                	</select></p>
                <label id="registerAddrErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="registerAddrImage"><span class="ash" id="registerAddrText">请选择注册地址</span></label>
             </li>
             <li class="right">
             <p><input type="text" class="int-xlarge" placeholder="详细街道地址" id="certAddr" name="certAddr" value="${codeMap.certAddr }"></p>
             <label id="certAddrErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="certAddrImage"><span class="ash" id="certAddrText">5-120个字符</span></label>
             </li>
         </ul>
   		  <ul>
             <li>
                <p class="word"><b class="red">*</b>营业执照注册号:</p>
                <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号" id="certNum" name="certNum" value="${groupKeyInfo.certNum}"></p>
                 <label id="certNumErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="certNumImage"><span class="ash" id="certNumText">最多20个字符，允许使用英语字母（区分大小写）、数字及“-”</span></label>
             </li>
         </ul>
         <ul>
              <li>
                <p class="word"><b class="red">*</b>营业执照副本:</p>
                <input type="hidden" value="营业执照副本" name="list[0].infoName">
                <input type="hidden" value="12" name="list[0].infoType">
                <input type="hidden" value="12001" name="list[0].infoItem">
                <p class="img"><img src="${imageMap['12001']}" id="certPic"></p>
                <p class="small-p">
               		<span><input type="file" id="image1" name="image1" class="file" style="display: " onchange="uploadImg('image1','certPic','idpsId','imgErrShow');">
               		<input type="button" value="点击上传" type="file" class="file-btn"><a href="javascript:" onclick="deleteImg('image1','certPic','idpsId','imgErrShow');">删除</a></span>
                	<span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                	<span id="imgErrShow"></span>
                </p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>行业:</p>
                <p>
	                <select class="select-medium" id="groupIndustry" name="groupIndustry">
	                	<option value="0">请选择</option>
	                	<c:forEach items="${industryMap}" var="map">
	                		<option value="${map.key }">${map.value }</option>
	                	</c:forEach>
	                </select>
                </p>
                <label id="groupIndustryErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupIndustryImage"><span class="ash" id="groupIndustryText">请选择行业信息</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">官网:</p>
                <p><input type="text" class="int-medium" placeholder="请填写官网网址" id="groupWebsite" name="groupWebsite" value="${groupKeyInfo.groupWebsite}"></p>
                 <label id="groupWebsitErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="groupWebsiteImage"><span class="ash" id="groupWebsiteText">3-60个字符，允许使用字母、数字、特殊字符</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>公司人数:</p>
                <p>
                	<select class="select-medium" id="groupMemberScale" name="groupMemberScale">
                		<option value="0" selected="selected">请选择</option>
                		<c:forEach var="map" items="${groupMemberMap}">
	                		<option value="${map.key}">${map.value}</option>
                		</c:forEach>
                	</select>
                </p>
                 <label id="groupMemberScaleErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupMemberScaleImage"><span class="ash" id="groupMemberScaleText">请选择公司人数信息</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>公司性质:</p>
                <p>
                <select class="select-medium" id="groupType" name="groupType">
                	<option value="0" selected="selected">请选择</option>
               		<c:forEach var="map" items="${groupTypeMap}">
	                	<option value="${map.key}">${map.value}</option>
                	</c:forEach>
                </select>
                </p>
                 <label id="groupTypeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupTypeImage"><span class="ash" id="groupTypeText">请选择公司性质信息</span></label>
             </li>
         </ul>
         <ul>
              <li class="form-btn" id="qf-btn"><input type="button" class="slp-btn regsiter-btn" value="保存资质" id="updateEnterprise"></li>
         </ul>
     </div>
     </div>
    <!--标题-->  
     <div class="account-title account-title-bjcolor">
     	<p>联系人信息</p>
     	<p class="right"><i class="icon-edit contacts" id="editContactInfo">修改</i></p>
     </div>
     <!--信息填写-->
     <div class="nav-form" id="ct-browse">
           <ul>
                <li>
                    <p class="word">联系人姓名:</p>
                    <p>${contactsInfo.contactName }</p>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word">所属部门:</p>
                    <p>${contactsInfo.contactDept}</p>
                 </li>
             </ul>
             <ul>
                 <li>
                    <p class="word">联系人邮箱:</p>
                    <p>${contactsInfo.contactEmail}</p>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word">联系人手机:</p>
                    <p>${contactsInfo.contactMp}</p>
                 </li>
             </ul>
             
     </div>
     <div class="nav-form" id="ct-edit" style="display:none;">
           <ul>
               <li>
                    <p class="word"><b class="red">*</b>联系人姓名:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写联系人姓名" id="contactName" name="contactName" value="${contactsInfo.contactName }"></p>
                     <label id="contactNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-d.png" id="contactNameImage"><span class="ash" id="contactNameText">2-24个字符，可用汉字或英语字母</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>所属部门:</p>
                    <p>
                    <select class="select-medium" id="contactDept" name="contactDept">
                    	<option value="0" selected="selected">请选择</option>
	               		<c:forEach var="map" items="${contactDeptMap}">
	                	    <option value="${map.key}">${map.value}</option>
                	    </c:forEach>
                    </select>
                    </p>
                    <label style="display:none" id="contactDeptErrMsg"><img src="${_slpbase}/images/icon-a.png"><span class="ash">请选择所在部门信息</span></label>
                 </li>
             </ul>
             <ul>
                 <li>
                    <p class="word">联系人邮箱:</p>
                    <p><input type="text" class="int-medium" placeholder="" id="contactEmail" name="contactEmail" value="${contactsInfo.contactEmail}"></p>
                    <label style="display:none" id="emailMsgError"><img src="${_slpbase}/images/icon-a.png" id="contactEmailMsgImage"><span id="contactEmailText" class="ash">请输入正确的邮箱地址</span></label>
                 </li>
             </ul>
              <ul>
                <li>
                    <p class="word"><b class="red">*</b>联系人手机:</p>
                    <p><input type="text" class="int-medium" placeholder="" id="contactMp" name="contactMp" value="${contactsInfo.contactMp}"></p>
                    <label style="display:none" id="contactMpErrMsg"><img src="${_slpbase}/images/icon-a.png" id="contactMpImage"><span id="contactMpText" class="ash">请填写正确手机号</span></label>
                 </li>
             </ul>
               <ul>
                  <li>
                    <p class="word"><b class="red">*</b>短信验证码:</p>
                    <p><input type="text" class="int-mini" id="phoneCode" name="phoneCode"></p>
                    <p><input type="button" class="int-btn" value="获取短信验证码" id="sendPhoneCode"></p>
                    <label id="phoneCodeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="phoneCodeImage"><span  id="phoneCodeText" class="ash">验证码错误</span></label>
                 </li>
             </ul>
              <ul>
                 <li class="form-btn" id="ct-btn"><input type="button" class="slp-btn regsiter-btn" value="保存联系人" id="updateContactsEnterprise">
                  <input type="hidden" id="idpsId" name="list[0].attrValue" value="${codeMap.idpsIdList[0] }">
                  <input type="hidden" id="custNameFlag">
            	  <input type="hidden" id="provinceCode">
            	  <input type="hidden" id="certAddrFlag">
            	  <input type="hidden" id="certNumFlag">
            	  <input type="hidden" id="groupTypeFlag">
            	  <input type="hidden" id="groupMemberScaleFlag">
            	  <input type="hidden" id="contactDeptFlag">
            	  <input type="hidden" id="provinceCodeFlag">
            	  <input type="hidden" id="contactMpFlag">
            	  <input type="hidden" id="phoneCodeFlag">
            	  <input type="hidden" id="groupIndustryFlag">
            	  <input type="hidden" id="picFlag">
            	  <input type="hidden" id="contactNameFlag">
                  </li>
             </ul>
     </div>
      </div>
      </form:form>
  </div>  
     
     
    </div>
 </div>
  <!--底部-->
   <%@ include file="/inc/foot.jsp" %>
  <!--底部 结束-->
</body>
</html>
<script type="text/javascript">
var baseInfoPager;
var qualificationSubmitPager;
var provinceCode = "${codeMap.provinceCode}";
var cityCode = "${codeMap.cityCode}";
var countyCode = "${codeMap.countyCode}";

var auditState = "${groupKeyInfo.auditState}";
(function() { 
	seajs.use([ 'app/jsp/user/qualification/baseinfo','app/jsp/user/qualification/qualificationSubmit'], function(BaseInfoQualificationPager,QualificationSubmitPager) {
		baseInfoPager = new BaseInfoQualificationPager({
			element : document.body
		});
		qualificationSubmitPager = new QualificationSubmitPager({
			element : document.body
		});
		baseInfoPager.render();
		qualificationSubmitPager.render();
	});
})(); 
$(function(){
	if(${groupKeyInfo.auditState}=='10'){
		$("#auditState").show();
	}
	if(${groupKeyInfo.auditState}=='11'){
		$("#auditState").html("(已验证)");
		$("#auditState").show();
	}
	if(${groupKeyInfo.auditState}=='12'){
		$("#auditState").html("(审核失败)");
		$("#auditState").show();
	}
	$("#groupIndustry").val("${codeMap.groupIndustry}");
	$("#groupMemberScale").val("${codeMap.groupMemberScale}");
	$("#groupType").val("${codeMap.groupType}");
	$("#contactDept").val("${codeMap.contactDept}");
	$("#certAddr").val("${codeMap.certAddr}");
})
</script>