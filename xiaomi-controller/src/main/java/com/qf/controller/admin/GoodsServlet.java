package com.qf.controller.admin;

import cn.hutool.core.util.StrUtil;
import com.qf.entity.Goods;
import com.qf.controller.BaseServlet;
import com.qf.service.GoodsService;
import com.qf.service.impl.GoodsServiceImpl;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.controller.admin
 * @company 千锋教育
 * @date 2024/6/14 10:02
 */
@WebServlet(urlPatterns = "/admin/goodsservlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 50, fileSizeThreshold = 1024 * 1024 * 50)
public class GoodsServlet extends BaseServlet {
    public String addGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        request.setCharacterEncoding("utf-8");
        String name = request.getParameter("name");
        String typeid = request.getParameter("typeid");
        String pubdate = request.getParameter("pubdate");
        String price = request.getParameter("price");
        String star = request.getParameter("star");
        Part picture = request.getPart("picture");
        String intro = request.getParameter("intro");

        // 校验
        if (StrUtil.isBlank(name) || StrUtil.isBlank(typeid) || StrUtil.isBlank(pubdate) || StrUtil.isBlank(price)
                || StrUtil.isBlank(star) || StrUtil.isBlank(intro) || picture == null) {
            return null;
        }

        // 处理图片
        String basePath = request.getSession().getServletContext().getRealPath("WEB-INF" + File.separator + "images");
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String submittedFileName = picture.getSubmittedFileName();
        String fileName = null;
        if (!submittedFileName.equals("")) {
            // 保存文件
            fileName = UUID.randomUUID().toString().replace("-", "").substring(16);
            // 后缀
            int pos = submittedFileName.lastIndexOf(".");
            if (pos >= 0) {
                String ext = submittedFileName.substring(pos);
                fileName += ext;
            }
            picture.write(basePath + File.separator + fileName);
            // 删除缓存
            picture.delete();
        }
        try {
            // 创建业务对象
            GoodsService goodsService = new GoodsServiceImpl();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Goods goods = new Goods(null, name, sdf.parse(pubdate), fileName, Integer.parseInt(price), Integer.parseInt(star), intro, Integer.parseInt(typeid), null);
            goodsService.add(goods);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
