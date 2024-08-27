package com.example.demo.port.in;

import com.example.demo.constant.SortDirection;
import com.example.demo.domain.DynamicRequest;
import com.example.demo.domain.EnquiryRequest;
import com.example.demo.domain.EnquiryResult;

public interface QueryDataPortIn {

    EnquiryResult<Object> queryData(DynamicRequest dynamicqueryV2, int rowPerPage, int pageNo,
                                    String sortBy, SortDirection sortDirection);
}
