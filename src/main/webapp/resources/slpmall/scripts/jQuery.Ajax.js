$.ajaxjson = function (url, dataMap, fnSuccess) {
    $.ajax({
        type: "POST",
        url: url,
        data: dataMap,
        dataType: "json",
        beforeSend: function () { },
        complete: function () { },
        success: fnSuccess,
        error : function (XMLHttpRequest, textStatus, errorThrown) {
			//layer.alert('提示<br>操作出现异常!<br> readyState:'+ XMLHttpRequest.readyState+ "<br>status:"+XMLHttpRequest.status +"<br>textStatus:"+textStatus);
			//layer.msg('数据加载中，请稍待...', {icon: 1}); 
		}
    });
}
$.ajaxtext = function (url, dataMap, fnSuccess) {
    $.ajax({
        type: "POST",
        url: url,
        data: dataMap,
        dataType:"text",
        beforeSend: function () { },
        complete: function () { },
        success: fnSuccess
    });
}

//主要是推荐这个函数。它将jquery系列化后的值转为name:value的形式。
function convertArray(o) { 
    var v = {};
    for (var i in o) {
        if (o[i].name != '__VIEWSTATE') {
            if (typeof (v[o[i].name]) == 'undefined')
                v[o[i].name] = o[i].value;
            else
                v[o[i].name] += "," + o[i].value;
        }
    }
    return v;
}

/*
随机字符串 
length : 字符串长度
*/
function randomString(length) {
    var chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    var size = length || 8;
    var i = 1;
    var ret = "";
    while (i <= size) {
        var max = chars.length - 1;
        var num = Math.floor(Math.random() * max);
        var temp = chars.substr(num, 1);
        ret += temp;
        i++;
    }
    return ret;
}
