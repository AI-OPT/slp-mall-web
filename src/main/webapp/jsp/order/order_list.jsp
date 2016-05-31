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
                <li>快充支付</li>
            </ul>
        </div>
        <div class="fsast-bav">
            <ul>
                <li><a href="#">首页</a></li>
                <li><a href="#">我的订单</a></li>
                <li class="shez"><a href="#">账户设置<i class="icon-angle-down"></i></a>
                <div class="setgs" style=" display:none;">
                    <ul>
                        <li><a href="#">资质认证</a></li>
                        <li><a href="#">安全设置</a></li>
                        <li><a href="#">登录密码</a></li>
                        <li><a href="#">支付密码</a></li>
                        <li><a href="#">手机绑定</a></li>
                        <li><a href="#">邮箱绑定</a></li>
                    </ul>
                </div>
                </li>
                <li><a href="#">消息</a><p class="icon">4</p></li>                          
            </ul>
        </div>
        <div class="fsast-search">
             <ul>
                  <li><input type="text" class="fsast-xlarge"></li>
                  <li><A href="#"><i class="icon-search"></i></A></li>
             </ul>
            
        </div>
    </div>
 </div>
<!--导航区域结束-->
     
     <!--订单详情-->
<div class="fsast-charge">
    <div class="big-wrapper"><!--内侧居中框架-->
    <!--我的订单-->
    <!--我的订单左侧-->
    <div class="my-order-menu">
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn1.png"></li>
            <li class="word">
                <p class="">订单中心</p>
                <p class="active"><A href="#">我的订单</A></p>
                <p><A href="#">评价管理</A></p>
                <p><A href="#">订单回收站</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn2.png"></li>
            <li class="word">
                <p class="">资产中心</p>
                <p><A href="#">账户余额</A></p>
                <p><A href="#">充值卡券</A></p>
                <p><A href="#">我的授信</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn3.png"></li>
            <li class="word">
                <p class="">关注中心</p>
                <p><A href="#">收藏夹</A></p>
                <p><A href="#">我的足迹</A></p>
                <p><A href="#">新消息（0）</A></p>
            </li>
        </ul>
        <ul>
            <li class="img"><img src="${_slpbase }/images/order-menu-iocn5.png"></li>
            <li class="word">
                <p class="">账户设置</p>
                <p><A href="#">个人资料</A></p>
                <p><A href="#">通讯录</A></p>
                <p><A href="#">我的API</A></p>
                <p><A href="#">安全设置</A></p>
            </li>
        </ul>
    </div>
 <!--／我的订单左侧结束-->  
 <!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">我的订单</a></p>
      </div>
      <div class="order-list-bj">
         <div class="advanced-search">
           <ul>
               <li class="close"><select class="select-small"><option>近三个月订单</option></select></li>
               <li>
                   <p class="word">订单类型</p>
                   <p><select class="select-small"><option>全部</option></select></p>
               </li>
               <li>
                   <p class="word">订单号</p>
                   <p><input type="text" class="int-small" placeholder="请输入订单号查询"></p>
               </li>
               <li><input type="button" class="order-btn" value="搜索"></li>
               <li class="av"><a href="#" class="is">高级搜索<i class="icon-angle-down"></i></a></li>
           </ul>
           <ul class="open-gaoj" style=" display:none;">
              <li>
                   <p class="word1">下单时间</p>
                   <p><input type="text" class="int-small" ><A href="#"><i class="icon-calendar"></i></A></p>
                   <p>-</p>
                   <p><input type="text" class="int-small" ><A href="#"><i class="icon-calendar"></i></A></p>
               </li>
               <li>
                   <p class="word">支付方式</p>
                   <p><select class="select-small"><option>全部</option></select></p>
               </li>
           </ul>
          </div> 
      </div>
      <!--/高级搜索结束-->
      
       <div class="order-list-bj">
           <!--选择订单信息 table-->
           <div class="order-list-table">
           <ul>
           <li><a href="#" class="current">全部</a></li>
           <li><a href="#">待支付</a></li>
           <li><a href="#">已成功</a></li>
           <li><a href="#">已关闭</a></li>
           <li><a href="#">已退款</a></li>
           <p><a href="#"><i class="icon-trash"></i>订单回收站</a></p>
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
                            <td width="50%">商品信息</td>
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
                                                <p>¥{{:adjustFee}}</p>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                              </tr>   
                              {{for productList ~parentState=state ~parentStateName=stateName ~size=productList.length}}     
                              <tr class="tr-border">
                               <td class="sp"  width="45%">
                                    <table width="100%" border="0">
                                          <tr>
                                            <td class="word" width="25%"><img src="{{:imageUrl}}"></td>
                                            <td>
                                             <div class="number">
                                             <p><a href="#">{{:prodName}}</a></p>
                                             </div>
                                            </td>	
                                          </tr>
                                    </table>
                                </td>
                                <td class="ash" width="10%">¥{{:salePrice}}</td>
                                <td width="10%">1</td>
                                <td width="10%" class="strong">{{:adjustFee}}</td>
								{{if #getIndex() == 0}}
                                <td width="10%" rowspan="{{:~size}}"> 
                                    <div class="number">
									<p>{{:~parentStateName}}</p>
                                    <p><a href="#">订单详情</a></p>
                                    </div>
                                </td>
                                <td width="15%" class="none-borer" rowspan="{{:~size}}">
                                    <div class="number">
									{{if ~parentState=='11'}}
                                    <p><input type="button" class="immedtl-btn" value="立即支付"></p>
									{{else}}
									<p><input type="button" class="again-btn" value="再次购买"></p>
									{{/if}}
                                    <p><a href="#">关闭订单</a></p>
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
<script type="text/javascript">
var pager;
(function () {
	seajs.use('app/jsp/order/orderList', function (OrderListPager) {
		pager = new OrderListPager({element: document.body});
		pager.render();
	});
})();
</script>
</body>
</html>


