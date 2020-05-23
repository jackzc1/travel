package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.JedisUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryDaoImpl implements CategoryDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    public List<Category> findAll() {

        Jedis jedis = JedisUtil.getJedis();
        /*Set<String> categorys = jedis.zrange("categorys", 0, -1);*/
        Set<Tuple> categorys = jedis.zrangeWithScores("categorys", 0, -1);

        List<Category> cs = null;
        if (categorys != null && categorys.size() != 0) {
            System.out.println("redis查询");
            cs = new ArrayList<>();
            //redis缓存中有,进行数据的拼接
            for (Tuple category : categorys) {
                Category c = new Category();
                c.setCid((int) category.getScore());
                c.setCname(category.getElement());
                cs.add(c);
            }

        } else {
            System.out.println("数据库查询");
            String sql = "select * from tab_category";
            cs = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
            //把cs存入redis中
            for (Category c : cs) {
                jedis.zadd("categorys", c.getCid(), c.getCname());
            }
        }

        return cs;
    }

    @Override
    public Category findByCid(Integer cid) {
        String sql = "select * from tab_category where cid = ?";
        Category category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Category.class), cid);
        return category;
    }

}
