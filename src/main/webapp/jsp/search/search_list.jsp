<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<title>搜索结果</title>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/imgloop.js" type="text/javascript"></script>
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/product/searchProduct', function (QueryProductPager) {
					pager = new QueryProductPager({element: document.body});
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
<div class="mainbav-bj">
 <div class="mainbav">
      <div class="logo"><img src="${_slpbase }/images/logo.png"></div>
      <!--导航 搜索区-->
    <%@ include file="/inc/logo-nav-menu.jsp" %>
    <!-- 结束 -->
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
                <input type="hidden" name="priceId" id="priceId" value="${requestScope.priceId}"/>
             	<input type="hidden" name="billType" id="billType" value="${requestScope.billType}"/>
             	<input type="hidden" name="orgired" id="orgired" value="${requestScope.orgired}"/>
                <div class="search-main">
                     <ul>
                         <li class="word">运营商:</li>
                         <li>
                             <p class="current"><A href="#" value="10" id="cmccId">中国移动</A></p>
                             <p><A  href="#" value="11" id="ctccId">中国联通</A></p>
                             <p><A  href="#" value="12" id="cuccId">中国电信</A></p>
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
                       <li>共<span id="totalcount"></span>件商品</li>
                       <li><A href="#">&lt;</A></li>
                       <li><span id="pageno"></span>/<span id="pagecount"></span></li>
                       <li><A href="#">&gt;</A></li>
                   </ul>
               </div>
        </div>
        
        <div class="big-wrapper"><!--白色背景-->
          <!--热销产品左侧-->
          <div class="product-list-left">
              <div class="parameter-left-tow">
                     <div class="parameter-left-tow-title"><p>热销推荐</p></div>
                        <div class="left-tow-list" id="hotProductData">
                         <script id="hotProductListTmpl" type="text/x-jsrender">
							<div class="left-tow-list">
								<ul>
                            		<li class="img"><a href="#"><img src="{{:picUrl}}"></a></li>
                            		<li class="word"><a href="#">{{:prodName}}</a> </li>
                            		<li class="left"><span>￥{{:~liToYuan(salePrice)}}</span><a href="#" class="pj">{{:commentIdCount}}评价</a></li>
                        		</ul>
							</div>
						</script>
                        </div>
                  </div>
          
          </div>
          <!--热销产品右侧-->
           <div class="product-list-right">
           <div class="big-list" id="productData">
           <!--图片列表块-->
		    </div>
		    <script id="productListTemple" type="text/template">
						<div class="single">
               				<div class="single-top">
							 <div class="picture-carousel">
                    			<div class="tb-booth tb-pic tb-s310">
                            		<a href="#"><img src="{{:picUrl}}"  class="jqzoom" id="bigPic"/></a>
                        		</div>
                        		<ul class="tb-thumb" id="thumblist">
									{{for thumnailUrl}}
										{{if #index==0}}
                            				<li class="tb-selected"><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img src="{{:#data}}" name="image"id="crruntImageId"></a></div></li>
                            			{{else}}
											<li><div class="tb-pic tb-s40"><a href="javascript:void(0);"><img  src="{{:#data}}" name="image" id="thumbnailId"></a></div></li>
										{{/if}}
									{{/for}}                       		
								</ul>
                   			</div>
							<div class="single-word">
               					<ul>
               						<li class="word">¥{{:~liToYuan(salePrice)}}<a href="#" class="pj">{{:commentIdCount}}评价</a></li>
               						<li><a href="#">{{:prodName}}</a></li>
               					</ul>
               				</div>
						</div>
					</div>
						   
	    </script>
           
         <!--分页-->
          
          <div class="paging-large">
			 <ul id="pagination-ul"></ul>
		  </div>
			 <!--分页-->
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

<script type="text/javascript">
$(document).ready(function(){
	
	$("#thumblist li a").click(function(){
		$(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
		$(this).parents().parents().children().children().children('.jqzoom').attr('src',$(this).find("img").attr("src"));
	});

});
</script>
