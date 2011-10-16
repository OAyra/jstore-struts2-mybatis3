<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<body id="topic-page">
	<%@ include  file="/forum/default/_topContainer.jsp" %>
	<div role="main" class="container_12">
		<div class="container_12">
			<div id="crumbs" class="grid_8">
				<a href="<%= request.getContextPath() %>/forum/"><s:property value="siteName"/></a>
				<s:property value="breadcrumbs"  escapeHtml="false"/>
			</div>
		<s:if test="null != posts">
			<div class="nav grid_4"><s:property value="getTopicPages()"  escapeHtml="false"/></div>
		</s:if>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
		
		<div role="main" class="container_12">
			<div class="grid_3">
				<div id="discussion-info">
					<h2>Info</h2>
					<ul>
						<li><s:property value="topicPostsLink"  escapeHtml="false"/></li>
						<li class="alt"><s:property value="topicVoices"  escapeHtml="false"/></li>
														
						<li> Started  <s:date name="topic.topicTime"  nice="true"  format="struts.date.format.past"/>  by  <s:property value="topic.topicPosterName"/></li>
					<s:if  test="1 < topic.topicPosts" >
						<li><a href="<s:property value='topicLastPostLink'/>"> Latest reply</a> from <s:property value="topic.topicLastPosterName"/></li>
					</s:if>
					<?php if ( bb_is_user_logged_in() ) : ?>
						<%--   <li <?php echo $class;?> id="favorite-toggle"><?php user_favorites_link(); ?></li>--%>
					<?php endif; do_action('topicmeta'); ?>
						<li id="resolution-flipper">This topic is not resolved</li>
					</ul>
				</div>
				<div id="discussion-tags">
					<h2>Tags</h2>
					<ul id="tags-list" class="tags-list list:tag">
						<li id="tag-1859_7579890"><a
							href="http://bbpress.org/forums/tags/cannot-modify-header-information"
							rel="tag">Cannot modify header information</a></li>
						<li id="tag-291_7579890" class="alt"><a
							href="http://bbpress.org/forums/tags/cookie" rel="tag">cookie</a>
						</li>
						<li id="tag-168_7579890"><a
							href="http://bbpress.org/forums/tags/cookies" rel="tag">cookies</a>
						</li>
						<li id="tag-1090_7579890" class="alt"><a
							href="http://bbpress.org/forums/tags/global" rel="tag">global</a>
						</li>
						<li id="tag-37639_7579890"><a
							href="http://bbpress.org/forums/tags/global-variable" rel="tag">global
								variable</a></li>
						<li id="tag-37640_7579890" class="alt"><a
							href="http://bbpress.org/forums/tags/global-variables" rel="tag">global
								variables</a></li>
					</ul>
					<form id="tag-form" method="post"
						action="http://bbpress.org/forums/tag-add.php"
						class="add:tags-list:">
						<p>
							<input name="tag" type="text" id="tag" /> <input type="hidden"
								name="id" value="35634" /> <input type="hidden" name="page"
								value="1" /> <input type="hidden" id="_wpnonce" name="_wpnonce"
								value="0e62ed270a" /><input type="hidden"
								name="_wp_http_referer" value="/forums/topic/cookie-integration" />
							<input type="submit" name="submit" id="tagformsub"
								value="Add Tag" />
						</p>
					</form>
					
				</div>
				<div id="feeds">
					<ul>
						<li><a
							href="http://bbpress.org/forums/rss/topic/cookie-integration"
							title="RSS feed of recent posts in this topic">Posts in this
								topic</a>
						</li>
					</ul>
				</div>
			</div> <!-- class=grid_3 -->
			
			
			<div class="grid_9">
				<div id="discussion">
				<s:bean name="com.usemodj.forum.bean.TopicCSS"  var="topicCSS">
					<s:param name="topic"  value="#topic"></s:param>
				</s:bean>
				<h2 <s:property  value="#topicCSS.getTopicClass('topictitle', 0, true)"/>><s:property value="topic.topicTitle"/>
					<span>  &laquo;&#8212; <a href="<s:property value='%{getTopicLink()}'/>#postform" ><s:text name="reply"/></a></span>
				</h2>
		
				<div id="ajax-response"></div>
				<ol id="thread" class="list:post">
				<s:iterator value="posts"  status="status"  var="post">
					<s:bean name="com.usemodj.forum.bean.PostCSS"  var="postCSS">
						<s:param name="post"  value="#post"></s:param>
					</s:bean>
					
					<li id="post-<s:property value='postId'/>"    <s:property  value="#postCSS.getPostDelAltClass('post')" escapeHtml="false"/> >  
					<%@ include file="/forum/default/_post.jsp"%>
					</li>
				</s:iterator>
				<s:if test="null != posts">
				<li class="post"><s:property value="getTopicPages()"  escapeHtml="false"/></li>
				</s:if>
				<s:if test=" page == lastPage">			
					<li id="post-new" class="post">
					<%@ include file="/forum/default/_postForm.jsp" %>
					</li>
					</s:if>
				</ol> <!--  id="thread"  -->
				<div class="clear"></div>


				</div> <!-- id="discussion" -->
			</div> <!-- class="grid_9" -->
			<div class="clear"></div>
		</div>
		<div class="clear"></div>

	</div> <!--  role=main -->
	
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
	</div> <!--  id=contentinfo -->
		
</body>
