package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.usemodj.forum.domain.Term;

public interface TermMapper {

	List<Term> selectTermJoinedWithTermTaxonomy(@Param("taxonomy")String taxonomy, @Param("number")int number) throws Exception;

	<T> Term selectTermBy(@Param("taxonomy")String taxonomy, @Param("field")String field, @Param("value")T value) throws Exception;

	 Term selectTermBy(@Param("taxonomy")String taxonomy, @Param("field")String field, @Param("value")String  value) throws Exception;
	
}
