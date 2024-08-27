package com.example.demo.port.out;

import com.example.demo.constant.SortDirection;
import com.example.demo.domain.DynamicRequest;
import com.example.demo.domain.EnquiryRequest;
import com.example.demo.domain.EnquiryResult;

public interface QueryDataPortOut {

    EnquiryResult<Object> queryData(DynamicRequest dynamicRequest, int rowPerPage,
                                    int pageNo, String sortBy, SortDirection sortDirection);
}
