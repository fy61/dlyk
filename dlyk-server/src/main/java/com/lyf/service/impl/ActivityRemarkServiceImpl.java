package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.mapper.TActivityRemarkMapper;
import com.lyf.model.TActivityRemark;
import com.lyf.model.TUser;
import com.lyf.query.ActivityQuery;
import com.lyf.query.ActivityRemarkQuery;
import com.lyf.query.BaseQuery;
import com.lyf.service.ActivityRemarkService;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private TActivityRemarkMapper tActivityRemarkMapper;

    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();

        //把ActivityRemarkQuery对象里面的属性数据复制到TActivityRemark对象里面去(复制要求：两个对象的属性名相同，属性类型要相同，这样才能复制)
        BeanUtils.copyProperties(activityRemarkQuery,tActivityRemark);

        tActivityRemark.setCreateTime(new Date());//创建时间

        //登录人的id
        Integer id = JWTUtils.parseUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setCreateBy(id);

        return tActivityRemarkMapper.insertSelective(tActivityRemark);
    }

    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current , ActivityRemarkQuery activityRemarkQuery) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TActivityRemark> list = tActivityRemarkMapper.selectActivityRemarkByPage(activityRemarkQuery);
        // 3.封装分页数据到PageInfo
        PageInfo<TActivityRemark> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TActivityRemark getActivityRemarkById(Integer id) {
        return tActivityRemarkMapper.selectByPrimaryKey(id);
    }
}
