package com.usemodj.jpetstore.struts.action;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Category;
import com.usemodj.jpetstore.service.CategoryService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Status;
import com.usemodj.struts.action.BaseAction;

public class CategoryAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CategoryAction.class);
	private CategoryService categoryService = new CategoryService();
	private List<Category> categoryList;
	
	public String execute() throws Exception{
		RowBounds rowBounds = new RowBounds();
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			this.categoryList = categoryService.selectCategoryList(session, Status.AT, rowBounds);
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
		int offset = RowBounds.NO_ROW_OFFSET;
		int limit = RowBounds.NO_ROW_LIMIT;
		RowBounds rowBounds = new RowBounds();
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			List<Category> categoryList = categoryService.selectCategoryList( session,Status.AT, rowBounds);
			this.setCategoryList(categoryList);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("CategoryAction.list() Exception:"+ e.getMessage());
		} finally {
			session.close();
		}
		
		return Constants.LIST;
	}
	// -- getter/setter --
	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}


}
