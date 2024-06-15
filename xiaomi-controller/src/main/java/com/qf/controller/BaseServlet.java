package com.qf.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/11 11:28
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //分发处理
        String methodName = request.getParameter("method");
        //1获取类对象
        Class<?> class1 = this.getClass();
        try {
            //2获取方法
            Method method = class1.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //3反射调用方法
            if (method != null) {
                String url= (String) method.invoke(this,request,response);
                if(url!=null&&!url.equals("")){
                    if(url.startsWith("redirect")){
                        //重定向
                        String s1 = url.substring(url.lastIndexOf(":") + 1);
                        response.sendRedirect(request.getContextPath()+s1);
                    }else{
                        //转发
                        request.getRequestDispatcher(url).forward(request,response);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
