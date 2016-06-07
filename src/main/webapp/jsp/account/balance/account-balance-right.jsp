<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="${_slpbase }/scripts/jQuery.Ajax.js">
</script>
<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/account/balance/balanceSevenDaysAgoSearch', function (BalanceSevenDaysAgoSearchPager) {
			pager = new BalanceSevenDaysAgoSearchPager({element: document.body});
			pager.render();
		});
	})();
</script>
<!--账户余额右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资产中心</a>&gt;</p>
          <p><a href="#">账户余额</a></p>
      </div>
     <div class="account-bj-none"><!--外侧-->
        <div class="balance-title">
                 <p>当前余额:<span id="balanceQueryUsableFundId"></span></p>
                 <p><input type="button" class="bala-btn" value="充值" onclick="location.href='${_base}/account/recharge/one'"></p>
                 <p class="pass-mar">支付密码安全度:</p>
                 <p><img src="${_slpbase }/images/icon-c.png"><A href="#" class="color">还未启用支付密码</A></p>
                 <p><input type="button" class="slp-btn setes-pass-btn" value="设置支付密码"></p>
        </div>
       	<script type="text/javascript">
       		$(document).ready(function(){
       			var balanceQueryUsableFundUrl = "${_base}/account/queryUsableFund"; 
       			//查询账户余额
       			$.ajaxtext(balanceQueryUsableFundUrl,"",function(data){
       				//alert("账户余额:"+data);
       				$('#balanceQueryUsableFundId').text(data);
       			});
       		});
       	</script>
     </div>
      <div class="order-list-bj">
     			<div class="account-title"><p>近7天收支记录<a href="${_base}/account/balance/detail">更多明细</a></p></div>
              <div class="account-table">
                  <table width="100%" border="0" id="table_title_id">
                      <tr class="bj">
                        <td>交易日期</td>                                                                                                                
                        <td>交易类型</td>
                        <td>流水号</td>
                        <td>交易金额</td>
                        <td>备注</td>
                      </tr> 
                      <script id="balanceSevenDaysAgoTmpl" type="text/x-jsrender">
					  	{{for}}
						<tr>
                        	<td>{{:~timestampToDate('yyyy-MM-dd hh:mm:ss', lastStatusDate)}}</td>
                       	 	<td>
							 {{if busiType == '1'}}	
								订单收费
							 {{/if}}
							 {{if busiType == '2'}}	
								充值缴费
							 {{/if}}
							</td>
                        	<td>{{:orderId}}</td>
                        	<td class="color">{{:totalFee/1000}}</td>
                        	<td><a href="#">{{:remark}}</a></td>
                      	</tr>
						{{/for}}
						
					  </script>                                                                                                                                                                          
                      <script id="balanceSevenDaysAgoNullTmpl" type="text/x-jsrender">
							<tr>
								<td colspan="5">交易记录为空</td>
							</tr>
					  </script>
					</table>
                </div>
      </div>

  </div>