<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Smart - UI Elements</title>

		<!-- Bootstrap -->
		<link href="css/bootstrap.css" rel="stylesheet">
		<link href="css/font-awesome.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">

		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
		  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
	<div class="container">			
		<header>
			<h1>订单查询
			</h1>	
		</header> 
	</div>
		
			
	<div class="content">
		<div class="container">
		<div class="row">
					<div class="col-sm-12">
					
					<div class="panel panel-default">
						 <div class="panel-heading">订单状态查询</div>
						 <div class="panel-body">
							<div class="text-center center-block">
						<form role="form" class="form-inline">
						 <div class="form-group">
							<label for="input-email" class="sr-only">会员名称</label>
							<input id="input-email" type="email" placeholder="会员名称" class="form-control">
						 </div>
						  <div class="form-group">
							<label for="input-email" class="sr-only">订单日期起</label>
							<input id="input-email" type="email" placeholder="订单日期起" class="form-control">
						 </div>
						  <div class="form-group">
							<label for="input-email" class="sr-only">订单日期止</label>
							<input id="input-email" type="email" placeholder="订单日期止" class="form-control">
						 </div>
						 <button type="submit" class="btn btn-success">查询</button>
					  </form>
							</div>
						 </div>
					
					</div>
					</div>
				</div>
			</div>	
				<div class="container">
				<div class="row">
					<div class="col-sm-12">
				<h2>订单详情</h2>
				   <table class="table table-bordered table-striped">
						<thead>
							<tr>
								<th>
									订单编号
								</th>
								<th>
									商品名称
								</th>
								<th>
									规格
								</th>
								<th>
									单价
								</th>
								<th>
									数量
								</th>
								<th>
									实际支付
								</th>
								<th>
									状态
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									1100000006965714
								</td>
								<td>
									AMD FX系列八核 FX-8300盒装CPU

								</td>
								<td>
									橙色
								</td>
								<td>
									5500
								</td>
								<td>
									1
								</td>
								<td>
									5500
								</td>
								<td>
									<span class="label label-danger">已发货</span>
								</td>
							</tr>
							<tr>
								<td>
									1100000006965714
								</td>
								<td>
									AMD FX系列八核 FX-8300盒装CPU

								</td>
								<td>
									橙色
								</td>
								<td>
									5500
								</td>
								<td>
									1
								</td>
								<td>
									5500
								</td>
								<td>
									<span class="label label-danger">已发货</span>
								</td>
							</tr>
							<tr>
								<td>
									1100000006965714
								</td>
								<td>
									AMD FX系列八核 FX-8300盒装CPU

								</td>
								<td>
									橙色
								</td>
								<td>
									5500
								</td>
								<td>
									1
								</td>
								<td>
									5500
								</td>
								<td>
									<span class="label label-danger">已发货</span>
								</td>
							</tr>
							
						</tbody>
					</table>
				</div>
				</div></div>
				</div>
	</div>
		
		<!-- Footer -->
		<footer class="footer">
			<div class="row">
				<div class="twelve col">
					 <p class="text-muted text-center">Copyright © 2015 XX单位 <a href="http://www.miibeian.gov.cn/">京ICP备15062073号-1</a></p>
		
				</div>
			</div>
		</footer>
		
		
		

		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="js/jquery-1.11.1.js"></script>
		<script src="js/bootstrap.js"></script>
		<script src="js/jquery.slimscroll.js"></script>
		<script src="js/gmaps.js"></script>
		<script src="js/main.js"></script>
	
	
	</body>
</html>