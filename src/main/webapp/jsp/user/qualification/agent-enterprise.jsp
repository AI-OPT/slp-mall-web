<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<script type="text/javascript">
	(function() {
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
      <div class="mar-account-title">
      <div class="account-title"><p>资质类型:企业</p></div>
      </div>
     <!--标题-->  
     <div class="account-title"><p>营业执照信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>企业名称:</p>
                <p><input type="text" class="int-xlarge" placeholder="请填写营业执照上的注册企业名称" id="companyName"></p>
                 <label id="companyNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-60个字符，可用中英文、数字、“-”、”_”、“（）”及”( )”</span></label>
             </li>
         </ul>
       	  <ul>
             <li>
                <p class="word"><b class="red">*</b>企业注册地址:</p>
                <p><select class="select-xmini" id="prince"></select></p>
                <p><select class="select-xmini" id="city"></select></p>
                <p><select class="select-xmini" id="district"></select></p>
                 <label id="addErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">企业名称长度为4-60个字符</span></label>
             </li>
             <li class="right">
             <p><input type="text" class="int-xlarge" placeholder="详细街道地址" id="address"></p>
             <label id="address" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">5-120个字符</span></label>
             </li>
         </ul>
   		  <ul>
             <li>
                <p class="word"><b class="red">*</b>营业执照注册号:</p>
                <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号" id="registerNum"></p>
                 <label id="registerNumErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">最多20个字符，允许使用英语字母（区分大小写）、数字及“-”</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>营业执照副本:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                </p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>成立日期:</p>
                <p><input type="text" class="int-medium"></p>
                 <label id="dateErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择日期</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>注册资本:</p>
                <p><input type="text" class="int-medium" placeholder=""></p>
                <p>万元</p>
                 <label id="capitalErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">1-12位字符，可用数字及"."</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>经营范围:</p>
                <p><textarea type="text" class="textarea-xxlarge"></textarea></p>
                <label id="scopeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-300个字符</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>法人姓名:</p>
                <p><input type="text" class="int-medium" placeholder="" id="corporationName" ></p>
                <label id="corporationNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">1-12位字符，可用数字及"."</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>法人身份证号码:</p>
                <p><input type="text" class="int-medium" placeholder="" id="idNumber"></p>
                <label id="idNumberErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">有效的18位身份证号</span></label>
             </li>
         </ul>
           <ul>
             <li>
                <p class="word"><b class="red">*</b>身份证复印件:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>请将身份证正面、反面照片合在一张图片上传，支持JPG/PNG/GIF格式，最大不超过3M</span>
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
                    <p><input type="text" class="int-medium" placeholder="" id="identifyNumber"></p>
                     <label id="identifyNumberErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">4-20个字符，可用数字及字母</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税人类型:</p>
                    <p><select class="select-medium"></select></p>
                     <label id="typeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择纳税人类型</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税类型税码:</p>
                    <p><select class="select-medium"></select></p>
                     <label id="taxCodeErMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择纳税类型税码</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>税务登记证:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
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
                    <p class="word"><b class="red">*</b>联系人姓名:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写联系人姓名" id="contactId"></p>
                     <label id="contactId" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">4-50个字符，可用数字及字母</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>代码证电子版:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
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
                    <p><input type="text" class="int-medium" placeholder="请填写开户银行名称"></p>
                     <label id="bankNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-20个字符</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>开户银行支行名称:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写开户银行支行名称"></p>
                     <label id="subbranchErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请输入支行名称</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>公司银行账户:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写公司银行账户"></p>
                     <label id="accountErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请输入银行名称</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>银行开户许可证:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
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
                    <p><select class="select-medium"></select></p>
                     <label id="tradeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择所在行业</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">官网:</p>
                    <p><input type="text" class="int-medium" id="officialWeb"></p>
                     <label id="officialWebErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">3-60个字符，允许使用字母、数字、特殊字符</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>公司人数:</p>
                    <p><select class="select-medium"></select></p>
                     <label id="numerErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择公司人数</span></label>
                 </li>
             </ul>
               <ul>
                <li>
                    <p class="word"><b class="red">*</b>公司性质:</p>
                    <p><select class="select-medium"></select></p>
                     <label id="natrueErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择公司性质</span></label>
                 </li>
             </ul>
     </div>
      <!--标题-->  
     <div class="account-title"><p>联系人信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word">联系人姓名:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写联系人姓名"></p>
                     <label id="contactNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-24个字符，可用汉字或英语字母</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>所属部门:</p>
                    <p><select class="select-medium"></select></p>
                    <label id="departmentErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择所在部门信息</span></label>
                 </li>
             </ul>
             <ul>
                 <li>
                    <p class="word">联系人邮箱:</p>
                    <p><select class="select-medium"></select></p>
                    <label id="contactEmailErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请填写正确的邮箱</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>联系人手机:</p>
                    <p><input type="text" class="int-medium" placeholder="请输入联系人手机"></p>
                    <label id="contactPhoneErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请填写正确手机号</span></label>
                 </li>
             </ul>
               <ul>
                 <li>
                    <p class="word"><b class="red">*</b>短信验证码:</p>
                    <p><input type="text" class="int-mini"></p>
                    <p><input type="button" id="SendPhoneCode" class="int-btn" value="获取短信验证码"></p>
                    <label id="phoneCodeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">验证码错误</span></label>
                 </li>
             </ul>
              <ul>
                 <li class="form-btn"><input type="button" id="submit" class="slp-btn regsiter-btn" value="保存资质"></li>
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
