<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<body id="tag-page">
	<%@ include  file="/forum/default/_topContainer.jsp" %>
	
	<div role="main" class="container_12">
		<div class="container_12">
			<div id="crumbs" class="grid_9">
				<a href="<?php bb_uri(); ?>">
					<?php bb_option('name'); ?>
				</a> &raquo; <a href="<?php bb_tag_page_link(); ?>">
					<?php _e('Tags'); ?>
				</a> &raquo;
				<?php bb_tag_name(); ?>
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div><!-- role="main"  -->

	<div role="main" class="container_12">
		<div class="grid_4">
			<div id="simple-search">
				<s:url var="searchUrl" namespace="/forum" action="search" />
				<s:form method="get" action="%{searchUrl}" role="search">
					<fieldset>
						<label for="simple-search-input">Search</label> <input
							id="simple-search-input" type="search" maxlength="100"
							results="10" autosave="javapress-org-forums" name="q"> <input
							type="submit" value="Search">
					</fieldset>
				</s:form>
			</div>
			<div id="feeds">
				<ul>
					<li><a href="http://bbpress.org/forums/rss/tags/integration"
						title="RSS feed of recent posts tagged integration">Recent
							Posts tagged <em>integration</em>
					</a>
					</li>
					<li><a
						href="http://bbpress.org/forums/rss/tags/integration/topics"
						title="RSS feed of recent topics tagged integration">Recent
							Topics tagged <em>integration</em>
					</a>
					</li>
				</ul>
			</div>
		</div>
		<!-- class=grid_4 -->
		<div class="grid_8">
			<div id="discussion-form"></div>
			<div id="discussions">
				<h2>
					Topics<span> &#8212; <a
						href="http://bbpress.org/forums/bb-login.php?redirect_to=http://bbpress.org/forums/tags/integration#postform"
						class="new-topic">start new</a> </span>
				</h2>
				<table>
					<thead>
						<tr>
							<th class="topic "></th>
							<th class="num">Posts</th>
							<th class="num">Voices</th>
							<th class="date end">Last Post</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="topics" status="status" var="topic">
							<s:bean name="com.usemodj.forum.bean.TopicCSS" var="topicCSS">
								<s:param name="topic" value="#topic"></s:param>
							</s:bean>

							<tr <s:property  value="#topicCSS.getTopicClass('', 1,  true)"/>>
								<s:url var="topicUrl" namespace="/forum" action="topic">
									<s:param name="topic.topicId" value="%{topicId}" />
								</s:url>

								<td><s:a href="%{topicUrl}">
										<s:property value="topicTitle" />
									</s:a>
									<div>
										<span><s:property value="#topicCSS.closedOrStickyFront" />
										</span> <span class="topic-pages"><abbr title="Pages">p.</abbr>
										<s:property value="getTopicPageLinks(#topic)"
												escapeHtml="false" />
										</span>
									</div></td>
								<td class="num"><s:property value="topicPosts" /></td>
								<td class="num"><s:property value="voicesCount" /></td>
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
										<div>
											<s:property value="topicLastPosterName" />
										</div>
									</s:a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
			<!--  id=discussions -->
			<s:property value="%{getTagPages(\"<div class='nav'>\",  \"</div>\")}"  escapeHtml="false" />
		</div>
		<!-- id=grid_8 -->
		<div class="clear"></div>

	</div>
	<!--  id=main -->

</body>

