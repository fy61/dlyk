package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TActivityRemark;
import com.lyf.model.TUser;
import com.lyf.query.ActivityRemarkQuery;
import com.lyf.result.R;
import com.lyf.service.ActivityRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    @PostMapping("/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(value = "Authorization") String token) {
        //axios提交post请求，提交过来的是json数据，使用@RequestBody注解接收
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.saveActivityRemark(activityRemarkQuery);
        return save >= 1 ? R.OK( ) : R.FALL();
    }

    @GetMapping("/api/activity/remark")
    public R activityRemarkPage(@RequestParam(value = "current" ,required = false) Integer current,
                                @RequestParam(value = "activityId") Integer activityId ){
        //required = false 可以不传,也可以传
        ActivityRemarkQuery query = new ActivityRemarkQuery();
        query.setActivityId(activityId);

        if (current == null) {
            current = 1;
        }
        PageInfo<TActivityRemark> pageInfo = activityRemarkService.getActivityRemarkByPage(current,query);
        return R.OK(pageInfo);
    }

    @GetMapping("/api/activity/remark/{id}")
    public R activityRemarkPage(@PathVariable(value = "id") Integer id) {
        TActivityRemark tActivityRemark = activityRemarkService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }

    @PutMapping("/api/activity/remark")
    public R editActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(value = "Authorization") String token) {
        //axios提交post请求，提交过来的是json数据，使用@RequestBody注解接收
        activityRemarkQuery.setToken(token);
        int update = activityRemarkService.updateActivityRemark(activityRemarkQuery);
        return update >= 1 ? R.OK( ) : R.FALL();
    }

    @DeleteMapping("/api/activity/remark/{id}")
    public R delActivityRemark(@PathVariable(value = "id") Integer id){
        int del = activityRemarkService.delActivityRemarkById(id);
        return del >= 1 ? R.OK( ) : R.FALL();
    }

}
