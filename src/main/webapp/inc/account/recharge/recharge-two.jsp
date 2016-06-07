<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资产中心</a>&gt;</p>
          <p><a href="${accountBalanceLink }">账户余额</a>&gt;</p>
          <p><a href="${accountRechargeOneLink }">充值</a>&gt;</p>
          <p><a href="#">选择支付方式</a></p>
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
                     <li class="ash-border"></li>
                     <li class="ash-yuan">3</li>
                     <li class="ash-word">在线支付</li>
                     </ul>
                      <ul>
                     <li class="ash-border"></li>
                     <li class="ash-yuan"><i class="icon-ok"></i></li>
                      <li>充值完成</li>
                     </ul>
           </div>
          <!--/步骤结束-->
              <div class="list-int">
                       <ul>
                            <li class="word">充值金额:</li>
                            <li><span class="jine">¥98.00</span></li>  
                        </ul>
                           <ul>
                            <li class="word1">请选择支付方式:</li>
                            <li class="current"><A href="#"><img src="${_slpbase }/images/kc-1.png"></A></li>
                            <li><A href="#"><img src="${_slpbase }/images/kc-2.png"></A></li>
                            <li><A href="#"><img src="${_slpbase }/images/kc-3.png"></A></li>
                        </ul>
                           <ul class="int-mar">
                            <li class="word">已选:</li>
                            <li>支付宝</li>  
                        </ul>
                          <ul>
                            <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" value="下一步" onclick="location.href='${_base}/account/recharge/three'"></li>
                        </ul>
                    
             </div>      
      </div>

  </div>  
     