package com.qf.util;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.util
 * @company 千锋教育
 * @date 2024/6/11 15:10
 */

import java.util.Base64;

//base64 编码和解码 激活邮件的时候 为 邮箱地址 code验证码 进行加密
//当 回传回来后 进行邮箱地址 和 code 的解密
public class Base64Utils {
    //加密
    public static String encode(String msg){
        return Base64.getEncoder().encodeToString(msg.getBytes());
    }
    //解密
    public static String decode(String msg){
        return new String(Base64.getDecoder().decode(msg));
    }
}
