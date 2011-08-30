package com.usemodj.forum.domain;

import java.io.Serializable;
/**
 * 	Table bb_meta
		=============
		meta_id, object_type, object_id, meta_key, meta_value
		-------------
		meta_id          bigint(20) PK
		object_type      varchar(16)
		object_id        bigint(20)
		meta_key         varchar(255)
		meta_value       longtext

 * @author jinny
 *
 */
public class Meta implements Serializable {
	long	metaId          ; // bigint(20) PK
	String	objectType      ; // varchar(16)
	long	objectId        ; // bigint(20)
	String 	metaKey         ; // varchar(255)
	String	metaValue       ; // longtext

	public Meta() {
		// TODO Auto-generated constructor stub
	}

	/*  setter/getter methods */
	public long getMetaId() {
		return metaId;
	}

	public void setMetaId(long metaId) {
		this.metaId = metaId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public long getObjectId() {
		return objectId;
	}

	public void setObjectId(long objectId) {
		this.objectId = objectId;
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
