package com.usemodj.forum.domain;

import java.io.Serializable;

public class Term implements Serializable {

	long termId   ;       // bigint(20) PK
	String name   ;          // varchar(55)
	String slug   ;          // varchar(200)
	long termGroup;       // bigint(10)
	
	TermTaxonomy tt = null;
	
	public Term(){
		this.tt = new TermTaxonomy();
	}

	public long getTermId() {
		return termId;
	}

	public void setTermId(long termId) {
		this.termId = termId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public long getTermGroup() {
		return termGroup;
	}

	public void setTermGroup(long termGroup) {
		this.termGroup = termGroup;
	}

	public TermTaxonomy getTt() {
		return tt;
	}

	public void setTt(TermTaxonomy tt) {
		this.tt = tt;
	}
	
}
