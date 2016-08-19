<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase }/styles/index.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/banner.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

</head>
<style type="text/css">
/**登录banner**/
.logo-banner-big{ width:100%; float:left; height:456px; background:url(theme/slp/images/bj1.png) no-repeat; min-width:1200px;}
.banner{ width:1200px; margin:0 auto; background:url(theme/slp/images/logo-banner.png) no-repeat; height:456px;}
.hero-gallery__cell {
  width: 100%; min-width:1200px;
 height: 460px;
  color: white;
  background: url(resources/slpmall/images/bj1.png) no-repeat;
  
}
.hero-gallery__cell--2 {
  background: url(resources/slpmall/images/bj2.png) no-repeat;
}
</style>
<body>
 <!--顶部菜单-->
 <%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
<div class="mainbav-bj">
 <div class="mainbav">
    <!-- 主导航 -->
    <%@ include file="/inc/logo-nav-menu.jsp" %>
    <!-- 结束 -->
   <!--banner区悬浮内容-->
   <div class="">        
    <!--banner 左侧-->
    <div class="banner-left">
        <ul>
            <li class="Mobile"><a href="#"><img src="${_slpbase }/images/left-a.png"></a>
            <!--弹出-->
             <div class="Mobile-hover" style="display:none;" id="cmccShowId">
                 <ul>
                	   <p>充话费</p>
                     <li class="current"><A href="#" onclick="pager._jumpToSearch('100004','10000010010000')">10元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100005','10000010010000')">20元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100006','10000010010000')">30元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100007','10000010010000')">50元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100008','10000010010000')">100元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100009','10000010010000')">200元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100010','10000010010000')">300元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100011','10000010010000')">500元</A></li>
                 </ul>
                  <ul>
                	   <p>充流量</p>
                     <li ><A href="javascript:void(0);" onclick="pager._jumpToSearch('100047','10000010020000')">10MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100048','10000010020000')">20MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100049','10000010020000')">50MB</A></li>
                     <li><A href="javascript:void(0);"onclick="pager._jumpToSearch('100050','10000010020000')">70MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100051','10000010020000')">100MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100052','10000010020000')">200MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100053','10000010020000')">250MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100054','10000010020000')">500MB</A></li>
                 </ul>
             </div>
            <!---->
            </li>
            <li  class="Unicom"><a href="#"><img src="${_slpbase }/images/left-b.png"></a>
             <!--弹出-->
             <div class="Unicom-hover" style="display:none;" id="ctccShowId">
                  <ul>
                	   <p>充话费</p>
                     <li class="current"><A href="#" onclick="pager._jumpToSearch('100004','10000010010000')">10元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100005','10000010010000')">20元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100006','10000010010000')">30元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100007','10000010010000')">50元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100008','10000010010000')">100元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100009','10000010010000')">200元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100010','10000010010000')">300元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100011','10000010010000')">500元</A></li>
                 </ul>
                  <ul>
                	   <p>充流量</p>
                     <li ><A href="javascript:void(0);" onclick="pager._jumpToSearch('100047','10000010020000')">10MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100048','10000010020000')">20MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100049','10000010020000')">50MB</A></li>
                     <li><A href="javascript:void(0);"onclick="pager._jumpToSearch('100050','10000010020000')">70MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100051','10000010020000')">100MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100052','10000010020000')">200MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100053','10000010020000')">250MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100054','10000010020000')">500MB</A></li>
                 </ul>
             </div>
            <!---->
            </li>
            <li class="telecom"><a href="#"><img src="${_slpbase }/images/left-c.png"></a>
             <!--弹出-->
             <div class="telecom-hover" style="display:none;"  id="cuccShowId">
                  <ul>
                	   <p>充话费</p>
                     <li class="current"><A href="#" onclick="pager._jumpToSearch('100004','10000010010000')">10元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100005','10000010010000')">20元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100006','10000010010000')">30元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100007','10000010010000')">50元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100008','10000010010000')">100元 </A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100009','10000010010000')">200元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100010','10000010010000')">300元</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100011','10000010010000')">500元</A></li>
                 </ul>
                  <ul>
                	   <p>充流量</p>
                     <li ><A href="javascript:void(0);" onclick="pager._jumpToSearch('100047','10000010020000')">10MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100048','10000010020000')">20MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100049','10000010020000')">50MB</A></li>
                     <li><A href="javascript:void(0);"onclick="pager._jumpToSearch('100050','10000010020000')">70MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100051','10000010020000')">100MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100052','10000010020000')">200MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100053','10000010020000')">250MB</A></li>
                     <li><A href="javascript:void(0);" onclick="pager._jumpToSearch('100054','10000010020000')">500MB</A></li>
                 </ul>
             </div>
            <!---->
            
            </li>
        </ul>
    </div>
    <!--banner 右侧-->
    <div class="banner-right">
        <!--充话费-->
       <div class="fast-charge">
      
         <div class="charge-title">
               <ul>
                   <li><A href="#" id="switchFL1" class="current">充话费</A></li>
                   <li><A href="#" id="switchFL2">充流量</A></li>
               </ul>    
         </div>
          <div id="date1">
              <div class="charge-list">
              <ul>
              <li class="int-border"><input type="text" class="int-dex-none" id="phoneNum1" maxlength="11" placeholder="请输入手机号码"><span class="word" id="gsd1"></span><input type="text" id="basicOrgId1" style="display:none;"><input type="text" id="PCode" style="display:none;"></li>
              <li><select id="phoneFee" class="int-dex"></select></li>
              <li class="word">售价:<span id="realFee"></span></li>
              <li><input type="button" id="CZ_BTN" value="立即充值" class="slp-btn dex-btn">
               <a id="submitOdrBtn" style="display:none"  target="_blank"><span id="submitOdrSpan">立即充值</span></a>
              </li>
              </ul>
              </div>
              <form id="testForm" method="post" target="_blank" action="/order/toOrderPay">
              <input type="hidden" id="okey" name="orderKey" >
              </form>
          </div>
          <div id="date2" style=" display:none;">
              <div class="charge-list">
              <ul>
              <li class="int-border"><input type="text" class="int-dex-none" maxlength="11" id="phoneNum2" placeholder="请输入手机号码"><span class="word" id="gsd2"></span><input type="text" id="gbasicOrgId" style="display:none;"><input type="text" id="PCode1" style="display:none;"></li>
              <li class="congz"><p><select id="location" class="select-cz"><option value="national">全国</option><option value="local">本地</option></select></p>
              <p class="se-mar"><select id="gprs"  class="select-cz"></select></p></li>
              <li class="word">售价:<span id="realFee1"></span></li>
              <li><input type="button" id="GPRS_BTN" value="立即充值" class="slp-btn dex-btn">
              <a id="submitGpBtn" style="display:none"  target="_blank"><span id="submitGpSpan">立即充值</span></a>
              </li>
              </ul>
              </div>
          </div>
          
       </div>
    <!--公告促销-->
        <div class="notice">
           <div class="notice-title">
               <ul>
               <li class="word">公告/促销</li>
               <li class="right"><a href="#">更多</a></li>
               </ul>
           </div>
           <div class="notice-list">
           <ul>
           <li><a href="#">【促销】新年手机享5折优惠，还有礼品</a></li>
           <li><a href="#">【公告】新年发货时间安排</a></li>
           <li><a href="#">【促销】新年手机享5折优惠，还有礼品</a></li>
           <li><a href="#">【公告】新年发货时间安排</a></li>
           </ul>
           </div>
        </div>
        	
    </div>
   </div>  
    <!--banner区悬浮内容结束--> 
           
 </div>
 </div>
<!--导航区域结束-->
<div class="big-wrapper">
    <!--banner区域-->
    <div class="hero-gallery js-flickity">
              <div class="hero-gallery__cell hero-gallery__cell--1">
                <div class="content-wrap">
                  <a href="javascript:void(0);"><img src="${_slpbase }/images/img1.png"></a>
                </div>
              </div>
              <div class="hero-gallery__cell hero-gallery__cell--2">
                <div class="content-wrap">
                  <a href="javascript:void(0);"><img src="${_slpbase }/images/img2.png"></a>
                </div>
                
              </div>
              
    
        </div>
    <!--banner区域结束-->
    <div class="small-banner">
    <p><img src="${_slpbase }/images/banner-small.png"></p>
    </div>    
    
    <!--图片列表-->
    <div class="plist">
    <!--左边-->
    	<div class="plist-left">
       	 <div class="plist-left-title">话费</div>
        <div class="plist-left-list">
        <p></a><img src="${_slpbase }/images/left-img1.png"></a></p>
        <p class="mar-img"></a><img src="${_slpbase }/images/left-img2.png"></a></p>
        </div>
        </div>
        
        <div class="plist-right">
        
       	 <!--  <div class="plist-right-title">
        <ul>
        	<li><a href="javascript:void(0);" opratorid="10" class="current" id="phoneBillCmcc" >中国移动</a></li>         
        	<li><a href="javascript:void(0);" opratorid="11" id="phoneBillCtcc">中国电信</a></li>
         	<li>
         		<a href="javascript:void(0);" opratorid="12" id="phoneBillCucc">中国联通</a>
         		<input type="hidden" id="phoneOprator">
         	</li>
        </ul>
        </div>-->
        <!--table1-->
        <div class="plist-right-title">
        <ul>
        	
        </ul>
        </div>
        <div>
            <div class="plist-right-list" id="phoneBillData">
           
            </div>
        </div>
        <!--table2-->
    </div>
</div>

  <!--图片列表-->
    <div class="plist">
    <!--左边-->
    	<div class="plist-left">
       	 <div class="plist-left-title">流量</div>
        <div class="plist-left-list">
        <p><A href="#"><img src="${_slpbase }/images/left-img3.png"></a></p>
        <p class="mar-img"><A href="#"><img src="${_slpbase }/images/left-img4.png"></a></p>
        </div>
        </div>
        
        <div class="plist-right">
        
       	 <!--  <div class="plist-right-title-tow">
        <ul>
        <li><a href="javascript:void(0);" opratorid="10" class="current" id="flowCmcc">中国移动</a></li>         
        <li><a href="javascript:void(0);" opratorid="11" id="flowCtcc">中国电信</a></li>
        <li>
        	<a href="javascript:void(0);" opratorid="12" id="flowCucc">中国联通</a>
        	<input type="hidden" id="flowOprator">
        </li>
        </ul>
        </div>-->
        <!--table1-->
        <div class="plist-right-title-tow">
        <ul>
        
        </ul>
        </div>
        
        <div>
            <div class="plist-right-list" >
            <div id="flowData">
            	
            </div>
			<a href="javascript:void(0);">
                <ul id="moreId">
                <li class="tit1">浏览更多</li>
                <li class="ash">热门</li>
                <li class="dred"><img src="${_slpbase }/images/tiaoz.png" id="moreproduct"></li> 
                </ul>
                </a>
            </div>
        </div> 
    </div>
</div>
 
 <!--推荐-->
     <div class="recommend">
          <div class="recommend-title">
          <ul>
          <li class="word">为您推荐</li>
          <li class="right"><A href="javascript:void(0);" id="refresh">刷新<i class="icon-refresh"></i></A></li>
          </ul>
          </div>
          <div class="recommend-list" id="hotData">
          
          </div>
     
     </div>

   <!--底部-->
	<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
   
   
</div>
 <script id="phoneBillTmpl" type="text/x-jsrender">
				<a href="javascript:void(0);">
                	<ul onclick="pager._detailPage('{{:skuId}}')">
                		<li onclick="pager._detailPage('{{:skuId}}')"><img src="{{:picUrl}}"></li>
                		<li class="tit" onclick="pager._detailPage('{{:skuId}}')">{{:prodName}}</li>
                		<li class="dred">{{:~liToYuan(salePrice)}}元</li> 
                	</ul>
                </a>
</script>
<script id="hotTmpl" type="text/x-jsrender">
         {{if #index%4==0}}
				<a href="javascript:void(0);" class="mar-none">
                	<ul onclick="pager._detailPage('{{:skuId}}')">
                		<li class="word" onclick="pager._detailPage('{{:skuId}}')">{{:prodName}}</li>
          				<li class="dred">¥{{:~liToYuan(salePrice)}}</li>
          				<li onclick="pager._detailPage('{{:skuId}}')"><img src="{{:picUrl}}"></li> 
                	</ul>
                </a>
		{{else}}
				<a href="javascript:void(0);">
                	<ul onclick="pager._detailPage('{{:skuId}}')">
                		<li class="word" onclick="pager._detailPage('{{:skuId}}')">{{:prodName}}</li>
          				<li class="dred">¥{{:~liToYuan(salePrice)}}</li>
          				<li onclick="pager._detailPage('{{:skuId}}')"><img src="{{:picUrl}}"></li> 
                	</ul>
                </a>
		{{/if}}
</script>
<script id="flowTmpl" type="text/x-jsrender">
				<a href="javascript:void(0);">
                	<ul onclick="pager._detailPage('{{:skuId}}')">
                		<li onclick="pager._detailPage('{{:skuId}}')"><img src="{{:picUrl}}"></li>
                		<li class="tit" onclick="pager._detailPage('{{:skuId}}')" >{{:prodName}}</li>
                		<li class="dred">{{:~liToYuan(salePrice)}}元</li> 
                	</ul>
                </a>
</script>
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/producthome/productHome', function (ProductHomePager) {
					pager = new ProductHomePager({element: document.body});
					pager.render();
				});
			})();
			
		</script>
</body>
</html>


