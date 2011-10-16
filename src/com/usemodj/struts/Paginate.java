package com.usemodj.struts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Topic;

public class Paginate implements Serializable {
	private static final long serialVersionUID = -8074438944268577607L;
	private static Logger logger = Logger.getLogger(Paginate.class);
	
	public enum LinkType {
		ARRAY, LIST, FLAT, PLAIN;
		
		public static LinkType toLinkType(String str) {
	        try {
	            return valueOf(str.toUpperCase());
	        }
	        catch (Exception ex) {
	            return PLAIN;
	        }
	    }   
	}
	

	public static int PER_PAGE = 5; // 30;
	
	private List<?> results;
	private long count =0;
	private int page = 1; //current page number
	private int perPage = 30; //size of per page
	private Location location;
	private long  objectId;
	
	public Paginate() {
//		this.page = 1;
		this.perPage = Paginate.PER_PAGE;
//		this.count = 0;
//		this.results = Collections.emptyList();
	}

	public Paginate( List<?> results, long count) {
		//this();
		this.results = results;
		this.count = count;
	}
	public int getPageNumber( ) {
		return getPageNumber( this.count, this.perPage);
	}
	public static int  getPageNumber( long rows, int perPage) {
		return (int) Math.ceil((double)rows/ perPage);
	}
	public static int  getPageNumber( long rows) {
		return getPageNumber(rows, Paginate.PER_PAGE);
	}
	
	public static  int getTopicPagesAdd( Topic topic, String view, boolean browseDeleted) {
		int  add = 0;
		String deletedPosts =  topic.getMetaValue("deleted_posts");
		logger.debug("-- topic.getMetaValue()  deleted_posts: "+ deletedPosts);
		if( "all".equals( view) && browseDeleted && null != deletedPosts){
			try {
				add = Integer.parseInt( deletedPosts);
			} catch (NumberFormatException e) {
				add = 0;
			}
		} else{
			add = 0;
		}
		logger.debug("-- getTopicPagesAdd() deleted_posts:" + add);
		return add;
	}
	

	/**
	 * 
	 *	public String getTopicPageLinks(Topic topic, boolean showAll, int endSize, String before, String after, String contextPath){
				String uri = contextPath + "/forum/topic?topic.topicId="+ topic.getTopicId();
				uri += "&%_%"; // page=%#%"
				//TODO: view, browseDeleted
				String view = "all";
				boolean showFirst = false;
				boolean browseDeleted = true;
				long posts = topic.getTopicPosts() +  Paginate.getTopicPagesAdd( topic, view, browseDeleted);
				String prevText = getText("&laquo; Previous");
				String nextText = getText("Next &raquo;");
				String prevTitle = getText("Previous page");
				String nextTitle = getText( "Next page");
				String nTitle = getText("Page %d");
				LinkType type = LinkType.ARRAY;
				int perPage = Paginate.PER_PAGE;
				String format = "page=%#%";
				int midSize = 2;
				int current =0;
				String addFragment = "";
				boolean dots = false;
				boolean preNext = true;
				List pageLinks = Paginate.getPaginateLinks(uri, current, topic.getTopicPosts(), midSize, endSize, perPage, format, type
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
	 *
	 * 
	 * @param uri
	 * @param page
	 * @param total
	 * @param midSize
	 * @param endSize
	 * @param perPage
	 * @param format
	 * @param type					: LinkType - ARRAY, LIST, FLAT, PLAIN
	 * @param addFragment	: String
	 * @param dots				: boolean
	 * @param showAll		: boolean 
	 * @param prevNext	: boolean
	 * @param prevText
	 * @param nextText
	 * @param prevTitle
	 * @param nextTitle
	 * @param nTitle
	 * @return
	 */
	public static <E> E  getPaginateLinks( String uri,  int page , long total, int midSize,  int endSize, int perPage, String format, LinkType type
			, String addFragment, boolean dots, boolean showAll, boolean prevNext, String prevText, String nextText, String prevTitle, String nextTitle, String nTitle){
		//int midSize =2; // How many numbers to either side of current not including current
		//int endSize = 1; // How many numbers on either end including the end
		//boolean prevNext = false;
		//boolean showAll = false;
		//String addFragment = "";
		//boolean dots = false;
		perPage = (1 > perPage)?  Paginate.PER_PAGE: perPage;
		
		int current = ( 1> page)? 1: page;
		int totalPages = (int)Math.ceil((double)total/perPage);
		if( totalPages < 2) return (E) "";
		//logger.debug("--- link uri:" + uri );
		
		List<String> pageLinks = new ArrayList<String>();
		if( prevNext && current  > 1){
			String link = uri.replace("%_%", ( 2== current? "":format));
			link = link.replace("%#%", String.valueOf(current -1));
			link = link.replace("?&", "?").replaceFirst("&+$", "").replaceAll("&{2,}", "&");
			link += addFragment;
			pageLinks.add( "<a class='prev page-numbers' href='"+ link + "' title='"+ prevTitle + "'>"
					+ prevText + "</a>");
		}
		
		for( int n=1; n <= totalPages; n++	){
			if( n == current) {
				//TODO: $n_display = bb_number_format_i18n( $n );
				String nDisplay = String.valueOf(n);
				//$n_display_title =  esc_attr( sprintf( $n_title, $n ) );
				String nDisplayTitle = String.format(nTitle, n);
				pageLinks.add( "<span class='page-numbers current'  title='"+ nDisplayTitle + "'> "+ nDisplay+ "</span>");
				dots = true;
			} else {
				if( showAll || (n <= endSize || ( n >= current - midSize && n <= current + midSize) || n > totalPages - endSize)) {
					//$n_display = bb_number_format_i18n( $n );
					//$n_display_title =  esc_attr( sprintf( $n_title, $n ) );
					String nDisplay = String.valueOf(n);
					String nDisplayTitle = String.format(nTitle, n);
					String link = uri.replace("%_%", ( 1 == n ? "": format));
					link = link.replace("%#%", String.valueOf(n));
					link = link.replace("?&", "?").replaceFirst("&+$", "").replaceAll("&{2,}", "&");
					link += addFragment; 
					pageLinks.add("<a class='page-numbers' href='"+ link + "' title='"+ nDisplayTitle + "' >"+ nDisplay + "</a>");
					dots = true;
				} else if( dots && !showAll){
					pageLinks.add("<span class='page-numbers dots'>&hellip;</span>");
					dots = false;
				}
			}
		}
		
		if( prevNext && ( current < totalPages || -1 == totalPages)) {
			String link = uri.replace("%_%", format);
			link = link.replace("%#%", String.valueOf(current + 1));
			link += addFragment;
			pageLinks.add("<a class='next page-numbers' href='"+ link + "' title='"+ nextTitle + "' >"+ nextText + "</a>");
		}
		String ret;
		switch( type) {
			case ARRAY:
						return (E) pageLinks;
			case LIST:
					ret = "<ul class='page-numbers'>\n\t<li> ";
					ret += StringUtils.join(pageLinks, "</li>\n\t<li>");
					ret += "</li>\n</ul>\n";
					break;
			default:
				ret = StringUtils.join(pageLinks, "\n");
				break;
		}
		return (E) ret;			
	}

	public static String getTopicPages( Topic topic, String contextPath, String queryString,  int page, int midSize , int endSize, int perPage, String addFragment, boolean dots, boolean showAll,  String view, String before, String after, boolean browseDeleted, boolean showFirst
			,boolean preNext,  String prevText, String nextText
			, String prevTitle,String nextTitle, String nTitle) {
		String uri =contextPath+"/forum/topic";
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
		
		//boolean browseDeleted = true;
		//boolean showFirst = false;
		long total = topic.getTopicPosts() +  Paginate.getTopicPagesAdd( topic, view, browseDeleted);
//		String prevText = getText("&laquo; Previous");
//		String nextText = getText("Next &raquo;");
//		String prevTitle = getText("Previous page");
//		String nextTitle = getText( "Next page");
//		String nTitle = getText("Page %d");
		if( perPage < 1)  perPage  = Paginate.PER_PAGE;
//		int midSize = 2; 
//		int endSize = 1;
		LinkType type = LinkType.FLAT;
//		String addFragment ="";
//		boolean dots = false;
//		boolean showAll = false;
//		boolean preNext = true;
		return before + Paginate.getPaginateLinks(uri, page,  total, midSize, endSize, perPage, format, type, 
				addFragment, dots, showAll, preNext, prevText, nextText, prevTitle, nextTitle, nTitle) + after;
	}

	// getter/setter methods
	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getOffset() {
		if( getPage()<1) setPage(1);
		return (getPage()-1) * getPerPage();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public long getObjectId() {
		return objectId;
	}
	
	public void setObjectId( long objectId) {
		this.objectId = objectId;
	}
}
