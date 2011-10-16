<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<body id="register-page">
	<%@ include  file="/forum/default/_topContainer.jsp" %>
	<div role="main"  class="container_12">
			<div  class="bbcrumb">
				<a href="<%= request.getContextPath() %>/forum/"><s:property value="siteName"/></a>
				&raquo; <s:text  name="Register"  />
			</div>
			<div class="grid_2"> &nbsp; </div>
			<div class="grid_5">
			
			<h2 id="register" role="main"> <s:text  name="Register"  /> </h2>
			<fieldset>
			<s:form action="register" method="post">
					<s:token/> 
					
				<legend>Profile Information</legend>
				<p>Your password will be emailed to the address you provide.</p>
				<tr><td>
					<s:actionmessage /><br/>
					<s:actionerror /> 
				</td></tr>
					<s:textfield key="user.userLogin"   size="30" maxlength="140"  required="true" labelposition="top" /> 
					<s:textfield  key="user.userEmail"   size="30" maxlength="140"   required="true" labelposition="top" /> 
					<s:textfield  key="user.userUrl"   size="30" maxlength="255" labelposition="top" /> 
					<s:textfield key="from"   size="30" maxlength="140"  labelposition="top"  /> 
					<s:textfield key="occ"   size="30" maxlength="140"  labelposition="top" />  
					<s:textfield key="interest"   size="30" maxlength="140"  labelposition="top"  /> 
				 <tr><td> (<span class="required"><strong>*</strong></span>) These items are required.
				 </td></tr>
				<p class="submit">
					<s:submit  align="center"  value="%{getText('Register')}" >&nbsp;&nbsp;<s:url var="loginUrl" action="login"/><s:a href="%{loginUrl}"><s:text name="Login"/></s:a> &nbsp;&nbsp;</s:submit>
				</p>
				
				</s:form>
				</fieldset>
				</div><!-- class=grid_5 -->
				<div class="grid_5"> &nbsp; </div>
	</div><!-- class=container_12 -->	
</body>
