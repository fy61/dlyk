package com.lyf.service;

import com.lyf.result.NameValue;
import com.lyf.result.SummaryData;

import java.util.List;

public interface StatisticService {
    SummaryData loadSummaryData();

    List<NameValue> loadSaleFunnelData();

    List<NameValue> loadSourcePieData();

    Integer[] getActivityBarChartData();

    Integer[] getClueBarChartData();
}
