<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>资质申请－代理商－企业</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css">
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
			企业代理资质<span id="auditState" style="display:none">(未认证)</span>
		</div>
      </div>
      </div>
     <!--标题-->  
     <div class="account-title">
     	<p>营业执照信息</p>
      	<p class="right"><i class="icon-edit qualifications" id="editEnterprise">修改</i></p>
     </div>
     <!--信息填写-->
     <div id="qf-browse">
     <div class="nav-form">
         <ul>
             <li>
                <p class="word">企业名称:</p>
                <p>${groupKeyInfo.custName }</p>
             </li>
         </ul>
       	  <ul>
             <li>
                <p class="word">企业注册地址:</p>
                <p>${groupKeyInfo.provinceCode}</p>
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
                <p class="word">成立日期:</p>
                <p>${groupKeyInfo.certIssueDate}</p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">注册资本:</p>
                <p>${groupKeyInfo.registeredCapitals}万元</p>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word">经营范围:</p>
                <p>${groupKeyInfo.groupBusinessScope}</p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">法人姓名:</p>
                <p>${groupKeyInfo.legalPerson}</p>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word">法人身份证号码:</p>
                <p>${groupKeyInfo.legalCertNum}</p>
             </li>
         </ul>
           <ul>
             <li>
                <p class="word">身份证复印件:</p>
                <p class="img"><img src="${imageMap['11004']}"></p>
             </li>
         </ul>
     </div>
     <!--标题-->  
     <div class="account-title"><p>税务登记证信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word">纳税人识别号:</p>
                    <p>${groupKeyInfo.taxpayerCode}</p>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">纳税人类型:</p>
                    <p>${groupKeyInfo.taxpayerType}</p>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">纳税类型税码:</p>
                    <p>${groupKeyInfo.taxpayerTypeCode}</p>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word">税务登记证:</p>
                <p class="img"><img src="${imageMap['13001']}"></p>
             </li>
         </ul>    
     </div>
     <!--标题-->  
     <div class="account-title"><p>组织机构代码证</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word">组织机构代码:</p>
                    <p>${groupKeyInfo.orgCode}</p>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word">代码证电子版:</p>
                <p class="img"><img src="${imageMap['14001']}"></p>
             </li>
         </ul>    
     </div>
    <!--标题-->  
     <div class="account-title"><p>银行开户许可证</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word">开户银行名称:</p>
                    <p>${bankInfo.bankName }</p>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">开户银行支行名称:</p>
                    <p>${bankInfo.subBranchName }</p>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">公司银行账户:</p>
                    <p>${bankInfo.acctNo }</p>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word">银行开户许可证:</p>
                <p class="img"><img src="${imageMap['15001']}"></p>
             </li>
         </ul>    
     </div>
      <!--标题-->  
     <div class="account-title"><p>企业介绍信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
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
     </div>
     <!--编辑-->
     <div  id="qf-edit" style="display:none;">
     	<div class="prompt-risk small-risk mt-0">
            <p>您的资质信息修改后需人工审核才能生效，审核时间为3个工作日，确定要修改资质信息吗？</p>
            <p class="img"><img src="${_slpbase}/images/yue-1.png" style="margin-top: 10px"></p>
        </div>
 
     <!--信息填写-->
     <form:form id="enterprise" method="post">
      <div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>企业名称:</p>
                <p><input type="text" class="int-xlarge" placeholder="请填写营业执照上的注册企业名称" id="custName" name="custName" value="${groupKeyInfo.custName }"></p>
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
                		<option value="${record.provinceCode}" <c:if test="${insertGroupKeyInfoRequest.provinceCode==record.provinceCode }">selected</c:if>>${record.areaName}</option>
                	</c:forEach>
                </select>
                
                </p>
                <p>
                 <select class="select-xmini" id="cityCode" name="cityCode">
                	<option value="0">请选择</option>
                	<c:forEach items="${cityList}" var="record">
                		<option value="${record.cityCode}">${record.areaName}</option>
                	</c:forEach>
                 </select>
                </p>
                <p>
                 <select class="select-xmini" id="countyCode" name="countyCode">
                 	<option value="0">请选择</option>
                 	<c:forEach items="${countyList}" var="record">
                		<option value="${record.areaCode}" <c:if test="${insertGroupKeyInfoRequest.provinceCode==record.provinceCode }">selected</c:if>>${record.areaName}</option>
                	</c:forEach>
                 </select>
                </p>
                <label id="registerAddrErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="registerAddrImage"><span class="ash" id="registerAddrText">请选择注册地址</span></label>
             </li>
             <li class="right">
            <p><input type="text" class="int-xlarge" placeholder="详细街道地址" id="certAddr" name="certAddr"></p>
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
                 <input type="hidden" name="list[0].infoName" value="营业执照副本"/>
                  <input type="hidden" value="12" name="list[0].infoType">
                <input type="hidden" value="12001" name="list[0].infoItem">
                <p class="img"><img src="${imageMap['12001']}" id="certPic1"></p>
                <p class="small-p">
                 <span>
                  <input type="button" value="点击上传" class="file-btn">
                  <input type="file" class="file" id="image1" name="image1" onchange="uploadImg('image1','certPic1','idpsId1','imgErrShow1');">
                  <a href="javascript:void(0)" onclick="deleteImg('image1','certPic1','idpsId1','imgErrShow1')">删除</a>
                  </span>
                 <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                 <span id="imgErrShow1"></span>
                </p>
             </li>
         </ul>
         <ul>
            <li>
               <p class="word"><b class="red">*</b>注册日期:</p>
                <p id="establishTimeId">
                  <input id="establishTime" name="establishTime" type="text" class="int-small" readonly value="${codeMap.certIssueDate }">
                  <A href="javascript:void(0);"><i class="icon-calendar"></i></A>
                 </p>
                <label style="display: none;" id="establishTimeErrorMsg"><img src="${_slpbase}/images/icon-a.png" id="establishTimeImage"><span class="ash" id="establishTimeText">请选择日期</span></label>  
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>注册资本:</p>
                <p><input type="text" class="int-medium" placeholder="" name="registeredCapitals" id="capital" value="${groupKeyInfo.registeredCapitals}"></p>
                <p>万元</p>
                <label id="capitalErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="capitalImage"><span class="ash" id="capitalText">1-12位字符，可用数字及"."</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>经营范围:</p>
                <p><textarea  class="textarea-xxlarge" name ="groupBusinessScope" id="scope">${groupKeyInfo.groupBusinessScope}</textarea></p>
                <label id="scopeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="scopeImage"><span class="ash" id="scopeText">4-300个字符</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>法人姓名:</p>
                <p><input type="text" class="int-medium" placeholder="" id="corporationName" name="legalPerson" value="${groupKeyInfo.legalPerson}"></p>
                <label id="corporationNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="corporationNameImage"><span class="ash" id="corporationNameText">1-12位字符，可用数字及"."</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>法人身份证号码:</p>
                <p><input type="text" class="int-medium" placeholder="" id="idNumber" name="legalCertNum" value="${groupKeyInfo.legalCertNum}"></p>
                <label id="idNumberErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="idNumberImage"><span class="ash" id="idNumberText">有效的18位身份证号</span></label>
             </li>
         </ul>
           <ul>
             <li>
                <p class="word"><b class="red">*</b>身份证复印件:</p>
                <input type="hidden" name="list[1].infoName" value="身份证复印件"/>
                 <input type="hidden" value="11" name="list[1].infoType">
                <input type="hidden" value="11004" name="list[1].infoItem">
                <p class="img"><img src="${imageMap['11004']}" id="certPic2"></p>
                <p class="small-p">
                <span>
                  <input type="button" value="点击上传" class="file-btn">
                  <input type="file" class="file" id="image2" name="image2" onchange="uploadImg('image2','certPic2','idpsId2','imgErrShow2');">
                    <a href="javascript:void(0)" onclick="deleteImg('image2','certPic2','idpsId2','imgErrShow2')">删除</a>
                	<span> 请将身份证正面、反面照片合在一起上传</span> 
                 </span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                <span id="imgErrShow2"></span>
                </p>
             </li>
         </ul>
     </div>
      <!--标题-->  
     <div class="account-title"><p>税务登记证信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税人识别号:</p>
                    <p><input type="text" class="int-medium" placeholder="" id="identifyNumber" name="taxpayerCode" value="${groupKeyInfo.taxpayerCode}"></p>
                    <label id="identifyNumberErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="identifyNumberImage"><span class="ash" id="identifyNumberText">4-20个字符，可用数字及字母</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税人类型:</p>
                    <p>
                     <select class="select-medium" id="taxpayerType" name="taxpayerType">
                    	<option selected="selected" value="0">请选择</option>
                    	<c:forEach var="map" items="${taxpayerTypeMap}">
                    		<option value="${map.key }">${map.value}</option>
                    	</c:forEach>
                     </select>
                    </p>

                     <label id="taxpayerTypeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="taxpayerTypeImage"><span class="ash" id="taxpayerTypeText"  name="taxpayerType">请选择纳税人类型信息</span></label>

                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税类型税码:</p>
                    <p>
	                    <select class="select-medium" id="taxCode" name="taxpayerTypeCode">
	                    	<option selected="selected" value="0">请选择</option>
	                    	<c:forEach var="map" items="${taxpayerTypeCodeMap}">
                    			<option value="${map.key }">${map.value}</option>
                    		</c:forEach>
	                    </select>
                    </p>
                     <label id="taxCodeErMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="ash">请选择纳税类型税码信息</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>税务登记证:</p>
                 <input type="hidden" name="list[2].infoName" value="税务登记证"/>
                <input type="hidden" value="13" name="list[2].infoType">
                <input type="hidden" value="13001" name="list[2].infoItem">
                <p class="img"><img src="${imageMap['13001']}" id="certPic3"></p>
                <p class="small-p">
                <span>
                  <input type="button" value="点击上传" class="file-btn">
                  <input type="file" class="file" id="image3" name="image3" onchange="uploadImg('image3','certPic3','idpsId3','imgErrShow3');">
                  <a href="javascript:void(0)" onclick="deleteImg('image3','certPic3','idpsId3','imgErrShow3')">删除</a>
                  </span>
                 <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                 <span id="imgErrShow3"></span>
                </p>
             </li>
         </ul>    
     </div>
     <!--标题-->  
     <div class="account-title"><p>组织机构代码证</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word"><b class="red">*</b>组织机构代码:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写组织机构代码" id="organizationCode" name="orgCode" value="${groupKeyInfo.orgCode}"></p>
                     <label id="organizationCodeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-d.png" id="organizationCodeImage"><span class="ash" id="organizationCodeText">4-24个字符，可用汉字或英语字母</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>代码证电子版:</p>
                <input type="hidden" name="list[3].infoName" value="代码证电子版"/>
                <input type="hidden" value="14" name="list[3].infoType">
                <input type="hidden" value="14001" name="list[3].infoItem">
                <p class="img"><img src="${imageMap['14001']}" id="certPic4"></p>
                <p class="small-p">
                <span>
                  <input type="button" value="点击上传" class="file-btn">
                  <input type="file" class="file" id="image4" name="image4" onchange="uploadImg('image4','certPic4','idpsId4','imgErrShow4');">
                  <a href="javascript:void(0)" onclick="deleteImg('image4','certPic4','idpsId4','imgErrShow4')">删除</a>
                  </span>
                 <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                 <span id="imgErrShow4"></span>
                </p>
             </li>
         </ul>    
     </div>
    <!--标题-->  
     <div class="account-title"><p>银行开户许可证</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word"><b class="red">*</b>开户银行名称:</p>

                    <p><input type="text" class="int-medium" placeholder="请填写开户银行名称" id="bankName" name="bankName" value="${bankInfo.bankName }"></p>
                    <label id="bankNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="bankNameImage"><span class="ash" id="bankNameText">4-20个字符</span></label>

                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>开户银行支行名称:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写开户银行支行名称" id="subbranchName" name="subbranchName" value="${bankInfo.subBranchName }"></p>
                     <label id="subbranchNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="subbranchNameImage"><span class="ash"  id="subbranchNameText">请输入支行名称</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>公司银行账户:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写公司银行账户" id="bankAccount" name="bankAccount" value="${bankInfo.acctNo }"></p>
                     <label id="bankAccountErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="bankAccountImage"><span class="ash" id="bankAccountText">请输入银行名称</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>银行开户许可证:</p>
                <input type="hidden" name="list[4].infoName" value="银行开户许可证"/>
                <input type="hidden" value="15" name="list[4].infoType">
                <input type="hidden" value="15001" name="list[4].infoItem">
                <p class="img"><img src="${imageMap['15001']}" id="certPic5"></p>
                <p class="small-p">
                <span>
                  <input type="button" value="点击上传" class="file-btn">
                  <input type="file" class="file" id="image5" name="image5" onchange="uploadImg('image5','certPic5','idpsId5','imgErrShow5');">
                  <a href="javascript:void(0)" onclick="deleteImg('image5','certPic5','idpsId5','imgErrShow5')">删除</a>
                  </span>
                 <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                 <span id="imgErrShow5"></span>
                </p>
             </li>
         </ul>    
     </div>
      <!--标题-->  
     <div class="account-title"><p>企业介绍信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
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
                		<c:forEach var="map" items="${groupMemberMap }">
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
	               		<c:forEach var="map" items="${groupTypeMap }">
                			<option value="${map.key}">${map.value}</option>
                		</c:forEach>
	                </select>
	                </p>
	                 <label id="groupTypeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupMemberScaleImage"><span class="ash" id="groupTypeText">请选择公司性质信息</span></label>
	             </li>
        	 </ul>
        	 <ul>
              <li class="form-btn" id="qf-btn"><input type="button" id="agentEnterpriseUpdate" class="slp-btn regsiter-btn" value="保存资质"></li>
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
                    <p>${contactsInfo.contactName}</p>
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
                    <p><input type="text" class="int-medium" placeholder="请填写联系人姓名" id="contactName" name="contactName" value="${contactsInfo.contactName}"></p>
                     <label id="contactNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-d.png" id="contactNameImage"><span class="ash" id="contactNameText">2-24个字符，可用汉字或英语字母</span></label>
                 </li>
             </ul>
              <ul>
                  <li>
                    <p class="word"><b class="red">*</b>所属部门:</p>
                    <p>
                    <select class="select-medium" id="contactDept" name="contactDept">
                    	<option value="0" selected="selected">请选择</option>
	               		<c:forEach var="map" items="${contactDeptMap }">
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
                    <p><input style="text" class="int-medium" id="contactEmail" name="contactEmail" value="${contactsInfo.contactEmail}"></p>
                    <label style="display:none" id="emailMsgError"><img src="${_slpbase}/images/icon-a.png" id="contactEmailMsgImage"><span class="ash" id="contactEmailText">请填写正确的邮箱</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>联系人手机:</p>
                    <p><input type="text" class="int-medium" placeholder="" id="contactMp" name="contactMp" value="${contactsInfo.contactMp}"></p>
                    <label style="display:none" id="contactMpErrMsg"><img src="${_slpbase}/images/icon-a.png" id="contactMpImage"><span class="ash" id="contactMpText">请填写正确手机号</span></label>
                 </li>
             </ul>
               <ul>
                 <li>
                    <p class="word"><b class="red">*</b>短信验证码:</p>
                    <p><input type="text" class="int-mini" id="phoneCode" name="phoneCode"></p>
                    <p><input type="button" class="int-btn" value="获取短信验证码" id="sendPhoneCode"></p>
                    <label id="phoneCodeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="phoneCodeImage"><span class="ash" id="phoneCodeText">验证码错误</span></label>
                 </li>
             </ul>
              <ul>
                 <li class="form-btn" id="ct-btn">
                   <input type="button" class="slp-btn regsiter-btn" id="agentEnterpriseContactsInfo" value="保存联系人">
                   <input type="hidden" id="custNameFlag">
               	   <input type="hidden" id="princeCodeFlag">
               	   <input type="hidden" id="certAddrFlag">
               	   <input type="hidden" id="certNumFlag">
               	   <input type="hidden" id="groupStypeFlag">
               	   <input type="hidden" id="groupMemberScaleFlag">
               	   <input type="hidden" id="contactDeptFlag">
               	   <input type="hidden" id="provinceCodeFlag">
               	   <input type="hidden" id="contactMpFlag">
               	   <input type="hidden" id="phoneCodeFlag">
               	   <input type="hidden" id="groupIndusteryFlag">
               	   
                  <input type="hidden" id="establishTimeFlag"/>
                  <input type="hidden" id="capitalFlag"/>
                  <input type="hidden" id="scopeFlag"/>
                  <input type="hidden" id="corporationNameFlag"/>
                  <input type="hidden" id="idNumberFlag"/>
                  <input type="hidden" id="identifyNumberFlag"/>
                  <input type="hidden" id="taxpayerTypeFlag"/>
                  <input type="hidden" id="taxCodeFlag"/>
                  <input type="hidden" id="picFlag"/>
                  <input type="hidden" id="contactNameFlag"/>

                 <input type="hidden" id="idpsId1" name="list[0].attrValue" value="${codeMap.idpsIdList[0] }">
                 <input type="hidden" id="idpsId2" name="list[1].attrValue" value="${codeMap.idpsIdList[1] }">
                 <input type="hidden" id="idpsId3" name="list[2].attrValue" value="${codeMap.idpsIdList[2] }">
                 <input type="hidden" id="idpsId4" name="list[3].attrValue" value="${codeMap.idpsIdList[3] }">
                 <input type="hidden" id="idpsId5" name="list[4].attrValue" value="${codeMap.idpsIdList[4] }">

                  <input type="hidden" id="bankNameFlag"/>
                  <input type="hidden" id="subbranchNameFlag"/>
                  <input type="hidden" id="bankAccountFlag"/>
                  <input type="hidden" id="organizationCodeFlag"/>
                 </li>
                 
             </ul>
     </div>
     </form:form>
  </div>  
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
	var enterprisePager;
	var auditState = "${groupKeyInfo.auditState}";
	
	var provinceCode = "${codeMap.provinceCode}";
	var cityCode = "${codeMap.cityCode}";
	var countyCode = "${codeMap.countyCode}";
	var certAddr = "${codeMap.certAddr}";
	(function() { 
		seajs.use([ 'app/jsp/user/qualification/baseinfo','app/jsp/user/qualification/agent-supplier-enterprise','app/jsp/user/qualification/qualificationSubmit'], function(BaseInfoQualificationPager,EnterprisePager,QualificationSubmitPager) {
			    baseInfoPager = new BaseInfoQualificationPager({
				element : document.body
			});
			    enterprisePager = new EnterprisePager({
				element : document.body
			});
			   var qualificationSubmitPager = new QualificationSubmitPager({
					element : document.body
				});
			baseInfoPager.render();
			enterprisePager.render();
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
		
		$("#taxpayerType").val("${codeMap.taxpayerType}");
		$("#taxCode").val("${codeMap.taxpayerTypeCode}");
		$("#groupIndustry").val("${codeMap.groupIndustry}");
		$("#groupMemberScale").val("${codeMap.groupMemberScale}");
		$("#groupType").val("${codeMap.groupType}");
		$("#contactDept").val("${codeMap.contactDept}");
		$("#certAddr").val("${codeMap.certAddr}");
	});
</script>