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
                <li><a href="#"><img src="${_slpbase }/images/login-logo.png"></a></li>
                <li>购物车</li>
            </ul>
        </div>
        <!--导航 搜索区-->
	    <div class="fsast-search">
             <ul>
                  <li><input type="text" class="fsast-xlarge"></li>
                  <li><A href="#"><i class="icon-search"></i></A></li>
             </ul>
        </div>
	    <!-- 结束 -->
    </div>
 </div>
<!--导航区域结束-->
     
     <!--购物车-->
<div class="fsast-charge">
     	<div class="big-wrapper"><!--内侧居中框架-->
       		<div class="payment-title"><p>全部<span>（20）</span></p></div>
           <div class="recharge-bj-tow"><!--白色背景-->
                <div id="shopCart" class="shopping-cart">
                   <script id="cartProdTemple" type="text/template">
                 <table width="100%" border="0">
                              <tr class="bj">
                                <td width="10%"><input type="checkbox" class="checkbox-medium" style=" display:inline-block;">全选</td>
                                <td width="35%">商品信息</td>
                                <td>单价</td>
                                <td  width="11%">数量</td>
                                <td>小计</td>
                                <td>操作</td>
                              </tr>
								{{for cartProdList}}
                              <tr>
                                <td><input type="checkbox" class="checkbox-medium"></td>
                                <td class="sp">
                                    <table width="100%" border="0">
                                          <tr>
                                            <td class="word" width="25%"><img src="${_slpbase }/images/sim.png"></td>
                                            <td><a href="#">{{:productName}}</a></td>
                                          </tr>
                                    </table>
                                </td>
                                <td class="ash">￥{{:salePrice}}</td>
                                <td class="clp-btn">
                                    <div class="number">
                                        <p><input id="delQtyBtn" type="button" value="-" class="small-xbtn"></p>
                                        <p><input id="productQty" type="text" value={{:buyNum}} class="xz-int"></p>
                                        <p><input id="addQtyBtn" type="button" value="+" class="small-xbtn"></p>
                                    </div>
                                </td>
                                <td id="prodPrice" class="bold">¥{{:salePrice}}*{{:buyNum}}</td>
                                <td>
                                <div class="number">
                                <p><a href="#"><i class="icon-remove"></i>删除</a></p>
                                </div>
                                </td>
                              </tr>
								{{/for}}
                  </table>   
                    </script>
                  </div>
         </div>
         
          <div class="recharge-bj-tow recharge-bj-three"> <!--白色背景-->
          <div class="left-chix">
              <ul>
                  <li><input type="checkbox" class="checkbox-medium" style=" margin-top:26px; float:left;">全选</li>
                  <li><a href="#">删除选中</a></li>
              </ul>
          </div>
          <div class="order-amount">
          <ul>
          <li>
          <p>已选中<span>25</span>件商品</p>
          <p>应付总计(不含运费):</p>
          <p><span>¥222231.00</span></p> 
          </li>
         <li>优惠：-¥50.00</li>
         </ul>
          </div>

          <div class="right-btn"><input type="button" class="slp-btn topay-btn" value="提交订单"></div>
         </div>
     </div>
 </div>
    <!--底部-->
<%@ include file="/inc/foot.jsp" %>
   <!--底部 结束-->
	<script src="${_slpbase }/scripts/jquery-1.11.1.min.js" type="text/javascript"></script>
	<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
	<script type="text/javascript">
		var cartProdList = $.parseJSON('${cartProdList}');
	</script>
</body>
</html>


