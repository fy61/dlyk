package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.mapper.TClueRemarkMapper;
import com.lyf.model.TActivityRemark;
import com.lyf.model.TClueRemark;
import com.lyf.query.ClueRemarkQuery;
import com.lyf.service.ClueRemarkService;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Resource
    private TClueRemarkMapper tClueRemarkMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClueRemark(ClueRemarkQuery clueRemarkQuery) {
        TClueRemark tClueRemark = new TClueRemark();

        //把ActivityRemarkQuery对象里面的属性数据复制到TActivityRemark对象里面去(复制要求：两个对象的属性名相同，属性类型要相同，这样才能复制)
        BeanUtils.copyProperties(clueRemarkQuery,tClueRemark);

        tClueRemark.setCreateTime(new Date());//创建时间

        //登录人的id
        Integer id = JWTUtils.parseUserFromJWT(clueRemarkQuery.getToken()).getId();
        tClueRemark.setCreateBy(id);

        return tClueRemarkMapper.insertSelective(tClueRemark);
    }

    @Override
    public PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery query) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TClueRemark> list = tClueRemarkMapper.selectActivityRemarkByPage(query);
        // 3.封装分页数据到PageInfo
        PageInfo<TClueRemark> info = new PageInfo<>(list);
        return info;
    }
}
