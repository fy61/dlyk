package com.lyf.service.impl;

import com.lyf.manager.StatisticManager;
import com.lyf.result.NameValue;
import com.lyf.result.SummaryData;
import com.lyf.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Resource
    private StatisticManager statisticManager;

    @Override
    public SummaryData loadSummaryData() {
        return statisticManager.loadSummaryData();
    }

    @Override
    public List<NameValue> loadSaleFunnelData() {
        return statisticManager.loadSaleFunnelData();

    }

    @Override
    public List<NameValue> loadSourcePieData() {
        return statisticManager.loadSourcePieData();
    }
}