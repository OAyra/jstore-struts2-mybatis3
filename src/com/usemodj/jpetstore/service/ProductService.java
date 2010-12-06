package com.usemodj.jpetstore.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Product;
import com.usemodj.jpetstore.mappers.ItemMapper;
import com.usemodj.jpetstore.mappers.ProductMapper;
import com.usemodj.struts.Status;

public class ProductService {
	private static Logger logger = Logger.getLogger( ProductService.class);
	
	public List<Product> selectProductList(SqlSession openSession,
			String categoryId, Status status, RowBounds rowBounds) throws Exception {
		List<Product> proList = null;
		try {
			//proList = openSession.selectList("selectProductList", categoryId, rowBounds);
			ProductMapper pMapper = openSession.getMapper( ProductMapper.class);
			proList = pMapper.selectProductList( categoryId, status, rowBounds);
		} catch (Exception e) {
			logger.error( " selectProductList() Exception : " + e.getMessage());
			throw e;
		}
		return proList;
	}

	public void updateStatus(SqlSession session, String productId, Status status) throws Exception {
		ProductMapper pMapper = session.getMapper( ProductMapper.class);
		pMapper.updateStatus( productId, status);
		ItemMapper iMapper = session.getMapper(ItemMapper.class);
		iMapper.updateStatusByProduct(productId, status);
	}
	public void updateItemStatus(SqlSession session, String productId, Status status) throws Exception {
		ItemMapper iMapper = session.getMapper(ItemMapper.class);
		iMapper.updateStatusByProduct(productId, status);
	}
	public Product selectProduct(SqlSession session, String productId) throws Exception {
		ProductMapper pMapper = session.getMapper(ProductMapper.class);
		return pMapper.selectProduct(productId);
	}

	public void updateProduct(SqlSession session, Product product) throws Exception {
		ProductMapper pMapper = session.getMapper( ProductMapper.class);
		pMapper.updateProduct( product);
	}

	public void createProduct(SqlSession session, Product product) throws Exception {
		ProductMapper pMapper = session.getMapper( ProductMapper.class);
		pMapper.insertProduct( product);
		
	}

//	public List<Product> selectProductList(SqlSession session,
//			RowBounds rowBounds) throws Exception {
//		ProductMapper pMapper = session.getMapper( ProductMapper.class);
//		return pMapper.selectProductIdList( rowBounds);
//	}

}
