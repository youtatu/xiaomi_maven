package com.qf.dao.impl;

import com.qf.dao.OrderDetailDao;
import com.qf.entity.OrderDetail;
import com.qf.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao.impl
 * @company 千锋教育
 * @date 2024/6/13 12:43
 */
public class OrderDetailDaoImpl implements OrderDetailDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    @Override
    public void insert(OrderDetail orderDetail) {
        Object[] params = {orderDetail.getOid(),orderDetail.getPid(),orderDetail.getNum(),orderDetail.getMoney()};
        Connection conn = DataSourceUtils.getConnection();
        String sql = "insert into tb_orderdetail values(null,?,?,?,?)";
        try {
            qr.update(conn,sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
