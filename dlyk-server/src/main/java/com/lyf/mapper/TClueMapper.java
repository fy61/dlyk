package com.lyf.mapper;

import com.lyf.model.TClue;
import com.lyf.query.BaseQuery;
import com.lyf.result.NameValue;
import com.lyf.result.TimeValue;

import java.util.List;

public interface TClueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClue record);

    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    List<TClue> selectClueByPage(BaseQuery build);

    void saveClue(List<TClue> tClueList);

    int selectByCount(String phone);

    TClue selectDetailById(Integer id);

    int deleteByIds(List<String> idList);

    Integer selectClueByCount();

    List<NameValue> selectBySource();

    List<TimeValue> selectClueByDay();
}