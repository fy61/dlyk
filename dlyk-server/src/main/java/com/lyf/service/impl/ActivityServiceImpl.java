package com.lyf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.mapper.TActivityMapper;
import com.lyf.model.TActivity;
import com.lyf.model.TUser;
import com.lyf.query.ActivityQuery;
import com.lyf.query.BaseQuery;
import com.lyf.service.ActivityService;
import com.lyf.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private TActivityMapper tActivityMapper;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        //activityQuery继承了BaseQuery,不需要创建一个空的BaseQuery直接带入就可以
        List<TActivity> list = tActivityMapper.selectActivityByPage(activityQuery);
        // 3.封装分页数据到PageInfo
        PageInfo<TActivity> info = new PageInfo<>(list);
        return info;
    }

    /**
     * 录入市场活动
     *
     * @param activityQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        //把ActivityQuery对象里面的属性数据复制到TActivity对象里面去(复制要求：两个对象的属性名相同，属性类型要相同，这样才能复制)
        BeanUtils.copyProperties(activityQuery, tActivity);

        tActivity.setCreateTime(new Date()); //创建时间

        //登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setCreateBy(loginUserId); //创建人

        return tActivityMapper.insertSelective(tActivity);
    }

    /**
     * 编辑市场活动
     *
     * @param activityQuery
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        //把ActivityQuery对象里面的属性数据复制到TActivity对象里面去(复制要求：两个对象的属性名相同，属性类型要相同，这样才能复制)
        BeanUtils.copyProperties(activityQuery, tActivity);

        tActivity.setEditTime(new Date()); //编辑时间

        //登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setEditBy(loginUserId); //创建人

        return tActivityMapper.updateByPrimaryKeySelective(tActivity);
    }

    /**
     * 根据id查询市场活动详情
     *
     * @param id
     * @return
     */
    @Override
    public TActivity getActivityById(Integer id) {
        return tActivityMapper.selectDetailByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelActivityIds(List<String> idList) {
        return tActivityMapper.deleteByIds(idList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delActivityById(Integer id) {
        return tActivityMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询有效的市场活动
     *
     * @return
     */
    @Override
    public List<TActivity> getOngoingActivity() {
        return tActivityMapper.selectOngoingActivity();
    }

    /**
     * 查询所有的市场活动
     *
     * @return
     */
    @Override
    public List<TActivity> getActivityByAll() {
        return tActivityMapper.selectActivityByAll();
    }

}
