<%@page language="java" contentType="text/html; charset=UTF-8"%>

<!--
	创建时间 ：2016年8月24日 上午2:03:28
	创建人：     YUHB
	页面编码 ：UTF-8
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>测试</title>
		<%@include file="/common/loadjs.jsp"%>

		<style type="text/css">
			html {
				position: relative;
				min-height: 100%;
			}
			body {
				/* Margin bottom by footer height */
				margin-bottom: 20px;
			}
			.footer {
				position: absolute;
				bottom: 0;
				width: 100%;
				/* Set the fixed height of the footer here */
				height: 20px;
				background-color: #f5f5f5;
			}
            .container {
                width: auto;
                max-width: 960px;
                padding: 0 10px;
            }
            .container .text-muted {
                margin: 2px 0;
            }
		</style>
	</head>

	<body>
		<div id="wrapper">
			<header id="header">
				<div id="loginBar">
					<div class="w960 tr">
						<a id="login_id" href="#">登录</a> <span class="sp">|</span> <a id="register_id" href="#">注册</a>
						<span class="sp">|</span> <a id="logout_id" href="#">退出</a>
					</div>
				</div>
				<div id="headBox">
					<div class="w960 oh">
						<a id="logoimg" href="#" class="fl mt10"><img src="images/logo.png" alt="logo" /></a>
						<nav id="navs" class="fr">
							<a id="summary_id" class="active" href="#">首页</a>
							<a id="item_id" href="#">订单管理</a>
							<a id="food_id" href="#">菜品管理</a>
							<a id="user_id" href="#">用户管理</a>
							<a id="manage_id" href="#">管理中心</a>
						</nav>
					</div>
				</div>
			</header><!-- // header end -->
			<div height="500">
				<iframe src="<%=ctx %>/web/index.do?id=summary_id" id="iframeid" frameborder="0" scrolling="no" width="100%"></iframe>
			</div>
			<footer class="footer">

			</footer>
		</div><!-- // wrapper end -->
	</body>
</html>

<script type="text/javascript">
	<%--alert("<%=ctx %>");--%>
//	alert("---"+_ctx);
	seajs.use("./jslib/ext/index", function(main){
		main.init();
	});
</script>
