package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.usemodj.forum.domain.User;

public interface UserMapper {

	User selectUser(@Param("user")User user)  throws Exception;
	long selectLastInsertId() throws Exception;
	long selectFoundRows() throws Exception;
	
	List<User> selectUserByLoginOrEmail(@Param("user")User user)  throws Exception;
	
	void updateUserStatus(@Param("userId")long userId,@Param("userStatus")int userStatus) throws Exception;

	void insertUser(@Param("user")User user) throws Exception;

}
