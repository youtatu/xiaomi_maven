package com.qf.dao.impl;

import com.qf.dao.CartDao;
import com.qf.entity.Cart;
import com.qf.entity.Goods;
import com.qf.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao.impl
 * @company 千锋教育
 * @date 2024/6/12 15:32
 */
public class CartDaoImpl implements CartDao {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public Cart selectByUidAndPid(int uid, int pid) {
        String sql = "select * from tb_cart where id=? and pid=?";
        try {
            return qr.query(sql, new BeanHandler<Cart>(Cart.class), uid, pid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Cart cart) {
        Object[] params = {cart.getId(), cart.getPid(), cart.getNum(), cart.getMoney()};
        String sql = "insert into tb_cart values(?,?,?,?)";
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cart cart) {
        Object[] params = {cart.getNum(), cart.getMoney(), cart.getId(), cart.getPid()};
        String sql = "update tb_cart set num=?,money=? where id=? and pid=?";
        try {
            qr.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Cart> selectByUid(int uid) {
        String sql = "select c.id as uid, c.pid, c.num, c.money, tg.* from tb_cart as c inner join tb_goods as tg on c.pid = tg.id where c.id = ?";
        try {
            return qr.query(sql, resultSet -> {
                List<Cart> cartList = new ArrayList<Cart>();
                while (resultSet.next()) {
                    int pid = resultSet.getInt("pid");
                    int num = resultSet.getInt("num");
                    int id = resultSet.getInt("id");
                    int money = resultSet.getInt("money");
                    String name = resultSet.getString("name");
                    Date pubdate = resultSet.getTimestamp("pubdate");
                    String picture = resultSet.getString("picture");
                    int star = resultSet.getInt("star");
                    int price = resultSet.getInt("price");
                    String intro = resultSet.getString("intro");
                    int typeid = resultSet.getInt("typeid");
                    Goods goods = new Goods(id, name, pubdate, picture, price, star, intro, typeid, null);
                    Cart cart = new Cart(uid, pid, num, money, goods);
                    cartList.add(cart);
                }
                return cartList;
            }, uid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int uid, int pid) {
        String sql = "delete from tb_cart where id=? and pid=?";
        try {
            qr.update(sql,uid,pid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByUid(int uid) {
        String sql = "delete from tb_cart where id=?";
        Connection conn = DataSourceUtils.getConnection();
        try {
            qr.update(conn,sql,uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
