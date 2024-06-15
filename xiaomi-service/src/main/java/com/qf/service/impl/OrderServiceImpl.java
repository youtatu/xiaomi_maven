package com.qf.service.impl;

import com.qf.dao.OrderDao;
import com.qf.dao.impl.OrderDaoImpl;
import com.qf.entity.Cart;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;
import com.qf.service.CartService;
import com.qf.service.OrderDetailService;
import com.qf.service.OrderService;
import com.qf.util.DataSourceUtils;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/13 9:25
 */
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public void add(Order order, List<Cart> cartList) {
        // 开启事务

        try {
            // 开启事务
            DataSourceUtils.begin();

            // 三张表
            // 1.tb_order 添加一条订单
            orderDao.insert(order);

            // 2. tb_order_detail 添加订单
            OrderDetailService orderDetailService = new OrderDetailServiceIpml();
            for (Cart cart : cartList) {
                OrderDetail orderDetail = new OrderDetail(0, order.getId(), cart.getPid(), cart.getNum(), cart.getMoney(),null);
                orderDetailService.add(orderDetail);
            }
            // 3.删除购物车信息 tb_cart
            CartService cartService = new CartServiceImpl();
            cartService.deleteByUid(order.getUid());

            // 提交事务
            DataSourceUtils.commit();

        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            DataSourceUtils.rollback();
            throw new RuntimeException(e);
        } finally {
            // 关闭链接
            DataSourceUtils.close();
        }
    }

    @Override
    public void updateState(String oid, String status) {
        orderDao.updateState(oid,status);
    }

    @Override
    public List<Order> findByUid(int uid) {
        return orderDao.selectByUid(uid);
    }

    @Override
    public Order findByOid(String oid) {
        return orderDao.selectByOid(oid);
    }

    @Override
    public List<OrderDetail> findOrderDetail(String oid) {
        return orderDao.selectOrderDetail(oid);
    }

}
