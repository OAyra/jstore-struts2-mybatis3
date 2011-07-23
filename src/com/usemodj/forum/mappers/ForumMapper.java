package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.forum.domain.Forum;

public interface ForumMapper {
	List<Forum> selectForums( RowBounds rowBounds) throws Exception;
	List<Forum> selectForums( @Param("offset") long offset, @Param("limit") int limit) throws Exception;
	
	long selectFoundRows() throws Exception;
	
	void deleteForumIN(long[] forumIds) throws Exception;

}
