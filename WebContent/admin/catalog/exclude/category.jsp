<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.usemodj.jpetstore.domain.Signon,com.usemodj.struts.Role"  %>
<% Signon signon = (Signon)request.getSession().getAttribute("login"); 
%>
<html><head>
<meta name="decorator" content="widget"/>
</head>
<body>
<ul>
  <s:iterator value="categoryList" status="stat">
       <s:url id="productUrl" namespace="/admin" action="product_list">
           <s:param name="product.categoryId" value="%{categoryId}"/>
       </s:url>
      <li><s:a href="%{productUrl}"><s:property value="name" /></s:a>
        <s:if test="null != signon && Role.ADMIN.equals( signon.role)"> <s:property value="status" /></s:if>
       </li>
   </s:iterator>
</ul>
</body>
</html>