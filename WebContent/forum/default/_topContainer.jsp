<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s"  uri="/struts-tags" %>
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
		
		<s:set var="user"  value='#session.user'/>
		<s:if test="%{ #user == null}">
			 
			<%@ include file="/forum/default/_loginForm.jsp" %>
		 </s:if>
		 <s:else>
		    
			<%@ include file="/forum/default/_loggedIn.jsp" %>
		</s:else>
		
		
		</div> <!-- class="container_12" -->
		<div class="clear"></div>
	</div><!-- id=login-container -->
	