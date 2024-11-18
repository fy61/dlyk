package com.lyf.web;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.lyf.constant.Constants;
import com.lyf.model.TCustomer;
import com.lyf.query.CustomerQuery;
import com.lyf.result.CustomerExcel;
import com.lyf.result.R;
import com.lyf.service.CustomerService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
public class CustomerController {
    @Resource
    private CustomerService customerService;

    @PostMapping("/api/clue/customer")
    public R convertCustomer(@RequestBody CustomerQuery customerQuery,@RequestHeader(value = "Authorization") String token){
        customerQuery.setToken(token);
        Boolean convert = customerService.convertCustomer(customerQuery);
        return convert ? R.OK() : R.FALL();
    }

    @GetMapping("/api/customers")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current) {
        if (current == null) {
            current = 1;
        }

        PageInfo<TCustomer> pageInfo = customerService.getCustomerByPage(current);
        return R.OK(pageInfo);
    }

    /**
     * 导出Excel
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/api/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {

        //要想让浏览器弹出下载框，你后端要设置一下响应头信息
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        //通知浏览器,这个是附件,需要下载
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(Constants.EXCEL_FILE_NAME+System.currentTimeMillis(), StandardCharsets.UTF_8) + ".xlsx");

        //2.后端查询数据库的数据,把数据写入Excel,把Excel以IO流的方式输出到前端(我们自己实现)
        List<CustomerExcel> dataList = customerService.getCustomerByExcel();

        EasyExcel.write(response.getOutputStream(), CustomerExcel.class)
                .sheet()
                .doWrite(dataList);
    }

}