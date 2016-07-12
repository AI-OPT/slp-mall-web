<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/bootstrap.css">
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/account/balance_detail/balanceDetailList', function (BalanceDetailListPager) {
			pager = new BalanceDetailListPager({element: document.body});
			pager.render();
		});
	})();
</script>

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
               <li class="word1"><select id="select_date_id" class="select-small" onchange="pager._queryAccountBalanceDetailList('pay_id');"></select></li>
           	  <li>
           	  	   <!-- <p><input id="effectDate_be" name="effectDate" class="int-medium" type="text" readonly /><A href="javascript:void(0);" id="effectDate" ><i class="icon-calendar date-nothing"></i> </A></p>	
                    -->
                   <p><input id="startDate_be" name="startDate" type="text" class="int-small" ><A href="javascript:void(0);" id="startDate" ><i class="icon-calendar"></i></A></p>
                   <p>-</p>
                   <p><input id="endDate_be" name="endDate" type="text" class="int-small" ><A href="javascript:void(0);" id="endDate"><i class="icon-calendar"></i></A></p>
               </li>
               <li><input id="search_button_id" type="button" class="order-btn" value="搜索"></li>
           </ul>
           </div>
       
     </div>
      <div class="order-list-bj">	
            <!--选择订单信息 table-->
           <div class="order-list-table">
           <input type="hidden" id="busiType_id" name="busiType_id" value="1"/>
           <ul>
           <li><a id="pay_id" href="javascript:void(0);" class="current">支付记录</a></li>
           <li><a id="charge_id" href="javascript:void(0);">充值记录</a></li>
           <li><a id="all_id" href="javascript:void(0);">全部</a></li>
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
                      <tbody id="table_info_id_pay_id">
                      	
                      </tbody>                                                                                                                                                                           
                      
					</table>
					<div id="showMessageDiv"></div>
                </div>
            </div>
               
              <div  style="text-align: right">
                      <ul id="pagination">
                      </ul>
              </div>
                  
     	 </div>
       	 <script id="balanceDetailListTmpl" type="text/x-jsrender">
					  	{{for}}
						<tr>
                        	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', createTime)}}</td>
                       	 	<td>
							 {{:busiType}}
							</td>
                        	<td>{{:orderId}}</td>
                        	<td class="color">{{:totalFee/1000}}</td>
                        	<td><a href="#">{{:remark}}</a></td>
                      	</tr>
						{{/for}}
						
					  </script>                                                                                                                                                                          
					  <script id="balanceDetailListNullTmpl" type="text/x-jsrender">
							<tr>
								<td colspan="5">交易记录为空</td>
							</tr>
					  </script>      
  </div>  