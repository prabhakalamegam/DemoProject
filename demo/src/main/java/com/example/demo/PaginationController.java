package com.example.demo;

import com.example.demo.domain.DynamicRequest;
import com.example.demo.domain.EnquiryRequest;
import com.example.demo.domain.EnquiryResult;
import com.example.demo.port.in.QueryDataPortIn;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/page")
@RestController
public class PaginationController {

    private QueryDataPortIn queryDataPortIn;

    public PaginationController(QueryDataPortIn queryDataPortIn) {
        super();
        this.queryDataPortIn = queryDataPortIn;
    }

    @PostMapping("/{dominName}/query")
    public EnquiryRequest<Object> pagination(@PathVariable String domainName, @RequestBody EnquiryRequest<DynamicRequest> enquiryReq){

        String searchId = "search-" + domainName;
        enquiryReq.getFilter().setSearchId(searchId);
        EnquiryResult<Object> objectEnquiryRequest = queryDataPortIn.queryData(enquiryReq.getFilter(),enquiryReq.getRowsPerPage(),
                enquiryReq.getPageNo(),enquiryReq.getSortBy(),enquiryReq.getSortDirection());

        return null;
    }
}
