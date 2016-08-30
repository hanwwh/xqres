define(["jquery","jqueryUI","My97DatePicker","jqueryExtend","ajaxFileUpload"], function(require, exports, module) {
	//发布函数
	//module作为seajs中的一个对象，exports为module的一个属性
	//该操作给export赋值，并实例化
	//init，checkUser作为export的属性
	//{}表示给对象赋值并实例化，[]表示数组，()表示函数传参
    var jquery = require("jquery");
	module.exports={
		init : function(){
            $("#iframeid").on("load", function(){
                // alert("0");
                $(this).height(0); //用于每次刷新时控制IFRAME高度初始化
                var height = $(this).contents().height() + 20;
                $(this).height( height < 500 ? 500 : height );
            });

            $("a[id$='_id']").on("click", function() {
                // alert($(this).attr("id"));
                var id_value = $(this).attr("id");
                // alert(id_value);
                var url = _ctx+"/web/index.do?id="+id_value;
                // alert(url);
                $("#iframeid").attr("src", url);
                /*$.ajax({
                    type : "GET",
                    url : url,
                    cache : false,
                    success : function(msg){
                        alert(msg);
                        $("#iframeid").attr("src", _ctx+msg);
                    }
                });*/

                /*if(id_value == "summary_id"){//首页
                    $("#iframeid").attr("src", _ctx+"/web/summary.do");
                } else if(id_value == "item_id"){//订单管理
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                } else if(id_value == "food_id"){//菜品管理
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                } else if(id_value == "user_id"){//用户管理
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                } else if(id_value == "manage_id"){//管理中心
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                } else if(id_value == "login_id"){//登陆
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                } else if(id_value == "register_id"){//注册
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                } else if(id_value == "logout_id"){//退出
                    $("#iframeid").attr("src", _ctx+"/order/printInputOrderIdx.do");
                }*/
            });
		},
		
		checkUser : function(){
			
		}
	};
	
});