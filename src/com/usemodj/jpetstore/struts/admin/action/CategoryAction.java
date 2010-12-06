package com.usemodj.jpetstore.struts.admin.action;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Category;
import com.usemodj.jpetstore.service.CategoryService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Status;
import com.usemodj.struts.action.AdminAction;

public class CategoryAction extends AdminAction {
	private static Logger logger = Logger.getLogger(CategoryAction.class);
	private CategoryService categoryService = new CategoryService();
	private List<Category> categoryList;
	private Category category = new Category();

	public String execute() throws Exception {
		RowBounds rowBounds = new RowBounds();
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			this.categoryList = categoryService.selectCategoryList(session, null, rowBounds);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(" -- CategoryAction.execute() Exception: "
					+ e.getMessage());

		} finally {
			session.close();
		}

		return SUCCESS;
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String list() throws Exception {
		// int offset = RowBounds.NO_ROW_OFFSET;
		// int limit = RowBounds.NO_ROW_LIMIT;
		RowBounds rowBounds = new RowBounds();
		SqlSession session = this.getSqlSessionFactory().openSession();
		List<Category> categoryList = null;
		try {
			categoryList = categoryService.selectCategoryList(session,null,
					rowBounds);
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(" -- CategoryAction.list() Exception: "
					+ e.getMessage());

		} finally {
			session.close();
		}
		this.setCategoryList(categoryList);

		return Constants.LIST;
	}

	/**
	 * 모든 카테고리 상태 수정
	 * 
	 * @return
	 */
	public String updateStatus() {
		RowBounds rowBounds = new RowBounds();
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			List<Category> categoryList = categoryService.selectCategoryList(
					session, null, rowBounds);
			for (Category cat : categoryList) {
				String catId = cat.getCategoryId();
				String status = this.request.getParameter(catId);
				if (null != status) {
					Status st = Status.valueOf(status);
					try {
						categoryService.updateStatus(session, catId, st);
					} catch (Exception e) {
						// e.printStackTrace();
						logger
								.error(" -- CategoryAction.updateStatus() Exception: "
										+ e.getMessage());
					}
				}
			}
			session.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("-- CategoryAction.updateStatus() Exception: "
					+ e.getMessage());
		} finally {
			session.close();
		}
		return Constants.REDIRECT;
	}

	/**
	 * 선택된 카테고리 상태만 수정
	 * 
	 * @return
	 */
	public String updateCheckedStatus(){
		RowBounds rowBounds = new RowBounds();
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
							categoryService.updateStatus( session, checked[i], st);
						} catch (Exception e) {
							//e.printStackTrace();
							logger.error(" -- CategoryAction.updateCheckedStatus() Exception: " + e.getMessage());
						}
					}
				}
			}
			session.commit();
		} catch (Exception e) {
			logger.error(" -- CategoryAction.updateCheckedStatus() Exception: " + e.getMessage());
		} finally{
			session.close();
		}
		return Constants.REDIRECT;
	}

	public String addCategory() {
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			logger.debug(" categoryId=" + this.category.getCategoryId());
			logger.debug(" name=" + this.category.getName());
			categoryService.insertCategory(session, this.category);
			session.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("--CategoryAction.addCategory() Exception: "
					+ e.getMessage());
		} finally {
			session.close();
		}
		return Constants.REDIRECT;
	}

	// -- getter/setter --
	public List<Category> getCategoryList() {
		return this.categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public List<Status> getStatusList() {
		return Status.getStatusList();
	}

	public String getStatusString() {
		return Status.statusString();
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
