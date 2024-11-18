package com.lyf.mapper;

import com.lyf.commons.DataScope;
import com.lyf.model.TClueRemark;
import com.lyf.query.ClueRemarkQuery;

import java.util.List;

public interface TClueRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

    @DataScope(tableAlias = "tcr", tableField = "create_by")
    List<TClueRemark> selectActivityRemarkByPage(ClueRemarkQuery query);
}