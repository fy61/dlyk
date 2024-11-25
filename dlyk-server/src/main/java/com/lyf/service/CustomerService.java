package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TCustomer;
import com.lyf.query.CustomerQuery;
import com.lyf.result.CustomerExcel;

import java.util.List;

public interface CustomerService {
    Boolean convertCustomer(CustomerQuery customerQuery);

    PageInfo<TCustomer> getCustomerByPage(Integer current);

    List<CustomerExcel> getCustomerByExcel(List<String> idList);
}
