<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

	<link href="<%=request.getContextPath()%>/decorators/widget.css" rel="stylesheet" type="text/css"/>
	<decorator:head />
	<div class="box">
		<div class="round4"></div>
		<div class="round2"></div>
		<div class="round1"></div>
		<div class="box-inner">
		<decorator:body />
		</div>
		<div class="round1"></div>
		<div class="round2"></div>
		<div class="round4"></div>
	</div>
	