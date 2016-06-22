<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--底部-->
  <div class="footer-wrapper">
 <!--底部－help-->
      <div class="footer">
        <div class="footer-main">
          <div class="footer-title">
              <ul>
                  <li>
                      <p><img src="${_slpbase }/images/foot-a.png"></p>
                      <p>话费流量全面支持</p>
                  </li>
                  <li>
                      <p><img src="${_slpbase }/images/foot-b.png"></p>
                      <p>价格更低优惠更多</p>
                  </li>
                  <li>
                      <p><img src="${_slpbase }/images/foot-c.png"></p>
                      <p>即时到账安全便捷</p>
                  </li>
                  <li>
                      <p><img src="${_slpbase }/images/foot-d.png"></p>
                      <p>企业充值轻松无忧</p>
                  </li>
              </ul>
          </div>
          
          <div class="footer-title-list">
          <ul>
          <li class="word">商品分类</li>
          <li><a href="${_base }/head/fastCharge?flowFastFlag=false">话费快充</a></li>
          <li><a href="${_base }/head/fastCharge?flowFastFlag=true">流量快充</a></li>
          <li><a href="javascript:void(0);" onclick="index_search_pager._hotWordSearch('话费卡')">话费卡</a></li>
          <li><a href="javascript:void(0);" onclick="index_search_pager._hotWordSearch('流量卡')">流量卡</a></li>
          </ul>
          <ul>
          <li class="word">帮助中心</li>
          <li><a href="#">话费充值</a></li>
          <li><a href="#">账户使用</a></li>
          <li><a href="#">支付购买</a></li>
          <li><a href="#">订单相关</a></li>
          </ul>
          <ul>
          <li class="word">商家合作</li>
          <c:choose>
	         <c:when test="${not empty sessionScope.user_session_key.userId }">
	          <li><a href="${_base}/myorder/list">企业采购</a></li>
	          <li><a href="${_base}/myorder/list">代理商申请</a></li>
	          <li><a href="${_base}/myorder/list">供货商合作</a></li>
	          <li><a href="#">招商平台</a></li>
           </c:when>
	         <c:otherwise >
	          <li><a href="${slp_uac_host}/reg/toRegister?userType=11">企业采购</a></li>
	          <li><a href="${slp_uac_host}/reg/toRegister?userType=12">代理商申请</a></li>
	          <li><a href="${slp_uac_host}/reg/toRegister?userType=13">供货商合作</a></li>
	          <li><a href="#">招商平台</a></li>
           </c:otherwise>
         </c:choose>
         
          </ul>
          <ul class="bor-none">
          <li class="word">网站导航</li>
          <li><a href="#">网站地图</a></li>
          <li><a href="http://www.asiainfo.com.cn" target="_blank">亚信官网</a></li>
          <li><a href="http://www.ai-ol.com/" target="_blank">亚信国际</a></li>
          <li><a href="http://www.asiainfodata.com/" target="_blank">亚信数据</a></li>
          </ul>
          </div>
        </div>
      </div>
   <!--底部－about-->
      <div class="footer-alink">
      <ul>
      <li>
      <a href="#">关于我们</a>
      <a href="#">联系我们</a>
      <a href="#">企业采购</a>
      <a href="#">代理商申请</a>
      <a href="#">供货合作</a>
      <a href="#">API文档</a>
      <a href="#">亚信官网</a>
      <a href="#">网站地图</a>
      </li>
      <li>京ICP备11005544号-15                京公网安备110108007119号</li>
      <li>©2016-2018 亚信旗下话费充值平台，版权所有  All Rights Reserved</li>
      </ul>
                                                                                                   
      </div>
    
    
    </div>
   <!--底部 结束-->
   
<link rel="stylesheet" type="text/css" href="${_base}/resources/slpmall/styles/slp-mall.css">