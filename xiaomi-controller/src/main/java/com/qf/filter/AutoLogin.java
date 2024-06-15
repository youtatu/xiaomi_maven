package com.qf.filter;

import com.qf.entity.User;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.filter
 * @company 千锋教育
 * @date 2024/6/12 9:14
 */
@WebFilter(urlPatterns = "/index.jsp")
public class AutoLogin implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1.先判断是否已经登录
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            filterChain.doFilter(request, response);
            return;
        }
        //2获取cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userinfo")) {
                    byte[] data = Base64.getDecoder().decode(cookie.getValue());
                    String userinfo = new String(data);
                    String[] arr = userinfo.split("#");
                    try {
                        UserService userService = new UserServiceImpl();
                        user = userService.login(arr[0], arr[1]);
                        request.getSession().setAttribute("user", user);
                    } catch (Exception e) {
                        // 删除cookie
                        cookie.setPath("/");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
