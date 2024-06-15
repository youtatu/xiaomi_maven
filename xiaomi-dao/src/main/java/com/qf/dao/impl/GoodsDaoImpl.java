package com.qf.dao.impl;

import com.qf.dao.GoodsDao;
import com.qf.entity.Goods;
import com.qf.entity.GoodsType;
import com.qf.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao.impl
 * @company 千锋教育
 * @date 2024/6/12 12:49
 */
public class GoodsDaoImpl implements GoodsDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public Long selectCount(String where, List<Object> params) {
        String sql = "select count(*) from tb_goods " + where;
        try {
            return qr.query(sql, new ScalarHandler<>(), params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Goods> selectByPage(String where, List<Object> params) {
        String sql = "select * from tb_goods " + where + " limit ?,?";
        try {
            return qr.query(sql, new BeanListHandler<>(Goods.class), params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Goods selectById(int id) {
        String sql = "select g.*,t.name as tname,t.level, t.parent from tb_goods as g inner join tb_goods_type as t on g.typeid=t.id where g.id=?";
        try {
            return qr.query(sql, rs -> {
                Goods goods = null;
                if (rs.next()) {
                    String name = rs.getString("name");
                    Date pubdate = rs.getTimestamp("pubdate");
                    String picture = rs.getString("picture");
                    int price = rs.getInt("price");
                    int star = rs.getInt("star");
                    String intro = rs.getString("intro");
                    int typeid = rs.getInt("typeid");
                    String tname = rs.getString("tname");
                    int level = rs.getInt("level");
                    int parent = rs.getInt("parent");
                    GoodsType goodsType = new GoodsType(typeid, tname, level, parent);
                    goods = new Goods(id, name, pubdate, picture, price, star, intro, typeid, goodsType);
                }
                return goods;
            }, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Goods goods) {
        Object[] params = {goods.getName(),goods.getPubdate(),goods.getPicture(),goods.getPrice(),goods.getStar(),goods.getIntro(),goods.getTypeid()};
        String sql = "insert into tb_goods values(null,?,?,?,?,?,?,?)";
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
