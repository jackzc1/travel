package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface FavoriterService {
    Favorite findOneByRidAndUid(int rid, int uid);

    boolean addFavorite(int rid, int uid);

    PageBean<Route> findPageByRid(int currentPage, int pageSize, int uid);
}
