define('app/jsp/drSubject/addDrSubject', function (require, exports, module) {
    'use strict';
    var $=require('jquery'),
        Widget = require('arale-widget/1.2.0/widget'),
        Dialog = require("artDialog/src/dialog"),
        Validator = require('arale-validator/0.10.2/index-debug'),
        AjaxController=require('opt-ajax/1.0.0/index');

    require("jsviews/jsrender.min");
    require("jsviews/jsviews.min");

    //实例化AJAX控制处理对象
    var ajaxController = new AjaxController();

    //定义页面组件类
    var AddDrSubjectPager = Widget.extend({
        //属性，使用时由类的构造函数传入
        attrs: {
        },
        //事件代理
        events: {
            //key的格式: 事件+空格+对象选择器;value:事件方法
        	 "click #submitBtn":"submitAddTable",
             "click #cancelBtb":"cancelAdd"
        },
        //重写父类
        setup: function () {
            AddDrSubjectPager.superclass.setup.call(this);
            this._initPage();
            //this._bindValidator();
        },
        cancelAdd:function(){
            var url = _base + "/drSubject/list";
            window.location.href = url;
         },
         _initPage: function(){
        	//面包屑导航
         	var _subjectId=$("#subjectId").val();
         	if(_subjectId!=undefined&&_subjectId!=""&&_subjectId!="0"){
         		setBreadCrumb("计费配置管理","修改详单科目");
         	}
         	else{
         		setBreadCrumb("计费配置管理","新增详单科目");
         	}
     		//左侧菜单选中样式
     		$("#mnu_bmc_config").addClass("current");
     	},
         _bindValidator: function(){
    		    var validator = new Validator({
    		        element: '#discount-step2',
    		        onFormValidated: function(err, results, form) {
    		            window.console && console.log && console.log(err, results, form);
    		        },
    		        failSilently: true
    		    });
    		    
    		    validator.addItem({
    		        element: '#subjectName',
    		        required: true,
    		        rule: 'minlength{min: 5} maxlength{max:20}',
//    		        alertFlag: true
    		    })

     	},
         
        submitAddTable:function(){
            var subjectName = $("#subjectName").val();
            var subjectDesc = $("#subjectDesc").val();
            var subjectId = $("#subjectId").val();
            var param = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;   
            if(subjectName == ""){
                var d = Dialog({
                    content:"科目名称不能为空",
                    ok:function(){
                        this.close();
                    }
                });
                d.showModal();
                return;
            }else if(subjectName.length >15){
            	 var d = Dialog({
                     content:"科目名称长度不能超过15",
                     ok:function(){
                         this.close();
                     }
                 });
                 d.showModal();
                 return;
            }
            if(!param.test(subjectName)){
            	var d = Dialog({
                    content:"科目名称只能输入中文、字母、数字",
                    ok:function(){
                        this.close();
                    }
                });
                d.showModal();
                return;
            	
            }
            ajaxController.ajax({
                type: "post",
                dataType : "json",
                url: _base+"/drSubject/addDrSubject",
                processing: true,
                message: "正在加载，请等待...",
                data:{
                	subjectName: subjectName,
                	subjectId:subjectId,
                    subjectDesc:subjectDesc
                },
                success: function(data){
                    if(data){
                    	var obj  = data.data.responseHeader;
                    	var content = obj.resultMessage ;
                        var d = Dialog({
                            content:content,
                            ok:function(){
                                window.location.href = _base+"/drSubject/list";
                            }
                        });
                        d.showModal();
                    }
                }
            });
        }
    });

    module.exports = AddDrSubjectPager;
});
