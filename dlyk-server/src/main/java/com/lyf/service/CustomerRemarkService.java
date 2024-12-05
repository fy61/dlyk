package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TCustomerRemark;
import com.lyf.query.CustomerRemarkQuery;

public interface CustomerRemarkService {
    PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId);

    int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery);
}
