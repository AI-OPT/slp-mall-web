<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<meta charset="UTF-8">
<title>修改邮箱－邮箱验证－绑定新邮箱</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css"/>
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css"/>
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css"/>
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css"/>
</head>

<body>
 <!--顶部菜单-->
 <div class="top-menu">
     <div class="top-menu-main">
     <ul class="left">
         <li>所在地</li>
         <li class="city"><a href="#" >北京<img src="${_slpbase }/images/open-a.png"></a>
         <!--选择所在地城市-->
                 <div class="city-hover" style=" display:none;">
                      <ul class="title">
                          <li class="hot">热门城市</li>
                          <li><A href="#">北京</A></li>
                          <li><A href="#">上海</A></li>
                          <li><A href="#">广东</A></li>
                          <li><A href="#">浙江</A></li> 
                          <li><A href="#">江苏</A></li>                             
                      </ul>
                       <ul class="city-list">
                          <li><A href="#">北京</A></li>
                          <li><A href="#">上海</A></li>
                          <li><A href="#">天津</A></li>
                          <li><A href="#">重庆</A></li>
                          <li><A href="#">河北</A></li> 
                          <li><A href="#">山西</A></li>
                          <li><A href="#">河南</A></li>
                          <li><A href="#">辽宁</A></li>
                          <li><A href="#">吉林</A></li>
                          <li><A href="#">黑龙江</A></li>
                          <li><A href="#">内蒙古</A></li> 
                          <li><A href="#">江苏</A></li>
                          <li><A href="#">山东</A></li>
                          <li><A href="#">安徽</A></li>
                          <li><A href="#">浙江</A></li>
                          <li><A href="#">福建</A></li>
                          <li><A href="#">湖北</A></li>
                          <li><A href="#">广东</A></li>
                          <li><A href="#">海南</A></li> 
                          <li><A href="#">四川</A></li>
                          <li><A href="#">贵州</A></li>  
                          <li><A href="#">云南</A></li>
                          <li><A href="#">北京</A></li>
                          <li><A href="#">西藏</A></li>
                          <li><A href="#">甘肃</A></li>
                          <li><A href="#">青海</A></li>
                          <li><A href="#">宁夏</A></li> 
                          <li><A href="#">新疆</A></li>
                          <li><A href="#">台湾</A></li>
                          <li><A href="#">香港</A></li>
                          <li><A href="#">澳门</A></li>                           
                      </ul>
                  </div>
                  
         </li> 
       <!--选择所在地城市结束-->
         
         
     </ul>
     <ul class="right">
         <li><A href="#">免费注册</A></li>
         <li><A href="#">登录</A>|</li>
         <li><A href="#"><i class="icon-shopping-cart"></i>购物车</A>|</li>
         <li><A href="#">我的订单</A>|</li>
         <li class="use"><A href="#">账户中心<img src="${_slpbase }/images/open-a.png"></A>|
             <!--账户展开-->
             <div class="use-hover" style=" display:none;">
                 <ul>
                     <li><A href="#">我的订单</A></li>
                     <li><A href="#">账户余额</A></li>
                     <li><A href="#">充值卡券</A></li>
                     <li><A href="#">收藏夹</A></li>
                     <li><A href="#">通讯录</A></li>
                     <li><A href="#">安全设置</A></li>
                 </ul>
             </div>
             <!--账户展开结束-->
         </li>
         <li><A href="#">企业采购</A>|</li>
         <li><A href="#">代理商</A>|</li>
         <li><A href="#">供货商</A>|</li>
         <li><A href="#">API</A>|</li>
         <li class="kefu"><A href="#">客户服务<img src="${_slpbase }/images/open-a.png"></A>
               <!--账户展开-->
             <div class="kefu-hover" style=" display:none;">
                 <ul>
                     <li><A href="#">帮助中心</A></li>
                     <li><A href="#">联系我们</A></li>
                     <li><A href="#">意见反馈</A></li>
                 </ul>
             </div>
             <!--账户展开结束-->
         </li>
     </ul>
     </div>
 
 </div>
<!--顶部菜单结束-->

<!--导航区域-->
<div class="fsast-bj">
 <div class="fsast-head">
        <div class="fsast-logo">
            <ul>
                <li><a href="#"><img src="${_slpbase }/images/login-logo.png"></a></li>
                <li>账户中心</li>
            </ul>
        </div>
        <div class="fsast-bav">
            <ul>
                <li><a href="#">首页</a></li>
                <li><a href="#">我的订单</a></li>
                <li class="shez"><a href="#">账户设置<i class="icon-angle-down"></i></a>
                <div class="setgs" style=" display:none;">
                    <ul>
                        <li><a href="#">资质认证</a></li>
                        <li><a href="#">安全设置</a></li>
                        <li><a href="#">登录密码</a></li>
                        <li><a href="#">支付密码</a></li>
                        <li><a href="#">手机绑定</a></li>
                        <li><a href="#">邮箱绑定</a></li>
                    </ul>
                </div>
                </li>
                <li><a href="#">消息</a><p class="icon">4</p></li>                          
            </ul>
        </div>
        <div class="fsast-search">
             <ul>
                  <li><input type="text" class="fsast-xlarge"></li>
                  <li><A href="#"><i class="icon-search"></i></A></li>
             </ul>  
        </div>
    </div>
 </div>
<!--导航区域结束-->
     
     <!--订单详情-->
<div class="fsast-charge">
    <div class="big-wrapper"><!--内侧居中框架-->
    <!--我的订单-->
    <!--我的订单左侧-->
    <div class="my-order-menu">
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn1.png"></li>
            <li class="word">
                <p class="">订单中心</p>
                <p class="active"><A href="#">我的订单</A></p>
                <p><A href="#">评价管理</A></p>
                <p><A href="#">订单回收站</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn2.png"></li>
            <li class="word">
                <p class="">资产中心</p>
                <p><A href="#">账户余额</A></p>
                <p><A href="#">充值卡券</A></p>
                <p><A href="#">我的授信</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn3.png"></li>
            <li class="word">
                <p class="">关注中心</p>
                <p><A href="#">收藏夹</A></p>
                <p><A href="#">我的足迹</A></p>
                <p><A href="#">新消息（0）</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn5.png"></li>
            <li class="word">
                <p class="">账户设置</p>
                <p><A href="#">个人资料</A></p>
                <p><A href="#">通讯录</A></p>
                <p><A href="#">我的API</A></p>
                <p><A href="#">安全设置</A></p>
            </li>
        </ul>
    </div>
 <!--／我的订单左侧结束-->  
 <!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">帐户设置</a>&gt;</p>
          <p><a>绑定邮箱</a></p>
      </div>
      <div class="account-bj">
    
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
	                     <li class="yellow-word">绑定新邮箱</li>
                     </ul>
                      <ul>
	                     <li class="ash-border"></li>
	                     <li class="ash-yuan">3</li>
	                     <li class="ash-word">完成修改</li>
                     </ul>
           </div>                                          
          <!--/步骤结束-->
              <div class="list-int">
                    <ul>
                        <li class="word">新邮箱:</li>
                        <li><input type="password" class="int-medium" placeholder=""></li>
                        <li class="lable"><img src="${_slpbase }/images/icon-c.png"><span>使用您常用的邮箱</span></li>
                    </ul>
                    <ul>
                        <li class="word">验证码:</li>
                        <li><input type="text" class="int-small"></li>
                        <li><img src="${_slpbase }/images/yzm-1.png"></li>
                        <li ><a href="#" class="alink">换一张</a></li>
                        <li class="lable"><img src="${_slpbase }/images/icon-a.png"><span class="red">验证码错误</span></li>
                    </ul>
                      <ul>
                        <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" value="发送验证邮件"></li>
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
                      <p><img src="${_slpbase }/images/foot-a.png"></p>
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
