package com.qf.controller;

import cn.hutool.core.util.StrUtil;
import com.qf.entity.*;
import com.qf.service.AddressService;
import com.qf.service.CartService;
import com.qf.service.OrderService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.service.impl.CartServiceImpl;
import com.qf.service.impl.OrderServiceImpl;
import com.qf.util.RandUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/11 11:26
 */
@WebServlet(urlPatterns = "/orderservlet")
public class OrderServlet extends BaseServlet {
    public String addOrder() {
        return "";
    }

    public String findOrder() {
        return "";
    }

    // 订单预览
    public String getOrderView(HttpServletRequest request, HttpServletResponse response) {
        // 判断用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        try {
            // 查询购物车信息
            CartService cartService = new CartServiceImpl();
            List<Cart> cartList = cartService.queryByUid(user.getId());
            if (cartList == null || cartList.isEmpty()) {
                request.setAttribute("msg", "购物车为空");
                return "/message.jsp";
            }
            // 查询收货地址
            AddressService addressService = new AddressServiceImpl();
            List<Address> addList = addressService.quaryByUid(user.getId());
            // 放入域中
            request.setAttribute("cartList", cartList);
            request.setAttribute("addList", addList);
            // 转发
            return "/order.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "结账预览失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    // 提交订单
    public String addOrder(HttpServletRequest request, HttpServletResponse response) {
        // 判断当前是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/self_info.jsp";
        }
        // 获取参数
        String aid = request.getParameter("aid");

        // 创建业务对象
        CartService cartService = new CartServiceImpl();
        List<Cart> cartList = cartService.queryByUid(user.getId());
        if (cartList == null || cartList.isEmpty()) {
            request.setAttribute("msg", "购物车为空");
            return "/message.jsp";
        }
        OrderService orderService = new OrderServiceImpl();

        try {
            // 生成订单号
            String orderId = RandUtils.createOrderId();
            // 遍历购物车
            Integer sum = 0;
            for (Cart cart : cartList) {
                sum += cart.getMoney();
            }
            Order order = new Order(orderId, user.getId(), sum, "1", new Date(), Integer.parseInt(aid),null);
            orderService.add(order, cartList);
            request.setAttribute("order", order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "提交订单失败");
            return "/message.jsp";
        }
    }

    // 查看订单
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
        // 判断当前是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        try {
            // 创建业务对象
            OrderService orderService = new OrderServiceImpl();
            List<Order> orderList = orderService.findByUid(user.getId());
            // 放入域中
            request.setAttribute("orderList", orderList);
            return "/orderList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","获取订单列表失败");
            return "/message.jsp";
        }
    }

    // 查看订单详情
    public String getOrderDetail(HttpServletRequest request, HttpServletResponse response) {
        // 判断当前是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        String oid = request.getParameter("oid");
        if(StrUtil.isBlank(oid)){
            request.setAttribute("msg","获取订单失败");
            return "/message.jsp";
        }

        try {
            // 创建业务对象
            OrderService orderService = new OrderServiceImpl();
            Order order = orderService.findByOid(oid);
            List<OrderDetail> orderDetails = orderService.findOrderDetail(oid);
            // 放入域中
            request.setAttribute("order",order);
            request.setAttribute("orderDetails", orderDetails);
            return "/orderDetail.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","获取订单列表失败");
            return "/message.jsp";
        }
    }
}

