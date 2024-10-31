package com.lyf.service;


import com.github.pagehelper.PageInfo;
import com.lyf.model.TActivityRemark;
import com.lyf.query.ActivityQuery;
import com.lyf.query.ActivityRemarkQuery;

public interface ActivityRemarkService {
    int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current, ActivityRemarkQuery activityRemarkQuery);

    TActivityRemark getActivityRemarkById(Integer id);
}
