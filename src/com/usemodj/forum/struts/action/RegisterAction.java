package com.usemodj.forum.struts.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.Location;
import com.usemodj.forum.PasswordHash;
import com.usemodj.forum.Utils;
import com.usemodj.forum.domain.User;
import com.usemodj.forum.service.MetaService;
import com.usemodj.forum.service.UserMetaService;
import com.usemodj.forum.service.UserService;
import com.usemodj.struts.action.BaseAction;

public class RegisterAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2372565762354480390L;
	private static Logger logger = Logger.getLogger( RegisterAction.class);
	UserService userService = new UserService();
	UserMetaService userMetaService = new UserMetaService();
	MetaService metaService = new MetaService();
	User user = null;
	String url = null;
	// usermeta key
	String from = null;
	String occ = null;
	String interest = null;
	
	public String execute() throws Exception {
		SqlSession sqlSession = null;
		User _user = null;
		this.setLocation( Location.REGISTER);
		
		if("GET".equalsIgnoreCase( request.getMethod()))
			return "input";

		String username = Utils.sanitizeUser( this.user.getUserLogin(), true) ;
		if( StringUtils.isBlank( username) || !username.equals( this.user.getUserLogin())) {
			addFieldError("user.userLogin", getText( String.format("%s is an invalid Username.",  this.user.getUserLogin())));
			return "input";
		}
		try {
			sqlSession = this.getForumSqlSessionFactory().openSession();
			List<User> users = userService.getUserByLoginOrEmail( sqlSession, this.user);
			for( User user: users) {
				logger.debug("-- user login: " + user.getUserLogin() + ", Email: "+ user.getUserEmail() );
			
				if(  user.getUserLogin().equals( this.user.getUserLogin()))
					this.addFieldError("user.userLogin", getText("Username  already exists."));
				
				if( user.getUserEmail().equals( this.user.getUserEmail()))
					this.addFieldError("user.userEmail", getText("Email  already exists."));
				
				return "input";
			}
			
			boolean bbInstalling = false;
			try {
				bbInstalling = (Boolean) getSession("BB_INSTALLING");
			} catch (Exception e) {
				bbInstalling = false;
			}
			long userId = addNewUser( sqlSession,  this.user, bbInstalling);
			logger.debug("-- userId: " + userId);
			if( null != from) 
				userMetaService.updateUserMeta(sqlSession, userId, "from", from);
			if( null != occ)
				userMetaService.updateUserMeta(sqlSession, userId, "occ", occ);
			if( null != interest)
				userMetaService.updateUserMeta(sqlSession, userId, "interest", interest);
				
			sqlSession.commit();
			addActionMessage( getText("Registration  succeeds."));
			return "success";
			
		} catch (Exception e) {
			 e.printStackTrace();
			 this.addActionError( e.getMessage());
			sqlSession.rollback();
		} finally {
			if( sqlSession != null ) sqlSession.close();
		}
		return "input";
		
	}

	private long addNewUser(SqlSession sqlSession, User user, boolean bbInstalling) throws Exception {
		//user_status = 1 means the user has not yet been verified
		user.setUserStatus(1);
		user.setUserNicename( user.getUserLogin());
		user.setUserRegistered( new Date());
		if( null == user.getDisplayName()) 
			user.setDisplayName( user.getUserLogin());
		
		PasswordHash ph = new PasswordHash(8, true);
		String plainPass = ph.generatePassword(sqlSession, 12, true);
		user.setPlainPass(plainPass);
		user.setUserPass( ph.hashPassword( plainPass));
		logger.debug("--plainPass: "+ plainPass);
		long userId = userService.addUser( sqlSession, user, bbInstalling);
		if( !bbInstalling){
			user.setId( userId);
			String uri =null;
			try {
				logger.debug("-- url: " + uri);
				uri = metaService.getBBOption(sqlSession, "uri");
			} catch (Exception e) {
				uri = "http://" + request.getLocalName() + request.getContextPath()+"/forum";
			}

			try {
				//logger.debug("-- siteName: " +getSiteName());
				sendPass( user, uri, getSiteName());
			} catch (IOException ie) {
				 ie.printStackTrace();
				 throw new Exception( ie.getMessage());
			} catch (MessagingException me){
				me.printStackTrace();
				throw new Exception( me.getMessage());
			}
		}
		 return userId;
	}
	
	private void sendPass(User user, String uri, String siteName) throws IOException, MessagingException {
		uri = "<a href='"+ uri + "' >"+ uri + "</a>";
		String message = String.format( getText("<pre>Your username is: %s \nYour password is: %s \nYou can now log in: %s</pre>")
					, user.getUserLogin(), user.getPlainPass(), uri);
		String subject = String.format(getText("%s: Password"), siteName);
		//TODO: SEND EMAIL
		Utils.sendMail(subject, message, user.getUserEmail());
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getOcc() {
		return occ;
	}

	public void setOcc(String occ) {
		this.occ = occ;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	
}
