package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wgy
 * @version v1.0
 * @project gp15_xiaomi
 * @Date 2023/4/13 10:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Integer id;
    private String detail;
    private String name;
    private String phone;
    private Integer uid;
    private Integer level;
}
