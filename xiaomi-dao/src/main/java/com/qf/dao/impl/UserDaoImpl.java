package com.qf.dao.impl;

import com.qf.dao.UserDao;
import com.qf.entity.User;
import com.qf.util.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.dao.impl
 * @company 千锋教育
 * @date 2024/6/11 14:29
 */
public class UserDaoImpl implements UserDao {
    private QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public User selectByUserName(String username) {
        try {
            return qr.query("select * from tb_user where username=?",new BeanHandler<>(User.class),username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(User user) {
        Object[] params={user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),user.getFlag(),user.getRole(),user.getCode()};
        try {
            qr.update("insert into tb_user values(null,?,?,?,?,?,?,?)",params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User selectByUserNameAndPassword(String username, String password) {
        try {
            return qr.query("select * from tb_user where username=? and password=?",new BeanHandler<>(User.class),username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
