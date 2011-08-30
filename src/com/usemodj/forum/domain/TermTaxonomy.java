package com.usemodj.forum.domain;

import java.io.Serializable;

/**
 * Table bb_term_taxonomy
	======================
	term_taxonomy_id, term_id, taxonomy, description, parent, count
	----------------------
	term_taxonomy_id bigint(20) PK
	term_id          bigint(20)
	taxonomy         varchar(32)
	description      longtext
	parent           bigint(20)
	count            bigint(20)

 * @author jinny
 *
 */
public class TermTaxonomy implements Serializable {
	long termTaxonomyId; // bigint(20) PK
	long termId         ; // bigint(20)
	String taxonomy        ; // varchar(32)
	String description     ; // longtext
	long parent          ; // bigint(20)
	long count           ; // bigint(20)

	public TermTaxonomy() {

	}

	public long getTermTaxonomyId() {
		return termTaxonomyId;
	}

	public void setTermTaxonomyId(long termTaxonomyId) {
		this.termTaxonomyId = termTaxonomyId;
	}

	public long getTermId() {
		return termId;
	}

	public void setTermId(long termId) {
		this.termId = termId;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getParent() {
		return parent;
	}

	public void setParent(long parent) {
		this.parent = parent;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
