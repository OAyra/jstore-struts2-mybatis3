package com.usemodj.jpetstore.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Item;
import com.usemodj.jpetstore.domain.Order;
import com.usemodj.jpetstore.mappers.ItemMapper;
import com.usemodj.struts.Status;

public class ItemService {
	private static Logger logger = Logger.getLogger( ItemService.class);

	public List<Item> selectItemList(SqlSession openSession, String productId,
			Status status, RowBounds rowBounds) throws Exception {
		List<Item> itemList;
		try {
			ItemMapper iMapper = openSession.getMapper(ItemMapper.class);
			itemList = iMapper.selectItemList( productId, status, rowBounds);
		} catch (Exception e) {
			logger.error( "ItemService.selectItemList() Exception: "+ e.getMessage());
			throw e;
		}
		return itemList;
	}
	public Item selectItem(SqlSession openSession, String itemId) throws Exception {
		Item item;
		try {
			ItemMapper iMapper = openSession.getMapper(ItemMapper.class);
			item = iMapper.selectItem(itemId);
		} catch (Exception e) {
			logger.error(" ItemService.selectItem() Exception: "+ e.getMessage());
			throw e;
		}
		return item;
	}
	public void updateStatus(SqlSession session, String itemId, Status status) throws Exception {
		ItemMapper iMapper = session.getMapper( ItemMapper.class);
		iMapper.updateStatus( itemId, status);
	}
	public void updateItem(SqlSession session, Item item) throws Exception {
		ItemMapper iMapper = session.getMapper( ItemMapper.class);
		iMapper.updateItem( item);
		
	}
	public void creatItem(SqlSession session, Item item) throws Exception{
		ItemMapper iMapper = session.getMapper( ItemMapper.class);
		iMapper.insertItem(item);
	}
//	public List<Item> selectItemList(SqlSession session, RowBounds rowBounds) throws Exception {
//		ItemMapper iMapper = session.getMapper(ItemMapper.class);
//		List<Item> itemList = iMapper.selectItemIdList( rowBounds);
//		return itemList;
//	}


}
