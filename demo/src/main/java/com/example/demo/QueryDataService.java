package com.example.demo;

import com.example.demo.constant.SortDirection;
import com.example.demo.domain.DynamicRequest;
import com.example.demo.domain.EnquiryRequest;
import com.example.demo.domain.EnquiryResult;
import com.example.demo.port.in.QueryDataPortIn;
import com.example.demo.port.out.QueryDataPortOut;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class QueryDataService implements QueryDataPortIn {

    private QueryDataPortOut queryDataPortOut;

    public QueryDataService(QueryDataPortOut queryDataPortOut) {
        this.queryDataPortOut = queryDataPortOut;
    }

    @Override
    @Transactional
    public EnquiryResult<Object> queryData(DynamicRequest dynamicqueryV2, int rowPerPage, int pageNo, String sortBy, SortDirection sortDirection) {
        EnquiryResult<Object> enquiryRequest = this.queryDataPortOut.queryData(dynamicqueryV2,rowPerPage,pageNo,sortBy,sortDirection);

        return null;
    }
}
