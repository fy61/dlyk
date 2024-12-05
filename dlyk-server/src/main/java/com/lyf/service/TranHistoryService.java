package com.bjpowernode.service;

import com.bjpowernode.domain.po.TTranHistory;
import com.bjpowernode.domain.query.TranHistoryQuery;

import java.util.List;

public interface TranHistoryService {

    int saveTranHistory(TranHistoryQuery tranHistoryQuery);

    List<TTranHistory> getTranHistoryByTranId(Integer tranId);
}
