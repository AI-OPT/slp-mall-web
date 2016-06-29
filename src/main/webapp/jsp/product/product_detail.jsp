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

<script src="${_base}/resources/spm_modules/app/jsp/product/carousel.js" type="text/javascript"></script>
<script src="${_base}/resources/spm_modules/app/jsp/product/product.js" type="text/javascript"></script>
</head>

<body>
<!--添加购物车提示弹出框  中-->
<div id="cover" class="eject-big">
		<div id="shopCartMedium" class="eject-medium">
			<div class="eject-medium-title">
				<p>添加购物车提示</p>
				<p class="img"><A href="jacascript:voida(0)"></A></p>
			</div>
			<div class="eject-medium-list">
					<div class="eject-buy">
						<ul>
							<li class="img"><img src="${_slpbase }/images/eject-buy.png" /></li>
							<li class="word">
								<p class="color">该商品已成功加入购物车！</p>
								<p>购物车内已有<span id="cartProdTotal">10</span>件商品</p>
							</li>
						</ul>
						<ul>
							<li><a href="${_base}/shopcart/cartDetails"><input type="button" class="slp-btn eject-buy-btn"  value="购物车结算"/></a></li>
							<li class="word-eg" id="continueShoping"><a href="javascript:void(0)">继续浏览</a></li>
						</ul>
						
					</div>
			</div>	
		</div>	
		<div id="shopCartMask" class="eject-mask"></div>	
</div>
<!--添加购物车提示弹出框  中结束-->	
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
     
     <!--商品详情-->
<div class="fsast-charge">
     	<div class="big-wrapper"><!--内侧居中框架-->
       		<div class="payment-title" id="productCatList">
            </div>
         <div class="recharge-bj-tow"><!--白色背景-->
         <!--商品详情-->
         <!--商品详情左侧 轮播-->
     <div class="left-effect">
                   <div class="carousel-left" id="productImageData">
                   </div>
                   <script id="productImageTemple" type="text/x-jsrender">
                         <div id="picarea">
                           <div id="bigpicarea">
							 {{for bigImagesUrl}}
								<div id="image-{{: #getIndex()+1}}" class="image"><a href="javascript:void(0)"><img alt="" src="{{:#data}}" width="360" height="457"></a><div class="word"></div>
                            	</div>
							 {{/for}}
						   </div>
						 </div>
                         <div id="smallpicarea">
                            <div id="thumbs">
                                <ul>
                                    <li class="first btnPrev"><i id="play_prev" class="icon-angle-left"></i></li>
                                	{{for smallImagesUrl}}
										{{if #index<4}}
										<li class="slideshowItem"><a id="thumb-{{: #getIndex()+1}}" href="javascript:"><img src="{{:#data}}"></a></li>
                                		{{else}}
										<li class="slideshowItem" style="display:none"><a id="thumb-{{: #getIndex()+1}}" href="javascript:"><img src="{{:#data}}"></a></li>
										{{/if}}
										{{/for}}
                                    <li class="last btnNext"><i id="play_next" class="icon-angle-right"></i></li>
                                </ul>
                            </div>
                        </div>
                   </script>
                   <div class="collection">
                   <!-- <p>商品ID：${skuId} </p>-->
                   <!-- <p><a href="#"><i class="icon-heart-empty"></i>收藏</a></p> -->
                   </div>
                 </div>
                <!--商品详情右侧-->
      			<div class="details">
      			 <div id="producSKUData"></div>
      			 <script id="producSKUTemple" type="text/template">
                   <ul class="details-title">
                       <li class="word" id="prodName">{{:prodName}}</li>
                       <li class="color">{{:productSellPoint}}</li>
                   </ul>
                   <ul class="details-list">
                       <li class="word">价格：</li>
                       <li class="color">￥{{:~liToYuan(salePrice)}}元</li>
                   </ul>
                   <!--<ul class="details-list">
                       <li class="word">所在地：</li>
                       <li><select class="details-large"><option>北京市海淀区</option></select></li>
                   </ul>-->
                    <ul class="details-list" id="activeDateDiv">
                       <li class="word">有效期：</li>
                       <li id="activeDate"></li>
                    </ul>
					<div id="productAttrDiv">
					{{for productAttrList}}
						<ul class="details-list">
					   		<li class="word" value="{{:attrId}}">{{:attrName}}：</li>
                       		<li class="attribute" value="" id="attrValue_{{:attrId}}">
							{{for attrValueList ~attrId=attrId}}
                           		<p onclick="pager._changeAttr(this,{{:attrvalueDefId}},{{:~attrId}})">
									{{if isOwn}}
										<a href="javascript:void(0);" class="current" name="{{:attrvalueDefId}}">
									{{else}}
                               			<a href="javascript:void(0); name="{{:attrvalueDefId}}">
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
					</div>
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
					   <li class="right"><span class="word1">库存:</span><span id="usableNum">{{:usableNum}}</span></li>
                       <!--<li class="right"><span class="word1">评价：</span><span>{{:commentNum}}</span></li>-->
                   	</ul>
					{{if rechargeType != 'D'}}
                   	<ul class="details-list btm-magin">
                   	<li class="btn-mar" id="buyBtnId" style="display:none"><input id="buyBtn" type="button" class="slp-btn details-btn none-bj" value="立即购买"></li>
                   	<li id="addCarBtnId" style="display:none"><input type="button" class="slp-btn details-btn" id="joinShopCart" value="加入购物车"></li>
                   	<li id="invalidBtnId" style="display:none"><input id="invalidBtn" type="button" class="slp--ash-btn" value="已下架"></li>
                   	</ul>
					{{/if}}
				</script>
               
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
                 	 <div class="left-tow-list" id="hotProductData"></div>
                         <script id="hotProductListTmpl" type="text/x-jsrender">
							<div class="left-tow-list">
								<ul>
                            		<li class="img"><a href="${_base}/product/detail?skuId={{:skuId}}"><img src="{{:picUrl}}"></a></li>
                            		<li class="word"><a href="${_base}/product/detail?skuId={{:skuId}}">{{:prodName}}</a> </li>
                            		<li class="left"><span>￥{{:~liToYuan(salePrice)}}</span></li>
                        		</ul>
							</div>
						</script>
              </div>   
       </div>
       <!--左侧-结束-->
       <!--右侧-->
        <div class="parameter-right">
              <div class="parameter-right-tilit">
                  <ul>
                      <li class="current" id="productInfoTab">商品详情</li>
                      <li id="productConfigTab">规格参数</li>
                  </ul>                  
              </div>
              <div id="date1">
                  <div class="commodity-word">
                      <ul id="configBriefParameterData">
                      </ul>
                      <script id="configBriefParameterTemple" type="text/template">
							{{if #index<6}}
							<li>
                               <p>{{:attrName}}:</p>
                               {{for attrValueList}}
                               <p>{{:attrValueName}}</p>
							   {{/for}}
                            </li>
							{{/if}}
				  	  </script>
                      <ul>
                      <li class="worde"><a href="javascript:void(0);" id="seeMoreConfigBtn" >更多参数</a></li>
                      </ul>
                  </div>
                   
                  <div class="commodity-list" id="porductInfoDiv">
                  	${prodDetail}
                  </div>
        		</div>
                <div id="date2" style=" display:none;">
                   <div class="specification">
                       	<ul id="configParameterData">
						</ul>
                   </div>
                  <script id="configParameterTemple" type="text/template">
							<li>
                               <p class="word">{{:attrName}}</p>
							   {{for attrValueList}}
                               <p>{{:attrValueName}}</p>
							   {{/for}}
                            </li>
				  </script>
                  
        		</div>
         
        </div>
       <!--右侧 结束-->
       
       
       </div> 

         </div>
     </div>

   <!--底部-->
<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
	<script type="text/javascript">
	var pager;
	var skuId = '${skuId}';
	var skuAttrs = '${skuAttrs}'
	var producSKU = $.parseJSON('${productSKU}');
	var productImages = $.parseJSON('${productImages}');
	var activeDateValue = '${activeDateValue}';
	var productCatId = '${productCatId}';
	
	(function () {
		seajs.use('app/jsp/product/productDetail', function (ProductDetailPager) {
			pager = new ProductDetailPager({element: document.body});
			pager.render();
		});
	})();
	</script>
</body>
</html>
