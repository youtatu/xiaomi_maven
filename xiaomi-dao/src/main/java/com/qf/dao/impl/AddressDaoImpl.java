package com.qf.dao.impl;

import com.qf.dao.AddressDao;
import com.qf.entity.Address;
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
 * @date 2024/6/12 22:16
 */
public class AddressDaoImpl implements AddressDao {
    private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    @Override
    public List<Address> selectByUid(int uid) {
        String sql = "select * from tb_address where uid=?";
        try {
            return qr.query(sql, new BeanListHandler<Address>(Address.class), uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insert(Address address) {
        Object[] params = {address.getDetail(),address.getName(),address.getPhone(),address.getUid(),address.getLevel()};
        String sql = "insert into tb_address values(null,?,?,?,?,?)";
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "delete from tb_address where id=?";
        try {
            qr.update(sql,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Address address) {
        Object[] params = {address.getName(),address.getPhone(),address.getDetail(),address.getId()};
        String sql = "update tb_address set name=?,phone=?,detail=? where id=?";
        try {
            qr.update(sql,params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void defaultAddress(int id, int uid) {
        String sql = "update tb_address set level=0 where uid=?";
        String sql1 = "update tb_address set level=1 where id=?";
        try {
            qr.update(sql,uid);
            qr.update(sql1,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
