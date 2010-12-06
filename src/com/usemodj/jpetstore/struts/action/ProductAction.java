package com.usemodj.jpetstore.struts.action;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Category;
import com.usemodj.jpetstore.domain.Product;
import com.usemodj.jpetstore.service.ProductService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Status;
import com.usemodj.struts.action.BaseAction;

public class ProductAction extends BaseAction {
	private static Logger logger = Logger.getLogger( ProductAction.class);
	protected ProductService productService = new ProductService();
	private List<Product> productList;
	private Product product;
	
	public String execupte() throws Exception {
		return SUCCESS;
	}
	public String input() throws Exception {
		return INPUT;
	}
	public String list() throws Exception {
		
		String categoryId = product.getCategoryId();
		//TODO: Paging...
		int offset = RowBounds.NO_ROW_OFFSET;
		int limit = RowBounds.NO_ROW_LIMIT;
		RowBounds rowBounds = new RowBounds(offset, limit);
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			List<Product> productList = productService.selectProductList( session, categoryId, Status.AT, rowBounds);
			this.setProductList(productList);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(" ProductAction.list() Exception:"+ e.getMessage());
		} finally {
			session.close();
		}
		
		return Constants.LIST;
	}
	
	// -- getter/setter --
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	
}
