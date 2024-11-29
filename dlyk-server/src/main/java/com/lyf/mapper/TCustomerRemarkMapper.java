package com.lyf.mapper;

import com.lyf.model.TCustomerRemark;

import java.util.List;

public interface TCustomerRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TCustomerRemark record);

    int insertSelective(TCustomerRemark record);

    TCustomerRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TCustomerRemark record);

    int updateByPrimaryKey(TCustomerRemark record);

    List<TCustomerRemark> selectCustomerRemarkPage(Integer customerId);
}