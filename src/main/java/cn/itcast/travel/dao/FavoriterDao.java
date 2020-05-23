package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface FavoriterDao {
    Favorite findOneByRidAndUid(int rid, int uid);

    List<Favorite> findByRid(Integer rid);

    boolean addFavorite(int rid, int uid);

    List<Route> findPageByRid(int currentPage, int pageSize, int uid);

    Integer countByUid(int uid);
}
