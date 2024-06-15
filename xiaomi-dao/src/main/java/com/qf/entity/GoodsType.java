package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.entity
 * @company 千锋教育
 * @date 2024/6/11 10:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsType {
    //类别编号
    private Integer id;
    //类别名称
    private String name;
    //类别级别
    private Integer level;
    //上一级
    private Integer parent;

}
