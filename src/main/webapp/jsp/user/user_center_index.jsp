<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=uft-8">
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<title>Insert title here</title>
</head>
<body>
 <!--顶部菜单-->
 <%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->
 <%@ include file="/inc/user-nav.jsp" %>
<!--导航区域-->

<!--导航区域结束-->
    
     <!--订单详情-->
<div class="fsast-charge">
    <div class="big-wrapper"><!--内侧居中框架-->
    	 <div class="account-home-banner"><img src="${_slpbase }/images/baner-03.png"></div>
    <!--我的订单-->
    <!--我的订单左侧-->
       <%@ include file="/inc/user-leftmenu.jsp" %>
 <!--／我的订单左侧结束-->  
 	
 <!--个人企业帐户中心首页-->  
  <div class="my-order-cnt my-order-cnt-top">
      <div class="order-list-bj order-list-bj-nonebottom">
        	<!--个人企业帐户中心首页-->
       		<div class="account-home-title">
       			 <div class="account-home-title-left">
       			 	<div class="account-title"><p>订单概览</p></div>
       			 	<div class="home-title-left-list">
       			 		<ul>
       			 			<li>
       			 				<p><img src="${_slpbase }/images/g-homeicon-1.png"></p>
       			 				<p><a href="#">待支付<span>100</span></a></p>
       			 			</li>
       			 			<li>
       			 				<p><img src="${_slpbase }/images/g-homeicon-2.png"></p>
       			 				<p><a href="#">账期订单<span>100</span></a></p>
       			 			</li>
       			 			<li>
       			 				<p><img src="${_slpbase }/images/g-homeicon-3.png"></p>
       			 				<p><a href="#">已退款<span>100</span></a></p>
       			 			</li>
       			 		</ul>
       			 	</div>			
       			 </div>
       			 <div class="home-border"></div>
       			 <div class="account-home-title-left">
       			 	<div class="account-title"><p>资产概览</p></div>
       			 	<div class="home-title-left-list">
       			 		<ul>
       			 			<li>
       			 				<p><img src="${_slpbase }/images/g-homeicon-4.png"></p>
       			 				<p><a href="#">账户余额<span>100</span></a></p>
       			 			</li>
       			 			<li>
       			 				<p><img src="${_slpbase }/images/g-homeicon-5.png"></p>
       			 				<p><a href="#">充值卡券<span>100</span></a></p>
       			 			</li>
       			 			<li>
       			 				<p><img src="${_slpbase }/images/g-homeicon-6.png"></p>
       			 				<p><a href="#">授信余额<span>10000</span></a></p>
       			 			</li>
       			 		</ul>
       			 	</div>			
       			 </div>
       		</div>
          	
      </div>
      <!--标签-->
			 <div class="account-home-bt">
			       <p>最近的订单</p>
			       <p class="right"><a href="#">更多>></a></p>
			  </div>
	<!--订单列表-->	  
	 <div class="order-list-bj order-list-bj-nonebottom  none-padding">
	 	<div class="home-table">
                 <table width="100%" border="0">
                        <tr>
                            	 <td width="40%" style="text-align:left;"><a href="#"><img src="${_slpbase }/images/sim-t1.png"></a><a href="#"><img src="${_slpbase }/images/sim-t1.png"></a></td>
                             <td width="15%" >2016-2-17</td>
                             <td width="15%" >¥560.00</td>
                             <td width="15%" >待付款</td>
                             <td width="15%" ><input type="button" class="immedtl-btn" value="查看详情"></td>
                        </tr>
                        <tr>
                            	 <td width="40%" style="text-align:left;"><a href="#"><img src="${_slpbase }/images/sim-t1.png"></a></td>
                             <td width="15%" >2016-2-17</td>
                             <td width="15%" >¥560.00</td>
                             <td width="15%" >待付款</td>
                             <td width="15%" ><input type="button" class="immedtl-btn" value="查看详情"></td>
                        </tr>
                        <tr>
                            	 <td width="40%" style="text-align:left;"><a href="#"><img src="${_slpbase }/images/sim-t1.png"></a></td>
                             <td width="15%" >2016-2-17</td>
                             <td width="15%" >¥560.00</td>
                             <td width="15%" >待付款</td>
                             <td width="15%" ><input type="button" class="immedtl-btn" value="查看详情"></td>
                        </tr>
                 </table>   
            </div>
	 </div>	
		<!--标签-->
			 <div class="account-home-bt">
			       <p>最近浏览</p>
			       <p class="right"><a href="#">更多>></a></p>
			  </div>	  
			  
		<!--浏览列表-->	  
			 <div class="order-list-bj order-list-bj-nonebottom">	  
			 	 <div class="home-browse">
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li>¥30.00</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li>¥30.00</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li>¥30.00</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li>¥30.00</li>
			 	 	</ul>
			 	 </div>
			 </div>	
			<!--标签-->
			 <div class="account-home-bt">
			       <p>最近收藏</p>
			       <p class="right"><a href="#">更多>></a></p>
			  </div>	  
			 <div class="order-list-bj order-list-bj-nonebottom">	  
			 	 <div class="home-csollection">
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 </div>
			 </div>
			 <!--标签-->
			  <div class="account-home-bt">
			       <p>为您推荐</p>
			       <p class="right"><a href="#"><i class="icon-refresh"></i>换一组</a></p>
			  </div>	  
			 <div class="order-list-bj order-list-bj-nonebottom none-padding">	  
			 	 <div class="home-csollection">
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul><ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 	<ul>
			 	 		<li class="border"><a href="#"><img src="${_slpbase }/images/browse-1.png"></a></li>
			 	 		<li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2...</a></li>
			 	 		<li>
			 	 			<p class="je">¥1099.00</p>
			 	 			<p class="pl"><a href="#">120876评价</a></p>
			 	 		</li>
			 	 	</ul>
			 	 </div>
			 <!--标签-->
			  <div class="account-home-bt account-home-bt-border">
			      <a href="#"><i class="icon-refresh"></i>换一组</a>
			  </div>	  
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
