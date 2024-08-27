package com.example.demo.domain;

import com.example.demo.constant.SortDirection;

public class EnquiryRequest<T> {

    private T filter;
    private int rowsPerPage;
    private int pageNo;
    private String sortBy;
    private SortDirection sortDirection;

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String toString() {
        return "EnquiryRequest{" +
                "filter=" + filter +
                ", rowsPerPage=" + rowsPerPage +
                ", pageNo=" + pageNo +
                ", sortBy='" + sortBy + '\'' +
                ", sortDirection=" + sortDirection +
                '}';
    }
}
