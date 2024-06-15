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
 * @date 2024/6/11 10:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //用户编号
    private Integer id;
    //用户名
    private String username;
    //密码
    private String password;
    //邮箱
    private String email;
    //性别
    private String gender;
    //标记0 未激活，1激活 2失效
    private Integer flag;
    //角色 0管理员 1普通用户
    private Integer role;
    //激活码
    private String code;
}
