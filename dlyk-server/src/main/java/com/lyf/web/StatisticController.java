package com.lyf.web;

import com.lyf.result.NameValue;
import com.lyf.result.R;
import com.lyf.result.SummaryData;
import com.lyf.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计Controller
 */
@RestController
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    /**
     * 概览统计数据查询
     * @return
     */
    @GetMapping("/api/summary/data")
    public R summaryDate(){
        SummaryData summaryData = statisticService.loadSummaryData();
        return R.OK(summaryData);
    }

    /**
     * 获取销售漏斗图的数据
     * @return
     */
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

    /**
     * 获取线索来源饼图
     * @return
     */
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

    /**
     * 获取市场活动柱状图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/activityBarChart")
    public R activityBarChart() {
        /**
         * 每个月的数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        Integer[] activityDataArray = statisticService.getActivityBarChartData();
        return R.OK(activityDataArray);
    }

    /**
     * 获取线索柱状图的数据
     *
     * @return
     */
    @GetMapping(value = "/api/statistic/clueBarChart")
    public R clueBarChart() {
        //x轴的数据数组
        int days = LocalDate.now().lengthOfMonth();
        Integer[] xDataArray = new Integer[days];
        int a = 0;
        for (int i = 1; i <= days; i++) {
            xDataArray[a] = i;
            a ++;
        }

        /**
         * 每天的数据按如下的数组格式返回即可
         * [120, 200, 150, 80, 70, 110, 130],
         */
        Integer[] yDataArray = statisticService.getClueBarChartData();

        Map<String, Integer[]> resultMap = new HashMap<>();
        resultMap.put("x", xDataArray);
        resultMap.put("y", yDataArray);
        return R.OK(resultMap);
    }

}
