package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TClueRemark;
import com.lyf.query.ClueRemarkQuery;

public interface ClueRemarkService {
    int saveClueRemark(ClueRemarkQuery clueRemarkQuery);

    PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery query);
}
