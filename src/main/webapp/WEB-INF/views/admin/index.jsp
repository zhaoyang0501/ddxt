<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ch">
<head>
<%@ include file="common/meta.jsp"%>
<link href="${pageContext.request.contextPath}/admin/css/fullcalendar.css" rel= "stylesheet">
<script src="${pageContext.request.contextPath}/admin/js/falgun/fullcalendar.min.js"></script>

<link>
<script type="text/javascript">
	$(function() {
		$.ajax({
			url : $.ims.getContextPath() + "/getDailyReportToApproveCount",
			dataType : "json",
			success : function(json) {
				if (json.state)
					$("#count_dailyReport").html(json.count);
			}
		});

		$.ajax({
			type : "GET",
			url : $.ims.getContextPath() + "/getRemoteNews/1",
			dataType : "json",
			success : function(result) {
				$("#book_manage_ui").empty();
				$.each(result.news, function(i, n) {
					$("#book_manage_ui").append(
							"<li ><i  class='icon-hand-right'></i><a href='"+n.url+"' >"
									+ n.title + "</a></li>");
				});
			}
		});
		$.ajax({
			type : "GET",
			url : $.ims.getContextPath() + "/getRemoteNews/2",
			dataType : "json",
			success : function(result) {
				$("#discuzz_ui").empty();
				$.each(result.news, function(i, n) {
					$("#discuzz_ui").append(
							"<li ><a href='"+n.url+"'><h5>"+ n.title + "</h5></a></li>");
				});
			}
		});
	});
</script>
</head>
<body>
	<div class="layout">
		<!-- Navbar
    ================================================== -->
		<!-- top -->
		<%@ include file="top.jsp"%>

		<!-- 导航 -->
		<%@ include file="menu.jsp"%>
		<div class="main-wrapper">
			<div class="container-fluid">
				<div class="row-fluid">
				<div class="span12">
					<div class="switch-board gray">
						<ul class="clearfix switch-item">
						<c:if test="${sessionScope.adminuser.username=='admin'}">
							<li><span class="notify-tip">${usernum }</span><a href="javascript:void(0);" class="brown"><i class="icon-user"></i><span>会员数</span></a></li>
						</c:if>
							<li><span class="notify-tip">${num1 }</span><a href="javascript:void(0);" class=" blue-violate"><i class="icon-lightbulb"></i><span>订单总数</span></a></li>
							<li><span class="notify-tip">${num2 }</span><a href="javascript:void(0);" class="orange"><i class="icon-cogs"></i><span>未确认订单</span></a></li>
							<li><span class="notify-tip">${num3 }</span><a href="javascript:void(0);" class=" magenta"><i class="icon-bar-chart"></i><span>已确认订单</span></a></li>
							<li><span class="notify-tip">${num4 }</span><a href="javascript:void(0);" class="green"><i class="icon-shopping-cart"></i><span>已发货</span></a></li>
							<li><span class="notify-tip">${num5 }</span><a href="javascript:void(0);" class=" bondi-blue"><i class="icon-time"></i><span>已付款</span></a></li>
							<li><span class="notify-tip">${num6 }</span><a href="javascript:void(0);" class=" dark-yellow"><i class="icon-file-alt"></i><span>已确认</span></a></li>
						</ul>
					</div>
				</div>
				</div>
					<div class="row-fluid">
				<div class="span12">
					<div class="content-widgets gray ">
						<div class="widget-head bondi-blue">
							<h3><i class="icon-ambulance"></i>公告</h3>
						</div>
						<div class="ribbon-wrapper-green">
							<div class="ribbon-green">
								NEWS
							</div>
						</div>
						<div class="widget-container">
							<h3>${news.title }</h3>
							<p>
							${news.context }
							</p>
						</div>
					</div>
				</div>
				</div>
			</div>
		</div>
		<!-- foot -->
		<%@ include file="foot.jsp"%>
	</div>

</body>
</html>