package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public PageBean<Route> findByPage(int cid, int currentPage, int pageSize, String rname) {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pageBean.setList(list);
        int count = 0;
        count = routeDao.findCount(cid, rname);
        pageBean.setTotalCount(count);
        int totalPage = 0;
        totalPage = count%pageSize == 0?count/pageSize:(count/pageSize + 1);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }
}
