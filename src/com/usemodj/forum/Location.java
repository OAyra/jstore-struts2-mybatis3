package com.usemodj.forum;


public enum Location {
	FRONT((short)1),
	FORUM((short)2),
	VIEW((short)3);
	
	private short num;
	
	Location(short num) {
		this.num = num;
	}
	
	public short getNum(){
		return num;
	}
}
