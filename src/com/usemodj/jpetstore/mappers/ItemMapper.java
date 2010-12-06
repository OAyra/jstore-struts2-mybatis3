package com.usemodj.jpetstore.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.jpetstore.domain.Item;
import com.usemodj.struts.Status;

public interface ItemMapper {

	public List<Item> selectItemList(@Param("productId")String productId, @Param("status")Status status, RowBounds rowBounds) throws Exception;

	public Item selectItem(String itemId);

	public void updateStatus(@Param("itemId") String itemId, @Param("status") Status status) throws Exception;

	public void updateItem(Item item) throws Exception;

	public void insertItem(Item item) throws Exception;

	public List<Item> selectItemIdList(RowBounds rowBounds) throws Exception;

	public void updateStatusByCategory(@Param("categoryId")String categoryId, @Param("status")Status status) throws Exception;

	public void updateStatusByProduct(@Param("productId")String productId, @Param("status")Status status) throws Exception;

}
