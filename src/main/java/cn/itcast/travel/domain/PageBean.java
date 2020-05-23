package cn.itcast.travel.domain;

import java.util.List;

//分页封装的实体类
public class PageBean<T> {

    //总页数
    private Integer totalPage;
    //当前页
    private Integer currentPage;
    //总条数
    private Integer totalCount;
    //每页显示多少条
    private Integer pageSize;
    //每页显示的记录
    private List<T> list;

    public PageBean(Integer totalPage, Integer currentPage, Integer totalCount, Integer pageSize, List<T> list) {
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageBean() {

    }

    public Integer getTotalPage() {

        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
