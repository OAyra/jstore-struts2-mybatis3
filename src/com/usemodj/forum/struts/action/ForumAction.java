package com.usemodj.forum.struts.action;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;
import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.domain.Topic;
import com.usemodj.forum.service.ForumService;
import com.usemodj.forum.service.TopicService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Paginate;
import com.usemodj.struts.action.BaseAction;

public class ForumAction extends BaseAction {
	private static Logger logger = Logger.getLogger( ForumAction.class);
	protected ForumService forumService = new ForumService();
	protected TopicService topicService = new TopicService();
	private Paginate paginate = new Paginate();
	private Forum forum = null;
	private List<Topic> stickyTopics = null;
	
	public String execute() throws  Exception {
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();
			paginate.setLocation(Location.FRONT);
			topicService.getLatestTopics(sqlSession, paginate);
			logger.debug("----- ForumAction SUCCESS  count: "+ paginate.getCount());
			logger.debug("----- Paginate page: "+ paginate.getPage() + " , perPage: "+ paginate.getPerPage());
			//stickyTopics = (List<Topic>) paginate.getResults();
			stickyTopics = topicService.getStickyTopics(sqlSession, -1);
		} catch (Exception e) {
			// 	e.printStackTrace();
			logger.error( "-- ForumAction.execute() Exception: "+  e.getMessage());
		} finally {
			sqlSession.close();
		}
		
		return Action.SUCCESS;
	}
	
	public String list() throws Exception {
		//int  forumId = forum.getForumId();
		SqlSession sqlSession = null;
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();
			forumService.getForums(sqlSession, paginate);
			logger.debug(" ------ Paginate count: " + paginate.getCount() + ", page: "+ paginate.getPage());
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error( "------- ForumAction.list() Excteption: " + e.getMessage());
		} finally {
			sqlSession.close();
		}
		
		return Constants.LIST;
	}

	/* util */
	
	/* Getter/Setter methods */
	public  Paginate getPaginate() {
		return paginate;
	}

	public void setPaginate(Paginate page) {
		this.paginate = page;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public List<Topic> getStickyTopics() {
		return stickyTopics;
	}

	public void setStickyTopics(List<Topic> topics) {
		this.stickyTopics = topics;
	}
	
	
}
