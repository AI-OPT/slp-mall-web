<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资产中心</a>&gt;</p>
         <p><a href="${accountBalanceLink }">账户余额</a>&gt;</p>
          <p><a href="${accountRechargeOneLink }">充值</a>&gt;</p>
          <p><a href="#">充值完成</a></p>
      </div>
      <div class="account-bj">
       <!--步骤-->
          <div class="steps steps-four">
                     <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">1</li>
                     <li class="yellow-word">填写充值金额</li>
                     </ul>
                     <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">2</li>
                     <li class="yellow-word">选择支付方式</li>
                     </ul>
                      <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan">3</li>
                     <li class="yellow-word">在线支付</li>
                     </ul>
                      <ul>
                     <li class="yellow-border"></li>
                     <li class="yellow-yuan"><i class="icon-ok"></i></li>
                     <li class="yellow-word">充值完成</li>
                     </ul>
           </div>
          <!--/步骤结束-->
              <div class="recharge-success">
                 <p><img src="${_slpbase }/images/succ.png"></p>
                 <p class="word">您已经充值成功，充值金额<span class="jine">￥100.00</span></p>
                 <p class="success-box"><a href="#">查看余额明细</a><a href="#">设置支付密码</a></p>
              </div>
      </div>

  </div>  
     