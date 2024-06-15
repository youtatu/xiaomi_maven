package com.qf.controller;

import cn.hutool.core.util.StrUtil;
import com.qf.entity.Goods;
import com.qf.entity.PageBean;
import com.qf.service.GoodsService;
import com.qf.service.impl.GoodsServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/12 11:19
 */
@WebServlet(urlPatterns = "/goodsservlet")
public class GoodsServlet extends BaseServlet {
    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) {

        String pageNum = request.getParameter("pageNum");
        String pageSize = request.getParameter("pageSize");
        int pn = 1, ps = 8;
        try {
            if (StrUtil.isNotBlank(pageNum)) {
                pn = Integer.parseInt(pageNum);
                if (pn < 1) {
                    pn = 1;
                }
            }
            if (StrUtil.isNotBlank(pageSize)) {
                ps = Integer.parseInt(pageSize);
                if (ps < 1) {
                    ps = 8;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        String typeId = request.getParameter("typeId");
        String name = request.getParameter("name");
        StringBuilder builder = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        // select * from tb_goods where typeid=? and name like ?
        if (StrUtil.isNotBlank(typeId)) {
            builder.append("and typeid=?");
            params.add(Integer.parseInt(typeId));
        }
        if (StrUtil.isNotBlank(name)) {
            builder.append("and name like ?");
            params.add("%" + name + "%");
        }
        if (builder.length() > 0) {
            builder.replace(0, 3, "where");
        }
        try {
            // 创建业务对象
            GoodsService goodsService = new GoodsServiceImpl();
            PageBean<Goods> pageBean = goodsService.findByPage(pn, ps, builder.toString(), params);
            // 放入域中
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("typeId", typeId);
            request.setAttribute("name", name);
            return "/goodsList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index.jsp";
    }

    public String getGoodsById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (StrUtil.isBlank(id)) {
            return null;
        }
        try {
            // 创建业务对象
            GoodsService goodsService = new GoodsServiceImpl();
            // 调用方法
            Goods goods = goodsService.findById(Integer.parseInt(id));
            // 放入域中
            request.setAttribute("goods", goods);
            // 转发
            return "/goodsDetail.jsp";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "redirect:/index.jsp";
    }
}
