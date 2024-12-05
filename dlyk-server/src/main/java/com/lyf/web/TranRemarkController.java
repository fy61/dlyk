package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TTranRemark;
import com.lyf.query.TranRemarkQuery;
import com.lyf.result.R;
import com.lyf.service.TranRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranRemarkController {
    @Resource
    private TranRemarkService tranRemarkService;

    @PostMapping(value = "/api/tran/submit")
    public R addTranRemark(@RequestBody TranRemarkQuery tranRemarkQuery,
                           @RequestHeader(value = "Authorization") String token) {

        tranRemarkQuery.setToken(token);
        int save = tranRemarkService.saveTranRemark(tranRemarkQuery);

        return save >= 1 ? R.OK() : R.FALL();
    }

    /**
     * 分页查询交易跟踪记录
     *
     * @param tranId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/tran/remark")
    public R tranRemarkPage(@RequestParam(value = "tranId") Integer tranId,
                            @RequestParam(value = "current") Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TTranRemark> pageInfo = tranRemarkService.getTranRemarkByPage(current, tranId);
        return R.OK(pageInfo);
    }
}
