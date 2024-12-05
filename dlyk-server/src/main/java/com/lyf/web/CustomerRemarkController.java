package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.mapper.TCustomerRemarkMapper;
import com.lyf.model.TCustomerRemark;
import com.lyf.query.CustomerRemarkQuery;
import com.lyf.result.R;
import com.lyf.service.CustomerRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerRemarkController {

    @Resource
    private CustomerRemarkService customerRemarkService;

    /**
     * 分页查询线索跟踪记录
     *
     * @param customerId
     * @param current
     * @return
     */
    @GetMapping(value = "/api/customer/remark")
    public R customerRemarkPage(@RequestParam(value = "current" ,required = false) Integer current,
                                @RequestParam(value = "customerId") Integer customerId) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TCustomerRemark> pageInfo = customerRemarkService.getCustomerRemarkByPage(current, customerId);
        return R.OK(pageInfo);
    }

    @PostMapping("/api/customer/submit")
    public R addCustomerRemark(@RequestBody CustomerRemarkQuery customerRemarkQuery,
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
        customerRemarkQuery.setToken((token));
        int save = customerRemarkService.saveCustomerRemark(customerRemarkQuery);
        return save >= 1 ? R.OK() : R.FALL();

    }
}
