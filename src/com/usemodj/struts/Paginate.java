package com.usemodj.struts;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.usemodj.forum.Location;

public class Paginate implements Serializable {
	private static final long serialVersionUID = -8074438944268577607L;

	private List<?> results;
	private long count =0;
	private int page = 1; //current page number
	private int perPage = 10; //size of per page
	private Location location;
	private int forumId;
	
	public Paginate() {
//		this.page = 1;
//		this.perPage = 10;
//		this.count = 0;
//		this.results = Collections.emptyList();
	}

	public Paginate( List<?> results, long count) {
		//this();
		this.results = results;
		this.count = count;
	}
	// getter/setter methods
	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getOffset() {
		if( getPage()<1) setPage(1);
		return (getPage()-1) * getPerPage();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	
}
