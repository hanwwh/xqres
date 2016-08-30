<%@page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>订单打印</title>
		<%@include file="/common/loadjs.jsp"%>
	</head>

	<body>
		<form class="form-horizontal" role="form" id="order_form">
			<fieldset>
				<legend>填写订单信息</legend>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="item_name_id">订单名称</label>
					<div class="col-sm-3">
						<input class="form-control" id="item_name_id" type="text"name="item_name" value="米欧简餐"/>
					</div>
					<label class="col-sm-2 control-label" for="item_price_id">订单价格</label>
					<div class="col-sm-3">
						<input class="form-control" id="item_price_id" type="text" name="item_price" value="" placeholder="0"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="product_no_id">手机号码</label>
					<div class="col-sm-3">
						<input class="form-control" id="product_no_id" type="text"name="product_no" value="" placeholder="10086"/>
					</div>
					<label class="col-sm-2 control-label" for="item_remark_id">备注</label>
					<div class="col-sm-3">
						<textarea class="form-control" id="item_remark_id" name="item_remark" rows="3" placeholder="姓名，地址等信息"></textarea>
					</div>
				</div>
				<div class="form-group">
					<div id="choose_food_div">
					</div>
				</div>
			</fieldset>
			<fieldset align="center">
				<legend></legend>
				<div class="btn-group">
					<div class="col-sm-5">
						<button type="button" id="commit_print" class="btn btn-success">提交打印</button>
					</div>
					<div class="col-sm-2">

					</div>
					<div class="col-sm-4">
						<button type="button" id="reset_id" class="btn btn-success">重置</button>
					</div>
				</div>
			</fieldset>
		</form>
	</body>
</html>

<script type="text/javascript">
	//引用printInputOrder.js，执行init方法
	seajs.use(_ctx+"/jsext/order/printInputOrder", function(main){
		main.init();
	});

</script>