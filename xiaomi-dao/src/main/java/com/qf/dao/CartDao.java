package com.qf.dao;

import com.qf.entity.Cart;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao
 * @company 千锋教育
 * @date 2024/6/12 15:32
 */
public interface CartDao {
    Cart selectByUidAndPid(int uid, int pid);

    void insert(Cart cart);

    void update(Cart cart);

    List<Cart> selectByUid(int uid);

    void delete(int uid, int pid);

    void deleteByUid(int uid);
}
