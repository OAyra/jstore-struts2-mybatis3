package com.usemodj.forum.domain;

import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * 
 * Table bb_usermeta
		=================
		umeta_id, user_id, meta_key, meta_value
		-----------------
		umeta_id         bigint(20) PK
		user_id          bigint(20)
		meta_key         varchar(255)
		meta_value       longtext
 * 
 * @author jinny
 *
 */
public class UserMeta implements Serializable {
	private static Logger logger = Logger.getLogger(UserMeta.class);
	
	long umetaId  ;   // bigint(20) PK
	long userId   ;   // bigint(20)
	String metaKey  ;   // varchar(255)
	String metaValue;   // longtext
	
	public UserMeta(){
		
	}

	public long getUmetaId() {
		return umetaId;
	}

	public void setUmetaId(long umetaId) {
		this.umetaId = umetaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getMetaKey() {
		return metaKey;
	}

	public void setMetaKey(String metaKey) {
		this.metaKey = metaKey;
	}

	public String getMetaValue() {
		return metaValue;
	}

	public void setMetaValue(String metaValue) {
		this.metaValue = metaValue;
	}
	
}
