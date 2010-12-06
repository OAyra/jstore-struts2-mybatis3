package com.usemodj.jpetstore.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Category;
import com.usemodj.jpetstore.mappers.CategoryMapper;
import com.usemodj.jpetstore.mappers.ItemMapper;
import com.usemodj.jpetstore.mappers.ProductMapper;
import com.usemodj.struts.Status;

public class CategoryService {
	private static Logger logger = Logger.getLogger( CategoryService.class);

	public List<Category> selectCategoryList(SqlSession openSession,
			Status status, RowBounds rowBounds) throws Exception {
		List<Category> catList = null;
		try {
			CategoryMapper cMapper = openSession.getMapper( CategoryMapper.class);
			//catList = cMapper.selectCategoryList( (null != status ? status.name():null), rowBounds);
			catList = cMapper.selectCategoryList( status, rowBounds);
		} catch (Exception e) {
			logger.error( " CategoryService.selectCategoryList() Exception : " + e.getMessage());
			throw e;
		}
		return catList;
	}

	public void updateStatus(SqlSession session, String categoryId, Status st) throws Exception {
		CategoryMapper cMapper = session.getMapper( CategoryMapper.class);
		cMapper.updateStatus( categoryId, st);
		ProductMapper pMapper = session.getMapper( ProductMapper.class);
		pMapper.updateStatusByCategory( categoryId, st);
		ItemMapper iMapper = session.getMapper( ItemMapper.class);
		iMapper.updateStatusByCategory( categoryId, st);
	}

	public void insertCategory(SqlSession session, Category category) throws Exception {
		CategoryMapper cMapper = session.getMapper( CategoryMapper.class);
		cMapper.insertCategory( category);
		
	}
	
}
