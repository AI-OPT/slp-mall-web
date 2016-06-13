<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>账户余额</title>
<%@ include file="/inc/inc.jsp" %>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet" type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var url = "${_base}/account/test";
	//alert(url);
	$(document).ready(function(){
		//
		//alert('aaaa');
		/* $.ajaxtext(url,"{aa:'ssss'}",function(data){
			alert(data);
			//$('#HH').html(data);
		}); */
		
	});
	/* function getjson() {  
	    $.ajax( {  
	        type : "post",  
	        url : url,  
	        dataType:"json",  
	        success : function(msg) {  
	            alert(msg);  
	        }  
	    });  
	} */  
</script>
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
 <!--账户余额右侧-->  
   <%@ include file="/jsp/account/balance/account-balance-right.jsp" %>
   
    </div>
     </div>
 </div>
   <!--底部-->
    <%@ include file="/inc/foot.jsp" %>
   
   <!--底部 结束-->
   <DIV ID="HH"></DIV>
</body>

</html>
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

