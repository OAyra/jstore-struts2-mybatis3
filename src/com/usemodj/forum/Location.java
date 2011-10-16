package com.usemodj.forum;


public enum Location {
	FRONT(1, "front-page"),
	FORUM(2, "forum-page"),
	TOPIC(4, "topic-page"),
	VIEW(8, "view-page"),
	TAG(16, "tag-page"), 
	REGISTER(32, "register-page"),
	LOGIN(64, "login-page");
	
	private int num;
	private String page;
	
	Location(int  num, String page) {
		this.num = num;
		this.page = page;
	}
	
	public int getNum(){
		return num;
	}
	public String getPage(){
		return page;
	}
}
