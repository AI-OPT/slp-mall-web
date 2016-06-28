<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>资质申请－代理商－个人</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
		
		(function() { 
			seajs.use(['app/jsp/user/qualification/agent-personal','app/jsp/user/qualification/baseinfo'],function(QualificationPager,BaseInfoQualificationPager) {
				var enterprisePager = new QualificationPager({
					element : document.body
				});
				var baseinfoPage = new BaseInfoQualificationPager({
					element : document.body
				});
				enterprisePager.render();
				baseinfoPage.render();
			});
		})();  
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
<!--资质申请右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资质认证</a></p>
      </div>
      <div class="account-bj">
       <!--提示风险-->
        <div class="prompt-risk small-risk">
            <p>提醒：代理商用户在完善基础认证信息后，才能查看代理商专属价格及专属货品，赶快提交认证所需信息吧！</p>
            <p class="img"><img src="${_slpbase}/images/yue-1.png"></p>
        </div>
      <!--标题-->
     <div class="mar-account-title">
     <div class="account-title"><p>资质类型:个人</p></div>
     </div>
     
     <!--标题-->  
     <div class="account-title"><p>个人资质信息</p></div>
     <!--信息填写-->
     <form:form id="agentPersonal" method="post">
     <div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>真实姓名:</p>
               <p><input type="text" class="int-medium" placeholder="请填写真实姓名" id="custName" name="custName"></p>
                     <label id="custNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-d.png" id="custNameImage"><span class="ash" id="custNameText">4-24个字符，可用汉字或英语字母</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>性别:</p>
                <p><input type="radio" name="gender" class="checkbox-medium" name="custSex" id="man" checked=><span class="Gender">男</span></p>
                <p><input type="radio" name="gender" class="checkbox-medium" name="custSex" id="woman" ><span class="Gender">女</span></p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>学历:</p>
                <p>
                 <select class="select-medium" id="custEducation">
                 	<option value="0">请选择</option>
                 	<option>初中及以下</option>
                 	<option>高中/中专</option>
                 	<option>大专或同等学历</option>
                 	<option>本科学历</option>
                 	<option>硕士研究生</option>
                 	<option>博士</option>
                 	<option>其他学历</option>
                 </select>
                 <label id="custEducationErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="custEducationImage"><span class="ash" id="custEducationText">请输入学历信息</span></label>
                </p>
             </li>
         </ul>
       	  <ul>
              <li>
                <p class="word"><b class="red">*</b>联系地址:</p>
                <p>
                
                <select class="select-xmini" id="provinceCode" name="custProvinceCode" >
                	<option value="0">请选择</option>
                	<c:forEach items="${provinceList}" var="record">
                		<option value="${record.provinceCode}" <c:if test="${insertGroupKeyInfoRequest.provinceCode==record.provinceCode }">selected</c:if>>${record.areaName}</option>
                	</c:forEach>
                </select>
                
                </p>
                <p>
                 <select class="select-xmini" id="cityCode" name="custCityCode">
                	
                 </select>
                </p>
                <p><select class="select-xmini" id="countryCode" name="custCountryCode"></select></p>
                <label id="registerAddrErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="registerAddrImage"><span class="ash" id="registerAddrText">请选择联系地址信息</span></label>
             </li>
             <li class="right">
             <p><input type="text" class="int-xlarge" placeholder="详细街道地址" id="certAddr" name="custAddr"></p>
             <label id="certAddrErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="certAddrImage"><span class="ash" id="certAddrText">5-120个字符</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>生日:</p>
                <p><select class="select-xmini" id="yy_mm_dd" name="yy_mm_dd">
                	 <option value="0">请选择</option>
                   </select>
                 </p>
                <p>
                	<select class="select-xmini" id="mm" name="mm">
                		<option value="0">请选择</option>
                	</select>
                </p>
                <p>
                	<select class="select-xmini" id="dd" name="dd">
                		<option value="0">请选择</option>
                	</select>
                </p>
                <label id="bithdayErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="bithdayImage"><span class="ash" id="bithdayText">请选择生日信息</span></label>
             </li>
             
         </ul>
   		  <ul>
             <li>
                <p class="word"><b class="red">*</b>收入:</p>
                <p>
	                <select class="select-medium" id="inCome" name="incomeLevel">
		                <option value="0">请选择收入</option>
		                <option>3000元及以下</option>
		                <option>3001-5000</option>
		                <option>5001-8000</option>
		                <option>8001-10000</option>
		                <option>10000元以上</option>
	                </select>
                </p>
                <label id="idComeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="inComeImage"><span class="ash" id="inComeText">请选择收入信息</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">介绍信息:</p>
                <p><textarea type="text" class="textarea-xxlarge" id="introduce" placeholder = "简要介绍您产品销售渠道方面的优势或经验等相关内容"></textarea></p>
             </li>
         </ul>
     </div>
     <!--标题-->  
     <div class="account-title"><p>身份证信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                	<p class="word"><b class="red">*</b>身份证号:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写和真实姓名一致的18位身份证号码" id="certNum"></p>
                     <label id="idNumberErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="idNumberImage"><span class="ash" id="idNumberText">18位数字</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>身份证正面照片:</p>
                    <p>
                    <!--上传身份证件-->
                         <div class="upload-card">
                       		<p>支持JPG/PNG/GIF格式，大小不超过3M，尺寸不限</p>
                             <div class="card-big">
                             <!--左侧上传-->
                             	<div class="card-left-big">
                                      <div class="card-left">
                                          <img src="${_slpbase}/images/formp-p.png" id="certPic"><br>上传照片
                                          <input type="file" class="file" id="image1" onchange="uploadImg('image1','certPic');">
                                      </div>
                                      <div class="card-left-word"><img src="${_slpbase}/images/icon-a.png">请上传正面照片</div>
                                  </div>
                             <!--左侧上传结束-->   
                             <!--右侧展示-->   
                                  <div class="card-right-big">
                                 		 <p>示例:</p>
                                         <div class="card-right">
                                         <img src="${_slpbase}/images/card-1.png">
                                         </div>
                                  </div>
                            <!--右侧展示结束-->         
                                  
                             </div>
                     </div>
                     <!--上传身份证件结束-->
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>身份证背面照片:</p>
                    <p>
                    <!--上传身份证件-->
                         <div class="upload-card">
                       		<p>支持JPG/PNG/GIF格式，大小不超过3M，尺寸不限</p>
                             <div class="card-big">
                             <!--左侧上传-->
                             	<div class="card-left-big">
                                      <div class="card-left">
                                          <img src="${_slpbase}/images/formp-p.png" id="certPic2"><br>上传照片
                                          <input type="file" class="file" id="image2" onchange="uploadImg('image2','certPic2');">
                                      </div>
                                      <div class="card-left-word"><img src="${_slpbase}/images/icon-a.png">请上传背面照片</div>
                                  </div>
                             <!--左侧上传结束-->   
                             <!--右侧展示-->   
                                  <div class="card-right-big">
                                 		 <p>示例:</p>
                                         <div class="card-right">
                                         <img src="${_slpbase}/images/card-1.png">
                                         </div>
                                  </div>
                            <!--右侧展示结束-->         
                                  
                             </div>
                     </div>
                     <!--上传身份证件结束-->
                 </li>
             </ul>
             <ul>
                 <li>
                    <p class="word"><b class="red">*</b>手持身份证正面照片:</p>
                    <p>
                    <!--上传身份证件-->
                         <div class="upload-card">
                       		<p>支持JPG/PNG/GIF格式，大小不超过3M，尺寸不限</p>
                             <div class="card-big">
                             <!--左侧上传-->
                             	<div class="card-left-big">
                                      <div class="card-left">
                                          <img src="${_slpbase}/images/formp-p.png" id="certPic3"><br>上传照片
                                          <input type="file" class="file" id="image3" onchange="uploadImg('image3','certPic3');">
                                      </div>
                                      <div class="card-left-word"><img src="${_slpbase}/images/icon-a.png">请上传手持证件照片</div>
                                  </div>
                             <!--左侧上传结束-->   
                             <!--右侧展示-->   
                                  <div class="card-right-big">
                                 		 <p>示例:</p>
                                         <div class="card-right">
                                         <img src="${_slpbase}/images/card-1.png">
                                         </div>
                                  </div>
                            <!--右侧展示结束-->         
                                  
                             </div>
                     </div>
                     <!--上传身份证件结束-->
                 </li>
             </ul>
             </div>
             </form:form>
             <ul>
                 <li class="form-btn">
                 	<input type="button" class="slp-btn regsiter-btn" id="savePersonalQualification" value="保存资质">
                 	<input type="hidden" id="custNameFlag"/>
                 	<input type="hidden" id="certAddrFlag">
                 	<input type="hidden" id="idNumberFlag"/>
                 	<input type="hidden" id="bithdayFlag"/>
                 	<input type="hidden" id="inComeFlag"/>
                 	<input type="hidden" id="custEducationFlag"/>
                 	<input type="hidden" id="provinceCodeFlag"/>
                 </li>
             </ul>
     </div>
     </div>
  		</div>  
   		</div>
		 <!--底部-->
    		<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
</body>
</html>
