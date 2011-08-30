<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div id="banner-container">
		<div id="banner"  role="banner" class="container_12">
		<s:url var="homeUrl" namespace="/forum" action="index"/>
			<div class="grid_3">
				<h1 id="title"><s:a   href="%{homeUrl}"> Java Press</s:a></h1>
			</div>
			<div class="grid_9">
				<ul id="nav">
					<li><a href="/">Home</a></li>
					<li><a href="/plugins/">Plugins</a></li>
					<li><a href="/about/">About</a></li>
					<li><a href="/documentation/">Docs</a></li>
					<li><a href="/blog/">Blog</a></li>

					<li><a class="current" href="/forums/">Forums</a></li>
					<li><a class="download" href="/download/">Download</a></li>
				</ul>
			</div>
		</div>
	</div>
	<!--  id=banner-container -->
	<div class="clear"></div>
		
	<div id="login-container">
		<div class="container_12">
			<div id="login">
				<form method="post" action="http://bbpress.org/forums/bb-login.php">
					<fieldset>
						<input name="re" type="hidden" value="" /> <input type="hidden"
							name="_wp_http_referer" value="/forums/" /> <label
							for="login-username">Username</label> <input
							id="login-username" name="user_login" type="text"
							title="Username" maxlength="40" tabindex="1" value="username" />
						<label for="login-password">Password</label> <input
							id="login-password" name="password" type="password"
							title="Password" maxlength="40" tabindex="2" value="password" />
						<label for="login-remember">Remember me</label> <input
							id="login-remember" name="remember" type="checkbox"
							title="Remember me for 2 weeks" tabindex="3" value="1" /> <input
							name="log_in" type="submit" tabindex="4" value="Log In" /> <a
							href="http://bbpress.org/forums/register.php" class="button">Join</a>

						<script type="text/javascript" charset="utf-8">
							var login_username = document
									.getElementById('login-username');
							login_username.onfocus = function(e) {
								if (e.srcElement.value === 'username') {
									e.srcElement.value = '';
								}
							}
							login_username.onblur = function(e) {
								if (e.srcElement.value === '') {
									e.srcElement.value = 'username';
								}
							}
							var login_password = document
									.getElementById('login-password');
							login_password.onfocus = function(e) {
								if (e.srcElement.value === 'password') {
									e.srcElement.value = '';
								}
							}
							login_password.onblur = function(e) {
								if (e.srcElement.value === '') {
									e.srcElement.value = 'password';
								}
							}
						</script>
					</fieldset>
				</form>
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
	</div><!-- id=login-container -->
	