<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${_slpbase }/scripts/jquery.page.js"></script>
<link href="${_slpbase }/styles/jquery-page.css" rel="stylesheet" type="text/css"> 
<!--账户余额右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资产中心</a>&gt;</p>
          <p><a href="${accountBalanceLink }">账户余额</a>&gt;</p>
          <p><a href="#">账户明细</a></p>
      </div>
     <div class="account-bj-none"><!--外侧-->
        <div class="advanced-search account-search">
           <ul>
               <li class="close"><select class="select-small"><option>近三个月订单</option></select></li>
           	  <li>
                   <p><input type="text" class="int-small" ><A href="#"><i class="icon-calendar"></i></A></p>
                   <p>-</p>
                   <p><input type="text" class="int-small" ><A href="#"><i class="icon-calendar"></i></A></p>
               </li>
               <li><input type="button" class="order-btn" value="搜索"></li>
           </ul>
           </div>
       
     </div>
      <div class="order-list-bj">	
            <!--选择订单信息 table-->
           <div class="order-list-table">
           <ul>
           <li><a href="#" class="current">支付记录</a></li>
           <li><a href="#">充值记录</a></li>
           <li><a href="#">全部</a></li>
           </ul>                                        
           </div>
            <!--选择订单信息 table结束-->
             <!--table1-->
            <div id="order-date">
              <div class="account-table">
                  <table width="100%" border="0">
                      <tr class="bj">
                        <td>交易日期</td>                                                                                                                
                        <td>交易类型</td>
                        <td>流水号</td>
                        <td>交易金额</td>
                        <td>备注</td>
                      </tr>                                                                                                                                                                           
                      <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">40.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
                       <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">10.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
                       <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">10.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
					</table>
                </div>
            </div>
            <!--table2-->
            <div id="order-date1" style="display:none;">
              <div class="account-table">
                  <table width="100%" border="0">
                      <tr class="bj">
                        <td>交易日期</td>                                                                                                                
                        <td>交易类型</td>
                        <td>流水号</td>
                        <td>交易金额</td>
                        <td>备注</td>
                      </tr>                                                                                                                                                                           
                      <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">30.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
                       <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">10.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
                       <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">10.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
					</table>
                </div>
            </div>  
            <!--table3-->
            <div id="order-date2" style="display:none;">
              <div class="account-table">
                  <table width="100%" border="0">
                      <tr class="bj">
                        <td>交易日期</td>                                                                                                                
                        <td>交易类型</td>
                        <td>流水号</td>
                        <td>交易金额</td>
                        <td>备注</td>
                      </tr>                                                                                                                                                                           
                      <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">20.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
                       <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">10.00</td>
                       <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
                       <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color">10.00</td>
                        <td><a href="#">订单号：1323434434</a></td>
                      </tr>
                     <tr>
                        <td>2016-2-19  10:30</td>
                        <td>交易支出</td>
                        <td>11234568945</td>
                        <td class="color-balck">10.00</td>
                        <td>网银支付</td>
                      </tr>
					</table>
                </div>
            </div>    
            <!--分页-->
            
            <div class="paging-large">
                
                <ul>
            		<div class="tcdPageCode"></div>    	
                    <!-- 
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
                     -->
                 </ul>
                 
                 <script type="text/javascript">
		
					$(".tcdPageCode").createPage({
								pageCount:20,
								current:2,
								pageSize : 6,
								totalCount:120,
								backFn:function(p){
									//pageSearch4Select(p);
								}
							});
				 </script>
              </div>   
     	 </div>
             
  </div>  