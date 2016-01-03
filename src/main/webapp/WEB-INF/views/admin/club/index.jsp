<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/ace/admin.club.js"></script>
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
						<div class="content-widgets light-gray">
							<div class="widget-head  bondi-blue" >
								<h3>社团查询</h3>
							</div>
							<div class="box well form-inline">
								<span>社团名称：</span>
								<input type="text" id="_name" >
								<a onclick="$.adminClub.initSearchDataTable()"
									class="btn btn-info" data-loading-text="正在加载..."><i class="icon-search"></i>查询</a>
							</div>
							<div class="widget-container">
								<table class="responsive table table-striped table-bordered"
									id="dt_table_view">
									<thead>
										<tr>
											<th >社团名称</th>
											<th >社团类别</th>
											<th >负责人姓名</th>
											<th >负责人电话</th>
											<th >指导老师</th>
											<th >指导老师电话</th>
											<th >社团宗旨</th>
											<th >登记日期</th>
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
								<label for="title" class="control-label">职称：</label>
								<div class="controls">
									<input type="text" name='job' id=job placeholder="">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal-footer center" id="div_footer">
			<a class="btn btn-primary" onclick="$.adminClub.saveClub()">保存</a>
			<a href="#" class="btn" data-dismiss="modal" id="closeViewModal">关闭</a>
		</div>
	</div>
</body>
</html>