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
 * @date 2024/6/11 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public static R success(){
        return new R(1,"success",null);
    }
    public static R success(Object data){
        return new R(1,"success",data);
    }
    public static R error(String msg){
        return new R(0,msg,null);
    }
}
