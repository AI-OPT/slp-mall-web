<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <!--顶部菜单-->
 <div class="top-menu">
     <div class="top-menu-main">
     <ul class="left">
         <li>所在地</li>
         <li class="city"><a href="javascript:void(0)" currentCityCode="11" currentCityName="北京" id="currentCity">北京<img src="${_slpbase }/images/open-a.png">
         
         </a>
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
                       <ul class="city-list" id="cityShowData">
                       <script id="cityTmpl" type="text/x-jsrender">
							<li><A href="javascript:void(0)" areaCodeId="{{:areaCode}}" areaNameId="{{:areaName}}"class="ATTs_BTN">{{:areaName}}</A></li>
					   </script>
                      </ul>
                  </div>
                  
         </li> 
       <!--选择所在地城市结束-->
         
         
     </ul>
     <ul class="right">
         <li><A href="#">免费注册</A></li>
         <li><A href="#">登录</A>|</li>
         <li><A href="${_base}/shopcart/cartDetails"><i class="icon-shopping-cart"></i>购物车</A>|</li>
         <li><A href="#">我的订单</A>|</li>
         <li class="use"><A href="${_base}/jsp/user/user_center_index.jsp">账户中心<img src="${_slpbase }/images/open-a.png"></A>|
             <!--账户展开-->
             <div class="use-hover" style=" display:none;">
                 <ul>
                     <li><A href="#">我的订单</A></li>
                     <li><A href="#">账户余额</A></li>
                     <li><A href="#">充值卡券</A></li>
                     <li><A href="#">收藏夹</A></li>
                     <li><A href="#">通讯录</A></li>
                     <li><A href="${_base}/user/security/securitySettings">安全设置</A></li>
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
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/top/top', function (TopPager) {
					pager = new TopPager({
						element: document.body
					});
					pager.render();
				});
			})();
			
</script>