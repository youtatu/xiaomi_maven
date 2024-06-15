package com.qf.service;

import com.qf.entity.Goods;
import com.qf.entity.PageBean;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/12 12:43
 */
public interface GoodsService {

    PageBean<Goods> findByPage(int pn, int ps, String where, List<Object> params);

    Goods findById(int id);

    void add(Goods goods);
}
