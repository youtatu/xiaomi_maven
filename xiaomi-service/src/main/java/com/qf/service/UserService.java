package com.qf.service;


import com.qf.entity.User;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/11 14:28
 */
public interface UserService {
    void regist(User user);

    boolean checkUserName(String username);

    User login(String username, String password);
}
