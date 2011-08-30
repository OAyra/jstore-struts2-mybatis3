package com.usemodj.forum;


public enum Location {
	FRONT(1),
	FORUM(2),
	TOPIC(4),
	VIEW(8),
	TAG(16);
	
	private int num;
	
	Location(int  num) {
		this.num = num;
	}
	
	public int getNum(){
		return num;
	}
}
