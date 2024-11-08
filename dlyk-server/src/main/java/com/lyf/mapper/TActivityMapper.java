package com.lyf.mapper;

import com.lyf.commons.DataScope;
import com.lyf.model.TActivity;
import com.lyf.model.TUser;
import com.lyf.query.ActivityQuery;
import com.lyf.query.BaseQuery;

import java.util.List;

public interface TActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    @DataScope(tableAlias = "ta",tableField = "owner_id")
    List<TActivity> selectActivityByPage(ActivityQuery activityQuery);

    TActivity selectDetailByPrimaryKey(Integer id);

    int deleteByIds(List<String> idList);

    List<TActivity> selectOngoingActivity();
}