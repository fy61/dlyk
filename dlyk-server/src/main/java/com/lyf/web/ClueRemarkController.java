package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TActivityRemark;
import com.lyf.model.TClueRemark;
import com.lyf.query.ActivityRemarkQuery;
import com.lyf.query.ClueRemarkQuery;
import com.lyf.result.R;
import com.lyf.service.ClueRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClueRemarkController {
    @Resource
    private ClueRemarkService clueRemarkService;

    @PostMapping("/api/clue/remark")
    public R addActivityRemark(@RequestBody ClueRemarkQuery clueRemarkQuery, @RequestHeader(value = "Authorization") String token) {
        //axios提交post请求，提交过来的是json数据，使用@RequestBody注解接收
        clueRemarkQuery.setToken(token);
        int save = clueRemarkService.saveClueRemark(clueRemarkQuery);
        return save >= 1 ? R.OK( ) : R.FALL();
    }

    @GetMapping("/api/clue/remark")
    public R clueRemarkPage(@RequestParam(value = "current" ,required = false) Integer current,
                                @RequestParam(value = "clueId") Integer clueId ){
        //required = false 可以不传,也可以传
        ClueRemarkQuery query = new ClueRemarkQuery();
        query.setClueId(clueId);

        if (current == null) {
            current = 1;
        }
        PageInfo<TClueRemark> pageInfo = clueRemarkService.getClueRemarkByPage(current,query);
        return R.OK(pageInfo);
    }
}
