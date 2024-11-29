package com.lyf.service;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TActivity;
import com.lyf.query.ActivityQuery;

import java.util.List;

public interface ActivityService {
    PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery);

    int saveActivity(ActivityQuery activityQuery);

    int updateActivity(ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int batchDelActivityIds(List<String> idList);

    int delActivityById(Integer id);

    List<TActivity> getOngoingActivity();

    List<TActivity> getActivityByAll();
}
