<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--我的订单右侧-->  
  <div class="my-order-cnt">
       <div class="payment-title">
          <p><a href="#">账户中心</a>&gt;</p>
          <p><a href="#">资产中心</a>&gt;</p>
          <p><a href="${accountBalanceLink }">账户余额</a>&gt;</p>
          <p><a href="${accountRechargeOneLink }">充值</a>&gt;</p>
          <p><a href="#">填写充值金额</a></p>
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
                     <li class="ash-border"></li>
                     <li class="ash-yuan">2</li>
                     <li class="ash-word">选择支付方式</li>
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
                            <li><input type="password" class="int-medium" placeholder="请输入密码"></li>
                            <li class="lable"><img src="${_slpbase }/images/icon-c.png"><span>请填写不少于10元的整数金额</span></li>
                        </ul>
                          <ul>
                            <li class="checx-word"><input type="button" class="slp-btn regsiter-btn" value="下一步" onclick="location.href='${_base}/account/recharge/two'"></li>
                        </ul>
                    
             </div>      
      </div>

  </div>  
     