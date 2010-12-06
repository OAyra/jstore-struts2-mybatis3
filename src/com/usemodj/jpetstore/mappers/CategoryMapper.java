package com.usemodj.jpetstore.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.usemodj.jpetstore.domain.Category;
import com.usemodj.struts.Status;

public interface CategoryMapper {

	List<Category> selectCategoryList(@Param("status")Status status, RowBounds rowBounds) throws Exception;

	void updateStatus(@Param("categoryId") String categoryId, @Param("status") Status status) throws Exception;

	void insertCategory(Category category) throws Exception;

}
