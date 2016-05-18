<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>搜索结果</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
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
     
 </div>
 </div>
<!--导航区域结束-->
     
     <!--搜索结果-->
     <div class="fsast-charge"><!--外侧-->
        <div class="big-wrapper"><!--内侧居中框架--> 
            <div class="payment-title">
                <p><a href="#">全部商品</a>></p>
                <p><a href="#">话费充值</a>></p>
                <p><a href="#">地域:</a></p>
                <p class="close">全国通用<A href="#"><i class="icon-remove"></i></A></p>
                <p class="close">全国通用<A href="#"><i class="icon-remove"></i></A></p>
            </div>
        </div>
         <!--搜索结果查询条件-->
        <div class="search-wrapper">
            <div class="big-wrapper"><!--内侧居中框架--> 
              
                <div class="search-main">
                     <ul>
                         <li class="word">运营商:</li>
                         <li>
                             <p class="current"><A href="#">中国移动</A></p>
                             <p><A href="#">中国联通</A></p>
                             <p><A href="#">中国电信</A></p>
                         </li>
                     </ul>
                       <ul>
                         <li class="word">面额:</li>
                         <li>
                             <p class="current"><A href="#">10元</A></p>
                             <p><A href="#">20元 </A></p>
                             <p><A href="#">30元</A></p>
                             <p><A href="#">50元</A></p>
                             <p><A href="#">100元</A></p>
                             <p><A href="#">200元</A></p>
                             <p><A href="#">300元</A></p>
                             <p><A href="#">500元</A></p>
                         </li>
                     </ul>
                      <ul>
                         <li class="word">地区:</li>
                         <li class="word-height">
                             <p><A href="#">全国通用</A></p>
                             <p class="current"><A href="#">北京</A></p>
                             <p><A href="#">北京</A></p>
                             <p><A href="#">上海</A></p>
                             <p><A href="#">广州</A></p>
                             <p><A href="#">四川</A></p>
                             <p><A href="#">河北</A></p>
                             <p><A href="#">河南</A></p>
                             <p><A href="#">山东</A></p>
                             <p><A href="#">山西</A></p>
                             <p><A href="#">辽宁</A></p>
                             <p><A href="#">广西</A></p>
                             <p><A href="#">云南</A></p>
                             <p><A href="#">河北</A></p>
                             <p><A href="#">更多</A></p>
                             <p><A href="#">河南</A></p>
                             <p><A href="#">山东</A></p>
                             <p><A href="#">山西</A></p>
                             <p><A href="#">河北</A></p>
                             <p class="more"><A href="#">更多<i class="icon-angle-down"></i></A></p>
                          </li>
                          <div class="more-ctn" style=" display:none;">
                           <li class="word-height">
                             <p><A href="#">广西</A></p>
                             <p><A href="#">云南</A></p>
                             <p><A href="#">河北</A></p>
                             <p><A href="#">更多</A></p>
                             <p><A href="#">河南</A></p>
                             <p><A href="#">山东</A></p>
                             <p><A href="#">山西</A></p>
                             <p><A href="#">河北</A></p>
                              <p><A href="#">广西</A></p>
                             <p><A href="#">云南</A></p>
                             <p><A href="#">河北</A></p>
                             <p><A href="#">更多</A></p>
                             <p><A href="#">河南</A></p>
                             <p><A href="#">山东</A></p>
                             <p><A href="#">山西</A></p>
                             <p><A href="#">河北</A></p>
                              <p><A href="#">广西</A></p>
                             <p><A href="#">云南</A></p>
                             <p><A href="#">河北</A></p>
                             <p><A href="#">更多</A></p>
                             <p><A href="#">河南</A></p>
                             <p><A href="#">山东</A></p>
                             <p><A href="#">山西</A></p>
                             <p><A href="#">河北</A></p>
                          </li></div>
                     </ul>  
                      <ul class="none-border">
                         <li class="word">充值方式:</li>
                         <li>
                             <p class="current"><A href="#">自助充值</A></p>
                             <p><A href="#">卡密充值</A></p>      
                         </li>
                     </ul>
                     
                                    
                </div>
            
            </div>
        </div>
        
        <div class="fsast-charge"><!--外侧-->
      	  <div class="big-wrapper"><!--内侧居中框架-->    
        <!--筛选结果--> 
       		 <div class="screening-results">
      			 <div class="results-left">
                   <div class="results-left-title">
                       <ul>
                           <li><a href="#">综合排序</a></li>
                           <li><a href="#">销量<img src="${_slpbase }/images/s.png"></a></li>
                           <li><a href="#">评论量</a></li>
                           <li class="rise"><a href="#">价格<img src="${_slpbase }/images/s.png"></a></li>
                           <li class="decline" style="display:none;"><a href="#">价格<img src="${_slpbase }/images/x.png"></a></li>
                       </ul>
                   </div>
                    <div class="results-left-city">
                         <ul>
                         <li>所在地:</li>
                             <li class="city"><a href="#" >北京<img src="${_slpbase }/images/open-a.png"></a>
                             <!--选择所在地城市-->
                                     <div class="city-hover" style="display:none;">
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
                     </div>   
                </div> 
               <div class="results-right">
                   <ul>
                       <li>共356785件商品</li>
                       <li><A href="#">&lt;</A></li>
                       <li><span>1</span>/20</li>
                       <li><A href="#">&gt;</A></li>
                   </ul>
               </div>
        </div>
        
        <div class="big-wrapper"><!--白色背景-->
          <!--热销产品左侧-->
          <div class="product-list-left">
              <div class="parameter-left-tow">
                     <div class="parameter-left-tow-title"><p>热销推荐</p></div>
                        <div class="left-tow-list">
                         <ul>
                            <li class="img"><a href="#"><img src="${_slpbase }/images/hot-1.png"></a></li>
                            <li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2G</a> </li>
                            <li class="left"><span>￥1099.00</span><a href="#" class="pj">43435评价</a></li>
                        </ul>
                        </div>
                          <div class="left-tow-list">
                         <ul>
                            <li class="img"><a href="#"><img src="${_slpbase }/images/hot-2.png"></a></li>
                            <li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2G</a> </li>
                            <li class="left"><span>￥1099.00</span><a href="#" class="pj">43435评价</a></li>
                        </ul>
                        </div>
                          <div class="left-tow-list">
                         <ul>
                            <li class="img"><a href="#"><img src="${_slpbase }/images/hot-1.png"></a></li>
                            <li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2G</a> </li>
                            <li class="left"><span>￥1099.00</span><a href="#" class="pj">43435评价</a></li>
                        </ul>
                        </div>
                          <div class="left-tow-list border-none">
                         <ul>
                            <li class="img"><a href="#"><img src="${_slpbase }/images/hot-2.png"></a></li>
                            <li class="word"><a href="#">华为(HUAWEI) 荣耀 畅玩5X 4G手机 破晓银 移动4G版(2G</a> </li>
                            <li class="left"><span>￥1099.00</span><a href="#" class="pj">43435评价</a></li>
                        </ul>
                        </div>
                  </div>
          
          </div>
          <!--热销产品右侧-->
           <div class="product-list-right">
           <div class="big-list">
           <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
            <!--图片列表块-->
           <div class="single">
               <div class="single-top">
               <!--图片轮播-->
                   <div class="picture-carousel">
                    <div class="tb-booth tb-pic tb-s310">
                            <a href="#"><img src="${_slpbase }/images/01_mid.jpg"  class="jqzoom" /></a>
                        </div>
                    
                        <ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="${_slpbase }/images/01_mid.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="${_slpbase }/images/02_mid.jpg"></a></div></li>
                        </ul>
                   
                   </div>
               <!--文字-->
               <div class="single-word">
               <ul>
               <li class="word">¥48<a href="#" class="pj">23423评价</a></li>
               <li><a href="#">光环系列二合一数据线光环系列二合一数据线</a></li>
               </ul>
      
               </div>
               </div>
           </div>
           <!--图片列表块技术-->
  
           
           </div>
           
           <div class="" style=" width:100%; float:left;">
           <div class="paging-large">
        <ul>
            <li>共100页</li>
            <li class="prev-up"><a href="#">&lt;上一页</a> </li>
            <li class="active"> <a href="#">1 </a> </li>
            <li> <a href="#">2 </a> </li>
            <li> <a href="#">3</a> </li>
            <li><span>....</span></li>
            <li> <a href="#">6</a> </li>
            <li> <a href="#">7</a> </li>
            <li class="next-down"><a href="#">下一页&gt;</a> </li>
   		     <li>
                <span>到</span>
                <span><input type="text" class="int-verysmall"></span>
                <span>页</span>
                <span class="btn-span"><a class="but-determine">确定</a></span>
            </li>
            
         </ul>
	</div>
           
           </div>
      
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
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/imgloop.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#thumblist li a").click(function(){
		$(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
		$(this).parents().parents().children().children().children('.jqzoom').attr('src',$(this).find("img").attr("src"));
	});

});
</script>

