package com.usemodj.forum.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 		Table bb_users
 *			==============
			ID, user_login, user_pass, user_nicename, user_email, user_url, user_registered, user_status, display_name
			--------------
			ID               bigint(20) unsigned PK
			user_login       varchar(60)
			user_pass        varchar(64)
			user_nicename    varchar(50)
			user_email       varchar(100)
			user_url         varchar(100)
			user_registered  datetime
			user_status      int(11)
			display_name     varchar(250)
 * 
 * 
 * @author jinny
 * @param <T>
 *
 */
public class User<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1058944165324421535L;
	long id        ; // bigint(20) unsigned PK
	String userLogin      ; // varchar(60)
	String userPass       ; // varchar(64)
	String userNicename   ; // varchar(50)
	String userEmail      ; // varchar(100)
	String userUrl        ; // varchar(100)
	Date userRegistered ; // datetime
	int userStatus     ; // int(11)
	String displayName    ; // varchar(250)

	 Map<String, T> usermeta = new HashMap<String, T>();
	 // SQL WHERE condition
	 String by = "ID";
	 String plainPass = null;
	 
	public User() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getUserNicename() {
		return userNicename;
	}

	public void setUserNicename(String userNicename) {
		this.userNicename = userNicename;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}

	public Date getUserRegistered() {
		return userRegistered;
	}

	public void setUserRegistered(Date userRegistered) {
		this.userRegistered = userRegistered;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Map<String, T> getUsermeta() {
		return usermeta;
	}

	public void setUsermeta(Map<String, T> usermeta) {
		this.usermeta = usermeta;
	}

	public  T getUsermetaValue(String key) {
		return (T) this.usermeta.get( key);
	}
	
	public  void setUsermetaValue(String key, T value){
		this.usermeta.put(key, (T) value);
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getPlainPass() {
		return plainPass;
	}

	public void setPlainPass(String plainPass) {
		this.plainPass = plainPass;
	}
}	
