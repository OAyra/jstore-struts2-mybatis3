package com.usemodj.forum.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Table bb_posts
	==============
	post_id, forum_id, topic_id, poster_id, post_text, post_time, poster_ip, post_status, post_position
	--------------
	post_id          bigint(20) PK
	forum_id         int(10)
	topic_id         bigint(20)
	poster_id        int(10)
	post_text        text
	post_time        datetime
	poster_ip        varchar(15)
	post_status      tinyint(1)
	post_position    bigint(20)

 * 
 * @author jinny
 *
 */
public class Post implements Serializable {
	long postId      ;    // bigint(20) PK
	int forumId     ;    // int(10)
	long topicId     ;    // bigint(20)
	int posterId    ;    // int(10)
	String postText    ;    // text
	Date postTime    ;    // datetime
	String posterIp    ;    // varchar(15)
	byte postStatus  ;    // tinyint(1)
	long postPosition;   // bigint(20)
	
	Map metas = new HashMap();

	public Post() {
		// TODO Auto-generated constructor stub
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
	}
	public long getTopicId() {
		return topicId;
	}
	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}
	public int getPosterId() {
		return posterId;
	}
	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}
	public String getPostText() {
		return postText;
	}
	public void setPostText(String postText) {
		this.postText = postText;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public String getPosterIp() {
		return posterIp;
	}
	public void setPosterIp(String posterIp) {
		this.posterIp = posterIp;
	}
	public byte getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(byte postStatus) {
		this.postStatus = postStatus;
	}
	public long getPostPosition() {
		return postPosition;
	}
	public void setPostPosition(long postPosition) {
		this.postPosition = postPosition;
	}

	public void setMetaValue(String metaKey, String metaValue) {
		this.metas.put(metaKey, metaValue);
	}
	public String getMetaValue( String metaKey) {
		return (String) this.metas.get(metaKey);
	}
}
