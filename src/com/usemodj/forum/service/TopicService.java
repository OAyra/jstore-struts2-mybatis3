package com.usemodj.forum.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.mappers.TopicMapper;
import com.usemodj.struts.Paginate;

public class TopicService {
	private Logger logger = Logger.getLogger( TopicService.class);
	
	private TopicMapper topicMapper = null;
	
	public Paginate getLatestTopics(SqlSession sqlSession, Paginate page) throws Exception {
		topicMapper = sqlSession.getMapper( TopicMapper.class);
		List<Topic> topics = topicMapper.selectLatestTopics(page.getForumId(), page.getLocation().getNum(), new RowBounds( (int) page.getOffset(), page.getPerPage()));
		long count = topicMapper.selectFoundRows();
		page.setResults((List<Topic>) topics);
		page.setCount(count);
		
		return page;
	}

	public List<Topic> getStickyTopics(SqlSession sqlSession, int forumId)  throws Exception {
		topicMapper = sqlSession.getMapper( TopicMapper.class);
		List<Topic> topics = topicMapper.selectStickyTopics( forumId);
		return topics;
	}
}
