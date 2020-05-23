package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;

public interface RouteDetailService {
    Route findOne(Integer rid);
}
