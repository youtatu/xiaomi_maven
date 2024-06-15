package com.qf.controller;

import cn.hutool.core.util.StrUtil;
import com.qf.entity.Cart;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import com.qf.service.impl.CartServiceImpl;
import com.qf.service.impl.GoodsServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/12 15:01
 */
@WebServlet(urlPatterns = "/cartservlet")
public class CartServlet extends BaseServlet {
    public String addCart(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        int num = 1;
        if (StrUtil.isBlank(goodsId)) {
            return null;
        }
        if (StrUtil.isNotBlank(number)) {
            num = Integer.parseInt(number);
            if (num < 1 || num > 5) {
                num = 1;
            }
        }
        try {
            // 添加购物车
            CartService cartService = new CartServiceImpl();
            Cart cart = cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));
            GoodsService goodService = new GoodsServiceImpl();
            Goods goods = goodService.findById(Integer.parseInt(goodsId));
            if (cart == null) {
                // 添加
                cart = new Cart(user.getId(), Integer.parseInt(goodsId), num, num * goods.getPrice(), null);
                cartService.add(cart);
            } else {
                // 修改
                cart.setNum(cart.getNum() + num);
                cart.setMoney(cart.getNum() * goods.getPrice());
                cartService.update(cart);
            }
            return "redirect:/cartSuccess.jsp";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("msg", "添加购物车失败" + e.getMessage());
            return "/message.jsp";
        }
    }

    public String getCart(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        try {
            // 创建业务对象
            CartService cartService = new CartServiceImpl();
            List<Cart> cartList = cartService.findByUid(user.getId());
            request.setAttribute("cartList", cartList);
            return "/cart.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "查看购物车失败");
            return "/message.jsp";
        }
    }

    /**
     * ajax修改购物车信息
     */
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        int num = 1;
        if (StrUtil.isBlank(goodsId)) {
            return null;
        }
        if (StrUtil.isNotBlank(number)) {
            num = Integer.parseInt(number);
        }
        // 创建业务对象
        CartService cartService = new CartServiceImpl();
        Cart cart = cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));
        // 调用业务方法
        GoodsService goodService = new GoodsServiceImpl();
        Goods goods = goodService.findById(Integer.parseInt(goodsId));
        if (num == 0) {
            // 删除
            cartService.delete(user.getId(), Integer.parseInt(goodsId));
        } else if (num == 1 || num == -1) {
            // 修改
            cart.setNum(cart.getNum() + num);
            cart.setMoney(cart.getNum() * goods.getPrice());
            cartService.update(cart);
        }
        return null;
    }

    /**
     * ajax清空购物车
     */
    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        CartService cartService = new CartServiceImpl();
        cartService.deleteByUid(user.getId());
        return null;
    }
}