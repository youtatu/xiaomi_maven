package com.qf.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.config
 * @company 千锋教育
 * @date 2024/6/13 16:02
 */
public class AlipayConfig {
    public static final String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";
    public static final String FORMAT = "JSON";
    public static final String CHARSET = "UTF-8";
    //签名方式
    public static final String SIGN_TYPE = "RSA2";

    public static String appId;
    public static String appPrivateKey;
    public static String alipayPublicKey;
    public static String notifyUrl;
    public static String returnUrl;
    static {
        Properties properties=new Properties();
        try {
            InputStream is = AlipayConfig.class.getClassLoader().getResourceAsStream("alipay.properties");
            properties.load(is);
            is.close();
            //属性赋值
            appId=properties.getProperty("appId");
            appPrivateKey=properties.getProperty("appPrivateKey");
            alipayPublicKey=properties.getProperty("alipayPublicKey");
            notifyUrl=properties.getProperty("notifyUrl");
            returnUrl=properties.getProperty("returnUrl");
            System.out.println(appId);
            System.out.println(appPrivateKey);
            System.out.println(alipayPublicKey);
            System.out.println(notifyUrl);
            System.out.println(returnUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
