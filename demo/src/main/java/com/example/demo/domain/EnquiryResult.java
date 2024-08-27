package com.example.demo.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnquiryResult<T> {
    private List<T> dataList;
    private long totalCount;
    private int totalPageCount;
    private Map<String, BigDecimal> aggregationResults = new HashMap<>();


    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Map<String, BigDecimal> getAggregationResults() {
        return aggregationResults;
    }

    public void setAggregationResults(Map<String, BigDecimal> aggregationResults) {
        this.aggregationResults = aggregationResults;
    }
}
