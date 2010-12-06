package com.usemodj.jpetstore.mappers;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.usemodj.jpetstore.domain.Order;
import com.usemodj.jpetstore.domain.OrderStatus;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {

	void insertOrderShippingAddr(Order order);

	void insertOrderStatus(OrderStatus oStatus);

	//List<Order> selectOrderListByUserId(@Param("userId")String userId, @Param("rowBounds")RowBounds rowBounds);
	List<Order> selectOrderListByUserId(@Param("userId") String userId, @Param("offset") int offset, @Param("limit") int limit);

	List<Order> selectOrderListByUserIdWithRowBounds(@Param("userId")String userId, RowBounds rowBounds) throws Exception;

}
