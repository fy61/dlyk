package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.mapper.TCustomerRemarkMapper;
import com.lyf.model.TCustomerRemark;
import com.lyf.query.CustomerRemarkQuery;
import com.lyf.service.CustomerRemarkService;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveCustomerRemark(CustomerRemarkQuery customerRemarkQuery) {
        TCustomerRemark customerRemark = new TCustomerRemark();

        //把前端提交过来的参数数据对象CustomerRemarkQuery复制到TCustomerRemark对象中
        //Spring框架有个工具类BeanUtils可以进行对象的复制,复制的条件要求是：两个对象的字段名要相同，字段的类型也相同，这样才可以复制
        BeanUtils.copyProperties(customerRemarkQuery,customerRemark);

        //解析jwt得到userId
        Integer loginUserId = JWTUtils.parseUserFromJWT(customerRemarkQuery.getToken()).getId();

        customerRemark.setCreateTime(new Date()); //创建时间
        customerRemark.setCreateBy(loginUserId); //创建人id

        return tCustomerRemarkMapper.insertSelective(customerRemark);

    }
}
