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
 * @date 2024/6/11 10:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    //编号
    private Integer id;
    //商品编号
    private Integer pid;
    //购买数量
    private Integer num;
    //金额
    private Integer money;
    // 商品信息
    private Goods goods;
}
