package com.lyf.mapper;

import com.lyf.model.TTran;
import com.lyf.result.TimeValue;

import java.math.BigDecimal;
import java.util.List;

public interface TTranMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TTran record);

    int insertSelective(TTran record);

    TTran selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TTran record);

    int updateByPrimaryKey(TTran record);

    BigDecimal selectBySuccessTranAmount();

    BigDecimal selectByTotalTranAmount();

    int selectByTotalTranCount();

    int selectBySuccessTranCount();

    List<TimeValue> selectTranByDay();

    List<TimeValue> selectSuccessTranByDay();

    List<TTran> selectTranPage();

    TTran selectById(Integer tranId);
}