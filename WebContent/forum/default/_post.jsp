<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
	<div id="position-<s:property value='postPosition'/>">
		<div class="grid_2 alpha">
			<ul class="post-author-info">
				<li class="avatar"><img alt=""
					src="http://www.gravatar.com/avatar/8071730adc116f101d9781dfc84e4818?s=132&amp;d=http%3A%2F%2Fwww.gravatar.com%2Favatar%2Fad516503a11cd5ca435acc9bb6523536%3Fs%3D132"
					class="photo avatar avatar-132"
					style="height: 132px; width: 132px;" />
				</li>
				<li class="name">Regina13</li>
				<li class="role"><a href="http://bbpress.org/forums/profile/regina13">Member</a>
				</li>
			</ul>
			<ul class="post-info">
				<li>Posted <s:date name="postTime"  nice="true"  format="struts.date.format.past"/> 
				<a href="#post-<s:property  value='postId' />"> #</a> <!-- php bb_post_admin(); -->
				</li>
			</ul>
		</div>
		<div class="grid_7 omega post">
			<s:property value='postText'  escapeHtml="false"/>
		</div>
		<div class="clear"></div>
	</div>
	