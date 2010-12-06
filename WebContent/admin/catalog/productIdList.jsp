<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.usemodj.jpetstore.domain.Signon,com.usemodj.struts.Role"  %>
<% //Signon signon = (Signon)request.getSession().getAttribute("login"); 
%>
<html><head>
<title><s:text name="product.list"/></title>
<meta name="decorator" content="panel"/>
</head>
<body>
<ul>
  <s:iterator value="productList" status="stat">
       <s:url var="productUrl" namespace="/admin" action="product_view">
           <s:param name="product.productId" value="%{productId}"/>
       </s:url>
      <li><s:property value="productId"/>: <s:a href="%{productUrl}" target="opener"><s:property value="name" /></s:a></li>
   </s:iterator>
</ul>
</body>
</html>