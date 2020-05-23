package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.service.RegisterService;
import cn.itcast.travel.service.impl.RegisterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeServlet")
public class ActiveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = (String) req.getParameter("code");
        RegisterService registerService = new RegisterServiceImpl();
        boolean flag = registerService.activeUser(code);
        String msg = null;
        if (flag) {
            //激活成功
            msg = "激活成功,请<a href='login.html'>登录</a>";
        } else {
            msg = "激活失败，请联系管理员";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(msg);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
