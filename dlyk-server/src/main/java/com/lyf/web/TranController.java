package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.model.TTran;
import com.lyf.query.TranQuery;
import com.lyf.result.R;
import com.lyf.service.TranService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class TranController {

    @Resource
    private TranService tranService;

    /**
     * 创建交易
     *
     * @param tranQuery
     * @return
     */
    @PostMapping(value = "/api/customer/tran")
    public R createTran(@RequestBody TranQuery tranQuery, @RequestHeader(value = "Authorization") String token){
        tranQuery.setToken(token);
        int save = tranService.saveTran(tranQuery);
        return save >= 1 ? R.OK() : R.FALL();
    }

    /**
     * 分页查询交易列表数据
     *
     * @param current
     * @return
     */
    @GetMapping(value = "/api/trans")
    public R tranPage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TTran> pageInfo = tranService.getTranByPage(current);
        return R.OK(pageInfo);
    }

    /**
     * 查询交易详情
     *
     * @param tranId
     * @return
     */
    @GetMapping(value = "/api/tran/{tranId}")
    public R tranDetail(@PathVariable(value = "tranId") Integer tranId) {
        TTran tTran = tranService.getTranById(tranId);
        return R.OK(tTran);
    }
}
