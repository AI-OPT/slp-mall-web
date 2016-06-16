<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <div class="logo">
    	<a href="${_base}">
    	<img src="${_slpbase}/images/logo.png">
    	</a>
    </div>
    <!--导航 搜索区-->
      <div class="mainbav-main">
      <!--搜索区-->
          <div class="searchBar">
              <ul class="searchTxt">
                  <li><input type="text" class="int-xxlarge" id="serachName"></li>
                  <li><A href="javascript:void(0);" id="BTN_SEARCH"><i class="icon-search"></i></A></li>
              </ul>
               <ul class="word">
                  <li><A href="javascript:void(0);" onclick="index_search_pager._hotWordSearch('充流量')">充流量</A></li>
                  <li><A href="javascript:void(0);" onclick="index_search_pager._hotWordSearch('充话费')">充话费</A></li>
                  <li><A href="javascript:void(0);" onclick="index_search_pager._hotWordSearch('50元')">50元</A></li>
                  <li><A href="javascript:void(0);" onclick="index_search_pager._hotWordSearch('100M')">100M</A></li>   
              </ul>
          </div>
          <!--搜索区结束-->
          <!--主导航-->
          <div class="breadcrumb">
              <ul>
                  <li><a href="${_base}">首页</a></li>
                  <li><a href="javascript:void(0);" id="logo_nav_phoneBillFastId">话费快充</a></li>
                  <li><a href="javascript:void(0);" id="logo_nav_flowFastId">流量快充</a></li>
                  <li><a href="#">话费卡</a></li>
                  <li><a href="#">流量卡</a></li>
                  <li><a href="#">API</a></li>
              </ul>
          </div>
          <!--主导航结束-->
     </div>
     <script type="text/javascript">
			var logo_nav_menu_pager;
			(function () {
				seajs.use('app/jsp/logonavmenu/logoNavMenu', function (HeadPager) {
					logo_nav_menu_pager = new HeadPager({element: document.body});
					logo_nav_menu_pager.render();
				});
			})();
			
		</script>
		<script type="text/javascript">
			var index_search_pager;
			(function () {
				seajs.use('app/jsp/search/search', function (SearchPager) {
					index_search_pager = new SearchPager({element: document.body});
					index_search_pager.render();
				});
			})();
			
		</script>