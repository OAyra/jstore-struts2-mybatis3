package com.usemodj.forum.struts.action;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.Location;
import com.usemodj.forum.PasswordHash;
import com.usemodj.forum.domain.User;
import com.usemodj.forum.service.UserService;
import com.usemodj.struts.action.BaseAction;

public class LoginAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5235472144928295365L;
	private static Logger logger = Logger.getLogger( LoginAction.class);
	UserService userService = new UserService();
	User user = null;
	String remember = null; 
	boolean loginAttempt = false;
	boolean logout = false;
	String url = null;
	
	public String execute() throws Exception {
		SqlSession sqlSession = null;
		User _user = null;
		try {
			this.setLocation(  Location.LOGIN) ;
			//logout
			if( true == this.logout) {
				removeSession("user");
				return "success";
			}
			_user = (User)getSession("user");
			if(null != _user) {
				logger.debug("-- LoginAction session user exists" );
				return "login-success";
			}
			logger.debug("-- url: "+ url);
			logger.debug("-- REFERER: " +request.getHeader("REFERER"));
			String referer = (String)getSession("REFERER");
			if( null == referer) {
				this.url = request.getHeader("REFERER");
				setSession("REFERER",  this.url);
			} else {
				this.url = referer;
			}
			if( "GET".equalsIgnoreCase( request.getMethod()))
				return "login";
			
			sqlSession = this.getForumSqlSessionFactory().openSession();
			 _user = userService.getUser( sqlSession, this.user);
			if( null == _user) {
				this.addActionError(getText("username or password is incorrect!"));
				return "login";
			}
			logger.debug("--- db user userPass:" + _user.getUserPass());
			if( !checkPassword(user.getUserPass(), _user.getUserPass(), _user.getId())) {
				addActionError( getText("username or password is incorrect!"));
				return "login";
			}
			//User is logging in for the first time, update their user_status to normal
			if( 1== _user.getUserStatus())
				updateUserStatus( sqlSession, _user.getId(), 0);
			
			removeSession("REFERER");
			setSession("user", _user);
			
		} catch(Exception e){
			logger.error("-- LoginAction Exception : " + e.getMessage());
		} finally {
			if(null != sqlSession) sqlSession.close();
		}
		return "login-success";
	}

	private void updateUserStatus(SqlSession sqlSession, long userId, int userStatus) throws Exception {
		userService.updateUserStatus( sqlSession, userId, userStatus);
	}

	private boolean checkPassword(String userPass, String hashPass,
			long  userId) {
		return PasswordHash.checkPassword( userPass, hashPass, userId);
		
	}

	//getter/ setter
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRemember() {
		return remember;
	}

	public void setRemember(String remember) {
		this.remember = remember;
	}

	public boolean isLogout() {
		return logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isLoginAttempt() {
		return loginAttempt;
	}

	public void setLoginAttempt(boolean loginAttempt) {
		this.loginAttempt = loginAttempt;
	}

}
