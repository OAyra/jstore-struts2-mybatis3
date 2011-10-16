package com.usemodj.forum.service;

import java.lang.reflect.Type;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usemodj.forum.domain.User;
import com.usemodj.forum.domain.UserMeta;
import com.usemodj.forum.mappers.UserMapper;
import com.usemodj.forum.mappers.UserMetaMapper;

public class UserMetaService {
	private static Logger logger = Logger.getLogger(UserMetaService.class );
	UserMetaMapper userMetaMapper = null;
	UserMapper  userMapper = null;
	
	public void updateUserMeta(SqlSession sqlSession, long userId,
			String metaKey, Map<String, Object> metaValue) throws Exception {
		metaKey = metaKey.replaceAll("(?)[^a-z0-9_]", "");
		
		Gson gson = new Gson();
		Type listType = new TypeToken<Map<String, Object>>() {}.getType();
		String json = gson.toJson(metaValue, listType);
		// Deserialize : gson.fromJson(json, listType);
		logger.debug("--- json: "+ json);
		userMetaMapper = sqlSession.getMapper( UserMetaMapper.class);
		UserMeta userMeta = userMetaMapper.selectUserMeta(userId, metaKey);
		if( null == userMeta)
			userMetaMapper.insertUserMeta( userId, metaKey, json);
		else
			userMetaMapper.updateUserMeta(userMeta.getUmetaId(), metaKey, json);
	}

	public void updateUserMeta(SqlSession sqlSession, long userId,
			String metaKey, String metaValue) throws Exception {
		metaKey = metaKey.replaceAll("(?)[^a-z0-9_]", "");
		userMetaMapper = sqlSession.getMapper( UserMetaMapper.class);
		UserMeta userMeta = userMetaMapper.selectUserMeta(userId, metaKey);
		if( null == userMeta)
			userMetaMapper.insertUserMeta( userId, metaKey, metaValue);
		else
			userMetaMapper.updateUserMeta(userMeta.getUmetaId(), metaKey, metaValue);
		
	}
	
}
