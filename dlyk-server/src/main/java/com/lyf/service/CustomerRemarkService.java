package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TCustomerRemark;

public interface CustomerRemarkService {
    PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId);
}
