<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags" %>   
<p id="login">
	
	<s:text name="Welcome"/>, <s:property  value="#session.user.userLogin"/>&nbsp;|
	<s:url var="logoutUrl" namespace="/forum" action="login">
		<s:param  name="logout"  value="true" />
	</s:url>
	<s:a href="%{logoutUrl}"> <s:text  name="Logout"/></s:a>&nbsp;|
	
</p>

