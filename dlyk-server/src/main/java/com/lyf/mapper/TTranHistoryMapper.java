package com.lyf.mapper;

import com.lyf.model.TTranHistory;

import java.util.List;

public interface TTranHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTranHistory record);

    int insertSelective(TTranHistory record);

    TTranHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTranHistory record);

    int updateByPrimaryKey(TTranHistory record);

    List<TTranHistory> selectByTranId(Integer tranId);
}