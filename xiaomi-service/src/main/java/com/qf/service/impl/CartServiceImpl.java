package com.qf.service.impl;

import com.qf.dao.CartDao;
import com.qf.dao.impl.CartDaoImpl;
import com.qf.entity.Cart;
import com.qf.service.CartService;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service.impl
 * @company 千锋教育
 * @date 2024/6/12 15:29
 */
public class CartServiceImpl implements CartService {
    private CartDao cartDao = new CartDaoImpl();
    @Override
    public Cart findByUidAndPid(int uid, int pid) {
        return cartDao.selectByUidAndPid(uid,pid);
    }

    @Override
    public void add(Cart cart) {
        cartDao.insert(cart);
    }

    @Override
    public void update(Cart cart) {
        cartDao.update(cart);
    }

    @Override
    public List<Cart> findByUid(int uid) {
        return cartDao.selectByUid(uid);
    }

    @Override
    public void delete(int uid, int pid) {
        cartDao.delete(uid,pid);
    }

    @Override
    public void deleteByUid(int uid) {
        cartDao.deleteByUid(uid);
    }

    @Override
    public List<Cart> queryByUid(int uid) {
        return cartDao.selectByUid(uid);
    }
}
