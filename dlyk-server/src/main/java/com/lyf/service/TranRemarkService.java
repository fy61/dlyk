package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TTranRemark;
import com.lyf.query.TranRemarkQuery;

public interface TranRemarkService {
    PageInfo<TTranRemark> getTranRemarkByPage(Integer current, Integer tranId);

    int saveTranRemark(TranRemarkQuery tranRemarkQuery);
}
