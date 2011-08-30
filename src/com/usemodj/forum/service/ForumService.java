package com.usemodj.forum.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.domain.Meta;
import com.usemodj.forum.mappers.ForumMapper;
import com.usemodj.forum.mappers.MetaMapper;
import com.usemodj.struts.Paginate;

public class ForumService {
	private Logger logger = Logger.getLogger( ForumService.class);
	
	private ForumMapper forumMapper;
	private MetaMapper metaMapper;
	
	public Paginate  getForums( SqlSession sqlSession, Paginate page) throws Exception {
		List<Forum> forums =null;
		long count = 0;
		try {
			forumMapper =sqlSession.getMapper(  ForumMapper.class);
			//forums = forumMapper.selectForums(new RowBounds( (int)page.getOffset(), page.getPerPage()));
			forums = forumMapper.selectForumsWithLimit(page.getOffset(), page.getPerPage());
			count = forumMapper.selectFoundRows();
			logger.debug(" ---- FoundRows(): " + count);
			page.setCount(count);
			page.setResults( forums);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(" ForumService.selectForums() Exception: " + e.getMessage());
			throw e;
		}
		
		return page;
	}

	public List<Forum> getForumsWithMeta( SqlSession sqlSession) throws Exception {
		Map forumMap = getForumsWithMetaMap( sqlSession);
		return (List<Forum>) new ArrayList<Forum>(forumMap.values());
	}
	
	public Map<Long, Forum> getForumsWithMetaMap(SqlSession sqlSession) throws Exception {
		//Map<Long, Forum> forumMap = new HashMap<Long, Forum>();  
		Map<Long, Forum> forumMap = new LinkedHashMap<Long, Forum>();
		try {
			forumMapper =sqlSession.getMapper(  ForumMapper.class);
			List<Forum> forums = forumMapper.selectForums( new RowBounds());
			if( null != forums && forums.size()>0) {
				for( Forum f: forums) {
					forumMap.put( Long.valueOf( f.getForumId()), f);
				}
				
				Long[] objectIds = forumMap.keySet().toArray( new Long[0]);
				logger.debug("--- forumMap.keySet() objectIds length: "+ objectIds.length);
				String objectType = "bb_forum";
				metaMapper = sqlSession.getMapper( MetaMapper.class);
				List<Meta> metas = metaMapper.selectMetaIN( ArrayUtils.toPrimitive(objectIds), objectType);
				for(Meta m : metas){
					long l = m.getObjectId();
					Forum f = forumMap.get( l);
					if( null != f) f.setMeta(m);
				}
				
			}
		} catch (Exception e) {
			logger.error(" ForumService.selectForums() Exception: " + e.getMessage());
			throw e;
		}
		
		return forumMap;
	}
	
//	public class EntryValueComparator implements Comparator{  
//		   public int compare(Object o1, Object o2) {  
//		      return compare((Map.Entry)o1, (Map.Entry)o2);  
//		   }  
//		   public int compare(Map.Entry e1, Map.Entry e2) {  
//		      int cf = ((Comparable)e1.getValue()).compareTo(e2.getValue());  
//		      if (cf == 0) {  
//		         cf = ((Comparable)e1.getKey()).compareTo(e2.getKey());  
//		      }  
//		      return cf;  
//		   }  
//		} 
	
	public Map getForumsHierarchical( SqlSession sqlSession) throws Exception {
		Map forumsMap = getForumsWithMetaMap(sqlSession);
		
		return forumsMap;
	}

	public String getForumBreadCrumb(SqlSession sqlSession, String contextPath, int forumId,
			String separator, String cssClass, boolean isForum) throws Exception {
		
		StringBuffer trail = new StringBuffer();
		forumMapper =sqlSession.getMapper(  ForumMapper.class);
		Forum trailForum = forumMapper.selectForum( forumId);
		appendMeta(sqlSession, trailForum);
		
		int  currentTrailForumId = trailForum.getForumId();
		while( null != trailForum && trailForum.getForumId() > 0){
			String crumb = separator;
			if( currentTrailForumId != trailForum.getForumId() || !isForum) {
				crumb +="<a " + (null == cssClass? "": cssClass	) + " href='" + getForumLink(trailForum.getForumId(), contextPath) + "'>";
			} else if( null != cssClass) {
				crumb += "<span "+ cssClass + ">";
			}
			crumb += trailForum.getForumName();
			if( currentTrailForumId != trailForum.getForumId() ||! isForum){
				crumb += "</a>";
			} else if( null != cssClass){
				crumb += "</span>";
			}
			trail.insert(0, crumb); // trail = crumb + trail;
			if( trailForum.getForumParent() == 0)  break;
			 trailForum = forumMapper.selectForum( trailForum.getForumParent());
			appendMeta(sqlSession, trailForum); 
				
		}//end of while loop
		
		return trail.toString();
	}

	private String getForumLink(int forumId, String contextPath) {
		String uri = contextPath+"/forum/forum.action?forum.forumId="+forumId;
		return uri;
	}

	 void appendMeta(SqlSession sqlSession, Forum forum) throws Exception {
		metaMapper =sqlSession.getMapper( MetaMapper.class);
		List<Meta> metas = metaMapper.selectMetas( forum.getForumId(), "bb_forum");
		for( Meta m : metas) {
			forum.setMetaValue(m.getMetaKey(), m.getMetaValue());
		}
	}

	public Forum getForum(SqlSession sqlSession, int forumId) throws Exception {
		forumMapper = sqlSession.getMapper( ForumMapper.class);
		Forum forum = forumMapper.selectForum(forumId);
		appendMeta( sqlSession, forum);
		return forum;
	}
}