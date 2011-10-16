<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page  import="com.usemodj.forum.Location" %>

<s:set var="lastPageOnly"  value="true"/>
<s:if test="forum.forumIsCategory != 1">
	<s:set var="add"  value="topicPagesAdd"/>
	<s:if test="null == #h2" >
		<s:if test='%{location.toString() == "TOPIC"}'>
			<s:set var="h2" value="%{getText('Reply')}"/>
		</s:if>
		<s:elseif test='%{location.toString() == "FORUM"}'>
			<s:set var="h2"  value="%{getText('New Topic in this Forum')}"/>
		</s:elseif>
		<s:elseif test='%{location.toString() == "TAG"  || location.toString() == "FRONT"}'>
			<s:set var="h2" value="%{getText('Add New Topic')}"/>
		</s:elseif>
	</s:if>
	
	<s:if test="null != #h2">
		<s:if test='%{location.toString() == "TOPIC"  && page != lastPage && #lastPageOnly}'>
			<s:set var="h2" >
			 	<a href="<s:property value='%{getTopicLink()}'/>#postform" ><s:property value="h2" /> &raquo;</a>
			</s:set>
		</s:if>
		<h2 class="post-form"><s:property value="h2" escapeHtml="false"/></h2>
	</s:if>
	
	<s:if test="%{(location.toString() == 'TOPIC'  && currentUserCan('write_post', #topic) && (page == lastPage || !lastPageOnly)) ||
		(location.toString() != 'TOPIC' && currentUserCan('write_topic', #forum))}" >
		<s:form cssClass="postform post-form"  id="postform"  method="post"  namespace="/forum" action="post">
		<fieldset>
			<%@ include file="/forum/default/_postFormField.jsp" %>
		</fieldset>
		</s:form>
	</s:if>

 </s:if><!--  test=forum.forumIsCategory -->
 
