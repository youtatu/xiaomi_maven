package com.qf.dao;

import com.qf.entity.User;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.dao
 * @company 千锋教育
 * @date 2024/6/11 14:29
 */
public interface UserDao {
     User selectByUserName(String username);
    void insert(User user);

    User selectByUserNameAndPassword(String username, String password);
}
