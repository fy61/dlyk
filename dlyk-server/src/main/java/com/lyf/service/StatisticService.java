package com.lyf.service;

import com.lyf.result.NameValue;
import com.lyf.result.SummaryData;

import java.math.BigDecimal;
import java.util.List;

public interface StatisticService {
    SummaryData loadSummaryData();

    List<NameValue> loadSaleFunnelData();

    List<NameValue> loadSourcePieData();

    Integer[] getActivityBarChartData();

    Integer[] getClueBarChartData();

    Integer[] getCustomerBarChartData();

    BigDecimal[] getTranBarChartData();

    BigDecimal[] getSuccessTranBarChartData();
}
