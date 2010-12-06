<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="decorator"	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title><decorator:title default="순천만 미나리" /></title>
<link href="<%=request.getContextPath()%>/decorators/main.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/prototype.js"></script>
<script src="<%=request.getContextPath()%>/scripts/scriptaculous.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/scripts/simple-slide-show.js" type="text/javascript"></script>
<style>
/* slide-show */
#slide-images{
	position:relative;
	display:block;
	margin:0px;
	padding:0px;
	width:980px;
	height:250px;
	overflow:hidden;
}

#slide-images li{
	position:absolute;
	display:block;
	list-style-type:none;
	margin:0px;
	padding:0px;
	background-color:#FFFFFF;
}

#slide-images li img{
	display:block;
	background-color:#FFFFFF;
}

</style>


<decorator:head />
<script type="text/javascript">
  document.observe("dom:loaded", function() {
       new Ajax.Updater($('categoryLink'), "<%= request.getContextPath() %>/shop/category",{asynchronous:true, evalJS:true});
  });

</script>

</head>

<body>

	<div id="main">
		<div id="topbar">
			<div style="float:left"><a href="<%=request.getContextPath()%>">홈:순천만 미나리</a></div>
			
		<% if(null == session.getAttribute("login")){ %>
		  <a href="<%=request.getContextPath()%>/login_input?url=<s:text name='hostname'/><%=request.getRequestURI()%>?<%=request.getQueryString() %>"><s:text name="login"/></a>
		<% }else {%>
		  <a href="<%=request.getContextPath()%>/logout?url=<s:text name='hostname'/><%=request.getRequestURI()%>?<%=request.getQueryString() %>"><s:text name="logout"/></a>
		 | <a href='<s:url namespace="/" action="signon_input"/>'><s:text name="signon.input"/></a>
		 | <a href='<s:url namespace="/shop" action="order_list"/>'>주문예약조회</a>
		<% }%>
		
		| <a href="<%= request.getContextPath() %>/shop/cart?url=<s:text name='hostname'/><%=request.getRequestURI()%>?<%=request.getQueryString() %>"><s:text name="cart"/></a> &nbsp;
		</div>
		<%-- 
		<div style="float: right; width: 20em; margin: 0pt 0pt 1em 2em;">
			<page:applyDecorator name="widget" page="/google.html" />
			<page:applyDecorator name="widget" page="/tiny-panel.html" /> 
		</div>
		--%>
		<div class="slide-show" style="text-align:center">
			<ul id="slide-images">
				<li><img  src="<%=request.getContextPath() %>/images/bay/suncheon-bay-wide.png" alt="One" title="One" /></li>
				<li><img   src="<%=request.getContextPath() %>/images/bay/suncheon-bay-wide2.png" alt="Two" title="Two" /></li>
				<li><img   src="<%=request.getContextPath() %>/images/bay/suncheon-bay-wide.png" alt="Three" title="Three" /></li>
				<li><img   src="<%=request.getContextPath() %>/images/bay/suncheon-bay-wide2.png" alt="Four" title="Four" /></li>
			</ul>
		</div>
		<div style="float: right; width: 210px; margin: 0pt 0pt 1em 2em;">
      <div id="categoryLink" style="width:100%;float:right;"></div>
      
		</div>
		<div id="content">
		<decorator:body />
		</div><!--/content-->
	
		<br class="clear:both"/>
		<br/>
		<div id="footer">
			<p class="copyright">Copyright &copy; 2010 usemodj.com. All rights reserved.</p>
		</div>
	</div><!--/main-->

</body>
</html>