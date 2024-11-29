package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.mapper.TCustomerRemarkMapper;
import com.lyf.model.TCustomerRemark;
import com.lyf.service.CustomerRemarkService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Resource
    private TCustomerRemarkMapper tCustomerRemarkMapper;
    @Override
    public PageInfo<TCustomerRemark> getCustomerRemarkByPage(Integer current, Integer customerId) {
        //1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        //2.查询
        List<TCustomerRemark> list = tCustomerRemarkMapper.selectCustomerRemarkPage(customerId);
        //3.封装分页数据到PageInfo
        PageInfo<TCustomerRemark> info = new PageInfo<>(list);

        return info;
    }
}
