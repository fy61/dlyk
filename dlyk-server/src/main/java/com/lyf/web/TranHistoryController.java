package com.lyf.web;


import com.lyf.model.TTranHistory;
import com.lyf.query.TranHistoryQuery;
import com.lyf.result.R;
import com.lyf.service.TranHistoryService;
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

        return save >= 1 ? R.OK() : R.FALL();
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
