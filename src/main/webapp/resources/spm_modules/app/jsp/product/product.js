//商品详情
$(function(){
$(".parameter-right-tilit ul li").click(function () {
                $(".parameter-right-tilit ul li").each(function () {
                    $(this).removeClass("current");
                });
                $(this).addClass("current");
});
$('.parameter-right-tilit ul li').click(function(){
  var index=$('.parameter-right-tilit ul li').index(this);
      if(index==0){
     $('#date1').show();
     $('#date2').hide();
   }
   if(index==1){
   $('#date2').show();
   $('#date1').hide();
   }
  }); 
});
//table切换结束  

//加入购物车关闭
jQuery(document).ready(function($) {
	$('.eject-medium-title .img').click(function(){
	$('.eject-mask').fadeOut(100);
	$('#shopCartMedium').slideUp(150);
	})
})	