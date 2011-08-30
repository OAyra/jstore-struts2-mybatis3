package com.usemodj.forum.struts.action;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usemodj.forum.domain.Term;
import com.usemodj.forum.mybatis.builder.ForumMybatisConfig;
import com.usemodj.forum.service.TermTaxonomyService;
import com.usemodj.forum.service.TopicServiceTest;

public class TagActionTest {
	private static Logger logger = Logger.getLogger( TopicServiceTest.class);
	  private static  SqlSessionFactory sqlSessionFactory;
	  private static TermTaxonomyService termTaxonomyService;
	  @BeforeClass
	  public static void setup() throws Exception {
	    sqlSessionFactory = new ForumMybatisConfig().getSqlSessionFactory();
	    termTaxonomyService = new TermTaxonomyService();
	  }

	@Test
	public void testGetTag() {
		//fail("Not yet implemented");
		//long id = 123;
		String id = "123";
		long userId = 123;
		long topicId = 123;
		getTag(id, userId, topicId);
	}

	<T> Term getTag( T id, long userId, long topicId	){
		if( id.getClass()  ==  Long.class){
		 System.out.println("-- long class:"+ id.getClass());
		} else {
			 System.out.println("--not long class:"+ id.getClass());
		}
		return null;
	}
	
	@Test
	public void testGetTaggedTopicIds(){
		SqlSession sqlSession = sqlSessionFactory.openSession();
		 String taxonomies = "bb_topic_tag";
		 String order = "DESC";
		 String field = "tt_id";
		 long userId = 0;
		 long terms = 1;
		List topicIds;
		try {
			topicIds = termTaxonomyService.getObjectsInTerm( sqlSession, terms, taxonomies, order, field, userId);
			 logger.debug("-- topicIds: "+ topicIds.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
