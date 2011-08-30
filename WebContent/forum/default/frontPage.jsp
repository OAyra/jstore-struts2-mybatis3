<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page
	import="com.usemodj.forum.ForumsHierarchical,com.usemodj.forum.Location"%>
<body id="front-page">
	<%@ include  file="/forum/default/_topContainer.jsp" %>
	
	<div id="main" role="main"  class="container_12">
		<div class="grid_4">
			<div id="forums">
				<h2>Forums</h2>
				<table >
					<tr>
						<th class="forum"></th>
						<th class="num end">Topics/Posts</th>
					</tr>
					<%
						ForumsHierarchical fh = (ForumsHierarchical) request.getAttribute("fh");
						while (fh.forumsLoopStep()) {
					%>
					<s:url var="forumUrl" namespace="/forum" action="forum">
						<s:param name="forum.forumId" value="%{fh.forum.forumId}" />
					</s:url>
					<%
						if (fh.isForumIsCategory()) {
					%>
					<tr
						<%=fh.getAltClass("forum",
							fh.getForumClass("bb-category"))%>>
						<td colspan="2"><s:a href="%{forumUrl}"><%=fh.getForumsLoop().pad("<div class='nest'>", 0)%><%=fh.getForum().getForumName()%>
								<div><%=fh.getForum().getForumDesc()%></div> <%=fh.getForumsLoop().pad("<div class='nest'>", 0)%></s:a> 
						</td>
					</tr>
					<%
						continue;
							} //end of if
					%>
					<tr <%=fh.getAltClass("forum", fh.getForumClass(null))%>>
						<td><s:a href="%{forumUrl}"><%=fh.getForumsLoop().pad("<div class='nest'>", 0)%><%=fh.getForum().getForumName()%> 
								<div><%=fh.getForum().getForumDesc()%></div><%=fh.getForumsLoop().pad("<div class='nest'>", 0)%></s:a>
						</td>
						<td class="num"><%=fh.getForum().getTopics()%>/<%=fh.getForum().getPosts()%></td>
					</tr>
					<%
						} //end while
					%>
				</table>
			</div><!-- id=forums -->

			<div id="simple-search">
				<s:url var="searchUrl" namespace="/forum" action="search"/>
				<s:form method="get" action="%{searchUrl}" role="search">
				<fieldset> 
				<label for="simple-search-input">Search</label>
				<input id="simple-search-input" type="search" maxlength="100" results="10" autosave="javapress-org-forums" name="q">
				<input type="submit" value="Search">
				</fieldset>
				</s:form>
			</div>

			<div id="hottags" role="main">
				<h2>Hot Tags</h2>
				 <p>
				<s:property value="topTags"  escapeHtml="false" />
				 </p>
			</div>

			<div id="feeds">
				<ul>
					<li><a href="http://bbpress.org/forums/rss/">All Recent
							Posts</a>
					</li>

					<li><a href="http://bbpress.org/forums/rss/topics">All
							Recent Topics</a>
					</li>
				</ul>
			</div><!-- id=feeds -->

		</div><!-- id=grid_4 -->
		<div class="grid_8">
			<div id="discussions">
				<h2>
					Topics<span> &#8212; <a
						href="http://bbpress.org/forums/bb-login.php?redirect_to=http://bbpress.org/forums/?new=1"
						class="new-topic">start new</a> </span>
				</h2>

				<table id="latest">
				 <thead>
					<tr>
						<th class="topic">Topic &#8212; <a
							href="http://usemodj.com/bbpress/bb-login.php?re=http://usemodj.com/bbpress/?new=1"
							class="new-topic">Add New &raquo;</a>
						</th>
						<th class="num">Posts</th>
						<th class="num">Voices</th>
						<th class="date end">Last Post</th>
					</tr>
					</thead>
					<tbody>
					<s:iterator value="stickyTopics" status="status" var="topic">
						<s:bean name="com.usemodj.forum.bean.TopicCSS" var="topicCSS">
							<s:param name="topic" value="#topic"></s:param>
						</s:bean>

						<tr <s:property  value="#topicCSS.getTopicClass('', 1, true)"/>>
							<s:url var="topicUrl" namespace="/forum" action="topic">
								<s:param name="topic.topicId" value="%{topicId}" />
							</s:url>

							<td><s:a href="%{topicUrl}"><s:property value="topicTitle" /></s:a>
									<div><span><s:property value="#topicCSS.closedOrStickyFront" /></span>
									 <span class="topic-pages"><abbr title="Pages">p.</abbr><s:property value="getTopicPageLinks(#topic)" escapeHtml="false" /></span>
									</div> 
							</td>
							<td class="num"><s:property value="topicPosts" />
							</td>
							<td class="num"><s:property value="#topic.getMetaValue('voices_count')" />
							</td>
							<s:url var="topicUrlAnchor" namespace="/forum" action="topic"
								anchor="post-%{topicLastPostId}">
								<s:param name="topic.topicId" value="%{topicId}" />
								<s:param name="page" value="%{getPageNumber(topicPosts)}"/>
							</s:url>
							<s:date var="topicDateTime" name="topicTime"
								format="yyyy-MM-dd - HH:mm a" />
							<td class="date"><s:a href="%{topicUrlAnchor}"
									title="%{topicDateTime}">
									<s:date name="topicTime" nice="true"
										format="struts.date.format.past" />
										<div><s:property value="topicLastPosterName" /></div>
								</s:a></td>
						</tr>
					</s:iterator>

					<s:iterator value="paginate.results" status="status" var="topic">
						<s:bean name="com.usemodj.forum.bean.TopicCSS" var="topicCSS">
							<s:param name="topic" value="#topic"></s:param>
						</s:bean>

						<tr <s:property  value="#topicCSS.getTopicClass('', 1,  true)"/>>
							<s:url var="topicUrl" namespace="/forum" action="topic">
								<s:param name="topic.topicId" value="%{topicId}" />
							</s:url>

								<td><s:a href="%{topicUrl}"><s:property value="topicTitle" /></s:a>
									<div><span><s:property value="#topicCSS.closedOrStickyFront" /></span>
									 <span class="topic-pages"><abbr title="Pages">p.</abbr><s:property value="getTopicPageLinks(#topic)" escapeHtml="false" /></span>
									</div> 
							</td>
							<td class="num"><s:property value="topicPosts" />
							</td>
							<td class="num"><s:property value="voicesCount" />
							</td>
							<s:url var="topicUrlAnchor" namespace="/forum" action="topic"
								anchor="post-%{topicLastPostId}">
								<s:param name="topic.topicId" value="%{topicId}" />
								<s:param name="page" value="%{getPageNumber(topicPosts)}"/>
							</s:url> 
							<s:date var="topicDateTime" name="topicTime"
								format="yyyy-MM-dd  HH:mm a" />
							<td class="date"><s:a href="%{topicUrlAnchor}"
									title="%{topicDateTime}">
									<s:date name="topicTime" nice="true"
										format="struts.date.format.past" />
										<div><s:property value="topicLastPosterName" /></div>
								</s:a></td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
			</div> <!--  id=discussions -->
			<s:property value="getTopicPages()"  escapeHtml="false" />
			
		</div> <!-- id=grid_8 -->
		<div class="clear"></div>

		</div><!--  id=main -->

	<div class="clear"></div>
	<div id="contentinfo" role="contentinfo" class="container_12">
		<div id="extra" class="grid_2">
			<ul>
				<li><a href="/about/privacy/">Privacy</a>
				</li>
				<li><a href="/about/gpl/"><abbr title="GNU Public License">GPL</abbr>
				</a>
				</li>
			</ul>
		</div>

		<div id="also" class="grid_8">
			<h2>See also:</h2>
			<ul>
				<li><a href="http://wordpress.org/">WordPress.org</a>
				</li>
				<!-- <li><a href="http://wordpress.com/">WordPress.com</a></li> -->
				<li><a href="http://wordpress.tv/">WordPress.TV</a>
				</li>
				<li><a href="http://wordcamp.org/">WordCamp</a>
				</li>

				<li><a href="http://jobs.wordpress.net/">WP Jobs</a>
				</li>
				<li><a href="http://ma.tt/">Matt</a>
				</li>
				<li><a
					href="http://www.facebook.com/pages/bbPress/37285629283/">Fan
						bbPress on Facebook</a>
				</li>
				<li><a href="http://bbpress.org/blog/feed/" class="feed">Blog
						Feed</a>
				</li>
			</ul>
		</div>
		<div class="clear"></div>

	</div><!-- id="contentinfo" -->

</body>