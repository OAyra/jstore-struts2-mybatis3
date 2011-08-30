package com.usemodj.forum.service;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.mybatis.builder.ForumMybatisConfig;

public class ForumServiceTest {

	  private static  SqlSessionFactory sqlSessionFactory;
	  private static ForumService forumService;
	  
	  @BeforeClass
	  public static void setup() throws Exception {
	    sqlSessionFactory = new ForumMybatisConfig().getSqlSessionFactory();
	    forumService = new ForumService();
	  }

	@Test
	public void testGetForums() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetForumsWithMeta() {
		//fail("Not yet implemented");
		SqlSession sqlSession = null;
	    try {
			sqlSession = sqlSessionFactory.openSession();
			List<Forum> forums = forumService.getForumsWithMeta( sqlSession);
			System.out.println(" --- forums size: "+ forums.size());
			for( Forum f : forums) {
				System.out.println("-- forumId: "+ f.getForumId()+ ", forumName: "+ f.getForumName() + ", forumOrder: "+ f.getForumOrder());
				if( null != f.getMeta()) System.out.println("   meta key: " + f.getMeta().getMetaKey() + ", meta value: "+ f.getMeta().getMetaValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	    
	}
	
	
	@Test
	public void testGetForumBreadCrumb() {
		SqlSession sqlSession = null;
	    try {
			sqlSession = sqlSessionFactory.openSession();
			String contextPath = "localhost:8080/jstore-struts2-mybatis3";
			int forumId = 3;
			String separator = "&raquo;";
			String cssClass = "";
			boolean isForum = true;
			String breadCrumb = forumService.getForumBreadCrumb(sqlSession, contextPath, forumId, separator, cssClass, isForum);
			System.out.println(" --- bread crumb: "+ breadCrumb);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

	}
}
