//我的订单  账户设置 点击展开
 $(function () {
    var st = 100;
    $('.fsast-bav ul .shez a').mouseenter(function () {
		$(this).children('i').toggleClass("icon-angle-down  icon-angle-up");
		$('.setgs').show(1);
    })
		$(".fsast-bav .setgs").click(function () {
                $(this).hide(1);
            });
		$('.fsast-bav ul .shez').mouseleave(function () {
		$(this).children('i').toggleClass("icon-angle-up icon-angle-down");
        $('.fsast-bav .setgs').hide(1);
    });	
  });

/**限制字数个数**/


(function($) {	
	$.fn.fonts = function(option){
		option = $.extend({},$.fn.fonts.option,option);
		return this.each(function(){
		var objString = $(this).text(),
		    objLength = $(this).text().length,
			num = option.fontNum;
		if(objLength > num){
            objString = $(this).text(objString.substring(0,num) + "…");
		}
		 })
	}
	// default options
	$.fn.fonts.option = {
	fontNum:100 //font num
	};
})(jQuery);