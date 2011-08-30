package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TermRelationshipMapper {

	List selectObjectIds(@Param("taxonomies")String[] taxonomies, @Param("order")String order,  @Param("field")String field, @Param("terms")long[] terms,
			@Param("userId")long userId) throws Exception;

	
	List selectTermRelationshipIds(@Param("taxonomy")String taxonomy, @Param("order")String order,  @Param("field")String field, @Param("term")long term,
			@Param("userId")long userId);

}
