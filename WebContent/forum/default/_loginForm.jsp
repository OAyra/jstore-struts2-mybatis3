<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags" %>
	<div style="font-size:95%"> <s:actionerror/></div>   
	<div id="login">
		<s:form method="post"  namespace="/forum" action="login">
		<tr><td>
			<s:url var="registerUrl" namespace="/forum" action="register">
			</s:url>

				<div id="login">
				<s:hidden  key="loginAttempt"  value="true"/>
				<s:hidden key="url" /> 
				 <label	for="login-username">Username</label> <input
					id="login-username" name="user.userLogin" type="text"
					title="Username" maxlength="40" tabindex="1" value="username" />
				<label for="login-password">Password</label> <input
					id="login-password" name="user.userPass" type="password"
					title="Password" maxlength="40" tabindex="2" value="password" />
				<label for="login-remember">Remember me</label> <input
					id="login-remember" name="remember" type="checkbox"
					title="Remember me for 2 weeks" tabindex="3" value="1" /> <input
					name="logIn" type="submit" tabindex="4" value="Log In" /> <s:a
					href="%{registerUrl}" cssClass="button">Register</s:a>

				<script type="text/javascript" charset="utf-8">
					var login_username = document.getElementById('login-username');
					login_username.onfocus = function(e) {
						var  elm = (window.event)? event.srcElement: e.currentTarget;
						if (elm.value === 'username') {
							elm.value = '';
						}
					};
					login_username.onblur = function(e) {
						var  elm = (window.event)? event.srcElement: e.currentTarget;
						if (elm.value === '') {
							elm.value = 'username';
						}
					};
					var login_password = document.getElementById('login-password');
					login_password.onfocus = function(e) {
						var  elm = (window.event)? event.srcElement: e.currentTarget;
						if (elm.value === 'password') {
							elm.value = '';
						}
					};
					login_password.onblur = function(e) {
						var  elm = (window.event)? event.srcElement: e.currentTarget;
						if (elm.value === '') {
							elm.value = 'password';
						}
					};
				</script>
				</div>
			
			</td></tr>
		</s:form>
		<div class="clear"></div>
	</div> <!-- id=login -->
