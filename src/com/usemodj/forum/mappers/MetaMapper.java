package com.usemodj.forum.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.usemodj.forum.domain.Meta;

public interface MetaMapper {
	List<Meta> selectMetaIN( @Param("objectIds")long[] objectIds, @Param("objectType")String objectType) throws Exception;
	//List<Meta> selectMetaIN( long[] objectIds) throws Exception;

	List<Meta> selectMetaLongArr( @Param("objectIds")Long[] objectIds, @Param("objectType")String objectType) throws Exception;

	List<Meta> selectMetaINInt( @Param("objectIds") int[] objectIds, @Param("objectType")String objectType) throws Exception;

	String  selectBBOption(@Param("metaKey")String metaKey) throws Exception;

	List<Meta> selectMetas( @Param("objectId")long objectId, @Param("objectType")String objectType) throws Exception;

	Meta selectMeta(  @Param("objectType")String objectType, @Param("objectId")long objectId, @Param("metaKey")String metaKey) throws Exception;

	void insertMeta(@Param("objectType")String objectType, @Param("objectId")long objectId, @Param("metaKey")String metaKey, @Param("metaValue")String metaValue) throws Exception;

	void updateMeta(@Param("objectType") String objectType, @Param("objectType")long objectId,  @Param("metaKey")String metaKey,
			@Param("metaValue")String metaValue) throws Exception;

	void deleteMeta(@Param("metaId")long metaId) throws Exception;
	
}