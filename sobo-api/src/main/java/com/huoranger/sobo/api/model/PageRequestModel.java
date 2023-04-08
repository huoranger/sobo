package com.huoranger.sobo.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author huoranger
 * @create 20/7/23
 * @desc
 **/
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class PageRequestModel<T> implements Serializable {
    private static final Integer DEF_PAGE_SIZE = 10;
    private static final Integer DEF_PAGE_NO = 1;

    private Integer pageSize = DEF_PAGE_SIZE;

    private Integer pageNo = DEF_PAGE_NO;

    @Setter
    private T filter;

    public PageRequestModel(Integer pageSize, Integer pageNo, T filter) {
        setPageSize(pageSize);
        setPageNo(pageNo);
        this.filter = filter;
    }

    public PageRequestModel<T> setPageSize(Integer pageSize) {
        if (pageSize <= 0) {
            this.pageSize = DEF_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
        return this;
    }

    public PageRequestModel<T> setPageNo(Integer pageNo) {
        if (pageNo <= 0) {
            this.pageNo = DEF_PAGE_NO;
        } else {
            this.pageNo = pageNo;
        }
        return this;
    }
}