package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Integer findCount(int cid, String rname) {
        String sql = "select count(*) from tab_route where cid = ?";
        StringBuffer sb = new StringBuffer(sql);
        List list = new ArrayList();
        list.add(cid);
        if (rname != null && !"".equals(rname)) {
            sb.append(" and rname like ?");
            list.add("%"+rname+"%");
        }
        Integer count = jdbcTemplate.queryForObject(sb.toString(), Integer.class, list.toArray());
        return count;
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = "select * from tab_route where cid = ? ";
        StringBuffer sb = new StringBuffer(sql);
        List list1 = new ArrayList();
        list1.add(cid);
        if (rname != null && !"".equals(rname)) {
            sb.append(" and rname like ?");
            list1.add("%"+rname+"%");
        }
        sb.append(" limit ?,?");
        list1.add(start);
        list1.add(pageSize);
        List<Route> list = jdbcTemplate.query(sb.toString(), new BeanPropertyRowMapper<>(Route.class), list1.toArray());
        return list;
    }

    @Override
    public Route findOne(Integer rid) {

        String sql = "select * from tab_route where rid = ?";
        Route route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        return route;
    }
}
