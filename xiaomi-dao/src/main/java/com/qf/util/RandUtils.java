package com.qf.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.util
 * @company 千锋教育
 * @date 2024/6/11 15:20
 */
public class RandUtils {
    //生成激活码
    public static String createCode(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s1 = sdf.format(new Date());
        String s2 = Integer.toHexString(new Random().nextInt());
        return s1+s2;
    }
    //生成订单号
    public static String createOrderId(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String s1 = sdf.format(new Date());
        String s2 = Integer.toHexString(new Random().nextInt(100000));
        return s1+s2;
    }
}
