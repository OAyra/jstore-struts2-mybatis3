package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.forum.domain.Forum;

public interface ForumMapper {
	List<Forum> selectForums( RowBounds rowBounds) throws Exception;
	List<Forum> selectForumsWithLimit( @Param("offset") long offset, @Param("limit") int limit) throws Exception;
	
	long selectFoundRows() throws Exception;
	
	void deleteForumIN(@Param("forumIds")int[] forumIds) throws Exception;
	Forum selectForum(@Param("forumId")int forumId) throws Exception;

}
