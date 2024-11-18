package com.lyf.mapper;

import com.lyf.commons.DataScope;
import com.lyf.model.TActivityRemark;
import com.lyf.query.ActivityRemarkQuery;
import com.lyf.query.BaseQuery;
import lombok.Data;

import java.util.List;

public interface TActivityRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    @DataScope(tableAlias = "tar",tableField = "create_by")
    List<TActivityRemark> selectActivityRemarkByPage(ActivityRemarkQuery activityRemarkQuery);

    int updateByActivityId(Integer clueRemarkId);
}