package com.qf.service.impl;

import com.qf.dao.GoodsDao;
import com.qf.dao.impl.GoodsDaoImpl;
import com.qf.entity.Goods;
import com.qf.entity.PageBean;
import com.qf.service.GoodsService;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service.impl
 * @company 千锋教育
 * @date 2024/6/12 12:43
 */
public class GoodsServiceImpl implements GoodsService {
    private GoodsDao goodsDao = new GoodsDaoImpl();

    @Override
    public PageBean<Goods> findByPage(int pn, int ps, String where, List<Object> params) {
        long totalSize = goodsDao.selectCount(where, params);
        params.add((pn - 1) * ps);
        params.add(ps);
        List<Goods> data = goodsDao.selectByPage(where, params);
        PageBean<Goods> pageBean = new PageBean<>(pn, ps, totalSize, data);
        return pageBean;
    }

    @Override
    public Goods findById(int id) {
        return goodsDao.selectById(id);
    }

    @Override
    public void add(Goods goods) {
        goodsDao.insert(goods);
    }
}
