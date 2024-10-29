package com.lyf.mapper;

import com.lyf.commons.DataScope;
import com.lyf.model.TUser;
import com.lyf.query.BaseQuery;

import java.util.List;

public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByLoginAct(String username);

    @DataScope(tableAlias = "tu",tableField = "id")
    List<TUser> selectUserByPage(BaseQuery query);

    TUser selectDetailById(Integer id);

    int deleteByIds(List<String> idList);

    List<TUser> selectByOwner();
}