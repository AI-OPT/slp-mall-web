(function($){
	var ms = {
		init:function(obj,args){
			return (function(){
				ms.fillHtml(obj,args);
				ms.bindEvent(obj,args);
			})();
		},
		//填充html
		fillHtml:function(obj,args){
			return (function(){
				obj.empty();
				obj.append('总数：<span>'+args.totalCount+'<span><span class="first"><em></em><a class="firstPage" href="javascript:void(0);">首页</a></span>');
				//上一页
				if(args.current > 1){
					obj.append('<span class="first"><em></em><a href="javascript:;" class="prevPage">上一页</a></span>');
				}else{
					obj.remove('.prevPage');
					obj.append('<span class="disabled">上一页</span>');
				}
				//中间页码
				if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
				}
				if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
					obj.append('<span>...</span>');
				}
				var start = args.current -2,end = args.current+2;
				if((start > 1 && args.current < 4)||args.current == 1){
					end++;
				}
				if(args.current > args.pageCount-4 && args.current >= args.pageCount){
					start--;
				}
				for (;start <= end; start++) {
					if(start <= args.pageCount && start >= 1){
						if(start != args.current){
							obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
						}else{
							obj.append('<span class="current">'+ start +'</span>');
						}
					}
				}
				if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
					obj.append('<span>...</span>');
				}
				if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
					obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
				}
				//下一页
				if(args.current < args.pageCount){
					obj.append('<span class="last"><a href="javascript:;" class="nextPage">下一页</a><em></em></span>');
				}else{
					obj.remove('.nextPage');
					obj.append('<span class="disabled">下一页</span>');
				}
				//
				var argsCurrent = args.current;
				if(args.pageCount == 0){
					argsCurrent = 0
				}
				obj.append('页数：<span id="jump_page_id">'+argsCurrent+'</span>/<span>'+args.pageCount+'</span><span><input id="jump_page" type="text" class="text2" size="3" /></span><a href="javascript:void(0);" class="jumpButton">跳转</a>');
			})();
		},
		//绑定事件
		bindEvent:function(obj,args){
			return (function(){
				//首页
				obj.on("click","a.firstPage",function(){
					var current = parseInt('1');
					
					
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					$('#jump_page_id').text(current);
					args.backFn(current);
					//var current = parseInt($(this).text());
					/*ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}*/
				});	
				//跳转
				obj.on("click","a.jumpButton",function(){
					var jumpNum = $('#jump_page').val();
					if(jumpNum == ''){
						jumpNum = '1';
					}
					var current = parseInt(jumpNum);
					
					//alert($(this).text()+' 至 '+$('#jump_page').val());
					//alert(args.pageCount);
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					//$('#jump_page_id').text(current);
					//$('#jump_page').val(jumpNum);
					args.backFn(current);
					//var current = parseInt($(this).text());
					/*ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}*/
				});
				//点击页码
				obj.on("click","a.tcdNumber",function(){
					var current = parseInt($(this).text());
					ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
					$('#jump_page_id').text(current);
					if(typeof(args.backFn)=="function"){
						args.backFn(current);
					}
				});
				//上一页
				obj.on("click","a.prevPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
					$('#jump_page_id').text(current-1);
					if(typeof(args.backFn)=="function"){
						args.backFn(current-1);
					}
				});
				//下一页
				obj.on("click","a.nextPage",function(){
					var current = parseInt(obj.children("span.current").text());
					ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
					$('#jump_page_id').text(current+1);
					if(typeof(args.backFn)=="function"){
						args.backFn(current+1);
					}
				});
			})();
		}
	}
	$.fn.createPage = function(options){
		var args = $.extend({
			pageCount : 10,
			current : 1,
			pageSize : 2,
			totalCount : 0,
			backFn : function(){}
		},options);
		ms.init(this,args);
	}
})(jQuery);