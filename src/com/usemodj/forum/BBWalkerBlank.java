package com.usemodj.forum;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BBWalkerBlank extends BBWalker {
	String  treeType;
	Map dbFields = new HashMap();
	String startLvl = "";
	String endLvl = "";
	
	public String getStartLvl() {
		return startLvl;
	}

	public void setStartLvl(String startLvl) {
		this.startLvl = startLvl;
	}

	public String getEndLvl() {
		return endLvl;
	}

	public void setEndLvl(String endLvl) {
		this.endLvl = endLvl;
	}

	public BBWalkerBlank(){
		this.dbFields.put("id", "");
		this.dbFields.put("parent", "");
	}
	
	public String startLvl( StringBuffer output, int depth){
		if( null == this.startLvl)
			return "";
		String indent = StringUtils.repeat("\t", depth);
		output.append( indent).append( this.startLvl);
		return output.toString();
	}

	public String endLvl( StringBuffer output, int depth){
		if( null == this.endLvl)
			return "";
		String indent = StringUtils.repeat("\t", depth);
		output.append( indent).append( this.endLvl);
		return output.toString();
	}
	
	public String startEl(){ return "";}
	public String endEl(){ return "";}
	
}