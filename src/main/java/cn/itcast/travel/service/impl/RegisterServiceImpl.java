package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RegisterDao;
import cn.itcast.travel.dao.impl.RegisterDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RegisterService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class RegisterServiceImpl implements RegisterService {

    private RegisterDao registerDao = new RegisterDaoImpl();

    @Override
    public boolean register(User user) {

        //根据username查询数据库中是否存在对应的user
        User user1 = registerDao.findUserByUserName(user.getUsername());
        if (user1 != null) {
            return false;
        }
        user.setStatus("N");
        String uuid = UuidUtil.getUuid();
        user.setCode(uuid);
        registerDao.insertUser(user);
        String content = "黑马旅游网<a href='http://localhost:80/travel/user/activeServlet?code="+user.getCode()+"'>(激活)</a>";
        MailUtils.sendMail(user.getEmail(), content,"激活邮件");
        return true;
    }

    @Override
    public boolean activeUser(String code) {
        User user = registerDao.findUserByCode(code);
        if (user != null) {
            registerDao.active(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findUser(User user) {
        User user1 = null;
        try {
            user1 = registerDao.findUser(user);
            return user1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
