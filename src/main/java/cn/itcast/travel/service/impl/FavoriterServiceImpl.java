package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriterDao;
import cn.itcast.travel.dao.impl.FavoriterDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriterService;

import java.util.List;

public class FavoriterServiceImpl implements FavoriterService {

    private FavoriterDao favoriterDao = new FavoriterDaoImpl();

    @Override
    public Favorite findOneByRidAndUid(int rid, int uid) {
        return favoriterDao.findOneByRidAndUid(rid, uid);
    }

    @Override
    public boolean addFavorite(int rid, int uid) {
        return favoriterDao.addFavorite(rid, uid);
    }

    @Override
    public PageBean<Route> findPageByRid(int currentPage, int pageSize, int uid) {
        PageBean<Route> pageBean = new PageBean<>();
        List<Route> list = favoriterDao.findPageByRid(currentPage, pageSize, uid);
        pageBean.setList(list);
        pageBean.setTotalCount(favoriterDao.countByUid(uid));
        int totalPage = pageBean.getTotalCount()%pageSize == 0?pageBean.getTotalCount()/pageSize:pageBean.getTotalCount()/pageSize+1;
        pageBean.setTotalPage(totalPage);
        pageBean.setPageSize(pageSize);
        pageBean.setCurrentPage(currentPage);
        return pageBean;
    }
}
