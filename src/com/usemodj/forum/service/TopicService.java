package com.usemodj.forum.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Meta;
import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.mappers.MetaMapper;
import com.usemodj.forum.mappers.TopicMapper;
import com.usemodj.struts.Paginate;

public class TopicService {
	private Logger logger = Logger.getLogger( TopicService.class);
	
	private TopicMapper topicMapper = null;
	private MetaMapper metaMapper = null;
	
	public Paginate getLatestTopics(SqlSession sqlSession, Paginate page) throws Exception {
		topicMapper = sqlSession.getMapper( TopicMapper.class);
		List<Topic> topics = topicMapper.selectLatestTopics((int) page.getObjectId(), page.getLocation().getNum(), new RowBounds( (int) page.getOffset(), page.getPerPage()));
		long count = topicMapper.selectFoundRows();
		appendMeta(sqlSession, topics, "bb_topic");
		page.setResults((List<Topic>) topics);
		page.setCount(count);
		
		return page;
	}

	public List<Topic> getStickyTopics(SqlSession sqlSession, int forumId)  throws Exception {
		topicMapper = sqlSession.getMapper( TopicMapper.class);
		List<Topic> topics = topicMapper.selectStickyTopics( forumId);
		appendMeta(sqlSession, topics, "bb_topic");
		return topics;
	}

	public Topic getTopic(SqlSession sqlSession, long topicId) throws Exception {
		topicMapper = sqlSession.getMapper( TopicMapper.class);
		Topic topic = topicMapper.selectTopic( topicId);
		appendMeta(sqlSession, topic, "bb_topic");
		return topic;
	}
	
	 void appendMeta(SqlSession sqlSession, Topic topic, String objectType) throws Exception {
		metaMapper =sqlSession.getMapper( MetaMapper.class);
		List<Meta> metas = metaMapper.selectMetas( topic.getTopicId(), objectType);
		for( Meta m : metas) {
			topic.setMetaValue(m.getMetaKey(), m.getMetaValue());
		}
	}

	public List getTaggedTopics(SqlSession sqlSession, Long[] taggedTopicIds,
			Paginate paginate) throws Exception {
		topicMapper = sqlSession.getMapper( TopicMapper.class);
		List topics = topicMapper.selectTaggedTopics( taggedTopicIds, paginate.getOffset(), paginate.getPerPage());
		long count = topicMapper.selectFoundRows();
		logger.debug(" ----TopicMapper-- FoundRows(): " + count);
		paginate.setCount(count);
		appendMeta( sqlSession, topics, "bb_topic");
		paginate.setResults( topics);

		return topics;
	}

	 void appendMeta(SqlSession sqlSession, List<Topic> topics, String objectType) throws Exception {
		 if( null == topics || topics.isEmpty()) return;
		 metaMapper = sqlSession.getMapper( MetaMapper.class);
		Map<Long, Topic> trans = new HashMap<Long, Topic>();
		for( Topic topic: topics) {
			trans.put(topic.getTopicId(), topic);
		}
		//logger.debug("--- trans : " + trans.toString());
		
		Long[] queryIds = trans.keySet().toArray(new Long[0]);
		List<Meta> metas = metaMapper.selectMetaLongArr(queryIds, objectType);
		for( Meta mt : metas){
			trans.get( mt.getObjectId()).setMetaValue(mt.getMetaKey(), mt.getMetaValue());
		}
		
	}

}
