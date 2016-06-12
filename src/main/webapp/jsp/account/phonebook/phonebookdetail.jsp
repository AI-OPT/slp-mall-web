<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>通讯录管理</title>
<%@ include file="/inc/inc.jsp"%>
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
						<a href="#">账户中心</a>&gt;
					</p>
					<p>
						<a href="#">账户设置</a>&gt;
					</p>
					<p>
						<a href="#">通讯录管理</a>&gt;
					</p>
					<p>公司同事-市场部（45）</p>
				</div>

				<div class="mail-bj">
					<!--白色背景-->
					<!--通讯录查询-->
					<div class="mail-title">
						<ul>
							<li>
								<p class="word">号码归属地</p>
								<p>
									<select class="select-small" id="provinceCode">
										<option value="">请选择</option>
										
									</select>
								</p>
							</li>
							<li>
								<p class="word">号码运营商</p>
								<p>
									<select class="select-small" id="basicOrgId">
										<option value="">请选择</option> 
									</select>
								</p>
							</li>
							<li>
								<p class="word">联系人姓名</p>
								<p>
									<input type="text" class="int-small" id="telName">
								</p>
							</li>
							<li>
								<p class="word">手机号</p>
								<p>
									<input type="text" class="int-small" id="telMp">
								</p>
							</li>
							<li><input type="button" class="order-btn" value="搜索" id="BTN_QUERY"></li>
						</ul>
					</div>
					<!--/通讯录查询结束-->
				</div>
				<!--白色背景-->
				<div class="order-list-bj">
					<!--按钮组-->
					<div class="buttong-oup">
						<ul>
							<li class="add"><a href="javascript:void(0)" id="BTN_ADD_PHONEBOOK">添加联系人</a></li>
							<li class="batch"><a href="javascript:void(0)" id="BTN_BATCH_IMPORT">批量导入联系人</a></li>
							<li class="click-close"><a href="javascript:void(0)" id="BTN_DELETE">删除</a></li>
							<li><a href="javascript:void(0)" id="BTN_REFRESH">刷新</a></li>
						</ul>
					</div>
					<div class="account-table">
						<table width="100%" border="0">
							<tr class="bj">
								<td width="10%"><input type="checkbox" id="CHECK_ALL"
									class="checkbox-medium" style="display: inline-block;">全选</td>
								<td>序列号</td>
								<td>姓名</td>
								<td>手机号</td>
								<td>归属地</td>
								<td>运营商</td>
								<td>操作</td>
							</tr>
							<tbody id="TBODY_PHONEBOOKS">
								
							</tbody> 
						</table>
					</div>

					<div class="paging-large">
					 <ul id="pagination-ul"></ul>
				  </div>


				</div>

			</div> 
			<!--右侧 end-->
		</div>
	</div> 
	<!--底部-->
	<%@ include file="/inc/foot.jsp"%>

	<!--底部 结束-->
	
	
		<!--弹出删除弹出框  中-->
	<div class="eject-big">
		<div class="eject-medium">
			<div class="eject-medium-title">
				<p>批量导入通讯录</p>
				<p class="img">
					<A href="#"></A>
				</p>
			</div>
			<div class="eject-medium-list">
				<div class="medium-list-form">
					<ul>
						<li>
							<p>上传通讯录文件:</p>
							<p>
								<input type="text" class="int-medium" id="TEXT_FILE_NAME" readonly>
							</p>
							<p class="btn-file">
								<input type="button" class="eject-liul" value="浏览"><input
									type="file" class="file" id="uploadFile">
							</p>
						</li>
						<li><input type="button" class="slp-btn qu-btn" id="uploadBtn" value="确认上传"></li>
					</ul>
				</div>
				<div class="medium-list-word">
					<ul>
						<li>注意：</li>
						<li>1.系统支持导入xls/xlsx格式的文件。<a href="#">下载通讯录文件模板.xlsx。</a></li>
						<li>2.字段顺序分别为姓名、手机号,文件包含重复手机号时，系统只取一条。姓名列允许为空或出现相同名字，若一个人有多个手机号，请分多行来输入。</li>
						<li>3.每个文件最多含有1000条记录。</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="eject-mask"></div>
	</div>
	<!--弹出删除弹出框  中结束-->
	
	
		<!--弹出删除弹出框 大-->
	<div class="eject-big" id="CLOSE_ADD_PHONEBOOK_WINDOW">
		<div class="eject-large">
			<!--弹出多行-->
			<div class="eject-big">
				<div class="eject-samll" id="samll-block">
					<div class="samll-block-title">
						<p>添加多行</p>
						<p class="img">
							<A href="#"></A>
						</p>
					</div>
					<!--确认删除-->
					<div class="eject-samll-confirm">
						<ul>
							<li class="word block-left">请输入您要添加的行数：</li>
							<li class="block-left"><input type="text" id="INPUT_ROW"
								class="table-int-mini">行 （2到100之间的整数）</li>
							<li><input type="button" class="slp-btn eject-small-btn"
								value="确认" id="BTN_INPUT_ROW"><input type="button"
								class="slp-btn eject-small-btn close-btn1" value="取消"></li>
						</ul>
					</div>
				</div>

			</div>
			<!--弹出多行-->
			<div class="eject-large-title">
				<p>添加通讯录</p>
				<p class="img">
					<A href="#"></A>
				</p>
			</div>
			<div class="eject-large-list">
				<div class="buttong-oup eject-oup">
					<ul>
						<li><a href="javascript:void(0)" id="HREF_ADD_ONE">添加一行</a></li>
						<li class="multi-line"><a href="javascript:void(0)" id="HREF_ADD_BATCH">添加多行</a></li>
					</ul>
				</div>

				<div class="eject-large-table ">
					<div class="account-table ejecttable">
						<table width="100%" border="0">
							<thead>
								<tr class="bj">
									<td>序列号</td>
									<td>姓名</td>
									<td><span style="color: #f00">*</span>手机号</td>
									<td width="20%"></td>
								</tr>
							</thead>
							<tbody id="TBODY_PhoneBooksBatchEdit">
							
							</tbody>
						</table>
					</div>
				</div>
				<div class="large-btn">
					<p>
						<input type="button" class="slp-btn large-qu-btn" id="BTN_SAVE_BATCH_ADD" value="保存添加">
					</p>
				</div>
			</div>
		</div>
		<div class="eject-mask" id="WINDOW_PHONEBOOK_BATCHEDIT"></div>
	</div>
	<!--弹出删除弹出框 大结束-->

	<script type="text/javascript"> 
	(function () {
		seajs.use('app/jsp/balance/phonebook/phonebookdetail', function (PhoneBookDetailPager) {
			var pager = new PhoneBookDetailPager({userId:1000,telGroupId:"<c:out value="${telGroupId}"/>",element: document.body});
			pager.render();
		});
	})();
	</script>
</body>

</html>
<script src="${_slpbase }/scripts/frame.js" type="text/javascript"></script>
<script src="${_slpbase }/scripts/flickity-docs.min.js"></script>

<script id="PhoneBooksImpl" type="text/x-jsrender">
								<tr class="current">
									<td width="10%"><input type="checkbox" name="CHEK_TEL_NO"
										class="checkbox-medium" value="{{:telNo}}"></td>
									<td>{{:telNo}}</td>
									<td>{{:telName}}</td>
									<td>{{:telMp}}</td>
									<td>{{:provinceName}}</td>
									<td>{{:basicOrgName}}</td>
									<td><a href="#" class="click">编辑</a></td>
								</tr> 
	
</script>

<script id="PhoneBooksBatchEditImpl" type="text/x-jsrender">
<tr>
								<td>{{:#index}}</td>
								<td><input type="text" class="table-int-mini" index="{{:#index}}" name="BATCH_TEL_NAME" value="{{:telName}}"></td>
								<td><input type="text" class="table-int-mini" index="{{:#index}}" name="BATCH_TEL_MP" value="{{:telMp}}"></td>
								<td class="eject-table-img" align="left"><span style="color:red" id="SPAN_ERROR_{{:#index}}">{{:error}}</span><a href="javascript:void(0)"  index="{{:#index}}" name="DEL_BATCH_EDIT_ROW">删除</a></td>
							</tr>
</script>

