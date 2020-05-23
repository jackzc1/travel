package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface RegisterDao {
    User findUserByUserName(String username);

    void insertUser(User user);

    User findUserByCode(String code);

    void active(User user);

    User findUser(User user);
}
