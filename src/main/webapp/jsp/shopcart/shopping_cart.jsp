<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
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
<div class="fsast-bj">
 <div class="fsast-head">
        <div class="fsast-logo">
            <ul>
                <li><a href="${_base}"><img src="${_slpbase }/images/login-logo.png"></a></li>
                <li>购物车</li>
            </ul>
        </div>
        <!--导航 搜索区-->
	    <div class="fsast-search">
             <ul>
                  <li><input type="text" class="fsast-xlarge" id="serachName"></li>
                  <li><A href="javascript:void(0);" id="BTN_SEARCH"><i class="icon-search"></i></A></li>
             </ul>
        </div>
	    <!-- 结束 -->
    </div>
 </div>
<!--导航区域结束-->
     
     <!--购物车-->
<div class="fsast-charge">
     	<div class="big-wrapper"><!--内侧居中框架-->
       		<div class="payment-title"><p>全部<span id="allProductNum">（${prodTotal==null?0:prodTotal}）</span></p></div>
           <div class="recharge-bj-tow"><!--白色背景-->
                <div class="shopping-cart">
                 <table width="100%" border="0">
                              <tr class="bj">
                                <td width="10%"><input type="checkbox" name="checkAll" onclick="pager._checkAll(this);" class="checkbox-medium" style=" display:inline-block;">全选</td>
                                <td width="35%">商品信息</td>
                                <td>单价</td>
                                <td  width="11%">数量</td>
                                <td>小计</td>
                                <td>操作</td>
                              </tr>
                              <tbody id="cartProdData"></tbody>
                  </table>
                   <script id="cartProdTemple" type="text/template">
                       <!-- 状态和库存可用量均检查 -->
					{{if state == '5' && usableNum > 0}}
							<tr id="{{:skuId}}_tr">
                                <td><input id="{{:skuId}}" type="checkbox" name="checkOne" class="checkbox-medium"></td>
                                <td class="sp">
                                    <table width="100%" border="0">
                                          <tr>
                                            <td class="word" width="25%"><img src="{{:picUrl}}"></td>
                                            <td><a href="${_base}/product/detail?skuId={{:skuId}}">{{:productName}}</a></td>
                                          </tr>
                                    </table>
                                </td>
                                <td class="ash">￥{{:~liToYuan(salePrice)}}</td>
                                <td class="clp-btn">
                                    <div class="number">
										<input type="hidden" id="{{:skuId}}_oldProdNum" value={{:buyNum}} />
                                        <p><input type="button" value="-" class="small-xbtn" onclick="pager._changeProdNum('{{:skuId}}',-1,{{:salePrice}},{{:usableNum}});"></p>
                                        <p><input id="{{:skuId}}_prodnum" type="text" value={{:buyNum}} class="xz-int" onchange="pager._modifyCartProdQty('{{:skuId}}',this,{{:salePrice}},{{:usableNum}});"></p>
                                        <p><input type="button" value="+" class="small-xbtn" onclick="pager._changeProdNum('{{:skuId}}',1,{{:salePrice}},{{:usableNum}});"></p>
                                    </div>
                                </td>
                                <td id="{{:skuId}}_prodPriceSubtotal" class="bold">¥{{:~shopCartPrices(salePrice,buyNum)}}</td>
                                <td>
                                <div class="number">
                                <p><a href="#" onclick="pager._delCartProd('{{:skuId}}');"><i class="icon-remove"></i>删除</a></p>
                                </div>
                                </td>
							</tr>
						{{else}}
							<tr id="{{:skuId}}_tr" class="none-color">
                                <td><input id="{{:skuId}}" type="checkbox" name="outOfStockProd" disabled="true" class="checkbox-medium"></td>
                                <td class="sp">
                                    <table width="100%" border="0">
                                          <tr>
                                            <td class="word" width="25%"><img src="{{:picUrl}}"></td>
                                            <td><a href="${_base}/product/detail?skuId={{:skuId}}">{{:productName}}</a></td>
                                          </tr>
                                    </table>
                                </td>
                                <td class="ash">无</td>
                                <td class="clp-btn">
                                    <div class="number">
                                        <p><input type="button" disabled="true" value="-" class="small-xbtn"></p>
                                        <p><input id="{{:skuId}}_prodnum" type="text" disabled="true" value={{:buyNum}} class="xz-int" ></p>
                                        <p><input type="button" disabled="true" value="+" class="small-xbtn"></p>
                                    </div>
                               		 	<span>暂时无货</span>
                                </td>
                                <td class="bold">无</td>
                                <td>
                                <div class="number">
                                <p><a href="#" onclick="pager._delCartProd('{{:skuId}}');"><i class="icon-remove"></i>删除</a></p>
                                </div>
                                </td>
							</tr>
						  {{/if}}
						</script>
						
						</div>
         </div>
         
          <div class="recharge-bj-tow recharge-bj-three"> <!--白色背景-->
          <div class="left-chix">
              <ul>
                  <li><input type="checkbox" name="checkAll" class="checkbox-medium" onclick="pager._checkAll(this);" style=" margin-top:26px; float:left;">全选</li>
                  <li><a href="#" id="deleteSelectProd">删除选中</a></li>
              </ul>
          </div>
          <div class="order-amount">
          <ul>
          <li>
          <p>已选中<span id="checkProductNum">0</span>件商品</p>
          <p>应付总计(不含运费):</p>
          <p><span id="cartProdTotal">￥0.00</span></p> 
          </li>
         </ul>
          </div>
            <form id="submitForm" action="${_base}/shopcart/applyOrder" method="post">

            </form>
          <div class="right-btn"><input id="submitOrder" type="button" class="slp-btn topay-btn" value="提交订单"></div>
         </div>
     </div>
 </div>
    <!--底部-->
<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
	<script type="text/javascript">
		var pager;
        var errMsg = '${errMsg}';
		var cartProdList = $.parseJSON('${cartProdList==null?"[]":cartProdList}');
		var skuNumLimit = '${skuNumLimit}';
		(function () {
			seajs.use('app/jsp/shoppingcart/shopCartDetails', function (shopCartDetailsPager) {
				pager = new shopCartDetailsPager({element: document.body});
				pager.render();
			});
		})();
		
		var index_search_pager;
		(function () {
			seajs.use('app/jsp/search/search', function (SearchPager) {
				index_search_pager = new SearchPager({element: document.body});
				index_search_pager.render();
			});
		})();
		
		
	</script>
</body>
</html>


