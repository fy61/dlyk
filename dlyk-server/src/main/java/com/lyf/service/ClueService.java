package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TClue;
import com.lyf.query.ClueQuery;

import java.io.InputStream;
import java.util.List;

public interface ClueService {
    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(InputStream inputStream,String token);

    Boolean checkPhone(String phone);

    int saveClue(ClueQuery clueQuery);

    TClue getClueById(Integer id);

    int updateClue(ClueQuery clueQuery);

    int batchDelClueIds(List<String> idList);

    int delClueById(Integer id);
}
