<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>商品详情</title>
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
     
     <!--商品详情-->
<div class="fsast-charge">
     	<div class="big-wrapper"><!--内侧居中框架-->
       		<div class="payment-title">
                <p><a href="#">一级分类</a>></p>
                <p><a href="#">二级分类</a>></p>
                <p><a href="#">三级分类</a>></p>
                <p>中国移动充值卡</p>
            </div>
         <div class="recharge-bj-tow"><!--白色背景-->
         <!--商品详情-->
         <!--商品详情左侧 轮播-->
     <div class="left-effect">
                     <div class="carousel-left">
                         <div id="picarea">
                           <div id="bigpicarea"></div>
                           <script id="bigImageTemple" type="text/template">
								{{if #index<9}}
								<div id="image_xixi-0{{: #getIndex()+1}}" class="image"><a href="#"><img alt="" src="{{:bigImageUrl}}" width="360" height="457"></a><div class="word"></div>
                            	</div>
								{{else}}
								<div id="image_xixi-{{: #getIndex()+1}}" class="image"><a href="#"><img alt="" src="{{:bigImageUrl}}" width="360" height="457"></a><div class="word"></div>
                            	</div>
								{{/if}}
						   </script>
						 </div>
                         <div id="smallpicarea">
                            <div id="thumbs">
                                <ul>
                                    <li class="first btnPrev"><i id="play_prev" class="icon-angle-left"></i></li>
                                	<div id="smallImageData"></div>
                                    <li class="last btnNext"><i id="play_next" class="icon-angle-right"></i></li>
                                </ul>
                                <script id="smallImageTemple" type="text/template">
								{{if #index<9}}
								<li class="slideshowItem"><a id="thumb_xixi-0{{: #getIndex()+1}}" href="javascript:"><img src="{{:smallImageUrl}}"></a></li>
						   		{{else}}
								<li class="slideshowItem"><a id="thumb_xixi-{{: #getIndex()+1}}" href="javascript:"><img src="{{:smallImageUrl}}"></a></li>	
								{{/if}}
								</script>
                            </div>
                        </div>
                   </div>
                   
                   <div class="collection">
                   <p>商品ID：${skuId} </p>
                   <!-- 保存当前商品的SKU标识的隐藏 -->
                   <input type="hidden" id="skuId" value="788964678909"/>
                   <p><a href="#"><i class="icon-heart-empty"></i>收藏</a></p>
                   </div>
                 </div>
                <!--商品详情右侧-->
      			<div class="details">
      			 <div id="producSKUData"></div>
      			 <script id="producSKUTemple" type="text/template">
                   <ul class="details-title">
                       <li class="word">{{:productName}}</li>
                       <li class="color">{{:productSellPoint}}</li>
                   </ul>
                   <ul class="details-list">
                       <li class="word">价格：</li>
                       <li class="color">￥{{:salePrice}}元</li>
                   </ul>
                   <ul class="details-list">
                       <li class="word">所在地：</li>
                       <li><select class="details-large"><option>北京市海淀区</option></select></li>
                   </ul>
                    <ul class="details-list" id="activeDateDiv">
                       <li class="word">有效期：</li>
                       <li id="activeDate"></li>
                    </ul>
					{{for productAttrList}}
						<ul class="details-list">
					   		<li class="word">{{:attrName}}：</li>
                       		<li class="attribute">
							{{for attrValueList}}
                           		<p>
									{{if isOwn}}
										<a href="#" class="current">
									{{else}}
                               			<a href="#">
									{{/if}}
									{{if imageUrl != null}}
                               		<span><img src="{{:imageUrl}}"></span>
									{{/if}}
                               		<span>{{:attrValueName}}</span>
                               		</a>
                           		</p>
							{{/for}}
                        	</li>
                   	 </ul>
					{{/for}}
					 <ul class="details-list">
                       <li class="word">购买数量：</li>
                       <li class="numbe">
                           <p><input id="delQtyBtn" type="button" class="details-jia" value="-"></p>
                           <p><input id="productQty" type="text" class="details-int" value="1"></p>
                           <p><input id="addQtyBtn"type="button" class="details-jia" value="+"></p>
                       </li>
                   	</ul>
                   	<ul class="details-list">
                       <li class="word">销量：</li>
                       <li>{{:saleNum}}</li>
                       <li class="right"><span class="word1">评价：</span><span>{{:commentNum}}</span></li>
                   	</ul>
					</script>
                   <ul class="details-list btm-magin">
                   <li class="btn-mar" id="buyBtnId"><input type="button" class="slp-btn details-btn" value="立即购买"></li>
                   <li id="addCarBtnId"><input type="button" class="slp-btn details-btn" id="joinShopCart" value="加入购物车"></li>
                   <li id="invalidBtnId" ><input type="button" class="slp--ash-btn" value="已下架"></li>
                   </ul>
               
               </div>

         </div>
		<div class="recharge-bj-tow recharge-bj-none"><!--白色背景-->
       			<div class="details-banner"><img src="${_slpbase }/images/sp-banner.png"></div>
       </div>
       
       <!--商品详情－详情参数-->
       <div class="details-parameter">
       <!--左侧-->
       <div class="parameter-left">
             <div class="parameter-left-none"><img src="${_slpbase }/images/left-1.png"></div>
             <div class="parameter-left-tow">
                 <div class="parameter-left-tow-title"><p>热销推荐</p></div>
                 	 <div class="left-tow-list" id="hotProductData">
                         <script id="hotProductListTmpl" type="text/x-jsrender">
							<div class="left-tow-list">
								<ul>
                            		<li class="img"><a href="#"><img src="{{:pictureUrl}}"></a></li>
                            		<li class="word"><a href="#">{{:skuName}}</a> </li>
                            		<li class="left"><span>￥{{:salePrice}}</span><a href="#" class="pj">{{:commentIdCount}}评价</a></li>
                        		</ul>
							</div>
						</script>
                        </div>
              </div>   
       </div>
       <!--左侧-结束-->
       <!--右侧-->
        <div class="parameter-right">
              <div class="parameter-right-tilit">
                  <ul>
                      <li class="current">商品详情</li>
                      <li>规格参数</li>
                      <li>商品评价</li>
                  </ul>                  
              </div>
              <div id="date1">
                  <div class="commodity-word">
                      <ul>
                          <li>
                              <p>运营商:</p>
                              <p>中国移动</p>
                          </li>
                           <li>
                              <p>归属地:</p>
                              <p>北京</p>
                          </li>
                           <li>
                              <p>充值面额:</p>
                              <p>50元</p>
                          </li>
                           <li>
                              <p>有效期:</p>
                              <p>2016-5-8 10:30</p>
                          </li>
                           <li>
                              <p>充值方式:</p>
                              <p>自动快充</p>
                          </li>
                      </ul>
                      
                      <ul>
                      <li class="worde"><a href="#">更多参数</a></li>
                      </ul>
                  </div>
                   
                  <div class="commodity-list">
                      <p><A href="#"><img src="${_slpbase }/images/parameter-a.png"></A></p>
                      <p><img src="${_slpbase }/images/parameter-b.png"></p>
                      <p><img src="${_slpbase }/images/parameter-c.png"></p>
                      <p><img src="${_slpbase }/images/parameter-d.jpg"></p>
                      <p><img src="${_slpbase }/images/parameter-e.png"></p>
                  </div>
        		</div>
                <div id="date2" style=" display:none;">
                   <div class="specification">
                       <ul>
                           <li>
                               <p class="word">运营商</p>
                               <p>归属地</p>
                           </li>
                           <li>
                               <p class="word">归属地</p>
                               <p>北京</p>
                           </li>
                           <li>
                               <p class="word">有效期</p>
                               <p>2016-5-8 10:30</p>
                           </li>
                           <li>
                               <p class="word">充值方式</p>
                               <p>自动快充</p>
                           </li>
                           <li>
                               <p class="word">充值面额</p>
                               <p>50元 </p>
                           </li>
                       </ul>
                   </div>
                  
                  
        		</div>
         
        </div>
       <!--右侧 结束-->
       
       
       </div> 

         </div>
     </div>

   <!--底部-->
<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
	<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
	<script src="${_slpbase }/scripts/carousel.js" type="text/javascript"></script>
	<script type="text/javascript">
	var target = ["xixi-01","xixi-02","xixi-03","xixi-04"];
	
	var pager;
	var producSKU = $.parseJSON('${productSKU}');
	var imageArrayList = $.parseJSON('${imageArrayList}');
	var activeDateValue = '${activeDateValue}';
	(function () {
		seajs.use('app/jsp/product/productDetail', function (ProductDetailPager) {
			pager = new ProductDetailPager({element: document.body});
			pager.render();
		});
	})();
	</script>
</body>
</html>
