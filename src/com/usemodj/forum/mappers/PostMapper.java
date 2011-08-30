package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.forum.domain.Post;

public interface PostMapper {

	List<Post> selectPosts( @Param("topicId")long topicId, @Param("view")String view, RowBounds rowBounds) throws Exception;

	int selectTopicVoices(@Param("topicId")long topicId) throws Exception;

}
