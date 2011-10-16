<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title><sitemesh:write property="title"	default="JavaPress Forum" />
</title>
<link	href="<%=request.getContextPath()%>/forum/default/decorators/reset.css"
	rel="stylesheet" type="text/css" />
<link	href="<%=request.getContextPath()%>/forum/default/decorators/text.css"
	rel="stylesheet" type="text/css" />
<link	href="<%=request.getContextPath()%>/forum/default/decorators/960.css"
	rel="stylesheet" type="text/css" />
<link	href="<%=request.getContextPath()%>/forum/default/decorators/style.css"
	rel="stylesheet" type="text/css" />

	
<script src="<%=request.getContextPath()%>/forum/default/decorators/jquery/jquery.js?ver=1.2.6"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/forum/default/decorators/wp-ajax-response.js?ver=20080316"
	type="text/javascript"></script>
<script type='text/javascript'>
	/*           */
	var wpListL10n = {
		url : "http://bbpress.org/forums/bb-admin/admin-ajax.php"
	};
	/*     */
</script>
<script src="<%=request.getContextPath()%>/forum/default/decorators/wp-lists.js?ver=20080826"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/forum/default/decorators/jquery/jquery.color.js?ver=2.0-4561"
	type="text/javascript"></script>
<script type='text/javascript'>
	/*           */
	var bbTopicJS = {
		currentUserId : "8262016",
		topicId : "35634",
		favoritesLink : "http://bbpress.org/forums/profile/usemodj/favorites",
		isFav : "0",
		confirmPostDelete : "Are you sure you want to delete this post?",
		confirmPostUnDelete : "Are you sure you want to undelete this post?",
		favLinkYes : "favorites",
		favLinkNo : "?",
		favYes : "This topic is one of your %favLinkYes% [%favDel%]",
		favNo : "%favAdd% (%favLinkNo%)",
		favDel : "&times;",
		favAdd : "Add this topic to your favorites"
	};
	/*     */
</script>
	
<script src="<%=request.getContextPath()%>/forum/default/decorators/topic.js?ver=20090602"
	type="text/javascript"></script>
	
<link rel="alternate" type="application/rss+xml"
	title="JavaPress Forum &raquo; Recent Posts"
	href="http://usemodj.com/bbpress/rss.php" />
<link rel="alternate" type="application/rss+xml"
	title="JavaPress Forum &raquo; Recent Topics"
	href="http://usemodj.com/bbpress/rss.php?topics=1" />
<meta name="generator" content="JavaPress  Forum 1.0" />

<sitemesh:write property="head" />

</head>
<body id='<s:property value="location.page"/>'>
    <sitemesh:write property="body" />
	<div class="clear"></div>
    <sitemesh:write property="footer" />
</body>

</html>
