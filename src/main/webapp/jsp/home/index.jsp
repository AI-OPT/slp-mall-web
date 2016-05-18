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
</head>

<body>
 <!--顶部菜单-->
 <%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
<div class="mainbav-bj">
 <div class="mainbav">
      <div class="logo"><img src="${_slpbase }/images/logo.png"></div>
      <!--导航 搜索区-->
      <div class="mainbav-main">
      <!--搜索区-->
          <div class="searchBar">
              <ul class="searchTxt">
                  <li><input type="text" class="int-xxlarge"></li>
                  <li><A href="#"><i class="icon-search"></i></A></li>
              </ul>
               <ul class="word">
                  <li><A href="#">搜索商品1</A></li>
                  <li><A href="#">搜索商品2</A></li>
                  <li><A href="#">搜索商品3</A></li>
                  <li><A href="#">搜索商品4</A></li>   
              </ul>
          </div>
          <!--搜索区结束-->
          <!--主导航-->
          <div class="breadcrumb">
              <ul>
                  <li><a href="#">首页</a></li>
                  <li><a href="#">话费快充</a></li>
                  <li><a href="#">流量快充</a></li>
                  <li><a href="#">话费卡</a></li>
                  <li><a href="#">流量卡</a></li>
                  <li><a href="#">API</a></li>
              </ul>
          </div>
          <!--主导航结束-->
           </div>
   <!--banner区悬浮内容-->
   <div class="">        
    <!--banner 左侧-->
    <div class="banner-left">
        <ul>
            <li class="Mobile"><a href="#"><img src="${_slpbase }/images/left-a.png"></a>
            <!--弹出-->
             <div class="Mobile-hover" style="display:none;">
                 <ul>
                	   <p>充话费</p>
                     <li class="current"><A href="#">10元 </A></li>
                     <li><A href="#">20元</A></li>
                     <li><A href="#">30元 </A></li>
                     <li><A href="#">50元</A></li>
                     <li><A href="#">100元 </A></li>
                     <li><A href="#">200元</A></li>
                     <li><A href="#">300元</A></li>
                     <li><A href="#">500元</A></li>
                 </ul>
                  <ul>
                	   <p>充流量</p>
                     <li><A href="#">10元 </A></li>
                     <li><A href="#">20元</A></li>
                     <li><A href="#">30元 </A></li>
                     <li><A href="#">50元</A></li>
                     <li><A href="#">100元 </A></li>
                     <li><A href="#">200元</A></li>
                     <li><A href="#">300元</A></li>
                     <li><A href="#">500元</A></li>
                 </ul>
             </div>
            <!---->
            </li>
            <li  class="Unicom"><a href="#"><img src="${_slpbase }/images/left-b.png"></a>
             <!--弹出-->
             <div class="Unicom-hover" style="display:none;">
                 <ul>
                	   <p>充话费</p>
                     <li class="current"><A href="#">10元 </A></li>
                     <li><A href="#">20元</A></li>
                     <li><A href="#">30元 </A></li>
                     <li><A href="#">50元</A></li>
                     <li><A href="#">100元 </A></li>
                     <li><A href="#">200元</A></li>
                     <li><A href="#">300元</A></li>
                     <li><A href="#">500元</A></li>
                 </ul>
                  <ul>
                	   <p>充流量</p>
                     <li><A href="#">10元 </A></li>
                     <li><A href="#">20元</A></li>
                     <li><A href="#">30元 </A></li>
                     <li><A href="#">50元</A></li>
                     <li><A href="#">100元 </A></li>
                     <li><A href="#">200元</A></li>
                     <li><A href="#">300元</A></li>
                     <li><A href="#">500元</A></li>
                 </ul>
             </div>
            <!---->
            </li>
            <li class="telecom"><a href="#"><img src="${_slpbase }/images/left-c.png"></a>
             <!--弹出-->
             <div class="telecom-hover" style="display:none;">
                 <ul>
                	   <p>充话费</p>
                     <li class="current"><A href="#">10元 </A></li>
                     <li><A href="#">20元</A></li>
                     <li><A href="#">30元 </A></li>
                     <li><A href="#">50元</A></li>
                     <li><A href="#">100元 </A></li>
                     <li><A href="#">200元</A></li>
                     <li><A href="#">300元</A></li>
                     <li><A href="#">500元</A></li>
                 </ul>
                  <ul>
                	   <p>充流量</p>
                     <li><A href="#">10元 </A></li>
                     <li><A href="#">20元</A></li>
                     <li><A href="#">30元 </A></li>
                     <li><A href="#">50元</A></li>
                     <li><A href="#">100元 </A></li>
                     <li><A href="#">200元</A></li>
                     <li><A href="#">300元</A></li>
                     <li><A href="#">500元</A></li>
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
                   <li><A href="#" class="current">充话费</A></li>
                   <li><A href="#">充流量</A></li>
               </ul>    
         </div>
          <div id="date1">
              <div class="charge-list">
              <ul>
              <li><input type="text" class="int-dex" placeholder="请输入手机号码"></li>
              <li><select class="int-dex"></select></li>
              <li class="word">售价:<span>¥29.5-¥30</span></li>
              <li><input type="button" value="立即充值" class="slp-btn dex-btn"></li>
              </ul>
              </div>
          </div>
          <div id="date2" style=" display:none;">
              <div class="charge-list">
              <ul>
              <li><input type="text" class="int-dex" placeholder="请输入手机号码"></li>
              <li class="congz"><p><select class="select-cz"></select></p><p class="se-mar"><select  class="select-cz"></select></p></li>
              <li class="word">售价:<span>¥29.5-¥30</span></li>
              <li><input type="button" value="立即充值" class="slp-btn dex-btn"></li>
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
                  <a href="#"><img src="${_slpbase }/images/img1.png"></a>
                </div>
              </div>
              <div class="hero-gallery__cell hero-gallery__cell--2">
                <div class="content-wrap">
                  <a href="#"><img src="${_slpbase }/images/img2.png"></a>
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
        
       	 <div class="plist-right-title">
        <ul>
        <li><a href="javascript:void(0);" class="current">快充</a></li>         
        <li><a href="javascript:void(0);">话费卡</a></li>
        </ul>
        </div>
        <!--table1-->
        <div id="img-list1">
            <div class="plist-right-list">
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
            </div>
        </div>
        <!--table2-->
        <div id="img-list2" style=" display:none;">
            <div class="plist-right-list">
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量501G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
            </div>
        </div>
        
    
    
    
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
        
       	 <div class="plist-right-title-tow">
        <ul>
        <li><a href="javascript:void(0);" class="current">快充</a></li>         
        <li><a href="javascript:void(0);">话费卡</a></li>
        </ul>
        </div>
        <!--table1-->
        <div id="img-list3">
            <div class="plist-right-list">
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量20G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
              	 <a href="#">
                <ul>
                <li class="tit1">浏览更多</li>
                <li class="ash">热门</li>
                <li class="dred"><img src="${_slpbase }/images/tiaoz.png"></li> 
                </ul>
                </a>
            </div>
        </div>
        <!--table2-->
        <div id="img-list4" style=" display:none;">
            <div class="plist-right-list">
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量120G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li><img src="${_slpbase }/images/ka.png"></li>
                <li class="tit">联通上网卡季卡</li>
                <li class="ash">本地流量50G</li>
                <li class="dred">120元</li> 
                </ul>
                </a>
                <a href="#">
                <ul>
                <li class="tit1">浏览更多</li>
                <li class="ash">热门</li>
                <li class="dred"><img src="${_slpbase }/images/tiaoz.png"></li> 
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
          <li class="right"><A href="#">刷新<i class="icon-refresh"></i></A></li>
          </ul>
          </div>
          <div class="recommend-list">
          <a href="#" class="mar-none">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#" class="mar-none">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          <a href="#">
          <ul>
          <li class="word">TP-link300M无线路由器</li>
          <li class="ash">简单实现无线桥接、中继，扩展无线覆盖</li>
          <li class="dred">¥99</li>
          <li><img src="${_slpbase }/images/imga.png"></li>
          </ul>
          </a>
          </div>
     
     </div>

   <!--底部-->
	<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
   
   
</div>


</body>
</html>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

