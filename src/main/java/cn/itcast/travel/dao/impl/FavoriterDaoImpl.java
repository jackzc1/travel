package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriterDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

public class FavoriterDaoImpl implements FavoriterDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findOneByRidAndUid(int rid, int uid) {
        String sql = "select * from tab_favorite where rid = ? and uid = ?";
        Favorite favorite = null;
        try {
            favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            return null;
        }
        return favorite;
    }

    @Override
    public List<Favorite> findByRid(Integer rid) {
        String sql = "select * from tab_favorite where rid = ?";
        List<Favorite> favorites = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Favorite.class), rid);
        return favorites;
    }

    @Override
    public boolean addFavorite(int rid, int uid) {
        String sql = "insert into tab_favorite values(?,?,?)";
        try {
            jdbcTemplate.update(sql, rid, new Date(), uid);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Route> findPageByRid(int currentPage, int pageSize, int uid) {
        String sql = "select * from tab_favorite f,tab_route r where f.rid = r.rid and uid = ? limit ?,?";
        List<Route> routes = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Route.class), uid, (currentPage - 1) * pageSize, pageSize);
        return routes;
    }

    @Override
    public Integer countByUid(int uid) {
        String sql = "select count(*) from tab_favorite f,tab_route r where f.rid = r.rid and uid = ?";
        Integer query = jdbcTemplate.queryForObject(sql, Integer.class, uid);
        return query;
    }
}
