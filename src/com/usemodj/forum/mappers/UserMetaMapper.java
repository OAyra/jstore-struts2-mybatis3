package com.usemodj.forum.mappers;

import org.apache.ibatis.annotations.Param;
import com.usemodj.forum.domain.UserMeta;

public interface UserMetaMapper {

	UserMeta selectUserMeta(@Param("userId") long userId, @Param("metaKey")String metaKey) throws Exception;

	void insertUserMeta(@Param("userId")long userId, @Param("metaKey")String metaKey, @Param("metaValue")String metaValue) throws Exception;

	void updateUserMeta(@Param("umetaId")long umetaId, @Param("metaKey")String metaKey,  @Param("metaValue")String metaValue) throws Exception;

}
