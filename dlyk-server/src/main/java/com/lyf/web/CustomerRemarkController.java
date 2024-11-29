package com.lyf.web;

import com.github.pagehelper.PageInfo;
import com.lyf.mapper.TCustomerRemarkMapper;
import com.lyf.model.TCustomerRemark;
import com.lyf.result.R;
import com.lyf.service.CustomerRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
