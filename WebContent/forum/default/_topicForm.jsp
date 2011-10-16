<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
		<h2> <s:url var="homeUrl" value="/forum"/>
			Start New Topic <s:if test="forum.forumId  < 1"> <span> &#8212; <s:a	href="%{homeUrl}">cancel</s:a></span></s:if>
		</h2>
		<form class="postform post-form" id="postform" method="post"
			action="http://bbpress.org/forums/bb-post.php">
			<fieldset>

				<div id="position-new">
					<div class="grid_8 alpha omega">
						<div id="post-form-title-container">
							<label id="topic-label" for="topic">Title</label> <input
								name="topic.topicTitle" type="text" id="topic"  size="50" maxlength="80"
								tabindex="1" />
							<script type="text/javascript" charset="utf-8">
								var topic_label = document
										.getElementById('topic-label');
								var topic_input = document
										.getElementById('topic');
								topic_label.onclick = function() {
									topic_input.focus();
								}
								topic_input.onfocus = function() {
									if (topic_input.value === '') {
										jQuery('#topic-label').fadeOut(
												'fast');
									}
								}
								topic_input.onblur = function() {
									if (topic_input.value === '') {
										jQuery('#topic-label').fadeIn(
												'fast');
									}
								}
							</script>
						</div>
						<div id="topic-support-status-container">
							<label for="topic-support-status">Status</label> <select
								name="topic.topicStatus" id="topic-support-status"
								tabindex="1">
								<option value="1">resolved</option>
								<option value="0" selected="selected">not resolved</option>
								<option value="2">not a support question</option>
							</select>
						</div>
						<div id="post-form-post-container">
							<p id="post-form-allowed-container" class="allowed">
								Allowed markup:
								<code>a blockquote code em strong ul ol li</code>
								. <br />You can also put code in between backtick (
								<code>`</code>
								) characters.
							</p>
							<label for="post_content">Post</label>
							<textarea name="post.postText" cols="50" rows="8"
								id="post_content"  tabindex="2"></textarea>
							<script type="text/javascript" charset="utf-8">
								var post_allowed = document
										.getElementById('post-form-allowed-container');
								var post_content = document
										.getElementById('post_content');
								post_allowed.onclick = function() {
									post_content.focus();
								}
								post_content.onfocus = function() {
									if (post_content.value === '') {
										jQuery(
												'#post-form-allowed-container')
												.fadeOut('fast');
									}
								}
								post_content.onblur = function() {
									if (post_content.value === '') {
										jQuery(
												'#post-form-allowed-container')
												.fadeIn('fast');
									}
								}
							</script>
						</div>
						<div id="post-form-tags-container">
							<label id="tags-label" for="tags-input">Tags (comma
								seperated)</label> <input id="tags-input" name="post.tags" type="text"
								size="50" maxlength="100" value="" tabindex="3" />
							<script type="text/javascript" charset="utf-8">
								var tags_label = document
										.getElementById('tags-label');
								var tags_input = document
										.getElementById('tags-input');
								tags_label.onclick = function() {
									tags_input.focus();
								}
								tags_input.onfocus = function() {
									if (tags_input.value === '') {
										jQuery('#tags-label').fadeOut(
												'fast');
									}
								}
								tags_input.onblur = function() {
									if (tags_input.value === '') {
										jQuery('#tags-label').fadeIn(
												'fast');
									}
								}
							</script>
						</div>
						<div id="post-form-forum-container">
							<label for="forum-id" style="display:block">Forum</label>
							<s:if test="forum.forumId >0">
								<input  type="hidden" name="topic.forumId" value='<s:property value="forum.forumId"/>'>
								<span id="forum-id"><s:property value="getForum().getForumName()"/></span>
							</s:if>
							<s:else> 
								<s:property value="forumDropdown"  escapeHtml="false"/>
							</s:else>
						</div>
						<div id="post-form-submit-container" class="submit">
							<input type="submit" id="postformsub" name="Submit"
								value="Create Topic" tabindex="5" />
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
				<input type="hidden" id="_wpnonce" name="_wpnonce"
					value="889f2ecac2" /><input type="hidden"
					name="_wp_http_referer" value="/forums/?new=1" /> <label
					for="subscription_checkbox"> <input
					name="subscription_checkbox" id="subscription_checkbox"
					type="checkbox" value="subscribe" /> Notify me of follow-up
					posts via email </label>
			</fieldset>
		</form>
		
		