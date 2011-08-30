<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page  import="com.usemodj.forum.ForumsHierarchical" %>
<body id="forum-page">
	<div id="wrapper">
		<div id="main">
		
			<div class="bbcrumb"><a href="<%= request.getContextPath() %>/forum/"><s:property value="siteName"/></a>
			<s:property value="breadcrumbs"  escapeHtml="false"/></div>
		
				<table id="latest"  role="main">
					<tr>
						<th>Topic &#8212; <a
							href="http://usemodj.com/bbpress/bb-login.php?re=http://usemodj.com/bbpress/?new=1"
							class="new-topic">Add New &raquo;</a></th>
						<th>Posts</th>
						<!-- <th>Voices</th> -->
						<th>Last Poster</th>
						<th>Freshness</th>
					</tr>

				<s:iterator value="stickyTopics"  status="status" var="topic">
				
					<s:bean name="com.usemodj.forum.bean.TopicCSS"  var="topicCSS">
						<s:param name="topic"  value="#topic"></s:param>
					</s:bean>
						
					    <tr  <s:property  value="#topicCSS.getTopicClass(2, true)"/> >
					    <s:url var="topicUrl"  namespace="/forum" action="topic">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
				
						<td><s:property value="#topicCSS.getClosedOrStickyLabel(false)"/>
							<s:a href="%{topicUrl}"><s:property value="topicTitle"/></s:a></td>
						<td class="num"><s:property value="topicPosts"/></td>
						<!-- <td class="num">1</td> -->
						<td class="num"><s:property value="topicLastPosterName"/></td>
					    <s:url var="topicUrlAnchor"  namespace="/forum" action="topic"  anchor="post-<s:property value=''postId/>">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
						<s:date var="topicDateTime"  name="topicTime"  format="yyyy-MM-dd - HH:mm a"/>
						<td class="num"><s:a href="%{topicUrlAnchor}"  title="%{topicDateTime}"><s:date name="topicTime"  nice="true"/></s:a>
							</td>
					</tr>
				</s:iterator>

				<s:iterator value="paginate.results"  status="status" var="topic">
					<s:bean name="com.usemodj.forum.bean.TopicCSS"  var="topicCSS">
						<s:param name="topic"  value="#topic"></s:param>
					</s:bean>
					    <tr  <s:property  value="#topicCSS.getTopicClass(2, true)"/> >
					    <s:url var="topicUrl"  namespace="/forum" action="topic">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
					
						<td><s:property value="#topicCSS.getClosedOrStickyLabel(false)"/><s:a href="%{topicUrl}"><s:property value="topicTitle"/></s:a></td>
						<td class="num"><s:property value="topicPosts"/></td>
						<!-- <td class="num">1</td> -->
						<td class="num"><s:property value="topicLastPosterName"/></td>
					    <s:url var="topicUrlAnchor"  namespace="/forum" action="topic"  anchor="post-<s:property value=''postId/>">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
						<s:date var="topicDateTime"  name="topicTime"  format="yyyy-MM-dd - HH:mm a"/>
						<td class="num"><s:a href="%{topicUrlAnchor}"  title="%{topicDateTime}"><s:date name="topicTime"  nice="true"/></s:a>
							</td>
					</tr>
				</s:iterator>
				</table>

				<h2>SubForums</h2>
				
				<table id="forumlist">
					<tr>
						<th>Main Theme</th>
						<th>Topics</th>
						<th>Posts</th>
					</tr>
<%
ForumsHierarchical fh = (ForumsHierarchical)request.getAttribute("fh");
while(  fh.forumsLoopStep()) { %>
		    <s:url var="forumUrl"  namespace="/forum" action="forum">
	          <s:param name="forum.forumId" value="%{fh.forum.forumId}"/>
	        </s:url>
<%
	if(  fh.isForumIsCategory()) {
%>	
	<tr <%= fh.getAltClass( "forum", fh.getForumClass("bb-category")) %>>
		<td colspan="3"><%=fh.getForumsLoop().pad("<div class='nest'>" , 0) %><s:a href="%{forumUrl}"><%= fh.getForum().getForumName() %></s:a> <small> &#8211;
		<%= fh.getForum().getForumDesc()%></small><%=fh.getForumsLoop().pad("<div class='nest'>" , 0) %> </td>
	</tr>
<% 
		continue;
	} //end of if %>
	<tr <%= fh.getAltClass( "forum", fh.getForumClass(null)) %>>
		<td><%=fh.getForumsLoop().pad("<div class='nest'>" , 0) %><s:a href="%{forumUrl}"> <%= fh.getForum().getForumName() %></s:a> <small> &#8211;
		<%= fh.getForum().getForumDesc()%></small><%=fh.getForumsLoop().pad("<div class='nest'>" , 0) %> </td>
		<td class="num"><%= fh.getForum().getTopics()  %> </td>
		<td class="num"><%= fh.getForum().getPosts() %> </td>
	</tr>
<% 
} //end while %>		
								
				</table>
			</div>
		</div>
	</div>
	
<%@ include  file="/forum/default/footer.jsp" %>
				