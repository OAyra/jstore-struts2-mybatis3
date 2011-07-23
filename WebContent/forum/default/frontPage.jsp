<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<body id="front-page">
	<div id="wrapper">
		<div id="header" role="banner">
			<h1>
				<a href="http://usemodj.com/bbpress/">bbpress 게시판</a>
			</h1>
			<p class="description">Just another bbPress community</p>
			<form class="login" method="post"
				action="http://usemodj.com/bbpress/bb-login.php">
				<p>
					<a href="http://usemodj.com/bbpress/register.php">Register</a> or
					log in - <a href="http://usemodj.com/bbpress/bb-login.php">lost
						password?</a>

				</p>
				<div>
					<label> Username<br /> <input name="user_login"
						type="text" id="quick_user_login" size="13" maxlength="40"
						value="" tabindex="1" /> </label> <label> Password<br /> <input
						name="password" type="password" id="quick_password" size="13"
						maxlength="40" tabindex="2" /> </label> <input name="re" type="hidden"
						value="" /> <input type="hidden" name="_wp_http_referer"
						value="/bbpress/" /> <input type="submit" name="Submit"
						class="submit" value="Log in &raquo;" tabindex="4" />
				</div>
				<div class="remember">
					<label> <input name="remember" type="checkbox"
						id="quick_remember" value="1" tabindex="3" /> Remember me </label>
				</div>
			</form>
			<div class="search">
				<form class="search-form" role="search"
					action="http://usemodj.com/bbpress/search.php" method="get">
					<p>
						<label class="hidden" for="q">Search:</label> <input class="text"
							type="text" size="14" maxlength="100" name="q" id="q" /> <input
							class="submit" type="submit" value="Search &raquo;" />

					</p>
				</form>
			</div>
		</div>
		<div id="main">

			<div id="hottags" role="main">
				<h2>Hot Tags</h2>
				<p class="frontpageheatmap">
					<a href='http://usemodj.com/bbpress/tags.php?tag=bbpress'
						title='2 topics' rel='tag' style='font-size: 22pt;'>bbPress</a> <a
						href='http://usemodj.com/bbpress/tags.php?tag=hello'
						title='1 topics' rel='tag' style='font-size: 8pt;'>hello</a> <a
						href='http://usemodj.com/bbpress/tags.php?tag=world'
						title='1 topics' rel='tag' style='font-size: 8pt;'>world</a> <a
						href='http://usemodj.com/bbpress/tags.php?tag=%ec%b9%b4%ed%85%8c%ea%b3%a0%eb%a6%ac'
						title='1 topics' rel='tag' style='font-size: 8pt;'>카테고리</a> <a
						href='http://usemodj.com/bbpress/tags.php?tag=%ed%85%8c%ec%8a%a4%ed%8a%b8'
						title='1 topics' rel='tag' style='font-size: 8pt;'>테스트</a>
				</p>
			</div>

			<div id="discussions">

				<h2>Latest Discussions</h2>

				<table id="latest">
					<tr>
						<th>Topic &#8212; <a
							href="http://usemodj.com/bbpress/bb-login.php?re=http://usemodj.com/bbpress/?new=1"
							class="new-topic">Add New &raquo;</a></th>
						<th>Posts</th>
						<!-- <th>Voices</th> -->
						<th>Last Poster</th>
						<th>Freshness</th>
					</tr>

				<s:iterator value="stickyTopics"  status="status" var="topic">
				
					<s:bean name="com.usemodj.forum.bean.TopicCSS"  var="topicCSS">
						<s:param name="topic"  value="#topic"></s:param>
					</s:bean>
						
					    <tr  <s:property  value="#topicCSS.getTopicClass('topic')"/> >
					    <s:url var="topicUrl"  namespace="/forum" action="topic">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
				
						<td><s:property value="#topicCSS.closedOrStickyFront"/>
							<s:a href="%{topicUrl}"><s:property value="topicTitle"/></s:a></td>
						<td class="num"><s:property value="topicPosts"/></td>
						<!-- <td class="num">1</td> -->
						<td class="num"><s:property value="topicLastPosterName"/></td>
					    <s:url var="topicUrlAnchor"  namespace="/forum" action="topic"  anchor="post-<s:property value=''postId/>">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
						<s:date var="topicDateTime"  name="topicTime"  format="yyyy-MM-dd - HH:mm a"/>
						<td class="num"><s:a href="%{topicUrlAnchor}"  title="%{topicDateTime}"><s:date name="topicTime"  nice="true"/></s:a>
							</td>
					</tr>
				</s:iterator>

				<s:iterator value="paginate.results"  status="status">
					<tr <s:if test="#status.odd == true">class='alt'</s:if>>
					    <s:url var="topicUrl"  namespace="/forum" action="topic">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
					
						<td><s:a href="%{topicUrl}"><s:property value="topicTitle"/></s:a></td>
						<td class="num"><s:property value="topicPosts"/></td>
						<!-- <td class="num">1</td> -->
						<td class="num"><s:property value="topicLastPosterName"/></td>
					    <s:url var="topicUrlAnchor"  namespace="/forum" action="topic"  anchor="post-<s:property value=''postId/>">
				          <s:param name="topic.topicId" value="%{topicId}"/>
				        </s:url>
						<s:date var="topicDateTime"  name="topicTime"  format="yyyy-MM-dd - HH:mm a"/>
						<td class="num"><s:a href="%{topicUrlAnchor}"  title="%{topicDateTime}"><s:date name="topicTime"  nice="true"/></s:a>
							</td>
					</tr>
				</s:iterator>
				</table>

				<h2>Forums</h2>
				<table id="forumlist">

					<tr>
						<th>Main Theme</th>
						<th>Topics</th>
						<th>Posts</th>

					</tr>
					<tr class="bb-parent bb-root">
						<td><a href="http://usemodj.com/bbpress/forum.php?id=2">bbpress
								테스트</a><small> &#8211; 우분투 환경에서 테스트</small>
						</td>
						<td class="num">3</td>
						<td class="num">3</td>
					</tr>
					<tr class="bb-parent bb-first-child bb-child alt">
						<td><div class="nest">
								<a href="http://usemodj.com/bbpress/forum.php?id=3">child of
									bbpress 테스트</a><small> &#8211; child forum</small>
							</div>
						</td>

						<td class="num">1</td>
						<td class="num">1</td>
					</tr>
					<tr class="bb-last-child bb-first-child bb-child">
						<td><div class="nest">
								<div class="nest">
									<a href="http://usemodj.com/bbpress/forum.php?id=4">child
										of child of bbpress 테스트</a><small> &#8211; child of child</small>
								</div>
							</div>
						</td>
						<td class="num">0</td>

						<td class="num">0</td>
					</tr>
					<tr class="bb-precedes-sibling bb-follows-niece bb-root alt">
						<td><a href="http://usemodj.com/bbpress/forum.php?id=1">bbpress
								설치 및 분석</a>
						</td>
						<td class="num">2</td>
						<td class="num">16</td>
					</tr>
					<tr class="bb-category bb-parent bb-follows-sibling bb-root">
						<td colspan="3"><a
							href="http://usemodj.com/bbpress/forum.php?id=5">카테고리 A</a>
						</td>

					</tr>
					<tr class="bb-last-child bb-first-child bb-child alt">
						<td><div class="nest">
								<a href="http://usemodj.com/bbpress/forum.php?id=6">forum A</a><small>
									&#8211; test 카테고리</small>
							</div>
						</td>
						<td class="num">1</td>
						<td class="num">1</td>
					</tr>
				</table>

			</div>


		</div>
	</div>
	
	<div id="footer" role="contentinfo">
		<p>
			bbpress 게시판 is proudly powered by <a href="http://bbpress.org">bbPress</a>.
		</p>

		<!-- If you like showing off the fact that your server rocks -->

		<!-- <p class="showoff">
This page generated in 0.12 seconds, using 15 queries.		</p> -->
	</div>

</body>