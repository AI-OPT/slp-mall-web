<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<%@ include file="/inc/inc.jsp"%>
   <title>设置支付密码</title>
	<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css"/>
	<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript">
		
		(function() {
			seajs.use([ 'app/jsp/user/bandemail/payPasswordConfirmInfo'], function(ConfirmInfoPager) {
				var pager = new ConfirmInfoPager({
					element : document.body
				});
				pager.render();
			});
		})();
		
		function clearPasswordInputValue(){
			$("#passwordInput").val("");
		}
		 
  </script>
</head>
 

<body onload="clearPasswordInputValue()">
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
 <!--我的订单右侧-->  
   <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">帐户设置</a>&gt;</p>
          <p>绑定邮箱</a></p>
      </div>
      <div class="account-bj">
    
       <!--提示风险-->
        <div class="prompt-risk">
            <p>提示：支付密码未启用。在使用账户余额等资产时，需要输入支付密码，提升账户安全度。支付密码不允许与登录密码相同！</p>
            <p class="img"><img src="${_slpbase }/images/yue-1.png"/></p>
        </div>
      
       <!--步骤-->
           <div class="steps">
                     <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">1</li>
                     <li class="yellow-word">验证身份</li>
                     </ul>
                     <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">2</li>
                     <li class="yellow-word">设置支付密码</li>
                     </ul>
                      <ul>
                     <li class="ash-border"></li>
                     <li class="ash-yuan">3</li>
                     <li class="ash-word">完成</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
              <div class="list-int">
                   <ul>
                        <li class="word">请输入支付密码:</li>
                        <li><input type="password" class="int-medium" placeholder="请输入支付密码"  id="passwordInput" value=""/></li>
                        <li class="lable" id="errorPawMsg" style="display: none;">
                        	<img src="${_slpbase }/images/icon-d.png" id="passwordImage"/><span id="showPawMsg">6-20个字符，可用字母、数字及符号的组合</span>
                        </li>
                        <label>
                          <span id="strength_L" style="display: none;"><img src="${_slpbase }/images/pass-a.png" />有被盗风险,建议使用字母、数字和符号两种及以上组合</span>
                          <span id="strength_M" style="display: none;"><img src="${_slpbase }/images/pass-b.png" />安全强度适中，可以使用三种以上的组合来提高安全强度</span>
                          <span id="strength_H" style="display: none;"><img src="${_slpbase }/images/pass-c.png" id="strength_H"/>你的密码很安全</span>
                         </label>
                    </ul>
                    <ul>
                        <li class="word">确认支付密码:</li>
                        <li><input type="password" class="int-medium" placeholder="再次确认密码" id="confirmationPassword"/></li>
                        <li class="lable" id="errorPasswordMsg" style="display: none;"><img src="${_slpbase }/images/icon-e.png" id="confirmationPasswordImage"/><span id="showPasswordMsg">两次输入的密码不一致</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" id="submitBtn" value="提交"/></li>
                    </ul>
                </div>
      </div>

  </div>  
  </div>  
 </div>
   <!--底部-->
    <div class="footer-wrapper">
    <!--底部－help-->
      <div class="footer">
        <div class="footer-main">
          <div class="footer-title">
              <ul>
                  <li>
                      <p><img src="${_slpbase }/images/foot-a.png"/></p>
                      <p>话费流量全面支持</p>
                  </li>
                  <li>
                      <p><img src="${_slpbase }/images/foot-b.png"></p>
                      <p>价格更低优惠更多</p>
                  </li>
                  <li>
                      <p><img src="${_slpbase }/images/foot-c.png"></p>
                      <p>即时到账安全便捷</p>
                  </li>
                  <li>
                      <p><img src="${_slpbase }/images/foot-d.png"></p>
                      <p>企业充值轻松无忧</p>
                  </li>
              </ul>
          </div>
          
          <div class="footer-title-list">
          <ul>
          <li class="word">商品分类</li>
          <li><a href="#">话费快充</a></li>
          <li><a href="#">流量快充</a></li>
          <li><a href="#">话费卡</a></li>
          <li><a href="#">流量卡</a></li>
          </ul>
          <ul>
          <li class="word">帮助中心</li>
          <li><a href="#">话费充值</a></li>
          <li><a href="#">账户使用</a></li>
          <li><a href="#">支付购买</a></li>
          <li><a href="#">订单相关</a></li>
          </ul>
          <ul>
          <li class="word">商家合作</li>
          <li><a href="#">企业采购</a></li>
          <li><a href="#">代理商申请</a></li>
          <li><a href="#">供货商合作</a></li>
          <li><a href="#">招商平台</a></li>
          </ul>
          <ul class="bor-none">
          <li class="word">网站导航</li>
          <li><a href="#">网站地图</a></li>
          <li><a href="#">亚信官网</a></li>
          <li><a href="#">亚信国际</a></li>
          <li><a href="#">亚信数据</a></li>
          </ul>
          </div>
        </div>
      </div>
   <!--底部－about-->
      <div class="footer-alink">
      <ul>
      <li>
      <a href="#">关于我们</a>
      <a href="#">联系我们</a>
      <a href="#">企业采购</a>
      <a href="#">代理商申请</a>
      <a href="#">供货合作</a>
      <a href="#">API文档</a>
      <a href="#">亚信官网</a>
      <a href="#">网站地图</a>
      </li>
      <li>京ICP备11005544号-15                京公网安备110108007119号</li>
      <li>©2016-2018 亚信旗下话费充值平台，版权所有  All Rights Reserved</li>
      </ul>
                                                                                                   
      </div>
    
    
    </div>
   <!--底部 结束-->

</body>
</html>
<script src="${_slpbase }/scripts/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>

