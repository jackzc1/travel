package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteDetailService;

import java.util.List;

public class RouteDetailServiceImpl implements RouteDetailService {

    private RouteDao routeDao = new RouteDaoImpl();

    private RouteImgDao routeImgDao = new RouteImgDaoImpl();

    private SellerDao sellerDao = new SellerDaoImpl();

    private CategoryDao categoryDao = new CategoryDaoImpl();

    private FavoriterDao favoriterDao = new FavoriterDaoImpl();

    @Override
    public Route findOne(Integer rid) {
        Route route = routeDao.findOne(rid);

        List<RouteImg> list = routeImgDao.findByRid(rid);

        if (list != null && list.size() != 0) {
            route.setRouteImgList(list);
        }

        Seller seller = sellerDao.findBySid(route.getSid());

        if (seller != null) {
            route.setSeller(seller);
        }

        Category category = categoryDao.findByCid(route.getCid());
        if (category != null) {
            route.setCategory(category);
        }

        List<Favorite> favorites = favoriterDao.findByRid(rid);

        if (favorites != null && favorites.size() != 0) {
            route.setCount(favorites.size());
        } else {
            route.setCount(0);
        }

        return route;
    }
}
