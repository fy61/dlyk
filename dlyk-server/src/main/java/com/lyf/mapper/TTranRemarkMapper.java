package com.lyf.mapper;

import com.lyf.model.TTranRemark;

import java.util.List;

public interface TTranRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTranRemark record);

    int insertSelective(TTranRemark record);

    TTranRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTranRemark record);

    int updateByPrimaryKey(TTranRemark record);

    List<TTranRemark> selectTranRemarkPage(Integer tranId);
}