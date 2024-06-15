package com.qf.service;

import com.qf.entity.GoodsType;

import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.service
 * @company 千锋教育
 * @date 2024/6/12 10:28
 */
public interface GoodsTypeService {
    List<GoodsType> findByLevel(int level);
}
