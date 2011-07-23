package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.forum.Location;
import com.usemodj.forum.domain.Topic;

public interface TopicMapper {
	 List<Topic> selectTopics( RowBounds rowBounds) throws Exception;
	 List<Topic> selectLatestTopics( @Param("forumId")int  forumId,@Param("location") short location, RowBounds rowBounds) throws Exception;
	 List<Topic> selectLatestTopics( @Param("offset")long offset, @Param("limit")int limit) throws Exception;
	 long selectFoundRows() throws Exception;
	List<Topic> selectStickyTopics(@Param("forumId")int forumId) throws Exception;
}