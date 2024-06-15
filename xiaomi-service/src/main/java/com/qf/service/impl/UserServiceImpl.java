package com.qf.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.qf.dao.UserDao;
import com.qf.dao.impl.UserDaoImpl;
import com.qf.entity.User;
import com.qf.service.UserService;
import com.qf.util.EmailUtils;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.service.impl
 * @company 千锋教育
 * @date 2024/6/11 14:28
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void regist(User user) {
        //密码加密
        user.setPassword(SecureUtil.md5(user.getPassword()));
        userDao.insert(user);
        //发送邮件
        EmailUtils.sendEmail(user);
    }

    @Override
    public boolean checkUserName(String username) {
        User user = userDao.selectByUserName(username);
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        password = SecureUtil.md5(password);
        User user = userDao.selectByUserNameAndPassword(username, password);
        if (user == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return user;
    }
}
