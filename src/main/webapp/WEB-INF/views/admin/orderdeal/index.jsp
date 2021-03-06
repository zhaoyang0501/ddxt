<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<title>订单系统</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/ace/admin.payorderdeal.js?sdfs=5551"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/falgun/bootbox.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".date").datetimepicker({
			language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
	        format:'yyyy-mm-dd',
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });
	});
	
	function fun_checkall(){
		if($("#checkall").is(':checked')){
			$("input[name='checkbox']").prop('checked',true);
		}
		else{
			$("input[name='checkbox']").removeAttr('checked');
		}
	}
	
</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		
		<input type="hidden" id="hf_id" />

		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets ">
							<div class="widget-head  bondi-blue" >
								<h3>订单处理</h3>
							</div>
							<div class="box well form-inline">
								<div>
									<input type="text" id="_user" placeholder='会员账号' >
									<input type="text" id="_id" placeholder='订单编号' >
									<select id='_state' placeholder='订单状态'>
									    <option value="">--选择订单状态--</option>
										<option value="商家未确认">商家未确认</option>
										<option value="已确认订单">已确认订单</option>
										<option value="发货成功">发货成功</option>
										<option value="已付款" >已付款</option>
									</select>
									<a onclick="$.adminPayorderdeal.initSearchDataTable()"
										class="btn btn-info" data-loading-text="正在加载..."><i class="icon-search"></i>查询</a>
								
									<a onclick="$.adminPayorderdeal.sendall()"
										class="btn btn-info" data-loading-text="正在加载..."><i class="icon-ambulance"></i>发货</a>
									
									<a onclick="$.adminPayorderdeal.payall()"
										class="btn btn-info" data-loading-text="正在加载..."><i class="icon-money "></i>付款</a>
									
									<a onclick="$.adminPayorderdeal.deleteall()"
										class="btn btn-info" data-loading-text="正在加载..."><i class="icon-trash"></i>删除</a>
							
								</div>
							</div>
							<div class="row-fluid">
								<table class="responsive table table-striped table-bordered"
									id="dt_table_view">
									<thead>
										<tr>
										<th >
										<label class="checkbox inline">
											<input onclick="fun_checkall()" id='checkall' type="checkbox" >全选 
											</label>
										</th>
											<th >订单编号</th>
											<th >收货人</th>
											<th >地址</th>
											
											<th >手机号码</th>
													
											<th >会员账号</th>
											<th >商品名称</th>
											<th >实际支付</th>
											<th >系数一</th>
											<th >系数二</th>
											<th >税后金额</th>
											<th >提交日期</th>
											<th >提交人</th>
											<th >状态</th>
										</tr>
									
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
	<!-- 编辑新增弹出框 -->
	<div class="modal hide fade" id="_modal">
		<div class="modal-header blue">
			<button type="button" class="close" data-dismiss="modal">×</button>
			<label id="_modal_header_label"></label>
		</div>
		<div class="modal-body" style="min-height: 400px;">
			<div class="row-fluid">
				<div class="span12">
					<div class="form-container grid-form form-background left-align form-horizontal">
						<form action="" method="get" id=''>
							<input type="hidden"  name='id' id="id" value="">
							
							<div class="control-group">
								<label for="title" class="control-label">用户名：</label>
								<div class="controls">
									<input type="text" name='username' id="username" placeholder="">
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">密码：</label>
								<div class="controls">
									<input type="text"  name='password' id="password" placeholder="">
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">姓名：</label>
								<div class="controls">
									<input type="text" name='name' id="name" placeholder="">
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">地址：</label>
								<div class="controls">
									<input type="text" name='address' id=address placeholder="">
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">email：</label>
								<div class="controls">
									<input type="text" name='email' id=email placeholder="">
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">电话：</label>
								<div class="controls">
									<input type="text" name='tel' id=tel placeholder="">
								</div>
							</div>
							<div class="control-group">
								<label for="title" class="control-label">学校：</label>
								<div class="controls">
									<input type="text" name='school' id=school placeholder="">
								</div>
							</div>
							
							<div class="control-group">
								<label for="title" class="control-label">班级：</label>
								<div class="controls">
									<input type="text" name='grade' id=grade placeholder="">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.adminUser.saveUser()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>