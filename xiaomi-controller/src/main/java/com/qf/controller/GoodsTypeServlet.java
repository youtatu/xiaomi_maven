package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.qf.entity.GoodsType;
import com.qf.entity.R;
import com.qf.service.GoodsTypeService;
import com.qf.service.impl.GoodsTypeServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/12 10:00
 */
@WebServlet(urlPatterns = "/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {
    public String goodstypelist(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        R r = null;
        try {
            List<GoodsType> goodsTypeList = goodsTypeService.findByLevel(1);
            r = R.success(goodsTypeList);
        } catch (Exception e) {
            e.printStackTrace();
            r = R.error("获取商品类别失败" + e.getMessage());
        }
        String jsonString = JSON.toJSONString(r);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonString);
        return null;
    }
}
