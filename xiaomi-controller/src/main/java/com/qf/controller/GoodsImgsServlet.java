package com.qf.controller;

import cn.hutool.core.util.StrUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author youta
 * @version v1.0
 * @project java2402_xiaomi
 * @package com.qf.controller
 * @company 千锋教育
 * @date 2024/6/14 10:34
 */
@WebServlet(urlPatterns = "/goodsImgs")
public class GoodsImgsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String picture = req.getParameter("picture");
        if (StrUtil.isBlank(picture)) {
            return;
        }
        String path = this.getServletContext().getRealPath("WEB-INF" + File.separator + "images" + File.separator + picture);
        File file = new File(path);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            ServletOutputStream out = resp.getOutputStream();
            byte[] buffer = new byte[1024 * 20];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            fis.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
