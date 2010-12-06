package com.usemodj.jpetstore.struts.admin.action;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Product;
import com.usemodj.jpetstore.service.ProductService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.Status;
import com.usemodj.struts.action.AdminAction;

public class ProductAction extends AdminAction {
	private static Logger logger = Logger.getLogger( ProductAction.class);
	protected ProductService productService = new ProductService();
	private List<Product> productList;
	private Product product = new Product();
	
	public String execupte() throws Exception {
		return SUCCESS;
	}
	public String input() throws Exception {
		return INPUT;
	}
	public String list() throws Exception {
		
		String categoryId = product.getCategoryId();
		SqlSession sqlSession = this.getSqlSessionFactory().openSession();
		//TODO: Paging...
		try {
			int offset = RowBounds.NO_ROW_OFFSET;
			int limit = RowBounds.NO_ROW_LIMIT;
			RowBounds rowBounds = new RowBounds(offset, limit);
			List<Product> productList = productService.selectProductList( sqlSession, categoryId, null, rowBounds);
			this.setProductList(productList);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error(" ProductAction.list() Exception: " + e.getMessage());
		} finally {
			sqlSession.close();
		}
		
		return Constants.LIST;
	}
	public String view(){
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			this.product = productService.selectProduct(session, product.getProductId());
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("-- ProductAction.view() Exception:" + e.getMessage());
		} finally {
			session.close();
		}
		return Constants.VIEW;
	}
	public String create(){
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			productService.createProduct(session, this.product);
			session.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			this.addActionError("Create Exception:"+ e.getMessage());
			logger.error("-- ProductAction.view() Exception:" + e.getMessage());
		} finally {
			session.close();
		}
		
		return Constants.REDIRECT;
	}
	public String update(){
		SqlSession session = this.getSqlSessionFactory().openSession();
		try {
			//Product status and Items status belong to the product are changed together
			productService.updateProduct(session, this.product);
			productService.updateItemStatus(session, this.product.getProductId(), this.product.getStatus());
			session.commit();
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error("-- ProductAction.view() Exception:" + e.getMessage());
			session.rollback();
		} finally {
			session.close();
		}
		
		return Constants.REDIRECT_VIEW;
	}
	public String productIdList(){
		SqlSession session = null;
		try {
			session = this.getSqlSessionFactory().openSession();
			this.productList = productService.selectProductList( session, null, null, new RowBounds());
		} catch (Exception e) {
			logger.error("-- ProductAction.productIdList() Exception:" + e.getMessage());
		} finally {
			session.close();
		}
		return Constants.PRODUCT_LIST;
		
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
							productService.updateStatus( session, checked[i], st);
						} catch (Exception e) {
							//e.printStackTrace();
							logger.error(" -- ProductAction.updateCheckedStatus() Exception: " + e.getMessage());
						}
					}
				}
			}
			session.commit();
		} catch (Exception e) {
			logger.error(" -- ProductAction.updateCheckedStatus() Exception: " + e.getMessage());
		} finally{
			session.close();
		}
		logger.debug("product.categoryId=" + this.product.getCategoryId());
		logger.debug("param product.categoryId=" + this.request.getParameter("product.categoryId"));
		return Constants.REDIRECT;
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
