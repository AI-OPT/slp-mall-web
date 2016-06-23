<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<script src="${_base}/resources/spm_modules/app/jsp/user/qualification/birthday.js" type="text/javascript"></script>
<script type="text/javascript">
	(function() {
		birth.init('yy_mm_dd');
		seajs.use('app/jsp/user/qualification/agent-select', function(
				QualificationPager) {
			var pager = new QualificationPager();
			pager.render();
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
     <div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>真实姓名:</p>
                <p><input type="text" class="int-xlarge" placeholder="请填写真实姓名" id="realName"></p>
                 <label id="realNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-60个字符，可用中英文、数字、“-”、”_”、“（）”及”( )”</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>性别:</p>
                <p><input type="radio" name="gender" class="checkbox-medium" ><span class="Gender">男</span></p>
                 <p><input type="radio"  name="gender" class="checkbox-medium" ><span class="Gender">女</span></p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>学历:</p>
                <p>
                 <select class="select-medium" id="education">
                 	<option value="0">请选择</option>
                 	<option>初中及以下</option>
                 	<option>高中/中专</option>
                 	<option>大专或同等学历</option>
                 	<option>本科学历</option>
                 	<option>硕士研究生</option>
                 	<option>博士</option>
                 	<option>其他学历</option>
                 </select>
                </p>
             </li>
         </ul>
       	  <ul>
             <li>
                <p class="word"><b class="red">*</b>联系地址:</p>
                  <select class="select-xmini" id="princeCode" name="princeCode">
                	<option value="0">请选择</option>
                	<c:forEach items="${provinceList}" var="record">
                		<option value="${record.provinceCode}">${record.areaName}</option>
	                	</c:forEach>
	                </select>
                <p>
                 <select class="select-xmini" id="cityCode" name="cityCode">
                	
                 </select>
                </p>
                <p><select class="select-xmini" id="countryCode" name="countryCode"></select></p>
                 <label id="addErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择联系地址</span></label>
             </li>
             <li class="right">
             <p><input type="text" class="int-xlarge" placeholder="详细街道地址" id="address"></p>
             <label id="addressErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">5-120个字符</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>生日:</p>
                <p><select class="select-xmini" id="yy_mm_dd">
                	 <option value="0">请选择</option>
                   </select>
                 </p>
                <p>
                	<select class="select-xmini" id="mm">
                		<option value="0">请选择</option>
                	</select>
                </p>
                <p>
                	<select class="select-xmini" id="dd">
                		<option value="0">请选择</option>
                	</select>
                </p>
             </li>
         </ul>
   		  <ul>
             <li>
                <p class="word"><b class="red">*</b>收入:</p>
                <p><select class="select-medium">
                <option>请选择收入</option>
                <option>3000元及以下</option>
                <option>3001-5000</option>
                <option>5001-8000</option>
                <option>8001-10000</option>
                <option>10000元以上</option>
                </select></p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">介绍信息:</p>
                <p><textarea type="text" class="textarea-xxlarge" id="introduce"></textarea></p>
             </li>
         </ul>
     </div>
     <!--标题-->  
     <div class="account-title"><p>身份证信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word">身份证号:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写和真实姓名一致的18位身份证号码" id="idNumber"></p>
                     <label id="idNumberErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">18位数字</span></label>
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
                                          <img src="${_slpbase}/images/formp-p.png"><br>上传照片
                                          <input type="file" class="file">
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
                  </p>
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
                                          <img src="${_slpbase}/images/formp-p.png"><br>上传照片
                                          <input type="file" class="file">
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
                  </p>
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
                                          <img src="${_slpbase}/images/formp-p.png"><br>上传照片
                                          <input type="file" class="file">
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
                  </p>
                 </li>
             </ul>
             <ul>
                 <li class="form-btn"><input type="button" class="slp-btn regsiter-btn" value="保存资质"></li>
             </ul>
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
