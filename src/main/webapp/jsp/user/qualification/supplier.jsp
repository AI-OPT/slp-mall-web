<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<!--Support IE Text -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<title>资质申请－供应商</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css">
<script type="text/javascript">
(function() { 
	seajs.use([ 'app/jsp/user/qualification/baseinfo'], function(QualificationPager) {
		var pager = new QualificationPager({
			element : document.body
		});
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
      </div>
      
      <!--提示风险-->
        <div class="prompt-risk small-risk">
            <p>提醒：代理商用户在完善基础认证信息后，才能查看代理商专属价格及专属货品，赶快提交认证所需信息吧！</p>
            <p class="img"><img src="${_slpbase}/images/yue-1.png"></p>
        </div>
     <!--标题-->  
     <div class="account-title"><p>营业执照信息</p></div>
     <!--信息填写-->
     <div class="nav-form">
         <ul>
             <li>
                <p class="word"><b class="red">*</b>企业名称:</p>
                <p><input type="text" class="int-xlarge" placeholder="请填写营业执照上的注册企业名称" id="custName" name="custName"></p>
                 <label id="custNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="custNameImage"><span class="ash" id="enterpriseErrMsgShow">4-60个字符，可用中英文、数字、“-”、”_”、“（）”及”( )”</span></label>
             </li>
         </ul>
       	  <ul>
             <li>
                <p class="word"><b class="red">*</b>企业注册地址:</p>
                <p>
                
                <select class="select-xmini" id="princeCode" name="princeCode">
                	<option value="0">请选择</option>
                	<c:forEach items="${provinceList}" var="record">
                		<option value="${record.provinceCode}">${record.areaName}</option>
                	</c:forEach>
                </select>
                
                </p>
                <p>
                 <select class="select-xmini" id="cityCode" name="cityCode">
                 </select>
                </p>
                <p><select class="select-xmini" id="countryCode" name="countryCode"></select></p>
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
                <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号" id="certNum" name="certNum"></p>
                 <label id="certNumErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="certNumImage"><span class="ash" id="certNumText">最多20个字符，允许使用英语字母（区分大小写）、数字及“-”</span></label>
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
                <p id="establishTimeId" ><input id="establishTime" type="text" class="int-small" readonly><A href="javascript:void(0);"><i class="icon-calendar"></i></A></p>
                <label style="display: none;" id="timeErrorMsg"><img src="${_slpbase}/images/icon-a.png" id="timeErrorMsgImage"><span class="red">请选择日期</span></label> 
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>注册资本:</p>
                <p><input type="text" class="int-medium" placeholder=""></p>
                <p>万元</p>
                 <label style="display: none;"><img src="${_slpbase}/images/icon-c.png"><span class="ash">1-12位字符，可用数字及"."</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>经营范围:</p>
                <p><textarea type="text" class="textarea-xxlarge"></textarea></p>
                <label style="display: none;"><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-300个字符</span></label>
             </li>
         </ul>
         <ul>
             <li>
                <p class="word"><b class="red">*</b>法人姓名:</p>
                <p><input type="text" class="int-medium" placeholder=""></p>
                <label><img src="${_slpbase}/images/icon-c.png"><span class="ash">1-12位字符，可用数字及"."</span></label>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word"><b class="red">*</b>法人身份证号码:</p>
                <p><input type="text" class="int-medium" placeholder=""></p>
                <label><img src="${_slpbase}/images/icon-c.png"><span class="ash">有效的18位身份证号</span></label>
             </li>
         </ul>
           <ul>
             <li>
                <p class="word"><b class="red">*</b>身份证复印件:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
	                <span>
	                	<input type="button" value="点击上传" class="file-btn">
	                	<input type="file" class="file">
	                	<a href="#">删除</a>
	                	<span>请将身份证正面、反面照片合在一张图</span>
	                </span>
	                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
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
                    <p><input type="text" class="int-medium" placeholder=""></p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">4-20个字符，可用数字及字母</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税人类型:</p>
                    <p><select class="select-medium">
                    	<option selected="selected">请选择</option>
                    	<option>一般纳税人</option>
                    	<option>小规模纳税人</option>
                    	<option>非增值税纳税人</option>
                    	</select>
                    </p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择纳税人类型</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>纳税类型税码:</p>
                    <p>
                       <select class="select-medium">
                    		<option selected="selected">请选择</option>
	                    	<option>0%</option>
	                    	<option>3%</option>
	                    	<option>6%</option>
	                    	<option>7%</option>
	                    	<option>11%</option>
	                    	<option>13%</option>
	                    	<option>17%</option>
                       </select>
                    </p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择纳税类型税码</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word"><b class="red">*</b>税务登记证:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span>
                  <input type="button" value="点击上传" class="file-btn">
                  <input type="file" class="file">
                  <a href="#">删除</a>
                 </span>
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
                    <p class="word">联系人姓名:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写联系人姓名" id="contactName" name="contactName"></p>
                     <label id="contactNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-d.png" id="contactNameImage"><span class="ash" id="contactNameText">4-24个字符，可用汉字或英语字母</span></label>
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
                    <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号"></p>
                     <label><img src="${_slpbase}/images/icon-c.png"><span class="ash">4-20个字符</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>开户银行支行名称:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号"></p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">请输入支行名称</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word"><b class="red">*</b>公司银行账户:</p>
                    <p><input type="text" class="int-medium" placeholder="请填写营业执照上的注册号"></p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">请输入银行名称</span></label>
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
                <p>
	                 <select class="select-medium" id="groupIndustery" name="groupIndustery">
	                	<option value="0">请选择</option>
	                	<c:forEach items="${industryList}" var="re">
	                		<option value="${re.industryCode }">${re.industryName }</option>
	                	</c:forEach>
	                </select>
                </p>
                <label id="groupIndusteryErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupIndusteryImage"><span class="ash" id="groupIndusteryText">请选择行业信息</span></label>
             </li>
             </ul>
             <ul>
                <li>
                <p class="word">官网:</p>
                <p><input type="text" class="int-medium" placeholder="请填写官网网址" id="groupWebsite" name="groupWebsite"></p>
                 <label id="groupWebsitErrMsg" style="display:none"><img src="${_slpbase}/images/icon-c.png" id="groupWebsiteImage"><span class="ash" id="groupWebsiteText">3-60个字符，允许使用字母、数字、特殊字符</span></label>
             </li>
             </ul>
             <ul>
               <li>
                <p class="word"><b class="red">*</b>公司人数:</p>
                <p>
                	<select class="select-medium" id="groupMemberScale" name="groupMemberScale">
                		<option value="0" selected="selected">请选择</option>
                		<option value="1">1-50人</option>
                		<option value="2">51-100人</option>
                		<option value="3">101-200人</option>
                		<option value="4">201-500人</option>
                		<option value="5">500-1000人</option>
                		<option value="6">1000人以上</option>
                	</select>
                </p>
                 <label id="groupMemberScaleErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupMemberScaleImage"><span class="ash" id="groupMemberScaleText">请选择公司人数信息</span></label>
             </li>
             </ul>
               <ul>
                <li>
                <p class="word"><b class="red">*</b>公司性质:</p>
                <p>
                <select class="select-medium" id="groupStype" name="groupStype">
                	<option value="0" selected="selected">请选择</option>
               		<option value="1">民营企业</option>
               		<option value="2">外商独资</option>
               		<option value="3">上市公司</option>
               		<option value="4">股份制企业</option>
               		<option value="5">国有企业机关</option>
               		<option value="6">事业单位</option>
               		<option value="7">其他</option>
                </select>
                </p>
                 <label id="groupStypeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="groupMemberScaleImage"><span class="ash" id="groupStypeText">请选择公司性质信息</span></label>
             </li>
             </ul>
     </div>
      <!--标题-->  
     <div class="account-title"><p>组织机构代码证</p></div>
     <!--信息填写-->
     <div class="nav-form">
           <ul>
                <li>
                    <p class="word"><b class="red">*</b>供应商品信息:</p>
                    <p><select class="select-medium"></select></p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择供应商品类型</span></label>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">品牌名称（中文):</p>
                    <p><input type="text" class="int-medium"/></p>
                 </li>
             </ul>
             <ul>
                <li>
                    <p class="word">品牌名称（英文):</p>
                    <p><input type="text" class="int-medium"/></p>
                     <label><img src="${_slpbase}/images/icon-a.png"><span class="red">请选择供应商品类型</span></label>
                 </li>
             </ul>
              <ul>
             <li>
                <p class="word">商品注册证:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                </p>
             </li>
         </ul>
          <ul>
             <li>
                <p class="word">行业资质证明:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                </p>
             </li>
         </ul> 
          <ul>
             <li>
                <p class="word">商品质检/检验报告:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                </p>
             </li>
         </ul> 
         <ul>
             <li>
                <p class="word">卫生/生产许可证:</p>
                <p class="img"><img src="${_slpbase}/images/fom-t.png"></p>
                <p class="small-p">
                <span><input type="button" value="点击上传" class="file-btn"><input type="file" class="file"><a href="#">删除</a></span>
                <span>支持JPG/PNG/GIF格式，最大不超过3M</span>
                </p>
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
                    <p><input type="text" class="int-medium" placeholder="请填写联系人姓名" id="contactName" name="contactName"></p>
                     <label id="contactNameErrMsg" style="display:none"><img src="${_slpbase}/images/icon-d.png" id="contactNameImage"><span class="ash" id="contactNameText">4-24个字符，可用汉字或英语字母</span></label>
                 </li>
             </ul>
              <ul>
                <li>
                    <p class="word"><b class="red">*</b>所属部门:</p>
                    <p>
                    <select class="select-medium" id="contactDept" name="contactDept">
                    	<option value="0" selected="selected">请选择</option>
	               		<option value="1">财务部</option>
	               		<option value="2">人事部</option>
	               		<option value="3">行政部</option>
	               		<option value="4">企划部</option>
	               		<option value="5">渠道部</option>
	               		<option value="6">技术部</option>
	               		<option value="7">销售部</option>
	               		<option value="8">工程项目部</option>
	               		<option value="9">研发部</option>
	               		<option value="10">采购部</option>
	               		<option value="11">维修部</option>
	               		<option value="12">客服部</option>
	               		<option value="13">市场部</option>
	               		<option value="14">产品部</option>
                   	    <option value="15">总经办</option>
	               		<option value="16">其他</option>
                    </select>
                    </p>
                    <label style="display:none" id="contactDeptErrMsg"><img src="${_slpbase}/images/icon-a.png"><span class="ash">请选择所在部门信息</span></label>
                 </li>
             </ul>
             <ul>
                  <li>
                    <p class="word">联系人邮箱:</p>
                    <p><input style="text" class="int-medium" id="contactEmail" name="contactEmail"></p>
                    <label style="display:none" id="emailMsgError"><img src="${_slpbase}/images/icon-a.png" id="emailMsgImage"><span  id="contactEmailText">请填写正确的邮箱</span></label>
                 </li>
             </ul>
              <ul>
                 <li>
                    <p class="word"><b class="red">*</b>联系人手机:</p>
                    <p><input type="text" class="int-medium" placeholder="" id="contactMp" name="contactMp"></p>
                    <label style="display:none" id="contactMpErrMsg"><img src="${_slpbase}/images/icon-a.png" id="contactMpImage"><span id="contactMpText">请填写正确手机号</span></label>
                 </li>
             </ul>
               <ul>
                 <li>
                    <p class="word"><b class="red">*</b>短信验证码:</p>
                    <p><input type="text" class="int-mini" id="phoneCode" name="phoneCode"></p>
                    <p><input type="button" class="int-btn" value="获取短信验证码" id="sendPhoneCode"></p>
                    <label id="phoneCodeErrMsg" style="display:none"><img src="${_slpbase}/images/icon-a.png" id="phoneCodeImage"><span  id="phoneCodeText">验证码错误</span></label>
                 </li>
             </ul>
              <ul>
                 <li class="form-btn">
                	 <input type="button" class="slp-btn regsiter-btn" value="保存资质" id="toSave">
                	 <input type="hidden" id="custNameFlag">
                	 <input type="hidden" id="princeCodeFlag">
                	 <input type="hidden" id="certAddrFlag">
                	 <input type="hidden" id="certNumFlag">
                	 <input type="hidden" id="groupStypeFlag">
                	 <input type="hidden" id="groupMemberScaleFlag">
                	 <input type="hidden" id="contactDeptFlag">
                	 <input type="hidden" id="princeCodeFlag">
                	 <input type="hidden" id="contactMpFlag">
                	 <input type="hidden" id="phoneCodeFlag">
                	 <input type="hidden" id="groupIndusteryFlag">
   
                	 <input type="hidden" id="ipdsId" name="list[0].infoName">
                 </li>
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
