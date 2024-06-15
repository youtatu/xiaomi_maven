package com.qf.service;

import com.qf.entity.Cart;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/13 9:25
 */
public interface OrderService {
    void add(Order order, List<Cart> cartList);

    void updateState(String oid, String status);

    List<Order> findByUid(int uid);

    Order findByOid(String oid);

    List<OrderDetail> findOrderDetail(String oid);
}
