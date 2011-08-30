package com.usemodj.forum.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Term;
import com.usemodj.forum.mappers.TermMapper;
import com.usemodj.forum.mappers.TermRelationshipMapper;

public class TermTaxonomyService {
	private static Logger logger = Logger.getLogger( TermTaxonomyService.class);
	TermMapper termMapper = null;
	TermRelationshipMapper termRelationshipMapper = null;
	
	public List<Term> getTerms(SqlSession sqlSession, String taxonomy, int number) throws Exception {
		termMapper = sqlSession.getMapper( TermMapper.class);
		List<Term> terms = null;
		try {
			terms = termMapper.selectTermJoinedWithTermTaxonomy( taxonomy, number);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return terms;
	}
	
	public <T> Term getTermBy( SqlSession sqlSession, String taxonomy,  String field,  T value) throws Exception{
		
		termMapper = sqlSession.getMapper( TermMapper.class);
		Term term = termMapper.selectTermBy( taxonomy, field, value);
		return term;
	}

	public  Term getTermBy( SqlSession sqlSession, String taxonomy,  String field, String  value) throws Exception{
		
		termMapper = sqlSession.getMapper( TermMapper.class);
		Term term = termMapper.selectTermBy( taxonomy, field, value);
		if( null != term) 
			logger.debug("--- term.tt: termId"+ term.getTt().getTermId()+ 
					", termTaxonomyId: "	+term.getTt().getTermTaxonomyId() +
					", taxonomy :"+ term.getTt().getTaxonomy());
		
		return term;
	}

	public <K> List getObjectsInTerm(SqlSession sqlSession, long[] terms,
			String[] taxonomies, String order, String field, long userId) throws Exception {
		termRelationshipMapper = sqlSession.getMapper( TermRelationshipMapper.class);
		order = ( "DESC" == order.toUpperCase())? "DESC": "ASC";
		List tr = termRelationshipMapper.selectObjectIds( taxonomies, order, field,  terms, userId);
	
		return tr;
	}
	
	public <K> List<Long> getObjectsInTerm(SqlSession sqlSession, long term,
			String taxonomy, String order, String field, long userId) throws Exception {
		termRelationshipMapper = sqlSession.getMapper( TermRelationshipMapper.class);
		order = ( "DESC" == order.toUpperCase())? "DESC": "ASC";
		logger.debug("--- field: "+ field + ", order:"+ order+", taxonomy:"+ taxonomy);
		List<Long> tr = termRelationshipMapper.selectTermRelationshipIds( taxonomy, order, field,  term, userId);
		logger.debug("--- tr: "+ tr.toString());
		return tr;
	}

}
