package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TClue;

import java.io.InputStream;

public interface ClueService {
    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(InputStream inputStream);
}
