<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.usemodj.jpetstore.domain.Signon,com.usemodj.struts.Role"  %>
<% //Signon signon = (Signon)request.getSession().getAttribute("login"); 
%>
<html><head>
<title><s:text name="file.upload"/></title>
<meta name="decorator" content="panel"/>
</head>
<body>
<s:actionerror />
<s:fielderror />
<s:actionmessage/>
<s:form action="upload" method="POST" enctype="multipart/form-data">
<tr>
<td colspan="2"><h1><s:text name="file.upload"/></h1></td>
</tr>

<s:file name="upload" label="%{getText('File')}"/>
<s:textfield name="caption" label="%{getText('Caption')}"/>
<s:submit  value="%{getText('file.upload')}"/>
</s:form></body>
</html>