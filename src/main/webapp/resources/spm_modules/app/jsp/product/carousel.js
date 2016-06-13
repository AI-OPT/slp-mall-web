var timer = null;
var offset = 5000;
var index = 0; /* -1第一个，0第二个... */
var imageNum = 0;

//大图交替轮换
function slideImage(i){
    var id = 'image-'+ i;
    $('#'+ id).animate({opacity: 1}, 800, function(){
		$(this).find('.word').animate({height: 'show'}, 'slow');
	}).show()
	.siblings(':visible')
	.find('.word').animate({height: 'hide'},'fast',function(){
		$(this).parent().animate({opacity: 0}, 800).hide();
	});
}
//bind thumb a
function hookThumb(){    
    $('#thumbs li a').bind('click', function(){
		if (timer) {
			clearTimeout(timer);
		}                
		var id = this.id;            
		index = getIndex(id);
		rechange(index);
		slideImage(index); 
		timer = window.setTimeout(auto, offset);  
		this.blur();            
		return false;
	});
}
//bind next/prev img
function hookBtn(){
    $('#thumbs li i').filter('#play_prev,#play_next').bind('click', function(){
		if (timer){
			clearTimeout(timer);
		}
		var id = this.id;
		if (id == 'play_prev') {
			if(imageNum > 4 && index>4){
				document.getElementById("thumb-"+index).parentElement.style.display="none"; 
				var beginId = index-4;
				document.getElementById("thumb-"+beginId).parentElement.style.display=""; 
			}
			index--;
			if (index == 0) {
				index = imageNum;
				if(imageNum>4){
					//将之前的隐藏
					for(var i=1;i<=imageNum-4;i++){
						document.getElementById("thumb-"+i).parentElement.style.display="none"; 
					}
					//显示后4图
					for(var i=imageNum-3;i<=imageNum;i++){
						document.getElementById("thumb-"+i).parentElement.style.display=""; 
					}
				}
			}
		}else{
			index++;
			if(imageNum > 4 && index>4 && index<=imageNum){
				document.getElementById("thumb-"+index).parentElement.style.display=""; 
				var beginId = index-4;
				document.getElementById("thumb-"+beginId).parentElement.style.display="none"; 
			}
			if (index > imageNum) {
				index = 1;
				if(imageNum>4){
					//显示前4图
					for(var i=1;i<=4;i++){
						document.getElementById("thumb-"+i).parentElement.style.display=""; 
					}
					//将4个之后的隐藏
					for(var i=5;i<=imageNum;i++){
						document.getElementById("thumb-"+i).parentElement.style.display="none"; 
					}
				}
			} 
		}
		rechange(index);
		slideImage(index);
		timer = window.setTimeout(auto, offset);
	});
}

//get index
function getIndex(v){
	var num = v.indexOf("-");
    return v.substring(num+1);
}

function rechange(loop){
    var id = 'thumb-'+ loop;
    $('#thumbs li a.current').removeClass('current');
    $('#'+ id).addClass('current');
}

function auto(){
    index++;
    if(imageNum > 4 && index>4 && index<=imageNum){
		document.getElementById("thumb-"+index).parentElement.style.display=""; 
		var beginId = index-4;
		document.getElementById("thumb-"+beginId).parentElement.style.display="none"; 
	}
	if (index > imageNum) {
		index = 1;
		if(imageNum>4){
			//显示前4图
			for(var i=1;i<=4;i++){
				document.getElementById("thumb-"+i).parentElement.style.display=""; 
			}
			//将4个之后的隐藏
			for(var i=5;i<=imageNum;i++){
				document.getElementById("thumb-"+i).parentElement.style.display="none"; 
			}
		}
	} 
    rechange(index);
    slideImage(index);
    timer = window.setTimeout(auto, offset);
}

