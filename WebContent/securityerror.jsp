<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

	<head>
    <title>Security Error</title>
	</head>

	<body>
		<h4>There has been a security error.</h4>
		<p>  Please contact technical support with the following information:</p> 
		<div style="float:left;text-align:left;">
		<!-- the exception and exceptionStack bean properties
		were created by Struts2's Exception Intercepter (see page 89) -->
		<h4>Exception Name: <s:property value="exception" /> </h4>
		<h4>Exception Details: <s:property value="exceptionStack" /></h4> 
		</div>
	
	    <p><a href="index.jsp">Return to the home page.</a></p>
	  
		
	</body>
	
</html>
