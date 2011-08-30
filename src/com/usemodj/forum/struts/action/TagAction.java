package com.usemodj.forum.struts.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.usemodj.forum.domain.Term;
import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.service.TermTaxonomyService;
import com.usemodj.forum.service.TopicService;
import com.usemodj.struts.Paginate;
import com.usemodj.struts.Paginate.LinkType;
import com.usemodj.struts.action.BaseAction;

/**
 * @author jinny
 *
 */
public class TagAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3296020916707119048L;
	private static Logger logger = Logger.getLogger( TagAction.class);
	public final static String TOPICS = "topics";
	TermTaxonomyService termTaxonomyService = new TermTaxonomyService();
	TopicService topicService = new TopicService();
	String tag = null;
	int page = 1;
	 List<Topic> topics = null;
	 Term term = null;
	private Paginate paginate;
	 
	public String execute() throws Exception {
		
		return Action.SUCCESS;
	}
	
	public String topics() throws Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = getForumSqlSessionFactory().openSession();
			long userId = 0;
			long topicId = 0;
			int page = (this.page <1)? 1: this.page;
			this.term = getTag( sqlSession, this.tag, userId, topicId);
			this.paginate = new Paginate();
			this.paginate.setPage(page);
			this.paginate.setPerPage( Paginate.PER_PAGE);
			if( null != this.term)  {
				this.topics = getTaggedTopics( sqlSession, term.getTermId(),  this.paginate);
				logger.debug("--- paginate tagged topics: "+ paginate.getResults().size());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if( null != sqlSession) sqlSession.close();
		}
		return TOPICS;
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
		boolean dots = false;
		boolean prevNext = false;
		LinkType type = LinkType.ARRAY;
		 
		uri += "&view="+ view;
		List pageLinks = Paginate.getPaginateLinks(uri,  current, total, midSize, endSize, perPage, format, type
									, addFragment, dots, showAll, prevNext, prevText, nextText, prevTitle, nextTitle, nTitle);
			
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
	
	public String getTagPages( String before, String after){
		String view = "all";
		return getTagPages( this.tag, this.page, view, before, after);
	}
	public String getTagPages( String tag, int page, String view, String before, String after ) {
		String uri = request.getContextPath()+"/forum/tag_topics";
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
		//boolean showFirst = false;
		//long total = topic.getTopicPosts() +  Paginate.getTopicPagesAdd( topic, view, browseDeleted);
		long total = this.paginate.getCount();
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

	 List getTaggedTopics( SqlSession sqlSession,  long tagId, Paginate paginate) throws Exception {
		 long userId = 0;
		 long topicId = 0;
		Term term = getTag(sqlSession, tagId, userId, topicId);
		if( null == term) return null;
		List<Long> taggedTopicIds = getTaggedTopicIds(  sqlSession,  term.getTermId());
		logger.debug("--- taggedTopicIds: "+ taggedTopicIds.toString());
		return topicService.getTaggedTopics( sqlSession, taggedTopicIds.toArray(new Long[0]), paginate);
	}

	 List getTaggedTopicIds(SqlSession sqlSession, long termId) throws Exception {
		 long[] terms = new long[]{ termId};
		 String[] taxonomies = new String[]{"bb_topic_tag"};
		// long terms =  termId;
		 //String taxonomies = "bb_topic_tag";
		 String order = "DESC";
		 String field = "tt_id";
		 long userId = 0;
		 List topicIds = termTaxonomyService.getObjectsInTerm( sqlSession, terms, taxonomies, order, field, userId);
		return topicIds;
	}

	<T> Term getTag(SqlSession sqlSession ,  T id, long userId, long topicId ) throws Exception{
		Term term = null;
		long ttId = 0;
		if( id.getClass()  ==  Long.class){
			ttId = (Long) id;
		} else {
			//term = termTaxonomyService.getTermBy( sqlSession, "slug", (String)id, "bb_topic_tag");
			term = termTaxonomyService.getTermBy( sqlSession, "bb_topic_tag", "name", (String)id);
			if( null == term) return null;
			ttId = term.getTt().getTermTaxonomyId();
		}
		/*
		if( userId > 0 && topicId > 0){
			String fields = "tt_ids";
			List ttIds = termTaxonomyService.getObjectTerms( topicId, "bb_topic_tag", userId, fields);
			if( !ttIds.contains( ttId)) return null;
		} */
		if( null == term)
			term = termTaxonomyService.getTermBy(sqlSession, "bb_topic_tag", "tt_id", ttId);
			
		return term;

	}
	// Setter/Getterr

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List getTopics() {
		return topics;
	}

	public void setTopics(List topics) {
		this.topics = topics;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
	
	
}
