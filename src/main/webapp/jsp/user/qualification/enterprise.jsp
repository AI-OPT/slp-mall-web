<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
      <!--提示风险-->
        <div class="prompt-risk small-risk">
            <p>提醒：请完善以下资质信息进行企业用户认证，，以享受企业专属价格及优惠。</p>
            <p class="img"><img src="${_slpbase}/images/yue-1.png"></p>
        </div>
     <!--标题-->  
     <div class="account-title"><p>企业资质信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>企业名称:</p>
                <p><input type="text" class="int-xlarge" placeholder="请填写营业执照上的注册企业名称" id="custName"></p>
                 <label id="custNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash" id="enterpriseErrMsgShow">4-60个字符，可用中英文、数字、“-”、”_”、“（）”及”( )”</span></label>
             </li>
         </ul>
       	  <ul>
             <li>
                <p class="word"><b class="red">*</b>企业注册地址:</p>
                <p><select class="select-xmini" id="princeCode"></select></p>
                <p><select class="select-xmini" id="cityCode"></select></p>
                <p><select class="select-xmini" id="countryCode"></select></p>
             </li>
             <li class="right">
             <p><input type="text" class="int-xlarge" placeholder="详细街道地址" id="certAddr"></p>
             <label id="certAddrErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">5-120个字符</span></label>
             </li>
         </ul>
   		  <ul>
             <li>
                <p class="word"><b class="red">*</b>营业执照注册号:</p>
                <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号" id="certNum"></p>
                 <label id="certNumErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">最多20个字符，允许使用英语字母（区分大小写）、数字及“-”</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>营业执照副本:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png" id="certPic"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                </p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>行业:</p>
                <p><select class="select-medium" id="groupIndustery"></select></p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word">官网:</p>
                <p><input type="text" class="int-medium" placeholder="请填写官网网址" id="groupWebsit"></p>
                 <label id="groupWebsitErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">3-60个字符，允许使用字母、数字、特殊字符</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>公司人数:</p>
                <p><select class="select-medium" id="groupMemberScale"></select></p>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>公司性质:</p>
                <p><select class="select-medium" id="groupStype"></select></p>
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
                    <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号" id="contactName"></p>
                     <label id="contactNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-24个字符，可用汉字或英语字母</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>所属部门:</p>
                    <p><select class="select-medium" id="contactDept"></select></p>
                    <label style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择所在部门信息</span></label>
                 </li>
             </ul>
             <ul>
                 <li>
                    <p class="word">联系人邮箱:</p>
                    <p><input style="text" class="int-medium" id="contactEmail"></p>
                    <label style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">请填写正确的邮箱</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>联系人手机:</p>
                    <p><input type="text" class="int-medium" placeholder="" id="contactMp"></p>
                    <label style="display:none" id="contactMpErrMsg"><img src="${_slpbase}/images/icon-a.png"><span class="red">请填写正确手机号</span></label>
                 </li>
             </ul>
               <ul>
                 <li>
                    <p class="word"><b class="red">*</b>短信验证码:</p>
                    <p><input type="text" class="int-mini" id="phoneCode"></p>
                    <p><input type="button" class="int-btn" value="获取短信验证码" id="sendPhoneCode"></p>
                    <label id="phoneCodeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png"><span class="red">验证码错误</span></label>
                 </li>
             </ul>
              <ul>
                 <li class="form-btn"><input type="button" class="slp-btn regsiter-btn" value="保存资质" id="submit"></li>
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