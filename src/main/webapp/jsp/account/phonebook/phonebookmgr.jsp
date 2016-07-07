<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>通讯录管理</title>
<%@ include file="/inc/inc.jsp"%>
<%-- <script src="${_base}/resources/spm_modules/app/jsp/balance/phonebook/phonebook.js" type="text/javascript"></script> --%>
<link href="${_slpbase }/styles/modular.css" rel="stylesheet"
	type="text/css">
<link href="${_slpbase }/styles/global.css" rel="stylesheet"
	type="text/css">
<link href="${_slpbase }/styles/frame.css" rel="stylesheet"
	type="text/css">
<link href="${_slpbase }/styles/font-awesome.css" rel="stylesheet"
	type="text/css">
</head>

<body>
	<!--顶部菜单-->
	<%@ include file="/inc/top-menu.jsp"%>
	<!--顶部菜单结束-->

	<!--导航区域-->
	<%@ include file="/inc/user-nav.jsp"%>
	<!--导航区域结束-->
	<!--订单详情-->
	<div class="fsast-charge">
		<div class="big-wrapper">
			<!--内侧居中框架-->
			<!--左侧-->
			<%@ include file="/inc/user-leftmenu.jsp"%>
			<!--左侧结束-->
			<!--右侧 begin-->
			<div class="my-order-cnt">
				<div class="payment-title">
					<p>
						<a href="${_base}/myorder/list">账户中心</a>&gt;
					</p>
					<p>
						<a href="javascript:void(0)">账户设置</a>&gt;
					</p>
					<p>
						<a href="${_base}/account/phonebook/phonebookmgr">通讯录管理</a>
					</p>
				</div>

				<!--白色背景-->
				<div class="order-list-bj">
					<!--按钮组-->
					<div class="buttong-oup">
						<p>您最多可添加10个通讯录组，每个通讯录组下可添加1000个联系人</p>
						<ul>
							<li class="batch"><a href="javascript:void(0)" id="HREF_ADD_TEL_GROUP">添加通讯录组</a></li>
						</ul>
					</div>
					<div class="account-table">
						<input id="deleteGroupId" style="display:none" />
						<table width="100%" border="0">
							<tr class="bj">
								<td>序列号</td>
								<td>通讯录组名</td>
								<td>联系人数量</td>
								<td>最后更新日期</td>
								<td>操作</td>
							</tr>
							<tbody id="TBODY_TEL_GROUP">
							</tbody> 
						</table>
					</div> 
				</div>

			</div>

			<!--右侧 end-->
		</div>
	</div> 
	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>

	<!--底部 结束-->

<!--添加通信录弹出框  开始-->
	<div class="eject-big">
		<div class="eject-medium" id="addTelGroupDiv">
			<div class="eject-medium-title">
				<p>添加通讯录</p>
				<p class="img">
					<A href="javascript:pager._hiddenDialog('addTelGroupDiv')"></A>
				</p>
			</div>
			<div class="eject-medium-list">
				<div class="medium-list-form">
					<ul>
						<li>
							<p>请输入通讯录组名称:</p>
							<p>
								<input type="text" class="int-medium" id="TEL_GROUP_NAME" maxLength="20">
							</p> <label><img src="${_slpbase }/images/icon-c.png"><span
								class="ash">2-20个字符，可用汉字、字母、数字、“-”、“_”的组合</span></label> <label id="LBL_ADD_TEL_GROUP" style="display:none"><img
								src="${_slpbase }/images/icon-a.png"><span class="red" id="SPAN_ADD_TEL_GROUP_TIP"></span></label>

						</li>
						<li><input type="button" class="slp-btn qu-btn" value="确定新增" id="BTN_ADD_TEL_GROUP"></li>
					</ul>
				</div>

			</div>
		</div>
		<div class="eject-mask"></div>
	</div>
	<!--添加通信录弹出框 结束-->	 
	
	<script id="TelGroupImpl" type="text/x-jsrender">
								<tr class="current">
									<td width="10%">{{:#index+1}}</td>
									<td><span id="SPAN_TEL_GROUP_TEXT_{{:telGroupId}}">{{:telGroupName}}</span><span id="SPAN_TEL_GROUP_INPUT_{{:telGroupId}}" style="display:none"><input type="text" value="{{:telGroupName}}" id="INPUT_TEL_GROUP_{{:telGroupId}}" class="table-int-mini" maxLength="20"/><input type="button" name="BTN_SAVE_TEL_GROUP" telGroupId="{{:telGroupId}}" class="mail-btn" value="保存"></span>
										<div id="name_error_{{:telGroupId}}" class="ejecr-pos-border" style="display: none;"></div>
										<a href="javascript:void(0)" class="click"><i class="icon-pencil" name="BTN_MODIFY_TEL_GROUP" id="BTN_MODIFY_TEL_GROUP_{{:telGroupId}}" telGroupId="{{:telGroupId}}"></i></a>
										<a href="javascript:void(0)" class="trash-close"><i class="icon-trash" name="BTN_DEL_TEL_GROUP" telGroupId="{{:telGroupId}}"></i></a></td>
									<td>{{:count}}</td>
									<td>{{:updateTimeStr}}</td>
									<td>
										<a href="javascript:pager._searchPhoneBooksDetail('{{:telGroupId}}','{{:telGroupName}}')">管理联系人</a>
										<a href="javascript:void(0)" class="charge-phone">充话费</a>
										<a href="javascript:void(0)" class="charge-flow">充流量</a>
									</td>
								</tr>
	
</script>
	
	
	<script type="text/javascript">
	var pager;
	(function () {
		seajs.use('app/jsp/balance/phonebook/phonebookmgr', function (PhoneBookMgrPager) {
			pager = new PhoneBookMgrPager({userId:123,element: document.body});
			pager.render();
		});
	})();
	</script>
	<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>
</body>

</html>
<%-- <script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script> --%>


