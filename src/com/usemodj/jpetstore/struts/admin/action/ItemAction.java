package com.usemodj.jpetstore.struts.admin.action;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.FileAttach;
import com.usemodj.jpetstore.domain.Item;
import com.usemodj.jpetstore.service.FileAttachService;
import com.usemodj.jpetstore.service.ItemService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Status;
import com.usemodj.struts.action.AdminAction;

public class ItemAction extends AdminAction {
	private static Logger logger = Logger.getLogger( ItemAction.class);
	protected ItemService itemService = new ItemService();
	private List<Item> itemList;
	private Item item = new Item();
    protected FileAttachService attachService = new FileAttachService();
    private List<FileAttach> itemFileList = null;
	
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
			this.itemList = itemService.selectItemList( session, productId, null, rowBounds);
		} catch (Exception e) {
			logger.error("--ItemAction.list() Exception:" + e.getMessage());
		} finally{
			session.close();
		}
		
		return Constants.LIST;
	}
	public String view() throws Exception {
		String itemId = item.getItemId();
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			this.item = itemService.selectItem( session, itemId);
			this.itemFileList = attachService.selectItemFileList( session, this.item.getItemId());
		} catch (Exception e) {
			logger.error("--ItemAction.view() Exception:" + e.getMessage());
		} finally {
			session.close();
		}
		
		return Constants.VIEW;
	}
	public String update() throws Exception {
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			if( null == this.item.getItemId() || this.item.getItemId().trim().isEmpty()){
				this.addActionError(getText("itemId.required"));
			}
			if( null == this.item.getProductId() || this.item.getProductId().trim().isEmpty()){
				this.addActionError(getText("productId.required"));
			}
			if( this.hasActionErrors()) return Constants.VIEW;

			itemService.updateItem( session, this.item);
			session.commit();
			this.addActionMessage(getText("work.success"));
		} catch (Exception e) {
			//e.printStackTrace();
			this.addActionError( "ERROR:" + e.getMessage());
			logger.error("--ItemAction.update() Exception: "+ e.getMessage());
			return Constants.VIEW;
		} finally {
			session.close();
		}
		return Constants.REDIRECT_VIEW;
	}
	public String create() {
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			if( null == this.item.getItemId() || this.item.getItemId().trim().isEmpty()){
				this.addActionError(getText("itemId.required"));
			}
			if( null == this.item.getProductId() || this.item.getProductId().trim().isEmpty()){
				this.addActionError(getText("productId.required"));
			}
			if( this.hasActionErrors()) return Constants.VIEW;
			
			itemService.creatItem( session, this.item);
			session.commit();
			this.addActionMessage(getText("work.success"));
		} catch (Exception e) {
			//e.printStackTrace();
			this.addActionError( e.getMessage());
			logger.error("--ItemAction.create() Exception: "+ e.getMessage());
			return Constants.VIEW;
		} finally {
			session.close();
		}
		return Constants.REDIRECT_VIEW;
	}
	
	public String itemIdList(){
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			this.itemList = itemService.selectItemList( session, null, null, new RowBounds());
		} catch (Exception e) {
			logger.error("-- ItemAction.itemIdList() Exception:" + e.getMessage());
		} finally {
			session.close();
		}
		return Constants.ITEM_LIST;
	}
	
	/**
	 * 선택된 카테고리 상태만 수정
	 * 
	 * @return
	 */
	public String updateCheckedStatus(){
		//RowBounds rowBounds = new RowBounds();
		SqlSession session = this.getSqlSessionFactory().openSession();
		String[] checked = this.request.getParameterValues("checkboxStatus");
		try {
			if(null != checked){
				for(int i=0; i < checked.length; i++){
					logger.debug("checkboxStatus:" + checked[i]);
					String status = this.request.getParameter( checked[i]);
					logger.debug("status: "+ status);
					if(null != status){
						Status st = Status.valueOf( status);
						try {
							itemService.updateStatus( session, checked[i], st);
						} catch (Exception e) {
							//e.printStackTrace();
							this.addActionError( e.getMessage());
							logger.error(" -- ItemAction.updateCheckedStatus() Exception: " + e.getMessage());
						}
					}
				}
			}
			session.commit();
		} catch (Exception e) {
			this.addActionError( e.getMessage());
			logger.error(" -- ItemAction.updateCheckedStatus() Exception: " + e.getMessage());
		} finally{
			session.close();
		}
		logger.debug("item.productId=" + this.item.getProductId());
		//logger.debug("param item.productId=" + this.request.getParameter("item.productId"));
		return Constants.REDIRECT;
	}
    public String itemFileList(){
    	SqlSession session = null;
    	try {
			session = this.getSqlSessionFactory().openSession();
			this.itemFileList = attachService.selectItemFileList( session, this.item.getItemId());
		} catch (Exception e) {
			this.addActionError( e.getMessage());
			logger.error( "FileUploadAtion.itemFiles() Exception:"+ e.getMessage());
		} finally {
			session.close();
		}
		return Constants.LIST;
    }
    public String delectItemFiles(){
    	SqlSession session = null;
    	try {
    		String filePath = context.getRealPath("/");
			String[] attachIds = request.getParameterValues("checkAttachId");
			logger.debug( "checked param length:"+ attachIds.length);
			session = this.getSqlSessionFactory().openSession();
			attachService.deleteItemFiles( session, attachIds, filePath);
			session.commit();
		} catch (Exception e) {
			logger.error( "ItemAction.deleteItemFiles() Exception:" + e.getMessage());
			this.addActionError( e.getMessage());
			session.rollback();
		} finally{
			session.close();
		}

    	return Constants.REDIRECT_VIEW;
    }
	
	// -- setter/getter --
    
	public List<FileAttach> getItemFileList() {
		return itemFileList;
	}
	public void setItemFileList(List<FileAttach> itemFileList) {
		this.itemFileList = itemFileList;
	}
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
