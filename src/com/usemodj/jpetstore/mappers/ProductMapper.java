package com.usemodj.jpetstore.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.jpetstore.domain.Product;
import com.usemodj.struts.Status;

public interface ProductMapper {

	public List<Product> selectProductList(@Param("categoryId")String categoryId,
			@Param("status")Status status, RowBounds rowBounds) throws Exception;

	public void updateStatus(@Param("productId") String productId, @Param("status") Status status) throws Exception;

	public Product selectProduct(String productId) throws Exception;

	public void updateProduct(Product product) throws Exception;

	public void insertProduct(Product product) throws Exception;

	public List<Product> selectProductIdList(RowBounds rowBounds) throws Exception;

	public void updateStatusByCategory(@Param("categoryId")String categoryId, @Param("status")Status status) throws Exception; 

}
