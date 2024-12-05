package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TTran;
import com.lyf.query.TranQuery;

public interface TranService {
    int saveTran(TranQuery tranQuery);

    PageInfo<TTran> getTranByPage(Integer current);

    TTran getTranById(Integer tranId);
}
