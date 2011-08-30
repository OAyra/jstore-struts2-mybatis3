package com.usemodj.forum.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Meta;
import com.usemodj.forum.domain.Post;
import com.usemodj.forum.mappers.MetaMapper;
import com.usemodj.forum.mappers.PostMapper;
import com.usemodj.struts.Paginate;

public class PostService {
	private static Logger logger = Logger.getLogger( PostService.class);
	PostMapper postMapper = null;
	MetaMapper metaMapper = null;
	
	public List<Post> getPosts(SqlSession sqlSession, String view, Paginate p) throws Exception {
		postMapper = sqlSession.getMapper( PostMapper.class);
		List<Post> posts = postMapper.selectPosts( p.getObjectId(), view, new RowBounds( (int) p.getOffset(), p.getPerPage()));
		if( null != posts && posts.size()>0) appendPostMeta( sqlSession,  posts, "bb_post");
		p.setResults(posts);
		return posts;
	}

	private void appendPostMeta(SqlSession sqlSession, List<Post> posts, String objectType) throws Exception {
		Map<Long, Post>  postMap = new LinkedHashMap<Long, Post>();
		for( Post p : posts) {
			postMap.put(p.getPostId(), p);
		}
		Set keys = postMap.keySet();
		if( keys.size()> 0){
			metaMapper = sqlSession.getMapper( MetaMapper.class);
			List<Meta> metas = metaMapper.selectMetaLongArr( (Long[])keys.toArray( new Long[0]), objectType);
			//List<Meta> metas = metaMapper.selectMetaIN( ArrayUtils.toPrimitive( (Long[])keys.toArray( new Long[0])), objectType);
			
			for( Meta meta: metas){
				long postId = meta.getObjectId();
				Post p = postMap.get( postId);
				p.setMetaValue(meta.getMetaKey(), meta.getMetaValue());
			}
		}
	}

	public int getTopicVoices(SqlSession sqlSession, long topicId) throws Exception {
		postMapper = sqlSession.getMapper( PostMapper.class);
		int voices = postMapper.selectTopicVoices( topicId);
		return voices;
	}
	
}
