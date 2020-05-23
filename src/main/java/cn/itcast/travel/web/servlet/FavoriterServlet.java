package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.FavoriterService;
import cn.itcast.travel.service.impl.FavoriterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favoriterServlet/*")
public class FavoriterServlet extends BaseServlet {

    private FavoriterService favoriterService = new FavoriterServiceImpl();

    public void isFavoriter(HttpServletRequest request, HttpServletResponse response) {

        String ridStr = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");
        Favorite favorite = null;

        ResultInfo resultInfo = new ResultInfo();
        if (user != null) {
            int rid;
            if (ridStr != null && !"".equals(ridStr)) {
                rid = Integer.parseInt(ridStr);
                favorite = favoriterService.findOneByRidAndUid(rid, user.getUid());
                if (favorite != null) {
                    resultInfo.setFlag(true);
                } else {
                    resultInfo.setFlag(false);
                }
            } else {
                resultInfo.setFlag(false);
            }
        } else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("尚未登录，请登录");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        try {
            objectMapper.writeValue(response.getOutputStream(), resultInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void add(HttpServletRequest request, HttpServletResponse response) {

        String ridStr = request.getParameter("rid");

        User user = (User) request.getSession().getAttribute("user");

        ResultInfo resultInfo = new ResultInfo();
        boolean flag = false;

        if (user != null) {
            if (ridStr != null && !"".equals(ridStr)) {
                int rid;
                rid = Integer.parseInt(ridStr);
                flag = favoriterService.addFavorite(rid, user.getUid());
            } else {
                resultInfo.setErrorMsg("出现错误，请联系管理员");
            }
        } else {
            resultInfo.setErrorMsg("尚未登录，请登录");
        }

        resultInfo.setFlag(flag);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        try {
            objectMapper.writeValue(response.getOutputStream(), resultInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void list(HttpServletRequest request, HttpServletResponse response) {

        //获得参数 currentPage pageSize
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        int currentPage = 1;
        if (currentPageStr != null && !"".equals(currentPageStr)) {
            currentPage = Integer.parseInt(currentPageStr);
        }

        int pageSize = 12;
        if (pageSizeStr != null && !"".equals(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }

        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return;
        }

        PageBean<Route> pageBean = favoriterService.findPageByRid(currentPage, pageSize, user.getUid());

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");

        try {
            objectMapper.writeValue(response.getOutputStream(), pageBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void isLogin(HttpServletRequest request, HttpServletResponse response) {


        User user = (User) request.getSession().getAttribute("user");

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");

        try {
            if (user == null) {
                objectMapper.writeValue(response.getOutputStream(), false);
            } else {
                objectMapper.writeValue(response.getOutputStream(), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
