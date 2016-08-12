<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="/inc/inc.jsp"%>
<title>搜索结果</title>

<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script src="${_slpbase }/scripts/imgloop.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css"> 
  
</head>

<body>
 <!--顶部菜单-->
 <%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
<div class="mainbav-bj">
 <div class="mainbav">
      <!--导航 搜索区-->
    <%@ include file="/inc/logo-nav-menu.jsp" %>
    <!-- 结束 -->
 </div>
 </div>
<!--导航区域结束-->
     
     <!--搜索结果-->
     <div class="fsast-charge"><!--外侧-->
        <div class="big-wrapper"><!--内侧居中框架--> 
            <div id="test" class="payment-title">
                <p><a href="#">全部商品</a>></p>
                <p><a href="#" id="typeTitleId">话费充值</a>></p>
               <p ><a href="#" id="areaTile"></a></p> 
              <!-- <span id="areaTile"></span> -->
            </div>
        </div>
         <!--搜索结果查询条件-->
        <div class="search-wrapper" >
            <div class="big-wrapper"><!--内侧居中框架--> 
            	<input type="hidden"  id="isHaveDataFlag" />
                <input type="hidden" name="priceId" id="priceId" value="${requestScope.priceId}"/>
             	<input type="hidden" name="billType" id="billType" value="${requestScope.billType}"/>
             	<input type="hidden" name="orgired" id="orgired" value="${requestScope.orgired}"/>
             	<input type="hidden" name="skuName" id="skuName" value="${requestScope.skuName}"/>
             	<input type="hidden" name="sourceFlag" id="sourceFlag" value="${requestScope.sourceFlag}"/>
                <!-- 公共查询条件默认数据 start-->
                <input type="hidden" id="agentSearch" value=""/>
                <input type="hidden" id="areaSearch" value=""/>
                <input type="hidden" id="priceSearch" value=""/>
                <!-- 公共查询条件数据 end-->
                <div class="search-main"   id="commonId">
                     <ul>
                         <li class="word">运营商:</li>
                         <li id="agentData">
                         <script id="agentTmpl" type="text/x-jsrender">
							{{for agentList}}
						 		<p id="{{:attrDefId}}"><A href="javascript:void(0);" onclick="pager._changeAgent('{{:attrDefId}}','{{:attrDefValue}}')"  value="{{:attrDefId}}">{{:attrDefValue}}</A></p>
							{{/for}}
							</script>
                         </li>
                     </ul>
                       <ul>
                         <li class="word" id="xsWord">面额:</li>
                         <li id="accountData">
                         <script id="accountTmpl" type="text/x-jsrender">
							{{for accountList}}
									<p id="{{:attrDefId}}"><A href="javascript:void(0);" onclick="pager._changePrice('{{:attrDefId}}','{{:attrDefValue}}')" value="{{:attrDefId}}">{{:attrDefValue}}</A></p>
							{{/for}}
							</script>
                         </li>
                     </ul>
                      <ul>
                         <li class="word">地区:</li>
                         <li class="word-height" id="areaData">
                          <script id="areaTmpl" type="text/x-jsrender">
							{{for areaList}}
								{{if #index<17}}
										<p id="{{:attrDefId}}"><A href="javascript:void(0);" onclick="pager._changeArea('{{:attrDefId}}','{{:attrDefValue}}')" value="{{:attrDefId}}">{{:attrDefValue}}</A></p>
								{{/if}}
								{{if #index==17}}
									<p class="more"><A href="javascript:void(0);" id="moreId">更多<i class="icon-angle-down"></i></A></p>
								{{/if}}
							{{/for}}
						</script>
                          </li>
                          <div class="more-ctn" style=" display:none;" id="lastArea">
                           <li class="word-height" id="lastAreaData">
                           	<script id="lastAreaTmpl" type="text/x-jsrender">
							{{for areaList}}
								{{if #index>=17}}	
									<p id="{{:attrDefId}}"><A onclick="pager._changeArea('{{:attrDefId}}','{{:attrDefValue}}')" href="javascript:void(0);" value="{{:attrDefId}}">{{:attrDefValue}}</A></p>
								{{/if}}
							{{/for}}
						</script>
                          </li></div>
                     </ul>
                    
                </div>
            
            </div>
        </div>
        
        <div class="fsast-charge"><!--外侧-->
      	  <div class="big-wrapper"><!--内侧居中框架-->    
        <!--筛选结果--> 
       		 <div class="screening-results">
      			 <div class="results-left" id="commonData">
                   <div class="results-left-title">
                       <ul>
                           <li class="color" id="zhOrderId"><a href="javascript:void(0);" id="generayId">综合排序</a></li>
                           <li><a href="javascript:void(0);" id="saleOrder" value="" onclick="pager._changeSaleOrder()">销量<img src="${_slpbase }/images/x.png" id="saleNumX"></a></li>
                           <!--  <li><a href="javascript:void(0);">评论量</a></li>-->
                           <li id="xpriceId"><a href="javascript:void(0);" id="priceOrder" value="" onclick="pager._changePriceOrder()">价格<img src="${_slpbase }/images/x.png" id="priceX"></a></li>
                           <li class="decline" style="display:none;" id="spriceId"><a href="javascript:void(0);" onclick="pager._changePriceOrder()">价格<img src="${_slpbase }/images/s.png" id="priceS"></a></li>
                       </ul>
                   </div>
                    <div class="results-left-city">
                         <ul>
                         <li>配送至:</li>
                             <li class="city"><a href="#" id="currentDispatch">北京<img src="${_slpbase }/images/open-a.png"></a>
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
                                            <ul class="city-list" id="dispatchCityShowData">
                                           <script id="dispatchCityTmpl" type="text/x-jsrender">
												<li><A href="javascript:void(0)" areaCodeId="{{:areaCode}}" areaNameId="{{:areaName}}"class="DSP_BTN">{{:areaName}}</A></li>
					   						</script>
                                          </ul>
                                         
                                          <!-- <ul class="city-list">
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100013','北京')">北京</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100021','上海')">上海</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100014','天津')">天津</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100034','重庆')">重庆</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100015','河北')">河北</A></li> 
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100016','山西')">山西</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100028','河南')">河南</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100018','辽宁')">辽宁</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100019','吉林')">吉林</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100020','黑龙江')">黑龙江</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100017','内蒙古')">内蒙古</A></li> 
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100022','江苏')">江苏</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100027','山东')">山东</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100024','安徽')">安徽</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100023','浙江')">浙江</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100025','福建')">福建</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100029','湖北')">湖北</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100031','广东')">广东</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100033','海南')">海南</A></li> 
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100035','四川')">四川</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100036','贵州')">贵州</A></li>  
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100037','云南')">云南</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100038','西藏')">西藏</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100039','陕西')">陕西</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100040','甘肃')">甘肃</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100041','青海')">青海</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100042','宁夏')">宁夏</A></li> 
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100043','新疆')">新疆</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100044','台湾')">台湾</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100045','香港')">香港</A></li>
                                              <li><A href="javascript:void(0)" onclick="pager._changeDispath('100046','澳门')">澳门</A></li>                           
                                          </ul> -->
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
                            		<li class="img"><a onclick="pager._detailPage('{{:skuId}}')" href="javascript:void(0)" ><img src="{{:picUrl}}"></a></li>
                            		<li class="word"><a onclick="pager._detailPage('{{:skuId}}')" href="javascript:void(0)" >{{:prodName}}</a> </li>
                            		<li class="left"><span>￥{{:~liToYuan(salePrice)}}</span></li>
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
                            		<a onclick="pager._detailPage('{{:skuId}}')" href="javascript:void(0)"><img src="{{:picUrl}}"  class="jqzoom" id="bigPic"/></a>
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
               						<li class="word">¥{{:~liToYuan(salePrice)}}</li>
               						<li>
										<a  href="javascript:void(0)" onclick="pager._detailPage('{{:skuId}}')">{{:prodName}}</a>
										<input type="hidden" id="catType" value="{{:productCatId}}">
									</li>
               					</ul>
               				</div>
						</div>
					</div>
						   
	    </script>
      </div>
        <!--分页-->
          
          <div style="text-align: right">
			 <ul id="pagination-ul"></ul>
		  </div>
			 <!--分页-->
        </div>
       		</div>
        </div>
     
     </div>
   <!--底部-->
    <%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/product/searchProduct', function (QueryProductPager) {
					pager = new QueryProductPager({element: document.body});
					pager.render();
				});
			})();
		</script>
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
