package com.qf.dao;

import com.qf.entity.Order;
import com.qf.entity.OrderDetail;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao
 * @company 千锋教育
 * @date 2024/6/13 9:39
 */
public interface OrderDao {
    void insert(Order order);

    void updateState(String oid, String status);

    List<Order> selectByUid(int uid);

    Order selectByOid(String oid);

    List<OrderDetail> selectOrderDetail(String oid);
}
