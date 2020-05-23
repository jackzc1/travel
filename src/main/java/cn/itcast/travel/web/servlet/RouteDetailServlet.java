package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteDetailService;
import cn.itcast.travel.service.impl.RouteDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/routeDetail/*")
public class RouteDetailServlet extends BaseServlet {

    private RouteDetailService routeDetailService = new RouteDetailServiceImpl();

    public void findOne(HttpServletRequest request, HttpServletResponse response) {
        String ridTemp = request.getParameter("rid");
        Integer rid = null;
        if (ridTemp != null && !"".equals(ridTemp)) {
            rid = Integer.parseInt(ridTemp);
        } else {
            return;
        }
        Route route = routeDetailService.findOne(rid);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        try {
            objectMapper.writeValue(response.getOutputStream(), route);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
