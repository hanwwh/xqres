define(["jquery","jqueryUI","My97DatePicker","common","ajaxFileUpload"], function(require, exports, module) {
	//发布函数
	//module作为seajs中的一个对象，exports为module的一个属性
	//该操作给export赋值，并实例化
	//init，checkUser作为export的属性
	//{}表示给对象赋值并实例化，[]表示数组，()表示函数传参
	var jquery = require("jquery");
	var jqueryUI = require("jqueryUI");
    var common = require("common");

	var foodids = [];
	module.exports={
		init : function(){
			$.getJSON(_ctx+"/order/selectFoods.do", function(data) {
				// alert(data);
				$.each(data, function(i, v){
					// alert(v.food_id+" = "+v.food_name);
					// $("#item_food_select").append(
					// 	"<option value='"+v.food_id+"' price='"+v.food_price+"'>"+v.food_name+"</option>");
					foodids[i] = v.food_id;
					var label_str = "<label class='col-sm-2 control-label' for='"+v.food_id+"'></label>";
					if(i == 0){
						label_str = "<label class='col-sm-2 control-label' for='"+v.food_id+"'>菜品选择</label>";
					}

					$("#choose_food_div").append(
						"<div class='form-group'>"+
							label_str+
							"<div class='col-sm-2'>"+
								"<input class='form-control' id='"+v.food_id+"' price='"+v.food_price+
									"' type='text' value='"+v.food_name+
									"' data-toggle='tooltip' data-placement='top' title='￥"+v.food_price+"'/>"+
							"</div>"+
							"<div class='btn-group' role='group' aria-label='...' id='food_change_"+v.food_id+"'>"+
								"<button type='button' class='btn btn-default'>"+
									"<span class='badge' id='food_num_"+v.food_id+"'>0</span>"+
								"</button>"+
								"<button type='button' class='btn btn-default' id='minus_btn_"+v.food_id+"'>"+
									"<span class='glyphicon glyphicon-minus-sign' aria-hidden='true'/>"+
								"</button>"+
								"<button type='button' class='btn btn-default' id='plus_btn_"+v.food_id+"'>"+
									"<span class='glyphicon glyphicon-plus-sign' aria-hidden='true'/>"+
								"</button>"+
							"</div>"+
							"<div class='col-sm-1'>"+
								"<input class='form-control' id='food_price_"+v.food_id+
									"' type='text' value='0'/>"+
							"</div>"+
						"</div>"
					);

				});

				// alert(foodids);
				$.each(foodids, function(i, v){
					var food_num_id = "#food_num_"+v;
					var minus_btn_id = "#minus_btn_"+v;
					var food_price_id = "#food_price_"+v;

					$(food_price_id).on("change", function() {
						module.exports.addTotalPrice();
					});

					// alert(minus_btn_id);
					// var minus_btn = $(minus_btn_id);
					// alert(minus_btn);
					$(minus_btn_id).on("click", function () {
						var num = $(food_num_id).text();
						if(num > 0){
							num--;
							$(food_num_id).text(num);

							var price_id = "#"+v;
							var price = parseFloat($(price_id).attr("price"));
							// var item_price_obj = $("#item_price_id");
							// var item_price_v = parseFloat(item_price_obj.val());
							// item_price_obj.val(item_price_v-price);
							$(food_price_id).val(price*num);
							module.exports.addTotalPrice();
						}

					});

					var plus_btn_id = "#plus_btn_"+v;
					// alert(plus_btn_id);
					// var plus_btn = $(plus_btn_id);
					// alert(plus_btn);
					$(plus_btn_id).on("click", function () {
						var num = $(food_num_id).text();
						num++;
						$(food_num_id).text(num);

						var price_id = "#"+v;
						var price = parseFloat($(price_id).attr("price"));
						// var item_price_obj = $("#item_price_id");
						// var item_price_v = parseFloat(item_price_obj.val());
						// item_price_obj.val(item_price_v+price);
						$(food_price_id).val(price*num);
						module.exports.addTotalPrice();
					});

				});


			});

			// $("#item_food_select").on("change", function() {
				// alert($("select option:selected").attr('price'));
				//将菜下拉选中的值赋值给隐藏的input
				// $("#food_select_id").val($("select option:selected").val());
			// });

			$("#commit_print").on("click", function() {
				// alert($(this).attr("id"));
				// var data = $(":input, select, textarea").tojson();
				//检查数据完整性
				if (!module.exports.checkData())
					return false;

				var data = module.exports.toFormJSON();
				// alert(data);
				$.post(_ctx+"/order/printInput.do", data, function(reponseText) {
					// alert(reponseText);
					if (reponseText == '1') {
						alert("打印成功!");

					} else {
						alert("打印失败!");

					}
				});
			});

			$("#reset_id").on("click", function() {
				// alert("1122334455");
				$("#item_price_id").val('');
				$("#product_no_id").val('');
				$("#item_remark_id").val('');

				$.each(foodids, function(i, v){
					$("#food_price_"+v).val(0);
					$("#food_num_"+v).text(0);
				});
			});
		},
		
		toFormJSON : function(){
			// alert("00000");
			// alert("--->"+foodids);
			var foods = '';
			var data = '{';
			data = data+'"itemName" : "'+$("#item_name_id").val()+
				'", "itemPrice" : "'+$("#item_price_id").val()+
				'", "itemRemark" : "'+$("#item_remark_id").val()+
				'", "productNo" : "'+$("#product_no_id").val()+
				'", "foods" : [';
			$.each(foodids, function(i, v){
				var food_num_id = "#food_num_"+v;
				var food_id = "#"+v;
				var food_price_id = "#food_price_"+v;

				if(i == 0)
					foods = '{"foodId" : "'+v+'", "foodName" : "'+$(food_id).val()+'", "foodPrice" : "'+
						$(food_price_id).val()+'", "foodNum" : "'+$(food_num_id).text()+'"}';
				else
					foods = foods+', {"foodId" : "'+v+'", "foodName" : "'+$(food_id).val()+'", "foodPrice" : "'+
						$(food_price_id).val()+'", "foodNum" : "'+$(food_num_id).text()+'"}';

			});

			data = data+foods+']}';
			// alert(data);

			return data;
		},

		addTotalPrice : function () {
			var total_price = 0;
			$.each(foodids, function(i, v){
				var food_price_id = "#food_price_"+v;
				var food_price_v = parseFloat($(food_price_id).val());
				total_price = total_price+food_price_v;
			});
			// alert(total_price);
			$("#item_price_id").val(total_price);
		},

		checkData : function () {
			if($("#item_name_id").val() == ''){
				alert('订单名称不能为空！');
				$("#item_name_id").focus();
				return false;
			}
			if($("#item_price_id").val() == ''){
				alert('订单价格不能为空！');
				$("#item_price_id").focus();
				return false;
			}
			if($("#product_no_id").val() == ''){
				alert('手机号码不能为空！');
				$("#product_no_id").focus();
				return false;
			}
			if($("#item_remark_id").val() == ''){
				alert('备注信息不能为空！');
				$("#item_remark_id").focus();
				return false;
			}

			var food_num = 0;
			$.each(foodids, function(i, v){
				// $("#food_price_"+v).val(0);
				if(parseFloat($("#food_num_"+v).text()) > 0){
					food_num++;
				}
			});

			if(food_num == 0){
				alert('请至少选择一种菜品！');
				return false;
			}

			return true;
		}
	};
	
});