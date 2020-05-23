package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface RegisterService {
    boolean register(User user);

    boolean activeUser(String code);

    User findUser(User user);
}
