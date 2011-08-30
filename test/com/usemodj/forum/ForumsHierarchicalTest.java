package com.usemodj.forum;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.mybatis.builder.ForumMybatisConfig;
import com.usemodj.forum.service.ForumService;

public class ForumsHierarchicalTest {
	  private static  SqlSessionFactory sqlSessionFactory;
	  private static ForumService forumService;
	  
	  @BeforeClass
	  public static void setup() throws Exception {
	    sqlSessionFactory = new ForumMybatisConfig().getSqlSessionFactory();
	    forumService = new ForumService();
	  }

	@Test
	public void testGetForumsHierarchical() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetForumsMap() {
		SqlSession sqlSession = null;
	    try {
			sqlSession = sqlSessionFactory.openSession();
			Map<Long, Forum> forums = forumService.getForumsWithMetaMap(sqlSession);
			System.out.println(" --- forums size: "+ forums.size());
			for( long k : forums.keySet()) {
				Forum f = forums.get( k);
				System.out.println("-- [ " + k + "], forumId: "+ f.getForumId()+", parent: "+ f.getForumParent()+ ", forumName: "+ f.getForumName() + ", forumOrder: "+ f.getForumOrder());
				if( null != f.getMeta()) System.out.println("   meta key: " + f.getMeta().getMetaKey() + ", meta value: "+ f.getMeta().getMetaValue());
			}
			
			ForumsHierarchical fh = new ForumsHierarchical();
			
			int childOf =0;
			int hierarchical = 1;
			int depth = 0;
			int cutBranch = 0;
			Map<Long, Forum> forumsMap = fh.getForumsMap( forums, childOf, hierarchical, depth, cutBranch);
			for(long k : forumsMap.keySet()) {
				Forum f = (Forum)forumsMap.get( k);
				if( null != f) {
					System.out.println("-- [ " + k + "], forumId: "+ f.getForumId()+", forumParent: "+ f.getForumParent()+ ", forumName: "+ f.getForumName() + ", forumOrder: "+ f.getForumOrder());
					if( null != f.getMeta()) System.out.println("   meta key: " + f.getMeta().getMetaKey() + ", meta value: "+ f.getMeta().getMetaValue());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}

	@Test
	public void testGetFlattenMap() {
		fail("Not yet implemented");
	}

	@Test
	public void forumsLoopStep() {
		SqlSession sqlSession = null;
	    try {
			sqlSession = sqlSessionFactory.openSession();
			Map<Long, Forum> forums = forumService.getForumsWithMetaMap(sqlSession);
			System.out.println(" --- forums size: "+ forums.size());
			for( long k : forums.keySet()) {
				Forum f = forums.get( k);
				System.out.println("-- [ " + k + "], forumId: "+ f.getForumId()+", parent: "+ f.getForumParent()+ ", forumName: "+ f.getForumName() + ", forumOrder: "+ f.getForumOrder());
				if( null != f.getMeta()) System.out.println("   meta key: " + f.getMeta().getMetaKey() + ", meta value: "+ f.getMeta().getMetaValue());
			}
			
			ForumsHierarchical fh = new ForumsHierarchical();
			
			//int childOf =0;
			int childOf =4;  //forum_id
			int hierarchical = 1;
			int depth = 0;
			int cutBranch = 0;
			Map<Long, Forum> forumsMap = fh.getForumsMap( forums, childOf, hierarchical, depth, cutBranch);
			if( null != forumsMap)
				for(long k : forumsMap.keySet()) {
					Forum f = (Forum)forumsMap.get( k);
					//if( null != f) {
						System.out.println("== [ " + k + "], forumId: "+ f.getForumId()+ ", forumName: "+ f.getForumName() + ", forumOrder: "+ f.getForumOrder());
						if( null != f.getMeta()) System.out.println("   meta key: " + f.getMeta().getMetaKey() + ", meta value: "+ f.getMeta().getMetaValue());
					//}forumsLoop
				}
			
			if( null != fh.getForumsLoopElements(forumsMap)) {
				while( fh.forumsLoopStep()) {
					//System.out.println(" -- forumId: "+ fh.forum.getForumId() + ", forumName: "+ fh.forum.getForumName());
					if( fh.isForumIsCategory()) {
					//if( null != fh.forum.getMeta() && "forum_is_category".equals( fh.forum.getMeta().getMetaKey()) && "1".equals(fh.forum.getMeta().getMetaValue() ) ) {
						System.out.println( fh.getAltClass( "forum", fh.getForumClass("bb-category")));
						System.out.println( fh.forumsLoop.pad("<div class='nest'>" , 0) + fh.forum.getForumName() + fh.forumsLoop.pad("</div>", 0));
						continue;
					}
					System.out.println( fh.getAltClass( "forum",  fh.getForumClass(null)));
					System.out.println( fh.forumsLoop.pad("<div class='nest'>" , 0) + fh.forum.getForumName() + fh.forumsLoop.pad("</div>", 0));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}

	}
}
