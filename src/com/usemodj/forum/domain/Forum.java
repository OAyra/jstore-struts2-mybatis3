package com.usemodj.forum.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Table bb_forums
 * ===============
 * forum_id, forum_name, forum_slug, forum_desc, forum_parent, forum_order, topics, posts
 * ---------------
 * forum_id         int(10) PK
 * forum_name       varchar(150)
 * forum_slug       varchar(255)
 * forum_desc       text
 * forum_parent     int(10)
 * forum_order      int(10)
 * topics           bigint(20)
 * posts            bigint(20)
 * 
 * @author jinny
 *
 */

public class Forum implements Serializable {

	 
	int  	forumId     ;	//  int(10) PK
	String  forumName   ;	//  varchar(150)
	String  forumSlug   ;	//  varchar(255)
	String  forumDesc   ;	//  text
	int	forumParent ;	//  int(10)
	int	forumOrder  ;	//  int(10)
	long  	topics      ;	//   bigint(20)
	long  	posts       ;	//   bigint(20)  * 
	Meta meta = null;
	Map metas = new HashMap();
	
	public Forum(){
		
	}
	
	public Forum( int forumId) {
		this.forumId = forumId;
	}

	// Setters /Getters methods
	
	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public String getForumSlug() {
		return forumSlug;
	}

	public void setForumSlug(String forumSlug) {
		this.forumSlug = forumSlug;
	}

	public String getForumDesc() {
		return forumDesc;
	}

	public void setForumDesc(String forumDesc) {
		this.forumDesc = forumDesc;
	}

	public int getForumParent() {
		return forumParent;
	}

	public void setForumParent(int forumParent) {
		this.forumParent = forumParent;
	}

	public int getForumOrder() {
		return forumOrder;
	}

	public void setForumOrder(int forumOrder) {
		this.forumOrder = forumOrder;
	}

	public long getTopics() {
		return topics;
	}

	public void setTopics(long topics) {
		this.topics = topics;
	}

	public long getPosts() {
		return posts;
	}

	public void setPosts(long posts) {
		this.posts = posts;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
	
	public void setMetaValue(String metaKey, String metaValue) {
		this.metas.put(metaKey, metaValue);
	}
	public String getMetaValue( String metaKey) {
		return (String) this.metas.get(metaKey);
	}
	
	public int getForumIsCategory(){
		try {
			return Integer.parseInt( getMetaValue("forum_is_category"));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
