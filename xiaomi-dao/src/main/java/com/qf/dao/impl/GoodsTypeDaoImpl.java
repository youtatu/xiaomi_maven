package com.qf.dao.impl;

import com.qf.dao.GoodsTypeDao;
import com.qf.entity.GoodsType;
import com.qf.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao.impl
 * @company 千锋教育
 * @date 2024/6/12 10:36
 */
public class GoodsTypeDaoImpl implements GoodsTypeDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    @Override
    public List<GoodsType> selectByLevel(int level) {
        try {
            return qr.query("select * from tb_goods_type where level = ?", new BeanListHandler<GoodsType>(GoodsType.class), level);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
