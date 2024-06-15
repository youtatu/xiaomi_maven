package com.qf.dao;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.dao
 * @company 千锋教育
 * @date 2024/6/12 12:49
 */
public interface GoodsDao {
    Long selectCount(String where, List<Object> params);

    List<Goods> selectByPage(String where, List<Object> params);

    Goods selectById(int id);

    void insert(Goods goods);
}
