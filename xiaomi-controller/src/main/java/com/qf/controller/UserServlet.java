package com.qf.controller;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.qf.entity.Address;
import com.qf.entity.R;
import com.qf.entity.User;
import com.qf.service.AddressService;
import com.qf.service.UserService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.service.impl.UserServiceImpl;
import com.qf.util.RandUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/11 11:05
 */
@WebServlet(urlPatterns = "/userservlet")
public class UserServlet extends BaseServlet {

    //注册
    public String regist(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("用户注册");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        //非空校验
        if (StrUtil.isBlank(username)) {
            request.setAttribute("registerMsg", "用户名不能为空");
            return "/register.jsp";
        }
        if (StrUtil.isBlank(password)) {
            request.setAttribute("registerMsg", "密码不能为空");
            return "/register.jsp";
        }
        if (!password.equals(repassword)) {
            request.setAttribute("registerMsg", "两次密码不一致");
            return "/register.jsp";
        }
        if (StrUtil.isBlank(email)) {
            request.setAttribute("registerMsg", "邮箱不能为空");
            return "/register.jsp";
        }
        if (StrUtil.isBlank(gender)) {
            request.setAttribute("registerMsg", "性别不能为空");
            return "/register.jsp";
        }
        String reg = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!email.matches(reg)) {
            request.setAttribute("registerMsg", "邮箱格式不正确");
            return "/register.jsp";
        }
        //创建业务对象
        UserService userService = new UserServiceImpl();
        try {
            User user = new User(null, username, password, email, gender, 0, 1, RandUtils.createCode());
            userService.regist(user);
            return "redirect:/registerSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("registerMsg", "注册失败:" + e.getMessage());
            return "/register.jsp";
        }
    }

    //登录
    public String login(HttpServletRequest request, HttpServletResponse response) {

        //获取数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vcode = request.getParameter("vcode");
        String auto = request.getParameter("auto");
        if (StrUtil.isBlank(username)) {
            request.setAttribute("msg", "用户名不能为空");
            return "/login.jsp";
        }
        if (StrUtil.isBlank(password)) {
            request.setAttribute("msg", "密码不能为空");
            return "/login.jsp";
        }
        if (StrUtil.isBlank(vcode)) {
            request.setAttribute("msg", "验证码不能为空");
            return "/login.jsp";
        }
        //判断验证码是否正确
        String servercode = (String) request.getSession().getAttribute("servercode");
        if (!vcode.equalsIgnoreCase(servercode)) {
            request.setAttribute("msg", "验证码错误");
            return "/login.jsp";
        }
        //创建业务对象
        try {
            UserService userService = new UserServiceImpl();
            User user = userService.login(username, password);
            request.getSession().setAttribute("user", user);
            // 判断是否勾选了auto
            if(auto!=null){
                System.out.println("勾选了");
                String userinfo = username + "#" + password;
                userinfo = Base64.getEncoder().encodeToString(userinfo.getBytes());
                Cookie cookie = new Cookie("userinfo", userinfo);
                cookie.setPath("/");
                cookie.setMaxAge(60*60*24*14);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            return "redirect:/index.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", e.getMessage());
            return "/login.jsp";
        }
    }

    //验证用户名是否存在
    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        if (StrUtil.isBlank(username)) {
            return null;
        }
        //创建业务对象
        UserService userService = new UserServiceImpl();
        boolean b = userService.checkUserName(username);
        R r = null;
        if (b) {//存在
            r = R.success();
        } else {//不存在
            r = R.error("");
        }
        String json = JSON.toJSONString(r);
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.getWriter().write(json);
        return null;
    }

    //返回验证码
    public String code(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //生成验证码
        LineCaptcha lineCaptcha = new LineCaptcha(100, 35, 4, 20);
        //放入域中
        System.out.println(lineCaptcha.getCode());
        request.getSession().setAttribute("servercode", lineCaptcha.getCode());
        //返回
        lineCaptcha.write(response.getOutputStream());
        return null;
    }

    // 退出
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        // 1.删除session中的用户
        request.getSession().removeAttribute("user");
        // 2session失效
        request.getSession().invalidate();
        // 3删除自动登录的cookie
        Cookie cookie = new Cookie("userinfo", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/login.jsp";
    }

    // 获取地址
    public String getAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        R r = null;
        try {
            // 创建业务对象
            AddressService addressService = new AddressServiceImpl();
            List<Address> addList = addressService.quaryByUid(user.getId());
            r = R.success(addList);
        } catch (Exception e) {
            r = R.error("获取地址失败" + e.getMessage());
        }
        String jsonString = JSON.toJSONString(r);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
        return null;
    }

    // 添加地址
    public String addAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        // 获取参数
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        if (StrUtil.isBlank(name)) {
            return "redirect:/self_info.jsp";
        }
        if (StrUtil.isBlank(phone)) {
            return "redirect:/self_info.jsp";
        }
        if (StrUtil.isBlank(detail)) {
            return "redirect:/self_info.jsp";
        }
        try {
            // 创建业务对象
            AddressService addressService = new AddressServiceImpl();
            Address address = new Address(null,detail,name,phone,user.getId(),0);
            addressService.add(address);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/self_info.jsp";
    }

    // 删除地址
    public String deleteAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        // 获取参数
        String id = request.getParameter("id");

        if (StrUtil.isBlank(id)) {
            return null;
        }
        R r = null;
        try {
            // 创建业务对象
            AddressService addressService = new AddressServiceImpl();
            addressService.delete(Integer.parseInt(id));
            r = R.success();
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error("删除失败" + e.getMessage());
        }
        String jsonString = JSON.toJSONString(r);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
        return null;
    }
    // 修改地址
    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        // 获取参数
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        if (StrUtil.isBlank(name)) {
            return "redirect:/self_info.jsp";
        }
        if (StrUtil.isBlank(phone)) {
            return "redirect:/self_info.jsp";
        }
        if (StrUtil.isBlank(detail)) {
            return "redirect:/self_info.jsp";
        }
        R r = null;
        try {
            // 创建业务对象
            AddressService addressService = new AddressServiceImpl();
            Address address = new Address(Integer.parseInt(id),detail,name,phone,user.getId(),0);
            addressService.update(address);
            r = R.success();
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error("修改失败" + e.getMessage());
        }
        return "redirect:/self_info.jsp";
    }

    // 默认地址
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        // 获取参数
        String id = request.getParameter("id");

        R r = null;
        try {
            // 创建业务对象
            AddressService addressService = new AddressServiceImpl();
            addressService.defaultAddress(Integer.parseInt(id),user.getId());
            r = R.success();
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error("设置默认失败" + e.getMessage());
        }
        String jsonString = JSON.toJSONString(r);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(jsonString);
        return null;
    }
}

