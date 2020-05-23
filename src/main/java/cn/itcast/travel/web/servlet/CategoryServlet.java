package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.transform.Rotate;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        List<Category> all = categoryService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            response.setContentType("application/json;charset=utf-8");
            objectMapper.writeValue(response.getOutputStream(), all);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
