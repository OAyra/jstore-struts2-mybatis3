<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="jgravatar.Gravatar,jgravatar.GravatarRating,jgravatar.GravatarDefaultImage" %>
<% 
Gravatar gravatar = new Gravatar();
gravatar.setSize(132);
gravatar.setRating(GravatarRating.GENERAL_AUDIENCES);
gravatar.setDefaultImage(GravatarDefaultImage.IDENTICON);
String gravatarUrl = gravatar.getUrl("usemodj@gmail.com");
 %>
	<form class="postform post-form" id="postform" method="post"
		action="http://bbpress.org/forums/bb-post.php">
		<fieldset>
			<div id="position-new">
				<div class="grid_2 alpha">
					<ul class="post-author-info">
						<li class="avatar"><img alt=""
							src="<%= gravatarUrl %>"
							class="photo avatar avatar-132"
							style="height: 132px; width: 132px;" />
						</li>
						<li class="name">usemodj</li>
						<li class="role">Member</li>
					</ul>
				</div>
				<div class="grid_7 omega post">
					<div id="post-form-post-container">
						<p id="post-form-allowed-container" class="allowed">
							Allowed markup:
							<code>a blockquote code em strong ul ol li</code>
							. <br />You can also put code in between backtick (
							<code>`</code>
							) characters.
						</p>
						<label for="post_content">Post</label>
						<textarea name="post_content" cols="50" rows="8"
							id="post_content" tabindex="1"></textarea>
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
									jQuery('#post-form-allowed-container').fadeOut('fast');
								}
							}
							post_content.onblur = function() {
								if (post_content.value === '') {
									jQuery('#post-form-allowed-container').fadeIn('fast');
								}
							}
						</script>
					</div>
					<div id="post-form-tags-container">
						<label id="tags-label" for="tags-input">Tags (comma	seperated)</label> 
						<input id="tags-input" name="tags" type="text"	size="50" maxlength="100" value=""  tabindex="2" />
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
									jQuery(	'#tags-label').fadeOut('fast');
								}
							}
							tags_input.onblur = function() {
								if (tags_input.value === '') {
									jQuery('#tags-label').fadeIn('fast');
								}
							}
						</script>
					</div>
					<div id="post-form-submit-container" class="submit">
						<input type="submit" id="postformsub" name="Submit"
							value="Send Reply" tabindex="3" />
						<p class="allowed"  style="text-align:center;">
							Allowed markup:
							<code>a blockquote code em strong ul ol li</code>
							. <br />You can also put code in between backtick (
							<code>`</code>
							) characters.
						</p>
							
					</div>
					<div class="clear"></div>
					<input type="hidden" id="_wpnonce" name="_wpnonce"	value="aafa9c49cb" />
					<input type="hidden"	name="_wp_http_referer" 	value="/forums/topic/cookie-integration" />
					<input	type="hidden" name="topic_id" value="35634" /> 
					<label for="subscription_checkbox"> 
					<input	name="subscription_checkbox" id="subscription_checkbox"
					type="checkbox" value="subscribe" /> This user should be
					notified of follow-up posts via email </label>
					
				</div>
				<div class="clear"></div>
			</div>
		</fieldset>
	</form>
	