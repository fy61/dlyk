package com.lyf.web;

import com.lyf.result.NameValue;
import com.lyf.result.R;
import com.lyf.result.SummaryData;
import com.lyf.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据统计Controller
 */
@RestController
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    @GetMapping("/api/summary/data")
    public R summaryDate(){
        SummaryData summaryData = statisticService.loadSummaryData();
        return R.OK(summaryData);
    }

    @GetMapping("/api/saleFunnel/data")
    public R saleFunnelData(){
        /**
         * [
         *    { value: 20, name: '成交' },
         *    { value: 60, name: '交易' },
         *    { value: 80, name: '客户' },
         *    { value: 100, name: '线索' }
         * ]
         *
         */
        List<NameValue> nameValueList = statisticService.loadSaleFunnelData();
        return R.OK(nameValueList);
    }

    @GetMapping(value = "/api/sourcePie/data")
    public R sourcePieData() {
        /**
         *   [
         *       { value: 1048, name: 'Search Engine' },
         *       { value: 735, name: 'Direct' },
         *       { value: 580, name: 'Email' },
         *       { value: 484, name: 'Union Ads' },
         *       { value: 300, name: 'Video Ads' }
         *   ]
         *
         */
        List<NameValue> nameValueList = statisticService.loadSourcePieData();
        return R.OK(nameValueList);
    }
}
