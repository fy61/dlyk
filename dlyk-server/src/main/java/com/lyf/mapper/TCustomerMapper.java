package com.lyf.mapper;

import com.lyf.model.TCustomer;
import com.lyf.result.TimeValue;

import java.util.List;

public interface TCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCustomer record);

    int insertSelective(TCustomer record);

    TCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomer record);

    int updateByPrimaryKey(TCustomer record);

    List<TCustomer> selectCustomerPage();

    List<TCustomer> selectCustomerByExcel(List<String> idList);

    Integer selectByCount();

    List<TimeValue> selectCustomerByDay();
}