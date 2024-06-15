package com.qf.service;

import com.qf.entity.Cart;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/12 15:29
 */
public interface CartService {
    Cart findByUidAndPid(int uid, int pid);

    void add(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(int id);

    void delete(int uid, int pid);

    void deleteByUid(int uid);

    List<Cart> queryByUid(int uid);
}
