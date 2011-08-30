package com.usemodj.forum.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.mybatis.builder.ForumMybatisConfig;
import com.usemodj.struts.Paginate;

public class TopicServiceTest {
		private static Logger logger = Logger.getLogger( TopicServiceTest.class);
	  private static  SqlSessionFactory sqlSessionFactory;
	  private static TopicService topicService;
	  
	  @BeforeClass
	  public static void setup() throws Exception {
	    sqlSessionFactory = new ForumMybatisConfig().getSqlSessionFactory();
	    topicService = new TopicService();
	  }

	@Test
	public void testGetLatestTopics() {
		//fail("Not yet implemented");
		SqlSession sqlSession = null;
		Paginate paginate = new Paginate();
		int page = 1;
		sqlSession = sqlSessionFactory.openSession();
		paginate.setLocation(Location.FRONT);
		if( 0< page)  paginate.setPage( page);
		try {
			topicService.getLatestTopics(sqlSession, paginate);
			List<Topic> latestTopics = (List<Topic>) paginate.getResults();
			logger.debug("----- ForumAction SUCCESS  count: "+ paginate.getCount());
			logger.debug("----- Paginate page: "+ paginate.getPage() + " , perPage: "+ paginate.getPerPage());
			for( Topic t: latestTopics) {
				logger.debug(" == forumId: " + t.getForumId() + ", topicId: " + t.getTopicId() + ", topicTitle: "+ t.getTopicTitle()
						+ ", open: "+ t.getTopicOpen()+ ", status:"+ t.getTopicStatus()+", sticky: "+ t.getTopicSticky() );
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testGetStickyTopics() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			List<Topic> stickyTopics = topicService.getStickyTopics(sqlSession, -1);
			logger.debug(" -- sticky topics size: " + stickyTopics.size());
			for( Topic t: stickyTopics) {
				logger.debug(" == forumId: " + t.getForumId() + ", topicId: " + t.getTopicId() + ", topicTitle: "+ t.getTopicTitle()
						+ ", open: "+ t.getTopicOpen()+ ", status:"+ t.getTopicStatus()+", sticky: "+ t.getTopicSticky() );
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
