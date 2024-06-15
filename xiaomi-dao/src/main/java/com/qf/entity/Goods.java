package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.entity
 * @company 千锋教育
 * @date 2024/6/11 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    //商品编号
    private Integer id;
    //商品名称
    private String name;
    //上架事件
    private Date pubdate;
    //图片
    private String picture;
    //价格
    private Integer price;
    //评分
    private Integer star;
    //商品介绍
    private String intro;
    //类别
    private Integer typeid;

    private GoodsType goodsType;
}
