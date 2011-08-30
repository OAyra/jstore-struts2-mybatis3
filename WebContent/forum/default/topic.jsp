<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<body id="topic-page">
	<div id="wrapper">
		<div id="main">
			<div class="bbcrumb"><a href="<%= request.getContextPath() %>/forum/"><s:property value="siteName"/></a>
			<s:property value="breadcrumbs"  escapeHtml="false"/></div>
			<div class="infobox" role="main">
		
		<div id="topic-info">
			<s:bean name="com.usemodj.forum.bean.TopicCSS"  var="topicCSS">
				<s:param name="topic"  value="#topic"></s:param>
			</s:bean>
		
		<h2 <s:property  value="#topicCSS.getTopicClass('topictitle', 0, true)"/>><s:property value="topic.topicTitle"/></h2>
		<span id="topic_posts">(<s:property value="topicPostsLink"  escapeHtml="false"/>)</span>
		<span id="topic_voices">(<s:property value="topicVoices"  escapeHtml="false"/>)</span>
		
		<ul class="topicmeta">
			<li> Started  <s:date name="topic.topicTime"  nice="true"  format="struts.date.format.past"/>  by  <s:property value="topic.topicPosterName"/></li>
		<s:if  test="1 < topic.topicPosts" >
			<li><a href="<s:property value='topicLastPostLink'/>"> Latest reply</a> from <s:property value="topic.topicLastPosterName"/></li>
		</s:if>
		<?php if ( bb_is_user_logged_in() ) : ?>
			<%--   <li <?php echo $class;?> id="favorite-toggle"><?php user_favorites_link(); ?></li>--%>
		<?php endif; do_action('topicmeta'); ?>
		</ul>
		</div>
		
		<?php topic_tags(); ?>
		
		<div style="clear:both;"></div>
		</div><!-- class=infobox -->
	

<s:if test =" null != posts">
	<s:property value="getTopicPages()"  escapeHtml="false"/>
	<div id="ajax-response"></div>
	<ol id="thread" class="list:post">
	<s:iterator value="posts"  status="status"  var="post">
		<s:bean name="com.usemodj.forum.bean.PostCSS"  var="postCSS">
			<s:param name="post"  value="#post"></s:param>
		</s:bean>
	
		<li id="post-<s:property value='postId'/>"  <s:property  value="#postCSS.getPostDelAltClass()"/>>
		<%@ include file="/forum/default/_post.jsp"%>
		</li>
	</s:iterator>
	</ol>
	
	<div class="clearit"><br style=" clear: both;" /></div>
	<p class="rss-link"><a href="<?php topic_rss_link(); ?>" class="rss-link" ><s:text  name='<abbr title="Really Simple Syndication">RSS</abbr> feed for this topic' /></a></p>
	<s:property value="getTopicPages()"  escapeHtml="false"/>
</s:if>


<s:if test="topic.topicOpen == 1"  >
	<%@ include file="/forum/default/_postForm.jsp"  %>
</s:if>
<s:else>
<h2><s:text name="Topic Closed"/></h2>
<p><s:text name="This topic has been closed to new replies."/></p>
</s:else>
<?php if ( bb_current_user_can( 'delete_topic', get_topic_id() ) || bb_current_user_can( 'close_topic', get_topic_id() ) || bb_current_user_can( 'stick_topic', get_topic_id() ) || bb_current_user_can( 'move_topic', get_topic_id() ) ) : ?>

<div class="admin">
<?php bb_topic_admin(); ?>
</div>

<?php endif; ?>
		
		</div><!--  id= main -->
	</div><!--  id=wrapper -->
</body>
