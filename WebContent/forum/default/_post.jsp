<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
		<div id="position-<s:property value='postPosition'/>">
			<div class="threadauthor">
				<?php post_author_avatar_link(); ?>
				<p>
					<strong><?php post_author_link(); ?></strong><br />
					<small><?php post_author_title_link(); ?></small>
				</p>
			</div>
			<div class="threadpost">
				<div class="post"><s:property value='postText'  escapeHtml="false"/></div>
				<div class="poststuff">Posted <s:date name="postTime"  nice="true"  format="struts.date.format.past"/> 
				<a href="#post-<s:property  value='postId' />"> #</a> <?php bb_post_admin(); ?></div>
			</div>
		</div>