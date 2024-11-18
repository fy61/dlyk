package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TActivity;
import com.lyf.model.TUser;
import com.lyf.query.ActivityQuery;
import com.lyf.query.UserQuery;
import com.lyf.result.R;
import com.lyf.service.ActivityRemarkService;
import com.lyf.service.ActivityService;
import com.lyf.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @Resource
    private ActivityRemarkService activityRemarkService;


    /**
     * 用户列表分页查询
     *
     * @param current
     * @return
     */
    @GetMapping("/api/activitys")
    public R activityPage(@RequestParam(value = "current", required = false) Integer current, ActivityQuery activityQuery) {
        //required = false 可以不传,也可以传
        if (current == null) {
            current = 1;
        }
        PageInfo<TActivity> pageInfo = activityService.getActivityByPage(current,activityQuery);
        return R.OK(pageInfo);
    }

    @PostMapping("/api/activity")
    public R addActivity(ActivityQuery activityQuery, @RequestHeader(value = "Authorization") String token){
        activityQuery.setToken(token);
        int save =activityService.saveActivity(activityQuery);
        return save >= 1 ? R.OK() : R.FALL();
    }

    @GetMapping(value = "/api/activity/{id}")
    public R loadActivity(@PathVariable(value = "id") Integer id) {
        TActivity tActivity = activityService.getActivityById(id);
        return R.OK(tActivity);
    }

    @PutMapping("/api/activity")
    public R editActivity(ActivityQuery activityQuery, @RequestHeader(value = "Authorization") String token){
        activityQuery.setToken(token);
        int update = activityService.updateActivity(activityQuery);
        return update >= 1 ? R.OK() : R.FALL();
    }

    @DeleteMapping("/api/activity")
    public R batchDelActivity(@RequestParam(value = "ids") String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = activityService.batchDelActivityIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FALL();
    }

    @DeleteMapping("/api/activity/{id}")
    public R delActivity(@PathVariable(value = "id")Integer id){
        int delRemark = activityRemarkService.deleteActivityRemarkByActivityId(id);
        if(delRemark >=1){
            int del = activityService.delActivityById(id);
            return (del >= 1 ? 1 : 0) + (delRemark >= 1 ? 1 : 0) >= 2 ? R.OK() : R.FALL();
        }else {
            return R.FALL();
        }
    }
}
