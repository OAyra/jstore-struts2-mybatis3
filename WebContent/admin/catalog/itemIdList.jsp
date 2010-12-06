<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.usemodj.jpetstore.domain.Signon,com.usemodj.struts.Role"  %>
<% //Signon signon = (Signon)request.getSession().getAttribute("login"); 
%>
<html><head>
<title><s:text name="item.list"/></title>
<meta name="decorator" content="panel"/>
</head>
<body>
<ul>
  <s:iterator value="itemList" status="stat">
       <s:url var="itemUrl" namespace="/admin" action="item_view">
           <s:param name="item.itemId" value="%{itemId}"/>
       </s:url>
      <li><s:property value="itemId"/>: <s:a href="%{itemUrl}" target="opener"><s:property value="attribute1" /></s:a></li>
   </s:iterator>
</ul>
</body>
</html>