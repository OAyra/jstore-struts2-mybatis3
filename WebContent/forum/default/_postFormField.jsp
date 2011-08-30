<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{location.toString() == 'TOPIC' ">
<p id="post-form-title-container">
	<label for="topic"><s:text name='Title'/>
		<input name="topic" type="text" id="topic" size="50" maxlength="100" tabindex="1" />
	</label>
</p>
</s:if>

<p id="post-form-post-container">
	<label for="post_content"><s:text name='Post' />
		<textarea name="post_content" cols="50" rows="8" id="post_content" tabindex="3"></textarea>
	</label>
</p>
<p id="post-form-tags-container">
	<label for="tags-input"><s:text name='Tags (comma separated)' />
		<input id="tags-input" name="tags" type="text" size="50" maxlength="100" value=""  tabindex="4" />
	</label>
</p>

<s:if test="%{location.toString() == 'TAG' || location.toString() == 'FRONT'}">
<p id="post-form-forum-container">
	<label for="forum-id"><s:text name='Forum'/>
		<?php bb_new_topic_forum_dropdown(); ?>
	</label>
</p>
</s:if>
<p id="post-form-submit-container" class="submit">
  <input type="submit" id="postformsub" name="Submit" value="<s:text  name='Send Post &raquo;' />" tabindex="4" />
</p>

<p id="post-form-allowed-container" class="allowed"><s:text  name='Allowed markup:' /> 
<code>a blockquote pre code br em strong ul ol li</code>. <br />
<s:text  name='You can also put code in between backtick ( <code>`</code> ) characters.' />
</p>
