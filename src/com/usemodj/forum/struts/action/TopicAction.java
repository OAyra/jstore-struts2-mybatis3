package com.usemodj.forum.struts.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.domain.Post;
import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.service.ForumService;
import com.usemodj.forum.service.MetaService;
import com.usemodj.forum.service.PostService;
import com.usemodj.forum.service.TopicService;
import com.usemodj.struts.Paginate;
import com.usemodj.struts.Paginate.LinkType;
import com.usemodj.struts.action.BaseAction;

public class TopicAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8764358382477901739L;
	private static Logger logger = Logger.getLogger( TopicAction.class);
	TopicService topicService = new TopicService();
	PostService postService = new PostService();
	ForumService forumService = new ForumService();
	MetaService metaService = new MetaService();
	Topic topic = null;
	List<Post> posts = null;
	Forum  forum = null;
	 Paginate paginate = new Paginate();
	int page;
	String view;
	 String siteName;
	String breadcrumbs;
	String topicPostsLink;
	 String topicVoices;
	private String topicLastPostLink;
			
	public String execute() throws Exception {
		SqlSession sqlSession = null;
		//location is topic
		this.setLocation(Location.TOPIC);
		
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();

			this.topic = topicService.getTopic( sqlSession, this.topic.getTopicId());
			if( 0 < this.page) this.paginate.setPage( this.page);
			this.paginate.setObjectId( this.topic.getTopicId());
			this.posts = postService.getPosts( sqlSession, view, this.paginate);
			this.forum = forumService.getForum( sqlSession, this.topic.getForumId());
			
			this.siteName = metaService.getBBOption(sqlSession, "name");
			//forum's bread crumb
			String cssClass = "";
			this.breadcrumbs = forumService.getForumBreadCrumb( sqlSession,  request.getContextPath(), this.getForum().getForumId(),  "&raquo;", cssClass, true);

			boolean isAdmin = false;
			boolean browseDeleted = true;
			this.page = this.page < 1? 1: this.page;
			this.topicPostsLink = this.getTopicPostsLink(topic, this.page, request.getContextPath(), this.view, isAdmin, browseDeleted);
			
			int voices  = getTopicVoices(sqlSession, this.topic);
			this.topicVoices = ( 1 < voices)?  String.format(" %d voices",  voices):  String.format(" %d voice",  voices);	
			this.topicLastPostLink = getTopicLastPostLink( this.topic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//sqlSession.commit();
			sqlSession.close();
		}
		
		return Action.SUCCESS;
	}

//	public String getPostForm( Forum forum, Topic topic, int page, String view, boolean browseDeleted, Location location, String h2, boolean lastPageOnly, String contextPath) {
//		String form = "";
//		if( null != forum && "1".equals( forum.getMetaValue("forum_is_category") )) {
//			return "";
//		}
//		int add = getTopicPagesAdd( topic, view,  browseDeleted);
//		if( StringUtils.isBlank( h2)){
//			h2 = getText("Reply");
//		} else if( Location.FORUM == location){
//			h2 = getText( "New Topic in this Forum");
//		} else if( Location.TAG == location || Location.FRONT == location) {
//			h2 = getText("Add New Topic");
//		}
//		
//		int lastPage = Paginate.getPageNumber( topic.getTopicPosts(), Paginate.PER_PAGE);
//		if( ! StringUtils.isBlank(h2)){
//			if( Location.TOPIC == location && page != lastPage && lastPageOnly){
//				h2 = "<a href='"+ getTopicLink(contextPath, topic.getTopicId(), lastPage, view) + "#postform'>"+ h2 + "&raquo;</a>";
//			}
//			form += "<h2 class='post-form'>"+ h2 + "</h2>\n";			
//		}
//		
//		//TODO: getPostForm
//		if( (Location.TOPIC ==location /*&& bb_current_user_can("write_post", $topic->topic_id) */ && (page == lastPage || lastPageOnly)) 
//				|| (Location.TOPIC != location /* && bb_current_user_can("write_topic", isset($forum->forum_id)? $forum->forum_id: 0)*/ )) {
//			form += "<form class='postform post-form' id='postform' method='post' action='"+ contextPath + "/forum/post.action'>\n";
//			form += "<fieldset>\n";
//			//bb_load_template( 'post-form.php', array('h2' => $h2) );
//			if( Location.FORUM == location){
//				form += "<input type='hidden' name='forum.forum_id' value='"+ forum.getForumId() +"' /> \n";
//			} else if( Location.TOPIC == location){
//				form += "<input type='hidden' name='topic.topic_id' value='"+ topic.getTopicId() +"' /> \n";
//			}
//			
//			form += "\n</fieldset>\n</form>\n";
//		} else if( ! isUserLoggedIn() ) {
//			form += "<p>"+ getText("You must <a href='%s'>log in</a> to post.", new String[]{contextPath + "/forum/login"})+ "</p>";
//		}
//		
//		return form;
//	}
	
	boolean isUserLoggedIn(){
		return false;
	}
	
	public int getTopicPagesAdd() {
		//TODO:  bb_current_user_can('browse_deleted') 
		boolean browseDeleted = true;
		return Paginate.getTopicPagesAdd( this.topic, this.view, browseDeleted);
	}
	
	public int  getLastPage() {
		logger.debug("-- topic.getTopicPosts(): " +  topic.getTopicPosts() + ", -- getTopicPagesAdd() : " + getTopicPagesAdd() );
		return Paginate.getPageNumber( topic.getTopicPosts()+ getTopicPagesAdd(), Paginate.PER_PAGE);
	}
	
	public String getTopicLink() {
		return getTopicLink(request.getContextPath(), this.topic.getTopicId(), getLastPage(), this.view);
	}
	public String getTopicPages(){
		String before = "<div class='nav'>";
		String after ="</div>";
		
		return getTopicPages( this.topic, this.page, this.view, before, after);
	}
	
	public String getTopicPages( Topic topic, int page, String view, String before, String after ) {
		String uri = request.getContextPath()+"/forum/topic";
		String queryString = request.getQueryString();
		logger.debug("-- request.getQueryString(): " + queryString);
		String format = "page=%#%";
		if( queryString.indexOf("page=") == -1){
			queryString = queryString.replaceFirst("&+$", "");
			queryString += "&%_%";
		} else {
			queryString = queryString.replaceAll("page=[0-9]*", "%_%");// %_% : replace by format (page=%#%)
			//queryString = queryString.replace("page=", "");
		}
		uri += "?"+ queryString;
		
		boolean browseDeleted = true;
		boolean showFirst = false;
		long total = topic.getTopicPosts() +  Paginate.getTopicPagesAdd( topic, view, browseDeleted);
		String prevText = getText("&laquo; Previous");
		String nextText = getText("Next &raquo;");
		String prevTitle = getText("Previous page");
		String nextTitle = getText( "Next page");
		String nTitle = getText("Page %d");
		int perPage = Paginate.PER_PAGE;
		int midSize = 2; 
		int endSize = 1;
		LinkType type = LinkType.FLAT;
		String addFragment ="";
		boolean dots = false;
		boolean showAll = false;
		boolean preNext = true;
		return before + Paginate.getPaginateLinks(uri, page,  total, midSize, endSize, perPage, format, type, 
				addFragment, dots, showAll, preNext, prevText, nextText, prevTitle, nextTitle, nTitle) + after;
	}
	
	public boolean currentUserCan(String str, Object obj) {
		//TODO: currentUserCan
		return true;
	}
	
	String getTopicLastPostLink( Topic topic) {
		int page = Paginate.getPageNumber( topic.getTopicPosts(), Paginate.PER_PAGE);
		return getTopicLink( request.getContextPath(),  topic.getTopicId(), page, view) + "#post-"+ topic.getTopicLastPostId();
	}
	protected String getTopicPostsLink( Topic topic, int page,  String contextPath, String view, boolean isAdmin, boolean browseDeleted) {
		long postNum = topic.getTopicPosts();
		String posts = ( 1< postNum)? String.format("%d posts", postNum) : String.format("%d post", postNum);
		
		StringBuffer buf = new StringBuffer();
		if(( "all".equals(view)  || isAdmin) && browseDeleted )
			buf.append("<a href='").append( getTopicLink( request.getContextPath(), topic.getTopicId(), page, view))
			.append( "'>").append( posts).append("</a>");
		else
			buf.append( posts);

	//		if ( bb_current_user_can( 'browse_deleted' ) ) {
	//			$user_id = bb_get_current_user_info( 'id' );
	//			if ( isset($topic->bozos[$user_id]) && 'all' != @$_GET['view'] )
	//				add_filter('get_topic_deleted_posts', create_function('$a', "\$a -= {$topic->bozos[$user_id]}; return \$a;") );
	//			if ( $deleted = get_topic_deleted_posts( $id ) ) {
	//				$extra = sprintf(__('+%d more'), $deleted);
	//				if ( 'all' == @$_GET['view'] )
	//					$r .= " $extra";
	//				else
	//					$r .= " <a href='" . esc_attr( add_query_arg( 'view', 'all', get_topic_link( $id ) ) ) . "'>$extra</a>";
	//			}
	//		}
		if( browseDeleted){
			long deleted = getTopicDeletedPosts(topic);
			logger.debug("-- deleted Posts: "+ deleted);
			if( deleted > 0) {
				String extra = String.format(" +%d more", deleted);
				if( "all".equals(view) ) {
					logger.debug("-- view: "+ view);
					buf.append( extra	);
				}else	{
					buf.append( " <a href='").append(  getTopicLink(contextPath, topic.getTopicId(), page, view))
					.append("'>").append( extra).append("</a>");
				}
			}
		}

		return  buf.toString();
	}
	
	long getTopicDeletedPosts( Topic topic) {
		long deletedPosts;
		try {
			deletedPosts = Long.parseLong( topic.getMetaValue( "deleted_posts"));
		} catch (NumberFormatException e) {
			deletedPosts =0;
		}
		return deletedPosts;
	}
	
	String getTopicLink( String contextPath, long topicId, int page, String view){
		return  contextPath + "/forum/topic.action?topic.topicId="+ topicId + "&page="+page + (StringUtils.isBlank(view)? "": "&view="+view);
	}
	
	int getTopicVoices( SqlSession sqlSession, Topic topic) throws Exception {
		int voices = 0;
		try {
			logger.debug("-- topic voices_count: " +topic.getMetaValue("voices_count") );
			voices = Integer.parseInt( topic.getMetaValue("voices_count"));
		} catch (NumberFormatException e) {
			 voices = postService.getTopicVoices( sqlSession, topic.getTopicId());
			updateTopicMeta( sqlSession, topic.getTopicId(), "voices_count", voices);
		}
		return voices;
	}
	
	
	private void updateTopicMeta(SqlSession sqlSession, long topicId,
			String metaKey, int metaValue) throws Exception {
		metaService.updateMeta( sqlSession, topicId, metaKey, metaValue, "bb_topic");
		
	}


	// === getter /setter ==
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public Paginate getPaginate() {
		return paginate;
	}

	public void setPaginate(Paginate paginate) {
		this.paginate = paginate;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getBreadcrumbs() {
		return breadcrumbs;
	}

	public void setBreadcrumbs(String breadcrumbs) {
		this.breadcrumbs = breadcrumbs;
	}

	public String getTopicPostsLink() {
		return topicPostsLink;
	}

	public void setTopicPostsLink(String topicPostsLink) {
		this.topicPostsLink = topicPostsLink;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getTopicVoices() {
		return topicVoices;
	}

	public void setTopicVoices(String topicVoices) {
		this.topicVoices = topicVoices;
	}

	public String getTopicLastPostLink() {
		return topicLastPostLink;
	}

	public void setTopicLastPostLink(String topicLastPostLink) {
		this.topicLastPostLink = topicLastPostLink;
	}

	
}
