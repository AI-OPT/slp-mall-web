<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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