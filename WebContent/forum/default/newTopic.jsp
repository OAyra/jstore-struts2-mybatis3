<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page
	import="com.usemodj.forum.ForumsHierarchical"%>
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
						while (fh.forumsLoopStep() > 0) {
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
			<div id="views">
				<h2>Views</h2>
				<ul>
					<li><a href="http://bbpress.org/forums/view/no-replies">Topics
							with no replies</a>
					</li>
					<li class="alt"><a
						href="http://bbpress.org/forums/view/untagged">Topics with no
							tags</a>
					</li>
					<li><a href="http://bbpress.org/forums/view/support-forum-no">Support
							topics that are not resolved and are more than 2 hours old</a>
					</li>
				</ul>
			</div><!--  id="views" -->
		</div><!-- id=grid_4 -->
		<div class="grid_8">
			<div id="discussions">
			
				<%@ include file="/forum/default/_topicForm.jsp" %>
			</div> <!--  id=discussions -->
			
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