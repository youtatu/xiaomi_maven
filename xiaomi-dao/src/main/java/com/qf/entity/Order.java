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
 * @date 2024/6/11 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    //订单编号
    private String id;
    //用户id
    private Integer uid;
    //订单金额
    private Integer money;
    //订单状态  1 未付款 2已付款 3已发货 4 已完成
    private String status;
    //订单时间
    private Date time;
    //收获地址编号
    private Integer aid;

    private Address address;
}
