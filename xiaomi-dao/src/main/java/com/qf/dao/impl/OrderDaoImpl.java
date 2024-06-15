package com.qf.dao.impl;

import com.qf.dao.OrderDao;
import com.qf.entity.Address;
import com.qf.entity.Goods;
import com.qf.entity.Order;
import com.qf.entity.OrderDetail;
import com.qf.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.dao.impl
 * @company 千锋教育
 * @date 2024/6/13 14:18
 */
public class OrderDaoImpl implements OrderDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public void insert(Order order) {
        String sql = "insert into tb_order values(?,?,?,?,?,?)";
        Object[] params = {order.getId(), order.getUid(), order.getMoney(), order.getStatus(), order.getTime(), order.getAid()};
        try {
            Connection conn = DataSourceUtils.getConnection();
            qr.update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateState(String oid, String status) {
        String sql = "update tb_order set status = ? where id = ?";
        try {
            qr.update(sql, status, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> selectByUid(int uid) {
        String sql = "select o.*,a.detail,a.name,a.phone,a.level from tb_order as o inner join tb_address as a on o.aid=a.id where o.uid=?";
        try {
            return qr.query(sql, rs -> {
                List<Order> orderList = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("id");
                    int money = rs.getInt("money");
                    String status = rs.getString("status");
                    Date time = rs.getTimestamp("time");
                    int aid = rs.getInt("aid");
                    String detail = rs.getString("detail");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    int level = rs.getInt("level");
                    Order order = new Order(id, uid, money, status, time, aid, new Address(aid, detail, name, phone, uid, level));
                    orderList.add(order);
                }
                return orderList;
            }, uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order selectByOid(String oid) {
        String sql = "select o.*,a.detail,a.name,a.phone,a.level from tb_order as o inner join tb_address as a on o.aid=a.id where o.id=?";
        try {
            return qr.query(sql, rs -> {
                Order order = null;
                if (rs.next()) {
                    String id = rs.getString("id");
                    int uid = rs.getInt("uid");
                    int money = rs.getInt("money");
                    String status = rs.getString("status");
                    Date time = rs.getTimestamp("time");
                    int aid = rs.getInt("aid");
                    String detail = rs.getString("detail");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    int level = rs.getInt("level");
                    order = new Order(id, uid, money, status, time, aid, new Address(aid, detail, name, phone, uid, level));
                }
                return order;
            }, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> selectOrderDetail(String oid) {
        String sql = "select od.id as od_id,od.oid,od.pid,od.num,od.money, g.* from tb_orderdetail as od inner join tb_goods as g on od.pid=g.id where od.oid=?";
        try {
            return qr.query(sql, rs -> {
                List<OrderDetail> orderDetailList = new ArrayList<>();
                while (rs.next()) {
                    int od_id = rs.getInt("od_id");
                    int pid = rs.getInt("pid");
                    int num = rs.getInt("num");
                    int money = rs.getInt("money");
                    String name = rs.getString("name");
                    Date pubdate = rs.getTimestamp("pubdate");
                    String picture = rs.getString("picture");
                    int price = rs.getInt("price");
                    int star = rs.getInt("star");
                    String intro = rs.getString("intro");
                    int typeid = rs.getInt("typeid");
                    OrderDetail orderDetail = new OrderDetail(od_id, oid, pid, num, money, new Goods(pid, name, pubdate, picture, price, star, intro, typeid, null));
                    orderDetailList.add(orderDetail);
                }
                return orderDetailList;
            }, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
