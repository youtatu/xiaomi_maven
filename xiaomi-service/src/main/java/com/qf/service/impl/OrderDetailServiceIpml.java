package com.qf.service.impl;

import com.qf.dao.OrderDetailDao;
import com.qf.dao.impl.OrderDetailDaoImpl;
import com.qf.entity.OrderDetail;
import com.qf.service.OrderDetailService;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service.impl
 * @company 千锋教育
 * @date 2024/6/13 11:48
 */
public class OrderDetailServiceIpml implements OrderDetailService {
    private OrderDetailDao orderDetailDao = new OrderDetailDaoImpl();
    @Override
    public void add(OrderDetail orderDetail) {
        orderDetailDao.insert(orderDetail);
    }
}
