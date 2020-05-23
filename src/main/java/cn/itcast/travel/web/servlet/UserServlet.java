package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RegisterService;
import cn.itcast.travel.service.impl.RegisterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    public void active(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    public void getUserNameServelt(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        ResultInfo resultInfo = new ResultInfo();
        if (user != null) {
            resultInfo.setData(user);
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("尚未登录，请登录");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(resp.getOutputStream(), resultInfo);
    }

    public void loginServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取参数
        Map<String, String[]> map = req.getParameterMap();
        //封装
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service 方法查询user
        RegisterService registerService = new RegisterServiceImpl();
        User user1 = registerService.findUser(user);

        //封装返回对象
        ResultInfo resultInfo = new ResultInfo();
        if (user1 != null) {
            //判断用户账号是否激活
            if ("Y".equals(user1.getStatus())) {
                //激活
                req.getSession().setAttribute("user", user1);
                resultInfo.setFlag(true);
            } else {
                resultInfo.setFlag(false);
                resultInfo.setErrorMsg("该用户尚未激活，请激活");
            }

        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("用户名或密码错误");
        }
        //json
        resp.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(resp.getOutputStream(), resultInfo);
    }

    public void logoutServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使session失效
        req.getSession().invalidate();
        //跳转回登录页
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String check = req.getParameter("check");
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo resultInfo = new ResultInfo();
            //调用方法
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(resultInfo);
            //返回结果
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(s);
            return;
        }
        //获得参数
        Map<String, String[]> map = req.getParameterMap();
        //组装参数
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用方法
        RegisterService registerService = new RegisterServiceImpl();

        boolean flag = registerService.register(user);
        //构造返回结果
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setData(user);
        if (flag) {
            resultInfo.setFlag(true);
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(resultInfo);
        //返回结果
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(s);
    }
}
