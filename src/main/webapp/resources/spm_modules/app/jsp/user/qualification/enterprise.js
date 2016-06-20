function uploadImg(imageId) {
	if(document.getElementById(imageId).value!=""){
	 $.ajaxFileUpload({  
         url:_base+"/user/qualification/uploadImg",  
         secureuri:false,  
         fileElementId:imageId,//file标签的id  
         dataType: "json",//返回数据的类型  
         //data:{imageId:imageId},//一同上传的数据  
         success: function (data, status) {
        	if(data.isTrue==true){
        		document.getElementById("image").src=data.url;
        		$("#idpsId").val(data.idpsId);
        	 }else{
        		 alert("error");
        	 }
         },  
         error: function (data, status, e) {  
             alert(e);  
         }  
     });  
	}
}

	function deleteImg(imageId){
		var ipdsId = $("#ipdsId").val();
		if(document.getElementById(imageId).value!=""){
		$.ajax({
	        type: "post",
	        processing: false,
	        url: _base+"/user/qualification/deleteImg",
	        dataType: "json",
	        data: {"ipdsId":ipdsId},
	        message: "正在加载数据..",
	        success: function (data) {
	        	if(data.isTrue==true){
	        		var url = getRealPath();
	        		document.getElementById("image").src=url+'/resources/slpmall/images/fom-t.png';
	        		var obj = document.getElementById(imageId);
	        		obj.outerHTML=obj.outerHTML; 
	        		$("#ipdsId").val("");
	        	}
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
				}
			    }); 
		}
	}
	
	
	function submit(){
		alert(11);
	}
	
	
	
	
	
	
	
	
	
	
	
	//获取当前项目根路径
	function getRealPath(){
		  //获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
		   var curWwwPath=window.document.location.href;
		   //获取主机地址之后的目录，如： myproj/view/my.jsp
		  var pathName=window.document.location.pathname;
		  var pos=curWwwPath.indexOf(pathName);
		  //获取主机地址，如： http://localhost:8083
		  var localhostPaht=curWwwPath.substring(0,pos);
		  //获取带"/"的项目名，如：/myproj
		  var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		 
		 //得到了 http://localhost:8083/myproj
		  var realPath=localhostPaht+projectName;
		  return realPath;
		}