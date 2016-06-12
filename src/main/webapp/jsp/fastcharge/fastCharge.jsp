<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>快充</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase}/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase}/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script src="${_slpbase}/scripts/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="${_slpbase}/scripts/frame.js" type="text/javascript"></script>
<script type="text/javascript">
			var pager;
			(function () {
				seajs.use('app/jsp/fastcharge/fastCharge', function (FastchargePager) {
					pager = new FastchargePager({element: document.body});
					pager.render();
				});
			})();
			
		</script>
</head>

<body>
<!--顶部菜单开始-->
<%@ include file="/inc/top-menu.jsp" %>
<!--顶部菜单结束-->

<!--导航区域-->
<div class="mainbav-bj">
 <div class="mainbav">
      <div class="logo"><img src="${_slpbase }/images/logo.png"></div>
       <!-- 主导航 -->
    	<%@ include file="/inc/logo-nav-menu.jsp" %>
    	<!-- 结束 -->
 </div>
 </div>
<!--导航区域结束-->
     
     <!--快充-->
     <div class="fsast-charge"><!--外侧框架-->
       <div class="big-wrapper"><!--内侧居中框架-->
       <div class="recharge-bj">
           <div class="center-main">
                  <div class="center-table">
                        <ul>
                        <li><A href="#" id="phoneBill">话费充值</A></li>
                        <li><A href="#" id="flowBill">流量充值</A></li>
                        </ul>
                        <input type="hidden" name="flowFastFlag" id="flowFastFlag" value="${requestScope.flowFastFlag}"/>
                   </div>
                <!--table1-->
                <div id="regeiter-date1">
           	 <div class="recharge-list">
                    <ul>
                        <li class="word" >请输入充值手机号:</li>
                        <li><input type="text" id="phoneNum1" class="recharge-xlarge"></li>
                        <li><a href="#">给多人充值</a></li>
                    </ul>
                    <ul>
                        <li class="word">充值额:</li>
                        <li class="bder" id="listHfee">
                         
                           <!--  <p class="hfee current"><a href="javascript:void(0);">500元</a></p>
                            <p  class="hfee"><a href="javascript:void(0);">200元</a></p>
                            <p class="hfee"><a href="javascript:void(0);">300元</a></p>
                            <p class="hfee"><a href="javascript:void(0);">100元</a></p>
                            <p class="hfee"><a href="javascript:void(0);">50元</a></p>
                            <p class="hfee"><a href="javascript:void(0);">30元</a></p>
                            <p class="hfee"><a href="javascript:void(0);">20元</a></p>
                            <p class="hfee"><a href="javascript:void(0);">10元</a></p> -->
                        </li>
                    </ul>
                  
                    <ul class="color">
                        <li class="word">价格:</li>
                        <li><span  id="hPrice">￥98.00-￥99.8</span></li>
                    </ul>
                    <ul>
                        <li class="rech-btn"><input type="button" class="slp-btn cz-btn" value="立即充值"></li>
                    </ul>
                
                </div>
                </div>
                <!--table2-->
                <div id="regeiter-date2" style=" display:none;">
               <div class="recharge-list">
                    <ul>
                        <li class="word" >请输入充值手机号:</li>
                        <li><input type="text" id="phoneNum2" class="recharge-xlarge"></li>
                        <li><a href="#">给多人充值</a></li>
                    </ul>
                    <ul>
                        <li class="word">流量额:</li>
                        <li class="bder" id="listLfee">
                         
                           <!--  <p><a href="#">1G</a></p>
                            <p><a href="#">2G</a></p>
                            <p><a href="#">3G</a></p>
                            <p class="Lfee current"><a href="#">500M</a></p>
                            <p><a href="#">200M</a></p>
                            <p><a href="#">300M</a></p>
                            <p><a href="#">100M</a></p>
                            <p><a href="#">50M</a></p>
                            <p><a href="#">30M</a></p>
                            <p><a href="#">20M</a></p>
                            <p><a href="#">10M</a></p> -->
                        </li>
                    </ul>
                    <ul>
                        <li class="word">使用范围:</li>
                         <li><input type="radio" name="flowRadio" value="local"  checked><span class="qg">省内</span></li>
                        <li><input type="radio" name="flowRadio" value="national"><span class="qg">全国</span></li>
                       
                    </ul>
                    <ul class="color">
                        <li class="word">价格:</li>
                        <li><span id="lPrice">￥98.00-￥99.8</span></li>
                    </ul>
                    <ul>
                        <li class="rech-btn"><input type="button" class="slp-btn cz-btn" value="立即充值"></li>
                    </ul>
                
                </div>
                </div>
           
           </div>
     
     </div>
     </div>
   </div>
    <script id="hfeeDataTmpl" type="text/x-jsrender">
           <p skuId="{{:skuInfo.skuId}}" salePrice="{{:skuInfo.salePrice}}" class="hfee"><a href="javascript:void(0);">{{:content}}</a></p>
 	</script>
  <script id="lfeeDataTmpl" type="text/x-jsrender">
           <p  skuId="{{:skuInfo.skuId}}" salePrice="{{:skuInfo.salePrice}}" class="lfee"><a href="javascript:void(0);">{{:content}}</a></p>
 </script>
<%@ include file="/inc/foot.jsp" %>

</body>
</html>



