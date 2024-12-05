package com.bjpowernode.web;

import com.bjpowernode.domain.po.TTranHistory;
import com.bjpowernode.domain.po.TTranRemark;
import com.bjpowernode.domain.query.TranHistoryQuery;
import com.bjpowernode.domain.query.TranRemarkQuery;
import com.bjpowernode.domain.result.R;
import com.bjpowernode.service.TranHistoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TranHistoryController {

    @Resource
    private TranHistoryService tranHistoryService;

    @PostMapping(value = "/api/tran/history")
    public R addTranHistory(@RequestBody TranHistoryQuery tranHistoryQuery,
                           @RequestHeader(value = "Authorization") String token) {
        //1、前端axios的post提交过来的参数，是一个json，后端接收要使用@RequestBody注解接收，代码如下：
        /**
         *       doPost("/api/activity/remark", {
         *         activityId : this.activityDetail.id,
         *         noteContent : this.activityRemark.noteContent
         *       })
         */
        //2、前端axios的post提交一个formData，此时可以使用@RequestParam或者XxxQuery对象接收，代码如下：
        /**
         *       doPost("/api/user", formData)
         */
        tranHistoryQuery.setToken(token);
        int save = tranHistoryService.saveTranHistory(tranHistoryQuery);

        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 查询交易历史/阶段记录
     *
     * @param tranId
     * @return
     */
    @GetMapping(value = "/api/tran/{tranId}/history")
    public R tranRemarkPage(@PathVariable(value = "tranId") Integer tranId) {
        List<TTranHistory> tTranHistoryList = tranHistoryService.getTranHistoryByTranId(tranId);
        return R.OK(tTranHistoryList);
    }
}
