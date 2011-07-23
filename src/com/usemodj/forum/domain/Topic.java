package com.usemodj.forum.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *  Table bb_topics
		===============
		topic_id, topic_title, topic_slug, topic_poster, topic_poster_name, topic_last_poster, topic_last_poster_name, topic_start_time, topic_time, forum_id, topic_status, topic_open, topic_last_post_id, topic_sticky, topic_posts, tag_count
		---------------
		topic_id         bigint(20) PK
		topic_title      varchar(100)
		topic_slug       varchar(255)
		topic_poster     bigint(20)
		topic_poster_name varchar(40)
		topic_last_poster bigint(20)
		topic_last_poster_name varchar(40)
		topic_start_time datetime
		topic_time       datetime
		forum_id         int(10)
		topic_status     tinyint(1)
		topic_open       tinyint(1)
		topic_last_post_id bigint(20)
		topic_sticky     tinyint(1)
		topic_posts      bigint(20)
		tag_count        bigint(20)

 * @author jinny
 *
 */
public class Topic implements Serializable {

	long  topicId         		; // bigint(20) PK
	String  topicTitle      	; // varchar(100)
	String  topicSlug       	; // varchar(255)
	long  topicPoster     	; // bigint(20)
	String  topicPosterName 	; // varchar(40)
	long  topicLastPoster 	; // bigint(20)
	String  topicLastPosterName 	; // varchar(40)
	Date  topicStartTime 	; // datetime
	Date  topicTime       	; // datetime
	int  forumId         	; // int(10)
	byte  topicStatus     	; // tinyint(1)
	byte  topicOpen       	; // tinyint(1)
	long  topicLastPostId 	; // bigint(20)
	byte  topicSticky     	; // tinyint(1)
	long  topicPosts      	; // bigint(20)
	long  tagCount        	; // bigint(20)
	
	public Topic() {
		// TODO Auto-generated constructor stub
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getTopicSlug() {
		return topicSlug;
	}

	public void setTopicSlug(String topicSlug) {
		this.topicSlug = topicSlug;
	}

	public long getTopicPoster() {
		return topicPoster;
	}

	public void setTopicPoster(long topicPoster) {
		this.topicPoster = topicPoster;
	}

	public String getTopicPosterName() {
		return topicPosterName;
	}

	public void setTopicPosterName(String topicPosterName) {
		this.topicPosterName = topicPosterName;
	}

	public long getTopicLastPoster() {
		return topicLastPoster;
	}

	public void setTopicLastPoster(long topicLastPoster) {
		this.topicLastPoster = topicLastPoster;
	}

	public String getTopicLastPosterName() {
		return topicLastPosterName;
	}

	public void setTopicLastPosterName(String topicLastPosterName) {
		this.topicLastPosterName = topicLastPosterName;
	}

	public Date getTopicStartTime() {
		return topicStartTime;
	}

	public void setTopicStartTime(Date topicStartTime) {
		this.topicStartTime = topicStartTime;
	}

	public Date getTopicTime() {
		return topicTime;
	}

	public void setTopicTime(Date topicTime) {
		this.topicTime = topicTime;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public byte getTopicStatus() {
		return topicStatus;
	}

	public void setTopicStatus(byte topicStatus) {
		this.topicStatus = topicStatus;
	}

	public byte getTopicOpen() {
		return topicOpen;
	}

	public void setTopicOpen(byte topicOpen) {
		this.topicOpen = topicOpen;
	}

	public long getTopicLastPostId() {
		return topicLastPostId;
	}

	public void setTopicLastPostId(long topicLastPostId) {
		this.topicLastPostId = topicLastPostId;
	}

	public byte getTopicSticky() {
		return topicSticky;
	}

	public void setTopicSticky(byte topicSticky) {
		this.topicSticky = topicSticky;
	}

	public long getTopicPosts() {
		return topicPosts;
	}

	public void setTopicPosts(long topicPosts) {
		this.topicPosts = topicPosts;
	}

	public long getTagCount() {
		return tagCount;
	}

	public void setTagCount(long tagCount) {
		this.tagCount = tagCount;
	}

	// Setter / Getter

	
	
}
