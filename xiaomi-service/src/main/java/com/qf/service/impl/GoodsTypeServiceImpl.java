package com.qf.service.impl;

import com.qf.dao.GoodsTypeDao;
import com.qf.dao.impl.GoodsTypeDaoImpl;
import com.qf.entity.GoodsType;
import com.qf.service.GoodsTypeService;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service.impl
 * @company 千锋教育
 * @date 2024/6/12 10:28
 */
public class GoodsTypeServiceImpl implements GoodsTypeService {
    private GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
    @Override
    public List<GoodsType> findByLevel(int level) {
        return goodsTypeDao.selectByLevel(level);
    }
}
