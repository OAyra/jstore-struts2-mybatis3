package com.usemodj.forum.struts.action;

import static org.junit.Assert.*;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usemodj.forum.Location;
import com.usemodj.forum.mybatis.builder.ForumMybatisConfig;
import com.usemodj.forum.service.TopicService;
import com.usemodj.forum.service.TopicServiceTest;

public class TopicActionTest {
	private static Logger logger = Logger.getLogger( TopicServiceTest.class);
	  private static  SqlSessionFactory sqlSessionFactory;
	  private static TopicService topicService;
	  
	  @BeforeClass
	  public static void setup() throws Exception {
	    sqlSessionFactory = new ForumMybatisConfig().getSqlSessionFactory();
	    topicService = new TopicService();
	  }

	@Test
	public void testGetTopicPagesAddTopicStringBoolean() {
		fail("Not yet implemented");
	}
	@Test
	public void testGetLocation(){
		Location location = Location.TOPIC;
		if( Location.TOPIC.getNum() == location.getNum())
			logger.debug("-- Location: " + location);
		else
			logger.debug("-- location != "+ location);
		
	}
}
