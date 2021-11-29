package org.bravo.gaia.commons.base;


import org.bravo.gaia.commons.util.ToStringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * pager
 * Created by dq on 2020/11/25
 */
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = -8685223070239741295L;
    /**
     * 当前页
     */
    private Integer pageNum;

    /**
     * 分页大小
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Integer totalCount;

    /**
     * 是否最后一页
     */
    private Boolean isStop;

    /**
     * 游标查询id
     */
    private String cursorId;

    /**
     * 当前页的记录
     */
    private List<T> data;

    public PageBean() {
    }

    public PageBean(PageBean<?> page) {
        this(page.getPageNum(), page.getTotalCount(), page.getPageSize());
    }

    public PageBean(Integer pageNum, Integer pageSize, Integer totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getStop() {
        return isStop;
    }

    public void setStop(Boolean stop) {
        isStop = stop;
    }

    public String getCursorId() {
        return cursorId;
    }

    public void setCursorId(String cursorId) {
        this.cursorId = cursorId;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 获取总页数
     */
    public Integer getAllPageNum() {
        if (totalCount != null && pageSize != null && pageSize != 0) {
            return (this.totalCount - 1) / this.pageSize + 1;
        }
        return null;
    }

    @Override
    public String toString() {
        return ToStringUtil.obj2String(this);
    }
}
