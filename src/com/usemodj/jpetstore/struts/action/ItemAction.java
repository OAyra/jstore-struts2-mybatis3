package com.usemodj.jpetstore.struts.action; 

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Item;
import com.usemodj.jpetstore.service.ItemService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Status;
import com.usemodj.struts.action.BaseAction;

public class ItemAction extends BaseAction {
	private static Logger logger = Logger.getLogger( ItemAction.class);
	protected ItemService itemService = new ItemService();
	private List<Item> itemList;
	private Item item;
	
	public String execupte() throws Exception {
		return SUCCESS;
	}
	public String input() throws Exception {
		return INPUT;
	}
	public String list() throws Exception {
		String productId = item.getProductId();
		int offset = RowBounds.NO_ROW_OFFSET;
		int limit = RowBounds.NO_ROW_LIMIT;
		RowBounds rowBounds = new RowBounds( offset, limit);
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			List<Item> itemList = itemService.selectItemList( session, productId, Status.AT, rowBounds);
			this.setItemList(itemList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return Constants.LIST;
	}
	public String view() throws Exception {
		String itemId = item.getItemId();
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			Item item = itemService.selectItem( session, itemId);
			this.setItem(item);
		}catch(Exception e){
			logger.error("ItemAction.view() Exception:" + e.getMessage());
		}finally{
			session.close();
		}
		return Constants.VIEW;
	}
	
	// -- setter/getter --
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

	
}
