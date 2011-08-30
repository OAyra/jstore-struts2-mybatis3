package com.usemodj.forum.struts.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.usemodj.forum.ForumsHierarchical;
import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.domain.Term;
import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.service.ForumService;
import com.usemodj.forum.service.MetaService;
import com.usemodj.forum.service.TermTaxonomyService;
import com.usemodj.forum.service.TopicService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Paginate;
import com.usemodj.struts.Paginate.LinkType;
import com.usemodj.struts.action.BaseAction;

public class ForumAction<E> extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1806709020315378331L;
	private static Logger logger = Logger.getLogger( ForumAction.class);
	protected ForumService forumService = new ForumService();
	protected TopicService topicService = new TopicService();
	protected MetaService metaService = new MetaService();
	private TermTaxonomyService  termTaxonomyService = new TermTaxonomyService();
	private Paginate paginate = new Paginate();
	private Forum forum = null;
	private List<Topic> stickyTopics = null;
	private List<Forum> forums = null;
	ForumsHierarchical fh = new ForumsHierarchical();
	int page = 1;
	private String siteName;
	String breadcrumbs;
	private  E  topTags;
	
	public String index() throws Exception{
		return execute();
	}
	public String execute() throws  Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();
			paginate.setLocation(Location.FRONT);
			if( 0< getPage()) this.paginate.setPage( getPage());
			topicService.getLatestTopics(sqlSession, paginate);
			logger.debug("----- ForumAction SUCCESS  count: "+ paginate.getCount());
			logger.debug("----- Paginate page: "+ paginate.getPage() + " , perPage: "+ paginate.getPerPage());
			//stickyTopics = (List<Topic>) paginate.getResults();
			stickyTopics = topicService.getStickyTopics(sqlSession, -1);
//			forums = forumService.getForumsWithMeta( sqlSession);
//			if( logger.isDebugEnabled())
//				logger.debug("--- ForumAction  Forums size : " +( ( forums  != null)? forums.size(): 0));
			Map<Long, Forum> forums = forumService.getForumsWithMetaMap(sqlSession);
			int childOf =0;
			int hierarchical = 1;
			int depth = 0;
			int cutBranch = 0;
			Map<Long, Forum> forumsMap = this.fh.getForumsMap( forums, childOf, hierarchical, depth, cutBranch);
			this.fh.getForumsLoopElements(forumsMap);
			request.setAttribute("fh", this.fh);
			//Top Tags
			//getTagHeatMap(SqlSession sqlSession, int page, String contextPath)
			this.topTags = (E)getTagHeatMap( sqlSession,  0, request.getContextPath());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error( "-- ForumAction.execute() Exception: "+  e.getMessage());
		} finally {
			if( null != sqlSession) sqlSession.close();
		}
		
		return Action.SUCCESS;
	}
	
	public String forum() throws Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();
			//site name
			this.siteName = metaService.getBBOption(sqlSession, "name");
			//forum's bread crumb
			String cssClass = "";
			this.breadcrumbs = forumService.getForumBreadCrumb( sqlSession,  request.getContextPath(), this.getForum().getForumId(),  "&raquo;", cssClass, true);
			// latest topics
			paginate.setLocation(Location.FORUM);
			paginate.setObjectId( this.getForum().getForumId());
			paginate.setPage( this.getPage());
			topicService.getLatestTopics(sqlSession, paginate);
			logger.debug("----- ForumAction FORUM  count: "+ paginate.getCount());
			logger.debug("----- Paginate page: "+ paginate.getPage() + " , perPage: "+ paginate.getPerPage());
			
			// sticky topics
			stickyTopics = topicService.getStickyTopics(sqlSession,  forum.getForumId());
			Map<Long, Forum> forums = forumService.getForumsWithMetaMap(sqlSession);
			int childOf = forum.getForumId();
			int hierarchical = 1;
			int depth = 0;
			int cutBranch = 0;
			Map<Long, Forum> forumsMap = fh.getForumsMap( forums, childOf, hierarchical, depth, cutBranch);
			fh.getForumsLoopElements(forumsMap);
			request.setAttribute("fh", fh);
		
		} catch (Exception e) {
			// 	e.printStackTrace();
			logger.error( "-- ForumAction.forum() Exception: "+  e.getMessage());
		} finally {
			sqlSession.close();
		}
		
		return "forum";
	}
	
	public String list() throws Exception {
		//int  forumId = forum.getForumId();
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();
			forumService.getForums(sqlSession, paginate);
			logger.debug(" ------ Paginate count: " + paginate.getCount() + ", page: "+ paginate.getPage());
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error( "------- ForumAction.list() Excteption: " + e.getMessage());
		} finally {
			sqlSession.close();
		}
		
		return Constants.LIST;
	}
	
	public int getPageNumber( long rows){
		return Paginate.getPageNumber(rows, Paginate.PER_PAGE);
	}
	public String getTopicPageLinks(Topic topic){
		 
		boolean showAll =false;
		int endSize =3;
		String before ="-";
		String after = null;
		return getTopicPageLinks(topic, showAll, endSize, before, after, request.getContextPath());
	}
	
	public String getTopicPageLinks(Topic topic, boolean showAll, int endSize, String before, String after, String contextPath){
		String uri = contextPath + "/forum/topic?topic.topicId="+ topic.getTopicId();
		uri += "&%_%"; // page=%#%"
		//TODO: view, browseDeleted
		String view = "all";
		boolean showFirst = false;
		boolean browseDeleted = true;
		long total = topic.getTopicPosts() +  Paginate.getTopicPagesAdd( topic, view, browseDeleted);
		String prevText = getText("&laquo; Previous");
		String nextText = getText("Next &raquo;");
		String prevTitle = getText("Previous page");
		String nextTitle = getText( "Next page");
		String nTitle = getText("Page %d");
		int perPage = Paginate.PER_PAGE;
		String format = "page=%#%";
		int midSize = 2;
		int current =1;
		String addFragment = "";
		boolean dots = true;
		boolean preNext = false;
		LinkType type = LinkType.ARRAY;
		List pageLinks = Paginate.getPaginateLinks(uri, current, total, midSize, endSize, perPage, format, type
											, addFragment, dots, showAll, preNext, prevText, nextText, prevTitle, nextTitle, nTitle);
			
		if(  null != pageLinks && !pageLinks.isEmpty()) {
			if( !showFirst) pageLinks.remove( 0);
		}
		String ret = "";
		if( null != pageLinks){
			if( null != before) ret += before;
			ret += StringUtils.join( pageLinks, "");
			if( null != after) ret += after;
		}
		return ret;
	}

	public <E> E getTagHeatMap(SqlSession sqlSession, int page, String contextPath) throws Exception{
		int smallest = 8; 
		int largest = 22;
		String unit = "pt";
		int limit = 40;
		LinkType format = LinkType.FLAT; 
		//LinkType format = LinkType.LIST;
		List<Term> tags = getTopTags( sqlSession,  limit);
		Map<String, Long> counts = new HashMap<String, Long>();
		Map<String, String> tagLinks = new HashMap<String, String>();
		for( Term t: tags ){
			counts.put(t.getName(), t.getTt().getCount());
			tagLinks.put(t.getName(), getTagLink(t, page, contextPath));
		}
		
		long minCount = Collections.min( counts.values());
		logger.debug("-- minCount: " + minCount);
		int spread = (int) (Collections.max( counts.values()) - minCount);
		if( 0 >= spread) spread = 1;
		int fontSpread = largest - smallest;
		if( 0 >= fontSpread) fontSpread = 1;
		
		int fontStep = fontSpread / spread;
		//do_action_ref_array( 'sort_tag_heat_map', array(&$counts) );
		List<String>  list = new ArrayList<String>();
		for(Map.Entry<String, Long> e: counts.entrySet()){
			String tag = e.getKey();
			long count = e.getValue();
			String tagLink = tagLinks.get(tag);
			tag = tag.replace(" ", "&nbsp;");
			int fontSize = Math.round( (float)smallest +((count - minCount)* fontStep));
			list.add("<a href='"+ tagLink + "' title='" + String.format("%d topics", count) 
					+ "' rel='"+ tag + "' style='font-size:"+ fontSize + unit+ ";' >"+ tag + "</a>" );
		}
		String ret = "";
		switch( format){
			case ARRAY:
				return (E) list;
			case LIST:
				//ret = "<ul class='bb-tag-heat-map'>\n\t<li>";
				//ret ="<ul id='tags-list' class='tags-list list:tag'>\n\t<li>";
				ret = "<ul>\n\t<li>";
				ret += StringUtils.join( list, "</li>\n\t<li>");
				ret += "</li>\n</ul>\n";
				break;
			default:
				ret = StringUtils.join( list, "\n");
				break;
		}
		
		return (E) ret;
	}
	
	public String getTopicPages(){
		return getTopicPages("<div class='nav'>", "</div>");
	}
	public String getTopicPages(String before, String after){
//		String before = "<div class='nav'>";
//		String after ="</div>";
		logger.debug("--- getTopicPages: page="+this.page + ", total="+ this.paginate.getCount() );
		return getTopicPages(this.page, this.paginate.getCount(), before, after);
	}

	public String getTopicPages( int page, long total, String before, String after ) {
		String uri = request.getContextPath()+"/forum/";
		String queryString = request.getQueryString();
		logger.debug("-- getTopicPages: request.getQueryString(): " + queryString);
		String format = "page=%#%";
		if(  "null".equals(queryString) ||  StringUtils.isBlank( queryString)){
			queryString = "%_%";
		}else	if( queryString.indexOf("page=") == -1){
			queryString = queryString.replaceFirst("&+$", "");
			queryString += "&%_%";
		} else {
			queryString = queryString.replaceAll("page=[0-9]*", "%_%");// %_% : replace by format (page=%#%)
			//queryString = queryString.replace("page=", "");
		}
		uri += "?"+ queryString;
		
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

	
	private String getTagLink(Term t, int page, String contextPath) {
		String tag = t.getName();
		page = (1< page)? page: 1;
		String baseUri = "/forum/tag_topics";
		String ret = getUri( baseUri, tag, page, contextPath);
		return ret;
	}
	private  String getUri(String baseUri, String tag, int page,	String contextPath) {
		String uri =  contextPath + baseUri + "?tag="+tag;
		if( 1< page) uri +=  "&page="+page;
		return uri;
	}
	
	List<Term> getTopTags( SqlSession sqlSession, int number) throws Exception {
		String taxonomy = "bb_topic_tag";
		List<Term> terms = termTaxonomyService.getTerms( sqlSession,  taxonomy, number);
		return terms;
	}
	/* util */
	
	/* Getter/Setter methods */
	public  Paginate getPaginate() {
		return paginate;
	}

	public void setPaginate(Paginate page) {
		this.paginate = page;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public List<Topic> getStickyTopics() {
		return stickyTopics;
	}

	public void setStickyTopics(List<Topic> topics) {
		this.stickyTopics = topics;
	}

	public List<Forum> getForums() {
		return forums;
	}

	public void setForums(List<Forum> forums) {
		this.forums = forums;
	}
	
	public ForumsHierarchical getFh() {
		return fh;
	}

	public void setFh(ForumsHierarchical fh) {
		this.fh = fh;
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
	public <E> E  getTopTags() {
		return (E) topTags;
	}
	public  void setTopTags( E topTags) {
		this.topTags = (E) topTags;
	}
	
}
