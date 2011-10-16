package com.usemodj.forum.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.forum.domain.Meta;
import com.usemodj.forum.mappers.MetaMapper;

public class MetaService {
	private static Logger logger = Logger.getLogger( MetaService.class);
	private MetaMapper metaMapper = null;
	
	public List<Meta> getMetaIN( SqlSession sqlSession, String objectType, long[] objectIds) throws Exception {
		List<Meta> metas = null;
		metaMapper = sqlSession.getMapper( MetaMapper.class);
		metas = metaMapper.selectMetaIN(objectIds, objectType);
		//metas = metaMapper.selectMetaIN(objectIds);
		return metas;
	}
	public String getBBOption( SqlSession sqlSession, String metaKey) throws Exception {
		metaMapper = sqlSession.getMapper( MetaMapper.class);
		return metaMapper.selectBBOption( metaKey);
	}
	
	public  void updateMeta(SqlSession sqlSession, long objectId,
			String metaKey, String metaValue,  String objectType) throws Exception {
		metaMapper = sqlSession.getMapper( MetaMapper.class);
		Meta meta = metaMapper.selectMeta( objectType, objectId, metaKey);
		if( null == meta) {
			metaMapper.insertMeta( objectType, objectId, metaKey, metaValue);
		} else if( meta.getMetaValue().equals(metaValue)){
			metaMapper.updateMeta( objectType, objectId, metaKey, metaValue);
		}
	}
	public void deleteBBOption(SqlSession sqlSession, String transientOption) {
		Meta meta = new Meta( 0, transientOption, "", "bb_option");
		 deleteMeta( sqlSession, meta);
	}
	private boolean deleteMeta(SqlSession sqlSession, Meta meta) {
		metaMapper = sqlSession.getMapper( MetaMapper.class);
		Meta _meta = null;
		try {
			 _meta = metaMapper.selectMeta(meta.getObjectType(), meta.getObjectId(), meta.getMetaKey());
			 if( null ==  _meta)
				 return false;
			 metaMapper.deleteMeta( _meta.getMetaId());
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("MetaService.deleteMeta() Exception:" + e.getMessage());
			return false;
		}
		return true;
	}
}
