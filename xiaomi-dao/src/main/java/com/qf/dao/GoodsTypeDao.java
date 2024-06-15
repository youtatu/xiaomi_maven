package com.qf.dao;

import com.qf.entity.GoodsType;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao
 * @company 千锋教育
 * @date 2024/6/12 10:36
 */
public interface GoodsTypeDao {
    List<GoodsType> selectByLevel(int level);
}
