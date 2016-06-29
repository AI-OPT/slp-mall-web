<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>我的订单</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css">
</head>

<body>
  <!--顶部菜单-->
  <%@ include file="/inc/top-menu.jsp" %>
  <!--顶部菜单结束-->

<!--导航区域-->
 <%@ include file="/inc/user-nav.jsp" %>
<!--导航区域结束-->
     
     <!--订单详情-->
<div class="fsast-charge">
    <div class="big-wrapper"><!--内侧居中框架-->
    <!--我的订单-->
    <!--我的订单左侧-->
  	<%@ include file="/inc/user-leftmenu.jsp" %>
 	<!--／我的订单左侧结束-->  
 <!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="${_base }">账户中心</a>&gt;</p>
          <p><a href="${_base }">我的订单</a></p>
      </div>
      <div class="order-list-bj">
         <div class="advanced-search">
           <input id="searchType" style="display:none" value="1"/>
           <ul>
               <li>
                   <p class="word1">订单类型</p>
                   <p>
                   	<select id="orderTypeQ" class="select-small">
                   		<option value="">全部</option>
                   	</select></p>
               </li>
               <li>
                   <p class="word">订单号 &nbsp;&nbsp;</p>
                   <p><input id="orderIdQ" type="text" class="int-small" placeholder="请输入订单号查询"></p>
               </li>
               <li class="word1" id="selectTimeDiv">
           	   <p>
	               	<select id="selectTimeQ" class="select-small">
	               		<option value="1">近三个月订单</option>
	               		<option value="2">今年历史订单</option>
	               		<option value="3">所有历史订单</option>
	               	</select>
	               	</p>
               </li>
               <li><input id="searchOrderBtn" type="button" class="order-btn" value="搜索"></li>
               <li class="av"><a id="changeSearchType" class="is">高级搜索<i class="icon-angle-down"></i></a></li>
           </ul>
           <ul class="open-gaoj" style=" display:none;">
               <li>
                   <p class="word1">支付方式</p>
                   <p>
                   		<select id="payStyleQ" class="select-small">
                   			<option value="">全部</option>
                   		</select>
                   </p>
               </li>
               <li>
                   <p class="word">下单时间</p>
                   <p id="timeBeginId" ><input id="orderTimeBeginQ" type="text" class="int-small" readonly><A href="javascript:void(0);"><i class="icon-calendar"></i></A></p>
                   <p>-</p>
                   <p id="timeEndId"><input id="orderTimeEndQ" type="text" class="int-small" readonly><A href="javascript:void(0);"><i class="icon-calendar"></i></A></p>
               </li>
           </ul>
          </div> 
      </div>
      <!--/高级搜索结束-->
      
       <div class="order-list-bj">
           <!--选择订单信息 table-->
           <div class="order-list-table">
           <input id="searchOrderState" value="" style="display:none"/>
           <ul>
           <li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this)" class="current">全部</a></li>
           <li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'11')">待支付</a></li>
           <li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'111,90')">已成功</a></li>
           <li><a href="javaScript:void(0)" onclick="pager._changeOrderState(this,'91')" style="border-right-style: none;" >已关闭</a></li>
           <!-- <p><a href="#"><i class="icon-trash"></i>订单回收站</a></p> -->
           </ul>                                        
           </div>
            <!--选择订单信息 table结束-->
          <!--订单详情-->  
          <div id="order-date">
          
          <div class="order-list-ctn">
             <div class="order-list-title">
             <div class="shopping-cart addto-title">
            		 <table>
                        <tr class="order-bj-s">
                            <td width="45%">商品信息</td>
                            <td width="10%">单价</td>
                            <td width="10%">数量</td>
                            <td width="10%">实付款</td>
                            <td width="10%">订单状态</td>
                            <td width="15%">操作</td>
                        </tr>
                   </table>
             </div>
             <div id="orderListData"></div>
             <script id="orderListTemple" type="text/x-jsrender">
             
               <div class="shopping-cart addto-list">
                 <table width="100%" border="0">
                                <tr class="order-bj-s">
                                <td colspan="6">
                                    <div class="purchase-list">
										<input id="orderType" value="{{:orderType}}" style="display:none">
                                        <ul>
                                            <li>
                                                <p>订单号:</p>
                                                <p>{{:orderId}}</p>
                                            </li>
                                            <li>
                                                <p>{{:~timesToFmatter(orderTime)}}</p>
                                            </li>
                                                <li>
                                                <p>订单金额:</p>
                                                <p>¥{{:~liToYuan(adjustFee)}}</p>
                                            </li>
											{{if state !='11' && state !='91'}}
											<li>	
												<p>支付方式:</p>
                                                <p>{{:payStyleName}}</p>
                                            </li>
											{{/if}}
                                        </ul>
                                    </div>
                                </td>
                              </tr>   
                              {{for productList ~parentState=state ~parentStateName=stateName ~size=productList.length ~orderType=orderType}}     
                              <tr class="tr-border">
                               <td class="sp"  width="45%">
                                    <table width="100%" border="0">
                                          <tr>
                                            <td class="word" width="25%"><img src="{{:imageUrl}}"></td>
                                            <td>
                                             <div class="number">
                                             <p><a href="${_base}/product/detail?skuId={{:skuId}}">{{:prodName}}</a></p>
                                             </div>
                                            </td>	
                                          </tr>
                                    </table>
                                </td>
                                <td class="ash" width="10%">¥{{:~liToYuan(salePrice)}}</td>
                                <td width="10%">{{:buySum}}</td>
                                <td width="10%" class="strong">¥{{:~liToYuan(totalFee)}}</td>
								{{if #getIndex() == 0}}
                                <td width="10%" rowspan="{{:~size}}"> 
                                    <div class="number">
									<p>{{:~parentStateName}}</p>
                                    <p><a href="${_base}/myorder/detail?orderId={{:orderId}}&orderType={{:~orderType}}" target="_Blank">订单详情</a></p>
                                    </div>
                                </td>
                                <td width="15%" class="none-borer" rowspan="{{:~size}}">
                                    <div class="number">
									{{if ~parentState=='11'}}
                                    <p><input type="button" class="immedtl-btn" value="立即支付" onclick="window.location.href='${_base}/order/pay?orderId={{:orderId}}'"></p>
									{{else ~orderType!='100010'}}
									<p><input type="button" class="again-btn" value="再次购买" onclick="pager._buyAgain('{{:orderId}}')"></p>
									{{/if}}
                                    <!--<p><a href="#">关闭订单</a></p>-->
                                    </div>
                                </td>
                                {{/if}}
                              </tr>  
						      {{/for}}
                  </table>   
                  </div>
				</script>
             </div>
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
 </div>
 <!--底部-->
<%@ include file="/inc/foot.jsp" %>
 <!--底部 结束-->
<script type="text/javascript">
var pager;
var payStyleParams = $.parseJSON('${payStyleParams}');
var orderStyleParams = $.parseJSON('${orderStyleParams}');
(function () {
	seajs.use('app/jsp/order/orderList', function (OrderListPager) {
		pager = new OrderListPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>


