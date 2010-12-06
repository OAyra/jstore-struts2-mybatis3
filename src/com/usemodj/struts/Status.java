package com.usemodj.struts;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public enum Status {
	AT("Active","활성"),
	IA("Inactive","비활성"),
	DL("Delete","삭제");
	
	private String fullName;
	private String koName;
	Status(String fullName, String koName){
		this.fullName = fullName;
		this.koName = koName;
	}
	public String getFullName(){
		return this.fullName;
	}
	public String getKoName(){
		return this.koName;
	}	
	public static List<Status> getStatusList(){
		return Arrays.asList( Status.values());
	}
	public static String statusString(){
		StringBuffer buf = new StringBuffer("#{");
		int i = Status.values().length;
		for( Status st: Status.values()){
			buf.append( st).append(":").append(st.getKoName());
			if( i > 1) buf.append(",");
			i--;
		}
		buf.append("}");
		return buf.toString();
	}
	public static Hashtable getStatusHash(){
		Hashtable hash = new Hashtable();
		for( Status st: Status.values()){
			hash.put(st.name(), st.getKoName());
		}
		return hash;
	}
}
