package com.lyf.manager;


import com.lyf.mapper.TActivityMapper;
import com.lyf.mapper.TClueMapper;
import com.lyf.mapper.TCustomerMapper;
import com.lyf.mapper.TTranMapper;
import com.lyf.result.NameValue;
import com.lyf.result.SummaryData;
import com.lyf.result.TimeValue;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class StatisticManager {

    @Resource
    private TActivityMapper tActivityMapper;

    @Resource
    private TClueMapper tClueMapper;

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Resource
    private TTranMapper tTranMapper;

    /**
     * 查询概览统计数据
     *
     * @return
     */
    public SummaryData loadSummaryData() {
        //有效的市场活动总数
        Integer effectiveActivityCount = tActivityMapper.selectOngoingActivity().size(); //偷懒了一下

        //总的市场活动数
        Integer totalActivityCount = tActivityMapper.selectByCount();

        //线索总数
        Integer totalClueCount = tClueMapper.selectClueByCount();

        //客户总数
        Integer totalCustomerCount = tCustomerMapper.selectByCount();

        //成功的交易额
        BigDecimal successTranAmount = tTranMapper.selectBySuccessTranAmount();

        //总的交易额（包含成功和不成功的）
        BigDecimal totalTranAmount = tTranMapper.selectByTotalTranAmount();

        return SummaryData.builder()
                .effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalCustomerCount(totalCustomerCount)
                .successTranAmount(successTranAmount)
                .totalTranAmount(totalTranAmount)
                .build();
    }

    /**
     * 获取销售漏斗图数据
     *
     * @return
     */
    public List<NameValue> loadSaleFunnelData() {
        List<NameValue> resultList = new ArrayList<>();
        /**
         * [
         *    { value: 20, name: '成交' },
         *    { value: 60, name: '交易' },
         *    { value: 80, name: '客户' },
         *    { value: 100, name: '线索' }
         * ]
         *
         */
        //线索
        int clueCount = tClueMapper.selectClueByCount();
        //客户
        int customerCount = tCustomerMapper.selectByCount();
        //交易
        int tranCount = tTranMapper.selectByTotalTranCount();
        //成交
        int tranSuccessCount = tTranMapper.selectBySuccessTranCount();

        NameValue clue = NameValue.builder().name("线索").value(clueCount).build();
        resultList.add(clue);

        NameValue customer = NameValue.builder().name("客户").value(customerCount).build();
        resultList.add(customer);

        NameValue tran = NameValue.builder().name("交易").value(tranCount).build();
        resultList.add(tran);

        NameValue tranSuccess = NameValue.builder().name("成交").value(tranSuccessCount).build();
        resultList.add(tranSuccess);



        return resultList;
    }

    /**
     * 获取线索来源统计数据
     *
     * @return
     */
    public List<NameValue> loadSourcePieData() {
        return tClueMapper.selectBySource();
    }

    /**
     * 获取市场活动统计数据（按月统计，统计今年的）
     *
     * @return
     */
    public Integer[] getActivityBarChartData() {
        List<TimeValue> timeValueList = tActivityMapper.selectActivityByMonth();
        /**
         * 01	3
         * 02	1
         * 04	15
         * 05	3
         * 08	5
         * 10	1
         * 11	4
         * 分析一下：
         * 3月没有数据、6月没有数据、7月没有数据、9月没有数据
         * 没有数据的月份要补0，不然总共11个月，只有7条数据，那么就会导致前端显示的时候，月份和对应的数字错位
         */
        //[3, 1, 0, 15, 3, 0, 0, 5, 0, 1, 4]
        int monthValue = LocalDate.now().getMonthValue();//当前是几月（11月）

//        //现在是11月，那么数组大小就是11
//        Integer[] resultArray = new Integer[monthValue];

        /*//数组的下标
        int a = 0;
        for (int i=1; i<=monthValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String month = timeValue.getTime(); //月份
                month = month.startsWith("0") ? month.substring(1) : month; //月份如果是0开头，把0去掉

                Integer value = timeValue.getValue();//月份对应的值（条数）

                if (month.equals(String.valueOf(i))) {
                    resultArray[a] = value;
                    break;
                } else {
                    resultArray[a] = 0;
                }
            }
            //数组下标+1
            a++;
        }*/

        Map<String, Integer> timeValueMap = timeValueList.stream()
                .collect(Collectors.toMap(tv -> tv.getTime().startsWith("0") ? tv.getTime().substring(1) : tv.getTime(), TimeValue::getValue));

        Integer[] resultArray = new Integer[monthValue];
        for (int i = 1; i <= monthValue; i++) {
            resultArray[i - 1] = timeValueMap.getOrDefault(String.valueOf(i), 0);
        }


        //返回结果数据
        return resultArray;
    }


    /**
     * 查询线索统计数据
     *
     * @return
     */
    public Integer[] getClueBarChartData() {
        List<TimeValue> timeValueList = tClueMapper.selectClueByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（28号）

//        //现在是28号，那么数组大小就是28
//        Integer[] resultArray = new Integer[dayValue];

//        //数组的下标
//        int a = 0;
//        for (int i=1; i<=dayValue; i++) {//当前是28月，那么我们循环28次，往结果数组中要放28条数据
//            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
//                String day = timeValue.getTime(); //天
//                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉
//
//                Integer value = timeValue.getValue();//天对应的值（条数）
//
//                if (day.equals(String.valueOf(i))) {
//                    resultArray[a] = value;
//                    break;
//                } else {
//                    resultArray[a] = 0;
//                }
//            }
//            //数组下标+1
//            a++;
//        }
        Map<String, Integer> timeValueMap = timeValueList.stream()
                .collect(Collectors.toMap(tv -> tv.getTime().startsWith("0") ? tv.getTime().substring(1) : tv.getTime(), TimeValue::getValue));

        Integer[] resultArray = new Integer[dayValue];
        for (int i = 1; i <= dayValue; i++) {
            resultArray[i - 1] = timeValueMap.getOrDefault(String.valueOf(i), 0);
        }


        //返回结果数据
        return resultArray;
    }

    /**
     * 查询客户统计数据（按天）（当前月）
     *
     * @return
     */
    public Integer[] getCustomerBarChartData() {
        List<TimeValue> timeValueList = tCustomerMapper.selectCustomerByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

/*        //现在是13号，那么数组大小就是13
        Integer[] resultArray = new Integer[dayValue];*/

        //数组的下标
/*        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                Integer value = timeValue.getValue();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = value;
                    break;
                } else {
                    resultArray[a] = 0;
                }
            }
            //数组下标+1
            a++;
        }*/

        Map<String, Integer> timeValueMap = timeValueList.stream()
                .collect(Collectors.toMap(tv -> tv.getTime().startsWith("0") ? tv.getTime().substring(1) : tv.getTime(), TimeValue::getValue));

        Integer[] resultArray = new Integer[dayValue];
        for (int i = 1; i <= dayValue; i++) {
            resultArray[i - 1] = timeValueMap.getOrDefault(String.valueOf(i), 0);
        }

        //返回结果数据
        return resultArray;
    }

    public BigDecimal[] getTranBarChartData() {

        List<TimeValue> timeValueList = tTranMapper.selectTranByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

        //现在是13号，那么数组大小就是13
        BigDecimal[] resultArray = new BigDecimal[dayValue];

//        Map<String, Integer> timeValueMap = timeValueList.stream()
//                .collect(Collectors.toMap(tv -> tv.getTime().startsWith("0") ? tv.getTime().substring(1) : tv.getTime(), TimeValue::getValue));
//
//        BigDecimal[] resultArray = new BigDecimal[dayValue];
//        for (int i = 1; i <= dayValue; i++) {
//            resultArray[i - 1] = BigDecimal.valueOf(timeValueMap.getOrDefault(String.valueOf(i), 0));
//        }

        //数组的下标
        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                BigDecimal amount = timeValue.getAmount();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = amount;
                    break;
                } else {
                    resultArray[a] = new BigDecimal(0);
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }

    public BigDecimal[] getSuccessTranBarChartData() {

        List<TimeValue> timeValueList = tTranMapper.selectSuccessTranByDay();
        /**
         * 03	2
         * 07	5
         * 09	6
         * 12	3
         * 13	3
         * 分析一下：
         * 1、2、4、5、6、8、10、11没有数据
         * 没有数据的那一天要补0，不然总共13天，只有5条数据，那么就会导致前端显示的时候，天和对应的数字错位
         */
        //[0, 0, 2, 0, 0, 0, 5, 0, 6, 0, 0, 3, 3]
        int dayValue = LocalDate.now().getDayOfMonth();//当前是几号（13号）

        //现在是13号，那么数组大小就是13
        BigDecimal[] resultArray = new BigDecimal[dayValue];

//        Map<String, Integer> timeValueMap = timeValueList.stream()
//                .collect(Collectors.toMap(tv -> tv.getTime().startsWith("0") ? tv.getTime().substring(1) : tv.getTime(), TimeValue::getValue));
//
//        BigDecimal[] resultArray = new BigDecimal[dayValue];
//        for (int i = 1; i <= dayValue; i++) {
//            resultArray[i - 1] = BigDecimal.valueOf(timeValueMap.getOrDefault(String.valueOf(i), 0));
//        }

        //数组的下标
        int a = 0;
        for (int i=1; i<=dayValue; i++) {//当前是11月，那么我们循环11次，往结果数组中要放11条数据
            for (TimeValue timeValue : timeValueList) { //循环从数据库中查询出来的 月份及对应的数据
                String day = timeValue.getTime(); //天
                day = day.startsWith("0") ? day.substring(1) : day; //月份如果是0开头，把0去掉

                BigDecimal amount = timeValue.getAmount();//天对应的值（条数）

                if (day.equals(String.valueOf(i))) {
                    resultArray[a] = amount;
                    break;
                } else {
                    resultArray[a] = new BigDecimal(0);
                }
            }
            //数组下标+1
            a++;
        }

        //返回结果数据
        return resultArray;
    }
}
