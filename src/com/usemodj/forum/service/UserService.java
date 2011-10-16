package com.usemodj.forum.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.usemodj.forum.domain.User;
import com.usemodj.forum.mappers.UserMapper;

public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class );
	UserMapper userMapper = null;
	UserMetaService userMetaService = new UserMetaService();
	
	public User getUser(SqlSession sqlSession, User user) throws Exception {
		 userMapper = sqlSession.getMapper( UserMapper.class);
		 user.setBy("user_login");
		 return  userMapper.selectUser( user);
	}
	
	public List<User> getUserByLoginOrEmail(SqlSession sqlSession, User user) throws Exception {
		 userMapper = sqlSession.getMapper( UserMapper.class);
		 return  userMapper.selectUserByLoginOrEmail( user);
	}
	
	public void updateUserStatus(SqlSession sqlSession, long userId,
			int userStatus) throws Exception {
		userMapper = sqlSession.getMapper( UserMapper.class);
		userMapper.updateUserStatus( userId, userStatus);
		
	}

	public long addUser(SqlSession sqlSession, User user, boolean bbInstalling) throws Exception {
		userMapper = sqlSession.getMapper( UserMapper.class);
		userMapper.insertUser( user);
		long userId = userMapper.selectLastInsertId();
		Map map = new HashMap();
		if( bbInstalling) {
			map.put("keymaster", true);
			userMetaService.updateUserMeta( sqlSession, userId, "bb_capabilities", map);
		}else{
			map.put("member", true);
			userMetaService.updateUserMeta( sqlSession, userId, "bb_capabilities", map);
			
		}
		return userId;
	}
	
}
