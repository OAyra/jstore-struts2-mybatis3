package com.usemodj.forum.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Forum;
import com.usemodj.forum.mappers.ForumMapper;
import com.usemodj.struts.Paginate;

public class ForumService {
	private Logger logger = Logger.getLogger( ForumService.class);
	
	private ForumMapper forumMapper;
	
	public Paginate  getForums( SqlSession sqlSession, Paginate page) throws Exception {
		List<Forum> forums =null;
		long count = 0;
		try {
			forumMapper =sqlSession.getMapper(  ForumMapper.class);
			//forums = forumMapper.selectForums(new RowBounds( (int)page.getOffset(), page.getPerPage()));
			forums = forumMapper.selectForums(page.getOffset(), page.getPerPage());
			count = forumMapper.selectFoundRows();
			logger.debug(" ---- FoundRows(): " + count);
			page.setCount(count);
			page.setResults( forums);
			
		} catch (Exception e) {
			//e.printStackTrace();
			logger.equals(" ForumService.selectForumList() Exception: " + e.getMessage());
			throw e;
		}
		
		return page;
	}
}