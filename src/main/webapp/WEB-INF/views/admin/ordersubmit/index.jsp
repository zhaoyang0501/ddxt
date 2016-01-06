<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="ch">
<%@ include file="../common/meta.jsp"%>
<head>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/ace/admin.item.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/admin/js/falgun/bootbox.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/falgun/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/js/falgun/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		if("${tip}" != null && "${tip}" != ""){
			noty({"text":"${tip}","layout":"top","type":"success","timeout":"2000"});
		}
	});
</script>
</head>
	
	<script type="text/javascript">
		$(function() {
		});
	</script>
</head>
<body>
	<div class="layout">
		<!-- top -->
		<%@ include file="../top.jsp"%>
		<!-- 导航 -->
		<%@ include file="../menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid ">
					<div class="span12">
						<div class="content-widgets light-gray" style="height: 700px">
						<div class="widget-head  bondi-blue" >
							<h3>订单提交</h3>
						</div>
						<div class="widget-container">
							<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/admin/ordersubmit/save">
								<div class="control-group">
									<label class="control-label">订单编号</label>
									<div class="controls">
										<input name="oid" type="text" class="">
									</div>
								</div>
								
								<div class="control-group">
									<label class="control-label">提交人</label>
									<div class="controls">
										<input name="" type="text" class="">
									</div>
								</div>
								
								
								<div class="control-group">
									<label class="control-label">留言</label>
									<div class="controls">
										<textarea name='remark' rows="3" cols=""></textarea>
									</div>
								</div>
								
								<div class="controls">
										<button type="submit" class="btn btn-primary">提交</button>
									</div>
							</form>
						</div>
					</div>
					
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../foot.jsp"%>
	</div>
</body>
</html>